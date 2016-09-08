import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.InputStreamReader; // Leer
import java.io.BufferedReader; // Leer
import java.util.Vector;


/**
 * Se encarga de administrar las conexiones
 */
public class ServerChat {
	static int n = 0; // numero de clientes
	// Vector que contiene los mensajes de clientes
	public static Vector<Mensaje> mensajes = null;

	/**
	 * Constructor
	 **/
	public ServerChat() {
		ServerChat.mensajes = new Vector<Mensaje>();
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

class Mensaje {
	public int id_cliente;
	public String msg;
	public String hora;

	public Mensaje(int id_cliente, String msg) {
		this.hora = "10:30"; // generar dinamicamente.
		this.id_cliente = id_cliente;
		this.msg = msg;
	}
	public String toString() {
		return "[Cliente" 
			+ this.id_cliente+" - " + this.hora+"]"
			+ " : " + this.msg;
	}
}








/**
 * Se encarga de atender a un Cliente
 */
class ClienteServer implements Runnable {
		int id;
		Socket socket;
		// Lleva el control de cuantos mensajes se han enviado al cliente
		int msg_enviados = 0;
		// Elemento para escribir en el socket
		PrintWriter out;

		public ClienteServer(Socket s, int i) {
			this.socket = s;
			this.id = i;
			try {
				// creamos un socket
				// Le enviamos al cliente
				// su numero(ID).
				this.out = new PrintWriter(
					socket.getOutputStream(), true
				);
				this.out.println(this.id);
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
					// enviar los mensajes al cliente
					while(
						msg_enviados<ServerChat.mensajes.size()
					) {
						this.out.println(
						ServerChat.mensajes.get(msg_enviados++)
						);
					}
					// enviamos un mensaje vacio.
					this.out.println("");
					//Recibimos datos del cliente(1 msg)
					String msg = buffer.readLine();
					if (msg.equals("exit")) {
						System.out.println(
							"El cliente "+this.id +" avandono el chat"
						);
						break;
					}
					if(msg.length()<1) {
						continue;
					}
					//creamos un objeto de Mensaje y lo agreamos
					// a la lista de mensajes del servidor
					Mensaje mensaje = new Mensaje(this.id, msg);
					System.out.println(mensaje);
					ServerChat.mensajes.add(mensaje);
				} catch(Exception e) {
					return;
				}
			}
		}
}
