/**
 * The HistogramIcon class is used to draw the histogram icon
 * 
 * @author Lawson Li
 */

package ca.CoursePlanner.Model.Histogram;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class HistogramIcon implements Icon {

	private static final Color AXIS_COLOR = Color.BLACK;
	private static final Color BAR_COLOR = Color.BLUE;
	private static final Color BACKGROUND_COLOR = Color.WHITE;

	private static final int MARGIN = 15;
	private static final int MARGIN_TWICE = MARGIN * 2;
	private static final int MARGIN_FOUR_TIMES = MARGIN * 4;

	private static final int BAR_SPACING = 2;
	private static final int BAR_SPACING_TWICE = BAR_SPACING * 2;

	private static final int FACTOR_FIX_COUNT_POS = 3;
	private static final int EVEN_FACTOR = 2;

	private Histogram histogram;
	private int iconHeight;
	private int iconWidth;

	public HistogramIcon(Histogram histogram, int iconHeight, int iconWidth) {
		this.histogram = histogram;
		this.iconHeight = iconHeight;
		this.iconWidth = iconWidth;
	}

	@Override
	public int getIconHeight() {
		return iconHeight;
	}

	@Override
	public int getIconWidth() {
		return iconWidth;
	}

	@Override
	public void paintIcon(Component comp, Graphics graph, int x, int y) {
		Graphics2D graph2D = (Graphics2D) graph;
		graph2D.setBackground(BACKGROUND_COLOR);

		int maxBarWidth = getMaxWidthForBars();
		int maxBarHeight = getMaxHeightForBars();

		drawAxis(graph2D, maxBarWidth, maxBarHeight);
		if (histogram.getMaxBarCount() > 0) {
			drawBarsAndText(graph2D, maxBarWidth, maxBarHeight);
		}
	}

	private int getMaxHeightForBars() {
		return iconHeight - MARGIN_FOUR_TIMES;
	}

	private int getMaxWidthForBars() {
		return iconWidth - MARGIN_TWICE - BAR_SPACING;
	}

	private void drawBarsAndText(Graphics2D graph2D, int maxBarWidth,
			int maxBarHeight) {

		int numberOfBars = histogram.getNumberBars();
		int maxBarCount = histogram.getMaxBarCount();

		int unitHeight = getUnitHeight(maxBarHeight, maxBarCount);
		int barWidth = getBarWidth(maxBarWidth, numberOfBars);

		int barCounter = 0; // counter help to determine the minRangeYpos
		int posX = MARGIN + BAR_SPACING_TWICE;// initial x position

		// iterate the bars in histogram and draw the bar and the text
		for (Histogram.Bar bar : histogram) {
			int barHeight = bar.getCount() * unitHeight;
			int posY = maxBarHeight + MARGIN - barHeight;
			int minRangeYpos = getTextForMinRangePosY(maxBarHeight, barCounter);

			graph2D.setColor(BAR_COLOR);
			drawTheBar(graph2D, posX, posY, barWidth, barHeight);

			graph2D.setColor(AXIS_COLOR);
			drawTheCountNum(graph2D, posX, posY, bar, barWidth);
			drawTheMinRangeNum(graph2D, posX, minRangeYpos, bar, barWidth);

			posX = posX + BAR_SPACING + barWidth; // get x position for next bar
			barCounter++;
		}
	}

	private void drawTheBar(Graphics2D graph2D, int posX, int posY,
			int barWidth, int barHeight) {
		Rectangle2D.Double barIcon;
		barIcon = new Rectangle2D.Double(posX, posY, barWidth, barHeight);
		graph2D.fill(barIcon);
	}

	private void drawTheCountNum(Graphics2D graph2D, int posX, int posY,
			Histogram.Bar bar, int barWidth) {
		graph2D.drawString(String.format("%d", bar.getCount()), posX + barWidth
				/ FACTOR_FIX_COUNT_POS, posY - BAR_SPACING);
	}

	private void drawTheMinRangeNum(Graphics2D graph2D, int posX,
			int minRangeYpos, Histogram.Bar bar, int barWidth) {
		graph2D.drawString(String.format("%d", bar.getRangeMin()), posX
				+ barWidth / FACTOR_FIX_COUNT_POS, minRangeYpos);
	}

	private int getTextForMinRangePosY(int maxBarHeight, int barCounter) {
		int minRangeYpos = maxBarHeight + MARGIN;
		if (barCounter % EVEN_FACTOR == 0) {
			minRangeYpos += MARGIN;
		} else {
			minRangeYpos += EVEN_FACTOR * MARGIN;
		}
		return minRangeYpos;
	}

	private int getBarWidth(int maxBarWidth, int numberOfBars) {
		return (maxBarWidth - BAR_SPACING * numberOfBars) / numberOfBars;
	}

	private int getUnitHeight(int maxBarHeight, int maxBarCount) {
		int unitHeight;
		if (maxBarCount == 0) {
			unitHeight = maxBarHeight;
		} else {
			unitHeight = maxBarHeight / maxBarCount;
		}
		return unitHeight;
	}

	private void drawAxis(Graphics2D graph2D, int maxBarWidth, int maxBarHeight) {
		graph2D.setColor(AXIS_COLOR);
		/*
		 * The drawLine() can also do this job I choose to use Rectangle2D
		 * because I want thicker axis
		 */
		Rectangle2D.Double xAxis;
		Rectangle2D.Double yAxis;

		int lineWeight = BAR_SPACING;
		int xAxisLength = maxBarWidth;
		int yAxisLength = maxBarHeight;

		xAxis = new Rectangle2D.Double(MARGIN, MARGIN + yAxisLength,
				xAxisLength, lineWeight);
		yAxis = new Rectangle2D.Double(MARGIN, MARGIN, lineWeight, yAxisLength);

		graph2D.fill(xAxis);
		graph2D.fill(yAxis);
	}

}
