package map.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import map.jsonClasses.*;

/**
 * @author Rafi Rosenbaum, Orly Sharon, Liz Sror
 * Class Members:
 * static final Logger log
 * static final double viewZoomAlg[]
 * static List<Cluster>[] clusters
 * static String[] mapURL
 * static MarkerPoint centerMap
 * static List<Marker> listMarkers
 * Marker[] arrayMarkers
 * static List<ColorType> typeAndColorListFromXMLFile
 */
public class Model {	
	private static final Logger log = LogManager.getLogger(Model.class);
	//								 			 0,1,  2, 3, 4, 5,6,7,8,  9,  10, 11,  12,  13,  14,   15,   16,   17,    18,    19,    20,    21
	public static final double viewZoomAlg[] = {0,100,70,30,10,7,3,1,0.7,0.3,0.1,0.07,0.03,0.01,0.01,0.007,0.003,0.001,0.0007,0.0003,0.0001,0.0001};
	@SuppressWarnings("unchecked")
	public static List<Cluster>[] clusters =(ArrayList<Cluster>[])new ArrayList[21];
	public static String[] mapURL = new String[21];
	public static MarkerPoint centerMap; 
	public static List<Marker> listMarkers;
	private Marker[] arrayMarkers;
	public static List<ColorType> typeAndColorListFromXMLFile = setColorlist();

	/**
	 * The function setDataFromFIle read the data from Json file 
	 * @param File fileFromUser
	 */
	public void setDataFromFIle(File fileFromUser){
		log.debug("The Function ' setDataFromFIle ' set the map from a Json-file");
		// Get json object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// read JSON file data as String
		try {
			String fileData= new String(Files.readAllBytes(Paths.get(fileFromUser.getAbsolutePath())));
			log.debug("Get the Json-file Successfully, the file is: " + fileFromUser.getAbsolutePath());
			this.arrayMarkers = (Marker[]) gson.fromJson(fileData, Marker[].class);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Can not open the file " + fileFromUser.getAbsolutePath() + " for this app");
		}
		// parse json string to object
		listMarkers = new ArrayList<Marker>();
		convertArrayToList();
		System.out.println("\n******    List Markers    ******\n");
		System.out.println(listMarkers);
		refreshMap();
	}

	/**
	 * The function addMarker add marker to the marker list 
	 * @param Marker marker
	 */
	public void addMarker(Marker marker) {
		marker.setID(listMarkers.size());
		if (listMarkers == null){
			listMarkers = new ArrayList<Marker>();
		}
		listMarkers.add(marker);
		log.debug("The function ' addMarker ' add the marker: " + marker.toString());
		refreshMap();
	}

	/**
	 * The function refreshMap build the maps again, when there is a change  
	 */
	public void refreshMap() {
		new BuildZoom();
		centerMap=findCenter();
	}

	/**
	 * The function convertArrayToList convert the Marker Array To Marker List
	 */
	private void convertArrayToList() {
		for(int i=0;i<this.arrayMarkers.length;i++) {
			this.arrayMarkers[i].setID(i);
			listMarkers.add(i,this.arrayMarkers[i]);
		}
	}

	/**
	 * The function findCenter calculate the map center according the markers points
	 * @return MarkerPoint of the new center
	 */
	public MarkerPoint findCenter() {
		log.debug("The Function ' findCenter '");
		List<Cluster> lestCluster=new ArrayList<Cluster>();
		lestCluster= clusters[20];
		int temp = clusters[20].size();
		double x=0,y=0;
		MarkerPoint centerPoint;
		Cluster firstCluster;
		Cluster othersCluster;
		if (temp>1) {
			firstCluster=lestCluster.get(0);
			for (int i=0;i<temp;i++) {
				othersCluster = lestCluster.get(i);
				firstCluster.getPStart().setX(min(firstCluster.getPStart().getX(),othersCluster.getPStart().getX()));
				firstCluster.getPStart().setY(min(firstCluster.getPStart().getY(),othersCluster.getPStart().getY()));
				firstCluster.getPEnd().setX(max(firstCluster.getPEnd().getX(),othersCluster.getPEnd().getX()));
				firstCluster.getPEnd().setY(max(firstCluster.getPEnd().getY(),othersCluster.getPEnd().getY()));
				x= (firstCluster.getPStart().getX()+firstCluster.getPEnd().getX())/2;
				y= (firstCluster.getPStart().getY()+firstCluster.getPEnd().getY())/2;
			}
			centerPoint = new MarkerPoint(x,y);
		} else {
			x= (lestCluster.get(0).getPStart().getX()+lestCluster.get(0).getPEnd().getX())/2;
			y= (lestCluster.get(0).getPStart().getY()+lestCluster.get(0).getPEnd().getY())/2;
			centerPoint = new MarkerPoint(x,y);
		}
		log.debug("The new center for the map is: " + centerPoint);
		return centerPoint;
	}

