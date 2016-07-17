/**
 * 
 */
package com.github.gmousset.rcrobotserver.tcp;

/**
 * @author gwendalmousset
 *
 */
public enum Command {
	ENGINES_POWER("engines_pow"), // (left, right), 0.0 < x < 1.0
	BYE("bye");
	
	private String value;
	private Command(final String pValue) {
		this.value = pValue;
	}
	
	public String getValue() {
		return this.value;
	}
}
