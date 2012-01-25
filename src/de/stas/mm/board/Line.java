package de.stas.mm.board;



public class Line {
	private Pin[] pins = new Pin[4];
	private Pin[] hintPins;
	
	public void setPin(Pin pin, int position) {
		pins[position] = pin;
	}
	
	public boolean lineIsFull() {
		for (int i = 0; i < pins.length; i++) {
			if (pins[i] == null) {
				return false;
			}
		}
		return true;
	}
	
	public Pin[] getPins() {
		return pins;
	}
	
	public Pin[] getHinPins() {
		return hintPins;
	}

	public void setHintPins(Pin[] hintPins) {
		this.hintPins = hintPins;
	}

	public boolean isCorrect() {
		boolean allCorrect = true;
		for (int i = 0; i < hintPins.length; i++) {
			if (hintPins[i] == null || hintPins[i].getColor() == PinColor.WHITE) {
				allCorrect = false;
			}
		}
		return allCorrect;
	}
	
	public String toString() {
		String str = "";
		for (int i = 0; i < pins.length; i++) {
			str += pins[i] + (i < pins.length ? " " : "");
		}
		return str;
	}
}
