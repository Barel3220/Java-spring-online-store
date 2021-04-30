package online.dao;

public class UserRoleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserRoleException() {
	}

	public UserRoleException(String message) {
		super(message);
	}

	public UserRoleException(Throwable cause) {
		super(cause);
	}

	public UserRoleException(String message, Throwable cause) {
		super(message, cause);
	}
}
