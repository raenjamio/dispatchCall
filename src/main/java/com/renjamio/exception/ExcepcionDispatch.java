package com.renjamio.exception;

public class ExcepcionDispatch extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2528099598490995547L;
    
	public ExcepcionDispatch(String message, Throwable cause) {
        super(message, cause);
    }

	public ExcepcionDispatch(String message) {
		  super(message);
	}
}
