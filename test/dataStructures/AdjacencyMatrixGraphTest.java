package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dataStructures.State;


public class AdjacencyMatrixGraphTest {
	private AdjacencyMatrixGraph<Integer> graph;
	private ArrayList<Pair> edges;
	private ArrayList<Pair> removedEdges;


	private void setupStageDirected() {
		graph = new AdjacencyMatrixGraph<>(4, true);
		edges = new ArrayList<>();
		removedEdges = new ArrayList<>();
	}

	private void setupStageUndirected() {
		graph = new AdjacencyMatrixGraph<>(4, false);
		edges = new ArrayList<>();
		removedEdges = new ArrayList<>();
	}

	private void setupStageGraphWithIsolatedVertices() {
		for (int i = 0; i < 8; i++) {
			graph.insertVertex(i+1);
		}
		edges = new ArrayList<>();
		removedEdges = new ArrayList<>();
	}

	@Test
	public void insertAndSearchVertexTest() {
		setupStageDirected();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		vertexInsertionLoop();
		setupStageUndirected();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		vertexInsertionLoop();
	}

	@Test
	public void linkVerticesInDirectedGraphTest() {
		setupStageDirected();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		setupStageGraphWithIsolatedVertices();

		int src = 1;
		int dst = 2;
		int weight = 4;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 1;
		weight = 3;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 3;
		weight = 9;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 7;
		weight = 5;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 8;
		weight = 5;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 5;
		weight = 7;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 5;
		dst = 8;
		weight = 2;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 6;
		dst = 1;
		weight = 8;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void linkVerticesInUndirectedGraphTest() {
		setupStageUndirected();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		setupStageGraphWithIsolatedVertices();

		int src = 1;
		int dst = 6;
		int weight = 1;
		edges = new ArrayList<>();
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 6;
		dst = 7;
		weight = 2;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 2;
		weight = 9;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 8;
		weight = 6;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 5;
		weight = 8;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 4;
		weight = 5;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		src = 3;
		dst = 4;
		weight = 4;
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		edges.add(new Pair(graph.getKeyToIndex().get(src)+1, graph.getKeyToIndex().get(dst)+1, weight));
		graph.link(src, dst, weight);

		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void unlinkVerticesInDirectedGraphTest() {
		linkVerticesInDirectedGraphTest();
		try {
			graph.unlink(2, 8);
			assertTrue(graph.getEdgeWeight(2, 8) == Integer.MAX_VALUE, "The edge was not removed");
			graph.unlink(6, 1);
			assertTrue(graph.getEdgeWeight(6, 1) == Integer.MAX_VALUE, "The edge was not removed");
		} catch (Exception e) {
			fail("All requested vertices were in the graph so an exception was not expected");
		}
	}

	@Test
	public void unlinkVerticesInUndirectedGraphTest() {
		linkVerticesInUndirectedGraphTest();
		try {
			graph.unlink(7, 8);
			assertTrue(graph.getEdgeWeight(7, 8) == Integer.MAX_VALUE, "The edge was not removed");
			assertTrue(graph.getEdgeWeight(8, 7) == Integer.MAX_VALUE, "The edge was not removed");
			graph.unlink(3, 4);
			assertTrue(graph.getEdgeWeight(3, 4) == Integer.MAX_VALUE, "The edge was not removed");
			assertTrue(graph.getEdgeWeight(4, 3) == Integer.MAX_VALUE, "The edge was not removed");
		} catch (Exception e) {
			fail("All requested vertices were in the graph so an exception was not expected");
		}
	}

	@Test
	public void BFSInUndirectedGraphTest() throws Exception {
		linkVerticesInUndirectedGraphTest();
		int src = 8;
		graph.BFS(src);

		Vertex<Integer>[] vertices = graph.getVertices();
		for(int i = 0; i < graph.getKeyToIndex().size(); i++) {
			assertTrue(vertices[i].getColor() == State.BLACK);
		}
		ArrayList<Integer> leastStopsPath = graph.getPath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 5, "It is not the least stops path");

		leastStopsPath = graph.getPath(1);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 1, "It is not the least stops path");

		src = 1;
		graph.BFS(src);
		for(int i = 0; i < graph.getKeyToIndex().size(); i++) {
			assertTrue(vertices[i].getColor() == State.BLACK);
		}
		leastStopsPath = graph.getPath(4);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 4, "It is not the least stops path");

