/**
 * CourseComparator is used to sort course by semester
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet.Comparator;

import java.util.Comparator;

import ca.CoursePlanner.Model.DataSet.Course;

public class CourseComparator implements Comparator<Course> {

	@Override
	public int compare(Course course1,
			Course course2) {
		int code1 = course1.getSemesterCode();
		int code2 = course2.getSemesterCode();
		return code1 - code2;
	}

}
