package map.jsonClasses;

public class MarkerPoint{
	//the structure of the json file
	private double x;
	private double y;

	public MarkerPoint() {
		// TODO Auto-generated constructor stub
	}
		
	public MarkerPoint(double x, double y){
		this.x=x;
		this.y=y;
	}

	public double getX(){
		return this.x;
	}

	public void setX(double x){
		this.x = x;
	}

	public double getY(){
		return this.y;
	}

	public void setY(double y){
		this.y = y;
	}

	@Override
	public String toString() {
		return getY() + " , "+ getX() ;
	}
}