		src = 1;
		graph.BFS(src);
		for(int i = 0; i < graph.getKeyToIndex().size(); i++) {
			assertTrue(vertices[i].getColor() == State.BLACK);
		}
		leastStopsPath = graph.getPath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 3, "It is not the least stops path");
	}

	@Test
	public void BFSInDirectedGraphTest() throws Exception {
		linkVerticesInDirectedGraphTest();
		int src = 8;
		graph.BFS(src);
		assertTrue(graph.searchVertex(src).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(3).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(5).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(6).getColor() == State.WHITE);
		assertTrue(graph.searchVertex(1).getColor() == State.WHITE);
		assertTrue(graph.searchVertex(2).getColor() == State.WHITE);
		assertTrue(graph.searchVertex(4).getColor() == State.WHITE);
		assertTrue(graph.searchVertex(7).getColor() == State.WHITE);
		ArrayList<Integer> leastStopsPath = graph.getPath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 5, "It is not the least stops path");

		leastStopsPath = graph.getPath(1);
		assertTrue(leastStopsPath.isEmpty(), "Vertex 1 is not reachable from source "+src+" so the path must be empty");

		src = 1;
		graph.BFS(src);
		assertTrue(graph.searchVertex(src).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(3).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(5).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(2).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(7).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(8).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(6).getColor() == State.WHITE);
		assertTrue(graph.searchVertex(4).getColor() == State.WHITE);
		leastStopsPath = graph.getPath(4);
		assertTrue(leastStopsPath.isEmpty(), "Vertex 4 is not reachable from any vertex so the path must be empty");

		src = 6;
		graph.BFS(src);
		assertTrue(graph.searchVertex(src).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(3).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(5).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(2).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(7).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(8).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(6).getColor() == State.BLACK);
		assertTrue(graph.searchVertex(4).getColor() == State.WHITE);
		leastStopsPath = graph.getPath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 1, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 2, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 5, "It is not the least stops path");

		leastStopsPath = graph.getPath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 1, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 2, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 3, "It is not the least stops path");
	}

	@Test
	public void DFSTest() {
		linkVerticesInUndirectedGraphTest();
		graph.DFS();

		Vertex<Integer>[] vertices = graph.getVertices();
		for(int i = 0; i < graph.getKeyToIndex().size(); i++) {
			assertTrue(vertices[i].getColor() == State.BLACK);
			assertTrue(vertices[i].getDiscovered() > 0 && vertices[i].getFinished() > 0, "There is a vertex that was not marked as discovered or finished");
		}
	}

	@Test
	public void DFSWithGivenSourceTest() {
		linkVerticesInDirectedGraphTest();
		graph.DFS(8);
		assertTrue(graph.searchVertex(1).getColor() == State.WHITE && graph.searchVertex(1).getDiscovered() == 0 && graph.searchVertex(1).getFinished() == 0);
		assertNull(graph.searchVertex(1).getPredecessor(), "1 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.searchVertex(2).getColor() == State.WHITE && graph.searchVertex(2).getDiscovered() == 0 && graph.searchVertex(2).getFinished() == 0);
		assertNull(graph.searchVertex(2).getPredecessor(), "2 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.searchVertex(3).getColor() == State.BLACK && graph.searchVertex(3).getDiscovered() > 0 && graph.searchVertex(3).getFinished() > 0);
		assertNotNull(graph.searchVertex(3).getPredecessor(), "3 is reachable from 8 so it must have a predecessor");
		assertTrue(graph.searchVertex(4).getColor() == State.WHITE && graph.searchVertex(4).getDiscovered() == 0 && graph.searchVertex(4).getFinished() == 0);
		assertNull(graph.searchVertex(4).getPredecessor(), "4 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.searchVertex(5).getColor() == State.BLACK && graph.searchVertex(5).getDiscovered() > 0 && graph.searchVertex(5).getFinished() > 0);
		assertNotNull(graph.searchVertex(5).getPredecessor(), "5 is reachable from 8 so it must have a predecessor");
		assertTrue(graph.searchVertex(6).getColor() == State.WHITE && graph.searchVertex(6).getDiscovered() == 0 && graph.searchVertex(6).getFinished() == 0);
		assertNull(graph.searchVertex(6).getPredecessor(), "6 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.searchVertex(7).getColor() == State.WHITE && graph.searchVertex(7).getDiscovered() == 0 && graph.searchVertex(7).getFinished() == 0);
		assertNull(graph.searchVertex(7).getPredecessor(), "7 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.searchVertex(8).getColor() == State.BLACK && graph.searchVertex(8).getDiscovered() > 0 && graph.searchVertex(8).getFinished() > 0);
		assertNull(graph.searchVertex(8).getPredecessor(), "8 is the source vertex so it must not have a predecessor");
	}

	@Test
	public void deleteVertexTest() {
		linkVerticesInDirectedGraphTest();
		int orderBeforeDeletion = graph.getOrder();
		assertFalse(graph.deleteVertex(100), "There is not a vertex with key 100 in the graph");
		assertTrue(graph.deleteVertex(8), "The vertex should have been deleted");
		assertNull(graph.searchVertex(8), "The vertex should have been deleted");
		assertTrue(orderBeforeDeletion == graph.getOrder()+1, "The order after insertion should be a unit less");
		removedEdges = new ArrayList<>();
		removedEdges.add(new Pair(2, 8, Integer.MAX_VALUE));
		removedEdges.add(new Pair(8, 3, Integer.MAX_VALUE));
		removedEdges.add(new Pair(8, 5, Integer.MAX_VALUE));
		removedEdges.add(new Pair(5, 8, Integer.MAX_VALUE));
		edges.removeAll(removedEdges);
		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void DijkstraTest() {
		linkVerticesInDirectedGraphTest();
		int src = 6;
		graph.Dijkstra(src);

		ArrayList<Integer> shortestPath = graph.getPath(5);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 1, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 2, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(shortestPath.get(4) == 5, "It is not the shortest path");

		shortestPath = graph.getPath(3);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 1, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 2, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 3, "It is not the shortest path");

		linkVerticesInUndirectedGraphTest();

		src = 8;
		graph.Dijkstra(src);
		shortestPath = graph.getPath(5);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 5, "It is not the shortest path");

		shortestPath = graph.getPath(1);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 6, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 1, "It is not the shortest path");

		src = 1;
		graph.Dijkstra(src);
		shortestPath = graph.getPath(4);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 6, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(shortestPath.get(4) == 4, "It is not the shortest path");

		src = 1;
		graph.Dijkstra(src);
		shortestPath = graph.getPath(3);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 6, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(shortestPath.get(4) == 4, "It is not the shortest path");
		assertTrue(shortestPath.get(5) == 3, "It is not the shortest path");
	}

	@Test
	public void FloydWarshallTest() {
		linkVerticesInDirectedGraphTest();
		graph.FloydWarshall();
		int[][] minimumDistances = graph.getAllPairsminimumDistances();
		Vertex<Integer>[][] minimumPaths = graph.getAllPairsShortestPath();
		for(int i = 0; i < minimumDistances.length; i++) {
			System.out.println(Arrays.toString(minimumDistances[i]));
			String line = "";
			for (int j = 0; j < minimumDistances[i].length; j++) {
				//TODO aqui los asserts
				line += (minimumPaths[i][j] != null?minimumPaths[i][j].getElement():"n") + " ";
			}
			System.out.println(line);
		}
	}

	private void vertexInsertionLoop() {
		int vertexCount = 0;
		for(int i = 0; i < 50; i++) {
			int r = (int)(Math.random()*100);
			if(graph.insertVertex(r)) {
				vertexCount++;
			}
			Vertex<Integer> found = graph.searchVertex(r);
			assertNotNull(found, "The vertex with key "+r+" must have been found as it was added either in a previous iteration of the for loop or in this iteration");
			assertTrue(graph.getOrder() == vertexCount, "The order("+graph.getOrder() + ") is not the expected("+vertexCount+")");
			int[][] edges = graph.getEdges();
			int v = graph.getKeyToIndex().get(r);
			for(int j = 0; j < graph.getKeyToIndex().size(); j++) {

				assertTrue(edges[v][j] == (v==j ? 0:Integer.MAX_VALUE), "The vertex just added should not have any edges");
			}
		}
		assertFalse(graph.isEmpty(), "Graph must not be empty after insertion");
		assertNull(graph.searchVertex(200), "No vertex with key 200 was added so it should not have been found");
	}

	private void verifyGraphContainsAllEdgesItShould() {
		for (Pair edge : edges) {
			assertTrue(graph.getEdges()[graph.getKeyToIndex().get(edge.i)][graph.getKeyToIndex().get(edge.j)] != 0, "A missing edge was found");
			assertTrue(edge.weight == graph.getEdges()[graph.getKeyToIndex().get(edge.i)][graph.getKeyToIndex().get(edge.j)], "The edge was found but it does not have the correct weight");
		}
	}

	static class Pair {
		int i,j,weight;
		public Pair(int i, int j, int weight) {
			this.i = i;
			this.j = j;
			this.weight = weight;
		}

		@Override
		public boolean equals(Object another) {
			Pair a = (Pair)another; //throws exception if another is not a Pair
			return a.i == i && a.j == j;
		}
	}
}
