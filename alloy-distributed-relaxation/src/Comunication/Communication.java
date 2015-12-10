package Comunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import Configuration.Configuration;
import Domain.Alloy;
import Domain.ForkHeater;

public class Communication {

	ServerSocketChannel serverSocketChannel;

	SocketChannel socketChannel;

	boolean serverMode = false;

	public Communication(boolean serverMode) {
		this.serverMode = serverMode;
	}

	public void send(ForkHeater fork) {
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true);
			int port = serverMode ? Configuration.send_port_server : Configuration.send_port_client;
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			socketChannel = serverSocketChannel.accept();
			ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
			oos.writeObject(fork);
			oos.close();
			socketChannel.close();
			serverSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ForkHeater sendAndWait(ForkHeater fork) {
		send(fork);
		return receiveFork();
	}

	public ForkHeater receiveFork() {
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
			if (socketChannel.connect(
					new InetSocketAddress(serverMode ? Configuration.hostname_server : Configuration.hostname_client,
							serverMode ? Configuration.receive_port_server : Configuration.receive_port_client))) {
				ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());

				ForkHeater fork = (ForkHeater) ois.readObject();
				ois.close();
				socketChannel.close();
				return fork;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void integrate(Alloy alloy, ForkHeater fork) {
		int x, y;
		for (int n = fork.start; n < fork.end; n++) {
			x = n % alloy.w;
			y = n / alloy.w;
			alloy.getDestination().atom[x][y] = fork.alloy.getDestination().atom[x][y];
		}
	}

}
