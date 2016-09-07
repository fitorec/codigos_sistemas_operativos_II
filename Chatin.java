import java.net.Socket;
import java.io.InputStreamReader; // Leer
import java.io.BufferedReader; // Leer
import java.io.PrintWriter; // escribbir
import java.util.Scanner;

public class Chatin {
	int id;
	public Chatin() {
		Scanner sc = new Scanner(System.in);
		try {
			//creando el socket
			Socket s = new Socket("127.0.0.1", 10);
			// creando un input para leer del socket
			InputStreamReader in = new InputStreamReader(
				s.getInputStream()
			);
			// obteniendo información por un buffer
			BufferedReader b = new BufferedReader(in);
			// leyendo la 1ra linea que es el ID
			this.id = Integer.parseInt(b.readLine());
			System.out.println("Soy el cliente:"+ this.id);
			// crea la salida del socket
			PrintWriter out = new PrintWriter(
				s.getOutputStream(), true
			);
			while(true) {
				String msg = sc.nextLine();
				out.println(msg);
				if(msg.equals("exit")) {
					break;
				}
			}
		}  catch(Exception e) {
			System.out.println("Error en la conexion");
		}
	}

	public static void main(String a[]) {
		new Chatin();
	}
}
