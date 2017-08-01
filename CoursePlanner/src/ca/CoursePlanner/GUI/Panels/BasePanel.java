/**
 * BasePanel is a abstract class extends from JPanel for other panel to inherit from.
 * It will provide some useful methods for it derived classes as well.
 * 
 * @author Lawson Li
 */

package ca.CoursePlanner.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.CoursePlanner.Model.CoursePlanner;
import ca.CoursePlanner.Model.DataSet.Course;

@SuppressWarnings("serial")
public abstract class BasePanel extends JPanel {

	private CoursePlanner planner;

	public BasePanel(CoursePlanner planner) {
		this.planner = planner;
		addChangeListener();
		setLayout(new BorderLayout());
	}

	public abstract void updatePanel();

	protected abstract JPanel makeMainContentPanel();

	protected void addTitle(String title) {
		add(makeTitle(title), BorderLayout.NORTH);
	}

	protected void addMainPanel(JPanel main) {
		makeBorderForPanel(main);

		add(main, BorderLayout.CENTER);
	}

	protected void preventResizingVertically() {
		Dimension prefSize = this.getPreferredSize();
		Dimension newSize = new Dimension(Integer.MAX_VALUE,
				(int) prefSize.getHeight());
		this.setMaximumSize(newSize);
	}

	protected void addChangeListener() {
		planner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				updatePanel();
			}
		});
	}

	protected void setCourseForDetail(Course course) {
		planner.setCourseForDetail(course);
	}

	protected ArrayList<Course> getListOfCourses() {
		return planner.getListOfCourses();
	}

	protected int[] getSemesterStat() {
		return planner.getSemesterStat();
	}

	protected int[] getCampusStat() {
		return planner.getCampusStat();
	}

	protected String getCourseName() {
		return planner.getCourseName();
	}

	protected Vector<String> getListOfCourseNames() {
		return planner.getListOfCourseNames();
	}

	protected Course getCourseForDetail() {
		return planner.getCourseForDetail();
	}

	protected void setListOfCourses(String subjectName, String catalogNum) {
		planner.setListOfCourses(subjectName, catalogNum);
	}

	protected void setListOfCourseName(String subjectName, boolean underGrad,
			boolean grad) {
		planner.setListOfCourseName(subjectName, underGrad, grad);
	}

	private JLabel makeTitle(String title) {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.BLUE);
		return titleLabel;
	}

	private void makeBorderForPanel(JPanel panel) {
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.GRAY));
	}

}
