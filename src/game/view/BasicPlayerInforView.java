package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.helper.ColorHelper;
import game.necessary.mybuild.ScreenPlayerManual;

public class BasicPlayerInforView extends JPanel implements ScreenPlayerManual {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1157998431779117459L;
	private int displayTargetLine;
	private int displayLevel;
	private int displayCurLine;
	private Font font;

	@Override
	public int getLevel() {
		return displayLevel;
	}

	@Override
	public void setLevel(int level) {
		this.displayLevel = level;
	}

	@Override
	public void setTargetLine(int line) {
		this.displayTargetLine = line;
	}

	@Override
	public int getLastestLine() {
		return displayCurLine;
	}

	@Override
	public void setCurrentLine(int line) {
		displayCurLine = line;
	}

	@Override
	public void renderView(float delta) {

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
		setPreferredSize(new Dimension(80, 493));
		font = new Font("tahoma", Font.BOLD, 20);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ColorHelper.getColor(ColorHelper.FADED_BACKGROUND));
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);

		g.setColor(Color.WHITE);
		g.setFont(this.font);

		int centerX = getPreferredSize().width / 2;
		int needToDrawY = 100;

		int gap = 30;

		g.drawString("Level", centerX - ("Level".length() * font.getSize())/4, needToDrawY);
		needToDrawY+=gap;
		g.drawString(displayLevel + "", centerX  - ((displayLevel + "").length()*font.getSize())/4, needToDrawY);
		needToDrawY+=gap;
		g.drawString("Target", centerX - ("Target".length() * font.getSize())/4, needToDrawY);
		needToDrawY+=gap;
		g.drawString(displayTargetLine + "", centerX - ((displayTargetLine + "").length()*font.getSize())/4, needToDrawY);
		needToDrawY+=gap;
		g.drawString("Done", centerX - ("Done".length() * font.getSize())/4, needToDrawY);
		needToDrawY+=gap;
		g.drawString(displayCurLine + "", centerX -((displayCurLine + "").length()*font.getSize())/4, needToDrawY);

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
