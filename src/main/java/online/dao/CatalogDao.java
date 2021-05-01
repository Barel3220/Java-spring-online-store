package online.dao;

import java.util.List;
import java.util.Optional;

import online.data.CatalogEntity;

public interface CatalogDao<CatalogKey> {
	public CatalogEntity create(CatalogEntity catalog);
	public Optional<CatalogEntity> readById(CatalogKey key);
	public List<CatalogEntity> readAll();
	public void update(CatalogEntity catalog);
	public void deleteByKey(CatalogKey key);
	public void deleteAll();
}
