package online.dao;

import java.util.List;

import online.data.ItemEntity;

public interface AdvancedItemDao extends ItemDao<String> {
	public List<ItemEntity> readAll(int size, int page);
	public List<ItemEntity> readAllByCatalogId(int size, int page, String catalogId);
}
