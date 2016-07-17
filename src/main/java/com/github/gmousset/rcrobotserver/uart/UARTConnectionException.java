package com.github.gmousset.rcrobotserver.uart;

public class UARTConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593098329871430665L;
	
	public UARTConnectionException(final Throwable pT) {
		super(pT);
	}
}
