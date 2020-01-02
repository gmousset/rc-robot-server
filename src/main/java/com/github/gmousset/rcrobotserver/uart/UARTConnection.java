/**
 * 
 */
package com.github.gmousset.rcrobotserver.uart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jssc.SerialPort;
import jssc.SerialPortException;


/**
 * @author gwendalmousset
 *
 */
public class UARTConnection {
	
	private static final Logger LOGGER = LogManager.getLogger(UARTConnection.class);
	private final String port;
	private SerialPort serialPort;
	
	public UARTConnection(final String pPort) {
		this.port = pPort;
	}
	
	public void connect() throws UARTConnectionException  {
		this.serialPort = new SerialPort(this.port);
		try {
			this.serialPort.openPort();
			this.serialPort.setParams(
					SerialPort.BAUDRATE_115200, 
					SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
		} catch (SerialPortException e) {
			throw new UARTConnectionException(e);
		}
	}
	
	public void sendCommand(final UARTCommand pCommand) throws UARTConnectionException {
		byte[] bytesCmd = pCommand.toUART();
		LOGGER.debug("[UART-Tx] " + bytesCmd[0] + "  " + bytesCmd[1] + "  " + bytesCmd[2] + "  " + bytesCmd[3]);
		try {
			this.serialPort.writeBytes(pCommand.toUART());
		} catch (SerialPortException e) {
			throw new UARTConnectionException(e);
		}
	}
}
