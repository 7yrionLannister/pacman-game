package dataStructures;

import java.util.ArrayList;

import dataStructures.Vertex.Color;

//IMPOTANT: E must override equals
public interface IGraph<E> {
	
	/**This inserts a vertex, defined with a E object that arrives as parameter, in a free space inside the graph and verifies if the insertion process 
	 * was done rightly.
	 * @return A boolean that indicates if the insertion process was done rightly.
	 * @param e is a vertex that is going to be inserted inside the graph. 
	 */
	boolean insertVertex(E e);
	/**This deletes a vertex, defined with a E object that arrives as parameter, looking for the respective value inside the graph and verifies if 
	 * the deletion process was done rightly. 
	 * @return A boolean that indicates if the deletion process was done rightly.
	 * @param e is a vertex that is going to be erased from the graph. 
	 */
	boolean deleteVertex(E e);
	/**This method links a source vertex with another vertex denoted as dst assigning it a weight to their respective connection where a edge is 
	 * added if the graph is undirected.
	 * @param src is an E object that represents the source vertex from the dst vertex is going to be linked. 
	 * @param dst is an E object that represents the vertex which src is going to be linked with.
	 * @param weight is an integer that represents the cost of arriving from src to dst
	 */
	void link(E src, E dst, int weight);
	/**This method unlinks a source vertex with another vertex denoted as dst where the other edge is removed if the graph is undirected. 
	 * @return A boolean that indicates if the unlinking process was done correctly.
	 * @param src is an E object that represents the source vertex from the dst vertex is going to be unlinked. 
	 * @param dst is an E object that represents the vertex which src is going to be unlinked with.
	 */
	boolean unlink(E src, E dst);
	/**It verifies if the actual graph contains a key of E type that arrives as parameter.
	 * @return A boolean that indicates if the actual graph contains a key of E type that arrives as parameter.
	 * @param key is vertex that is going to be searched inside the graph. 
	 */
	boolean containsVertex(E key);
	/**It allows to get the order as an integer that represents the actual graph size.
	 * @return An Integer that represents the actual graph size.
	 */
	int getOrder();
	/**It verifies if the actual graph is empty or not.
	 * @return A boolean that indicates if the actual graph of vertices is empty or not.
	 */
	boolean isEmpty();
	/**This performs BFS as from a source vertex where the vertices configuration is going to be fixed in order to BFS can be finished right.
	 * @return A boolean that indicates if BSF was done correctly. 
	 * @param src is a reference vertex to do BFS.
	 */
	boolean BFS(E src);
	/**This performs DFS which is iterative and traverses every vertex independent if it is not reachable from certain vertices
	 * where is needed a stack of recursive calls in DFSVisit method. Moreover, some vertices configuration have to fixed in 
	 * order to complete DFS rightly.
	 */
	void DFS();
	/**This performs the DFS taking into account a source vertex that arrives as parameter where some vertices configuration have to fixed in 
	 * order to complete DFS rightly.
	 * @param src is a reference vertex to start DFS process.
	 */
	void DFS(E src);
	/**This performs Dijkstra which finds the shortest path and distances from a vertex to all that can be reached from src. 
	 * @param src is a reference vertex to start Dijkstra process.
	 */
	void Dijkstra(E src) ;
	/**This performs Prim which finds the minimum spanning tree edges of a graph when this is connected and there is a source vertex. 
	 * @return An ArrayList which has all the edges of the minimum spanning tree.
	 * @param src is a reference vertex to start Prim process.
	 */
	ArrayList<Edge<E>> primMinimumSpanningTree(E src);
	/**This performs Kruskal which finds the minimum spanning tree edges of a graph regardless of this is connected or not.
	 * @return An ArrayList which has all the edges of the minimum spanning tree.
	 */
	ArrayList<Edge<E>> kruskalMinimumSpannigTree();

	/**This performs FloydWarshall which find the shortest distance between every pair of vertices.
	 */
	void FloydWarshall() ;
	/**This method returns an ArrayList of vertices that represents the single path for a specified vertex that arrive as parameter if and only if
	 * bfs, dfs or dijkstra have been called before to determinate this path where if the ArrayList is empty is because there is no possible path
	 * to reach that vertex.
	 * @return An ArrayList that represent a single path from vertex dst. 
	 * @param dst is a vertex that indicates from where the path have to be built.
	 */
	ArrayList<E> getSingleSourcePath(E dst);
	/**It allows to obtain the distance of a single vertex specified as parameter. 
	 * @return An Integer the distance of the entire single path of dst. 
	 * @param dst is a vertex from where distance is going to be calculated. 
	 */
	int getSingleSourceDistance(E dst);
	/**It allows to obtain the path between two pair of vertices through an ArrayList of vertices.
	 * @return An ArrayList that represents the path between src and dst. 
	 * @param src is a vertex from which we can access to dst. 
	 * @param dst is the vertex which dst can access.
	 */
	//for the all pairs shortest path
	ArrayList<E> getPath(E src, E dst);
	/**It allows to get the distance between two pairs of vertices.
	 * @return An Integer that represents the distance between two pairs of vertices.
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	//for the all pairs shortest path
	int getDistance(E src, E dst);
	/**It allows to obtain the time that DFS performs discovering a vertex that arrives as parameter.
	 * @return An Integer that indicates how many time took to discover the inside the graph. 
	 * @param key is a vertex that is going to be discovered inside the graph.
	 */
	int getDFSDiscoveredTime(E key);
	/**It allows to obtain the time that DFS performs finishing a vertex that arrives as parameter.
	 * @return An Integer that indicates how many time took to finish the inside the graph. 
	 * @param key is a vertex that is going to be finished inside the graph. 
	 */
	int getDFSFinishedTime(E key);
	/**It allows to obtain the key state as a color. 
	 * @return A State that indicates the vertex color. 
	 * @param key is a vertex which its color is going to be extracted.
	 */
	Color getVertexColor(E key);
	/**It allows to obtain a single predecessor of a determinate vertex that arrives as parameter.
	 * @return A E object that represents the single predecessor. 
	 * @param key is a ordinary vertex of the graph.
	 */
	E getSingleSourcePredecessor(E key);
	/**It allows to determinate if two pairs of vertices have and edge in common.
	 * @return A boolean that indicates if the list contains this edge.
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	boolean containsEdge(E src, E dst);
	/**It allows to obtain all the adjacent vertices of a determinate source that arrives as parameter.
	 * @return An ArrayList that represents all the adjacent vertices of a determinate source that arrives as parameter.
	 * @param key is a vertex which its adjacent vertices are going to be found.
	 */
	ArrayList<E> getAdjacent(E key);
	/**It allows to get the weight between two pairs of vertices that arrive as parameters.
	 * @return An Integer that represents the cost of the entire edge formed by src and dst. 
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	int getEdgeWeight(E src, E dst);
	/**It allows to get all the actual graph vertices in a ArrayList. 
	 * @return An ArrayList of E type with all the actual graph vertices.
	 */
	ArrayList<E> getVertices();
	/**This method allows you to obtain the vertex as from a source vertex and a determinate distance.
	 * @param src is the source vertex. 
	 * @param distance is the distance from the source vertex to the vertex that we need to obtain.
	 * @return E is an E object that represents the found vertex.
	 */
	E getVertexAtGivenDistance(E src, int distance);
}
