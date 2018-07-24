package game.model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import game.view.ViewEventUpdating;

public class BasicGameModel implements TetrisModel {
	private List<Block> allBlockDropped;
	private Queue<HoldingBlock> nextHoldingBlockDrop;
	private Queue<Block> nextBlocksDrop;
	private Random rd = new Random();
	private Block currentBlock = null;
	private Block lastHolding = null;
	private int currentLine;
	private int targetLine;
	private int level;
	private int score;
	private boolean[][] mappingGameBooleanMap;
	private List<Observer> observers;
	private int totalScore;

	public BasicGameModel() {
		allBlockDropped = new LinkedList<>();
		nextBlocksDrop = new LinkedList<>();
		nextHoldingBlockDrop = new LinkedList<>();
		mappingGameBooleanMap = new boolean[TetrisModel.MAX_ROW][TetrisModel.MAX_COL];
		observers = new ArrayList<>(10);
		targetLine = GameConstraint.GAME_DEFAULT_TARGET_BY_LEVELS[0];
	}

	@Override
	public Block createRandomNewBlock() {
		Block output = null;
		int y = -4;
		BlockManual blockManual = BlockManual.values()[rd.nextInt(BlockManual.values().length)];
		output = new Block(blockManual.getBehavior(), new Point(4 * BlockSegment.size, y * BlockSegment.size),
				new Color(rd.nextInt(256), rd.nextInt(256), rd.nextInt(256)));
		// TODO define some material colors for default block
		return output;
	}

	@Override
	public void dropCurrentBlock(int step) {
		currentBlock.center.y += BlockSegment.size;
		for (BlockSegment sgm : currentBlock.getSegments())
			sgm.getLocacation().y += BlockSegment.size;
		mappingBlockToBooleanGameMap(mappingGameBooleanMap);
		updateChanges(ViewEventUpdating.DROP_CURRENT_BLOCK);
	}

	@Override
	public boolean canDrops(Block b, int step) {
		for (BlockSegment sgm : b.getSegments()) {
			int futureY = sgm.getLocacation().y + step * BlockSegment.size;
			int futureX = sgm.getLocacation().x;
			int mappingY = futureY / BlockSegment.size;
			int mappingX = futureX / BlockSegment.size;
			// checking is it filled or bottom
			if (mappingY < 0)
				return true;
			if (mappingX < 0 || mappingY >= TetrisModel.MAX_ROW || mappingX >= TetrisModel.MAX_COL
					|| mappingGameBooleanMap[mappingY][mappingX])
				return false;
		}
		return true;
	}

	@Override
	public boolean isGoal() {
		return currentLine == targetLine;
	}

	@Override
	public int nextLevel() {
		return level + 1;
	}

	@Override
	public HoldingBlock getLastHodingBlock() {
		HoldingBlock output = null;
		// System.out.println("get last holding block! with lastholding == null?" +
		// (lastHolding == null));
		if (lastHolding != null) {
			output = new HoldingBlock(lastHolding.blockBihavior.getTypeManual(), lastHolding.wholeColor);
		}

		return output;
	}

	@Override
	public void holdingBlock(Block b) {
		this.lastHolding = b;
	}

	@Override
	public List<Block> getAllBlockDropped() {
		return this.allBlockDropped;
	}

	@Override
	public List<HoldingBlock> getListNextDrops() {
		List<HoldingBlock> output = new ArrayList<>(nextHoldingBlockDrop);
		return output;
	}

	@Override
	public void addNextDrop(Block b) {
		this.nextBlocksDrop.add(b);
		this.nextHoldingBlockDrop.add(new HoldingBlock(b.blockBihavior.getTypeManual(), b.wholeColor));
		updateChanges(ViewEventUpdating.NEXT_DROP);
	}

	@Override
	public int getLastestScore() {
		return score;
	}

