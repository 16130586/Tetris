package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.controller.TetrisController;
import game.model.GameConstraint;
import game.necessary.mybuild.ScreenIntro;
import game.necessary.mybuild.ScreenManual;

public class BasicIntroGameView extends JFrame implements ScreenIntro {
	private TetrisController controller;
	private Map<String, JButton> btContainer = new LinkedHashMap<>();
	private Font smallFont, bigFont;
	private Image background;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicIntroGameView(TetrisController controller) {
		this.controller = controller;
		try {
			background = ImageIO.read(new File(GameConstraint.INTRO_BACKGROUND));
			System.out.println("loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void renderView(float delta) {
		this.repaint();
	}

	@Override
	public void pauseView() {

	}

	@Override
	public void resumeView() {

	}

	@Override
	public void disposeView() {
		this.dispose();
	}

	@Override
	public void create() {
		initComponents();
		initLayout();
		initEvents();
	}

	private void initEvents() {
		btContainer.get("quit").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.quit();
			}
		});
		btContainer.get("quit");

		btContainer.get("newGame").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("new game");
				controller.openScreen("main");
			}
		});
		;
		btContainer.get("newGame");

		btContainer.get("highScore");
		btContainer.get("highScore").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("open high score");
				controller.openScreen("highScore");
			}
		});
	}

	private void initLayout() {

		int middleX = getPreferredSize().width / 2;
		int startedY = getPreferredSize().height / 2;

		for (JButton bt : btContainer.values()) {
			putOn(middleX, startedY, bt);
			System.out.println(startedY);
			startedY += 50;
		}

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void putOn(int middleX, int middleY, JButton bt) {
		int btWidth = bt.getPreferredSize().width;
		int btHeight = bt.getPreferredSize().height;

		int newestX = middleX - btWidth / 2;
		int newsetY = middleY + btHeight / 2;

		bt.setBounds(newestX, newsetY, btWidth, btHeight);

	}

	private void initComponents() {
		setPreferredSize(new Dimension(583, 690));
		setContentPane(new Canvas(background, 583, 690));
		smallFont = new Font("Comic Sans", Font.PLAIN, 18);
		bigFont = new Font("Comic Sans", Font.PLAIN, 32);

		btContainer.put("newGame", createTransparnetButton("New Game"));
		btContainer.put("highScore", createTransparnetButton("High Score"));
		btContainer.put("quit", createTransparnetButton("Quit"));

	}

	private JButton createTransparnetButton(String name) {
		JButton bt = new JButton(name);
		System.out.println(name);
		bt.setForeground(Color.RED);
		bt.setFont(smallFont);
		bt.setOpaque(false);
		bt.setContentAreaFilled(false);
		bt.setBorderPainted(false);
		bt.setHorizontalAlignment(SwingConstants.CENTER);
		bt.setVerticalAlignment(SwingConstants.CENTER);
		getContentPane().add(bt);
		return bt;
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

	private class Canvas extends JPanel {
		private Image background;

		public Canvas(Image background, int x, int y) {
			super();
			this.background = background;
			this.setSize(x, y);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

	}
}
