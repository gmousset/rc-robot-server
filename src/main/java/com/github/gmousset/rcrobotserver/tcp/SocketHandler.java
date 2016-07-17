/**
 * 
 */
package com.github.gmousset.rcrobotserver.tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.gmousset.rcrobotserver.system.Robot;

/**
 * @author gwendalmousset
 *
 */
public class SocketHandler implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(SocketHandler.class);
	
	private final Robot robot;
	private final Socket socket;
	private BufferedInputStream inputStream;
	//private BufferedOutputStream outputStream;
	
	public SocketHandler(final Robot pRobot, final Socket pSocket) {
		this.robot = pRobot;
		this.socket = pSocket;
	}
	
	public void run() {
		
		try (final InputStream in = this.socket.getInputStream();
				final OutputStream out = this.socket.getOutputStream()) {
			
			LOGGER.info("connection with " + socket.getRemoteSocketAddress() + " opened");
			this.inputStream = new BufferedInputStream(this.socket.getInputStream());
			//this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
			
			while (!this.socket.isClosed()) {
				if (this.inputStream.available() > 0) {
					byte[] inBytes = new byte[this.inputStream.available()];
					this.inputStream.read(inBytes);
					final String mesg = new String(inBytes).trim();
					LOGGER.debug("[SRVR-Rx] " + mesg);
					
					if (mesg.equalsIgnoreCase(Command.BYE.getValue())) {
						this.socket.close();
					} else if (mesg.startsWith(Command.ENGINES_POWER.getValue())) {
						final String rawParameters = mesg.substring(Command.ENGINES_POWER.getValue().length() + 1);
						final String[] parameters = rawParameters.split(":");
						if (parameters.length == 2) {
							try {
								final Float powLeft = Float.valueOf(parameters[0]);
								final Float powRight = Float.valueOf(parameters[1]);
								this.robot.setEnginesPower(powLeft, powRight);
							} catch (final NumberFormatException nfe) {
								LOGGER.warn("ignore command: " + mesg);
							}
						}
					}
				}
			}
			LOGGER.debug("connection with " + this.socket.getRemoteSocketAddress() + " closed");
		} catch (final IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
