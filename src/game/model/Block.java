package game.model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Block {
	protected List<BlockSegment> segments;
	protected Point center;
	protected IBlockManual blockBihavior;
	protected Color wholeColor;

	public Block(IBlockManual blockBihavior, Point centerLocation, Color c ) {
		this.blockBihavior = blockBihavior;
		this.segments = new ArrayList<>();
		this.center = centerLocation;
		this.wholeColor = c;
		initBlock(centerLocation, c);
	}

	public List<BlockSegment> getSegments() {
		return segments;
	}

	public Point getCenter() {
		System.out.println("center " + center.x + " " + center.y);
		return center;
	}

	public void addSegment(BlockSegment sgm) {
		this.segments.add(sgm);
	}

	public void setBlockBihavior(IBlockManual behavior) {
		this.blockBihavior = behavior;
		segments.clear();
		initBlock(center, wholeColor);
	}

	public void rotate(boolean[][] rawMap) {
		if (blockBihavior != null && rawMap != null)
			blockBihavior.rotate(this , rawMap);
	}

	public void dropByStep(int step) {
		if (blockBihavior != null)
			blockBihavior.dropStep(this, step);
	}

	protected void initBlock(Point center, Color c) {
		if (blockBihavior != null)
			blockBihavior.initBlockSegments(this, center, c);
	}

	public boolean isCollision(Block droppedBlock) {
		for (int thisSegmentCounter = 0; thisSegmentCounter < segments.size(); thisSegmentCounter++) {
			for (int thatSegmentCounter = 0; thatSegmentCounter < droppedBlock.segments.size(); thatSegmentCounter++) {
				if (segments.get(thisSegmentCounter).isCollision(droppedBlock.segments.get(thatSegmentCounter)))
					return true;
			}
		}
		return false;
	}

}
