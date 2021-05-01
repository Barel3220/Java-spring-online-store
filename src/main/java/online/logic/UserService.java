package online.logic;

import java.util.List;

import online.data.UserEntity;

public interface UserService {
	public UserEntity publishNewUser(UserEntity userEntity);
	public List<UserEntity> getUsers (int size, int page, String role);
	public String checkUserRole(String onlineStore, String userEmail, String role);
	public UserEntity getSpecificUser(String userOnlineStore, String userEmail);
}
