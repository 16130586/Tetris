package game.model;

public enum BlockType {
	DOT(0), I(1), L(2), SQUARE(3), T(4), ZICZAC(5);
	private int type;

	BlockType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}