package de.stas.mm.board;


import java.util.LinkedList;

import de.stas.mm.InvalidLineException;
import de.stas.mm.LineEmptyException;
import de.stas.mm.LineNotFullException;
import de.stas.mm.LoseException;


public class Board {
	private Line[] lines;
	private int lastLine;
	private Line secretLine;
	private int linesCount;
	private int pinsCount;
	
	public Board(int linesCount, int pinsCount) {
		lines = new Line[linesCount];
		this.pinsCount = pinsCount;
		this.linesCount = linesCount;
    	secretLine = new Line();
    	LinkedList<Integer> setPins = new LinkedList<Integer>();
    	int index = 0;
    	while (index < pinsCount) {
    		int randomIndex = (int) (Math.random()*PinColor.getAllColors().length);
    		if (setPins.contains(randomIndex)) continue;
    		secretLine.setPin(new Pin(PinColor.getAllColors()[randomIndex]), index++);
    		setPins.add(randomIndex);
    	}
    	System.out.println(secretLine);
	}

	public void setPin(Pin pin, int linePosition, int pinPosition) throws InvalidLineException {
		if (linePosition == lastLine) {
			if (lines[lastLine] == null) {
				lines[lastLine] = new Line();
			}
			lines[lastLine].setPin(pin, pinPosition);
		} else {
			throw new InvalidLineException();
		}
	}
	
	public Pin[] testCodeLineWithSecretLine(Line lastLine, Line secretLine) throws LineNotFullException {
		if (!lastLine.lineIsFull()) {
			throw new LineNotFullException();
		}
		int[] hintPinRandomPositions = new int[pinsCount];
		for (int i = 0; i < hintPinRandomPositions.length; i++) {
			hintPinRandomPositions[i] = -1;
		}
		Pin[] hintPins = new Pin[pinsCount];
		final int sameColor = 1;
		int samePosition = 2;
		final int samePositionFlag = 3;
		int hintPinColor = 0;
		Pin[] secretPins = secretLine.getPins();
		Pin[] lastLinePins = lastLine.getPins();
		loop:for (int i = 0; i < secretPins.length; i++) {
			for (int k = i - 1; k >= 0; k--) {
				if (secretPins[k].equalColor(secretPins[i])) {
					continue loop;
				}
			}
			for (int j = 0; j < lastLinePins.length; j++) {
				if (secretPins[i].equalColor(lastLinePins[j])) {
					hintPinColor |= sameColor;
					if (i == j) {
						hintPinColor |= samePosition;
					}
				}
				Pin hintPin;
				int color;
				int position;
				switch(hintPinColor) {
				case sameColor :
					color = PinColor.WHITE;
					position = newRandomPosition(hintPinRandomPositions);
					hintPin = new Pin(color);
					hintPins[position] = hintPin; 
					hintPinColor = 0;
					break;
				case samePositionFlag:
					color = PinColor.RED;
					position = newRandomPosition(hintPinRandomPositions);
					hintPin = new Pin(color);
					hintPins[position] = hintPin; 
					hintPinColor = 0;
					break;
				}
			}
		}
		return hintPins;
	}

	private int newRandomPosition(int[] positions) {
		int randomPos = (int)(Math.random() * pinsCount);
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] == randomPos) {
				randomPos = (int)(Math.random() * pinsCount);
				i = -1;
			}
		}
		for (int i = 0; i < positions.length; i++) {
			if (positions[i] == -1) {
				positions[i] = randomPos;
				break;
			}
		}
		return randomPos;
	}
	
	public boolean testCodeLineWithSecretLine() throws LineNotFullException, LineEmptyException, LoseException {
		if (lines[lastLine] != null) {
			Pin[] hintPins = testCodeLineWithSecretLine(lines[lastLine], secretLine);
			lines[lastLine].setHintPins(hintPins);
			if (lines[lastLine].isCorrect()) {
				return true;
			}
			lastLine++;
			if (lastLine == linesCount) {
				throw new LoseException();
			}
		} else {
			throw new LineEmptyException();
		}
		return false;
	}

	public Line[] getLines() {
		return lines;
	}
	
	public Line getSecretLine() {
		return secretLine;
	}
}
