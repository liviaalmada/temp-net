package tvg.clustering.measure2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.jgrapht.graph.DefaultWeightedEdge;

import tvg.DirectedTVG;
import tvg.TimeIntervalVertex;
import tvg.clustering.DirectedTVGCluster;
import tvg.clustering.TVGClusterPair;
import tvg.clustering.ClusterPairComparator;

public class ClusterPairImpl2 implements TVGClusterPair{
	
	private DirectedTVG graph;
	private DirectedTVGCluster c1;
	private DirectedTVGCluster c2;
	private double similarity;
	private double dependencySum;
	private HashSet<DefaultWeightedEdge> cut;
	
	public ClusterPairImpl2(DirectedTVGCluster c1, DirectedTVGCluster c2, DirectedTVG g) {
		// TODO Auto-generated constructor stub
		this.c1 = c1;
		this.c2 = c2;
		this.graph = g; 
		cut = new HashSet<DefaultWeightedEdge>();
		
		//System.out.println("Entre "+c1.getId()+" e "+c2.getId());
		calculateSimilarity();
		//System.out.println("similaridade calculada "+similarity);
	}
	
	public ClusterPairImpl2() {
		// TODO Auto-generated constructor stub
	}
	
	public double getSimilarity(){
		return similarity;
	}

	public double getDepencySum(){
		return dependencySum;
	}
	
	private double calculateSimilarity(){
		double dep1=0;
		double dep2=0;
		for (TimeIntervalVertex v : c1.vertexSet()) {
			for (TimeIntervalVertex u : c2.vertexSet()) {
				dep1 += directionalDependence(u, v);
				dep2 += directionalDependence(v, u);
				//dependencySum += dep;
				//System.out.println("Grau de dependencia entre "+u.getId()+" e "+v.getId()+": "+dep);
			}
		}
		
		similarity = Math.max(dep1/c1.vertexSet().size(), dep2/c2.vertexSet().size());
		
		return similarity;
	}
	
//	private double dependencyDegree(TimeIntervalVertex u, TimeIntervalVertex v){
//		
//		//System.out.println(graph.outDegreeOf(u));
//		//System.out.println(graph.inDegreeOf(u));
//		//double quoV = (v.lifeTime()*graph.outDegreeOf(v));
//		return Math.max(directionalDependencey(u, v), 
//						directionalDependencey(v, u));		
//	}

	private double directionalDependence(TimeIntervalVertex u, TimeIntervalVertex v){
		double quocU = (u.lifeTime()*graph.outDegreeOf(u));
		DefaultWeightedEdge e;
		TimeIntervalVertex aux;
		double depDegree = 0;			
		double time ;	
		
		if(graph.containsEdge(u, v)){
			e = graph.getEdge(u, v);
			time = graph.getEdgeWeight(e);
			cut.add(e);
			aux = new TimeIntervalVertex(u.getInitialTime() + time, u.getFinalTime() + time);
			//System.out.println(aux.intersectionSize(v));
			depDegree = aux.intersectionSize(v)/quocU;		
		}	
		
		return depDegree;
	}
	
	public DirectedTVGCluster getC1() {
		return c1;
	}

	public DirectedTVGCluster getC2() {
		return c2;
	}
	
	public void setGraph(DirectedTVG graph) {
		this.graph = graph;
	}

	public void setC1(DirectedTVGCluster c1) {
		this.c1 = c1;
	}

	public void setC2(DirectedTVGCluster c2) {
		this.c2 = c2;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public void setCut(HashSet<DefaultWeightedEdge> cut) {
		this.cut = cut;
	}
	
	public PriorityQueue<TVGClusterPair>  merge(PriorityQueue<TVGClusterPair> pairs, ArrayList<DirectedTVGCluster> clustering){
		
		HashSet<TimeIntervalVertex> vertices = new HashSet<TimeIntervalVertex>();
		vertices.addAll(c1.vertexSet());
		vertices.addAll(c2.vertexSet());
		int id = Math.min(c1.getId(), c2.getId());
		
		DirectedTVGCluster newCluster = new DirectedTVGCluster(graph, vertices, id);
		
		// calculate new similarities, between the new clusters and the others old clusters		
		HashMap<Integer, TVGClusterPair> aux = new HashMap<Integer, TVGClusterPair>();
		PriorityQueue<TVGClusterPair>  newPairs = new PriorityQueue<TVGClusterPair>(1, new ClusterPairComparator());
		TVGClusterPair newPair;
		for (TVGClusterPair p : pairs) {
			if(c1 == p.getC1() || c2 == p.getC1() ){
				if(!aux.containsKey(p.getC2().getId())){
					newPair = new ClusterPairImpl2(newCluster, p.getC2(), graph);
					aux.put(p.getC2().getId(), newPair);
				}
				
			}else if(c1 == p.getC2() || c2 == p.getC2()){
				if(!aux.containsKey(p.getC1().getId())){
					newPair = new ClusterPairImpl2(newCluster, p.getC1(), graph);	
					aux.put(p.getC1().getId(), newPair);
				}			
			}else{		
				newPairs.add(p);
			}
		}
		
		for (Entry<Integer, TVGClusterPair> e : aux.entrySet()) {
			newPairs.add(e.getValue());
		}

		clustering.add(newCluster);
		clustering.remove(c1);
		clustering.remove(c2);
		return newPairs;
	}

	public HashSet<DefaultWeightedEdge> getCut() {
		return cut;
	}

	public void setDependencySum(double dependencySum) {
		this.dependencySum = dependencySum;
	}
	
}
