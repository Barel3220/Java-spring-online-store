package online.dao.rdb;

import org.springframework.data.repository.PagingAndSortingRepository;

import online.data.CatalogEntity;

public interface CatalogCrud extends PagingAndSortingRepository<CatalogEntity, String> {
	
}
