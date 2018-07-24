package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.helper.ColorHelper;
import game.necessary.mybuild.ScreenScore;

public class BasicScoreView extends JPanel implements ScreenScore {
	/**
	 * 
	 */
	private Font font;
	private static final long serialVersionUID = 1L;
	private int currentScore = 0;

	@Override
	public int getLastestScore() {
		return currentScore;
	}

	@Override
	public void setDisplayScore(int score) {
		currentScore = score;
	}

	@Override
	public void create() {
		setPreferredSize(new Dimension(320, 32));
		font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
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
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ColorHelper.getColor(ColorHelper.FADED_BACKGROUND));
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("Score", 15, getPreferredSize().height - 5);
		g.drawString(currentScore + "",
				getPreferredSize().width - 15 - ((currentScore + "").length() * font.getSize()) / 2,
				getPreferredSize().height - 5);
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
