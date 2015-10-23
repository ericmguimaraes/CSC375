package assignment2.library;

public class Controller {

	static SynchronizedLibrary syncronizedGameServer;

	static LockFreeLibrary lockFreeLibrary;

	public static void main(String args[]) {
		// Main.main(args);
		syncronizedGameServer = new SynchronizedLibrary();
		lockFreeLibrary = new LockFreeLibrary();
	}
}
