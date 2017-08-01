/**
 * CoursesDetailsPanel is used to display the details for selected course
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.GUI.Panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.CoursePlanner.Model.CoursePlanner;
import ca.CoursePlanner.Model.DataSet.Course;
import ca.CoursePlanner.Model.DataSet.SectionType;

@SuppressWarnings("serial")
public class CoursesDetailsPanel extends BasePanel {

	private static final int MAX_LENGTH_DISPLAY = 20;
	private static final int NUM_OF_ROWS_FOR_SECTION = 5;
	private static final int NUM_OF_ROWS_FOR_COURSE = 4;
	private static final int NUM_OF_COL = 2;

	private Course course;

	private String[] itemName = { "Course Name: ", "Location: ", "Semester",
			"Instructors: " };

	private String[] details = new String[NUM_OF_ROWS_FOR_COURSE];
	private ArrayList<String> sections = new ArrayList<String>();
	private ArrayList<String> enroll = new ArrayList<String>();

	public CoursesDetailsPanel(CoursePlanner planner) {
		super(planner);
		course = getCourseForDetail();
		addTitle("Details Of Course Offering");
		addMainPanel(makeMainContentPanel());
		preventResizingVertically();
	}

	@Override
	protected JPanel makeMainContentPanel() {
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.add(makePanelForCourseDetails());
		main.add(makePanelForSectionDetails());
		return main;
	}

	private JPanel makePanelForCourseDetails() {
		JPanel main = new JPanel();
		setDetailsForCourse();
		main.setLayout(new GridLayout(NUM_OF_ROWS_FOR_COURSE, NUM_OF_COL));
		for (int row = 0; row < NUM_OF_ROWS_FOR_COURSE; row++) {
			for (int col = 0; col < NUM_OF_COL; col++) {
				if (col == 0) {
					main.add(new JLabel(itemName[row]));
				} else {
					JLabel content = new JLabel();
					content.setFocusable(true);
					content.setText(details[row]);
					content.setBorder(BorderFactory
							.createLineBorder(Color.BLUE));
					main.add(content);
				}
			}
		}

		return main;
	}

	private void setDetailsForCourse() {
		if (course != null) {
			details[0] = course.getCourseName();
			details[1] = course.getLocation();
			details[2] = String.format("%d", course.getSemesterCode());
			details[3] = course.getInstructors();
			if (details[3].length() > MAX_LENGTH_DISPLAY) {
				details[3] = details[3].substring(0, MAX_LENGTH_DISPLAY);
			}

		} else {
			details[0] = "";
			details[1] = "";
			details[2] = "";
			details[3] = "";
		}
	}

	private JPanel makePanelForSectionDetails() {
		JPanel section = new JPanel();
		setSectionAndEnroll();
		section.setLayout(new GridLayout(NUM_OF_ROWS_FOR_SECTION, NUM_OF_COL));
		for (int row = 0; row < NUM_OF_ROWS_FOR_SECTION; row++) {
			for (int col = 0; col < NUM_OF_COL; col++) {
				JLabel content = new JLabel(" ");
				if (course != null) {
					if (col == 0) {
						content.setText(sections.get(row));
					} else {
						content.setText(enroll.get(row));
					}
				}
				content.setBorder(BorderFactory.createEmptyBorder());
				section.add(content);
			}
		}
		return section;
	}

	private void setSectionAndEnroll() {
		if (course != null) {
			sections.add("Section Type");
			enroll.add("Enrollment (filled/cap)");
			for (SectionType sec : course) {
				sections.add(sec.getSectionType());
				enroll.add(sec.getEnrollment());
			}
			while (sections.size() < NUM_OF_ROWS_FOR_SECTION) {
				sections.add(" ");
				enroll.add(" ");
			}
		}
	}

	@Override
	public void updatePanel() {
		removeAll();
		course = getCourseForDetail();
		sections = new ArrayList<String>();
		enroll = new ArrayList<String>();
		addTitle("Details Of Course Offering");
		addMainPanel(makeMainContentPanel());
		revalidate();
		repaint();
	}

}
