package dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;

import dataStructures.State;


public class AdjacencyListGraph<E> implements IGraph<E>{
	private HashMap<E, Vertex<E>> vertices;
	private HashMap<E, ArrayList<AdjacencyListEdge<E>>> adjacencyLists;
	private boolean isDirected;
	private Vertex<E> lastSrc;
	private static int DFStime;
	private AdjacencyMatrixGraph<E> graphForWarshall;

	public AdjacencyListGraph(boolean isDirected) {
		this.isDirected = isDirected;
		vertices = new HashMap<>();
		adjacencyLists = new HashMap<>();
	}

	@Override
	public boolean insertVertex(E e) {
		if(!vertices.containsKey(e)) {
			vertices.put(e, new Vertex<E>(e));
			adjacencyLists.put(e, new ArrayList<>());
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteVertex(E sk) {
		if(vertices.containsKey(sk)) {
			vertices.remove(sk); //remove the vertex itself
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //and remove all edges where it is dst
				@Override
				public void accept(E t, Vertex<E> u) {
					ArrayList<AdjacencyListEdge<E>> toremove = new ArrayList<>();
					for(AdjacencyListEdge<E> ale : adjacencyLists.get(t)) {
						if(ale.getDst().getElement().equals(sk)) {
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

	@Override
	public void link(E src, E dst, int weight) {
		insertVertex(src); //Inserts src if not currently in the graph
		insertVertex(dst); //Inserts dst if not currently in the graph
		Vertex<E> s = vertices.get(src);
		Vertex<E> d = vertices.get(dst);
		AdjacencyListEdge<E> newedge1 = new AdjacencyListEdge<>(s, d, weight);

		ArrayList<AdjacencyListEdge<E>> sEdges = adjacencyLists.get(src);
		sEdges.remove(newedge1); //if the edge already exists remove it
		sEdges.add(newedge1);
		if(!isDirected) { //Add the additional edge if this graph is undirected
			AdjacencyListEdge<E> newedge2 = new AdjacencyListEdge<>(d, s, weight);

			ArrayList<AdjacencyListEdge<E>> dEdges = adjacencyLists.get(dst);
			dEdges.remove(newedge2); //if the edge already exists remove it
			dEdges.add(newedge2); 
		}
	}

	@Override
	public boolean unlink(E src, E dst) {
		Vertex<E> s = vertices.get(src);
		Vertex<E> d = vertices.get(dst);
		if(s != null && d != null) {
			adjacencyLists.get(src).remove(new AdjacencyListEdge<>(s, d, 1)); //remove edge (s,d)
			if(!isDirected) { //Remove the other edge if this graph is undirected
				adjacencyLists.get(dst).remove(new AdjacencyListEdge<E>(d, s, 1));
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean containsVertex(E key) {
		return vertices.containsKey(key);
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
	public boolean BFS(E src) {
		if(vertices.containsKey(src)) {
			Vertex<E> s = vertices.get(src);
			lastSrc = s;
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make BFS
				@Override
				public void accept(E e, Vertex<E> u) {
					u.setColor(State.WHITE);
					u.setDistance(Integer.MAX_VALUE);
					u.setPredecessor(null);
				}
			});
			s.setColor(State.GRAY);
			s.setDistance(0);
			//s.predecessor is already null so skip that step
			Queue<Vertex<E>> queue = new Queue<>();
			queue.enqueue(s);
			try {
				while(!queue.isEmpty()) {
					Vertex<E> u = queue.dequeue();
					ArrayList<AdjacencyListEdge<E>> adj = adjacencyLists.get(u.getElement());
					for(AdjacencyListEdge<E> ale: adj) {
						Vertex<E> v = ale.getDst();
						if(v.getColor() == State.WHITE) {
							v.setColor(State.GRAY);
							v.setDistance(u.getDistance()+1);
							v.setPredecessor(u);
							queue.enqueue(v);
						}
					}
					u.setColor(State.BLACK);
				}
			} catch (Exception emptyQueueException) {
				//-_-
			}
			return true;
		}
		return false;
	}

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

	private void pathFill(Vertex<E> src, Vertex<E> dst, ArrayList<E> path) {
		if(src == dst) {
			path.add(src.getElement());
		} else {
			pathFill(src, dst.getPredecessor(), path);
			path.add(dst.getElement());
		}
	}

	//dfs that traverses every vertex independent if it is not reachable from certain vertices
	//it uses stack of recursive calls in dfsvisit method
	@Override
	public void DFS() {
		vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make DFS
			@Override
			public void accept(E e, Vertex<E> u) {
				u.setColor(State.WHITE);
				u.setPredecessor(null);
			}
		});
		DFStime = 0;
		vertices.forEach(new BiConsumer<E, Vertex<E>>() {
			@Override
			public void accept(E e, Vertex<E> u) {
				if(u.getColor() == State.WHITE) {
					DFSVisit(u);
				}
			}
		});
	}

	//recursive method for traversing every reachable vertex from u
	private void DFSVisit(Vertex<E> u) {
		DFStime++;
		u.setDiscovered(DFStime);
		u.setColor(State.GRAY);
		ArrayList<AdjacencyListEdge<E>> adj = adjacencyLists.get(u.getElement());
		for(AdjacencyListEdge<E> ale : adj) {
			Vertex<E> v = ale.getDst();
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
			vertices.forEach(new BiConsumer<E, Vertex<E>>() { //Fix the vertices configuration to make DFS
				@Override
				public void accept(E e, Vertex<E> u) {
					u.setDiscovered(0);
					u.setFinished(0);
					u.setColor(State.WHITE);
					u.setPredecessor(null);
				}
			});
			DFStime = 1;
			Vertex<E> s = vertices.get(src);
			s.setColor(State.GRAY);
			s.setDiscovered(DFStime);
			//s.predecessor is already null so skip that step
			Stack<Vertex<E>> stack = new Stack<>();
			stack.push(s);
			while(!stack.isEmpty()) {
				Vertex<E> u = stack.pop();
				ArrayList<AdjacencyListEdge<E>> adj = adjacencyLists.get(u.getElement());
				for(AdjacencyListEdge<E> ale: adj) {
					Vertex<E> v = ale.getDst();
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
				for(AdjacencyListEdge<E> ale : adjacencyLists.get(u.getElement())) {
					if(ale.getDst().getDistance() > ale.getSrc().getDistance() + ale.getWeight()) {
						pq.remove(ale.getDst());
						ale.getDst().setDistance(ale.getSrc().getDistance() + ale.getWeight());
						ale.getDst().setPredecessor(ale.getSrc());
						pq.offer(ale.getDst());
					}
				}
			}
		}
	}

	@Override
	public void FloydWarshall() {
		// TODO Auto-generated method stub

	}

	public boolean isDirected() {
		return isDirected;
	}

	public E getLastSrc() {
		return lastSrc.getElement();
	}

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

	@Override
	public int getSingleSourceDistance(E dst) {
		if(vertices.containsKey(dst)) {
			return vertices.get(dst).getDistance();
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public int getDFSDiscoveredTime(E key) {
		if(vertices.containsKey(key)) {
			return vertices.get(key).getDiscovered();
		}
		return 0;
	}

	@Override
	public int getDFSFinishedTime(E key) {
		if(vertices.containsKey(key)) {
			return vertices.get(key).getFinished();
		}
		return 0;
	}

	@Override
	public State getVertexColor(E key) {
		if(vertices.containsKey(key)) {
			return vertices.get(key).getColor();
		}
		return null;
	}

	@Override
	public E getSingleSourcePredecessor(E key) {
		if(vertices.containsKey(key) && vertices.get(key).getPredecessor() != null) {
			return vertices.get(key).getPredecessor().getElement();
		}
		return null;
	}

	@Override
	public ArrayList<E> getPath(E src, E dst) {
		ArrayList<E> path = new ArrayList<>();
		if(vertices.containsKey(src) && vertices.containsKey(dst)) {
			//TODO implementar
		}
		return path;
	}

	@Override
	public int getDistance(E src, E dst) {
		if(vertices.containsKey(src) && vertices.containsKey(dst)) {
			//TODO implementar
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean containsEdge(E src, E dst) {
		if(vertices.containsKey(src) && vertices.containsKey(dst)) {
			return adjacencyLists.get(src).contains(new AdjacencyListEdge<E>(new Vertex<E>(src), new Vertex<E>(dst), Integer.MAX_VALUE));
		}
		return false;
	}
	
	@Override
	public ArrayList<E> getAdjacent(E key) {
		ArrayList<E> adj = new ArrayList<>();
		if(vertices.containsKey(key)) {
			for(AdjacencyListEdge<E> ale : adjacencyLists.get(key)) {
				adj.add(ale.getDst().getElement());
			}
		}
		return adj;
	}

	@Override
	public int getEdgeWeight(E src, E dst) {
		if(vertices.containsKey(src) && vertices.containsKey(dst)) {
			ArrayList<AdjacencyListEdge<E>> srcAdj = adjacencyLists.get(src);
			for(int i = 0; i < srcAdj.size(); i++) {
				if(srcAdj.get(i).getDst().getElement().equals(dst)) {
					return srcAdj.get(i).getWeight();
				}
			}
			if(src.equals(dst)) {
				return 0;
			}
		}
		return Integer.MAX_VALUE;
	}
}
