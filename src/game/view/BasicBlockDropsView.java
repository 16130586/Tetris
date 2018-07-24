package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import game.helper.ColorHelper;
import game.model.Block;
import game.model.BlockSegment;
import game.model.TetrisModel;
import game.necessary.mybuild.ScreenBlockDroppingManual;

public class BasicBlockDropsView extends JPanel implements ScreenBlockDroppingManual {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3394805251906176383L;
	private List<Block> boardGame;
	private Block currentBlock;

	public BasicBlockDropsView(List<Block> dropsBlock) {
		this.boardGame = dropsBlock;
	}

	@Override
	public void renderView(float delta) {
		repaint();
	}

	@Override
	public void pauseView() {

	}

	@Override
	public void resumeView() {

	}


	@Override
	public void disposeView() {
		
	}

	@Override
	public void create() {
		setPreferredSize(new Dimension(TetrisModel.MAX_COL * BlockSegment.size, TetrisModel.MAX_ROW * BlockSegment.size));
	}

	@Override
	public void setBlockDropsList(List<Block> blocks) {
		this.boardGame = blocks;
	}

	@Override
	public List<Block> getBlockDropsList() {
		return boardGame;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// background-color
		g.setColor(ColorHelper.getColor(ColorHelper.BACKGROUND));
		// // drawing background
		g.drawRect(0, 0, 10 * BlockSegment.size, 18 * BlockSegment.size);

		for (int r = 0; r < 18; r++) {
			int y = r * BlockSegment.size;
			for (int c = 0; c < 10; c++) {
				int x = c * BlockSegment.size;
				g.setColor(ColorHelper.getColor(ColorHelper.BACKGROUND));
				// inside vertical and horizontal cross over
				g.fillRect(x, y, BlockSegment.size, BlockSegment.size);
				g.setColor(ColorHelper.getColor(ColorHelper.VERTICAL_DECOREATE));
				// vertical-decorate-line
				g.drawLine(x, 0, x, 18 * BlockSegment.size);
			}
			g.setColor(ColorHelper.getColor(ColorHelper.HORIZONTAL_DECOREATE));
			// horizontal-decorate-line
			g.drawLine(0, y, 10 * BlockSegment.size, y);
		}
		for (int i = 0; i < boardGame.size(); i++) {
			Block b = boardGame.get(i);
			for (BlockSegment s : b.getSegments()) {
				g.setColor(s.getColor());
				g.fillRect(s.getLocacation().x + 1, s.getLocacation().y + 1, BlockSegment.size - 2,
						BlockSegment.size - 2);
			}
		}
		if(currentBlock != null)
		for(BlockSegment sgm : currentBlock.getSegments()) {
			g.setColor(sgm.getColor());
			g.fillRect(sgm.getLocacation().x + 1, sgm.getLocacation().y + 1, BlockSegment.size - 2,
					BlockSegment.size - 2);
		}

	}

	@Override
	public void setCurrentBLockDrop(Block b) {
		this.currentBlock = b;
	}
	@Override
	public void showView() {
		this.setVisible(true);		
	}

	@Override
	public void hideView() {
		this.setVisible(false);
	}

	@Override
	public void resizeView(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
	}

}
