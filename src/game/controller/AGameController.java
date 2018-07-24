package game.controller;

import java.util.Map;

import game.model.TetrisModel;
import game.necessary.mybuild.GameListener;
import game.necessary.mybuild.ScreenManual;

public abstract class AGameController implements TetrisController, GameListener {
	@SuppressWarnings("rawtypes")
	protected Map<Class, ScreenManual> views;
	protected Thread animationThread;
	protected TetrisModel model;
	protected ScreenManual currentScreen;
	protected float delta;

	@SuppressWarnings("rawtypes")
	public AGameController(TetrisModel model, Map<Class, ScreenManual> views) {
		this.views = views;
		this.model = model;
	}

	@Override
	public void rotateCurrentBlock() {
		model.rotateCurrentBlock();
	}
}
