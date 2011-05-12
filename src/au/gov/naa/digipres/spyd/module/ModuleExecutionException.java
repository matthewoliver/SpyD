package au.gov.naa.digipres.spyd.module;

public class ModuleExecutionException extends Exception {

	public ModuleExecutionException(String message) {
		super(message);
	}

	public ModuleExecutionException(Exception ex) {
		super(ex);
	}

	public ModuleExecutionException(String message, Exception ex) {
		super(message, ex);
	}

}
