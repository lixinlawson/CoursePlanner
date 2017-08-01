/**
 * DataSet is the data to store the data reading from data file.
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import ca.CoursePlanner.GUI.*;
import ca.CoursePlanner.Model.DataSet.Comparator.SubjectComparator;

public class DataSet implements Iterable<Subject> {

	private static final int COLUMN_OF_DATA = 8;

	ArrayList<Subject> subjects = new ArrayList<Subject>();

	public DataSet(File file) {
		if (!file.exists()) {
			new FileNotFound(file);
		} else {
			readAndStoreDataFromFile(file);
		}
	}

	private void readAndStoreDataFromFile(File file) {
		try {
			Scanner fileScanner = new Scanner(file);
			fileScanner.nextLine();
			while (fileScanner.hasNextLine()) {
				String data = fileScanner.nextLine();
				String[] courseInfor = data.split(",");
				courseInfor = fixCourseInfor(courseInfor);
				Course course = new Course(courseInfor);
				addCourse(course);
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void addCourse(Course course) {
		Subject subjectToAddCourse;
		if (hasSubjectOf(course)) {
			subjectToAddCourse = getSubjectOf(course);
		} else {
			subjectToAddCourse = new Subject(course.getSubject());
			subjects.add(subjectToAddCourse);
			Collections.sort(subjects, new SubjectComparator());
		}
		subjectToAddCourse.addCourse(course);
	}

	private Subject getSubjectOf(Course course) {
		Subject subjectOfCourse = null;
		String courseSubject = course.getSubject();
		for (Subject subject : subjects) {
			String subjectName = subject.getSubjectName();
			if (subjectName.equals(courseSubject)) {
				subjectOfCourse = subject;
			}
		}
		assert (subjectOfCourse == null);
		return subjectOfCourse;
	}

	private boolean hasSubjectOf(Course course) {
		boolean has = false;
		String courseSubject = course.getSubject();
		for (Subject subject : subjects) {
			String subjectName = subject.getSubjectName();
			if (subjectName.equals(courseSubject)) {
				has = true;
			}
		}
		return has;
	}

	private String[] fixCourseInfor(String[] courseInfor) {
		String[] fixedInfor = new String[COLUMN_OF_DATA];
		int currentDataNum = courseInfor.length;
		if (currentDataNum == COLUMN_OF_DATA) {
			// the data size is correct
			fixedInfor = courseInfor;
		} else if (currentDataNum < COLUMN_OF_DATA) {
			// the instructors column is empty
			for (int i = 0; i < COLUMN_OF_DATA - 1; i++) {
				// copy all the data for the except the instructors column
				fixedInfor[i] = courseInfor[i];
			}

			fixedInfor[COLUMN_OF_DATA - 1] = "";
			// fill the instructors column with empty string
		} else {
			// multiply instructors separate by comma
			for (int i = 0; i < COLUMN_OF_DATA - 1; i++) {
				// copy all the data for the except the instructors column
				fixedInfor[i] = courseInfor[i];
			}
			// add multiple instructors to one string
			String instructors = courseInfor[COLUMN_OF_DATA - 1];
			for (int i = COLUMN_OF_DATA; i < currentDataNum; i++) {
				instructors += "," + courseInfor[i];
			}

			// add instructors to fixedInfor
			fixedInfor[COLUMN_OF_DATA - 1] = instructors;
		}
		return fixedInfor;
	}

	@Override
	public Iterator<Subject> iterator() {
		assert subjects.size() > 0;
		return Collections.unmodifiableList(subjects).iterator();
	}

	public void dumpModel() {
		for (Subject subject : subjects) {
			String subjectName = subject.getSubjectName();
			for (Catalog catalog : subject) {
				String catalogNum = catalog.getCatalogNum();
				System.out.println(subjectName + " " + catalogNum);
				for (Course course : catalog) {
					course.pringCourseInfor();
				}

			}
		}
	}

}
