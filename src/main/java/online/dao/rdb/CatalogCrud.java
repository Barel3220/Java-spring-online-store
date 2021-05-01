package online.dao.rdb;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import online.data.CatalogEntity;

public interface CatalogCrud extends PagingAndSortingRepository<CatalogEntity, String> {
	public List<CatalogEntity> findAllByCatalogOnlineStore(@Param("catalogOnlineStore") String catalogOnlineStore, Pageable pageable);
	
}
