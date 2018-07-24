package game.launcher;

import java.util.HashMap;
import java.util.Map;

import game.controller.MainGameController;
import game.controller.TetrisController;
import game.model.BasicGameModel;
import game.model.TetrisModel;
import game.necessary.mybuild.ScreenIntro;
import game.necessary.mybuild.MainScreenManual;
import game.necessary.mybuild.ScreenManual;
import game.view.BasicGameView;
import game.view.BasicIntroGameView;

public class TetrisLauncher {
	public static void main(String[] args) {
		TetrisModel gameModel = new BasicGameModel();
		@SuppressWarnings("rawtypes")
		Map<Class, ScreenManual> views = new HashMap<>();

		TetrisController gameController = new MainGameController(gameModel, views);
		ScreenManual boardGameScreen = new BasicGameView(gameModel, gameController);
		ScreenManual introGameScreen = new BasicIntroGameView(gameController);

		views.put(MainScreenManual.class, boardGameScreen);
		views.put(ScreenIntro.class, introGameScreen);

		
		gameController.create();
	}
}
