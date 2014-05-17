package org.agh.map.managament;

import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;

import android.graphics.Color;

public class GlobalState  {
	private int notVisitedColor = Color.WHITE;
	private int visitedColor = Color.BLUE;
	private int nextColor = Color.RED;
	private int lineColor = Color.GREEN;
	private int textColor = Color.MAGENTA;
	private STYLE style = (STYLE) SimpleMarkerSymbol.STYLE.DIAMOND;
	private int textSize = 10;

	public int getNotVisitedColor() {
		return notVisitedColor;
	}

	public void setNotVisitedColor(int notVisitedColor) {
		this.notVisitedColor = notVisitedColor;
	}

	public int getVisitedColor() {
		return visitedColor;
	}

	public void setVisitedColor(int visitedColor) {
		this.visitedColor = visitedColor;
	}

	public int getNextColor() {
		return nextColor;
	}

	public void setNextColor(int nextColor) {
		this.nextColor = nextColor;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public STYLE getStyle() {
		return style;
	}

	public void setStyle(STYLE style) {
		this.style = style;
	}
}
