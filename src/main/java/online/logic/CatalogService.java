package online.logic;

import java.util.List;

import online.data.CatalogEntity;

public interface CatalogService {
	public CatalogEntity publishNewCatalog(CatalogEntity catalog, String role);
	public List<CatalogEntity> getCatalogs(int size, int page, String role);
	public List<CatalogEntity> getCatalogsByCatalogOnlineStore(int size, int page, String catalogOnlineStore);
	public String checkUserRole(String onlineStore, String userEmail, String role);
	public CatalogEntity getSpecificCatalog(String catalogOnlineStore, String catalogId);
}
