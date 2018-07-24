package game.necessary.mybuild;

public interface ScreenManual {
	void showView();

	void renderView(float delta);

	void pauseView();

	void resumeView();

	void hideView();

	void resizeView(int width, int height);

	void disposeView();

	void create();

	default void toMessage(String msg) {
		
	}

}
