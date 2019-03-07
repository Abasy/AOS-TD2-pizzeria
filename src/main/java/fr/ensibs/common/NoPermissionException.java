package fr.ensibs.common;

public class NoPermissionException extends Exception {

	/**
	 * Error Serial ID
	 */
	private static final long serialVersionUID = 473086282439841342L;

	public NoPermissionException() {
		// TODO Auto-generated constructor stub
	}

	public NoPermissionException (Person.PERMISSION role) {
		super("Only " + role + " can access this service");
	}
	
	public NoPermissionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoPermissionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoPermissionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoPermissionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

