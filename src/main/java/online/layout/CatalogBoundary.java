package online.layout;

import online.data.CatalogEntity;

public class CatalogBoundary {
	private CatalogKey key;
	private String name;
	private String creatorEmail;
	private String onlineStore;
	private boolean expired;
	
	public CatalogBoundary() {
		// TODO Auto-generated constructor stub
	}

	public CatalogBoundary(CatalogEntity catalog) {
		key = new CatalogKey(catalog.getCatalogOnlineStore(), catalog.getCatalogId());
		name = catalog.getName();
		creatorEmail = catalog.getCreatorEmail();
		expired = catalog.isExpired();
		onlineStore = catalog.getCatalogOnlineStore();
	}

	public CatalogKey getKey() {
		return key;
	}

	public void setKey(CatalogKey key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	
	public String getOnlineStore() {
		return onlineStore;
	}
	
	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	public CatalogEntity toEntity() {
		CatalogEntity catalog = new CatalogEntity();
		
		catalog.setCatalogOnlineStore(this.getOnlineStore());
		catalog.setCreatorEmail(this.getCreatorEmail());
		catalog.setName(this.getName());
		catalog.setExpired(this.isExpired());		
		
		return catalog;
	}
	
	
}
