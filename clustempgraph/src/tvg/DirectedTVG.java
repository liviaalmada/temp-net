package tvg;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class DirectedTVG extends SimpleDirectedWeightedGraph<TimeIntervalVertex, DefaultWeightedEdge> {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectedTVG(EdgeFactory<TimeIntervalVertex, DefaultWeightedEdge> ef) {
		super(ef);
	}
	
	public void setEdgeWeight(DefaultWeightedEdge e, double w){
		this.getEdgeSource(e).addOutWeight(w);
		super.setEdgeWeight(e, w);
	}
}
