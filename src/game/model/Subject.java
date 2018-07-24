package game.model;

import game.view.ViewEventUpdating;

public interface Subject {

	void register(Observer o);

	void unRegister(Observer o);

	void updateChanges(ViewEventUpdating data);
}