	@Override
	public void setDisplayScore(int score) {
		this.score = score;
		System.out.println(Thread.currentThread().getName() + " before updating score! " + score);
		updateChanges(ViewEventUpdating.SCORE);
	}

	@Override
	public List<Integer> needToDestroyLines() {
		List<Integer> output = new ArrayList<>(TetrisModel.MAX_ROW);
		if (mappingGameBooleanMap == null)
			mappingGameBooleanMap = mappingBlockToBooleanGameMap();
		else
			mappingBlockToBooleanGameMap(this.mappingGameBooleanMap);
		for (int row = 0; row < mappingGameBooleanMap.length; row++) {
			int filledSegmentCounter = 0;
			for (int col = 0; col < mappingGameBooleanMap[row].length; col++) {
				if (mappingGameBooleanMap[row][col])
					filledSegmentCounter++;
			}
			if (filledSegmentCounter == mappingGameBooleanMap[row].length)
				output.add(row);
		}
		return output;
	}

	private boolean[][] mappingBlockToBooleanGameMap() {
		boolean[][] output = new boolean[TetrisModel.MAX_ROW][TetrisModel.MAX_COL];
		mappingBlockToBooleanGameMap(output);
		return output;
	}

	private void mappingBlockToBooleanGameMap(boolean[][] rawGameMap) {
		for (Block block : getAllBlockDropped()) {
			for (BlockSegment segment : block.getSegments()) {
				int mappingX = segment.getLocacation().x / BlockSegment.size;
				int mappingY = segment.getLocacation().y / BlockSegment.size;
				rawGameMap[mappingY][mappingX] = true;
			}
		}
	}

	@Override
	public void setCurrentBlock(Block b) {
		this.currentBlock = b;
	}

	@Override
	public void rotateCurrentBlock() {
		if (perfectlyCurrentBlockAppear())
			currentBlock.rotate(this.mappingGameBooleanMap);
	}

	@Override
	public void moveCurrentBlock(boolean isLeft) {
		boolean canMove = true;
		boolean perfectlyAppear = this.perfectlyCurrentBlockAppear();
		if (!perfectlyAppear) {
			if (isLeft) {
				for (BlockSegment sgm : currentBlock.getSegments())
					if (sgm.getLocacation().x - BlockSegment.size < 0) {
						canMove = false;
						break;
					}
				if (canMove) {
					for (BlockSegment sgm : currentBlock.getSegments()) {
						sgm.getLocacation().x -= BlockSegment.size;
					}
					currentBlock.center.x -= BlockSegment.size;
				}
			} else {
				for (BlockSegment sgm : currentBlock.getSegments())
					if (sgm.getLocacation().x + BlockSegment.size > (TetrisModel.MAX_COL - 1) * BlockSegment.size) {
						canMove = false;
						break;
					}
				if (canMove) {
					for (BlockSegment sgm : currentBlock.getSegments()) {
						sgm.getLocacation().x += BlockSegment.size;
					}
					currentBlock.center.x += BlockSegment.size;
				}
			}
			return;
		}

		if (isLeft) {
			for (BlockSegment sgm : currentBlock.getSegments()) {
				int futureX = sgm.getLocacation().x - BlockSegment.size;
				if (futureX < 0 || mappingGameBooleanMap[sgm.getLocacation().y / BlockSegment.size][futureX
						/ BlockSegment.size]) {
					canMove = false;
					break;
				}
			}
			if (canMove) {
				for (BlockSegment sgm : currentBlock.getSegments()) {
					sgm.getLocacation().x -= BlockSegment.size;
				}
				currentBlock.center.x -= BlockSegment.size;
			}
		}

		if (!isLeft) {
			for (BlockSegment sgm : currentBlock.getSegments()) {
				int futureX = sgm.getLocacation().x + BlockSegment.size;
				if (futureX >= TetrisModel.MAX_COL * BlockSegment.size
						|| mappingGameBooleanMap[sgm.getLocacation().y / BlockSegment.size][futureX
								/ BlockSegment.size]) {
					canMove = false;
					break;
				}
			}
			if (canMove) {
				for (BlockSegment sgm : currentBlock.getSegments()) {
					sgm.getLocacation().x += BlockSegment.size;
				}
				currentBlock.center.x += BlockSegment.size;
			}
		}

	}

