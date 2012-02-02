package tvg.clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;

import tvg.DirectedTVG;
import tvg.TimeIntervalVertex;

public class ClusterPair {
	
	private DirectedTVG<DefaultWeightedEdge> graph;
	private Cluster<TimeIntervalVertex, DefaultWeightedEdge> c1;
	private Cluster<TimeIntervalVertex, DefaultWeightedEdge> c2;
	private double similarity;
	private HashSet<DefaultWeightedEdge> cut;
	
	public ClusterPair(Cluster<TimeIntervalVertex, DefaultWeightedEdge> c1, Cluster<TimeIntervalVertex, DefaultWeightedEdge> c2, DirectedTVG<DefaultWeightedEdge> g) {
		// TODO Auto-generated constructor stub
		this.c1 = c1;
		this.c2 = c2;
		this.graph = g; 
		cut = new HashSet<DefaultWeightedEdge>();
		calculateSimilarity();
		System.out.println("similaridade calculada "+similarity);
	}
	
	public ClusterPair() {
		// TODO Auto-generated constructor stub
	}
	
	public double getSimilarity(){
		return similarity;
	
	}
	
	public double calculateSimilarity(){
		similarity = 0;
			
		for (TimeIntervalVertex v : c1.vertexSet()) {
			for (TimeIntervalVertex u : c2.vertexSet()) {
				incrementSimilarity(u, v);				
				incrementSimilarity(v, u);
			}
		}
		//System.out.println(cutSize);
		return similarity/cut.size();
	}
	
	private void incrementSimilarity(TimeIntervalVertex u, TimeIntervalVertex v){
		DefaultWeightedEdge e;
		TimeIntervalVertex aux;
		if(graph.containsEdge(u, v)){
			e = graph.getEdge(u, v);
			cut.add(e);
			double time = graph.getEdgeWeight(e);
			aux = new TimeIntervalVertex(u.getInitialTime() + time, u.getFinalTime() + time);
			System.out.println(aux.intersectionSize(v));
			similarity += aux.intersectionSize(v)/u.size();
			//System.out.println(similarity);
		}
	}

	public Cluster<TimeIntervalVertex, DefaultWeightedEdge> getC1() {
		return c1;
	}

	public Cluster<TimeIntervalVertex, DefaultWeightedEdge> getC2() {
		return c2;
	}
	
	public void setGraph(DirectedTVG<DefaultWeightedEdge> graph) {
		this.graph = graph;
	}

	public void setC1(Cluster<TimeIntervalVertex, DefaultWeightedEdge> c1) {
		this.c1 = c1;
	}

	public void setC2(Cluster<TimeIntervalVertex, DefaultWeightedEdge> c2) {
		this.c2 = c2;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public void setCut(HashSet<DefaultWeightedEdge> cut) {
		this.cut = cut;
	}
	
	public Cluster<TimeIntervalVertex, DefaultWeightedEdge> merge(PriorityQueue<ClusterPair> pairs){
		
		HashSet<TimeIntervalVertex> vertices = new HashSet<TimeIntervalVertex>();
		vertices.addAll(c1.vertexSet());
		vertices.addAll(c2.vertexSet());
		int id = Math.min(c1.getId(), c2.getId());
		
		Cluster<TimeIntervalVertex, DefaultWeightedEdge> newCluster = new Cluster<TimeIntervalVertex, DefaultWeightedEdge>(graph, vertices, id);
		
		
		// calculate new similarities, between the new clusters and the others old clusters		
		HashMap<Integer, ClusterPair> aux = new HashMap<Integer, ClusterPair>();
		
		ClusterPair newPair;
		for (ClusterPair p : pairs) {
			if(c1 == p.c1 || c2 == p.c1 ){
				pairs.remove(p);
				if(!aux.containsKey(p.c2.getId())){
					newPair = new ClusterPair();
					newPair.setC1(newCluster);
					newPair.setC2(p.c2);
					newPair.setCut(p.cut);
					newPair.setSimilarity(p.similarity);
					aux.put(p.c2.getId(), p);
				}else{
					newPair = aux.get(p.c2.getId());
					newPair.cut.addAll(p.cut);
					newPair.similarity += p.similarity;
				}
				
			}else if(c1 == p.c2 || c2 == p.c2){
				if(!aux.containsKey(p.c1.getId())){
					newPair = new ClusterPair();
					newPair.setC1(newCluster);
					newPair.setC2(p.c1);
					newPair.setCut(p.cut);
					newPair.setSimilarity(p.similarity);
					aux.put(p.c1.getId(), p);
				}else{
					newPair = aux.get(p.c1.getId());
					newPair.cut.addAll(p.cut);
					newPair.similarity += p.similarity;
				}				
			}			
		}

		return newCluster;
	}
	
}
