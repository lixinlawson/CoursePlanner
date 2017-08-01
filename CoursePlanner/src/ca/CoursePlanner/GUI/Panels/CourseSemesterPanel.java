/**
 * CourseSemesterPanel is used to display the courses in semester with buttons
 * 
 * @author Lawson Li
 */

package ca.CoursePlanner.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.CoursePlanner.Model.CoursePlanner;
import ca.CoursePlanner.Model.DataSet.Course;
import ca.CoursePlanner.Model.DataSet.Semester;

@SuppressWarnings("serial")
public class CourseSemesterPanel extends BasePanel {

	private static final int TABLE_SIZE = 600;
	private static final int SFU_FOUND_YEAR = 1965;
	private static final int CURRENT_YEAR = 2014;
	private static final Semester[] semesters = { Semester.SPRING,
			Semester.SUMMER, Semester.FALL };

	private JPanel[][] panelsForButton;
	private int[] years;

	ArrayList<Course> listOfCourse;

	public CourseSemesterPanel(CoursePlanner planner) {
		super(planner);
		addTitle("Course Offerings by Semester");
		addMainPanel(makeMainContentPanel());
	}

	@Override
	protected JPanel makeMainContentPanel() {
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setPreferredSize(new Dimension(TABLE_SIZE, TABLE_SIZE));
		main.add(makePanelForSemesters(), BorderLayout.NORTH);
		main.add(makePanelForYears(), BorderLayout.WEST);
		main.add(makePanelForButtons(), BorderLayout.CENTER);
		return main;
	}

	private JPanel makePanelForButtons() {
		JPanel courseButton = new JPanel();
		courseButton.setLayout(new GridBagLayout());
		setPanelsForButtons();
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		for (int y = 0; y < years.length; y++) {
			for (int x = 0; x < semesters.length; x++) {
				c.gridx = x;
				c.gridy = y;
				courseButton.add(panelsForButton[y][x], c);
				if (listOfCourse != null) {
					for (Course course : listOfCourse) {
						assert course == null;
						int yearOfCourse = course.getYear();
						Semester semOfCourse = course.getSemester();
						boolean sameSemester = semOfCourse == semesters[x];
						boolean sameYear = yearOfCourse == years[y];
						if (sameYear && sameSemester) {
							addButtonToPanelInGridBag(y, x, course);
						}
					}
				}

			}
		}
		return courseButton;
	}

	private void addButtonToPanelInGridBag(int y, int x, final Course course) {
		JPanel temp = panelsForButton[y][x];
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		String textOnButton = course.getCourseName() + " "
				+ course.getLocation();
		JButton button = new JButton(textOnButton);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setCourseForDetail(course);
			}
		});
		buttonPanel.add(button, BorderLayout.NORTH);
		temp.add(buttonPanel);
	}

	private void setPanelsForButtons() {
		panelsForButton = new JPanel[years.length][semesters.length];
		for (int x = 0; x < semesters.length; x++) {
			for (int y = 0; y < years.length; y++) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panelsForButton[y][x] = panel;
			}
		}
	}

	private JPanel makePanelForYears() {
		JPanel year = new JPanel();
		year.setBackground(Color.WHITE);
		year.setLayout(new GridBagLayout());
		setYears();
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.ipadx = 20;
		for (int i = 0; i < years.length; i++) {
			c.gridy = i;
			year.add(new JLabel(String.format("%d", years[i])), c);
		}
		return year;
	}

	private JPanel makePanelForSemesters() {
		JPanel semester = new JPanel();
		semester.setBackground(Color.WHITE);
		semester.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 20;
		c.weightx = 1;
		c.gridx = 0;
		semester.add(new JLabel("Spring"), c);
		c.gridx++;
		semester.add(new JLabel("Summer"), c);
		c.gridx++;
		semester.add(new JLabel("Fall"), c);
		return semester;
	}

	private void setYears() {
		int minYear = CURRENT_YEAR;
		int maxYear = SFU_FOUND_YEAR;
		int year;
		if (listOfCourse == null) {
			minYear = 2007;
			maxYear = 2014;
		} else {
			for (Course course : listOfCourse) {
				year = course.getYear();
				if (year < minYear) {
					minYear = year;
				}
				if (year > maxYear) {
					maxYear = year;
				}
			}
		}
		int yearToAdd = minYear;
		years = new int[maxYear - minYear + 1];
		for (int i = 0; i < years.length; i++) {
			years[i] = yearToAdd;
			yearToAdd++;
		}
	}

	@Override
	public void updatePanel() {
		listOfCourse = getListOfCourses();
		removeAll();
		addTitle("Course Offerings by Semester");
		addMainPanel(makeMainContentPanel());
		revalidate();
		repaint();
	}
}
