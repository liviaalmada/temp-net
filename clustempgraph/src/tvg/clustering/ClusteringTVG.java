package tvg.clustering;

import java.util.AbstractCollection;

import tvg.DirectedTVG;

public interface ClusteringTVG{
	AbstractCollection<DirectedTVGCluster> execute(DirectedTVG g) throws Exception;
}
