package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JPanel;

public class UIPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final PlayerControl playerCtrl;
	
	private final GameStateSource gameStateSrc;
	
	public UIPane(int width, int height, PlayerControl pc, GameStateSource gss) {
		if(width < 200 || height < 200)
			throw new IllegalArgumentException("Width and height must be greater than 200!");
		this.playerCtrl = Objects.requireNonNull(pc);
		this.gameStateSrc = Objects.requireNonNull(gss);

		super.setSize(width, height);
	}
	
	@Override
	public void paint(Graphics g) {
		GameState state = gameStateSrc.getState();
		if(state.isOver()) {
			if(state.getWinner() == playerCtrl.getId()) {
				// you win
			} else {
				// you lose
			}
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			g.drawRect(2, 2, getWidth() - 10, getHeight() - 10);
			// paint the game
		    for(Shape s : state.getShapes()) {
		    	s.draw(this, g);
		    }
		}
	}
}
