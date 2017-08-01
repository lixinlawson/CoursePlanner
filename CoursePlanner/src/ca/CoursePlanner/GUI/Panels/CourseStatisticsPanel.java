/**
 * CourseStatisticsPanel is used to display the histogram for the statistics
 * 
 * @author Lawson Li
 */

package ca.CoursePlanner.GUI.Panels;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.CoursePlanner.Model.CoursePlanner;
import ca.CoursePlanner.Model.Histogram.Histogram;
import ca.CoursePlanner.Model.Histogram.HistogramIcon;

@SuppressWarnings("serial")
public class CourseStatisticsPanel extends BasePanel {

	private static final int GRAM_WIDTH = 240;
	private static final int GRAM_HEIGHT = 150;
	private static final int NUM_OF_CAMPUS_RECORD = 4;
	private static final int NUM_OF_SEMESTER = 3;
	private int[] semesterStat = {};
	private int[] campusStat = {};
	private JLabel subTitle = new JLabel("Course: ");
	Histogram semesterGram;
	Histogram campusGram;

	public CourseStatisticsPanel(CoursePlanner planner) {
		super(planner);
		addTitle("Statistics");
		addMainPanel(makeMainContentPanel());
		preventResizingVertically();
	}

	@Override
	protected JPanel makeMainContentPanel() {
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.add(subTitle);
		main.add(new JLabel(" "));
		main.add(new JLabel("Semester offerings:"));
		main.add(makeSemesterStatGram());
		main.add(new JLabel("(0 = Spring, 1 = Summer, 2 = Fall)"));
		main.add(new JLabel(" "));
		main.add(new JLabel("Campus offerings:"));
		main.add(makeCampusStatGram());
		main.add(new JLabel("(0 = BBY, 1 = SRY, 2 = VAN, 3 = Others)"));
		return main;
	}

	private JLabel makeSemesterStatGram() {
		semesterGram = new Histogram(semesterStat, NUM_OF_SEMESTER);
		final JLabel label = new JLabel(new HistogramIcon(semesterGram,
				GRAM_HEIGHT, GRAM_WIDTH));
		semesterGram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				label.repaint();
			}
		});
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		return label;
	}

	private JLabel makeCampusStatGram() {
		campusGram = new Histogram(campusStat, NUM_OF_CAMPUS_RECORD);
		final JLabel label = new JLabel(new HistogramIcon(campusGram,
				GRAM_HEIGHT, GRAM_WIDTH));
		campusGram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				label.repaint();
			}
		});
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		return label;
	}

	@Override
	public void updatePanel() {
		subTitle.setText("Course: " + getCourseName());
		semesterStat = getSemesterStat();
		campusStat = getCampusStat();
		semesterGram.setData(semesterStat);
		campusGram.setData(campusStat);
		repaint();
	}
}
