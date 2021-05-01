package online.layout;

import java.util.Map;

import online.data.ItemEntity;

public class ItemBoundary {
	private ItemKey key;
	private String catalogId;
	private String onlineStore;
	private String creatorEmail;
	private String name;
	private String price;
	private boolean expired;
	private int amountInStock;
	private Map<String, Object> moreAttributes;
	
	public ItemBoundary() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemBoundary(ItemEntity item) {
		key = new ItemKey(item.getItemOnlineStore(), item.getItemId());
		catalogId = item.getCatalogId();
		onlineStore = item.getItemOnlineStore();
		creatorEmail = item.getCreatorEmail();
		name = item.getName();
		price = item.getPrice();
		expired = item.isExpired();
		amountInStock = item.getAmountInStock();
		moreAttributes = item.getMoreAttributes();
	}

	public ItemKey getKey() {
		return key;
	}

	public void setKey(ItemKey key) {
		this.key = key;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getOnlineStore() {
		return onlineStore;
	}

	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public int getAmountInStock() {
		return amountInStock;
	}

	public void setAmountInStock(int amountInStock) {
		this.amountInStock = amountInStock;
	}

	public Map<String, Object> getMoreAttributes() {
		return moreAttributes;
	}

	public void setMoreAttributes(Map<String, Object> moreAttributes) {
		this.moreAttributes = moreAttributes;
	}
	
	public ItemEntity toEntity() {
		ItemEntity item = new ItemEntity();
		
		item.setCatalogId(this.getCatalogId());
		item.setItemOnlineStore(this.getOnlineStore());
		item.setCreatorEmail(this.getCreatorEmail());
		item.setName(this.getName());
		item.setPrice(this.getPrice());
		item.setExpired(this.isExpired());
		item.setAmountInStock(this.getAmountInStock());
		item.setMoreAttributes(this.getMoreAttributes());
		
		return item;
	}
}
