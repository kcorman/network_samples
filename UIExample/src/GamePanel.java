import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

	private List<Square> squares = new ArrayList<>();
	
	public void addSquare(Square square) {
		squares.add(square);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(Square sq : squares) { 
			sq.paintThisThing(g);
		}
	}
}
