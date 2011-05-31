package au.gov.naa.digipres.spyd.communication;

import au.gov.naa.digipres.spyd.core.SpydException;

public class EmailException extends SpydException {
	public EmailException(Exception ex) {
		super(ex);
	}

	public EmailException(String message) {
		super(message);
	}

	public EmailException(String message, Exception ex) {
		super(message, ex);
	}
}
