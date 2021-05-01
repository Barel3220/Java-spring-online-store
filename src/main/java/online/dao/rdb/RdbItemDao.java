package online.dao.rdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import online.dao.AdvancedItemDao;
import online.data.ItemEntity;

@Repository
public class RdbItemDao implements AdvancedItemDao {
	private ItemCrud itemCrud;
	private IdCreatorCrud idCreatorCrud;
	
	public RdbItemDao() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public void setItemCrud(ItemCrud itemCrud) {
		this.itemCrud = itemCrud;
	}
	
	@Autowired
	public void setIdCreatorCrud(IdCreatorCrud idCreatorCrud) {
		this.idCreatorCrud = idCreatorCrud;
	}

	@Override
	@Transactional
	public ItemEntity create(ItemEntity item) {
		IdCreator idCreator = this.idCreatorCrud.save(new IdCreator());
		item.setKey(item.getItemOnlineStore() + "#" + idCreator.getId());
		
		// SQL INSERT
		if (!this.itemCrud.existsById(item.getKey())) 
			if (!checkDuplicates(item)) {
			ItemEntity itemEntity = this.itemCrud.save(item);
			this.idCreatorCrud.delete(idCreator);
			return itemEntity;
			}
			else
				throw new RuntimeException("Item Already Exists by Value");		
		else
			throw new RuntimeException("Item Already Exists, Id: " + item.getKey());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ItemEntity> readById(String key) {
		return this.itemCrud.findById(key);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemEntity> readAll() {
		List<ItemEntity> list = new ArrayList<ItemEntity>();
		this.itemCrud.findAll().forEach(list::add);
		return list;
	}

	@Override
	@Transactional
	public void update(ItemEntity item) {
		ItemEntity found = this.readById(item.getKey())
				.orElseThrow(() -> new RuntimeException("Invalid Item Key: " + item.getKey()));
		
		if (item.getName() != null)
			found.setName(item.getName());
		
		if (item.getPrice() != null || !item.getPrice().trim().isEmpty())
			found.setPrice(item.getPrice());
		
		if (item.getAmountInStock() != 0)
			found.setAmountInStock(item.getAmountInStock());
		
		this.itemCrud.save(found);
	}

	@Override
	@Transactional
	public void deleteByKey(String key) {
		ItemEntity found = readById(key).orElseThrow(() -> new RuntimeException("Invalid Item key: " + key));
		this.itemCrud.delete(found);
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.itemCrud.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemEntity> readAll(int size, int page) {
		return this.itemCrud.findAll(PageRequest.of(page, size)).getContent();
	}
	
	@Override
	public List<ItemEntity> readAllByCatalogId(int size, int page, String catalogId) {
		return this.itemCrud.findAllByCatalogId(catalogId, PageRequest.of(page, size));
	}
	
	public boolean checkDuplicates(ItemEntity item) {
		List<ItemEntity> list = this.readAll();
		for (ItemEntity itemEntity : list) {
			if (item.getCatalogId() == itemEntity.getCatalogId()
					&& item.getCreatorEmail() == itemEntity.getCreatorEmail()
					&& item.getItemOnlineStore() == itemEntity.getItemOnlineStore()
					&& item.getName() == itemEntity.getName()
					&& item.getPrice() == itemEntity.getPrice()
					&& item.getAmountInStock() == itemEntity.getAmountInStock()
					&& item.isExpired() == itemEntity.isExpired())
				return true;
		}
		return false;
	}	
}
