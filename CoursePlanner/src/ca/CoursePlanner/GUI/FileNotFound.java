/**
 * FileNotFound class is the JFrame to show the file not found message.
 * 
 * @author Lawson Li
 */
package ca.CoursePlanner.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FileNotFound extends JFrame {

	public FileNotFound(File file) {
		super("Error Message");

		setLayout(new BorderLayout());

		add(makeIconLabel(), BorderLayout.WEST);
		add(makeMainPanel(file), BorderLayout.EAST);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JLabel makeIconLabel() {
		ImageIcon cheese = new ImageIcon("icons\\warning.png");
		return new JLabel(cheese);
	}

	private JPanel makeMainPanel(File file) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(makeMessageLabel(file));
		mainPanel.add(makeOKButtonPanel());
		mainPanel.add(Box.createVerticalGlue());
		return mainPanel;
	}

	private JLabel makeMessageLabel(File file) {
		String filePath = file.getAbsolutePath();
		String message = String.format(" Data file (%s) not found. ", filePath);
		return new JLabel(message);
	}

	private JPanel makeOKButtonPanel() {
		JPanel okPanel = new JPanel();
		okPanel.setLayout(new BoxLayout(okPanel, BoxLayout.LINE_AXIS));
		okPanel.add(Box.createHorizontalGlue());
		okPanel.add(makeOKButton());
		okPanel.add(Box.createHorizontalGlue());
		return okPanel;
	}

	private JButton makeOKButton() {
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				System.exit(0);
			}
		});
		return ok;
	}
}
