package kmeans;

public class Point {
	private int index = -1; //denotes which Cluster it belongs to
	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Double getSquareOfDistance(Point anotherPoint){
		return  (x - anotherPoint.x) * (x - anotherPoint.x)
				+ (y - anotherPoint.y) *  (y - anotherPoint.y) ;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString(){
		return "(" + x + "," + y +")";
	} 
}