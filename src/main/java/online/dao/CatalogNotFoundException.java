package online.dao;

public class CatalogNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CatalogNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
	public CatalogNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CatalogNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CatalogNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
