/**
 * 
 */
package com.github.gmousset.rcrobotserver.uart;

/**
 * @author gwendalmousset
 *
 */
public interface UARTCommand {
	byte[] toUART();
	
	public enum UARTBytesCommand {
		REBOOT((byte) 0x00),
		ENGINE_POWER((byte) 0x01);
		
		private final byte value;
		
		/**
		 * 
		 */
		private UARTBytesCommand(final byte pByte) {
			this.value = pByte;
		}
		
		public byte value() {
			return this.value;
		}
	}
}

