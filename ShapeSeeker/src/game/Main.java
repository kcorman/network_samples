package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	public static void main(String[] arr) {
		JFrame fram = new JFrame("ShapeSeeker");
		fram.setSize(WIDTH, HEIGHT);
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fram.setVisible(true);
		UIPane ui = new UIPane(WIDTH, HEIGHT, new NoOpPc(), new StupidStateSource(fram));
		fram.add(ui);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {fram.repaint();}, 0, 30, TimeUnit.MILLISECONDS);
	}
	private static class StupidStateSource implements GameStateSource {
		GameState oldState;
		final JFrame bounds;
		public StupidStateSource(JFrame bounds) {
			this.bounds = bounds;
			List<Shape> shapeList = new ArrayList<>();
			int numShapes = new Random().nextInt(15);
			for(int i = 0;i<numShapes;i++) {
				shapeList.add(new DumbCircle((int)(WIDTH * Math.random()), (int)(HEIGHT * Math.random()),5, 5));
			}
			oldState = new GameState(shapeList);
		}
		@Override
		public GameState getState() {
			for(Shape s : oldState.getShapes()) {
				((DumbCircle) s ).update(bounds.getBounds());
			}
			return oldState;
		}
		
	}
	private static class NoOpPc implements PlayerControl {

		@Override
		public void mouseMoved(int x, int y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(int x, int y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public long getId() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	private static class DumbCircle implements Shape {
		int width = 50;
		int height = 50;
		public DumbCircle(int x, int y, int dx, int dy) {
			super();
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
		}

		int x, y, dx, dy;
		@Override
		public void draw(JComponent parent, Graphics g) {
			g.setColor(Color.RED);
			g.fillRect(x, y, width, height);
		}

		public void update(Rectangle bounds) {
			x += dx;
			y += dy;
			boolean boink = false;
			if(this.x + width >= bounds.getX() + bounds.getWidth()) {
				dx *= -1;
				x = (int) (bounds.getX() + bounds.getWidth()) - width;
				boink = true;
			} else if(this.x < bounds.getX()) {
				dx *= -1;
				x = (int) bounds.getX();
				boink = true;
			}
			if(this.y + height >= bounds.getY() + bounds.getHeight()) {
				System.out.println("Bounds y=" + bounds.getY() + ", bounds height=" + bounds.getHeight());
				System.out.println("Bounced at y=" + y + ", y + height= " + (y + height));
				dy *= -1;
				y = (int) (bounds.getY() + bounds.getHeight()) - height;
				boink = true;
			} else if(this.y < bounds.getY()) {
				dy *= -1;
				y = (int) bounds.getY();
				boink = true;
			}
			if(boink) {
				dx *= (Math.random() + 0.55);
				dy *= (Math.random() + 0.55);
			}

		}
		
	}
}
