package online.logic;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aop.UserRoleValidation;
import online.dao.AdvancedUserDao;
import online.dao.UserNotFoundException;
import online.dao.UserRoleException;
import online.data.UserEntity;

@Service
public class UserServiceImplementation implements UserService {
	private AdvancedUserDao advancedUserDao;
	public final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Autowired
	public void setAdvancedUserDao(AdvancedUserDao advancedUserDao) {
		this.advancedUserDao = advancedUserDao;
	}
	
	@Override
	@Transactional
	public UserEntity publishNewUser(UserEntity userEntity) {
		if (!validate(userEntity)) 
			throw new RuntimeException("Invalid User");
		 else 
			return this.advancedUserDao.create(userEntity);
	}

	@Override
	public List<UserEntity> getUsers(int size, int page, String role) {
		if (role == "null")
			throw new UserNotFoundException("User Doesn't Exist");
		else if (role != "admin")
			throw new UserRoleException("User MUST be Admin to complete this operation");
		else
			return this.advancedUserDao.readAll(size, page);
	}

	@Override
	public UserEntity getSpecificUser(String userOnlineStore, String userEmail) {
		Optional<UserEntity> user = this.advancedUserDao.readById(userOnlineStore + "#" + userEmail);
		if(user.isPresent())
			return user.get();
		throw new UserNotFoundException("User Doesn't Exist");
	}
	
	@Override
	@UserRoleValidation
	public String checkUserRole(String onlineStore, String userEmail, String role) {
		return role;
	}
	
	private boolean validateEmail(String userEmail) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(userEmail);
		
		if (!matcher.matches() ) {
			throw new RuntimeException("Email is not valid!");
		}
		return true;
	}
	
	private boolean validate(UserEntity userEntity) {
		return userEntity != null
				&& userEntity.getUserName() != null && !userEntity.getUserName().trim().isEmpty()
				&& userEntity.getUserOnlineStore() != null && !userEntity.getUserOnlineStore().trim().isEmpty()
				&& userEntity.getRole() != null
				&& validateEmail(userEntity.getUserEmail());
	}

}
