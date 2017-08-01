/**
 * CoursePlannerGUI is the main GUI for course planner.
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.GUI;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.CoursePlanner.GUI.Panels.*;
import ca.CoursePlanner.Model.CoursePlanner;

@SuppressWarnings("serial")
public class CoursePlannerGUI extends JFrame {

	private static final String DATA_FILE_PATH = "data\\course_data_2014.csv";
	private CoursePlanner planner;

	public CoursePlannerGUI(CoursePlanner planner) {
		super("FAS Course Planner");
		this.planner = planner;
		setLayout(new BorderLayout());

		add(makeLeftPanel(), BorderLayout.WEST);
		add(makeRightPanel(), BorderLayout.EAST);
		add(new CourseSemesterPanel(planner), BorderLayout.CENTER);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JPanel makeLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(new CourseListFilterPanel(planner));
		leftPanel.add(new CourseListPanel(planner));

		leftPanel.add(Box.createVerticalGlue());
		return leftPanel;
	}

	private JPanel makeRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		rightPanel.add(new CourseStatisticsPanel(planner));
		rightPanel.add(new CoursesDetailsPanel(planner));

		rightPanel.add(Box.createVerticalGlue());
		return rightPanel;
	}

	public static void main(String[] arg) {
		File file = new File(DATA_FILE_PATH);
		if (file.exists()) {
			new CoursePlannerGUI(new CoursePlanner(file));
		} else {
			new FileNotFound(file);
		}
	}

}
