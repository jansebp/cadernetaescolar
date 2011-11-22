package exceptions;

public class CadernetaException extends RuntimeException {
	public CadernetaException() {
		super("Algo deu errado.");
	}
	
	public CadernetaException(String description) {
		super(description);
	}

}
