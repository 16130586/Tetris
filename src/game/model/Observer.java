package game.model;

import game.view.ViewEventUpdating;

public interface Observer {

	void update(ViewEventUpdating type);
}