	@Override
	public void freeDropCurrentBlock(Block b) {
		while (canDrops(b, 1))
			dropCurrentBlock(1);
	}

	@Override
	public Block getNextDrop() {
		nextHoldingBlockDrop.remove();
		Block out = nextBlocksDrop.remove();
		return out;
	}

	@Override
	public Block getCurrentBlock() {
		return this.currentBlock;
	}

	@Override
	public void addDroppedBlock(Block b) {
		this.allBlockDropped.add(b);
	}

	@Override
	public int destroyLine(List<Integer> input) {
		int output = input.size();
		for (int i = input.size() - 1; i >= 0; i--) {
			int lineNeedToDestroy = input.get(i);
			for (Block curBlock : allBlockDropped) {
				Iterator<BlockSegment> segments = curBlock.getSegments().iterator();
				while (segments.hasNext()) {
					BlockSegment sgm = segments.next();
					int lineMapping = sgm.getLocacation().y / BlockSegment.size;
					if (lineMapping == lineNeedToDestroy) {
						mappingGameBooleanMap[sgm.getLocacation().y / BlockSegment.size][sgm.getLocacation().x
								/ BlockSegment.size] = false;
						segments.remove();
					}
				}
			}
		}
		dropRestSegments(allBlockDropped, input);
		return output;
	}

	private void dropRestSegments(List<Block> blocks, List<Integer> linesDestroy) {
		BlockSegment[][] sgmMatrix = new BlockSegment[TetrisModel.MAX_ROW][TetrisModel.MAX_COL];
		for (Block b : blocks) {
			for (BlockSegment sgm : b.getSegments()) {
				int mappingX = sgm.getLocacation().x / BlockSegment.size;
				int mappingY = sgm.getLocacation().y / BlockSegment.size;
				sgmMatrix[mappingY][mappingX] = sgm;
			}
		}
		while (!linesDestroy.isEmpty()) {
			int lineToDestroy = linesDestroy.remove(0);
			for (int line = lineToDestroy; line > 0; line--) {
				for (int col = 0; col < TetrisModel.MAX_COL; col++) {
					sgmMatrix[line][col] = sgmMatrix[line - 1][col];
					sgmMatrix[line - 1][col] = null;
					if (sgmMatrix[line][col] != null) {
						mappingGameBooleanMap[line][col] = true;
						mappingGameBooleanMap[line - 1][col] = false;
						sgmMatrix[line][col].getLocacation().y = line * BlockSegment.size;
					}
				}
			}
		}
	}

	@Override
	public void setStatgeTargetLines(int target) {
		this.targetLine = target;
		updateChanges(ViewEventUpdating.PLAYER_INFORMATION);
	}

	@Override
	public int getCurrentDoneLines() {
		return currentLine;
	}

	@Override
	public void setCurrentDoneLines(int current) {
		this.currentLine = current;
		updateChanges(ViewEventUpdating.PLAYER_INFORMATION);
	}

	@Override
	public boolean isDeadthOnThisStage() {
		if (!perfectlyCurrentBlockAppear()) {
			for (int i = allBlockDropped.size() - 1; i >= 0; i--) {
				Block droppedBlock = allBlockDropped.get(i);
				if (currentBlock.isCollision(droppedBlock))
					return true;
			}
		}
		return false;
	}

