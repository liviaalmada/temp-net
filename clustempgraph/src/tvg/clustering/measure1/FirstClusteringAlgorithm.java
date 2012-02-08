package tvg.clustering.measure1;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;

import tvg.DirectedTVG;
import tvg.TimeIntervalVertex;
import tvg.clustering.Cluster;
import tvg.clustering.DirectedTVGCluster;
import tvg.clustering.TVGClusterPair;
import tvg.clustering.ClusterPairComparator;
import tvg.clustering.ClusteringTVG;
public class FirstClusteringAlgorithm implements ClusteringTVG{	
	
	@Override
	public AbstractCollection<DirectedTVGCluster> execute(DirectedTVG g) throws Exception {

		//graph = g; 
		int size = g.vertexSet().size();
				
		ArrayList<DirectedTVGCluster> clustering = new ArrayList<DirectedTVGCluster>();
		PriorityQueue<TVGClusterPair> pairs = new PriorityQueue<TVGClusterPair>((int) Math.ceil((double)size/2.),new ClusterPairComparator());
						
		for (TimeIntervalVertex v : g.vertexSet()) {
			HashSet<TimeIntervalVertex> set = new HashSet<TimeIntervalVertex>();
			set.add(v);
			clustering.add(new DirectedTVGCluster(g, set, v.getId()));
		}
	
		ClusterPairImpl1 pair;
		for (int i = 0; i<clustering.size(); i++) {
			for (int j = i+1; j<clustering.size(); j++) {
				pair = new ClusterPairImpl1(clustering.get(i), clustering.get(j), g);
				if(pair.getSimilarity()>0)pairs.add(pair);
			}
		}
		
		//System.out.println("topo: "+pairs.peek().getC1().getId()+ " " + pairs.peek().getC2().getId());
		/*for (ClusterPair clusterPair : pairs) {
			//System.out.println(clusterPair.getC1().getId()+" "+clusterPair.getC2().getId());	
		}*/
		
		size = pairs.size();
		while(size>0){
			TVGClusterPair moreSimilar = pairs.remove();
			pairs = moreSimilar.merge(pairs, clustering);
			size = pairs.size();
			for (Cluster<TimeIntervalVertex, DefaultWeightedEdge> cl : clustering) {
				System.out.println("Cluster "+cl.getId());
				System.out.print("Vertices:");
				for (TimeIntervalVertex v : cl.vertexSet()) {
					System.out.print(v.getId()+" ");
				}
				System.out.println();
			}
		}
		
		
		return clustering;
	}

	/*public double similarity(Cluster<TimeIntervalVertex, DefaultWeightedEdge> a, Cluster<TimeIntervalVertex, DefaultWeightedEdge> b){
		similarity = 0;
		cutSize = 0;
		
		for (TimeIntervalVertex v : a.vertexSet()) {
			for (TimeIntervalVertex u : b.vertexSet()) {
				incrementSimilarity(u, v);				
				incrementSimilarity(v, u);
			}
		}
		//System.out.println(cutSize);
		return similarity/cutSize;
	}
	
	private void incrementSimilarity(TimeIntervalVertex u, TimeIntervalVertex v){
		DefaultWeightedEdge e;
		TimeIntervalVertex aux;
		if(graph.containsEdge(u, v)){
			cutSize++;
			e = graph.getEdge(u, v);
			double time = graph.getEdgeWeight(e);
			aux = new TimeIntervalVertex(u.getInitialTime() + time, u.getFinalTime() + time);
			System.out.println(aux.intersectionSize(v));
			similarity += aux.intersectionSize(v)/u.size();
			//System.out.println(similarity);
		}
	}*/
	
	
	
		
}
