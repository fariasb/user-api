package cl.ey.userapi.exception;

public class UserEmailAlreadyRegisterException extends Exception{

	private static final long serialVersionUID = 202553311081272028L;

	public UserEmailAlreadyRegisterException(String message) {
		super(message);

	}
}
