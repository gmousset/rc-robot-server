/**
 * 
 */
package com.github.gmousset.rcrobotserver.uart;

import jssc.SerialPort;
import jssc.SerialPortException;


/**
 * @author gwendalmousset
 *
 */
public class UARTConnection {
	
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
					SerialPort.BAUDRATE_9600, 
					SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
		} catch (SerialPortException e) {
			throw new UARTConnectionException(e);
		}
	}
	
	public void sendCommand(final UARTCommand pCommand) throws UARTConnectionException {
		try {
			this.serialPort.writeString(pCommand.toUART() + "\n");
		} catch (SerialPortException e) {
			throw new UARTConnectionException(e);
		}
	}
}
