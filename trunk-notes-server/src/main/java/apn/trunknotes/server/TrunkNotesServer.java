package apn.trunknotes.server;

import org.eclipse.jetty.server.Server;

/**
 * User: antonion
 * Date: 22/01/14 10:54
 */
public class TrunkNotesServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);

		server.start();
		server.join();
	}
}
