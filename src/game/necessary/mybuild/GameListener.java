package game.necessary.mybuild;

public interface GameListener {
	void create();

	void render();

	void resize(int width, int height);

	void pause();

	void resume();

	void dispose();

	void hide();
}
