package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.controller.TetrisController;
import game.model.Block;
import game.model.HoldingBlock;
import game.model.TetrisModel;
import game.necessary.mybuild.MainScreenManual;
import game.necessary.mybuild.ScreenBlockDroppingManual;
import game.necessary.mybuild.ScreenHoldingManual;
import game.necessary.mybuild.ScreenManual;
import game.necessary.mybuild.ScreenNextDropsManual;
import game.necessary.mybuild.ScreenPlayerManual;
import game.necessary.mybuild.ScreenScore;

public class BasicGameView extends JFrame implements MainScreenManual {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5144322221342515955L;
	private Font font;
	@SuppressWarnings("rawtypes")
	private Map<Class, Object> viewComponents;
	private TetrisModel gameModel;
	private TetrisController gameController;

	public BasicGameView(TetrisModel gameModel, TetrisController gameController) {
		this.gameModel = gameModel;
		this.gameController = gameController;
		viewComponents = new HashMap<>();
	}

	@Override
	public void renderView(float delta) {
		for (Object o : viewComponents.values()) {
			if (o instanceof JPanel)
				((JPanel) o).repaint();
		}
	}

	@Override
	public void pauseView() {

	}

	@Override
	public void resumeView() {

	}

	@Override
	public void create() {
		ScreenPlayerManual playerManual = new BasicPlayerInforView();
		ScreenNextDropsManual nDropsManual = new BasicNextDropsView(gameModel.getListNextDrops());
		ScreenHoldingManual hdManual = new BasicHoldingView(gameModel.getLastHodingBlock());
		ScreenBlockDroppingManual droppingManual = new BasicBlockDropsView(gameModel.getAllBlockDropped());
		ScreenScore score = new BasicScoreView();

		playerManual.setTargetLine(gameModel.getTargetLine());

		viewComponents.put(ScreenPlayerManual.class, playerManual);
		viewComponents.put(ScreenNextDropsManual.class, nDropsManual);

		viewComponents.put(ScreenHoldingManual.class, hdManual);

		viewComponents.put(ScreenBlockDroppingManual.class, droppingManual);

		viewComponents.put(ScreenScore.class, score);

		droppingManual.setCurrentBLockDrop(gameModel.getCurrentBlock());

		for (Object screen : viewComponents.values())
			if (screen instanceof ScreenManual)
				((ScreenManual) screen).create();

		initLayout();
		initEventListeners();

		font = new Font("tahoma", Font.BOLD, 32);
	}

