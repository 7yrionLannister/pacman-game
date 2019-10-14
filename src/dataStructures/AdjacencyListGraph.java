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
	private static int DFStime;
	
	public AdjacencyListGraph(boolean isDirected, boolean isWeighted) {
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
		vertices = new HashMap<>();
	}

	@Override
	public boolean insertVertex(E e) {
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

	//TODO modificar este metodo para que si ya existe una arista de src a dst, 
	//elimine la existente para aniadir la nueva
	@Override
	public void link(E src, E dst, int weight) {
		if(!isWeighted) {
			weight = 1;
		}
		insertVertex(src); //Inserts src if not currently in the graph
		insertVertex(dst); //Inserts dst if not currently in the graph
		AdjacencyListVertex<E> s = vertices.get(src);
		AdjacencyListVertex<E> d = vertices.get(dst);
		s.getEdges().add(new AdjacencyListEdge<>(s, d, weight));
		if(!isDirected) { //Add the additional edge is this graph is undirected
			d.getEdges().add(new AdjacencyListEdge<>(d, s, weight));
		}
	}
	
	@Override
	public boolean unlink(E src, E dst) {
		// TODO Auto-generated method stub
		return false;
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
		return vertices.isEmpty();
	}

	//traverses all vertices reachable from src
	@Override
	public boolean BFS(E src) throws Exception {
		if(vertices.containsKey(src)) {
			AdjacencyListVertex<E> s = vertices.get(src);
			lastSrcInBSF = s;
			vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() { //Fix the vertices configuration to make BFS
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

	//dfs that traverses every vertex independent if it is not reachable from certain vertices
	//it uses stack of recursive calls in dfsvisit method
	@Override
	public void DFS() {
		vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() { //Fix the vertices configuration to make DFS
			@Override
			public void accept(E e, AdjacencyListVertex<E> u) {
				u.setColor(State.WHITE);
				u.setPredecessor(null);
			}
		});
		DFStime = 0;
		vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() {
			@Override
			public void accept(E e, AdjacencyListVertex<E> u) {
				if(u.getColor() == State.WHITE) {
					DFSVisit(u);
				}
			}
		});
	}
	
	//recursive method for traversing every reachable vertex from u
	private void DFSVisit(AdjacencyListVertex<E> u) {
		DFStime++;
		u.setDiscovered(DFStime);
		u.setColor(State.GRAY);
		ArrayList<AdjacencyListEdge<E>> adj = u.getEdges();
		for(AdjacencyListEdge<E> ale : adj) {
			AdjacencyListVertex<E> v = ale.getDst();
			if(v.getColor() == State.WHITE) {
				v.setPredecessor(u);
				DFSVisit(v);
			}
		}
		u.setColor(State.BLACK);
		DFStime++;
		u.setFinished(DFStime);
	}

	//iterative dfs using stack data structure
	//it does not traverses all the vertices as the main implementation of dfs, instead
	//it traverses the reachable vertices starting from src
	@Override
	public void DFS(E src) {
		if(vertices.containsKey(src)) {
			vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() { //Fix the vertices configuration to make DFS
				@Override
				public void accept(E e, AdjacencyListVertex<E> u) {
					u.setColor(State.WHITE);
					u.setPredecessor(null);
				}
			});
			DFStime = 1;
			AdjacencyListVertex<E> s = vertices.get(src);
			s.setColor(State.GRAY);
			s.setDiscovered(DFStime);
			//s.predecessor is already null so skip that step
			Stack<AdjacencyListVertex<E>> stack = new Stack<>();
			stack.push(s);
			while(!stack.isEmpty()) {
				AdjacencyListVertex<E> u = stack.pop();
				ArrayList<AdjacencyListEdge<E>> adj = u.getEdges();
				for(AdjacencyListEdge<E> ale: adj) {
					AdjacencyListVertex<E> v = ale.getDst();
					if(v.getColor() == State.WHITE) {
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
	
	@Override
	public void Dijkstra(E src, E dst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FloydWarshall(E src, E dst) {
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
