package online.dao.rdb;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import online.data.ItemEntity;

public interface ItemCrud extends PagingAndSortingRepository<ItemEntity, String> {
	public List<ItemEntity> findAllByCatalogId(@Param("catalogId") String catalogId, Pageable pageable);
}
