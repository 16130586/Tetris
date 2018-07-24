package game.model;

import java.awt.Color;

public class HoldingBlock {
	private BlockType type;
	private Color c;

	public HoldingBlock(BlockType type, Color c) {
		super();
		this.type = type;
		this.c = c;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	public Color getColor() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}


}