	public static double min (double num1, double num2) {
		return num1<num2 ? num1 : num2;
	}

	public static double max (double num1, double num2) {
		return num1>=num2 ? num1 : num2;
	}

	/**
	 * The function exportToJson export the marker list to a Json File 
	 */
	public static void exportToJson(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		java.util.Date date = new java.util.Date();
		String folderPath ="C:/Taldor Project/ExportFile";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		String filePath ="C:/Taldor Project/ExportFile/jsonFile " + dateFormat.format(date)+".json";
		if (!listMarkers.isEmpty()){
			try{
				File folder = new File(folderPath);
				folder.mkdirs();
				folder.createNewFile();			
				FileWriter file = new FileWriter(filePath);
				file.write(gson.toJson(listMarkers));
				file.flush();
				file.close();
				log.debug("Export Json file successfully");
			}catch(NullPointerException e){
				e.printStackTrace();
				log.error("There is no json to export");
			}catch (IOException e) {
				e.printStackTrace();
				log.error("Can not export file for this app");
			}
		}
	}

	/**
	 * The function exportClusterByZoom export clusters of specific zoom to Json file
	 * @param zoom
	 */
	public static void exportClusterByZoom(int zoom){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		java.util.Date date = new java.util.Date();
		String folderPath ="C:/Taldor Project/ExportFile";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		String filePath ="C:/Taldor Project/ExportFile/ClustersForZoom " + zoom +" "+ dateFormat.format(date)+".json";
		if (!listMarkers.isEmpty()){
			try{
				File folder = new File(folderPath);
				folder.mkdirs();
				folder.createNewFile();			
				FileWriter file = new FileWriter(filePath);
				file.write(gson.toJson(clusters[zoom-1]));
				file.flush();
				file.close();
				log.debug("Export Json file successfully");
			}catch(NullPointerException e){
				e.printStackTrace();
				log.error("There is no json to export");
			}catch (IOException e) {
				e.printStackTrace();
				log.error("Can not export file for this zoom");
			}
		}
	}

	/**
	 * The function exportCluster export clusters of the all zooms to Json file
	 */
	public static void exportClusters(){
		for(int i=1;i<=21;i++){
			exportClusterByZoom(i);
		}
	}

	/**
	 * The function setColorlist receive a XML file which contain the type options of the clusters
	 * @return List<ColorType>
	 */
	public static List<ColorType> setColorlist(){
		log.debug ("function ' setColorlist '");
		List<ColorType> catgList = new ArrayList<ColorType>(10);
		ColorType ct= new ColorType();
		try {
			//open XML file 
			File fXmlFile = new File("./configuration/categoryFile.xml");
			log.debug ("Open the file categoryFile.xml Succeeded");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile); 
			doc.getDocumentElement().normalize();
			//crate list of element from XML file 
			NodeList nList = doc.getElementsByTagName("Category");			
			//make list of type and match color
			for (	int i=0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);	
				Element eElement = (Element) nNode;
				ct.setColor(eElement.getElementsByTagName("Color").item(0).getTextContent());
				ct.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());		
				catgList.add(ct);
				ct= new ColorType();
				log.debug("Type: " + ct.getName() + "\tcolor: " + ct.getColor());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return catgList;
	}
	
	////    Set && Get    ////
	public Marker[] GetArrayMarkers() {
		return this.arrayMarkers;
	}

	public void setArrayMarkers(Marker[] arrayMarkers) 	{
		this.arrayMarkers=arrayMarkers;
	}
}