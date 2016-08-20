import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello, Danny! I see you and you look nice today.");
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("In main: creating new GamePanel");
		GamePanel panel = new GamePanel();
		
		Square sq1 = new Square(100, 100, 300, 300);
		panel.addSquare(sq1);

		Square sq2 = new Square(100, 500, 300, 300);
		panel.addSquare(sq2);
		
		frame.add(panel);
		Random r = new Random();
		while(true) {
			sq1.update();
			sq2.update();
			Thread.sleep(100);
			frame.repaint();
			
		}
		
		/*
		frame.setLayout(new BorderLayout());

		JPanel pan1 = new JPanel();
		pan1.add(new JLabel("Poop"));
		pan1.setBackground(Color.PINK);
		frame.add(pan1, BorderLayout.WEST);
		JPanel pan2 = new JPanel();
		pan2.setBackground(Color.BLACK);
		frame.add(pan2, BorderLayout.EAST);*/
		
	}

}
