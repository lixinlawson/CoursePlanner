/**
 * CatalogComparator is used to sort catalogs by catalog number.
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.Model.DataSet.Comparator;

import java.util.Comparator;

import ca.CoursePlanner.Model.DataSet.Catalog;

public class CatalogComparator implements Comparator<Catalog> {

	@Override
	public int compare(Catalog catalog1,
			Catalog catalog2) {
		String catalogNum1 = catalog1.getCatalogNum();
		String catalogNum2 = catalog2.getCatalogNum();
		return catalogNum1.compareTo(catalogNum2);
	}

}
