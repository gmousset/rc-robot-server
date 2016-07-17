/**
 * 
 */
package com.github.gmousset.rcrobotserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.gmousset.rcrobotserver.system.Robot;
import com.github.gmousset.rcrobotserver.tcp.Server;
import com.github.gmousset.rcrobotserver.uart.UARTConnectionException;
import com.github.gmousset.rcrobotserver.uart.UARTManager;

/**
 * @author gwendalmousset
 *
 */
public class AppMain {

	private static final Logger LOGGER = LogManager.getLogger(AppMain.class);
	
	/**
	 * @param args
	 * @throws UARTConnectionException 
	 */
	public static void main(String[] args) throws UARTConnectionException {
		final Robot robot = new Robot();
		final UARTManager uartManager = new UARTManager(robot);
		uartManager.setup();
		final Server server = new Server(robot, 8765);
		final Thread thread = new Thread(server);
		thread.start();
		LOGGER.info("Server started");
	}
}
