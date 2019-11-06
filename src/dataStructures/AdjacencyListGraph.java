package dataStructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;

import dataStructures.Vertex.Color;


public class AdjacencyListGraph<E> implements IGraph<E>{
	
	/**It represents a hash map from E objects to vertices of E type.
	 */
	private HashMap<E, Vertex<E>> vertices;
	/**It represents a hash map from E objects to ArrayList of AdjacencyListEdge<E> type.
	 */
	private HashMap<E, ArrayList<Edge<E>>> adjacencyLists;

	/**It indicates if the graph is directed or not.
	 */
	private boolean isDirected;
	/**It represents the source vertex that is in the final position of the path. 
	 */
	private Vertex<E> lastSrc;
	/**It represents how many time DFS process lasts. 
	 */
	private static int DFStime;
	/**It represents a modified Adjacency List in order to perform FloydWarshall correctly.
	 */
	private AdjacencyMatrixGraph<E> graphForWarshall; 	
	
	/**It creates a list as a array of edges of a graph either directed or undirected.
	 * @param isDirected is a boolean that indicates if the Adjacency List is going to represent a directed graph or not.
	 */
	public AdjacencyListGraph(boolean isDirected) {
		this.isDirected = isDirected;
		vertices = new HashMap<>();
		adjacencyLists = new HashMap<>();
	}
	/**This inserts a vertex, defined with a E object that arrives as parameter, in a free space and verifies if the insertion process 
	 * was done rightly.
	 * @return A boolean that indicates if the insertion process was done rightly.
	 * @param e is a vertex that is going to be inserted inside the list. 
	 */
	@Override
	public boolean insertVertex(E e) {
		if(!vertices.containsKey(e)) {
			vertices.put(e, new Vertex<E>(e));
			adjacencyLists.put(e, new ArrayList<>());
			return true;
		}
		return false;
	}
	/**This deletes a vertex, defined with a E object that arrives as parameter, looking for the respective key inside the list and verifies if 
	 * the deletion process was done rightly. Moreover, the vertex associated edges are deleted too.
	 * @return A boolean that indicates if the deletion process was done rightly.
	 * @param sk is a vertex that is going to be erased from the list. 
	 */
	@Override
	public boolean deleteVertex(E sk) {
		if(vertices.containsKey(sk)) {
			vertices.remove(sk); //remove the vertex itself
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //and remove all edges where it is dst
				@Override
				public void accept(E t, Vertex<E> u) {
					ArrayList<Edge<E>> toremove = new ArrayList<>();
					for(Edge<E> ale : adjacencyLists.get(t)) {
						if(ale.getDst().equals(sk)) {
							toremove.add(ale);
						}
					}
					adjacencyLists.get(t).removeAll(toremove);
				}
			});

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
		Vertex<E> s = vertices.get(src);
		Vertex<E> d = vertices.get(dst);
		Edge<E> newedge1 = new Edge<>(s.getElement(), d.getElement(), weight);

