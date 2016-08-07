package game;

import java.util.List;
import java.util.Objects;

public class GameState {
	private List<Shape> shapes;
	
	public GameState(List<Shape> shapes) {
		this.shapes = Objects.requireNonNull(shapes);
	}
	public List<Shape> getShapes() {
		return shapes;
	}
	
	public boolean isOver() {
		return false;
	}
	
	public long getWinner() {
		throw new UnsupportedOperationException("Game not over!");
	}
}
