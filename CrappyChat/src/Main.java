import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] arr) throws UnknownHostException, IOException {
		boolean isServer = Boolean.parseBoolean(arr[0]);
		int port = Integer.parseInt(arr[1]);
		
		if(isServer) {
			doServer(port);
		} else {
			String host = arr[2];
			doClient(port, host);
		}
	}

	private static void doServer(int port) throws IOException {
		ServerSocket srvr = new ServerSocket(port);
		Socket sock = srvr.accept();
		handleSocket(sock);
	}

	private static void doClient(int port, String host) throws UnknownHostException, IOException {
		Socket sock = new Socket(host, port);
		handleSocket(sock);
	}

	private static void handleSocket(Socket sock) throws IOException {
		Scanner sc = new Scanner(System.in);
		// create a new thread to print all output from the socket
		Executors.newSingleThreadExecutor().execute(() -> {
			InputStream is;
			try {
				is = sock.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				while(true) {
					String line = br.readLine();
					if(null == line) {
						System.out.println("End of session.");
						return;
					}
					System.out.println("Other: " + line);
				}
			} catch (IOException e) {
				return;
			}
			
		});
		PrintWriter out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		while(true) {
			String line = sc.nextLine();
			if(line.equals("END")) {
				out.close();
				break;
			}
			out.write(line + "\n");
			out.flush();
		}
	}
}
