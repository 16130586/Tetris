package game.model;

import java.awt.Color;
import java.awt.Point;

public class BlockSegment {
	public static int size = 32;
	private Color c;
	private Point location;

	public BlockSegment(Color c, Point location) {
		this.c = new Color(c.getRed(), c.getGreen(), c.getBlue());
		this.location = new Point(location.x, location.y);
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		BlockSegment.size = size;
	}

	public Color getColor() {
		return c;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public Point getLocacation() {
		return location;
	}

	public void setLocacation(Point locacation) {
		this.location = locacation;
	}

	public boolean isCollision(BlockSegment other) {
		return this.location.x == other.location.x && (this.location.y + BlockSegment.size) == other.location.y;
	}

}
