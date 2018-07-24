package game.model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.helper.RotationHepler;

public interface IBlockManual {
	int ROTATION_ANGLE = 90;

	default void rotate(Block block, boolean[][] rawMapFilled) {
		Point center = block.getCenter();
		List<Point> futureLocationSegments = new ArrayList<>(4);
		boolean isARotationOutBound = false;
		boolean isOverlapFilledSegment = false;
		for (BlockSegment sgm : block.getSegments()) {
			Point imgPoint = RotationHepler.rolate(ROTATION_ANGLE, center, sgm.getLocacation());
			futureLocationSegments.add(imgPoint);
			if (imgPoint.x < 0 || imgPoint.x >= TetrisModel.MAX_COL * BlockSegment.size
					|| imgPoint.y >= TetrisModel.MAX_ROW * BlockSegment.size) {
				isARotationOutBound = true;
				break;
			}
			if (rawMapFilled[imgPoint.y / BlockSegment.size][imgPoint.x / BlockSegment.size]) {
				isOverlapFilledSegment = true;
				break;
			}
		}
		if (!isARotationOutBound && !isOverlapFilledSegment) {
			for (int i = 0; i < block.getSegments().size(); i++) {
				Point o = block.getSegments().get(i).getLocacation();
				o.x = futureLocationSegments.get(i).x;
				o.y = futureLocationSegments.get(i).y;
			}
		}

	}

	default void dropStep(Block block, int step) {
		for (BlockSegment sgm : block.getSegments()) {
			sgm.getLocacation().y += step * BlockSegment.size;
		}
		block.getCenter().y += step * BlockSegment.size;
	}

	default void initBlockSegments(Block block, Point centerLocation, Color c) {
		for (Point p : initSegmentsLocation(centerLocation , 1)) {
			block.addSegment(new BlockSegment(c, p));
		}
	}

	List<Point> initSegmentsLocation(Point centerLocation , int oppScale);

	int getHeight();

	int getWidth();

	public BlockType getTypeManual();

	public void setTypeManual(BlockType t);
}
