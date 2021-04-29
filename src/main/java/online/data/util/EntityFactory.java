package online.data.util;

import java.util.Date;
import java.util.Map;

import online.data.UserEntity;
import online.data.UserRole;


public interface EntityFactory {
	public UserEntity createNewUser(String userOnlineStore, String userEmail, String userName, UserRole role);
}
