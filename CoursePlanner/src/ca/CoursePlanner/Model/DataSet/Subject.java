/**
 *  Subject is a subset of the data set stores catalogs
 *  
 *  @author Lawson Li
 */

package ca.CoursePlanner.Model.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ca.CoursePlanner.Model.DataSet.Comparator.CatalogComparator;

public class Subject implements Iterable<Catalog> {

	String subjectName;
	ArrayList<Catalog> catalogs = new ArrayList<Catalog>();

	public Subject(String subject) {
		subjectName = subject;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void addCourse(Course course) {
		Catalog catalogToAddCourse;
		if (hasCatalogOf(course)) {
			catalogToAddCourse = getCatalogOf(course);
		} else {
			catalogToAddCourse = new Catalog(course.getCatalog());
			catalogs.add(catalogToAddCourse);
			Collections.sort(catalogs, new CatalogComparator());
		}
		catalogToAddCourse.addCourse(course);
	}

	private boolean hasCatalogOf(Course course) {
		String courseCatalog = course.getCatalog();
		boolean has = false;
		for (Catalog catalog : catalogs) {
			String catalogNum = catalog.getCatalogNum();
			if (catalogNum.equals(courseCatalog)) {
				has = true;
			}
		}
		return has;
	}

	private Catalog getCatalogOf(Course course) {
		Catalog catalogOfCourse = null;
		String courseCatalog = course.getCatalog();
		for (Catalog catalog : catalogs) {
			String catalogNum = catalog.getCatalogNum();
			if (catalogNum.equals(courseCatalog)) {
				catalogOfCourse = catalog;
			}
		}
		assert (catalogOfCourse == null);
		return catalogOfCourse;
	}

	@Override
	public Iterator<Catalog> iterator() {
		assert catalogs.size() > 0;
		return Collections.unmodifiableList(catalogs).iterator();
	}
}
