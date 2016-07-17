/**
 * 
 */
package com.github.gmousset.rcrobotserver.uart;

/**
 * @author gwendalmousset
 *
 */
public class EnginePowerCommand implements UARTCommand {
	private final String cmdFormat = "EnPow:%c:%d";
	private final Integer maxValue = 255;
	private final Location location;
	private final Integer power;
	
	public EnginePowerCommand(final Location pLocation, final Float pPower) {
		this.location = pLocation;
		this.power = Math.min(maxValue, Math.round(255 * pPower));
	}
	
	@Override
	public String toUART() {
		return String.format(cmdFormat, this.location.getValue(), this.power);
	}

	public enum Location {
		FRONT('f'),
		REAR('r'),
		LEFT('l'),
		RIGHT('r');
		
		private char value;
		private Location(final char pValue) {
			this.value = pValue;
		}
		public char getValue() {
			return this.value;
		}
	}
}
