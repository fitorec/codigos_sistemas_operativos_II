import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.InputStreamReader; // Leer
import java.io.BufferedReader; // Leer

public class ServerChat {
	static int n = 0; // numero de clientes
	public ServerChat() {
		try {
			ServerSocket serv = new ServerSocket(10);
			System.out.println("Corriendo servidor");
			while(true) {
				ClienteServer cliente = new ClienteServer(
					serv.accept(), ++ServerChat.n
				);
				(new Thread(cliente)).start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Crea el servidor
	public static void main(String... a){
		new ServerChat();
	}
}


class ClienteServer implements Runnable {
		int id;
		Socket socket;
		public ClienteServer(Socket s, int i) {
			this.socket = s;
			this.id = i;
			try {
				// creamos un socket
				// Le enviamos al cliente
				// su numero(ID).
				PrintWriter out = new PrintWriter(
					socket.getOutputStream(), true
				);
				out.println(this.id);
				// Mostramos que se agregao un nuevo cliente
				System.out.println("Nuevo cliente id: "+id);
			} catch(Exception e) {
				System.out.println("No se puede escribir");
			}
		}

		/** Recibe mensajes del cliente
		 * mientras que el mensaje sea diferente de exit
		 **/
		@Override
		public void run() {
			BufferedReader buffer = null;
			try {
				// creando un input para leer del socket
				InputStreamReader in = new InputStreamReader(
					this.socket.getInputStream()
				);
				// obteniendo información por un buffer
				buffer = new BufferedReader(in);
			} catch(Exception e) {
				System.out.println("error al leer");
				return;
			}
			while(true) {
				try {
					String msg = buffer.readLine();
					if (msg.equals("exit")) {
						System.out.println(
							"El cliente "+this.id +" avandono el chat"
						);
						break;
					}
					System.out.println("Cliente "+ this.id +":"+msg);
				} catch(Exception e) {
					return;
				}
			}
		}
}
