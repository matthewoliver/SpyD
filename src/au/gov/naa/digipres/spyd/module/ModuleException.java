package au.gov.naa.digipres.spyd.module;

public class ModuleException extends Exception {

	public ModuleException(String message) {
		super(message);
	}

	public ModuleException(Exception ex) {
		super(ex);
	}

	public ModuleException(String message, Exception ex) {
		super(message, ex);
	}

}
