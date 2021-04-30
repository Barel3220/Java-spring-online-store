package online.data.util;

import org.springframework.stereotype.Component;

import online.data.UserEntity;
import online.data.UserRole;

@Component
public class EntityFactoryImplementation implements EntityFactory {

	@Override
	public UserEntity createNewUser(String userOnlineStore, String userEmail, String userName, UserRole role) {
		UserEntity user = new UserEntity(userOnlineStore, userEmail, userName, role);
		return user;
	}

}
