package dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;

import dataStructures.State;


public class AdjacencyListGraph<E> implements IGraph<E>{
	private HashMap<E, AdjacencyListVertex<E>> vertices;
	private boolean isDirected;
	private boolean isWeighted;
	private AdjacencyListVertex<E> lastSrc;
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
		if(vertices.containsKey(sk)) {
			vertices.remove(sk); //remove the vertex itself
			vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() { //and remove all edges where it is dst
				@Override
				public void accept(E t, AdjacencyListVertex<E> u) {
					ArrayList<AdjacencyListEdge<E>> toremove = new ArrayList<>();
					for(AdjacencyListEdge<E> ale : u.getEdges()) {
						if(ale.getDst().getElement().equals(sk)) {
							toremove.add(ale);
						}
					}
					u.getEdges().removeAll(toremove);
				}
			});

			return true;
		}
		return false;
	}

	@Override
	public void link(E src, E dst, int weight) {
		if(!isWeighted) {
			weight = 1;
		}
		insertVertex(src); //Inserts src if not currently in the graph
		insertVertex(dst); //Inserts dst if not currently in the graph
		AdjacencyListVertex<E> s = vertices.get(src);
		AdjacencyListVertex<E> d = vertices.get(dst);
		AdjacencyListEdge<E> newedge1 = new AdjacencyListEdge<>(s, d, weight);

		s.getEdges().remove(newedge1); //if the edge already exists remove it
		s.getEdges().add(newedge1);
		if(!isDirected) { //Add the additional edge if this graph is undirected
			AdjacencyListEdge<E> newedge2 = new AdjacencyListEdge<>(d, s, weight);
			d.getEdges().remove(newedge2); //if the edge already exists remove it
			d.getEdges().add(newedge2); 
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
			lastSrc = s;
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
						v.setDistance(u.getDistance()+1); //TODO considerar en vez de sumar 1, sumar el peso de la arista(u,v), esto porque sumando de uno en uno va a dar el mismo resultado que si alguien le da a getBFSPath el .size(), asi que no brinda mucha informacion adicional
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

	//pre: bfs, dfs or dijkstra have been called
	//it is only the shortest path in unweighted graphs, else is just a path
	//it is the least stops path
	//returns empty arraylist if the dst vertex was not reachable from lastSrcInBST, or if dst == null
	public ArrayList<E> getPath(E dst) {
		AdjacencyListVertex<E> d = vertices.get(dst);
		ArrayList<E> path = new ArrayList<E>();
		if(d != null && d.getPredecessor() != null) {
			pathFill(lastSrc, d, path);
		} else if(d == lastSrc && d != null) {
			path.add(d.getElement());
		}

		return path;
	}

	private void pathFill(AdjacencyListVertex<E> src, AdjacencyListVertex<E> dst, ArrayList<E> path) {
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
	public void Dijkstra(E src) {
		PriorityQueue<AdjacencyListVertex<E>> pq = new PriorityQueue<AdjacencyListVertex<E>>();
		if(vertices.containsKey(src)) {
			lastSrc = vertices.get(src);
			vertices.forEach(new BiConsumer<E, AdjacencyListVertex<E>>() {
				@Override
				public void accept(E t, AdjacencyListVertex<E> u) {
					u.setDistance(Integer.MAX_VALUE);
					u.setPredecessor(null);
					pq.offer(u);
				}
			});
			pq.remove(lastSrc);
			lastSrc.setDistance(0);
			pq.offer(lastSrc);
			while(!pq.isEmpty()) {
				AdjacencyListVertex<E> u = pq.poll();
				for(AdjacencyListEdge<E> ale : u.getEdges()) {
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
	public void FloydWarshall(E src, E dst) {
		// TODO Auto-generated method stub

	}

	public boolean isDirected() {
		return isDirected;
	}

	public boolean isWeighted() {
		return isWeighted;
	}

	public AdjacencyListVertex<E> getLastSrc() {
		return lastSrc;
	}

	public HashMap<E, AdjacencyListVertex<E>> getVertices() {
		return vertices;
	}
}
