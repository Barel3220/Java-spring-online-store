package online.dao;

import java.util.List;
import java.util.Optional;

import online.data.ItemEntity;

public interface ItemDao<ItemKey> {
	public ItemEntity create(ItemEntity item);
	public Optional<ItemEntity> readById(ItemKey key);
	public List<ItemEntity> readAll();
	public void update(ItemEntity item);
	public void deleteByKey(ItemKey key);
	public void deleteAll();
}
