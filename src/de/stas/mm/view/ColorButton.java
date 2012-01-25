package de.stas.mm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.Button;
import de.stas.mm.R;
import de.stas.mm.board.PinColor;

public class ColorButton extends Button {
	private int color;
	private int percent;
	private Bitmap copy;
	private boolean selected;
	
	public ColorButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.colorButton);
		int color_ = ta.getInt(R.styleable.colorButton_color, 0);
		switch (color_) {
		case 0: color = PinColor.GREEN; break;
		case 1: color = PinColor.YELLOW; break;
		case 2: color = PinColor.ORANGE; break;
		case 3: color = PinColor.PURPLE; break;
		case 4: color = PinColor.BLUE; break;
		case 5: color = PinColor.BLACK; break;
		case 6: color = PinColor.RED; break;
		case 7: color = PinColor.CYAN; break;
		default : color = PinColor.WHITE;
		}
		percent = ta.getInt(R.styleable.colorButton_percent, 0);
		ta.recycle();
	}
	
	@Override
	public void onMeasure(int w, int h) {
		WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
		int width = (int) (wm.getDefaultDisplay().getWidth() * 0.2);
		int height = (int) (wm.getDefaultDisplay().getHeight() * 0.08);
		super.onMeasure(width, height);
		setMeasuredDimension(width, height);
	}
	
	public int getColor() {
		return color;
	}
	
	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
		buildDrawingCache();
		copy = getDrawingCache();
		if (selected) {
			Paint borderPaint = new Paint();
			borderPaint.setAntiAlias(true);
			borderPaint.setStyle(Style.STROKE);
			borderPaint.setColor(Color.GRAY);
			borderPaint.setStrokeWidth(3);
			borderPaint.setPathEffect(new DashPathEffect(new float[] {4, 4}, 2));
			RectF borderRect = new RectF();
			borderRect.left = 0;
			borderRect.top = 0;
			borderRect.right = getMeasuredWidth();
			borderRect.bottom = getMeasuredHeight() - 8;
			c.drawRoundRect(borderRect, 10, 10, borderPaint);
		}
		Bitmap toned = colorTone(color, percent, copy);
		c.drawBitmap(toned, 0, 0, new Paint());
	}
	
	public void select(boolean select, boolean invalidate) {
		this.selected = select;
		if (invalidate) {
			invalidate();
		}
	}
	
	@Override
	public boolean isSelected() {
		return selected;
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
	private Bitmap colorTone(int color, int percent, Bitmap src) {
		Bitmap result = src.copy(Config.ARGB_8888, true);
		int width = src.getWidth();
		int height = src.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixel = src.getPixel(i, j);
				int blueSrc = pixel & 0xFF;
				int greenSrc = (pixel & 0xFF00) >> 8;
				int redSrc = (pixel & 0xFF0000) >> 16;
				int alpha = (pixel & (0xFF << 24));
				if (blueSrc == greenSrc && greenSrc == redSrc && redSrc == 0) continue;
				
				int blue = color & 0xFF;
				int green = (color & 0xFF00) >> 8;
				int red = (color & 0xFF0000) >> 16;

				int blueDiff = blueSrc - blue;
				int greenDiff = greenSrc - green;
				int redDiff = redSrc - red;
				
				blueDiff = (int)(blueDiff / 100.0 * percent);
				greenDiff = (int)(greenDiff / 100.0 * percent);
				redDiff = (int)(redDiff / 100.0 * percent);
				
				blueSrc -= blueDiff;
				greenSrc -= greenDiff;
				redSrc -= redDiff;
				
				pixel = alpha | redSrc << 16 | greenSrc << 8 | blueSrc;
				result.setPixel(i, j, pixel);
			}
		}
		return result;
	}
}
