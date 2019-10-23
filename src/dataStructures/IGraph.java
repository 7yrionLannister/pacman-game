package dataStructures;

import java.util.ArrayList;

//IMPOTANT: E must override equals
public interface IGraph<E> {

	boolean insertVertex(E e);
	
	boolean deleteVertex(E e);
	
	void link(E src, E dst, int weight);
	
	boolean unlink(E src, E dst);
	
	boolean containsVertex(E key);
	
	int getOrder();
	
	boolean isEmpty();
	
	boolean BFS(E src) throws Exception;
	
	void DFS();
	
	void DFS(E src);
	
	void Dijkstra(E src) ;
	
	void FloydWarshall() ;

	ArrayList<E> getPath(E dst);
	
	int getSingleSourceDistance(E dst);
	
	int getDFSDiscoveredTime(E key);
	
	int getDFSFinishedTime(E key);
	
	State getVertexColor(E key);
	
	E getElement();
	
	E getSingleSOurcePredecessor(E key);
}
