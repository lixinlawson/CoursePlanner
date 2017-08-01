/**
 * SubjectComparator is used to sort subject
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet.Comparator;

import java.util.Comparator;

import ca.CoursePlanner.Model.DataSet.Subject;

;;

public class SubjectComparator implements Comparator<Subject> {

	@Override
	public int compare(Subject subject1, Subject subject2) {
		String subjectName1 = subject1.getSubjectName();
		String subjectName2 = subject2.getSubjectName();
		return subjectName1.compareTo(subjectName2);
	}

}
