package map.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import map.main.Main;
import map.model.*;
import javax.swing.ImageIcon;
import org.apache.log4j.*;

public class View {
	private static final  org.apache.log4j.Logger log = Logger.getLogger(View.class);
	public static URL url;	
	public int viewZoom=14;

	double axisY=32.106253,axisX=34.802831;
	String urlMarker =null;
	String urlKey="AIzaSyABjalW5fov1m0-03THbtr38aA4oDskpro";
	Image image = null;
	JLabel lblNewMap;
	JTextField slideText = new JTextField();
	public View() throws FileNotFoundException {
		log.debug("This View is running");
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenu markerMenu;
		JMenu aboutMenu;
		JMenuItem openFileItem;
		JMenuItem exportJsonItem;
		JMenuItem exportClusterItem;
		JMenuItem exportClusterByZoomItem;
		JMenuItem newMarkerItem;
		JMenuItem exportResuletItem;
		JMenuItem aboutItem;

		//  Create the menu bar.
		menuBar = new JMenuBar();

		//  Build the file menu.
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription("File");
		menuBar.add(fileMenu);

		//  Build the marker menu.
		markerMenu = new JMenu("Marker");
		markerMenu.setMnemonic(KeyEvent.VK_F);
		markerMenu.getAccessibleContext().setAccessibleDescription("Marker");
		menuBar.add(markerMenu);

		//  Build the about menu.
		aboutMenu = new JMenu("About");
		aboutMenu.setMnemonic(KeyEvent.VK_F);
		aboutMenu.getAccessibleContext().setAccessibleDescription("About");
		menuBar.add(aboutMenu);

		//  a group of JMenuItems for file menu.
		openFileItem = new JMenuItem("Open file",KeyEvent.VK_A);
		openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		openFileItem.getAccessibleContext().setAccessibleDescription("Load your clusters");
		fileMenu.add(openFileItem);
		exportJsonItem = new JMenuItem("Export markers to json file",KeyEvent.VK_B);
		exportJsonItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		exportJsonItem.getAccessibleContext().setAccessibleDescription("Save the contents of your clusters");
		fileMenu.add(exportJsonItem);
		exportClusterItem = new JMenuItem("Export cluster to json file",KeyEvent.VK_B);
		exportClusterItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		exportClusterItem.getAccessibleContext().setAccessibleDescription("Save the contents of your cluster");
		fileMenu.add(exportClusterItem);
		exportClusterByZoomItem = new JMenuItem("Export cluster to json file by zoom",KeyEvent.VK_B);
		exportClusterByZoomItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		exportClusterByZoomItem.getAccessibleContext().setAccessibleDescription("Save the contents of your cluster by zoom");
		fileMenu.add(exportClusterByZoomItem);

		//  a group of JMenuItems for marker menu.
		newMarkerItem = new JMenuItem("Add new marker",KeyEvent.VK_C);
		newMarkerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
		newMarkerItem.getAccessibleContext().setAccessibleDescription("Add new marker");
		markerMenu.add(newMarkerItem);
		exportResuletItem = new JMenuItem("Open in web",KeyEvent.VK_D);
		exportResuletItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.ALT_MASK));
		exportResuletItem.getAccessibleContext().setAccessibleDescription("Save the contents of your clusters");
		markerMenu.add(exportResuletItem);

		//  a group of JMenuItems for about menu.
		aboutItem = new JMenuItem("About",KeyEvent.VK_E);
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.ALT_MASK));
		aboutItem.getAccessibleContext().setAccessibleDescription("About");
		aboutMenu.add(aboutItem);

		// attaching the menu to the  main frame
		JFrame frame = new JFrame("Hit-Taldor Map Project");
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		// attaching the panels to the  main frame
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.setBounds(100, 20, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 700);
		frame.add(panel, BorderLayout.CENTER);

		//MAP PANEL right
		JPanel mapPanel = new JPanel();
		lblNewMap = new JLabel("");
		try {
			url = new URL("http://maps.googleapis.com/maps/api/staticmap?center=Albany,+NY&zoom=13&scale=false&size=600x600&maptype=roadmap&format=png&visual_refresh=true");
			image = ImageIO.read(url);
			System.out.println(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lblNewMap.setIcon(new ImageIcon(image));
		mapPanel.add(lblNewMap);
		frame.add(mapPanel, BorderLayout.CENTER);

		//button panel left
		JPanel buttonsPanel = new JPanel();
		JLabel lblNewLabel = new JLabel("");
		URL logoURL_hit;
		Image logoImage_hit = null;
		try {
			logoURL_hit = new URL("http://i.imgur.com/IWYjlg0.png");
			logoImage_hit = ImageIO.read(logoURL_hit);
		} catch (IOException e) {
			e.printStackTrace();
		}

		lblNewLabel.setIcon(new ImageIcon(logoImage_hit));
		frame.add(lblNewLabel, BorderLayout.EAST);
		GridBagLayout gbl_buttonsPanel = new GridBagLayout();
		gbl_buttonsPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_buttonsPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_buttonsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		buttonsPanel.setLayout(gbl_buttonsPanel);
		JButton UP = new JButton("^");
		GridBagConstraints gbc_UP = new GridBagConstraints();
		gbc_UP.insets = new Insets(0, 0, 5, 5);
		gbc_UP.anchor = GridBagConstraints.NORTHWEST;
		gbc_UP.gridx = 1;
		gbc_UP.gridy = 0;
		buttonsPanel.add(UP, gbc_UP);

		JButton LEFT = new JButton("<");
		GridBagConstraints gbc_LEFT = new GridBagConstraints();
		gbc_LEFT.insets = new Insets(0, 0, 5, 5);
		gbc_LEFT.gridx = 0;
		gbc_LEFT.gridy = 1;
		buttonsPanel.add(LEFT, gbc_LEFT);

		JButton RIGHT = new JButton(">");
		GridBagConstraints gbc_RIGHT = new GridBagConstraints();
		gbc_RIGHT.insets = new Insets(0, 0, 5, 0);
		gbc_RIGHT.gridx = 2;
		gbc_RIGHT.gridy = 1;
		buttonsPanel.add(RIGHT, gbc_RIGHT);

		JButton DOWN = new JButton("v");
		GridBagConstraints gbc_DOWN = new GridBagConstraints();
		gbc_DOWN.insets = new Insets(0, 0, 0, 5);
		gbc_DOWN.gridx = 1;
		gbc_DOWN.gridy = 2;
		buttonsPanel.add(DOWN, gbc_DOWN);	
		frame.add(buttonsPanel, BorderLayout.WEST);

		//slider panel
		JPanel buttom_slide = new JPanel();
		frame.add(buttom_slide, BorderLayout.SOUTH);
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(slider.getValue()!=viewZoom){
					viewZoom=slider.getValue();
					setMap();
					slideText.setText(String.valueOf(viewZoom));
				}
			}
		});
		buttom_slide.add(slider);
		slider.setMaximum(20);
		slider.setMinimum(1);
		slider.setValue(14);
		slideText.setColumns(2);
		buttom_slide.add(slideText);
		frame.setVisible(true);

		////////end of view settings////////////////
		ViewFunc vFunc = new ViewFunc();

		// menu open file listener 
		// file menu 
		openFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	      
				Main.model.setDataFromFIle(vFunc.openFileFromUser());	    
				setMap();
			}
		});
		exportJsonItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	  
				Model.exportToJson();	    
			}
		});
		exportClusterItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	  
				Model.exportClusters();  
			}
		});
		exportClusterByZoomItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	  
				new ChooseZoom();
			}
		});

		// marker menu 
		newMarkerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	  
				new AddNewMarkerWindow();	    
			}
		});
		exportResuletItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	  
				vFunc.exportResultURL();	    
			}
		});

		// about menu    
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	  
				vFunc.showMessage();    
			}
		});

		// buttons 
		UP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {	
				try {
					axisY = vFunc.upArrowButton(axisX,axisY,viewZoom,Model.viewZoomAlg,urlMarker, urlKey);
					setMap();	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		DOWN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev1) {	
				try {
					axisY = vFunc.downArrowButton(axisX,axisY,viewZoom,Model.viewZoomAlg,urlMarker, urlKey);
					setMap();	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		RIGHT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev2) {	
				try {
					axisX = vFunc.rightArrowButton(axisX,axisY,viewZoom,Model.viewZoomAlg,urlMarker, urlKey);
					setMap();	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});		  
		LEFT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev3) {	
				try {
					axisX = vFunc.leftArrowButton(axisX,axisY,viewZoom,Model.viewZoomAlg,urlMarker, urlKey);
					setMap();	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});    
	}
	// get string and change the map
	public void  setMap (String urlString) throws IOException{
		this.urlMarker=urlString;
		setMap();
	}

	// set/refresh map function   
	public void setMap(){
		if (!(Model.mapURL[viewZoom]==null))
			urlMarker=Model.mapURL[viewZoom].toString();
		try {
			url = new URL("https://maps.googleapis.com/maps/api/staticmap?&scale=1&center="+axisY+","+axisX+"&zoom="+viewZoom+"&size=2000x2000&"+urlMarker+"&key="+urlKey);
			image = ImageIO.read(url);
			System.out.println(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lblNewMap.setIcon(new ImageIcon(image));
	}
}