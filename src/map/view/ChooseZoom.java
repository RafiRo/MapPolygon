package map.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;


import map.model.Model;

public class ChooseZoom {
	ViewFunc vFunc = new ViewFunc();

	public  ChooseZoom() {
		JFrame frame = new JFrame(" Choose Zoom for json export");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JButton zoomButton = new JButton("enter zoom");
		new JLabel("Zoom:",SwingConstants.LEFT);
		String[] zooms = { "1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21" };
		JComboBox<String> comboZoomsList = new JComboBox<String>(zooms);
		comboZoomsList.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboZoomsList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comboZoomsList.getSelectedItem();
			}
		});
		panel.add(comboZoomsList, BorderLayout.WEST);
		panel.add(zoomButton);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		zoomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String zoomValString =(String)comboZoomsList.getSelectedItem();	
				int zoomVal = Integer.parseInt(zoomValString);
				Model.exportClusterByZoom(zoomVal);					
				frame.setVisible(false); //you can't see me!
				frame.dispose(); //Destroy the JFrame object
			}
		});
	}
}