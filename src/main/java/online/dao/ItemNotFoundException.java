package online.dao;

public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ItemNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ItemNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ItemNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
