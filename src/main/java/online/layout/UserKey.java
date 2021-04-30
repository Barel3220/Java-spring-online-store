package online.layout;

public class UserKey {
	private String onlineStore;
	private String userEmail;
	
	public UserKey() {
		// TODO Auto-generated constructor stub
	}

	public UserKey(String onlineStore, String userEmail) {
		this.onlineStore = onlineStore;
		this.userEmail = userEmail;
	}

	public String getOnlineStore() {
		return onlineStore;
	}

	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}	
}
