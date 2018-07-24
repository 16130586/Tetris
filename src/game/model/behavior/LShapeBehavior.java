package game.model.behavior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.model.BlockSegment;
import game.model.BlockType;
import game.model.IBlockManual;

public class LShapeBehavior extends AShapeBehavior implements IBlockManual {

	public LShapeBehavior(BlockType type) {
		super(type);
	}
	public LShapeBehavior() {
		super(null);
	}

	@Override
	public List<Point> initSegmentsLocation(Point centerLocation, int oppScale) {
		List<Point> output = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			output.add(new Point(centerLocation.x  + i * (BlockSegment.size / oppScale), centerLocation.y));
		}
		output.add(new Point(centerLocation.x + (BlockSegment.size / oppScale),
				centerLocation.y - (BlockSegment.size / oppScale)));

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
