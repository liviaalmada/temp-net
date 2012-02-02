package tvg.clustering;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;


import tvg.DirectedTVG;
import tvg.TimeIntervalVertex;
public class FirstClusteringAlgorithm implements ClusteringTVG<DefaultWeightedEdge>{
	private DirectedTVG<DefaultWeightedEdge> graph;
	private double similarity;
	private int cutSize;
	
	
	
	@Override
	public AbstractCollection<Cluster<TimeIntervalVertex, DefaultWeightedEdge>> execute(DirectedTVG<DefaultWeightedEdge> g) throws Exception {

		graph = g; 
		int size = g.vertexSet().size();
				
		ArrayList<Cluster<TimeIntervalVertex, DefaultWeightedEdge>> clustering = new ArrayList<Cluster<TimeIntervalVertex,DefaultWeightedEdge>>();
		PriorityQueue<ClusterPair> pairs = new PriorityQueue<ClusterPair>((int) Math.ceil((double)size/2.),new ClusterPairComparator());
						
		for (TimeIntervalVertex v : g.vertexSet()) {
			HashSet<TimeIntervalVertex> set = new HashSet<TimeIntervalVertex>();
			set.add(v);
			clustering.add(new Cluster<TimeIntervalVertex, DefaultWeightedEdge>(g, set, v.getId()));
		}
	
		ClusterPair pair;
		for (int i = 0; i<clustering.size(); i++) {
			for (int j = i+1; j<clustering.size(); j++) {
				pair = new ClusterPair(clustering.get(i), clustering.get(j), g);
				if(pair.getSimilarity()>0)pairs.add(pair);
			}
		}
		for (ClusterPair clusterPair : pairs) {
			System.out.println(clusterPair.getC1().getId()+" "+clusterPair.getC2().getId());	
		}
		
		
		while(pairs.size()>0){
			ClusterPair moreSimilar = pairs.remove();
			clustering.add(moreSimilar.merge(pairs));
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
	
	
	public static void main(String[] args) {
		DirectedTVG<DefaultWeightedEdge> g = new DirectedTVG<DefaultWeightedEdge>(
				new ClassBasedEdgeFactory<TimeIntervalVertex, DefaultWeightedEdge>(DefaultWeightedEdge.class));
		TimeIntervalVertex u = new TimeIntervalVertex(0, 3, 0);
		TimeIntervalVertex v = new TimeIntervalVertex(0, 6, 1);
		g.addVertex(u);
		g.addVertex(v);
		g.addEdge(u, v);
		DefaultWeightedEdge e = g.getEdge(u, v);
		g.setEdgeWeight(e, 3);
		System.out.println(g.getEdgeWeight(e));
		
		FirstClusteringAlgorithm cl = new FirstClusteringAlgorithm();
		
		HashSet<TimeIntervalVertex> a1 = new HashSet<TimeIntervalVertex>();
		a1.add(u);
		Cluster<TimeIntervalVertex, DefaultWeightedEdge> c1 = new Cluster<TimeIntervalVertex, DefaultWeightedEdge>(g,a1,u.getId());
		
		HashSet<TimeIntervalVertex> a2 = new HashSet<TimeIntervalVertex>();
		a2.add(v);
		Cluster<TimeIntervalVertex, DefaultWeightedEdge> c2 = new Cluster<TimeIntervalVertex, DefaultWeightedEdge>(g,a2,v.getId());
		
		g.addEdge(u, v);
		try {
			cl.execute(g);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println(cl.similarity(c1, c2));
	}

		
}
