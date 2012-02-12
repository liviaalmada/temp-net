package tvg.clustering.measure2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;

import tvg.DirectedTVG;
import tvg.TimeIntervalVertex;


public class DependencyTest {
	static String separator = " ";
	public static void main(String[] args) {
		
		DirectedTVG g = new DirectedTVG(
				new ClassBasedEdgeFactory<TimeIntervalVertex, DefaultWeightedEdge>(DefaultWeightedEdge.class));
		
		File file = new File("grafo-exemplo.txt"); 
		try {
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);  
			
			String line = null;  
			line = bReader.readLine();
			int numVert = Integer.valueOf(line);
			
			//System.out.println(numVert);
			HashMap<Integer, TimeIntervalVertex> map = new HashMap<Integer, TimeIntervalVertex>(numVert);

			// read vertex
			String[] vertex;
			double initTime;
			double finTime;
			int id;
			TimeIntervalVertex v;
			while(numVert>0){
				line = bReader.readLine();
				vertex = line.split(separator);
				initTime = Double.valueOf(vertex[0]);
				finTime = Double.valueOf(vertex[1]);
				id = Integer.valueOf(vertex[2]);
				v = new TimeIntervalVertex(initTime, finTime, id);
				g.addVertex(v);
				map.put(id, v);
				numVert--;
			}
			
			// read edge
			String[] edge;
			TimeIntervalVertex u;
			DefaultWeightedEdge e;
			double w;
			while((line = bReader.readLine()) != null) {  
			     edge = line.split(separator);
			     v = map.get(Integer.valueOf(edge[0]));
			     u = map.get(Integer.valueOf(edge[1]));
			     w = Double.valueOf(edge[2]);
			     g.addEdge(v, u);
			     e = g.getEdge(v, u);
			     g.setEdgeWeight(e, w);
			} 
			
			DependencyClusteringAlgorithm cl = new DependencyClusteringAlgorithm();
			cl.execute(g);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
	}
}
