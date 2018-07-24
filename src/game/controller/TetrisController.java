package game.controller;


import game.model.Block;
import game.model.HoldingBlock;

public interface TetrisController   {
	
	void creatRandomNewBlock();

	void dropCurrentBlockStep(int step);

	void holdingBlock(Block b);

	HoldingBlock getLastHodingBlock();

	boolean isGoal();

	void nextLevel();

	void nextGoal();

	void setScore(int score);

	void start();

	void rotateCurrentBlock();
	
	void move(boolean isLeft);

	void freeDropCurrentBlock();
	
	void pushPullHoldingBlock();
	
	void create();

	void quit();

	void openScreen(String string);
	

}
