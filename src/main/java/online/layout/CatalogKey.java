package online.layout;

public class CatalogKey {
	private String onlineStore;
	private String catalogId;
	
	public CatalogKey() {
		// TODO Auto-generated constructor stub
	}

	public CatalogKey(String onlineStore, String catalogId) {
		this.onlineStore = onlineStore;
		this.catalogId = catalogId;
	}

	public String getOnlineStore() {
		return onlineStore;
	}

	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
}
