package map.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.*;
import map.jsonClasses.*;

public class ViewFunc {

	public File openFileFromUser(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("plase insert json file in order to show markers on map");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON FILES", "json");
		fileChooser.setFileFilter(filter);
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			return selectedFile;
		}
		return null;
	}

	public void showMessage() {
		JOptionPane.showMessageDialog(null, "Map project\nVersion:1.2Alpha\n\n\nTeam:\nOrly Sharon\nRafael Rosenbaum\nLiz Sror");
	}

	public double upArrowButton(double axisX, double axisY,int viewZoom, double viewZoomAlg[],String urlMarker1 , String urlKey) throws IOException{
		axisY=axisY+ viewZoomAlg[viewZoom];
		return axisY;
	}

	public double downArrowButton(double axisX, double axisY,int viewZoom, double viewZoomAlg[],String urlMarker1 , String urlKey) throws IOException{
		axisY=axisY- viewZoomAlg[viewZoom];
		return axisY;
	}

	public double rightArrowButton(double axisX, double axisY,int viewZoom, double viewZoomAlg[],String urlMarker1 , String urlKey) throws IOException{
		axisX=axisX+ viewZoomAlg[viewZoom];
		return axisX;
	}

	public double leftArrowButton(double axisX, double axisY,int viewZoom, double viewZoomAlg[],String urlMarker1 , String urlKey) throws IOException{
		axisX=axisX- viewZoomAlg[viewZoom];
		return axisX;
	}

	public void addNewMarkerWindow(){
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextField xFiled = new JTextField(20);
		JLabel xLabel =new JLabel("x");
		JButton addMAkerButton = new JButton("enter marker");
		panel.add(xLabel);
		panel.add(xFiled);
		JTextField yFiled = new JTextField(20);
		JLabel yLabel =new JLabel("y");
		panel.add(yLabel);
		panel.add(yFiled);

		JTextField cityFiled = new JTextField(20);
		JLabel cityLabel =new JLabel("city:");
		panel.add(cityLabel);
		panel.add(cityFiled);

		JTextField stretFiled = new JTextField(20);
		JLabel stretLabel =new JLabel("stret");
		panel.add(stretLabel);
		panel.add(stretFiled);

		JTextField stretNumFiled = new JTextField(20);
		JLabel stretNumLabel =new JLabel("stret number");
		panel.add(stretNumLabel);
		panel.add(stretNumFiled);

		JTextField infoFiled = new JTextField(20);
		JLabel infoLabel =new JLabel("info");
		panel.add(infoLabel);
		panel.add(infoFiled);

		panel.add(addMAkerButton);

		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.pack();

		frame.setVisible(true);
		frame.setResizable(false);
	}

	public void exportResultURL(){
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI(View.url.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	// Location finding
	public Marker getJsonData(String locationInfo) {
		// make a marker
		locationInfo = locationInfo.replaceAll(" ", "%20");
		Marker tempMarker=new Marker();
		tempMarker.setMarkerInfo(new MarkerInfo());
		tempMarker.setMarkerPoint(new MarkerPoint());
		tempMarker.getMarkerInfo().setAddress(new MarkerInfoAddress());

		// read Json from link
		StringBuilder sb = new StringBuilder();
		try {
			System.out.println("https://maps.googleapis.com/maps/api/geocode/json?address="+locationInfo+"&key=AIzaSyABjalW5fov1m0-03THbtr38aA4oDskpro");
			URLConnection connection = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+locationInfo+"&key=AIzaSyABjalW5fov1m0-03THbtr38aA4oDskpro").openConnection();
			//URLConnection connection = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=%D7%A2%D7%98%D7%A8%D7%94%201%20%D7%A0%D7%A6%D7%A8%D7%AA%20%D7%A2%D7%99%D7%9C%D7%99%D7%AA&key=AIzaSyABjalW5fov1m0-03THbtr38aA4oDskpro").openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			// Print
			System.out.println(sb);

			// DECODEING
			JSONObject allData = new JSONObject(sb.toString());
			JSONArray resultsArray = allData.getJSONArray("results");
			JSONObject reoultNumber0 =resultsArray.getJSONObject(0);
			JSONObject geomatry = reoultNumber0.getJSONObject("geometry");
			JSONObject location = geomatry.getJSONObject("location");
			JSONArray address =  reoultNumber0.getJSONArray("address_components");
			JSONObject address0 = address.getJSONObject(0);
			JSONObject address1 = address.getJSONObject(1);
			JSONObject address2 = address.getJSONObject(2);

			///inserting
			tempMarker.getMarkerInfo().setName(reoultNumber0.getString("formatted_address"));
			tempMarker.getMarkerPoint().setY(location.getDouble("lng"));
			tempMarker.getMarkerPoint().setX(location.getDouble("lat"));
			tempMarker.getMarkerInfo().getAddress().setAdressNumber(address0.getString("long_name"));
			tempMarker.getMarkerInfo().getAddress().setStreet(address1.getString("long_name"));
			tempMarker.getMarkerInfo().getAddress().setCity(address2.getString("long_name"));
		} catch (IOException e) {	}
		return tempMarker;
	}
}