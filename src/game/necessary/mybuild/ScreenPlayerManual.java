package game.necessary.mybuild;

public interface ScreenPlayerManual extends ScreenManual {
	int getLevel();

	void setLevel(int level);

	void setTargetLine(int line);

	int getLastestLine();

	void setCurrentLine(int line);

	
}
