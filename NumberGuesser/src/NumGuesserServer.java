import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NumGuesserServer {
	
	public static void main(String[] arr) throws UnknownHostException, IOException {
		int port = 43435;
		ServerSocket sock = new ServerSocket(port);
		// connect to first player
		Socket p1 = sock.accept();
		Socket p2 = sock.accept();
		
		doGame(p1, p2);
	}
	
	private static void doGame(Socket p1, Socket p2) throws IOException {
		Socket picker;
		Socket guesser;
		// Figure out who is picker
		if(Math.random() > .5) {
			// p1 is picker
			picker = p1;
			guesser = p2;
		} else {
			// s2 is picker
			picker = p2;
			guesser = p1;
		}

		DataOutputStream pickerOutput = new DataOutputStream(picker.getOutputStream());
		DataInputStream pickerInput = new DataInputStream(picker.getInputStream());
		DataOutputStream guesserOutput = new DataOutputStream(guesser.getOutputStream());
		DataInputStream guesserInput = new DataInputStream(guesser.getInputStream());
		
		pickerOutput.writeBoolean(true);
		guesserOutput.writeBoolean(false);
		
		// wait for pick from picker
		int pickedNum = pickerInput.readInt();
		
		// tell guesser to start guessing
		int numGuesses = 1;
		
		// To let the guesser know to start, write a boolean. We could literally write anything
		guesserOutput.writeBoolean(false);
		int guessedNum = guesserInput.readInt();
		while(guessedNum != pickedNum) {
			// We want to write -1 if their guess was too low, 0 if it was correct, and +1 if it was too high
			int val = guessedNum - pickedNum;
			int sign = val / Math.abs(val);
			guesserOutput.writeInt(sign);
			guessedNum = guesserInput.readInt();
			numGuesses++;
		}
		// Guesser guessed successfully! 
		guesserOutput.writeInt(0);
		// write back number of guesses to opponent
		pickerOutput.writeInt(numGuesses);
		
	}
}
