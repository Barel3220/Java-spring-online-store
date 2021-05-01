package online.data.util;

import java.util.Map;

import online.data.CatalogEntity;
import online.data.ItemEntity;
import online.data.UserEntity;
import online.data.UserRole;


public interface EntityFactory {
	public UserEntity createNewUser(String userOnlineStore, String userEmail, String userName, UserRole role);
	public CatalogEntity createNewCatalog(String catalogOnlineStore, String name, boolean expired, String creatorEmail);
	public ItemEntity createNewItem(String catalogId, String itemOnlineStore, String creatorEmail, String name, String price,
			boolean expired, int amountInStock, Map<String, Object> moreAttributes);
}
