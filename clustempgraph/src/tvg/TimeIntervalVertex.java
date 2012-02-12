package tvg;

import org.jgrapht.graph.DefaultWeightedEdge;

import tvg.clustering.TVGClusterPair;


public class TimeIntervalVertex {
	private double initialTime;
	private double finalTime;
	private int id;
	private double outWeight;
	private DirectedTVG graph;
	
	public TimeIntervalVertex(double initialTime, double finalTime, int id) {
		// TODO Auto-generated constructor stub
		this.initialTime = initialTime;
		this.finalTime = finalTime;
		this.id = id;
	}
	
	public TimeIntervalVertex(double initialTime, double finalTime, int id, DirectedTVG graph) {
		// TODO Auto-generated constructor stub
		this.initialTime = initialTime;
		this.finalTime = finalTime;
		this.id = id;
		this.graph = graph;
		outWeight = 0;
	}
	
	public void addOutWeight(double w){		
		outWeight += w;
//		for (DefaultWeightedEdge e : graph.outgoingEdgesOf(this)) {
//				outWeight += graph.getEdgeWeight(e);
//		}
	}
	
	public TimeIntervalVertex(double initialTime, double finalTime) {
		// TODO Auto-generated constructor stub
		this.initialTime = initialTime;
		this.finalTime = finalTime;
	}
	
	public double getInitialTime() {
		return initialTime;
	}
	public void setInitialTime(double intialTime) {
		this.initialTime = intialTime;
	}
	public double getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(double finalTime) {
		this.finalTime = finalTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double lifeTime(){
		return finalTime - initialTime + 1;
	}
	
	public static double lifeTime(double initialTime, double finalTime){
		return finalTime - initialTime + 1;
	}
	
	public double intersectionSize(TimeIntervalVertex u){
		double min = 0;
		double max = 0;
		
//		if(this.initialTime<u.getInitialTime()) min = u.getInitialTime();
//		else min = this.initialTime;
		min = Math.max(initialTime, u.initialTime);
//		
//		if(this.finalTime>u.getFinalTime()) max = u.getFinalTime(); 
//		else max = this.finalTime;
		max = Math.min(finalTime, u.finalTime);
		if(max<min)return 0;
		return max - min + 1;
	}
	
	public double uniounSize(TimeIntervalVertex u){
		double min;
		double max;
		
		min = Math.min(initialTime, u.initialTime);
		max = Math.max(finalTime, u.finalTime);
		
		return max - min + 1;
	}

	public double getOutWeight() {
		return outWeight;
	}
}
