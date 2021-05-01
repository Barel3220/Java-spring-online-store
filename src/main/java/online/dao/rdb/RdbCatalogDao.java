package online.dao.rdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import online.dao.AdvancedCatalogDao;
import online.data.CatalogEntity;

@Repository
public class RdbCatalogDao implements AdvancedCatalogDao {
	private CatalogCrud catalogCrud;
	private IdCreatorCrud idCreatorCrud;
	
	public RdbCatalogDao() {
		// TODO Auto-generated method stub
	}	
	
	@Autowired
	public void setCatalogCrud(CatalogCrud catalogCrud) {
		this.catalogCrud = catalogCrud;
	}
	
	@Autowired
	public void setIdCreatorCrud(IdCreatorCrud idCreatorCrud) {
		this.idCreatorCrud = idCreatorCrud;
	}

	@Override
	@Transactional
	public CatalogEntity create(CatalogEntity catalog) {
		IdCreator idCreator = this.idCreatorCrud.save(new IdCreator());
		catalog.setKey(catalog.getCatalogOnlineStore() + "#" + idCreator.getId());
		
		// SQL INSERT
		if (!this.catalogCrud.existsById(catalog.getKey())) {
			CatalogEntity catalogEntity = this.catalogCrud.save(catalog);
			this.idCreatorCrud.delete(idCreator);
			return catalogEntity;
		}
		else
			throw new RuntimeException("Catalog already exists, id: " + catalog.getKey());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CatalogEntity> readById(String key) {
		return this.catalogCrud.findById(key);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CatalogEntity> readAll() {
		List<CatalogEntity> list = new ArrayList<CatalogEntity>();
		this.catalogCrud.findAll().forEach(list::add);
		return list;
	}

	@Override
	@Transactional
	public void update(CatalogEntity element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void deleteByKey(String key) {
		CatalogEntity found = readById(key).orElseThrow(() -> new RuntimeException("Invalid Catalog key: " + key));
		this.catalogCrud.delete(found);
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.catalogCrud.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<CatalogEntity> readAll(int size, int page) {
		return this.catalogCrud.findAll(PageRequest.of(page, size)).getContent();
	}

}
