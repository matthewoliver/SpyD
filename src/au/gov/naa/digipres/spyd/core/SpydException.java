package au.gov.naa.digipres.spyd.core;

public class SpydException extends Exception {

	public SpydException(String message) {
		super(message);
	}

	public SpydException(Exception ex) {
		super(ex);
	}

	public SpydException(String message, Exception ex) {
		super(message, ex);
	}

}
