import java.awt.Color;
import java.awt.Graphics;

public class Square {
	private int x;
	private int y;
	private int width;
	private int height;
	static int numCreated = 0;

	public Square(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		numCreated++;
	}

	public void move(int dx, int dy) {
		x += dx;
		y += dy; // y = y + dy
	}

	public void update() {
		move(2, 2);
	}

	public void paintThisThing(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}
}
