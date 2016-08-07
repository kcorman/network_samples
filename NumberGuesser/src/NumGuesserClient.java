import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The client portion of the client-server NumGuesser game. This handles being a client for either a Picker or a Guesser. The server determines which it is randomly
 * Note that you must run two instances of this before the server will start the game, as there is no single-player option
 *
 */
public class NumGuesserClient {
	private static final String HOST = "localhost";
	private static final int PORT = 55555;

	/**
	 * Entry point for client. Does not take any arguments. Instead, to change the port or host to connect to,
	 * change the values of the constants defined above and recompile. Keep in mind that the port must stay in sync with the server port
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket(HOST, PORT);
		DataOutputStream output = new DataOutputStream(s.getOutputStream());
		DataInputStream input = new DataInputStream(s.getInputStream());
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Waiting for other players...");
		// Wait for the game to start. When it's ready, the server will send a true if we're the picker,
		// and false otherwise
		boolean isPicker = input.readBoolean();
		if(isPicker) {
			// prompt user for a number
			System.out.println("You are the picker. Enter a number: ");
			int num = userInput.nextInt();
			output.writeInt(num);
			
			// Now we wait to see how many tries the guesser made
			int numTries = input.readInt();
			System.out.println("The guesser guessed your number after " + numTries + " tries.");
		}else {
			System.out.println("You are the guesser. Please stand by while the picker picks a mysterious number");
			// We're the guesser. The server will start off by writing another false, and then
			// keep writing -1 if our guess is too low, +1 if it's too high, and 0 if it's equal
			input.readBoolean();
			System.out.println("Guess a number");
			int guess = userInput.nextInt();
			
			// Write our first guess to the server
			output.writeInt(guess);
			
			// get the response
			int response = input.readInt();
			while(response != 0) {
				if(response > 0) {
					System.out.println("Your guess was too damn high!");
				} else {
					System.out.println("Too low. Guess again.");
				}
				guess = userInput.nextInt();
				output.writeInt(guess);
				response = input.readInt();
			}
			System.out.println("Good jorb! u did guud");
		}
	}

}
