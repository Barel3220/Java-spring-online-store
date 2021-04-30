package online.dao;

import java.util.List;

import online.data.UserEntity;

public interface AdvancedUserDao extends UserDao<String> {
	public List<UserEntity> readAll(int size, int page);
}
