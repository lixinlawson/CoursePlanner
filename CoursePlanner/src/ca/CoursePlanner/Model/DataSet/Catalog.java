/**
 * Catalog is subset of the main data set used to store the courses directly
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ca.CoursePlanner.Model.DataSet.Comparator.CourseComparator;

public class Catalog implements Iterable<Course> {

	private static final String VAN_CAMPUS = "HRBRCNTR";
	private static final String SRY_CAMPUS = "SURREY";
	private static final String BBY_CAMPUS = "BURNABY";

	String catalogNum;
	ArrayList<Course> courses = new ArrayList<Course>();

	int offeringsBBY = 0;
	int offeringsSRY = 0;
	int offeringsVAN = 0;
	int offeringsOther = 0;

	int offeringsSpring = 0;
	int offeringsSummer = 0;
	int OfferingsFall = 0;

	public Catalog(String catalog) {
		catalogNum = catalog;
	}

	public void addCourse(Course course) {
		for (Course c : courses) {
			if (c.isSameCourse(course)) {
				c.mergeSameCourse(course);
				return;
			}
		}
		courses.add(course);
		updateCampusCounters(course);
		updateSemesterCounters(course);
		Collections.sort(courses, new CourseComparator());
	}

	public boolean hasTheCourse(Course course) {
		boolean has = false;
		for (Course courseIn : courses) {
			if (courseIn.isSameCourse(course)) {
				has = true;
			}
		}
		return has;
	}

	private void updateCampusCounters(Course course) {
		String courseLocation = course.getLocation();
		if (courseLocation.equals(BBY_CAMPUS)) {
			offeringsBBY++;
		} else if (courseLocation.equals(SRY_CAMPUS)) {
			offeringsSRY++;
		} else if (courseLocation.equals(VAN_CAMPUS)) {
			offeringsVAN++;
		} else {
			offeringsOther++;
		}
	}

	private void updateSemesterCounters(Course course) {
		Semester semester = course.getSemester();
		switch (semester) {
		case SPRING:
			offeringsSpring++;
			break;
		case SUMMER:
			offeringsSummer++;
			break;
		case FALL:
			OfferingsFall++;
			break;
		default:
			assert false;
		}
	}

	public String getCatalogNum() {
		return catalogNum;
	}

	public int getOfferingsBBY() {
		return offeringsBBY;
	}

	public int getOfferingsSRY() {
		return offeringsSRY;
	}

	public int getOfferingsVAN() {
		return offeringsVAN;
	}

	public int getOfferingsOther() {
		return offeringsOther;
	}

	public int getOfferingsSpring() {
		return offeringsSpring;
	}

	public int getOfferingsSummer() {
		return offeringsSummer;
	}

	public int getOfferingsFall() {
		return OfferingsFall;
	}

	@Override
	public Iterator<Course> iterator() {
		assert courses.size() > 0;
		return Collections.unmodifiableList(courses).iterator();
	}

}
