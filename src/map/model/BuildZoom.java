package map.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import map.jsonClasses.Marker;
import map.jsonClasses.MarkerPoint;

/**
 * The BuildZoom class build the List of the clusters in all zooms
 * @author Rafi Rosenbaum, Orly Sharon, Liz Sror
 */
public class BuildZoom{
	private static final Logger log = LogManager.getLogger(BuildZoom.class);

	public BuildZoom(){
		log.debug("The Function ' BuildZoom ' Build Array of 21 Maps");
		for (int i=20;i>=0;i--) {
			if (i==0) 	{
				Model.clusters[i] = null;
				Model.clusters[i]=setClustersByZoom(i);
			} else 	{
				Model.clusters[i] = null;
				Model.clusters[i]=setClustersByZoom(i);
			}
			log.debug("Zoom Number " + i + " Build Successfully");
			Model.mapURL[i] = new String(getClusterUrl(i));
			setClustersByZoom(i);
		}
	}

	/**
	 * The function getClusterUrl set a URL address for a specific zoom and return the result 
	 * @param zoom	the zoom number 
	 * @return string of the URL address
	 */
	public String getClusterUrl(int zoom) {
		log.debug("The Functaion ' getClusterUrl ' Set URL-Map From Google-Maps-API");
		String colorString = null;
		String tempURL=null;
		List<Cluster> clusterURL = new ArrayList<>(Model.clusters[zoom]);
		for (int i=0 ; i < clusterURL.size() ; i++) {
			colorString = getColor(Model.clusters[zoom].get(i).getType(),Model.typeAndColorListFromXMLFile);
			if (clusterURL.get(i).getPStart().getX()==clusterURL.get(i).getPEnd().getX() && clusterURL.get(i).getPStart().getY()==clusterURL.get(i).getPEnd().getY()) {
				tempURL += "&markers=color:"+colorString+"%7Cweight:5%7Cfillcolor:"+colorString+"%7C"+clusterURL.get(i).getPStart().getX()+",%20"+clusterURL.get(i).getPStart().getY();
			} else 	{
				double middleX = (clusterURL.get(i).getPStart().getX()+clusterURL.get(i).getPEnd().getX())/2;
				double middleY = (clusterURL.get(i).getPStart().getY()+clusterURL.get(i).getPEnd().getY())/2;
				int counter = clusterURL.get(i).getPointList().size();
				tempURL += "&path=color:"+colorString+"%7Cweight:5%7Cfillcolor:"+colorString+"%7C"+clusterURL.get(i).getPStart().getX()+",%20"+clusterURL.get(i).getPStart().getY()+"%7C"+clusterURL.get(i).getPEnd().getX()+",%20"+clusterURL.get(i).getPStart().getY()+"%7C"+clusterURL.get(i).getPEnd().getX()+",%20"+clusterURL.get(i).getPEnd().getY()+"%7C"+clusterURL.get(i).getPStart().getX()+",%20"+clusterURL.get(i).getPEnd().getY()+"%7C"+clusterURL.get(i).getPStart().getX()+",%20"+clusterURL.get(i).getPStart().getY()+"&markers=icon:http://dummyimage.com/80x30/fff.png%26text%3d"+counter+"-"+clusterURL.get(i).getType()+"%7C"+middleX+",%20"+middleY;
			}
		}
		log.debug("The UTL for zoom #"+ zoom + " is:  " + tempURL);
		return tempURL;
	}

	/**
	 * The function setClustersByZoom build the map of this zoom according the previous map
	 * @param zoom	the zoom number
	 * @return List<Cluster> which contains all the clusters on the specific zoom
	 */
	public List<Cluster> setClustersByZoom(int zoom) {
		if (zoom==20) { // the first zoom
			return firstZoom();
		} else {
			List<Cluster> thisZoomCluster=new ArrayList<>();
			List<Cluster> prevZoomCluster= new ArrayList<Cluster>(Model.clusters[zoom+1]);
			while (!prevZoomCluster.isEmpty()) {
				Cluster temp =  new Cluster(prevZoomCluster.get(0));
				prevZoomCluster.remove(0);
				int i=0;
				while (i<prevZoomCluster.size()) 	{
					if (isContain(temp,prevZoomCluster.get(i),zoom)) {
						double pointStartX = Model.min(temp.getPStart().getX(),prevZoomCluster.get(i).getPStart().getX());
						double pointStartY = Model.min(temp.getPStart().getY(),prevZoomCluster.get(i).getPStart().getY());
						double pointEndX = Model.max(temp.getPEnd().getX(),prevZoomCluster.get(i).getPEnd().getX());
						double pointEndY = Model.max(temp.getPEnd().getY(),prevZoomCluster.get(i).getPEnd().getY());
						MarkerPoint pStart = new MarkerPoint(pointStartX,pointStartY);
						MarkerPoint pEnd   = new MarkerPoint(pointEndX,pointEndY);
						temp.setPStart(pStart);
						temp.setPEnd(pEnd);
						temp.addToPointList(prevZoomCluster.get(i).getPointList());
						prevZoomCluster.remove(i);
					} else { i++; }
				}
				thisZoomCluster.add(temp);
				log.debug("The Function ' setClustersByZoom ' Set The Map At Zoom #" + zoom + " The Map Is: " + temp.toString());
				temp=null;
			}
			return thisZoomCluster;
		}
	}

