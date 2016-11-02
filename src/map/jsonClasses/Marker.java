package map.jsonClasses;

public class Marker {
	//the structure of the json file
	private MarkerPoint markerPoint;
	private MarkerInfo markerInfo;
	private int ID;
	
	public Marker() {
		// TODO Auto-generated constructor stub
	}
	public Marker(Marker m){
		this.markerPoint = new MarkerPoint(m.getMarkerPoint().getX(),m.getMarkerPoint().getY()); 
		this.markerInfo = new MarkerInfo(m.getMarkerInfo());  
		this.ID = m.getID();
	}

	public void setMarkerPoint(MarkerPoint point){
		this.markerPoint = point;
	}

	public void setMarkerInfo(MarkerInfo info){
		this.markerInfo = info;
	}

	public void setID(int id){
		this.ID=id;
	}

	public MarkerPoint getMarkerPoint(){
		return markerPoint;
	}

	public MarkerInfo getMarkerInfo() {
		return markerInfo;
	}

	public int getID(){
		return this.ID;
	}

	@Override
	public String toString(){
		return "\n\n\nID: "+ID + "\n("+getMarkerPoint().toString() +
				")\n"+getMarkerInfo().toString();
	}
}
