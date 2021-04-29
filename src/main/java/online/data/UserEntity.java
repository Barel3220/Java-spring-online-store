package online.data;

public class UserEntity implements OnlineStoreEntity<String> {
	private String key;
	private String userOnlineStore;
	private String userEmail;
	private String userName;
	private UserRole role;
	
	public UserEntity() {
		// creating an empty constructor for spring beans
	}

	public UserEntity(String userOnlineStore, String userEmail, String userName, UserRole role) {
		this.userOnlineStore = userOnlineStore;
		this.userEmail = userEmail;
		this.userName = userName;
		this.role = role;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	public String getUserOnlineStore() {
		return userOnlineStore;
	}

	public void setUserOnlineStore(String userOnlineStore) {
		this.userOnlineStore = userOnlineStore;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
