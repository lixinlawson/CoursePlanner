/**
 * CourseListFilterPanel is a derived class from BasePanel class
 * Used to create JPanel for course list filter
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.GUI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.CoursePlanner.Model.*;

@SuppressWarnings("serial")
public class CourseListFilterPanel extends BasePanel {

	private Vector<String> subjects;
	private String subjectSelected;
	private boolean includeUndergrad = true;
	private boolean includeGrad = false;

	public CourseListFilterPanel(CoursePlanner planner) {
		super(planner);
		subjects = planner.getListOfSubjects();
		subjectSelected = subjects.firstElement();
		addTitle("Course List Filter");
		addMainPanel(makeMainContentPanel());
		preventResizingVertically();
	}

	@Override
	protected JPanel makeMainContentPanel() {
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.add(makeDepartmentSelecter());
		main.add(makeIncludeSelecter());
		main.add(makeUpdateButton());
		return main;
	}

	private JPanel makeDepartmentSelecter() {
		JPanel selecter = new JPanel();
		selecter.setLayout(new BoxLayout(selecter, BoxLayout.LINE_AXIS));
		selecter.add(new JLabel("Department "));
		selecter.add(makeDepartmentsComboBox());
		return selecter;
	}

	private JComboBox<String> makeDepartmentsComboBox() {
		final JComboBox<String> depart = new JComboBox<String>(subjects);
		depart.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				subjectSelected = (String) depart.getSelectedItem();
			}

		});
		return depart;
	}

	private JPanel makeIncludeSelecter() {
		JPanel selecter = new JPanel();
		selecter.setLayout(new BoxLayout(selecter, BoxLayout.PAGE_AXIS));

		selecter.add(makeUndergradCheckBox());
		selecter.add(makeGradCheckBox());
		return selecter;
	}

	private JCheckBox makeUndergradCheckBox() {
		final JCheckBox underGrad = new JCheckBox("Include undergrad courses");
		underGrad.setSelected(true);
		underGrad.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				includeUndergrad = underGrad.isSelected();
			}

		});
		return underGrad;
	}

	private JCheckBox makeGradCheckBox() {
		final JCheckBox grad = new JCheckBox("Include grad courses");
		grad.setSelected(false);
		grad.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				includeGrad = grad.isSelected();
			}

		});
		return grad;
	}

	private JButton makeUpdateButton() {
		JButton update = new JButton("Update Course List");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setListOfCourseName(subjectSelected, includeUndergrad,
						includeGrad);
			}
		});
		return update;
	}

	@Override
	public void updatePanel() {
		// do not need to update once created
	}

}
