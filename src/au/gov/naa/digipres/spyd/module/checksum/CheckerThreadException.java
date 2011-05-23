package au.gov.naa.digipres.spyd.module.checksum;

import au.gov.naa.digipres.spyd.module.ModuleException;

public class CheckerThreadException extends ModuleException {

	public CheckerThreadException(Exception ex) {
		super(ex);
	}

	public CheckerThreadException(String message) {
		super(message);
	}

	public CheckerThreadException(String message, Exception ex) {
		super(message, ex);
	}
}
