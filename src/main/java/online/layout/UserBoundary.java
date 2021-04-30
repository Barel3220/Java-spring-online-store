package online.layout;

import online.data.UserEntity;
import online.data.UserRole;

public class UserBoundary {
	private UserKey key;
	private String userName;
	private String role;
	
	public UserBoundary() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBoundary(UserEntity user) {
		key = new UserKey(user.getUserOnlineStore(), user.getUserEmail());
		userName = user.getUserName();
		role = user.getRole().name();
	}

	public UserKey getKey() {
		return key;
	}

	public void setKey(UserKey key) {
		this.key = key;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public UserEntity toEntity() {
		UserEntity entity = new UserEntity();
		
		if(this.getKey().getOnlineStore() == null || this.getKey().getUserEmail() == null)
			throw new RuntimeException("Must Enter Online Store and Email");
		
		String onlineStore = this.getKey().getOnlineStore();
		String userEmail = this.getKey().getUserEmail();
		
		if (onlineStore != null && userEmail != null)
			entity.setKey(onlineStore + "#" + userEmail);
		else
			entity.setKey(null);
		
		entity.setUserOnlineStore(onlineStore);
		entity.setUserEmail(userEmail);
		entity.setUserName(this.userName);
		entity.setRole(UserRole.valueOf(this.role));
		
		return entity;
	}
}
