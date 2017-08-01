/**
 *  SectionType class is used to stored the section type and enrollment condition for a course.
 * 
 *  @author Lawson Li
 */

package ca.CoursePlanner.Model.DataSet;

public class SectionType {

	private String sectionType;
	private int enrollTotal;
	private int enrollCapacity;

	public SectionType(String sectionType, int enrollTotal, int enrollCapacity) {
		this.sectionType = sectionType.toUpperCase();
		this.enrollTotal = enrollTotal;
		this.enrollCapacity = enrollCapacity;
	}

	public String getSectionType() {
		return sectionType;
	}

	public String getEnrollment() {
		return String.format("%d / %d", enrollTotal, enrollCapacity);
	}

	public int getEnrollTotal() {
		return enrollTotal;
	}

	public int getEnrollCapacity() {
		return enrollCapacity;
	}

	public boolean isSameType(SectionType comp) {
		String typeOfComp = comp.getSectionType();
		boolean isSame = sectionType.equals(typeOfComp);
		return isSame;
	}

	public void mergeSameTypeComponent(SectionType comp) {
		enrollTotal += comp.getEnrollTotal();
		enrollCapacity += comp.getEnrollCapacity();
	}

	public String toString() {
		return String.format("Type: %s, Enrollment: %d /%d", sectionType,
				enrollTotal, enrollCapacity);
	}
}
