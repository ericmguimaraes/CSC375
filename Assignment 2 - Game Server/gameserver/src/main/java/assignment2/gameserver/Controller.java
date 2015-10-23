package assignment2.gameserver;

import org.openjdk.jmh.Main;

public class Controller {

	static SynchronizedGameServer syncronizedGameServer;

	static NoJDKGameServer noJDKGameServer;

	public static void main(String args[]) {
		syncronizedGameServer = new SynchronizedGameServer();
		noJDKGameServer = new NoJDKGameServer();
		Main.main(args);
	}
}
