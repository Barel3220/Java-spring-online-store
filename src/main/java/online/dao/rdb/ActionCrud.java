package online.dao.rdb;

import org.springframework.data.repository.PagingAndSortingRepository;

import online.data.ActionEntity;

public interface ActionCrud extends PagingAndSortingRepository<ActionEntity, String> {

}
