/**
 * Course is an immutable class used to store the details of a course
 * I also can get the course name, year and semester
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ca.CoursePlanner.Model.DataSet.Comparator.SectionTypeComparator;

public class Course implements Iterable<SectionType> {

	private static final int FACTOR_GET_SEMESTER = 10;
	private static final int FACTOR_GET_CENTURY = 1000;
	private static final int CODE_FOR_SUMMER = 4;
	private static final int CODE_FOR_SPRING = 1;
	private static final int CODE_FOR_1900 = 0;
	private static final int CENTURY_1900 = 1900;
	private static final int CENTURY_2000 = 2000;

	private static final int INDEX_STRM = 0;
	private static final int INDEX_SUBJECT = 1;
	private static final int INDEX_CATALOG_NBR = 2;
	private static final int INDEX_LOCATION = 3;
	private static final int INDEX_ENRL_CAP = 4;
	private static final int INDEX_SSR_COMPONENT = 5;
	private static final int INDEX_ENRL_TOT = 6;
	private static final int INDEX_INSTRUCTORS = 7;

	private int semesterCode;
	private String subject;
	private String catalog;
	private String location;
	private String instructors;
	private ArrayList<SectionType> sectionTypes = new ArrayList<SectionType>();

	public Course(String[] courseInfor) {
		semesterCode = Integer.parseInt(courseInfor[INDEX_STRM]);
		subject = courseInfor[INDEX_SUBJECT].toUpperCase();
		catalog = courseInfor[INDEX_CATALOG_NBR].toUpperCase();
		location = courseInfor[INDEX_LOCATION].toUpperCase();
		instructors = courseInfor[INDEX_INSTRUCTORS];
		addSectionTypeToCourse(courseInfor);
	}

	private void addSectionTypeToCourse(String[] courseInfor) {
		String type = courseInfor[INDEX_SSR_COMPONENT].toUpperCase();
		int enrollTotal = Integer.parseInt(courseInfor[INDEX_ENRL_TOT]);
		int enrollCapacity = Integer.parseInt(courseInfor[INDEX_ENRL_CAP]);
		sectionTypes
				.add(new SectionType(type, enrollTotal, enrollCapacity));
	}

	public boolean isSameCourse(Course course) {
		boolean sameSemester = semesterCode == course.getSemesterCode();
		boolean sameSubject = subject.equals(course.getSubject());
		boolean sameCatalog = catalog.equals(course.getCatalog());
		boolean sameLocation = location.equals(course.getLocation());
		return sameSemester && sameSubject && sameCatalog && sameLocation;
	}

	public void mergeSameCourse(Course course) {
		for (SectionType type : course) {
			SectionType newType = type;
			updateSectionType(newType);
			updateInstructors(course);
		}
	}

	private void updateSectionType(SectionType newType) {
		if (hasSectionType(newType)) {
			getSectionTypeOf(newType).mergeSameTypeComponent(newType);
		} else {
			sectionTypes.add(newType);
			Collections.sort(sectionTypes, new SectionTypeComparator());
		}
	}

	private void updateInstructors(Course course) {
		String newInstructor = course.getInstructors();
		if (instructors.isEmpty()) {
			instructors = newInstructor;
		} else if (!instructors.contains(newInstructor)) {
			instructors += ", " + newInstructor;
		}
	}

	private SectionType getSectionTypeOf(SectionType newType) {
		SectionType sectionType = null;
		for (SectionType type : sectionTypes) {
			if (type.isSameType(newType)) {
				sectionType = type;
			}
		}
		assert (sectionType == null);
		return sectionType;
	}

	private boolean hasSectionType(SectionType newComp) {
		boolean has = false;
		for (SectionType type: sectionTypes) {
			if (type.isSameType(newComp)) {
				has = true;
			}
		}
		return has;
	}

	public int getYear() {
		int year;
		int centuryCode = semesterCode / FACTOR_GET_CENTURY;
		int deacdeYearCode = semesterCode % FACTOR_GET_CENTURY
				/ FACTOR_GET_SEMESTER;

		if (centuryCode == CODE_FOR_1900) {
			year = CENTURY_1900 + deacdeYearCode;
		} else {
			year = CENTURY_2000 + deacdeYearCode;
		}

		return year;
	}

	public Semester getSemester() {
		int code = semesterCode % FACTOR_GET_SEMESTER;
		Semester semester;
		if (code == CODE_FOR_SPRING) {
			semester = Semester.SPRING;
		} else if (code == CODE_FOR_SUMMER) {
			semester = Semester.SUMMER;
		} else { // otherwise is Fall
			semester = Semester.FALL;
		}
		return semester;
	}

	public String getCourseName() {
		return String.format("%s %s", subject, catalog);
	}

	public int getSemesterCode() {
		return semesterCode;
	}

	public String getSubject() {
		return subject;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getLocation() {
		return location;
	}

	public String getInstructors() {
		return instructors;
	}

	@Override
	public Iterator<SectionType> iterator() {
		assert sectionTypes.size() > 0;
		return Collections.unmodifiableList(sectionTypes).iterator();
	}

	public void pringCourseInfor() {
		System.out.printf("\t%d in %s by %s\n", semesterCode, location,
				instructors);
		for (SectionType type : sectionTypes) {
			System.out.printf("\t\t%s\n", type.toString());
		}
	}

}
