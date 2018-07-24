package game.model.behavior;

import game.model.BlockType;
import game.model.IBlockManual;

public abstract class AShapeBehavior implements IBlockManual {
	protected BlockType type;
	public AShapeBehavior(BlockType type) {
		this.type = type;
	}
	@Override
	public BlockType getTypeManual() {
		return type;
	}

	public void setTypeManual(BlockType t) {
		this.type = t;
	};
}
