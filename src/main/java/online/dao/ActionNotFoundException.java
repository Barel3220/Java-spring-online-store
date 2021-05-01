package online.dao;

public class ActionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ActionNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
	public ActionNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ActionNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ActionNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
