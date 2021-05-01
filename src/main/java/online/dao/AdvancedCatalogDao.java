package online.dao;

import java.util.List;

import online.data.CatalogEntity;

public interface AdvancedCatalogDao extends CatalogDao<String> {
	public List<CatalogEntity> readAll(int size, int page);
}
