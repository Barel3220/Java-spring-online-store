package online.logic;

import java.util.List;

import online.data.ItemEntity;

public interface ItemService {
	public ItemEntity publishNewItem(ItemEntity item, String role);
	public List<ItemEntity> getItems(int size, int page, String role);
	public String checkUserRole(String onlineStore, String userEmail, String role);
	public ItemEntity getSpecificItem(String itemOnlineStore, String creatorEmail);
}