		ArrayList<Edge<E>> sEdges = adjacencyLists.get(src);
		sEdges.remove(newedge1); //if the edge already exists remove it
		sEdges.add(newedge1);
		if(!isDirected) { //Add the additional edge if this graph is undirected
			Edge<E> newedge2 = new Edge<>(d.getElement(), s.getElement(), weight);

			ArrayList<Edge<E>> dEdges = adjacencyLists.get(dst);
			dEdges.remove(newedge2); //if the edge already exists remove it
			dEdges.add(newedge2); 
		}
	}
	/**This method unlinks a source vertex with another vertex denoted as dst where the other edge is removed if the graph is undirected. 
	 * @return A boolean that indicates if the unlinking process was done correctly.
	 * @param src is an E object that represents the source vertex from the dst vertex is going to be unlinked. 
	 * @param dst is an E object that represents the vertex which src is going to be unlinked with.
	 */
	@Override
	public boolean unlink(E src, E dst) {
		Vertex<E> s = vertices.get(src);
		Vertex<E> d = vertices.get(dst);
		if(s != null && d != null) {
			adjacencyLists.get(src).remove(new Edge<E>(s.getElement(), d.getElement(), 1)); //remove edge (s,d)
			if(!isDirected) { //Remove the other edge if this graph is undirected
				adjacencyLists.get(dst).remove(new Edge<E>(d.getElement(), s.getElement(), 1));
			}
			return true;
		}
		return false;
	}
	/**It verifies if the actual vertices contains a key of E type that arrives as parameter.
	 * @return A boolean that indicates if the actual vertices contains a key of E type that arrives as parameter.
	 * @param key is vertex that is going to be searched inside the vertices list. 
	 */
	@Override
	public boolean containsVertex(E key) {
		return vertices.containsKey(key);
	}
	/**It allows to get the order as an integer that represents the actual vertices size.
	 * @return An Integer that represents the actual vertices list size.
	 */
	@Override
	public int getOrder() {
		return vertices.size();
	}
	/**It verifies if the actual list of vertices is empty or not.
	 * @return A boolean that indicates if the actual list of vertices is empty or not.
	 */
	@Override
	public boolean isEmpty() {
		return vertices.isEmpty();
	}
	/**This performs BFS as from a source vertex where the vertices configuration is going to be fixed in order to BFS can be finished right.
	 * @return A boolean that indicates if BSF was done correctly. 
	 * @param src is a reference vertex to do BFS.
	 */
	//traverses all vertices reachable from src
	@Override
	public boolean BFS(E src) {
		if(vertices.containsKey(src)) {
			Vertex<E> s = vertices.get(src);
			lastSrc = s;
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make BFS
				@Override
				public void accept(E e, Vertex<E> u) {
					u.setColor(Color.WHITE);
					u.setDistance(Integer.MAX_VALUE);
					u.setPredecessor(null);
				}
			});
			s.setColor(Color.GRAY);
			s.setDistance(0);
			//s.predecessor is already null so skip that step
			Queue<Vertex<E>> queue = new Queue<>();
			queue.enqueue(s);
			try {
				while(!queue.isEmpty()) {
					Vertex<E> u = queue.dequeue();
					ArrayList<Edge<E>> adj = adjacencyLists.get(u.getElement());
					for(Edge<E> ale: adj) {
						Vertex<E> v = vertices.get(ale.getDst());
						if(v.getColor() == Color.WHITE) {
							v.setColor(Color.GRAY);
							v.setDistance(u.getDistance()+1);
							v.setPredecessor(u);
							queue.enqueue(v);
						}
					}
					u.setColor(Color.BLACK);
				}
			} catch (Exception emptyQueueException) {
				//-_-
			}
			return true;
		}
		return false;
	}
	/**This method returns an ArrayList of vertices that represents the single path for a specified vertex that arrive as parameter if and only if
	 * bfs, dfs or dijkstra have been called before to determinate this path where if the ArrayList is empty is because there is no possible path
	 * to reach that vertex.
	 * @return An ArrayList<E> that represent a single path from vertex dst. 
	 * @param dst is a vertex that indicates from where the path have to be built.
	 */
	//pre: bfs, dfs or dijkstra have been called
	//it is only the shortest path in unweighted graphs, else is just a path
	//it is the least stops path
	//returns empty arraylist if the dst vertex was not reachable from lastSrcInBST, or if dst == null
	@Override
	public ArrayList<E> getSingleSourcePath(E dst) {
		Vertex<E> d = vertices.get(dst);
		ArrayList<E> path = new ArrayList<E>();
		if(d != null && d.getPredecessor() != null) {
			pathFill(lastSrc, d, path);
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
	private void pathFill(Vertex<E> src, Vertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			pathFill(src, dst.getPredecessor(), path);
			path.add(dst.getElement());
		}
	}
	/**This performs DFS which is recursive and traverses every vertex independent if it is not reachable from certain vertices
	 *where is needed a stack of recursive calls in DFSVisit method. Moreover, some vertices configuration have to fixed in 
	 * order to complete DFS rightly.
	 */
	//dfs that traverses every vertex independent if it is not reachable from certain vertices
	//it uses stack of recursive calls in dfsvisit method
	@Override
	public void DFS() {
		vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make DFS
			@Override
			public void accept(E e, Vertex<E> u) {
				u.setColor(Color.WHITE);
				u.setPredecessor(null);
			}
		});
		DFStime = 0;
		vertices.forEach(new BiConsumer<E, Vertex<E>>() {
			@Override
			public void accept(E e, Vertex<E> u) {
				if(u.getColor() == Color.WHITE) {
					DFSVisit(u);
				}
			}
		});
	}
	/**This performs the DFS visit process recursively as from a vertex that arrives as parameter where every reachable vertex from u is going
	 * to be traversed. 
	 * @param u is a Vertex<E> that represents the initial point of DFS visit process.
	 */
	//recursive method for traversing every reachable vertex from u
	private void DFSVisit(Vertex<E> u) {
		DFStime++;
		u.setDiscovered(DFStime);
		u.setColor(Color.GRAY);
		ArrayList<Edge<E>> adj = adjacencyLists.get(u.getElement());
		for(Edge<E> ale : adj) {
			Vertex<E> v = vertices.get(ale.getDst());
			if(v.getColor() == Color.WHITE) {
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
	//iterative dfs using stack data structure
	//it does not traverses all the vertices as the main implementation of dfs, instead
	//it traverses the reachable vertices starting from src
	@Override
	public void DFS(E src) {
		if(vertices.containsKey(src)) {
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make DFS
				@Override
				public void accept(E e, Vertex<E> u) {
					u.setDiscovered(0);
					u.setFinished(0);
					u.setColor(Color.WHITE);
					u.setPredecessor(null);
				}
			});
			DFStime = 1;
			Vertex<E> s = vertices.get(src);
			s.setColor(Color.GRAY);
			s.setDiscovered(DFStime);
			//s.predecessor is already null so skip that step
			Stack<Vertex<E>> stack = new Stack<>();
			stack.push(s);
			while(!stack.isEmpty()) {
				Vertex<E> u = stack.pop();
				ArrayList<Edge<E>> adj = adjacencyLists.get(u.getElement());
				for(Edge<E> ale: adj) {
					Vertex<E> v = vertices.get(ale.getDst());
					if(v.getColor() == Color.WHITE) {
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
		if(vertices.containsKey(src)) {
			lastSrc = vertices.get(src);
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make Dijkstra
				@Override
				public void accept(E t, Vertex<E> u) {
					u.setDistance(Integer.MAX_VALUE);
					u.setPredecessor(null);
					pq.offer(u);
				}
			});
			pq.remove(lastSrc);
			lastSrc.setDistance(0);
			pq.offer(lastSrc);
			while(!pq.isEmpty()) {
				Vertex<E> u = pq.poll();
				for(Edge<E> ale : adjacencyLists.get(u.getElement())) {
					Vertex<E> s = vertices.get(ale.getSrc());
					Vertex<E> d = vertices.get(ale.getDst());
					if(d.getDistance() > s.getDistance() + ale.getWeight()) {
						pq.remove(ale.getDst());
						d.setDistance(s.getDistance() + ale.getWeight());
						d.setPredecessor(s);
						pq.offer(d);
					}
				}
			}
		}
	}
	/**This performs FloydWarshall which find the shortest distance between every pair of vertices. 
	 */
	@Override
	public void FloydWarshall() {
		graphForWarshall = new AdjacencyMatrixGraph<>(vertices.size(), isDirected);
		vertices.forEach(new BiConsumer<E, Vertex<E>> () {
			@Override
			public void accept(E t, Vertex<E> u) {
				graphForWarshall.insertVertex(t);
			}
		});
		adjacencyLists.forEach(new BiConsumer<E, ArrayList<Edge<E>>>() {
			@Override
			public void accept(E t, ArrayList<Edge<E>> u) {
				for(Edge<E> ale : u) {
					graphForWarshall.link(ale.getSrc(), ale.getDst(), ale.getWeight());
				}
			}
		});
		graphForWarshall.FloydWarshall();
	}
	/**It allows to obtain a boolean that represents if the graph is directed or not.
	 * @return A boolean that indicates if the graph is directed or not.
	 */
	public boolean isDirected() {
		return isDirected;
	}
	/**It returns the last source vertex inside the graph. 
	 * @return An E object that represents the last source vertex inside the graph.
	 */
	public E getLastSrc() {
		return lastSrc.getElement();
	}
	/**It allows to get all the actual graph vertices in a ArrayList. 
	 * @return An ArrayList of E type with all the actual graph vertices.
	 */
	public ArrayList<E> getVertices() {
		ArrayList<E> verts = new ArrayList<>();
		vertices.forEach(new BiConsumer<E, Vertex<E>>() {
			@Override
			public void accept(E t, Vertex<E> u) {
				verts.add(t);	
			}
		});
		return verts;
	}
	/**It allows to obtain the distance of a single vertex specified as parameter. Returns infinite if dst does not exist in the vertices list. 
	 * @return An Integer the distance of the entire single path of dst. 
	 * @param dst is a vertex from where distance is going to be calculated. 
	 */
	@Override
	public int getSingleSourceDistance(E dst) {
		if(vertices.containsKey(dst)) {
			return vertices.get(dst).getDistance();
		}
		return Integer.MAX_VALUE;
	}
	/**It allows to obtain the time that DFS performs discovering a vertex that arrives as parameter.
	 * @return An Integer that indicates how many time took to discover the inside the list. 
	 * @param key is a vertex that is going to be discovered inside the list. 
	 */
	@Override
	public int getDFSDiscoveredTime(E key) {
		if(vertices.containsKey(key)) {
			return vertices.get(key).getDiscovered();
		}
		return 0;
	}
	/**It allows to obtain the time that DFS performs finishing a vertex that arrives as parameter.
	 * @return An Integer that indicates how many time took to finish the inside the list. 
	 * @param key is a vertex that is going to be finished inside the list. 
	 */
	@Override
	public int getDFSFinishedTime(E key) {
		if(vertices.containsKey(key)) {
			return vertices.get(key).getFinished();
		}
		return 0;
	}
	/**It allows to obtain the key state as a color. 
	 * @return A State that indicates the vertex color. 
	 * @param key is a vertex which its color is going to be extracted. 
	 */
	@Override
	public Color getVertexColor(E key) {
		if(vertices.containsKey(key)) {
			return vertices.get(key).getColor();
		}
		return null;
	}
	/**It allows to obtain a single predecessor of a determinate vertex that arrives as parameter.
	 * @return A E object that represents the single predecessor. 
	 * @param key is a ordinary vertex of the list. 
	 */
	@Override
	public E getSingleSourcePredecessor(E key) {
		if(vertices.containsKey(key) && vertices.get(key).getPredecessor() != null) {
			return vertices.get(key).getPredecessor().getElement();
		}
		return null;
	}
	/**It allows to obtain the path between two pair of vertices through an ArrayList of vertices.
	 * @return An ArrayList<E> that represents the path between src and dst. 
	 * @param src is a vertex from which we can access to dst. 
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public ArrayList<E> getPath(E src, E dst) {
		return graphForWarshall.getPath(src, dst);
	}
	/**It allows to get the distance between two pairs of vertices.
	 * @return An Integer that represents the distance between two pairs of vertices.
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public int getDistance(E src, E dst) {
		return graphForWarshall.getDistance(src, dst);
	}
	/**It allows to determinate if two pairs of vertices have and edge in common.
	 * @return A boolean that indicates if the list contains this edge.
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public boolean containsEdge(E src, E dst) {
		if(vertices.containsKey(src) && vertices.containsKey(dst)) {
			return adjacencyLists.get(src).contains(new Edge<E>(src, dst, Integer.MAX_VALUE));
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
		if(vertices.containsKey(key)) {
			for(Edge<E> ale : adjacencyLists.get(key)) {
				adj.add(ale.getDst());
			}
		}
		return adj;
	}
	/**It allows to get the weight between two pairs of vertices that arrive as parameters. Returns infinite if there is no edge between these 
	 * two vertices.
	 * @return An Integer that represents the cost of the entire edge formed by src and dst. 
	 * @param src is a vertex from which we can access to dst.
	 * @param dst is the vertex which dst can access.
	 */
	@Override
	public int getEdgeWeight(E src, E dst) {
		if(vertices.containsKey(src) && vertices.containsKey(dst)) {
			ArrayList<Edge<E>> srcAdj = adjacencyLists.get(src);
			for(int i = 0; i < srcAdj.size(); i++) {
				if(srcAdj.get(i).getDst().equals(dst)) {
					return srcAdj.get(i).getWeight();
				}
			}
			if(src.equals(dst)) {
				return 0;
			}
		}
		return Integer.MAX_VALUE;
	}
	/**This performs Prim which finds the minimum spanning tree edges of a graph when this is connected and there is a source vertex. 
	 * @return An ArrayList<Edge<E>> which has all the edges of the minimum spanning tree.
	 * @param src is a reference vertex to start Prim process.
	 */
	@Override
	public ArrayList<Edge<E>> primMinimumSpanningTree(E src) {
		ArrayList<Edge<E>> prim = new ArrayList<Edge<E>>();
		if(vertices.containsKey(src)) {
			lastSrc = vertices.get(src);
			PriorityQueue<Vertex<E>> pq = new PriorityQueue<Vertex<E>>();
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make Prim
				@Override
				public void accept(E t, Vertex<E> u) {
					u.setDistance(Integer.MAX_VALUE);
					u.setColor(Color.WHITE);
					u.setPredecessor(null);
					pq.offer(u);
				}
			});
			pq.remove(lastSrc);
			lastSrc.setDistance(0);
			pq.offer(lastSrc);
			
			while(!pq.isEmpty()) {
				Vertex<E> u = pq.poll();
				for(Edge<E> ale : adjacencyLists.get(u.getElement())) {
					Vertex<E> s = vertices.get(ale.getSrc());
					Vertex<E> d = vertices.get(ale.getDst());
					if(d.getColor() == Color.WHITE && d.getDistance() > ale.getWeight()) {
						pq.remove(d);
						Vertex<E> pred = d.getPredecessor();
						if(pred != null) { //remove the edge that has ale.dst as dst vertex
							Edge<E> edgeToRemove = new Edge<>(pred.getElement(), ale.getDst(), 1);
							prim.remove(edgeToRemove);
						}
						d.setDistance(ale.getWeight());
						d.setPredecessor(s);
						pq.offer(d);
						prim.add(ale);
					}
				}
				u.setColor(Color.BLACK);
			}
		}
		return prim;
	}
	/**This performs Kruskal which finds the minimum spanning tree edges of a graph regardless of this is connected or not.
	 * @return An ArrayList<Edge<E>> which has all the edges of the minimum spanning tree.
	 */
	@Override
	public ArrayList<Edge<E>> kruskalMinimumSpannigTree() {
		UnionFind<E> uf = new UnionFind<>(getVertices());
		ArrayList<Edge<E>> kruskal = new ArrayList<>();
		ArrayList<Edge<E>> edges = new ArrayList<>();
		for(ArrayList<Edge<E>> adjList : adjacencyLists.values()) {
			for(Edge<E> edge : adjList) {
				if(!edges.contains(new Edge<>(edge.getDst(), edge.getSrc(), 1))) {
					edges.add(edge);
				}
			}
		}
		Collections.sort((List) edges);
		for(int i = 0; i < edges.size(); i++) {
			Edge<E> edge = edges.get(i);
			if(uf.find(edge.getSrc()) != uf.find(edge.getDst())) {
				uf.union(edge.getSrc(), edge.getDst());
				kruskal.add(edge);
			}
		}
		return kruskal;
	}
}
