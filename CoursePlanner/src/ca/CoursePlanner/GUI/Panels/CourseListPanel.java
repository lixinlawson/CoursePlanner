/**
 * CoureListPanel is used to create a scroll panel to display list of courses under a given suject
 * It allows user to select course
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.CoursePlanner.Model.CoursePlanner;

@SuppressWarnings("serial")
public class CourseListPanel extends BasePanel {

	private static final int LIST_HEIGHT = 400;
	private static final int LIST_WIDTH = 300;
	private static final int CELL_WIDTH = 140;

	JList<String> courseList;

	public CourseListPanel(CoursePlanner planner) {
		super(planner);
		addTitle("Course List");
		addMainPanel(makeMainContentPanel());
	}

	@Override
	protected JPanel makeMainContentPanel() {
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(makeScrollPaneOfList(), BorderLayout.CENTER);
		return main;
	}

	private JScrollPane makeScrollPaneOfList() {
		setCourseList();
		JScrollPane scrolPanel = new JScrollPane(courseList);
		scrolPanel.setPreferredSize(new Dimension(LIST_WIDTH, LIST_HEIGHT));
		return scrolPanel;
	}

	private void setCourseList() {
		courseList = new JList<String>();
		courseList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		courseList.setVisibleRowCount(-1);
		courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		courseList.setFixedCellWidth(CELL_WIDTH);
		courseList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				String courseName = courseList.getSelectedValue();
				if (courseName != null) {
					String[] courseInfor = courseName.split(" ");
					setListOfCourses(courseInfor[0], courseInfor[1]);
				}

			}
		});
	}

	@Override
	public void updatePanel() {
		courseList.setListData(getListOfCourseNames());
		repaint();
	}
}
