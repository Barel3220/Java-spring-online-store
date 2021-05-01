package online.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection="CATALOGS")
public class CatalogEntity implements OnlineStoreEntity<String> {
	private String key;
	private String catalogId;
	private String catalogOnlineStore;
	private String name;
	private String creatorEmail;
	private boolean expired;
	
	public CatalogEntity() {
		// TODO Auto-generated constructor stub
	}

	public CatalogEntity(String catalogOnlineStore, String name, boolean expired, String creatorEmail) {
		this.catalogOnlineStore = catalogOnlineStore;
		this.name = name;
		this.expired = expired;
		this.creatorEmail = creatorEmail;
	}

	@Override
	@Id
	public String getKey() {
		this.key = this.catalogOnlineStore + "#" + this.catalogId;
		return this.key;
	}
	
	@Override
	public void setKey(String key) {
		this.key = key;
		String[] temp = key.split("#");
		this.catalogOnlineStore = temp[0];
		this.catalogId = temp[1];
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogOnlineStore() {
		return catalogOnlineStore;
	}

	public void setCatalogOnlineStore(String catalogOnlineStore) {
		this.catalogOnlineStore = catalogOnlineStore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
}
