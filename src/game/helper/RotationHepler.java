package game.helper;

import java.awt.Point;

public class RotationHepler {
	public static Point rolate(int angle, Point origin, Point point) {
		Point output = new Point();
		Point reOrigin = new Point(point.x - origin.x, point.y-origin.y);
		output.x = -reOrigin.y;
		output.y = reOrigin.x;
		
		output.x+=origin.x;
		output.y+=origin.y;
		return output;
	}
}
