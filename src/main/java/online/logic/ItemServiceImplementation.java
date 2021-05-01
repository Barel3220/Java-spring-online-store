package online.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aop.UserRoleValidation;
import online.dao.AdvancedItemDao;
import online.dao.ItemNotFoundException;
import online.dao.UserNotFoundException;
import online.dao.UserRoleException;
import online.data.ItemEntity;

@Service
public class ItemServiceImplementation implements ItemService {
	private AdvancedItemDao advancedItemDao;
	
	@Autowired
	public void setAdvancedItemDao(AdvancedItemDao advancedItemDao) {
		this.advancedItemDao = advancedItemDao;
	}

	@Override
	@Transactional
	public ItemEntity publishNewItem(ItemEntity item, String role) {
		if (!validate(item) && roles(role))
			throw new RuntimeException("Invalid Item");
		else
			return this.advancedItemDao.create(item);
	}

	@Override
	public List<ItemEntity> getItems(int size, int page, String role) {
		roles(role);
		return this.advancedItemDao.readAll(size, page);
	}
	
	@Override
	public List<ItemEntity> getItemsByCatalogId(int size, int page, String role, String catalogId) {
		if (role == "customer")
			return this.advancedItemDao.readAllByCatalogId(size, page, catalogId);		
		else
			throw new UserRoleException("User MUST be Customer to complete this operation");
	}

	@Override
	@UserRoleValidation
	public String checkUserRole(String onlineStore, String userEmail, String role) {
		return role;
	}

	@Override
	public ItemEntity getSpecificItem(String itemOnlineStore, String itemId) {
		Optional<ItemEntity> item = this.advancedItemDao.readById(itemOnlineStore + "#" + itemId);
		if (item.isPresent())
			return item.get();
		else throw new ItemNotFoundException("Item Doesn't Exist");
	}
	
	private boolean validate(ItemEntity item) {
		return item != null
				&& item.getItemOnlineStore() != null && !item.getItemOnlineStore().trim().isEmpty()
				&& item.getCatalogId() != null && !item.getCatalogId().trim().isEmpty()
				&& item.getName() != null && !item.getName().trim().isEmpty()
				&& item.getPrice() != null && !item.getPrice().trim().isEmpty()
				&& item.getCreatorEmail() != null && !item.getCreatorEmail().trim().isEmpty();
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
