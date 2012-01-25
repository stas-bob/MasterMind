package de.stas.mm.board;

import android.graphics.Color;

public class PinColor extends Color {
	public static final int ORANGE = 0xFFC86400;
	public static final int PURPLE = 0xFFC800C8;
	
	public static int[] getAllColors() {
		return new int[] {ORANGE, PURPLE, RED, BLUE, CYAN, YELLOW, BLACK, GREEN};
	}
}
