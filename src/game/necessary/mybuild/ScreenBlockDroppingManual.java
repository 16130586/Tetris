package game.necessary.mybuild;

import java.util.List;

import game.model.Block;

public interface ScreenBlockDroppingManual extends ScreenManual {
	public void setBlockDropsList(List<Block> blocks);

	public List<Block> getBlockDropsList();
	
	void setCurrentBLockDrop(Block b);
	
	
	
}
