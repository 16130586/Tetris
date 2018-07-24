package game.model;

import java.util.List;

import game.necessary.mybuild.ScoreManual;

public interface TetrisModel extends ScoreManual , Subject {
	public final int MAX_COL = 10;
	public final int MAX_ROW = 18;

	Block createRandomNewBlock();

	void dropCurrentBlock(int step);

	boolean isGoal();

	int nextLevel();

	HoldingBlock getLastHodingBlock();

	void holdingBlock(Block b);

	List<Block> getAllBlockDropped();

	void addDroppedBlock(Block b);

	List<HoldingBlock> getListNextDrops();

	Block getNextDrop();

	List<Integer> needToDestroyLines();

	void setCurrentBlock(Block b);

	Block getCurrentBlock();

	void rotateCurrentBlock();

	void moveCurrentBlock(boolean isLeft);

	void addNextDrop(Block b);

	boolean canDrops(Block b, int step);

	int destroyLine(List<Integer> input);

	void freeDropCurrentBlock(Block b);
	
	void setStatgeTargetLines(int target);
	
	int getTargetLine();
	
	int getCurrentDoneLines();
	
	void setCurrentDoneLines(int current);

	boolean isDeadthOnThisStage();

	void loadNextStageData();

	void pushPullHoldingBlock();
	
	int getTotalScore();

	void reset();

}
