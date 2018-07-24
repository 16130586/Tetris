package game.necessary.mybuild;

import java.util.List;

import game.model.HoldingBlock;

public interface ScreenNextDropsManual extends ScreenManual {

	void setNextDropsList(List<HoldingBlock> listNextDrops);

}
