package game.helper;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorHelper {
	public static final String VERTICAL_DECOREATE = "a";
	public static final String HORIZONTAL_DECOREATE = "b";
	public static final String CIRCLE_DECOREATE = "c";
	public static final String BACKGROUND = "d";
	public static final String FADED_BACKGROUND = "e";
	static Map<String, MaterialDesignColor> container;
	static {
		container = new HashMap<>();
		container.put(VERTICAL_DECOREATE, MaterialDesignColor.BLACK);
		container.put(HORIZONTAL_DECOREATE, MaterialDesignColor.BLACK);
		container.put(CIRCLE_DECOREATE, MaterialDesignColor.RED);
		container.put(BACKGROUND, MaterialDesignColor.BROWN);
		container.put(FADED_BACKGROUND, MaterialDesignColor.FADED_BROWN);

	}

	public static Color getColor(String name) {
		return container.get(name.toLowerCase()).c;
	}

	enum MaterialDesignColor {
		BROWN(new Color(63, 39, 35)), BLUE_GRAY(new Color(176, 190, 197)), GREY(new Color(189, 189, 189)), BLACK(
				new Color(0, 0, 0)), RED(new Color(216, 67, 21)) , FADED_BROWN(new Color(60 , 30 , 30));
		private Color c;

		private MaterialDesignColor(Color c) {
			this.c = c;
		}

	}
}
