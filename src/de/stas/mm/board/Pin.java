package de.stas.mm.board;



public class Pin {
	private int color;
	
	public Pin(int color) {
		this.color = color;
	}
	
	public boolean equalColor(Pin otherPin) {
		if (color == otherPin.getColor()) {
			return true;
		}
		return false;
	}
	
	
	public int getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		switch (color) {
		case PinColor.RED: return "red";
		case PinColor.YELLOW: return "yellow";
		case PinColor.BLUE: return "blue";
		case PinColor.MAGENTA: return "magenta";
		case PinColor.GREEN: return "green";
		case PinColor.BLACK: return "black";
		case PinColor.WHITE: return "white";
		case PinColor.ORANGE: return "orange";
		case PinColor.PURPLE: return "purple";
		case PinColor.CYAN: return "cyan";
		default : return color + "";
		}
	}

}
