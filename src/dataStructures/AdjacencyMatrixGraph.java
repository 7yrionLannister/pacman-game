package dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class AdjacencyMatrixGraph<E> implements IGraph<E> {
	private int[][] edges;
	private boolean isDirected;
	private HashMap<E, Integer> keyToIndex;
	private Vertex<E>[] vertices;
	private Vertex<E> lastSrc;
	private int freeRow;
	private static int DFStime;
	private int[][] allPairsminimumDistances;
	private Vertex<E>[][] allPairsShortestPath;
	
	/**
	 * 
	 * @param size
	 * @param isDIrected
	 */
	//usar size mas grande que los vertices presupuestados para reducir las veces que se debe crear matriz por falta de espacio
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
	/**
	 * 
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
	/**
	 * 
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
	/**
	 * 
	 */
	@Override
	public void link(E src, E dst, int weight) {
		Integer s = keyToIndex.get(src);
		Integer d = keyToIndex.get(dst);
		if(s != null && d != null) {
			edges[s][d] = weight;
			if(!isDirected) { //Add the additional edge if this graph is undirected
				edges[d][s] = weight;
			}
		}
	}
	/**
	 * 
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
	/**
	 * 
	 */
	@Override
	public boolean containsVertex(E key) {
		return keyToIndex.containsKey(key);
	}
	/**
	 * 
	 */
	@Override
	public int getOrder() {
		return keyToIndex.size();
	}
	/**
	 * 
	 */
	@Override
	public boolean isEmpty() {
		return keyToIndex.isEmpty();
	}
	/**
	 * 
	 */
	@Override
	public boolean BFS(E src) {
		if(keyToIndex.containsKey(src)) {
			Vertex<E> s = vertices[keyToIndex.get(src)];
			lastSrc = s;
			for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make BFS
				vertices[i].setColor(State.WHITE);
				vertices[i].setDistance(Integer.MAX_VALUE);
				vertices[i].setPredecessor(null);
			}
			s.setColor(State.GRAY);
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
						if(u != v && adj[i] != Integer.MAX_VALUE && v.getColor() == State.WHITE) { // no self loop && edge exists && no visited yet
							v.setColor(State.GRAY);
							v.setDistance(u.getDistance()+1);
							v.setPredecessor(u);
							queue.enqueue(v);
						}
					}
					u.setColor(State.BLACK);
				}
			} catch(Exception emptyQueueException) {
				//-_-
			}
			return true;
		}
		return false;
	}
	/**
	 * 
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
	/**
	 * 
	 * @param src
	 * @param dst
	 * @param path
	 */
	private void singleSourcePathFill(Vertex<E> src, Vertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			singleSourcePathFill(src, dst.getPredecessor(), path);
			path.add(dst.getElement());
		}
	}
	/**
	 * 
	 */
	//dfs that traverses every vertex independent if it is not reachable from certain vertices
	//it uses stack of recursive calls in dfsvisit method
	@Override
	public void DFS() {
		for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make DFS
			vertices[i].setColor(State.WHITE);
			vertices[i].setPredecessor(null);
		}
		DFStime = 0;
		for(int i = 0; i < keyToIndex.size(); i++) {
			if(vertices[i].getColor() == State.WHITE) {
				DFSVisit(vertices[i]);
			}
		}
	}
	/**
	 * 
	 * @param u
	 */
	//recursive method for traversing every reachable vertex from u
	private void DFSVisit(Vertex<E> u) {
		DFStime++;
		u.setDiscovered(DFStime);
		u.setColor(State.GRAY);
		int uIndex = keyToIndex.get(u.getElement());
		for(int i = 0; i < keyToIndex.size(); i++) {
			Vertex<E> v = vertices[i];
			int vIndex = keyToIndex.get(v.getElement());
			if(edges[uIndex][vIndex] != Integer.MAX_VALUE && v.getColor() == State.WHITE) { //edge exists && vertex has not been visited
				v.setPredecessor(u);
				DFSVisit(v);
			}
		}
		u.setColor(State.BLACK);
		DFStime++;
		u.setFinished(DFStime);
	}
	/**
	 * 
	 */
	@Override
	public void DFS(E src) {
		if(keyToIndex.containsKey(src)) {
			for(int i = 0; i < keyToIndex.size(); i++) { //Fix the vertices configuration to make DFS
				vertices[i].setColor(State.WHITE);
				vertices[i].setPredecessor(null);
			}
			int uIndex = keyToIndex.get(src);
			DFStime = 1;
			Vertex<E> s = vertices[uIndex];
			s.setColor(State.GRAY);
			s.setDiscovered(DFStime);
			//s.predecessor is already null so skip that step
			Stack<Vertex<E>> stack = new Stack<>();
			stack.push(s);
			while(!stack.isEmpty()) {
				Vertex<E> u = stack.pop();
				for(int i = 0; i < keyToIndex.size(); i++) {
					Vertex<E> v = vertices[i];
					int vIndex = keyToIndex.get(v.getElement());
					if(edges[uIndex][vIndex] != Integer.MAX_VALUE && v.getColor() == State.WHITE) { //edge exists && vertex has not been visited
						DFStime++;
						v.setColor(State.GRAY);
						v.setDiscovered(DFStime);
						v.setPredecessor(u);
						stack.push(v);
					}
				}
				u.setColor(State.BLACK);
				DFStime++;
				u.setFinished(DFStime);
			}
		}
	}
	/**
	 * 
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
	/**
	 * 
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
	/**
	 * 
	 */
	@Override
	public int getEdgeWeight(E src, E dst) {
		if(keyToIndex.containsKey(src) && keyToIndex.containsKey(dst)) {
			return edges[keyToIndex.get(src)][keyToIndex.get(dst)];
		}
		return Integer.MAX_VALUE;
	}
	/**
	 * 
	 * @return
	 */
	public int[][] getEdges() {
		return edges;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isDirected() {
		return isDirected;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<E> getVertices() {
		ArrayList<E> verts = new ArrayList<E>();
		for(int i = 0; i < keyToIndex.size(); i++) {
			verts.add(vertices[i].getElement());
		}
		return verts;
	}
	/**
	 * 
	 * @return
	 */
	public E getLastSrc() {
		return lastSrc.getElement();
	}
	/**
	 * 
	 * @return
	 */
	public int getFreeRow() {
		return freeRow;
	}
	/**
	 * 
	 */
	@Override
	public int getSingleSourceDistance(E dst) {
		if(keyToIndex.containsKey(dst)) {
			return vertices[keyToIndex.get(dst)].getDistance();
		}
		return Integer.MAX_VALUE;
	}
	/**
	 * 
	 */
	@Override
	public int getDFSDiscoveredTime(E key) {
		if(keyToIndex.containsKey(key)) {
			return vertices[keyToIndex.get(key)].getDiscovered();
		}
		return 0;
	}
	/**
	 * 
	 */
	@Override
	public int getDFSFinishedTime(E key) {
		if(keyToIndex.containsKey(key)) {
			return vertices[keyToIndex.get(key)].getFinished();
		}
		return 0;
	}
	/**
	 * 
	 */
	@Override
	public State getVertexColor(E key) {
		if(keyToIndex.containsKey(key)) {
			return vertices[keyToIndex.get(key)].getColor();
		}
		return null;
	}
	/**
	 * 
	 */
	@Override
	public E getSingleSourcePredecessor(E key) {
		if(keyToIndex.containsKey(key) && vertices[keyToIndex.get(key)].getPredecessor() != null) {
			return vertices[keyToIndex.get(key)].getPredecessor().getElement();
		}
		return null;
	}
	/**
	 * 
	 */
	@Override
	public ArrayList<E> getPath(E src, E dst) {
		Vertex<E> d = vertices[keyToIndex.get(dst)];
		Vertex<E> s = vertices[keyToIndex.get(src)];
		ArrayList<E> path = new ArrayList<E>();
		if(allPairsShortestPath != null && d != null && s != null) {
			if(d.getPredecessor() != null) {
				pathFill(s, d, path);
			} else if(d == s && d != null) {
				path.add(d.getElement());
			}
		}
		return path;
	}
	/**
	 * 
	 * @param src
	 * @param dst
	 * @param path
	 */
	private void pathFill(Vertex<E> src, Vertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			pathFill(src, allPairsShortestPath[keyToIndex.get(src.getElement())][keyToIndex.get(dst.getElement())], path);
			path.add(dst.getElement());
		}
	}
	/**
	 * 
	 */
	@Override
	public int getDistance(E src, E dst) {
		if(allPairsminimumDistances != null && keyToIndex.containsKey(src) && keyToIndex.containsKey(dst)) {
			return allPairsminimumDistances[keyToIndex.get(src)][keyToIndex.get(dst)];
		}
		return 0;
	}
	/**
	 * 
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
	/**
	 * 
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
}
