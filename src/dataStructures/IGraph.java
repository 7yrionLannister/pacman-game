package dataStructures;

import java.util.List;

public interface IGraph<T> {
	
	void addVertex(T t);
	
	void link(T t1, T t2, int weight);
	
	Vertex<T> searchVertex(T data) ;
	
	void BFS(T src) ;
	
	void DFS(T src) ;
	
	void Dijkstra(T start, T end) ;
	
	void FloydWarshall(T start, T end) ;
	
	List<Vertex<T>> getVertices();
}
