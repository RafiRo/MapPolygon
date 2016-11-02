package kmeans;

import java.io.*;
import java.util.*;
import map.jsonClasses.Marker;
import map.model.*;

public class KMeans {
	private static final Random random = new Random();
	public final List<Point> allPoints;
	public final int k;
	public static Model m;
	private Clusters pointClusters; //the k Clusters

	/**
	 * @param k : number of clusters
	 */
	public KMeans(int k) {
		if (k < 2)
			new Exception("The value of k should be 2 or more.").printStackTrace();
		this.k = k;
		List<Point> points = new ArrayList<Point>();
		changeMarkerToPoint(Model.listMarkers,points);
		this.allPoints = Collections.unmodifiableList(points);
	}

	/**
	 * step 1: get random seeds as initial centroids of the k clusters
	 */
	private void getInitialKRandomSeeds(){
		pointClusters = new Clusters(allPoints);
		List<Point> kRandomPoints = getKRandomPoints();
		for (int i = 0; i < k; i++){
			kRandomPoints.get(i).setIndex(i);
			pointClusters.add(new Cluster(kRandomPoints.get(i)));
		} 
	}

	private void changeMarkerToPoint(List<Marker> listMarkers,List<Point> listPoints) {
		for (int i = 0; i < listMarkers.size() ; i++){
			listPoints.add(new Point(listMarkers.get(i).getMarkerPoint().getX(), listMarkers.get(i).getMarkerPoint().getY()));
		}
	}

	private List<Point> getKRandomPoints() {
		List<Point> kRandomPoints = new ArrayList<Point>();
		boolean[] alreadyChosen = new boolean[allPoints.size()];
		int size = allPoints.size();
		for (int i = 0; i < k; i++) {
			int index = -1, r = random.nextInt(size--) + 1;
			for (int j = 0; j < r; j++) {
				index++;
				while (alreadyChosen[index])
					index++;
			}
			kRandomPoints.add(allPoints.get(index));
			alreadyChosen[index] = true;
		}
		return kRandomPoints;
	}

	/**
	 * step 2: assign points to initial Clusters
	 */
	private void getInitialClusters(){
		pointClusters.assignPointsToClusters();
	}

	/** 
	 * step 3: update the k Clusters until no changes in their members occur
	 */
	private void updateClustersUntilNoChange(){
		boolean isChanged = pointClusters.updateClusters();
		while (isChanged)
			isChanged = pointClusters.updateClusters();
	}

	/**
	 * do K-means clustering with this method
	 */
	public List<Cluster> getPointsClusters() {
		if (pointClusters == null) {
			getInitialKRandomSeeds();
			getInitialClusters();
			updateClustersUntilNoChange();
		}
		return pointClusters;
	}

	public static void main(String[] args) {
		//  String pointsFilePath = "files/randomPoints.csv";
		File nfile = new File("markerFile/map.json");
		m = new Model ();
		m.setDataFromFIle(nfile);
		KMeans kMeans = new KMeans(6);
		List<Cluster> pointsClusters = kMeans.getPointsClusters();
		for (int i = 0 ; i < kMeans.k; i++)
			System.out.println("Cluster " + i + ": " + pointsClusters.get(i));
	}
}