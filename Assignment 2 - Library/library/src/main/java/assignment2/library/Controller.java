package assignment2.library;

public class Controller {

	static SynchronizedLibrary syncronizedGameServer;

	static NoJDKLibrary noJDKGameServer;

	public static void main(String args[]) {
		//Main.main(args);
		syncronizedGameServer = new SynchronizedLibrary();
	}
}
