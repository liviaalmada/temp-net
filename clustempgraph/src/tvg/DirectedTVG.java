package tvg;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class DirectedTVG<E> extends SimpleDirectedWeightedGraph<TimeIntervalVertex, E> {

		
	public DirectedTVG(EdgeFactory<TimeIntervalVertex, E> ef) {
		super(ef);
	}
	
}
