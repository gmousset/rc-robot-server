/**
 * 
 */
package com.github.gmousset.rcrobotserver.tcp;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.gmousset.rcrobotserver.system.Robot;


/**
 * @author gwendalmousset
 *
 */
public class Server implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(Server.class);
			
	private final Robot robot;
	private final int port;
	
	public Server(final Robot pRobot, final int pPort) {
		this.robot = pRobot;
		this.port = pPort;
	}

	public void run() {
		try (final ServerSocket serverSocket = new ServerSocket(this.port)) {
			LOGGER.info("Server listing port " + this.port);
			while (true) {
				final Socket socket = serverSocket.accept();
				LOGGER.info("server accepted new connection");
				final SocketHandler handler = new SocketHandler(this.robot, socket);
				final Thread thHandler = new Thread(handler);
				thHandler.start();
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
