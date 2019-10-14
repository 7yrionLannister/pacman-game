package dataStructures;


public interface IGraph<E> {

	boolean insertVertex(E e);
	
	boolean deleteVertex(E e);
	
	void link(E src, E dst, int weight);
	
	boolean unlink(E src, E dst);
	
	AdjacencyListVertex<E> searchVertex(E key);
	
	int getOrder();
	
	boolean isEmpty();
	
	boolean BFS(E src) throws Exception;
	
	void DFS();
	
	void DFS(E src);
	
	void Dijkstra(E src, E dst) ;
	
	void FloydWarshall(E src, E dst) ;
}
