package org.xbmc.android.remote.presentation.widget;

import org.xbmc.android.util.ImportUtilities;
import org.xbmc.api.business.IManager;
import org.xbmc.api.type.ThumbSize;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;

public class FiveLabelsItemView extends AbstractItemView {
	
	private final static int POSTER_WIDTH = ThumbSize.getPixel(ThumbSize.SMALL);;
	private final static int POSTER_HEIGHT = (int)(POSTER_WIDTH * ImportUtilities.POSTER_AR);
	private final static Rect POSTER_RECT = new Rect(0, 0, POSTER_WIDTH, POSTER_HEIGHT);
	
	public String subtitle;
	public String subtitleRight;
	public String bottomtitle;
	public String bottomright;
	
	public FiveLabelsItemView(Context context, IManager manager, int width, Bitmap defaultCover, Drawable selection) {
		super(context, manager, width, defaultCover, selection);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(mWidth, POSTER_HEIGHT);
	}
	
	protected void onDraw(Canvas canvas) {
		PAINT.setTextAlign(Align.LEFT);
		PAINT.setColor(Color.WHITE);
		PAINT.setFakeBoldText(false);
		final int width = mWidth;
		PAINT.setTextAlign(Align.LEFT);
		final boolean isSelected = isSelected() || isPressed();
		
		drawPoster(canvas, POSTER_WIDTH, POSTER_HEIGHT, width);
		
		PAINT.setAntiAlias(true);
		if (title != null) {
			PAINT.setColor(isSelected ? Color.WHITE : Color.BLACK);
			PAINT.setTextSize(SIZE18);
			canvas.drawText(title, POSTER_WIDTH + PADDING, SIZE25, PAINT);
		}
		PAINT.setColor(isSelected ? Color.WHITE : Color.rgb(80, 80, 80));
		PAINT.setTextSize(SIZE12);
		
		PAINT.setTextAlign(Align.RIGHT);
		float subtitleRightWidth = 0;
		if (subtitleRight != null) {
			subtitleRightWidth = PAINT.measureText(subtitleRight);
			canvas.drawText(subtitleRight, width - PADDING, SIZE42, PAINT);
		}
		float bottomrightWidth = 0;
		if (bottomright != null) {
			PAINT.setTextSize(SIZE20);
			PAINT.setFakeBoldText(true);
			PAINT.setColor(isSelected ? Color.WHITE : Color.argb(68, 0, 0, 0));
			bottomrightWidth = PAINT.measureText(subtitleRight);
			canvas.drawText(bottomright, width - PADDING, SIZE65, PAINT);
		}
		
		PAINT.setColor(isSelected ? Color.WHITE : Color.rgb(80, 80, 80));
		PAINT.setTextSize(SIZE12);
		PAINT.setTextAlign(Align.LEFT);
		PAINT.setFakeBoldText(false);
		if (subtitle != null) {
			canvas.drawText(ellipse(subtitle, width - (int)subtitleRightWidth - SIZE50), SIZE55, SIZE42, PAINT);
		}
		if (bottomtitle != null) {
			canvas.drawText(ellipse(bottomtitle, width - (int)bottomrightWidth - SIZE50), SIZE55, SIZE59, PAINT);
		}

	}

	@Override
	protected Rect getPosterRect() {
		return POSTER_RECT;
	}
}