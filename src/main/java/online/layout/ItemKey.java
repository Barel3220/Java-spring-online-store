package online.layout;

public class ItemKey {
	private String onlineStore;
	private String itemId;
	
	public ItemKey() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemKey(String onlineStore, String itemId) {
		this.onlineStore = onlineStore;
		this.itemId = itemId;
	}

	public String getOnlineStore() {
		return onlineStore;
	}

	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
