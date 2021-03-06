package dataStructures;

import java.util.ArrayList;
import java.util.Collections;
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
	/**It represents a hash map from E objects to indexes of the matrix.
	 */
	private HashMap<E, Integer> keyToIndex;
	/**It is an array of vertices that represents all the graph vertices.
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
	private int DFStime;
	/**It is a matrix of integers that represents the minimum distance from all vertices to all vertices. 
	 */
	private int[][] allPairsminimumDistances;
	/**It is a matrix of vertices that stores the shortest path from all vertices to all vertices. 
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
		if(!containsVertex(e)) {
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
	 * @param e is a vertex that is going to be erased from the matrix.
	 */
	@Override
	public boolean deleteVertex(E e) {
		if(containsVertex(e)) {
			int indexToDelete = keyToIndex.get(e);
			vertices[indexToDelete] = vertices[getOrder()-1];
			vertices[getOrder()-1] = null;
			for(int i = 0; i < getOrder(); i++) { 
				edges[indexToDelete][i] = edges[getOrder()-1][i];
				edges[getOrder()-1][i] = Integer.MAX_VALUE;
				edges[i][indexToDelete] = edges[i][getOrder()-1];
				edges[i][getOrder()-1] = Integer.MAX_VALUE;
			}

			freeRow--;
			if(freeRow > 0 && indexToDelete < getOrder()-1) {
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
		insertVertex(src); //Inserts src if not currently in the graph
		insertVertex(dst); //Inserts dst if not currently in the graph
		int s = keyToIndex.get(src);
		int d = keyToIndex.get(dst);
		edges[s][d] = weight;

		if(!isDirected) { //Add the additional edge if this graph is undirected
			edges[d][s] = weight;
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
		if(containsVertex(src)) {
			Vertex<E> s = vertices[keyToIndex.get(src)];
			lastSrc = s;
			for(int i = 0; i < getOrder(); i++) { //Fix the vertices configuration to make BFS
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
	 * @param dst is the destiny vertex to obtain the path from the last source vertex.
	 */
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
			try {
				singleSourcePathFill(src, dst.getPredecessor(), path);
				path.add(dst.getElement());
			} catch(NullPointerException npe) {
				//c:
			}
		}
	}

	/**This performs DFS which is recursive and traverses every vertex independent if it is not reachable from certain vertices
	 *where is needed a stack of recursive calls in DFSVisit method. Moreover, some vertices configuration have to fixed in 
	 * order to complete DFS rightly.
	 */
	@Override
	public void DFS() {
		for(int i = 0; i < getOrder(); i++) { //Fix the vertices configuration to make DFS
			vertices[i].setColor(Color.WHITE);
			vertices[i].setPredecessor(null);
		}
		DFStime = 0;
		for(int i = 0; i < getOrder(); i++) {
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
		for(int i = 0; i < getOrder(); i++) {
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
		if(containsVertex(src)) {
			for(int i = 0; i < getOrder(); i++) { //Fix the vertices configuration to make DFS
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
				for(int i = 0; i < getOrder(); i++) {
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

	/**This performs Dijkstra which finds the shortest path and distances from a vertex to all that can be reached from src. 
	 * @param src is a reference vertex to start Dijkstra process.
	 */
	@Override
	public void Dijkstra(E src) {
		PriorityQueue<Vertex<E>> pq = new PriorityQueue<Vertex<E>>();
		if(containsVertex(src)) {
			Vertex<E> s = vertices[keyToIndex.get(src)];
			lastSrc = s;
			for(int i = 0; i < getOrder(); i++) { //Fix the vertices configuration to make Dijkstra
				vertices[i].setDistance(Integer.MAX_VALUE);
				vertices[i].setPredecessor(null);
				vertices[i].setColor(Color.WHITE);
				pq.offer(vertices[i]);
			}
			pq.remove(lastSrc);
			lastSrc.setDistance(0);
			pq.offer(lastSrc);
			while(!pq.isEmpty()) {
				Vertex<E> u = pq.poll();
				int uIndex = keyToIndex.get(u.getElement());
				for(int i = 0; i < getOrder(); i++) {
					if(edges[uIndex][i] != Integer.MAX_VALUE && vertices[i].getDistance() > (long)vertices[uIndex].getDistance() + (long)edges[uIndex][i]) { //edge exists && the current shortest path can be improved
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
		int[][] d1 = new int[getOrder()][getOrder()];
		Vertex<E>[][] p1 = (Vertex<E>[][])new Vertex[d1.length][d1.length];
		for(int i = 0; i < d1.length; i++) {
			for (int j = 0; j < d1[i].length; j++) {
				d1[i][j] = edges[i][j];
				p1[i][j] = (edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE) ? vertices[i]: null;
			}
		}

		for(int k = 0; k < getOrder(); k++) {
			allPairsminimumDistances = d1.clone();
			allPairsShortestPath = p1.clone();
			for(int i = 0; i < getOrder(); i++) {
				for(int j = 0; j < getOrder(); j++) {
					if((long)d1[i][j] > (long)d1[i][k] + (long)d1[k][j]) {
						allPairsminimumDistances[i][j] = d1[i][k] + d1[k][j];
						allPairsShortestPath[i][j] = p1[k][j];
					}
				}
			}
		}
	}

	/**It allows to get the weight between two pairs of vertices that arrive as parameters. Returns infinite if there is no edge between these 
	 * two vertices.
	 * @return An Integer that represents the cost of the entire edge formed by src and dst. 
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public int getEdgeWeight(E src, E dst) {
		if(containsVertex(src) && containsVertex(dst)) {
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
	@Override
	public ArrayList<E> getVertices() {
		ArrayList<E> verts = new ArrayList<E>();
		for(int i = 0; i < getOrder(); i++) {
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

	/**It allows to obtain the distance of a single vertex specified as parameter. Returns infinite if dst does not exist in the matrix. 
	 * @return An Integer the distance of the entire single path of dst. 
	 * @param dst is a vertex from where distance is going to be calculated. 
	 */
	@Override
	public int getSingleSourceDistance(E dst) {
		if(containsVertex(dst)) {
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
		if(containsVertex(key)) {
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
		if(containsVertex(key)) {
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
		if(containsVertex(key)) {
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
		if(containsVertex(key) && vertices[keyToIndex.get(key)].getPredecessor() != null) {
			return vertices[keyToIndex.get(key)].getPredecessor().getElement();
		}
		return null;
	}

	/**It allows to obtain the path between two pair of vertices through an ArrayList of vertices.
	 * @return An ArrayList that represents the path between src and dst. 
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
		if(allPairsminimumDistances != null && containsVertex(src) && containsVertex(dst)) {
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
		if(containsVertex(src) && containsVertex(dst)) {
			int i = keyToIndex.get(src);
			int j = keyToIndex.get(dst);
			return edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE;
		}
		return false;
	}

	/**It allows to obtain all the adjacent vertices of a determinate source that arrives as parameter.
	 * @return An ArrayList that represents all the adjacent vertices of a determinate source that arrives as parameter.
	 * @param key is a vertex which its adjacent vertices are going to be found.
	 */
	@Override
	public ArrayList<E> getAdjacent(E key) {
		ArrayList<E> adj = new ArrayList<>();
		if(containsVertex(key)) {
			for(int i = 0; i < getOrder(); i++) {
				if(edges[keyToIndex.get(key)][i] != 0 && edges[keyToIndex.get(key)][i] != Integer.MAX_VALUE) {
					adj.add(vertices[i].getElement());
				}
			}
		}
		return adj;
	}

	/**This performs Prim which finds the minimum spanning tree edges of a graph when this is connected and there is a source vertex. 
	 * @return An ArrayList which has all the edges of the minimum spanning tree.
	 * @param src is a reference vertex to start Prim process.
	 */
	@Override
	public ArrayList<Edge<E>> primMinimumSpanningTree(E src) {
		ArrayList<Edge<E>> prim = new ArrayList<Edge<E>>();
		if(containsVertex(src)) {
			lastSrc = vertices[keyToIndex.get(src)];
			PriorityQueue<Vertex<E>> pq = new PriorityQueue<Vertex<E>>();
			for(int i = 0; i < getOrder(); i++) { //Fix the vertices configuration to make Prim
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
				for(int i = 0; i < getOrder(); i++) {
					Vertex<E> v = vertices[i];
					if(uIndex != i && edges[uIndex][i] != 0 && edges[uIndex][i] != Integer.MAX_VALUE && v.getColor() == Color.WHITE && vertices[i].getDistance() > edges[uIndex][i]) { //edge exists && the current shortest path can be improved
						Edge<E> edge = new Edge<>(u.getElement(), vertices[i].getElement(), edges[uIndex][i]);
						pq.remove(v);
						Vertex<E> pred = v.getPredecessor();
						if (pred != null) { //remove the edge that has v as dst vertex
							Edge<E> edgeToRemove = new Edge<>(pred.getElement(), edge.getDst(),1);
							prim.remove(edgeToRemove);
						}
						v.setDistance(edge.getWeight());
						v.setPredecessor(u);
						pq.offer(v);
						prim.add(edge);
					}
				}
				u.setColor(Color.BLACK);
			}
		}
		return prim;
	}

	/**This performs Kruskal which finds the minimum spanning tree edges of a graph regardless of this is connected or not.
	 * @return An ArrayList which has all the edges of the minimum spanning tree.
	 */
	@Override
	public ArrayList<Edge<E>> kruskalMinimumSpannigTree() {
		UnionFind<E> uf = new UnionFind<>(getVertices());
		ArrayList<Edge<E>> kruskal = new ArrayList<>();
		ArrayList<Edge<E>> edgesList = new ArrayList<>();
		//TODO as the matrix is symmetric the running time can be improved by checking only the half of the cells
		for(int i = 0; i < getOrder(); i++) {
			for(int j = 0; j < getOrder(); j++) {
				if(edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE && !edgesList.contains(new Edge<>(vertices[j].getElement(), vertices[i].getElement(), 1))) {
					edgesList.add(new Edge<>(vertices[i].getElement(), vertices[j].getElement(), edges[i][j]));
				}
			}
		}
		Collections.sort(edgesList);
		for(int i = 0; i < edgesList.size(); i++) {
			Edge<E> edge = edgesList.get(i);
			if(uf.find(edge.getSrc()) != uf.find(edge.getDst())) {
				uf.union(edge.getSrc(), edge.getDst());
				kruskal.add(edge);
			}
		}
		return kruskal;
	}
	/**This method allows you to obtain the vertex as from a source vertex and a determinate distance.
	 * @param src is the source vertex. 
	 * @param distance is the distance from the source vertex to the vertex that we need to obtain.
	 * @return E is an E object that represents the found vertex.
	 */
	@Override
	public E getVertexAtGivenDistance(E src, int distance) {
		if(containsVertex(src)) {
			int s = keyToIndex.get(src);
			for(int i = 0; i < getOrder(); i++) {
				if(edges[s][i] == distance) {
					return vertices[i].getElement();
				}
			}
		}
		return null;
	}
}
