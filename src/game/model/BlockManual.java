package game.model;

import game.model.behavior.*;

public enum BlockManual {
	I(new IShapeBehavior(BlockType.I)), L(new LShapeBehavior(BlockType.L)), SQUARE(new SquareShapeBehavior(BlockType.SQUARE)),  T(new TShapeBehavior(BlockType.T)
					), Z(new ZicZacShapeBehavior(BlockType.ZICZAC));
	private IBlockManual behavior;

	BlockManual(IBlockManual m) {
		this.behavior = m;
	}

	public IBlockManual getBehavior() {
		return this.behavior;
	}

}
