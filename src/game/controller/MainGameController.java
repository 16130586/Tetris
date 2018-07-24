package game.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import game.model.Block;
import game.model.GameConstraint;
import game.model.HoldingBlock;
import game.model.Observer;
import game.model.TetrisModel;
import game.necessary.mybuild.ScreenIntro;
import game.necessary.mybuild.MainScreenManual;
import game.necessary.mybuild.ScoreManual;
import game.necessary.mybuild.ScreenManual;

public class MainGameController extends AGameController implements Runnable {
	private boolean isStop;

	@SuppressWarnings("rawtypes")
	public MainGameController(TetrisModel model, Map<Class, ScreenManual> views) {
		super(model, views);
	}

	@Override
	public void creatRandomNewBlock() {
		model.createRandomNewBlock();
	}

	@Override
	public void dropCurrentBlockStep(int step) {
		model.dropCurrentBlock(step);
	}

	@Override
	public void holdingBlock(Block b) {
		model.holdingBlock(b);
	}

	@Override
	public boolean isGoal() {
		return model.isGoal();
	}

	@Override
	public void nextLevel() {
		model.nextLevel();
	}

	@Override
	public void nextGoal() {
		model.isGoal();
	}

	@Override
	public void setScore(int score) {
		ScoreManual scoreScreen = (ScoreManual) views.get(ScoreManual.class);
		scoreScreen.setDisplayScore(score);

	}

	@Override
	public void create() {

		// loading next game stage

		// blinding to model
		for (int i = 0; i < 4; i++)
			model.addNextDrop(model.createRandomNewBlock());
		model.setCurrentBlock(model.getNextDrop());
		// start model
		System.out.println("First Create Model donw!");

		// blinding to view
		for (ScreenManual screen : views.values()) {
			screen.create();
			if (screen instanceof Observer) {
				model.register((Observer) screen);
			}
		}

		currentScreen = views.get(ScreenIntro.class);
		currentScreen.showView();
		System.out.println("Loading done!");
		// game done

	}

	@Override
	public void render() {
		currentScreen.renderView(delta);
	}

	@Override
	public void resize(int width, int height) {
		currentScreen.resizeView(width, height);
	}

	@Override
	public void pause() {
		currentScreen.pauseView();
	}

	@Override
	public void resume() {
		currentScreen.resumeView();
	}

	@Override
	public void dispose() {
		currentScreen.disposeView();
	}

	@Override
	public void hide() {
		currentScreen.hideView();
	}

	@Override
	public HoldingBlock getLastHodingBlock() {
		return model.getLastHodingBlock();
	}

	@Override
	public void start() {
		animationThread = new Thread(this);
		animationThread.setName("Thread-GameController");
		animationThread.start();

	}

	@Override
	public void move(boolean isLeft) {
		model.moveCurrentBlock(isLeft);
	}

	@Override
	public void freeDropCurrentBlock() {
		model.freeDropCurrentBlock(model.getCurrentBlock());
	}

	@Override
	public void run() {
		// have to modify this running condition for terminating running thread
		while (true)
			if (!isStop)
				try {
					if (!model.isDeadthOnThisStage() && !model.isGoal()) {
						if (model.canDrops(model.getCurrentBlock(), 1)) {
							model.dropCurrentBlock(1);
						} else {
							model.addDroppedBlock(model.getCurrentBlock());
							model.setCurrentBlock(model.getNextDrop());
							model.addNextDrop(model.createRandomNewBlock());

							// System.out.println("swicthing another current block!");
							// apply changes to dropping screen
						}
						if (!model.isDeadthOnThisStage() && !model.needToDestroyLines().isEmpty()) {
							int lines = model.destroyLine(model.needToDestroyLines());
							int oldScore = model.getLastestScore();
							// it will change in the future(the computing score behavior)
							// System.out.println(Thread.currentThread().getName() + " cal new score: " +
							// oldScore + " +"
							// + lines + "*" + "10");
							int newScore = oldScore + lines * TetrisModel.MAX_COL + (lines - 1) * 5;
							// applies changes for display score screen
							// System.out.println(Thread.currentThread().getName() + " assign new value to
							// score " + newScore);
							model.setDisplayScore(newScore);
							// applies changes for Information score screen
							model.setCurrentDoneLines(model.getCurrentDoneLines() + lines);
							// System.out.println("Line Destroy detected!");

						}
						// applies changes
					} else if (model.isDeadthOnThisStage()) {
						// move to deadth screen -> print end game string -> saving last player's data.
						// exit game
						if (model.nextLevel() == 1) {
							// not existing total score
							writeScoreIntoFile(model.getLastestScore());
						} else
							writeScoreIntoFile(model.getTotalScore());

						String[] options = new String[] { "Yes", "No" };
						int response = JOptionPane.showOptionDialog(null, "Do you want to back to main menu?", "",
								JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
						if (response == 0) {
							openScreen("menu");
							isStop = !isStop;
						} else {
							System.exit(0);
						}
					} else if (model.isGoal()) {
						if (model.nextLevel() < GameConstraint.GAME_DEFAULT_LEVEL) {
							currentScreen.toMessage("Next Level!");
							System.out.println("LOADED next stage");
							Thread.sleep(2000);
							model.loadNextStageData();
						} else {
							currentScreen.toMessage("Congratulation,It's your finishing!");
							writeScoreIntoFile(model.getTotalScore());
							Thread.sleep(2000);
							System.exit(0);
						}
					}
					currentScreen.renderView(0.0f);
					Thread.sleep(350);
					// System.out.println("dropped");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

	}

	private void writeScoreIntoFile(int totalScore) {
		File f = new File(GameConstraint.HIGH_SCORE_FILE);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
			writer.append(totalScore + "");
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void pushPullHoldingBlock() {
		model.pushPullHoldingBlock();
	}

	@Override
	public void quit() {
		for (ScreenManual sc : views.values()) {
			sc.disposeView();
		}
		System.exit(1);
	}

	@Override
	public void openScreen(String string) {
		if (string.equals("main")) {
			if (!isStop) {
				currentScreen.disposeView();
				views.get(MainScreenManual.class).showView();
				currentScreen = views.get(MainScreenManual.class);
				start();
			} else {
				isStop = !isStop;
				model.reset();
			}
		}
		if (string.equals("menu")) {
			currentScreen.disposeView();
			views.get(ScreenIntro.class).showView();
			currentScreen = views.get(ScreenIntro.class);
		}
		if (string.equals("highScore")) {
			String[] scores = getHighScores().split("\n");
			List<Integer> contaier = new LinkedList<>();
			int maxAccepted = 9;
			int counter = 1;
			for(String s : scores) {
				if(s == null || s.equals("")) continue;
				contaier.add(Integer.valueOf(s));
				if(counter++ >= maxAccepted)
					break;
			}
			Collections.sort(contaier , new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o2.compareTo(o1);
				}
			});
			counter = 1;
			StringBuffer bf = new StringBuffer();
			for(Integer i :  contaier) {
				bf.append(counter++ + "." + "        " + i + "");
				bf.append("\n");
			}
			JOptionPane.showConfirmDialog(null, bf.toString(), "Hight Scores", JOptionPane.CANCEL_OPTION);
		}
	}

	private String getHighScores() {
		StringBuffer rs = new StringBuffer();
		File f = new File(GameConstraint.HIGH_SCORE_FILE);
		BufferedReader reader = null;
		try {
			if(!f.exists()) f.createNewFile();
			reader = new BufferedReader(new FileReader(f));
			String line = "";
			while ((line = reader.readLine()) != null) {
				rs.append(line);
				rs.append("\n");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs.toString();
	}

}
