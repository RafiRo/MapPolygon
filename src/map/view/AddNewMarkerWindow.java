package map.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import map.jsonClasses.*;
import map.main.Main;
import map.model.ColorType;
import map.model.Model;

public class AddNewMarkerWindow {
	ViewFunc vFunc = new ViewFunc();
	public Marker tempMarker = null;

	public AddNewMarkerWindow() {
		tempMarker= new Marker();
		JFrame frame = new JFrame("Add New Marker");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextField xFiled = new JTextField(20);
		JLabel xLabel =new JLabel("Marker Name:");
		JButton addMAkerButton = new JButton("enter marker");
		panel.add(xLabel);
		panel.add(xFiled);
		JLabel locationLabel =new JLabel("location Address:");
		JTextField locationField = new JTextField(20);    
		panel.add(locationLabel);
		panel.add(locationField);
		JTextField infoFiled = new JTextField(20);
		JLabel infoLabel =new JLabel("Phone number:");
		panel.add(infoLabel);
		panel.add(infoFiled);
		JLabel typeLabel =new JLabel("Type:",SwingConstants.LEFT);
		panel.add(typeLabel);
		List<ColorType> typeAndColorListFromXMLFile = Model.setColorlist();		
		JComboBox<String> comboTypesList = new JComboBox<String>();
		for (int i=0; i< typeAndColorListFromXMLFile.size();i++){
			comboTypesList.addItem(typeAndColorListFromXMLFile.get(i).getName());
		}
		comboTypesList.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboTypesList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comboTypesList.getSelectedItem();
			}
		});
		panel.add(comboTypesList, BorderLayout.WEST);
		panel.add(addMAkerButton);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		addMAkerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comboTypesVal =(String)comboTypesList.getSelectedItem();	
				tempMarker = vFunc.getJsonData(locationField.getText());
				tempMarker.getMarkerInfo().setPhone(infoFiled.getText());				
				tempMarker.getMarkerInfo().setType(comboTypesVal);
				if (!xFiled.getText().equals("")){
					tempMarker.getMarkerInfo().setName(xFiled.getText());
				}
				Main.model.addMarker(tempMarker);
				Main.view.setMap();
				frame.setVisible(false); //you can't see me!
				frame.dispose(); //Destroy the JFrame object
			}
		});
	}
}