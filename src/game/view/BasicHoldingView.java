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
import game.necessary.mybuild.ScreenHoldingManual;

public class BasicHoldingView extends JPanel implements ScreenHoldingManual {
	private static final long serialVersionUID = 1L;
	private HoldingBlock holding;
	private Font font;

	public BasicHoldingView(HoldingBlock block) {
		this.holding = block;
	}

	@Override
	public void setHodingBlock(HoldingBlock h) {
		holding = h;
	}

	@Override
	public HoldingBlock getHoldingBlock() {
		return holding;
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
		setPreferredSize(new Dimension(80, 80));
		font = new Font("tahoma", Font.BOLD, 14);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ColorHelper.getColor(ColorHelper.FADED_BACKGROUND));
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("HOLD", getPreferredSize().width - ("HOLD".length() * font.getSize()), 13);
		if (holding != null) {
			Color c = holding.getColor();
			g.setColor(c);
			drawHoldingBlock(g);
		}
	}

	private void drawHoldingBlock(Graphics g) {
		// TODO : repaint all holding block for responsive ui
		int centerX = getPreferredSize().width / 2;
		int centerY = getPreferredSize().height / 2;

		List<Point> segmentsLocation = null;
		IBlockManual blockManual = null;
		int oppScale = 2;

		switch (holding.getType()) {
		case DOT:
			blockManual = new DotShapeBehavior();
			centerX -= (BlockSegment.size / (2 * oppScale));
			centerY -= (BlockSegment.size / (2 * oppScale));
			break;
		case I:
			blockManual = new IShapeBehavior();

			break;
		case L:
			blockManual = new LShapeBehavior();
			centerX -= (BlockSegment.size / (2 * oppScale));
			break;
		case SQUARE:
			blockManual = new SquareShapeBehavior();
			break;
		case T:
			blockManual = new TShapeBehavior();
			centerX -= (BlockSegment.size / (2 * oppScale));
			centerY -= (BlockSegment.size / (2 * oppScale));
			break;
		case ZICZAC:
			blockManual = new ZicZacShapeBehavior();
			centerX -= (BlockSegment.size / (2 * oppScale));
			centerY -= (2 * BlockSegment.size / (2 * oppScale));

			break;
		}
		segmentsLocation = blockManual.initSegmentsLocation(new Point(centerX, centerY), oppScale);
		for (Point p : segmentsLocation)
			g.fillRect(p.x, p.y, (BlockSegment.size / oppScale) - 1, (BlockSegment.size / oppScale) - 1);
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
