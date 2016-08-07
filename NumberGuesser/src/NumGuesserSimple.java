import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This NumGuesser class combines the client and server into one program. You use program arguments to specify whether you're listening for connections
 * (aka server) or connecting (client). Note that once the connection is made, this is not really a client-server program but more of a peer-to-peer type
 *
 * See NumGuesserServer and NumGuesserClient for the true client-server version of this program
 */
public class NumGuesserSimple {
	
	/**
	 * The entry point for the program. Arguments look like the following
	 * For client: false 55555 localhost
	 * For server: true 55555
	 * 
	 * If we run this in client mode from the command line, it would look like
	 * java NumGuesserSimple false localhost 55555
	 * @param arr the array of arguments
	 */
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

	/**
	 * Handles the "server" setup
	 * @param port the port the server listens on
	 * @throws IOException
	 */
	private static void doServer(int port) throws IOException {
		ServerSocket srvr = new ServerSocket(port);
		Socket sock = srvr.accept();
		handleSocket(sock);
		srvr.close();
	}

	/**
	 * Handles the client setup
	 * @param port the port of the remote server
	 * @param host the host of the remote server, either ip address or hostname
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private static void doClient(int port, String host) throws UnknownHostException, IOException {
		Socket sock = new Socket(host, port);
		handleSocket(sock);
		sock.close();
	}

	/**
	 * The actual logic of the game. Note that both players will call into this method, but they get different behavior based on the random roll below
	 * @param sock the socket to use for communicating to the other player
	 * @throws IOException
	 */
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

	/**
	 * Handles the guesser portion of the game.
	 * @param localUserInput a scanner for getting data from the local user (i.e. the human who is sitting in front of the monitor)
	 * @param outputToRemoteServer a stream for sending data to the remote server
	 * @param remoteServerInput a stream for reading data from the remote server
	 * @throws IOException
	 */
	private static void doGuesser(Scanner localUserInput, DataOutputStream outputToRemoteServer, DataInputStream remoteServerInput) throws IOException {
		System.out.println("You are the guesser. Please wait while your opponent picks a number.");
		// We are the guesser. First read the number sent over by them
		int numberToGuess = remoteServerInput.readInt();
		
		// We get the initial guess outside of the loop, and each subsequent guess inside the loop. This is because
		// we want a different message for "retry" guesses and the initial guess
		System.out.println("Guess the number.");
		int guess = localUserInput.nextInt();
		if(guess == numberToGuess) {
			// rare case, the first guess is correct
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

	/**
	 * Handles the picker portion of the game.
	 * @param localUserInput a scanner for getting data from the local user (i.e. the human who is sitting in front of the monitor)
	 * @param outputToRemoteServer a stream for sending data to the remote server
	 * @param remoteServerInput a stream for reading data from the remote server
	 * @throws IOException
	 */
	private static void doPicker(Scanner localUserInput, DataOutputStream outputToRemoteServer, DataInputStream remoteServerInput) throws IOException {
		System.out.println("You are the picker. Choose a number between 1 and 100 (inclusive)");
		int pickedNumber = localUserInput.nextInt();
		outputToRemoteServer.writeInt(pickedNumber);
		
		// Now read the number of guesses it took them to finish
		int numGuesses = remoteServerInput.readInt();
		System.out.println("It took your opponent " + numGuesses + " tries to guess the number");
	}
}
