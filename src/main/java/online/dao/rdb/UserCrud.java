package online.dao.rdb;

import org.springframework.data.repository.PagingAndSortingRepository;

import online.data.UserEntity;

public interface UserCrud extends PagingAndSortingRepository<UserEntity, String>{

}
