package online.data.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import online.data.CatalogEntity;
import online.data.ItemEntity;
import online.data.UserEntity;
import online.data.UserRole;

@Component
public class EntityFactoryImplementation implements EntityFactory {

	@Override
	public UserEntity createNewUser(String userOnlineStore, String userEmail, String userName, UserRole role) {
		UserEntity user = new UserEntity(userOnlineStore, userEmail, userName, role);
		return user;
	}

	@Override
	public CatalogEntity createNewCatalog(String catalogOnlineStore, String name, boolean expired,
			String creatorEmail) {
		CatalogEntity catalog = new CatalogEntity(catalogOnlineStore, name, expired, creatorEmail);
		return catalog;
	}

	@Override
	public ItemEntity createNewItem(String catalogId, String itemOnlineStore, String creatorEmail, String name, String price,
			boolean expired, int amountInStock, Map<String, Object> moreAttributes) {
		ItemEntity item = new ItemEntity(catalogId, itemOnlineStore, creatorEmail, name, price, expired, amountInStock, moreAttributes);
		return item;
	}

}

