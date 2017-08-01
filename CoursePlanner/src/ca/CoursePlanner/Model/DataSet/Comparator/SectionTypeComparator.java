/**
 * SectionTypeComparator is used to sort the section types
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet.Comparator;

import java.util.Comparator;

import ca.CoursePlanner.Model.DataSet.SectionType;

public class SectionTypeComparator implements Comparator<SectionType> {
	@Override
	public int compare(SectionType comp1, SectionType comp2) {
		String type1 = comp1.getSectionType();
		String type2 = comp2.getSectionType();
		return type1.compareTo(type2);
	}
}