	private void initEventListeners() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_SPACE:
					gameController.freeDropCurrentBlock();
					break;
				case KeyEvent.VK_LEFT:
					gameController.move(true);
					break;
				case KeyEvent.VK_RIGHT:
					gameController.move(false);
					break;
				case KeyEvent.VK_UP:
					gameController.rotateCurrentBlock();
					break;
				case KeyEvent.VK_DOWN:
					gameController.dropCurrentBlockStep(1);
					break;
				case KeyEvent.VK_Z:
					gameController.pushPullHoldingBlock();
					break;
				default:
					break;
				}
			}
		});
	}

	private void initLayout() {
		setPreferredSize(new Dimension(583, 650));
		JPanel playerInfor = (JPanel) viewComponents.get(ScreenPlayerManual.class);
		JPanel nDrops = (JPanel) viewComponents.get(ScreenNextDropsManual.class);
		JPanel holdingBlock = (JPanel) viewComponents.get(ScreenHoldingManual.class);
		JPanel dropping = (JPanel) viewComponents.get(ScreenBlockDroppingManual.class);
		JPanel score = (JPanel) viewComponents.get(ScreenScore.class);
		int gap = 3;
		setLayout(null);

		add(playerInfor);
		add(nDrops);
		add(holdingBlock);
		add(dropping);
		add(score);

		holdingBlock.setBounds(0, score.getPreferredSize().height + gap, holdingBlock.getPreferredSize().width,
				holdingBlock.getPreferredSize().height);
		playerInfor.setBounds(0, holdingBlock.getLocation().y + holdingBlock.getPreferredSize().height + gap,
				playerInfor.getPreferredSize().width, playerInfor.getPreferredSize().height);
		score.setBounds(holdingBlock.getLocation().x + holdingBlock.getPreferredSize().width + gap, 0,
				score.getPreferredSize().width, score.getPreferredSize().height);
		dropping.setBounds(score.getLocation().x, score.getLocation().y + score.getPreferredSize().height + gap,
				dropping.getPreferredSize().width, dropping.getPreferredSize().height);
		nDrops.setBounds(dropping.getLocation().x + dropping.getPreferredSize().width + gap, dropping.getLocation().y,
				nDrops.getPreferredSize().width, nDrops.getPreferredSize().height);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public int getLastestScore() {
		ScreenScore score = (ScreenScore) viewComponents.get(ScreenScore.class);
		return score.getLastestScore();
	}

	@Override
	public void setDisplayScore(int score) {
		ScreenScore scoreScreen = (ScreenScore) viewComponents.get(ScreenScore.class);
		scoreScreen.setDisplayScore(score);
	}

	@Override
	public void setBlockDropsList(List<Block> blocks) {
		ScreenBlockDroppingManual screen = (ScreenBlockDroppingManual) viewComponents
				.get(ScreenBlockDroppingManual.class);
		screen.setBlockDropsList(blocks);
	}

	@Override
	public List<Block> getBlockDropsList() {
		ScreenBlockDroppingManual screen = (ScreenBlockDroppingManual) viewComponents
				.get(ScreenBlockDroppingManual.class);
		return screen.getBlockDropsList();
	}

	@Override
	public void setHodingBlock(HoldingBlock h) {
		ScreenHoldingManual screen = (ScreenHoldingManual) viewComponents.get(ScreenHoldingManual.class);
		screen.setHodingBlock(h);

	}

	@Override
	public HoldingBlock getHoldingBlock() {
		ScreenHoldingManual screen = (ScreenHoldingManual) viewComponents.get(ScreenHoldingManual.class);
		return screen.getHoldingBlock();
	}

	@Override
	public void setNextDropsList(List<HoldingBlock> listNextDrops) {
		ScreenNextDropsManual screen = (ScreenNextDropsManual) viewComponents.get(ScreenNextDropsManual.class);
		screen.setNextDropsList(listNextDrops);
	}

	@Override
	public int getLevel() {
		ScreenPlayerManual screen = (ScreenPlayerManual) viewComponents.get(ScreenPlayerManual.class);
		return screen.getLevel();
	}

	@Override
	public void setLevel(int level) {
		ScreenPlayerManual screen = (ScreenPlayerManual) viewComponents.get(ScreenPlayerManual.class);
		screen.setLevel(level);
	}

	@Override
	public void setTargetLine(int line) {
		ScreenPlayerManual screen = (ScreenPlayerManual) viewComponents.get(ScreenPlayerManual.class);
		screen.setTargetLine(line);
	}

	@Override
	public int getLastestLine() {
		ScreenPlayerManual screen = (ScreenPlayerManual) viewComponents.get(ScreenPlayerManual.class);
		return screen.getLastestLine();
	}

	@Override
	public void setCurrentLine(int line) {
		ScreenPlayerManual screen = (ScreenPlayerManual) viewComponents.get(ScreenPlayerManual.class);
		screen.setCurrentLine(line);

	}

	@Override
	public void setCurrentBLockDrop(Block b) {
		ScreenBlockDroppingManual screen = (ScreenBlockDroppingManual) viewComponents
				.get(ScreenBlockDroppingManual.class);
		screen.setCurrentBLockDrop(b);
	}

	@Override
	public void update(ViewEventUpdating type) {
		if (type == null)
			return;
		ViewEventUpdating data = type;
		switch (data) {
		case PLAYER_INFORMATION:
			ScreenPlayerManual pl = (ScreenPlayerManual) viewComponents.get(ScreenPlayerManual.class);
			pl.setCurrentLine(gameModel.getCurrentDoneLines());
			pl.setLevel(gameModel.nextLevel() - 1);
			pl.setTargetLine(gameModel.getTargetLine());
			break;
		case HOLDING_BLOCK:
			ScreenHoldingManual hd = (ScreenHoldingManual) viewComponents.get(ScreenHoldingManual.class);
			hd.setHodingBlock(gameModel.getLastHodingBlock());
			break;
		case NEXT_DROP:
			ScreenNextDropsManual nd = (ScreenNextDropsManual) viewComponents.get(ScreenNextDropsManual.class);
			nd.setNextDropsList(gameModel.getListNextDrops());
			break;
		case SCORE:
			ScreenScore score = (ScreenScore) viewComponents.get(ScreenScore.class);
			System.out.println(Thread.currentThread().getName() + " processing score + " + gameModel.getLastestScore());
			score.setDisplayScore(gameModel.getLastestScore());
			break;
		case DROP_CURRENT_BLOCK:
			setCurrentBLockDrop(gameModel.getCurrentBlock());
			break;
		}
	}

	@Override
	public void toMessage(String msg) {
		Graphics g = getGraphics();
		if (g == null) {
			System.out.println(getClass().getSimpleName() + " " + "Error with to Message!");
			return;
		}
		int centerX = getPreferredSize().width / 2;
		int centerY = getPreferredSize().height / 2;
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(msg, centerX - (msg.length() * font.getSize()) / 4, centerY);

		g.setFont(null);
	}

	@Override
	public void disposeView() {
		this.dispose();
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
