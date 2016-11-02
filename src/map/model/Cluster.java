package map.model;

import java.util.ArrayList;
import java.util.List;

import map.jsonClasses.*;

public class Cluster {
	/**
	 * this class contain the details of the polygon
	 * in the pointList we save all the markers under the polygon
	 * @param MarkerPoint pStart
	 * @param MarkerPoint pEnd
	 * @param String type
	 * @param List<Marker> pointList
	 * @param String URL
	 * @author Rafi Rosenbaum, Orly Sharon, Liz Sror
	 */
	private MarkerPoint pStart, pEnd;
	private String type;
	private List<Marker> pointList;
	private String URL=null;

	public Cluster(MarkerPoint pStart,MarkerPoint pEnd, String type, List<Marker> pointList){
		this.pStart=pStart;
		this.pEnd=pEnd;
		this.type=type;
		this.pointList= new ArrayList<>(pointList);
	}

	public Cluster(Cluster p){
		this.pStart = new MarkerPoint (p.getPStart().getX(),p.getPStart().getY());
		this.pEnd = new MarkerPoint (p.getPEnd().getX(),p.getPEnd().getY());
		this.type = p.getType();
		this.pointList = new ArrayList<Marker>(p.getPointList());
	}

	@Override
	public String toString(){
		return "start point: "+pStart.toString() + "end point: " + pEnd + "type: "+type+"point List: " + pointList + "\nURL: "+ URL+"\n marker list:"+ pointList;
	}

	public void addToPointList (List<Marker> pointList)	{
		this.pointList.addAll(pointList);
	}

	////   Set  && Get ////
	public void setPStart(MarkerPoint pStart){
		this.pStart=pStart;
	}

	public void setPEnd(MarkerPoint pEnd){
		this.pEnd=pEnd;
	}

	public void setType(String type){
		this.type=type;
	}

	public void setPointList(List<Marker> pointList){
		this.pointList=pointList;
	}

	public MarkerPoint getPStart(){
		return this.pStart;
	}

	public MarkerPoint getPEnd(){
		return this.pEnd;
	}

	public String getType(){
		return this.type;
	}

	public List<Marker> getPointList(){
		return this.pointList;
	}

	public String getURL(){
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
}
