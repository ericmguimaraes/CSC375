
public class Controller {

	static SynchronizedGameServer syncronizedGameServer;

	static NoJDKGameServer noJDKGameServer;
	
	public static void main(String args[]){
		syncronizedGameServer = new SynchronizedGameServer();
		noJDKGameServer = new NoJDKGameServer();
	}
}
