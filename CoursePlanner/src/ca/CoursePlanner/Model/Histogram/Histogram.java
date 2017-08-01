/**
 * Histogram class is used to create a iterable histogram object
 * It has an immutable class Bar
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.Histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Histogram implements Iterable<Histogram.Bar> {

	private static final int INDEX_DIFFER = 1;
	private static final int LOWEST_RANGE = 0;
	private int numOfBars;
	private int[] data;
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	public Histogram(int[] data, int numOfBars) {
		setNumberBars(numOfBars);
		setData(data);
	}

	public void setData(int[] data) {
		this.data = Arrays.copyOf(data, data.length);
		notifyListeners();
	}

	public void setNumberBars(int numOfBars) {
		// error handling for 0 or negative input
		if (numOfBars > 0) {
			this.numOfBars = numOfBars;
		} else {
			this.numOfBars = 1;
		}
	}

	public int getNumberBars() {
		return numOfBars;
	}

	public int getMaxBarCount() {
		int maxCount = 0;
		for (Histogram.Bar bar : this) {
			if (maxCount <= bar.getCount()) {
				maxCount = bar.getCount();
			}
		}
		return maxCount;
	}

	public Iterator<Histogram.Bar> iterator() {
		assert numOfBars > 0;
		ArrayList<Bar> listOfBars = getListOfBars();
		return Collections.unmodifiableList(listOfBars).iterator();
	}

	private ArrayList<Bar> getListOfBars() {
		ArrayList<Bar> listOfBars = new ArrayList<Bar>();
		addBars(listOfBars);

		return listOfBars;
	}

	private void addBars(ArrayList<Bar> listOfBars) {
		int minRange = LOWEST_RANGE;
		int maxRange;
		int count = 0;
		for (int i = 0; i < numOfBars; i++) {
			if(data.length > 0){
				count = data[i];
			}
			maxRange = minRange;
			assert minRange <= maxRange;
			listOfBars.add(new Bar(minRange, maxRange, count));
			minRange = maxRange + INDEX_DIFFER;
		}
	}


	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners() {
		if (listeners == null) {
			return;
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}
	public class Bar {
		private int rangeMin;
		private int rangeMax;
		private int height;

		public Bar(int min, int max, int height) {
			this.rangeMin = min;
			this.rangeMax = max;
			this.height = height;
		}

		public int getRangeMin() {
			return rangeMin;
		}

		public int getRangeMax() {
			return rangeMax;
		}

		public int getCount() {
			return height;
		}

		public String toString() {
			return String
					.format("Bar[%d, %d] = %d", rangeMin, rangeMax, height);
		}

	}

}