	@Override
	public void loadNextStageData() {
		currentLine = 0;
		targetLine = GameConstraint.GAME_DEFAULT_TARGET_BY_LEVELS[nextLevel()];
		level = nextLevel();
		totalScore += score;
		score = 0;

		allBlockDropped.clear();
		mappingGameBooleanMap = mappingBlockToBooleanGameMap();
		nextBlocksDrop.clear();
		nextHoldingBlockDrop.clear();
		currentBlock = null;

		for (int i = 0; i < 4; i++) {
			addNextDrop(createRandomNewBlock());
		}
		currentBlock = getNextDrop();
		updateChanges(ViewEventUpdating.DROP_CURRENT_BLOCK);
		updateChanges(ViewEventUpdating.SCORE);
		updateChanges(ViewEventUpdating.NEXT_DROP);
		updateChanges(ViewEventUpdating.PLAYER_INFORMATION);
		updateChanges(ViewEventUpdating.HOLDING_BLOCK);
	}

	private boolean perfectlyCurrentBlockAppear() {
		boolean perfectlyAppear = true;
		for (BlockSegment sgm : currentBlock.getSegments())
			if (sgm.getLocacation().y < 0) {
				perfectlyAppear = false;
				break;
			}
		return perfectlyAppear;
	}

	@Override
	public void register(Observer o) {
		if (!this.observers.contains(o))
			observers.add(o);
	}

	@Override
	public void updateChanges(ViewEventUpdating data) {
		for (int i = 0; i < observers.size(); i++) {
			Observer o = observers.get(i);
			o.update(data);
		}
	}

	@Override
	public void unRegister(Observer o) {
		this.observers.remove(o);
	}

	@Override
	public int getTargetLine() {
		return this.targetLine;
	}

	@Override
	public void pushPullHoldingBlock() {
		// System.out.println(Thread.currentThread().getName() + " "
		// + "before checking lashongding with lashoding == null?" + (lastHolding ==
		// null));

		// pull
		if (lastHolding != null) {
			// System.out.println("lay ra");
			addNextDropOnTopQueue(this.currentBlock);
			currentBlock = lastHolding;
			lastHolding = null;
			updateChanges(ViewEventUpdating.NEXT_DROP);
		}
		// push
		else {
			// System.out.println("bo vo ");
			// System.out.println("trong luc bo vo current == null ?" + (currentBlock ==
			// null));
			lastHolding = currentBlock;
			currentBlock = getNextDrop();
			addNextDrop(createRandomNewBlock());
			// System.out.println(Thread.currentThread().getName() + " " + "after bo vo :
			// lastholding == null ?"
			// + (lastHolding == null));
		}
		updateChanges(ViewEventUpdating.HOLDING_BLOCK);
		updateChanges(ViewEventUpdating.DROP_CURRENT_BLOCK);

	}

	private synchronized void addNextDropOnTopQueue(Block currentBlock) {
		LinkedList<Block> temp = new LinkedList<>(this.nextBlocksDrop);
		temp.add(0, currentBlock);
		this.nextBlocksDrop = temp;
	}

	@Override
	public int getTotalScore() {
		return totalScore;
	}

	@Override
	public void reset() {
		currentLine = 0;
		targetLine = GameConstraint.GAME_DEFAULT_TARGET_BY_LEVELS[0];
		level = 0;
		score = 0;
		totalScore = 0;

		allBlockDropped.clear();
		mappingGameBooleanMap = mappingBlockToBooleanGameMap();
		nextBlocksDrop.clear();
		nextHoldingBlockDrop.clear();
		currentBlock = null;

		for (int i = 0; i < 4; i++) {
			addNextDrop(createRandomNewBlock());
		}
		currentBlock = getNextDrop();
		updateChanges(ViewEventUpdating.DROP_CURRENT_BLOCK);
		updateChanges(ViewEventUpdating.SCORE);
		updateChanges(ViewEventUpdating.NEXT_DROP);
		updateChanges(ViewEventUpdating.PLAYER_INFORMATION);
		updateChanges(ViewEventUpdating.HOLDING_BLOCK);

	}
}