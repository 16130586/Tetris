package game.model.behavior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.model.BlockSegment;
import game.model.BlockType;
import game.model.IBlockManual;

public class IShapeBehavior extends AShapeBehavior implements IBlockManual {

	public IShapeBehavior(BlockType type) {
		super(type);
	}
	public IShapeBehavior() {
		super(null);
	}
	@Override
	public List<Point> initSegmentsLocation(Point centerLocation, int oppScale) {
		List<Point> output = new ArrayList<>();
		for (int i = -2; i <= 1; i++) {
			output.add(new Point(centerLocation.x + i * (BlockSegment.size / oppScale), centerLocation.y));
		}
		return output;
	}

	@Override
	public int getHeight() {
		return BlockSegment.size;
	}

	@Override
	public int getWidth() {
		return BlockSegment.size * 4;
	}

}
