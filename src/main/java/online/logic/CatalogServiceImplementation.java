package online.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aop.UserRoleValidation;
import online.dao.AdvancedCatalogDao;
import online.dao.CatalogNotFoundException;
import online.dao.UserNotFoundException;
import online.dao.UserRoleException;
import online.data.CatalogEntity;

@Service
public class CatalogServiceImplementation implements CatalogService {
	private AdvancedCatalogDao advancedCatalogDao;
	
	@Autowired
	public void setAdvancedCatalogDao(AdvancedCatalogDao advancedCatalogDao) {
		this.advancedCatalogDao = advancedCatalogDao;
	}

	@Override
	@Transactional
	public CatalogEntity publishNewCatalog(CatalogEntity catalog, String role) {
		if (!validate(catalog) && roles(role))
			throw new RuntimeException("Invalid Catalog");
		else
			return this.advancedCatalogDao.create(catalog);
	}

	@Override
	public List<CatalogEntity> getCatalogs(int size, int page, String role) {
		roles(role);
		return this.advancedCatalogDao.readAll(size, page);
	}
	
	@Override
	public List<CatalogEntity> getCatalogsByCatalogOnlineStore(int size, int page, String catalogOnlineStore) {
		return this.advancedCatalogDao.readAllByCatalogOnlineStore(size, page, catalogOnlineStore);
	}

	@Override
	public CatalogEntity getSpecificCatalog(String catalogOnlineStore, String catalogId) {
		Optional<CatalogEntity> catalog = this.advancedCatalogDao.readById(catalogOnlineStore + "#" + catalogId);
		if (catalog.isPresent())
			return catalog.get();
		else
			throw new CatalogNotFoundException("Catalog Doesn't Exist");
	}
	
	@Override
	@UserRoleValidation
	public String checkUserRole(String onlineStore, String userEmail, String role) {
		return role;
	}
	
	private boolean validate(CatalogEntity catalog) {
		return catalog != null
				&& catalog.getCatalogOnlineStore() != null && !catalog.getCatalogOnlineStore().trim().isEmpty()
				&& catalog.getCreatorEmail() != null && !catalog.getCreatorEmail().trim().isEmpty()
				&& catalog.getName() != null && !catalog.getName().trim().isEmpty();
		
	}
	
	private boolean roles(String role) {
		if (role == "null")
			throw new UserNotFoundException("User Doesn't Exist");
		else if (role != "admin" && role != "manager")
			throw new UserRoleException("User MUST be Admin or Manager to complete this operation");
		else
			return true;
	}
}
