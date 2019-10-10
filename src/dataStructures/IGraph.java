package dataStructures;

import java.util.List;

public interface IGraph<T> {
	
	void insertVertex(T t);
	
	boolean deleteVertex(T sk);
	
	void link(T t1, T t2, int w);
	
	Vertex<T> searchVertex(T sk);
	
	int getOrder();
	
	boolean isEmpty();
	
	void BFS(T src) ;
	
	void DFS(T src) ;
	
	void Dijkstra(T start, T end) ;
	
	void FloydWarshall(T start, T end) ;
	
	List<Vertex<T>> getVertices();
}
