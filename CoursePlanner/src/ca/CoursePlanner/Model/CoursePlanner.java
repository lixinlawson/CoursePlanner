/**
 * CoursePlanner is the main model of the course planner
 * It is used to create a CoursePlanner object which will give UI's the data they need
 * 
 * @author Lawson Li
 */

package ca.CoursePlanner.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.CoursePlanner.Model.DataSet.*;

public class CoursePlanner {

	private DataSet data;
	private Vector<String> listOfCourseName;
	private ArrayList<Course> listOfCourses;
	private String courseName = "";
	private int[] campusStat = new int[4];
	private int[] semesterStat = new int[3];
	private Course courseForDetail;

	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

	public CoursePlanner(File file) {
		assert file.exists();
		data = new DataSet(file);
		// data.dumpModel();
	}

	public Vector<String> getListOfSubjects() {
		Vector<String> subjects = new Vector<String>();
		for (Subject subject : data) {
			subjects.add(subject.getSubjectName());
		}
		return subjects;
	}

	public Vector<String> getListOfCourseNames() {
		return listOfCourseName;
	}

	public ArrayList<Course> getListOfCourses() {
		return listOfCourses;
	}

	public String getCourseName() {
		return courseName;
	}

	public int[] getCampusStat() {
		return campusStat;
	}

	public int[] getSemesterStat() {
		return semesterStat;
	}

	public Course getCourseForDetail() {
		return courseForDetail;
	}

	public void setListOfCourses(String subjectName, String catalogNum) {
		listOfCourses = new ArrayList<Course>();
		Catalog catalog = getCatalog(subjectName, catalogNum);
		for (Course course : catalog) {
			listOfCourses.add(course);
		}
		setCampusStat(catalog);
		setSemesterStat(catalog);
		courseName = subjectName + " " + catalogNum;
		notifyListeners();
	}

	public void setListOfCourseName(String subjectName, boolean underGrad,
			boolean grad) {
		listOfCourseName = new Vector<String>();
		Subject subject = getSubject(subjectName);
		assert subject != null;
		for (Catalog catalog : subject) {
			String catalogNum = catalog.getCatalogNum();
			if (underGrad) {
				if (!isGradCourse(catalogNum)) {
					listOfCourseName.add(subjectName + " " + catalogNum);
				}
			}
			if (grad) {
				if (isGradCourse(catalogNum)) {
					listOfCourseName.add(subjectName + " " + catalogNum);
				}
			}
		}

		courseName = "";
		campusStat = new int[4];
		semesterStat = new int[3];
		listOfCourses = null;
		courseForDetail = null;
		notifyListeners();
	}

	public void setCourseForDetail(Course course) {
		courseForDetail = course;
		notifyListeners();
	}

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	private boolean isGradCourse(String catalogNum) {
		boolean grad = false;
		if (catalogNum.startsWith("5") || catalogNum.startsWith("6")
				|| catalogNum.startsWith("7") || catalogNum.startsWith("8")) {
			grad = true;
		}
		return grad;
	}

	private Subject getSubject(String subjectName) {
		Subject sub = null;
		for (Subject subject : data) {
			String name = subject.getSubjectName();
			if (name.equals(subjectName)) {
				sub = subject;
			}
		}
		return sub;
	}

	private Catalog getCatalog(String subjectName, String catalogNum) {
		Subject sub = getSubject(subjectName);
		Catalog cata = null;
		for (Catalog catalog : sub) {
			String num = catalog.getCatalogNum();
			if (num.equals(catalogNum)) {
				cata = catalog;
			}
		}
		return cata;
	}

	private void setSemesterStat(Catalog catalog) {
		semesterStat[0] = catalog.getOfferingsSpring();
		semesterStat[1] = catalog.getOfferingsSummer();
		semesterStat[2] = catalog.getOfferingsFall();
	}

	private void setCampusStat(Catalog catalog) {
		campusStat[0] = catalog.getOfferingsBBY();
		campusStat[1] = catalog.getOfferingsSRY();
		campusStat[2] = catalog.getOfferingsVAN();
		campusStat[3] = catalog.getOfferingsOther();
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
}
