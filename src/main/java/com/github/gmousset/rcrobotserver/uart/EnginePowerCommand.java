/**
 * 
 */
package com.github.gmousset.rcrobotserver.uart;

/**
 * @author gwendalmousset
 *
 */
public class EnginePowerCommand implements UARTCommand {
	private final String cmdFormat = "EPW:%c:%c:%03d";	// location:direction:power
	private final Location location;
	private final Direction direction;
	private final Integer power;
	
	public EnginePowerCommand(final Location pLocation, final Integer pPower) {
		this.location = pLocation;
		if (pPower < 0) {
			this.power = pPower * -1;
			this.direction = Direction.BACKWARD;
		} else {
			this.power = pPower;
			this.direction = Direction.FORWARD;
		}
	}
	
	public String toUARTString() {
		return String.format(cmdFormat, this.location.value(), this.direction.value, this.power);
	}
	
	@Override
	public byte[] toUART() {
		/*
		 * Command:		1 byte
		 * Location:	1 byte
		 * Direction:	1 byte
		 * Power:		1 byte
		 */
		final byte[] command = new byte[6];
		command[0] = UARTCommand.UARTBytesCommand.ENGINE_POWER.value();
		command[1] = this.location.value; 
		command[2] = this.direction.value;
		command[3] = this.power.byteValue();
		
		return command;
	}
	

	public enum Location {
		LEFT((byte) 0x00),
		RIGHT((byte) 0x01);
		
		private byte value;
		private Location(final byte pValue) {
			this.value = pValue;
		}
		public byte value() {
			return this.value;
		}
	}
	
	public enum Direction {
		FORWARD((byte) 0x00),
		BACKWARD((byte) 0x01);
		
		private byte value;
		private Direction(final byte pValue) {
			this.value = pValue;
		}
		public byte value() {
			return this.value;
		}
	}
}
