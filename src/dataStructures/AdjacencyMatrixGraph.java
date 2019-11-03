package dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import dataStructures.Vertex.Color;


public class AdjacencyMatrixGraph<E> implements IGraph<E> {
	
	/**It is a matrix of integers that represents all the graph edges. 
	 */
	private int[][] edges;
	/**It indicates if the graph is directed or not.
	 */
	private boolean isDirected;
	/**It represents a hash map from E objects to integers.
	 */
	private HashMap<E, Integer> keyToIndex;
	/**It is a matrix of vertices that represents the all the graph vertices.
	 */
	private Vertex<E>[] vertices;
	/**It represents the source vertex that is in the final position of the path.
	 */
	private Vertex<E> lastSrc;
	/**It represents a free row where a new vertex can be allocated.
	 */
	private int freeRow;
	/**It represents how many time DFS process lasts.
	 */
	private static int DFStime;
	/**It is a matrix of integers that represents the minimum distance of all pairs of vertices. 
	 */
	private int[][] allPairsminimumDistances;
	/**It is a matrix of vertices that indicates the shortest path of all pairs of vertices. 
	 */
	private Vertex<E>[][] allPairsShortestPath;
	
	/**It creates a matrix as a array of edges of a graph either directed or undirected with a size that arrives as parameter.
	 * @param size is an Integer that represents the size of the Adjacency Matrix. 
	 * @param isDIrected is a boolean that indicates if the Adjacency Matrix is going to represent a directed graph or not.
	 */
	public AdjacencyMatrixGraph(int size, boolean isDIrected) {
		edges = new int[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				edges[i][j] = Integer.MAX_VALUE;
			}
			edges[i][i] = 0;
		}
		this.isDirected = isDIrected;
		keyToIndex = new HashMap<>();
		vertices = (Vertex<E>[])new Vertex[size];
		freeRow = 0;
	}
	/**This inserts a vertex, defined with an element of type E that arrives as parameter, in a free row and verifies if the insertion process 
	 * was done rightly. 
	 * @return A boolean that indicates if the insertion process was done rightly.
	 * @param e is a vertex that is going to be inserted inside the matrix. 
	 */
	@Override
	public boolean insertVertex(E e) {
		if(!keyToIndex.containsKey(e)) {
			keyToIndex.put(e, freeRow);
			vertices[freeRow] = new Vertex<>(e);
			freeRow++;
			if(freeRow >= edges.length) {
				int[][] newedges = new int[edges.length+10][edges.length+10];
				Vertex<E>[] newvertices = (Vertex<E>[])new Vertex[edges.length+10];
				for(int i = 0; i < newedges.length; i++) {
					if(i < vertices.length) {
						newvertices[i] = vertices[i];
					}
					for (int j = 0; j < newedges.length; j++) {
						newedges[i][j] = i==j?0:Integer.MAX_VALUE;
						if(i < edges.length && j < edges.length) {
							newedges[i][j] = edges[i][j];
						}
					}
				}
				edges = newedges;
				vertices = newvertices;
			}
			return true;
		}
		return false;
	}
	/**This deletes a vertex, defined with a E object that arrives as parameter, looking for the respective key inside the matrix and verifies if 
	 * the deletion process was done rightly.
	 * @return A boolean that indicates if the deletion process was done rightly.
	 * @param sk is a vertex that is going to be erased from the matrix.
	 */
	@Override
	public boolean deleteVertex(E e) {
		if(keyToIndex.containsKey(e)) {
			int indexToDelete = keyToIndex.get(e);
			vertices[indexToDelete] = vertices[keyToIndex.size()-1];
			vertices[keyToIndex.size()-1] = null;
			for(int i = 0; i < keyToIndex.size(); i++) { 
				edges[indexToDelete][i] = edges[keyToIndex.size()-1][i];
				edges[keyToIndex.size()-1][i] = Integer.MAX_VALUE;
				edges[i][indexToDelete] = edges[i][keyToIndex.size()-1];
				edges[i][keyToIndex.size()-1] = Integer.MAX_VALUE;
			}

			freeRow--;
			if(freeRow > 0 && indexToDelete < keyToIndex.size()-1) {
				keyToIndex.put(vertices[indexToDelete].getElement(), indexToDelete);
			}
			keyToIndex.remove(e);
			return true;
		}
		return false;
	}
	/**This method links a source vertex with another vertex denoted as dst assigning it a weight to their respective connection where a edge is 
	 * added if the graph is undirected. 
	 * @param src is an E object that represents the source vertex from the dst vertex is going to be linked. 
	 * @param dst is an E object that represents the vertex which src is going to be linked with.
	 * @param weight is an integer that represents the cost of arriving from src to dst.
	 */
	@Override
	public void link(E src, E dst, int weight) {

		Integer s = keyToIndex.get(src);
		Integer d = keyToIndex.get(dst);
		if(s != null && d != null) {
			edges[s][d] = weight;
			if(!isDirected) { 
				edges[d][s] = weight;
			}

		insertVertex(src); //Inserts src if not currently in the graph
		insertVertex(dst); //Inserts dst if not currently in the graph
		s = keyToIndex.get(src);
		d = keyToIndex.get(dst);
		edges[s][d] = weight;
		if(!isDirected) { //Add the additional edge if this graph is undirected
			edges[d][s] = weight;
		}
	}
}
	/**This method unlinks a source vertex with another vertex denoted as dst where the other edge is removed if the graph is undirected.
	 * @return A boolean that indicates if the unlinking process was done correctly.
	 * @param src is an E object that represents the source vertex from the dst vertex is going to be unlinked. 
	 * @param dst is an E object that represents the vertex which src is going to be unlinked with. 
	 */
	@Override
	public boolean unlink(E src, E dst) {
		Integer s = keyToIndex.get(src);
		Integer d = keyToIndex.get(dst);
		if(s != null && d != null) {
			edges[s][d] = Integer.MAX_VALUE;
			if(!isDirected) { //Remove the other edge if this graph is undirected
				edges[d][s] = Integer.MAX_VALUE;
			}
			return true;
		}
		return false;
	}
	/**It verifies if the actual keyToIndex contains a key of E type that arrives as parameter.
	 * @return A boolean that indicates if the actual vertices contains a key of E type that arrives as parameter.
	 * @param key is vertex that is going to be searched inside keyToIndex.
	 */
	@Override
	public boolean containsVertex(E key) {
		return keyToIndex.containsKey(key);
	}
	/**It allows to get the order as an integer that represents the actual keyToIndex size.
	 * @return An Integer that represents the actual keyToIndex size.
	 */
	@Override
	public int getOrder() {
		return keyToIndex.size();
	}
	/**It verifies if the actual keyToIndex is empty or not.
	 * @return A boolean that indicates if the actual keyToIndex is empty or not.
	 */
	@Override
	public boolean isEmpty() {
		return keyToIndex.isEmpty();
	}
	/**This performs BFS as from a source vertex where the vertices configuration is going to be fixed in order to BFS can be finished right.
	 * @return A boolean that indicates if BSF was done correctly. 
	 * @param src is a reference vertex to do BFS. 
	 */
	@Override
	public boolean BFS(E src) {
		if(keyToIndex.containsKey(src)) {
			Vertex<E> s = vertices[keyToIndex.get(src)];
			lastSrc = s;
			for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make BFS
				vertices[i].setColor(Color.WHITE);
				vertices[i].setDistance(Integer.MAX_VALUE);
				vertices[i].setPredecessor(null);
			}
			s.setColor(Color.GRAY);
			s.setDistance(0);
			//s.predecessor is already null so skip that step
			Queue<Vertex<E>> queue = new Queue<>();
			queue.enqueue(s);
			try {
				while(!queue.isEmpty()) {
					Vertex<E> u = queue.dequeue();
					int[] adj = edges[keyToIndex.get(u.getElement())];
					for(int i = 0; i < vertices.length; i++) {
						Vertex<E> v = vertices[i];
						if(u != v && adj[i] != Integer.MAX_VALUE && v.getColor() == Color.WHITE) { // no self loop && edge exists && no visited yet
							v.setColor(Color.GRAY);
							v.setDistance(u.getDistance()+1);
							v.setPredecessor(u);
							queue.enqueue(v);
						}
					}
					u.setColor(Color.BLACK);
				}
			} catch(Exception emptyQueueException) {
				//-_-
			}
			return true;
		}
		return false;
	}
	/**This method returns an ArrayList of vertices that represents the single path for a specified vertex that arrive as parameter if and only if
	 * bfs, dfs or dijkstra have been called before to determinate this path where if the ArrayList is empty is because there is no possible path
	 * to reach that vertex.
	 * @param src is a reference vertex to start DFS process.
	 */
	//pre: bfs, dfs or dijkstra have been called
	//it is only the shortest path in unweighted graphs, else is just a path
	//it is the least stops path
	//returns empty arraylist if the dst vertex was not reachable from lastSrcInBST, or if dst == null
	@Override
	public ArrayList<E> getSingleSourcePath(E dst) {
		Vertex<E> d = vertices[keyToIndex.get(dst)];
		ArrayList<E> path = new ArrayList<E>();
		if(d != null && d.getPredecessor() != null) {
			singleSourcePathFill(lastSrc, d, path);
		} else if(d == lastSrc && d != null) {
			path.add(d.getElement());
		}

		return path;
	}
	/**This method appoints a path between two vertices that arrive as parameters inside a ArrayList.
	 * @param src A Vertex<E> that represents the source where dst is associated.
	 * @param dst A Vertex<E> that represents the vertex which src is connected with.
	 * @param path An ArrayList<E> that contains a path between src and dst.
	 */
	private void singleSourcePathFill(Vertex<E> src, Vertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			singleSourcePathFill(src, dst.getPredecessor(), path);
			path.add(dst.getElement());
		}
	}
	/**This performs DFS which is recursive and traverses every vertex independent if it is not reachable from certain vertices
	 *where is needed a stack of recursive calls in DFSVisit method. Moreover, some vertices configuration have to fixed in 
	 * order to complete DFS rightly.
	 */
	@Override
	public void DFS() {
		for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make DFS
			vertices[i].setColor(Color.WHITE);
			vertices[i].setPredecessor(null);
		}
		DFStime = 0;
		for(int i = 0; i < keyToIndex.size(); i++) {
			if(vertices[i].getColor() == Color.WHITE) {
				DFSVisit(vertices[i]);
			}
		}
	}
	/**This performs the DFS visit process recursively as from a vertex that arrives as parameter where every reachable vertex from u is going
	 * to be traversed. 
	 * @param u is a Vertex<E> that represents the initial point of DFS visit process.
	 */
	private void DFSVisit(Vertex<E> u) {
		DFStime++;
		u.setDiscovered(DFStime);
		u.setColor(Color.GRAY);
		int uIndex = keyToIndex.get(u.getElement());
		for(int i = 0; i < keyToIndex.size(); i++) {
			Vertex<E> v = vertices[i];
			int vIndex = keyToIndex.get(v.getElement());
			if(edges[uIndex][vIndex] != Integer.MAX_VALUE && v.getColor() == Color.WHITE) { //edge exists && vertex has not been visited
				v.setPredecessor(u);
				DFSVisit(v);
			}
		}
		u.setColor(Color.BLACK);
		DFStime++;
		u.setFinished(DFStime);
	}
	/**This performs the DFS taking into account a source vertex that arrives as parameter where some vertices configuration have to fixed in 
	 * order to complete DFS rightly.
	 * @param src is a reference vertex to start DFS process.
	 */
	@Override
	public void DFS(E src) {
		if(keyToIndex.containsKey(src)) {
			for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make DFS
				vertices[i].setColor(Color.WHITE);
				vertices[i].setPredecessor(null);
			}
			int uIndex = keyToIndex.get(src);
			DFStime = 1;
			Vertex<E> s = vertices[uIndex];
			s.setColor(Color.GRAY);
			s.setDiscovered(DFStime);
			//s.predecessor is already null so skip that step
			Stack<Vertex<E>> stack = new Stack<>();
			stack.push(s);
			while(!stack.isEmpty()) {
				Vertex<E> u = stack.pop();
				for(int i = 0; i < keyToIndex.size(); i++) {
					Vertex<E> v = vertices[i];
					int vIndex = keyToIndex.get(v.getElement());
					if(edges[uIndex][vIndex] != Integer.MAX_VALUE && v.getColor() == Color.WHITE) { //edge exists && vertex has not been visited
						DFStime++;
						v.setColor(Color.GRAY);
						v.setDiscovered(DFStime);
						v.setPredecessor(u);
						stack.push(v);
					}
				}
				u.setColor(Color.BLACK);
				DFStime++;
				u.setFinished(DFStime);
			}
		}
	}
	/**This performs Dijkstra as from a source vertex that arrives as parameter where some vertices configuration have to fixed in 
	 * order to complete Dijkstra rightly.
	 * @param src is a reference vertex to start Dijkstra process.
	 */
	@Override
	public void Dijkstra(E src) {
		PriorityQueue<Vertex<E>> pq = new PriorityQueue<Vertex<E>>();
		if(keyToIndex.containsKey(src)) {
			Vertex<E> s = vertices[keyToIndex.get(src)];
			lastSrc = s;
			for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make Dijkstra
				vertices[i].setDistance(Integer.MAX_VALUE);
				vertices[i].setPredecessor(null);
				pq.offer(vertices[i]);
			}
			pq.remove(lastSrc);
			lastSrc.setDistance(0);
			pq.offer(lastSrc);
			while(!pq.isEmpty()) {
				Vertex<E> u = pq.poll();
				int uIndex = keyToIndex.get(u.getElement());
				for(int i = 0; i < keyToIndex.size(); i++) {
					if(edges[uIndex][i] != Integer.MAX_VALUE && vertices[i].getDistance() > vertices[uIndex].getDistance() + edges[uIndex][i]) { //edge exists && the current shortest path can be improved
						pq.remove(vertices[i]);
						vertices[i].setDistance(vertices[uIndex].getDistance() + edges[uIndex][i]);
						vertices[i].setPredecessor(vertices[uIndex]);
						pq.offer(vertices[i]);
					}
				}
			}
		}
	}
	/**This performs FloydWarshall which find the shortest distances and paths between every pair of vertices. 
	 */
	@Override
	public void FloydWarshall() {
		int[][] d1 = new int[keyToIndex.size()][keyToIndex.size()];
		Vertex<E>[][] p1 = (Vertex<E>[][])new Vertex[d1.length][d1.length];
		for(int i = 0; i < d1.length; i++) {
			for (int j = 0; j < d1[i].length; j++) {
				d1[i][j] = edges[i][j];
				p1[i][j] = (edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE) ? vertices[i]: null;
			}
		}

		for(int k = 0; k < keyToIndex.size(); k++) {
			allPairsminimumDistances = d1.clone();
			allPairsShortestPath = p1.clone();
			for(int i = 0; i < keyToIndex.size(); i++) {
				for(int j = 0; j < keyToIndex.size(); j++) {
					if((long)d1[i][j] > (long)d1[i][k] + (long)d1[k][j]) {
						allPairsminimumDistances[i][j] = d1[i][k] + d1[k][j];
						allPairsShortestPath[i][j] = p1[k][j];
					}
				}
			}
		}
	}
	/**It allows to get the weight between two pairs of vertices that arrive as parameters.
	 * @return An Integer that represents the cost of the entire edge formed by src and dst. 
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public int getEdgeWeight(E src, E dst) {
		if(keyToIndex.containsKey(src) && keyToIndex.containsKey(dst)) {
			return edges[keyToIndex.get(src)][keyToIndex.get(dst)];
		}
		return Integer.MAX_VALUE;
	}
	/**It allows to get all the actual graph edges. 
	 * @return An int[][] that represents all the actual graph edges.
	 */
	public int[][] getEdges() {
		return edges;
	}
	/**It allows to obtain a boolean that represents if the graph is directed or not.
	 * @return A boolean that indicates if the graph is directed or not.
	 */
	public boolean isDirected() {
		return isDirected;
	}
	/**It allows to get all the actual graph vertices in a ArrayList. 
	 * @return An ArrayList of E type with all the actual graph vertices.
	 */
	public ArrayList<E> getVertices() {
		ArrayList<E> verts = new ArrayList<E>();
		for(int i = 0; i < keyToIndex.size(); i++) {
			verts.add(vertices[i].getElement());
		}
		return verts;
	}
	/**It returns the last source vertex inside the graph. 
	 * @return An E object that represents the last source vertex inside the graph. 
	 */
	public E getLastSrc() {
		return lastSrc.getElement();
	}
	/**It returns the free row value as an integer.
	 * @return An Integer that represents the free row value.
	 */
	public int getFreeRow() {
		return freeRow;
	}
	/**It allows to obtain the distance of a single vertex specified as parameter.
	 * @return An Integer the distance of the entire single path of dst. 
	 * @param dst is a vertex from where distance is going to be calculated.
	 */
	@Override
	public int getSingleSourceDistance(E dst) {
		if(keyToIndex.containsKey(dst)) {
			return vertices[keyToIndex.get(dst)].getDistance();
		}
		return Integer.MAX_VALUE;
	}
	/**It allows to obtain the time that DFS performs discovering a vertex that arrives as parameter.
	 * @return An Integer that indicates how many time took to discover the inside the matrix. 
	 * @param key is a vertex that is going to be discovered inside the matrix. 
	 */
	@Override
	public int getDFSDiscoveredTime(E key) {
		if(keyToIndex.containsKey(key)) {
			return vertices[keyToIndex.get(key)].getDiscovered();
		}
		return 0;
	}
	/**It allows to obtain the time that DFS performs finishing a vertex that arrives as parameter.
	 * @return An Integer that indicates how many time took to finish the inside the matrix. 
	 * @param key is a vertex that is going to be finished inside the matrix.
	 */
	@Override
	public int getDFSFinishedTime(E key) {
		if(keyToIndex.containsKey(key)) {
			return vertices[keyToIndex.get(key)].getFinished();
		}
		return 0;
	}
	/**It allows to obtain the key state as a color. 
	 * @return A State that indicates the vertex color. 
	 * @param key is a vertex which its color is going to be extracted. 
	 */
	@Override
	public Color getVertexColor(E key) {
		if(keyToIndex.containsKey(key)) {
			return vertices[keyToIndex.get(key)].getColor();
		}
		return null;
	}
	/**It allows to obtain a single predecessor of a determinate vertex that arrives as parameter.
	 * @return A E object that represents the single predecessor. 
	 * @param key is a ordinary vertex of the matrix.  
	 */
	@Override
	public E getSingleSourcePredecessor(E key) {
		if(keyToIndex.containsKey(key) && vertices[keyToIndex.get(key)].getPredecessor() != null) {
			return vertices[keyToIndex.get(key)].getPredecessor().getElement();
		}
		return null;
	}
	/**It allows to obtain the path between two pair of vertices through an ArrayList of vertices.
	 * @return An ArrayList<E> that represents the path between src and dst. 
	 * @param src is a vertex from which we can access to dst. 
	 * @param dst is the vertex which dst can access.
	 * @throws NullPointerException if either src or dst are not elements of the graph
	 */
	@Override
	public ArrayList<E> getPath(E src, E dst) {
		Vertex<E> d = vertices[keyToIndex.get(dst)];
		Vertex<E> s = vertices[keyToIndex.get(src)];
		ArrayList<E> path = new ArrayList<E>();
		if(allPairsShortestPath != null && d != null && s != null) {
			if(allPairsShortestPath[keyToIndex.get(src)][keyToIndex.get(dst)] != null) {
				pathFill(s, d, path);
			} else if(d == s && d != null) {
				path.add(d.getElement());
			}
		}
		return path;
	}
	/**It allows to determinate the path between two pairs of vertices saving it in an ArrayList of vertices.
	 * @param src A Vertex<E> that represents the source where dst is associated.
	 * @param dst A Vertex<E> that represents the vertex which src is connected with.
	 * @param path An ArrayList of vertices that represents the path between two pairs of vertices.
	 */
	private void pathFill(Vertex<E> src, Vertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			pathFill(src, allPairsShortestPath[keyToIndex.get(src.getElement())][keyToIndex.get(dst.getElement())], path);
			path.add(dst.getElement());
		}
	}
	/**It allows to get the distance between two pairs of vertices.
	 * @return An Integer that represents the distance between two pairs of vertices.
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public int getDistance(E src, E dst) {
		if(allPairsminimumDistances != null && keyToIndex.containsKey(src) && keyToIndex.containsKey(dst)) {
			return allPairsminimumDistances[keyToIndex.get(src)][keyToIndex.get(dst)];
		}
		return 0;
	}
	/**It allows to determinate if two pairs of vertices have and edge in common.
	 * @return A boolean that indicates if the list contains this edge.
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public boolean containsEdge(E src, E dst) {
		if(keyToIndex.containsKey(src) && keyToIndex.containsKey(dst)) {
			int i = keyToIndex.get(src);
			int j = keyToIndex.get(dst);
			return edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE;
		}
		return false;
	}
	/**It allows to obtain all the adjacent vertices of a determinate source that arrives as parameter.
	 * @return An ArrayList<E> that represents all the adjacent vertices of a determinate source that arrives as parameter.
	 * @param key is a vertex which its adjacent vertices are going to be found.
	 */
	@Override
	public ArrayList<E> getAdjacent(E key) {
		ArrayList<E> adj = new ArrayList<>();
		if(keyToIndex.containsKey(key)) {
			for(int i = 0; i < keyToIndex.size(); i++) {
				if(edges[keyToIndex.get(key)][i] != 0 && edges[keyToIndex.get(key)][i] != Integer.MAX_VALUE) {
					adj.add(vertices[i].getElement());
				}
			}
		}
		return adj;
	}
	/**
	 * @param src
	 * @return An ArrayList<Edge<E>>
	 */
	@Override
	public ArrayList<Edge<E>> primMinimumSpanningTree(E src) {
		ArrayList<Edge<E>> prim = new ArrayList<Edge<E>>();
		PriorityQueue<Vertex<E>> pq = new PriorityQueue<Vertex<E>>();
		if(keyToIndex.containsKey(src)) {
			Vertex<E> s = vertices[keyToIndex.get(src)];
			lastSrc = s;
			for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make Dijkstra
				vertices[i].setDistance(Integer.MAX_VALUE);
				vertices[i].setColor(Color.WHITE);
				vertices[i].setPredecessor(null);
				pq.offer(vertices[i]);
			}
			pq.remove(lastSrc);
			lastSrc.setDistance(0);
			pq.offer(lastSrc);
			while(!pq.isEmpty()) {
				Vertex<E> u = pq.poll();
				int uIndex = keyToIndex.get(u.getElement());
				for(int i = 0; i < keyToIndex.size(); i++) {
					Edge<E> edge = new Edge<>(u.getElement(), vertices[i].getElement(), edges[uIndex][i]);
					if(u.getColor() == Color.WHITE && vertices[i].getDistance() > edge.getWeight()) { //edge exists && the current shortest path can be improved
						pq.remove(vertices[i]);
						Vertex<E> pred = vertices[keyToIndex.get(edge.getDst())].getPredecessor();
						if (pred != null) {
							Edge<E> edgeToRemove = new Edge<>(pred.getElement(), edge.getDst(),1);
							prim.remove(edgeToRemove);
						}
						vertices[i].setDistance(edge.getWeight());
						vertices[i].setPredecessor(u);
						pq.offer(vertices[i]);
						prim.add(edge);
					}
				}
				u.setColor(Color.BLACK);
			}
		}
		return prim;
	}
	
	@Override
	public ArrayList<Edge<E>> kruskalMinimumSpannigTree() {
		// TODO Auto-generated method stub
		return null;
	}
}
