import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NumGuesserMain {
	
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
		srvr.close();
	}

	private static void doClient(int port, String host) throws UnknownHostException, IOException {
		Socket sock = new Socket(host, port);
		handleSocket(sock);
		sock.close();
	}

	private static void handleSocket(Socket sock) throws IOException {
		Scanner localUserInput = new Scanner(System.in);
		// Figure out who is the guesser and who is the picker
		// We'll accomplish this by having both parties send a random number over. Whoevers is highest will be declared
		// the picker
		
		double myRandomNumber = Math.random(); // random double between 0.0 and 1.0
		
		// To send this over, we need to use an abstraction called a DataOutputStream
		DataOutputStream outputToRemoteServer = new DataOutputStream(sock.getOutputStream());
		// now write our number
		outputToRemoteServer.writeDouble(myRandomNumber);
		System.out.println("Our roll is " + myRandomNumber);
		// To read their number we need the inverse of a DataOutputStream: a DataInputStream
		DataInputStream remoteServerInput = new DataInputStream(sock.getInputStream());
		
		// Since both processes write the number, it's safe to assume there is a number available for us to read
		double theirRandomNumber = remoteServerInput.readDouble();
		System.out.println("Their roll is " + theirRandomNumber);
		
		// Whoever is highest gets to be the picker
		boolean isPicker = myRandomNumber > theirRandomNumber;
		if(isPicker) {
			doPicker(localUserInput, outputToRemoteServer, remoteServerInput);
		} else {
			doGuesser(localUserInput, outputToRemoteServer, remoteServerInput);
		}
	}

	private static void doGuesser(Scanner localUserInput, DataOutputStream outputToRemoteServer, DataInputStream remoteServerInput) throws IOException {
		System.out.println("You are the guesser. Please wait while your opponent picks a number.");
		// We are the guesser. First read the number sent over by them
		int numberToGuess = remoteServerInput.readInt();
		
		System.out.println("Guess the number.");
		int guess = localUserInput.nextInt();
		if(guess == numberToGuess) {
			System.out.println("Wow, you got it on the first try... hacker.");
			outputToRemoteServer.writeInt(1);
		} else {
			// more normal case. We took a guess and got it wrong
			int numGuesses = 1; // keep track of how many tries we made
			while(guess != numberToGuess) {
				if(guess > numberToGuess) {
					System.out.println("Too high. Guess again");
				} else {
					System.out.println("Too low. Guess again.");
				}
				guess = localUserInput.nextInt();
				numGuesses++;
			}
			System.out.println("You guessed correctly after " + numGuesses + " tries.");
			outputToRemoteServer.writeInt(numGuesses);
		}
	}

	private static void doPicker(Scanner localUserInput, DataOutputStream outputToRemoteServer, DataInputStream remoteServerInput) throws IOException {
		System.out.println("You are the picker. Choose a number between 1 and 100 (inclusive)");
		int pickedNumber = localUserInput.nextInt();
		outputToRemoteServer.writeInt(pickedNumber);
		
		// Now read the number of guesses it took them to finish
		int numGuesses = remoteServerInput.readInt();
		System.out.println("It took your opponent " + numGuesses + " tries to guess the number");
	}
}
