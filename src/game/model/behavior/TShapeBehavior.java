package game.model.behavior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.model.BlockSegment;
import game.model.BlockType;
import game.model.IBlockManual;

public class TShapeBehavior extends AShapeBehavior implements IBlockManual {


	public TShapeBehavior(BlockType type) {
		super(type);
	}
	public TShapeBehavior() {
		super(null);
	}

	@Override
	public List<Point> initSegmentsLocation(Point centerLocation , int oppScale) {
		List<Point> output = new ArrayList<>();
		output.add(new Point(centerLocation.x - (BlockSegment.size/oppScale), centerLocation.y));
		output.add(new Point(centerLocation.x, centerLocation.y));
		output.add(new Point(centerLocation.x + (BlockSegment.size/oppScale), centerLocation.y));
		output.add(new Point(centerLocation.x, centerLocation.y + (BlockSegment.size/oppScale)));
		return output;
	}

	@Override
	public int getHeight() {
		return BlockSegment.size * 2;
	}

	@Override
	public int getWidth() {
		return BlockSegment.size * 3;
	}

}
