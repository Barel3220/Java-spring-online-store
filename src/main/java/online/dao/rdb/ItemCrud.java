package online.dao.rdb;

import org.springframework.data.repository.PagingAndSortingRepository;

import online.data.ItemEntity;

public interface ItemCrud extends PagingAndSortingRepository<ItemEntity, String> {

}
