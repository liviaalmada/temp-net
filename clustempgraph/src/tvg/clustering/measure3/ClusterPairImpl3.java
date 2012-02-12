package tvg.clustering.measure3;

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

public class ClusterPairImpl3 implements TVGClusterPair{
	
	private DirectedTVG graph;
	private DirectedTVGCluster c1;
	private DirectedTVGCluster c2;
	private double similarity;
	private double dependencySum;
	private HashSet<DefaultWeightedEdge> cut;
	
	public ClusterPairImpl3(DirectedTVGCluster c1, DirectedTVGCluster c2, DirectedTVG g) {
		// TODO Auto-generated constructor stub
		this.c1 = c1;
		this.c2 = c2;
		this.graph = g; 
		cut = new HashSet<DefaultWeightedEdge>();
		
		//System.out.println("Entre "+c1.getId()+" e "+c2.getId());
		calculateSimilarity();
		//System.out.println("similaridade calculada "+similarity);
	}
	
	public ClusterPairImpl3() {
		// TODO Auto-generated constructor stub
	}
	
	public double getSimilarity(){
		return similarity;
	}

	public double getDepencySum(){
		return dependencySum;
	}
	
	private double calculateSimilarity(){
		double sim1=0;
		double sim2=0;
		for (TimeIntervalVertex v : c1.vertexSet()) {
			for (TimeIntervalVertex u : c2.vertexSet()) {
				if(graph.containsEdge(u ,v))sim1 += verticeSimilarity(u, v);
				if(graph.containsEdge(v, u))sim2 += verticeSimilarity(v, u);
				//dependencySum += dep;
				//System.out.println("Grau de dependencia entre "+u.getId()+" e "+v.getId()+": "+dep);
			}
		}
		
		similarity = Math.max(sim1/c1.vertexSet().size(), sim2/c2.vertexSet().size());
		
		return similarity;
	}
	
	private double verticeSimilarity(TimeIntervalVertex u, TimeIntervalVertex v){
		double sim1 = 1 - u.intersectionSize(v)/u.uniounSize(v);
		double sim2 = 1 - graph.getEdgeWeight(graph.getEdge(u, v))/u.getOutWeight();
		
		return (sim1 + sim2)/2;
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
					newPair = new ClusterPairImpl3(newCluster, p.getC2(), graph);
					aux.put(p.getC2().getId(), newPair);
				}
				
			}else if(c1 == p.getC2() || c2 == p.getC2()){
				if(!aux.containsKey(p.getC1().getId())){
					newPair = new ClusterPairImpl3(newCluster, p.getC1(), graph);	
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
