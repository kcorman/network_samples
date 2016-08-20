import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private List<Square> squares = Collections.synchronizedList(new ArrayList<>());
	
	public GamePanel() {
		System.out.println("Creating GamePanel()");
		this.addMouseListener(new MyMouseListener());
		System.out.println("Mouse listeners: " + this.getMouseListeners());
	}
	public void addSquare(Square square) {
		squares.add(square);
	}

	public void removeSquare(Square square) {
		squares.remove(square);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		List<Square> squares = new ArrayList<>(this.squares);
		for(Square sq : squares) { 
			sq.paintThisThing(g);
		}
	}
	
	private class MyMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			int clickX = e.getX();
			int clickY = e.getY();
			System.out.println("clicked! x=" + clickX + ", y=" + clickY);

			List<Square> squaresCopy = new ArrayList<>(squares);
			for(Square square : squaresCopy) {
				// check if the X is within the square
				if(clickX >= square.getX() && clickX <= square.getX() + square.getWidth()) {
					// if we're here, then the click is within the bounds in the X dimension
					if(clickY >= square.getY() && clickY <= square.getY() + square.getHeight()) {
						// they clicked inside the square
						removeSquare(square);
					}
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Mouse pressed!");
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
