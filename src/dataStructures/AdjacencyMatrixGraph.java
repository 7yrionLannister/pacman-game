package dataStructures;

import java.util.HashMap;

public class AdjacencyMatrixGraph<E> implements IGraph<E> {
	private int[][] edges;
	private boolean isDirected;
	private HashMap<E, Integer> keyToIndex;
	private HashMap<E, Vertex<E>> vertices;
	private Vertex<E> lastSrc;
	private int freeRow;
	private static int DFSTime;

	//usar size mas grande que los vertices presupuestados para reducir las veces que se debe crear matriz por falta de espacio
	public AdjacencyMatrixGraph(int size, boolean isDIrected) {
		edges = new int[size][size];
		this.isDirected = isDIrected;
		keyToIndex = new HashMap<>();
		vertices = new HashMap<>();
		freeRow = 0;
	}

	@Override
	public boolean insertVertex(E e) {
		if(!vertices.containsKey(e)) {
			keyToIndex.put(e, freeRow);
			vertices.put(e, new Vertex<>(e));
			freeRow++;
			if(freeRow > edges.length) {
				int[][] newedges = new int[edges.length+10][edges.length+10];
				for(int i = 0; i < edges.length; i++) {
					for (int j = 0; j < edges.length; j++) {
						newedges[i][j] = edges[i][j];
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteVertex(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void link(E src, E dst, int weight) {
		Integer s = keyToIndex.get(src);
		Integer d = keyToIndex.get(dst);
		if(s != null && d != null) {
			edges[s][d] = weight;
		}
	}

	@Override
	public Vertex<E> searchVertex(E key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean BFS(E src) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DFS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Dijkstra(E src) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FloydWarshall(E src, E dst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DFS(E src) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean unlink(E src, E dst) {
		// TODO Auto-generated method stub
		return false;
	}

}
