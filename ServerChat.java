import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;

public class ServerChat {
	static int n = 0; // numero de cliente
	public ServerChat() {
		try {
			ServerSocket serv = new ServerSocket(10);
			System.out.println("Corriendo servidor");
			while(true) {
				// creamos un socket 
				// asociado al cliente
				Socket sCliente = serv.accept();
				// Le enviamos al cliente
				// su numero(ID).
				PrintWriter out = new PrintWriter(sCliente.getOutputStream(), true);
				out.println(++ServerChat.n);
				// Mostramos que se agregao un nuevo cliente
				System.out.println("Nuevo cliente id: "+ServerChat.n);
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
