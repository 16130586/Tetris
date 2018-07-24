package game.model.behavior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.model.Block;
import game.model.BlockSegment;
import game.model.BlockType;
import game.model.IBlockManual;

public class DotShapeBehavior extends AShapeBehavior implements IBlockManual {
	
	public DotShapeBehavior(BlockType type) {
		super(type);
	}
	 public DotShapeBehavior() {
		super(null);
	}
	@Override
	public List<Point> initSegmentsLocation(Point centerLocation , int oppScale) {
		List<Point> output = new ArrayList<Point>();
		output.add(new Point(centerLocation.x, centerLocation.y));
		return output;
	}

	@Override
	public void rotate(Block block, boolean[][] rawMapFilled) {
	}

	@Override
	public int getHeight() {
		return BlockSegment.size;
	}

	@Override
	public int getWidth() {
		return BlockSegment.size;
	}

}
