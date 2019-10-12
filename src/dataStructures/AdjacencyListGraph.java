package dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import dataStructures.AdjacencyListVertex.State;

public class AdjacencyListGraph<E> implements IGraph<E>{
	private HashMap<E, AdjacencyListVertex<E>> vertices;
	private boolean isDirected;
	private boolean isWeighted;
	private AdjacencyListVertex<E> lastSrcInBSF;
	
	public AdjacencyListGraph(boolean isDirected, boolean isWeighted) {
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
		vertices = new HashMap<>();
	}

	@Override
	public boolean insertIsolatedVertex(E e) {
		if(!vertices.containsKey(e)) {
			vertices.put(e, new AdjacencyListVertex<E>(e));
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteVertex(E sk) {
		// TODO Auto-generated method stub
		//Tomar en cuenta que al eliminar un vertice hay que eliminar todas las aristas que los contengan
		return false;
	}

	@Override
	public void link(E src, E dst, int weight) {
		if(!isWeighted) {
			weight = 1;
		}
		insertIsolatedVertex(src); //Inserts src if not currently in the graph
		insertIsolatedVertex(dst); //Inserts dst if not currently in the graph
		AdjacencyListVertex<E> s = vertices.get(src);
		AdjacencyListVertex<E> d = vertices.get(dst);
		s.getEdges().add(new AdjacencyListEdge<>(s, d, weight));
		if(!isDirected) { //Add the additional edge is this graph is undirected
			d.getEdges().add(new AdjacencyListEdge<>(d, s, weight));
		}
	}

	@Override
	public AdjacencyListVertex<E> searchVertex(E key) {
		return vertices.get(key);
	}

	@Override
	public int getOrder() {
		return vertices.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	//traverses all vertices reachable from src
	@Override
	public boolean BFS(E src) throws Exception {
		if(vertices.containsKey(src)) {
			AdjacencyListVertex<E> s = vertices.get(src);
			vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() {
				@Override
				public void accept(E e, AdjacencyListVertex<E> u) {
					u.setColor(State.WHITE);
					u.setDistance(Integer.MAX_VALUE);
					u.setPredecessor(null);
				}
			});
			s.setColor(State.GRAY);
			s.setDistance(0);
			//s.predecessor is already null so skip that step
			Queue<AdjacencyListVertex<E>> queue = new Queue<>();
			queue.enqueue(s);
			while(!queue.isEmpty()) {
				AdjacencyListVertex<E> u = queue.dequeue();
				ArrayList<AdjacencyListEdge<E>> adj = u.getEdges();
				for(AdjacencyListEdge<E> ale: adj) {
					AdjacencyListVertex<E> v = ale.getDst();
					if(v.getColor() == State.WHITE) {
						v.setColor(State.GRAY);
						v.setDistance(u.getDistance()+1);
						v.setPredecessor(u);
						queue.enqueue(v);
					}
				}
				u.setColor(State.BLACK);
			}
			return true;
		}
		return false;
	}
	
	//it is only the shortest path in unweighted graphs, else is just a path
	//returns empty arraylist if the dst vertex was not reachable from lastSrcInBST, or if dst == null
	public ArrayList<E> getBFSPath(E dst) {
		AdjacencyListVertex<E> d = vertices.get(dst);
		ArrayList<E> path = new ArrayList<E>();
		if(d != null && d.getPredecessor() != null) {
			BFSPathFill(lastSrcInBSF, vertices.get(dst), path);
		}
		
		return path;
	}
	
	private void BFSPathFill(AdjacencyListVertex<E> src, AdjacencyListVertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			BFSPathFill(src, dst.getPredecessor(), path);
			path.add(dst.getElement());
		}
	}

	@Override
	public void DFS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Dijkstra(E start, E end) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FloydWarshall(E start, E end) {
		// TODO Auto-generated method stub

	}

	public boolean isDirected() {
		return isDirected;
	}

	public boolean isWeighted() {
		return isWeighted;
	}

	public AdjacencyListVertex<E> getLastSrcInBSF() {
		return lastSrcInBSF;
	}
}
