package online.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

import org.springframework.data.annotation.Id;

@Document(collection="ITEMS")
public class ItemEntity implements OnlineStoreEntity<String> {
	private String key;
	private String itemId;
	private String catalogId;
	private String itemOnlineStore;
	private String creatorEmail;
	private String name;
	private String price;
	private boolean expired;
	private int amountInStock;
	private Map<String, Object> moreAttributes;
	
	public ItemEntity() {
		// TODO Auto-generated constructor stub
	}

	public ItemEntity(String catalogId, String itemOnlineStore, String creatorEmail, String name, String price,
			boolean expired, int amountInStock, Map<String, Object> moreAttributes) {
		this.catalogId = catalogId;
		this.itemOnlineStore = itemOnlineStore;
		this.creatorEmail = creatorEmail;
		this.name = name;
		this.price = price;
		this.expired = expired;
		this.amountInStock = amountInStock;
		this.moreAttributes = moreAttributes;
	}
	
	@Override
	@Id
	public String getKey() {
		this.key = this.itemOnlineStore + "#" + this.itemId;
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
		String[] temp = key.split("#");
		this.itemOnlineStore = temp[0];
		this.itemId = temp[1];
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getItemOnlineStore() {
		return itemOnlineStore;
	}

	public void setItemOnlineStore(String itemOnlineStore) {
		this.itemOnlineStore = itemOnlineStore;
	}
	
	public String getCreatorEmail() {
		return this.creatorEmail;
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
}