	/**
	 * The function isContain get two cluster and check if prevZoomCluster contain in thisZoomCluster
	 * @param thisZoomCluster cluster from the previous zoom
	 * @param prevZoomCluster cluster from the present zoom
	 * @param zoom the present zoom number
	 * @return true if prevZoomCluster contain in thisZoomCluster by the distance according the viewZoomAlg array
	 */
	private boolean isContain(Cluster thisZoomCluster,Cluster prevZoomCluster, int zoom) {
		double sX = - Model.viewZoomAlg[zoom]+thisZoomCluster.getPStart().getX();
		double sY = - Model.viewZoomAlg[zoom]+thisZoomCluster.getPStart().getY();
		double eX = Model.viewZoomAlg[zoom]+thisZoomCluster.getPEnd().getX();
		double eY = Model.viewZoomAlg[zoom]+thisZoomCluster.getPEnd().getY();

		double nsX = prevZoomCluster.getPStart().getX();
		double nsY = prevZoomCluster.getPStart().getY();
		double neX = prevZoomCluster.getPEnd().getX();
		double neY = prevZoomCluster.getPEnd().getY();

		boolean b1= sX < nsX && nsX < eX;
		boolean b2= sY < nsY && nsY < eY;
		boolean b3= sX < neX && neX < eX;
		boolean b4= sY < neY && neY < eY;

		return (b1 && b2)||(b3 && b4);
	}

	/**
	 * The function firstZoom converter each marker, that received from the Json file, to cluster
	 * @return List<Cluster> which contains all the clusters on the first zoom
	 */
	private List<Cluster> firstZoom() {
		List<Marker> listMarkers = new ArrayList<Marker>(Model.listMarkers);
		List<Cluster> firstCluster=new ArrayList<Cluster>();
		for (int i=0;i<listMarkers.size();i++) 	{
			Marker tempMarker=new Marker(listMarkers.get(i));
			List<Marker> tempList = new ArrayList<>();
			tempList.add(tempMarker );
			Cluster tempCluster = new Cluster(tempMarker.getMarkerPoint(),tempMarker.getMarkerPoint(),tempMarker.getMarkerInfo().getType(),tempList);
			firstCluster.add(tempCluster);
			tempList.remove(0);
		}
		return firstCluster;
	}

	/**
	 * The function setClusterType find the maximum type of markers that are in a specific cluster, for all the clusters an the map for a specific zoom
	 * @param  zoom the present zoom number
	 */
	public void setClusterType(int zoom){
		log.debug("The Function ' setClusterType ' Define for each Cluster, the type, by find the max marker type in the Cluster");
		List<Cluster> thisZoomCluster=new ArrayList<Cluster>(Model.clusters[zoom]);
		String maxType = "";
		for (int i=0; i<thisZoomCluster.size();i++) { // check the type of each Cluster in this specific zoom
			Hashtable<String, Integer> table = new Hashtable<String, Integer>();
			for (Marker markerList : thisZoomCluster.get(i).getPointList()) {
				String markerType = markerList.getMarkerInfo().getType();
				if (table.containsKey(markerType)) {
					int counter = table.get(markerType);
					table.remove(markerType);
					table.put(markerType, ++counter);
				}else{ table.put(markerType, 1);}
			}
			int maxValue = 0;
			for (Marker markerList : thisZoomCluster.get(i).getPointList()) {
				String markerType = markerList.getMarkerInfo().getType();
				if (table.get(markerType)> maxValue){
					maxValue = table.get(markerType);
					maxType = markerType;
				}
			}
			log.debug("In zoom Number:" + zoom + " the max type is: " + maxType);
			thisZoomCluster.get(i).setType(maxType);
		}
	}

	/**
	 * The function getColor decide the cluster color
	 * @param type string of the marker type
	 * @param catgList List<ColorType>
	 * @return string of the color type
	 */
	public String getColor(String type, List<ColorType> catgList){
		String colorReturn="black";
		log.debug("The Function '  getColor ' the type from user is:"+type );
		for (int i=0; i< catgList.size();i++){
			if ((catgList.get(i).getName().compareTo(type))==0)	{
				colorReturn = catgList.get(i).getColor();
			}
		}
		log.debug("The Function ' getColor ' set color to: "+colorReturn );
		return colorReturn;
	}
}