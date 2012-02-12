package tvg.clustering;

import java.util.Comparator;


public class ClusterPairComparator implements Comparator<TVGClusterPair>{

	@Override
	public int compare(TVGClusterPair o1, TVGClusterPair o2) {
		// TODO Auto-generated method stub
		//a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
		double difference = o1.getSimilarity() - o2.getSimilarity();
		if(difference>0) return -1;
		if(difference<0) return 1;
		return 0;
		// se o1 é mais similar é positivo
		// se o2 'mais similar é negativo
	}

}
