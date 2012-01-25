package de.stas.mm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import de.stas.mm.InvalidLineException;
import de.stas.mm.R;
import de.stas.mm.activity.Main;
import de.stas.mm.board.Board;
import de.stas.mm.board.Line;
import de.stas.mm.board.Pin;
import de.stas.mm.board.PinColor;

public class BoardView extends View {
	private Board board;
	private Main main;
	private int viewHeight;
	private int viewWidth;
	private int cellWidth;
	private int cellHeight;
	private int boardRightPadding;
	private int pinTopPadding;
	private int pinBottomPadding;
	private int pinLeftPadding;
	private int pinRightPadding;
	private int squarePadding;
	private int hintPinPadding;
	
	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void onMeasure(int w, int h) {

		super.onMeasure(w, h);
		setMeasuredDimension(w, h);
		viewHeight = getMeasuredHeight();
		viewWidth = getMeasuredWidth();
		boardRightPadding = 60;
		pinTopPadding = 10;
		pinBottomPadding = pinTopPadding;
		pinLeftPadding = 15;
		pinRightPadding = pinLeftPadding;
		cellWidth = (viewWidth - boardRightPadding) / 4;
		cellHeight = viewHeight/10;
		squarePadding = 10;
		hintPinPadding = 15;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		int selectedColor = main.getSelectedColor();
		int pinPosition = (int) (me.getX()/cellWidth);
		int linePosition = (int) ((viewHeight - me.getY())/cellHeight);
		try {
			if (pinPosition < 4) 
				board.setPin(new Pin(selectedColor), linePosition, pinPosition);
		} catch (InvalidLineException e) {
			main.handleError(e);
		}
		invalidate();
		return false;
	}
	
	@Override
	public void onDraw(Canvas c) {
		Paint linePaint = new Paint();
		Paint pinPaint = new Paint();
		Paint holePaint = new Paint();
		RectF rect = new RectF();
		holePaint.setColor(Color.rgb(140, 90, 60));
		linePaint.setStyle(Style.STROKE);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				rect.bottom = (9 - i) * cellHeight + cellHeight - pinBottomPadding;
				rect.top = (9 - i) * cellHeight + pinTopPadding;
				rect.left = j * cellWidth + pinLeftPadding;
				rect.right = j * cellWidth + cellWidth - pinRightPadding;
				c.drawArc(rect, 0, 360, false, linePaint);
				rect.bottom = (9 - i) * cellHeight + cellHeight - pinBottomPadding - 1;
				rect.top = (9 - i) * cellHeight + pinTopPadding + 1;
				rect.left = j * cellWidth + pinLeftPadding + 1;
				rect.right = j * cellWidth + cellWidth - pinRightPadding - 1;
				c.drawArc(rect, 0, 360, false, holePaint);
			}
			c.drawRect(new Rect(viewWidth - boardRightPadding + squarePadding, (9 - i) * cellHeight + squarePadding, viewWidth - squarePadding, (9 - i) * cellHeight + cellHeight - squarePadding), linePaint);
		}
		Line[] lines = board.getLines(); 
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] != null) {
				Pin[] hintPins = lines[i].getHinPins();
				if (hintPins != null) {
					if (hintPins[0] != null) {
						//|_|_|
						//|x|_|
						pinPaint.setColor(hintPins[0].getColor());
						rect.bottom = (9 - i) * cellHeight + cellHeight - hintPinPadding; 
						rect.top = (9 - i) * cellHeight + cellHeight/2; 
						rect.left = viewWidth - boardRightPadding + hintPinPadding;
						rect.right = viewWidth - boardRightPadding/2;
						c.drawArc(rect, 0, 360, false, pinPaint);
					}
					if (hintPins[1] != null) {
						//|x|_|
						//|_|_|
						pinPaint.setColor(hintPins[1].getColor());
						rect.bottom = (9 - i) * cellHeight + cellHeight/2;
						rect.top = (9 - i) * cellHeight + hintPinPadding;
						rect.left = viewWidth - boardRightPadding + hintPinPadding;
						rect.right = viewWidth - boardRightPadding/2;
						c.drawArc(rect, 0, 360, false, pinPaint);
					}
					if (hintPins[2] != null) {
						//|_|_|
						//|_|x|
						pinPaint.setColor(hintPins[2].getColor());
						rect.bottom = (9 - i) * cellHeight + cellHeight - hintPinPadding;
						rect.top = (9 - i) * cellHeight + cellHeight/2;
						rect.left = viewWidth - boardRightPadding/2;
						rect.right = viewWidth - hintPinPadding;
						c.drawArc(rect, 0, 360, false, pinPaint);
					}
					if (hintPins[3] != null) {
						//|_|x|
						//|_|_|
						pinPaint.setColor(hintPins[3].getColor());
						rect.bottom = (9 - i) * cellHeight + cellHeight/2;
						rect.top = (9 - i) * cellHeight + hintPinPadding;
						rect.left = viewWidth - boardRightPadding/2;
						rect.right = viewWidth - hintPinPadding;
						c.drawArc(rect, 0, 360, false, pinPaint);
					}
					if (hintPins[0] == hintPins[1] && hintPins[1] == hintPins[2] && hintPins[2] == hintPins[3] && hintPins[3] == null) {
						Paint textPaint = new Paint();
						textPaint.setAntiAlias(true);
						textPaint.setTextSize(20);
						textPaint.setColor(PinColor.RED);
						Rect rectt = new Rect();
						textPaint.getTextBounds("zero", 0, "zero".length(), rectt);
						int textWidth = rectt.width();
						int centerDiff = boardRightPadding/2 - textWidth/2;
						c.drawText("zero", viewWidth - boardRightPadding + centerDiff, (9 - i) * cellHeight + cellHeight/2 + textPaint.getTextSize()/2, textPaint);
					}
				}
				Pin[] pins = lines[i].getPins();
				for (int j = 0; j < pins.length; j++) {
					if (pins[j] != null) {
						int bitmapResId = -1;
						switch (pins[j].getColor()) {
						case PinColor.BLACK: bitmapResId = R.drawable.black; break;
						case PinColor.BLUE: bitmapResId = R.drawable.blue; break;
						case PinColor.RED: bitmapResId = R.drawable.red; break;
						case PinColor.CYAN: bitmapResId = R.drawable.cyan; break;
						case PinColor.YELLOW: bitmapResId = R.drawable.yellow; break;
						case PinColor.ORANGE: bitmapResId = R.drawable.orange; break;
						case PinColor.GREEN: bitmapResId = R.drawable.green; break;
						case PinColor.PURPLE : bitmapResId = R.drawable.purple; break;
						}
						rect.bottom = (9 - i) * cellHeight + cellHeight - pinBottomPadding;
						rect.top = (9 - i) * cellHeight + pinTopPadding;
						rect.left = j * cellWidth + pinLeftPadding;
						rect.right = j * cellWidth + cellWidth - pinRightPadding;
						Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(bitmapResId)).getBitmap();
						Bitmap sbmp = Bitmap.createScaledBitmap(bmp, (int)rect.width(), (int)rect.height(), true);
						c.drawBitmap(sbmp, rect.left, rect.top, new Paint());
					}
				}
			}
		}
		super.onDraw(c);
	}
}
