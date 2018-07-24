package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import game.helper.ColorHelper;
import game.model.BlockSegment;
import game.model.HoldingBlock;
import game.model.IBlockManual;
import game.model.behavior.DotShapeBehavior;
import game.model.behavior.IShapeBehavior;
import game.model.behavior.LShapeBehavior;
import game.model.behavior.SquareShapeBehavior;
import game.model.behavior.TShapeBehavior;
import game.model.behavior.ZicZacShapeBehavior;
import game.necessary.mybuild.ScreenNextDropsManual;

public class BasicNextDropsView extends JPanel implements ScreenNextDropsManual {
	/**
	 * 
	 */
	private Font font;
	private static final long serialVersionUID = 1L;
	private List<HoldingBlock> nextDropsBlocks;

	public BasicNextDropsView(List<HoldingBlock> nexts) {
		this.nextDropsBlocks = nexts;
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
		setPreferredSize(new Dimension(5 * BlockSegment.size, 576));
		font = new Font(Font.SERIF, Font.BOLD, 24);
	}

	@Override
	public void setNextDropsList(List<HoldingBlock> listNextDrops) {
		this.nextDropsBlocks = listNextDrops;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO : repaint all next block for reponsive ui
		super.paintComponent(g);

		int centerX = getPreferredSize().width / 2;
		int offY = 3 * BlockSegment.size;

		drawBackGround(ColorHelper.getColor(ColorHelper.BACKGROUND), g);
		drawNextDrops(centerX, offY, 16, g);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("Nexts", centerX - ("Nexts".length() * font.getSize() / 4), 2 * BlockSegment.size);
	}

	private void drawBackGround(Color background, Graphics g) {
		g.setColor(background);
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
	}

	private void drawNextDrops(int centerX, int centerY, int gap, Graphics g) {
		g.fillRect(0, centerY, getPreferredSize().width, getPreferredSize().height - centerY);
		for (int i = 0; i < nextDropsBlocks.size(); i++, centerX = getPreferredSize().width / 2) {
			HoldingBlock hdb = nextDropsBlocks.get(i);
			centerY += gap + BlockSegment.size;

			IBlockManual blockManual = null;
//			System.out.println(hdb);
			switch (hdb.getType()) {
			case DOT:
				blockManual = new DotShapeBehavior();
				centerX -= blockManual.getWidth() / 2;
				break;
			case I:
				blockManual = new IShapeBehavior();
				break;
			case L:
				blockManual = new LShapeBehavior();
				centerX -= BlockSegment.size / 2;
				break;
			case SQUARE:
				blockManual = new SquareShapeBehavior();
				break;
			case T:
				blockManual = new TShapeBehavior();
				centerX -= BlockSegment.size / 2;
				break;
			case ZICZAC:
				blockManual = new ZicZacShapeBehavior();
				centerX -= BlockSegment.size / 2;
				break;

			}

			for (Point p : blockManual.initSegmentsLocation(new Point(centerX, centerY), 1)) {
				g.setColor(hdb.getColor());
				g.fillRect(p.x, p.y, BlockSegment.size - 1, BlockSegment.size - 1);
			}
			centerY += blockManual.getHeight() + gap + BlockSegment.size;
		}
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
