package tvg;


public class TimeIntervalVertex {
	private double initialTime;
	private double finalTime;
	private int id;
	
	public TimeIntervalVertex(double initialTime, double finalTime, int id) {
		// TODO Auto-generated constructor stub
		this.initialTime = initialTime;
		this.finalTime = finalTime;
		this.id = id;
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
	
	public double size(){
		return finalTime - initialTime + 1;
	}
	
	public static double size(double initialTime, double finalTime){
		return finalTime - initialTime + 1;
	}
	
	public double intersectionSize(TimeIntervalVertex u){
		double min;
		double max;
		
		if(this.initialTime<u.getInitialTime()) min = u.getInitialTime();
		else min = this.initialTime;
		
		if(this.finalTime>u.getFinalTime()) max = u.getFinalTime(); 
		else max = this.finalTime;
		
		return max - min + 1;
	}
	
}
