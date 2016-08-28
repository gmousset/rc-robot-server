/**
 * 
 */
package com.github.gmousset.rcrobotserver.uart;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.gmousset.rcrobotserver.system.Robot;
import com.github.gmousset.rcrobotserver.uart.EnginePowerCommand.Location;


/**
 * @author gwendalmousset
 *
 */
public class UARTManager {
	
	private static final Logger LOGGER = LogManager.getLogger(UARTManager.class);
	private final Robot robot;
	private UARTConnection connection;
	
	public UARTManager(final Robot pRobot) {
		this.robot = pRobot;
	}
	
	public void setup() throws UARTConnectionException {
		this.setupUARTConnection();
		this.setupEngineObserver();
	}
	
	private void setupUARTConnection() throws UARTConnectionException {
		this.connection = new UARTConnection("/dev/ttyAMA0");
		try {
			this.connection.connect();
		} catch (UARTConnectionException e) {
			LOGGER.error(e.getMessage());
			throw new UARTConnectionException(e.getCause());
		}
	}
	
	private void setupEngineObserver() {
		this.robot.getLeftPower().distinctUntilChanged().debounce(500, TimeUnit.MILLISECONDS).subscribe(power -> {
			final UARTCommand command = new EnginePowerCommand(Location.LEFT, power);
			this.sendCommand(command);
		});
		
		this.robot.getRightPower().distinctUntilChanged().debounce(500, TimeUnit.MILLISECONDS).subscribe(power -> {
			final UARTCommand command = new EnginePowerCommand(Location.RIGHT, power);
			this.sendCommand(command);
		});
	}
	
	private void sendCommand(final UARTCommand pCommand) {
		LOGGER.debug("[UART-Tx] " + pCommand.toUART());
		
		try {
			this.connection.sendCommand(pCommand);
		} catch (UARTConnectionException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
