package de.stas.mm.view;

import java.util.LinkedList;


public class ColorButtonGroup {
	private LinkedList<ColorButton> buttons = new LinkedList<ColorButton>();
	private ColorButton selectedButton;
	
	public void add(ColorButton button) {
		buttons.add(button);
	}
	
	public void select(int color) throws Exception {
		boolean found = false;
		for (ColorButton button : buttons) {
			if (button.getColor() == color) {
				if (button.isSelected()) {
					button.select(true, false);
				} else {
					button.select(true, true);
				}
				selectedButton = button;
				found = true;
			} else {
				if (button.isSelected()) {
					button.select(false, true);
				} else {
					button.select(false, false);
				}
			}
		}
		if (!found) throw new Exception("color not available");
	}
	
	public ColorButton getSelectedColorButton() {
		return selectedButton; 
	}
}
