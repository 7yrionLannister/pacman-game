package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;

import dataStructures.State;


public class AdjacencyListGraphTest {
	private AdjacencyListGraph<Integer> graph;
	private ArrayList<AdjacencyListEdge<Integer>> edges;
	private ArrayList<AdjacencyListEdge<Integer>> removedEdges;


	private void setupStageDirected() {
		graph = new AdjacencyListGraph<>(true);
		edges = new ArrayList<>();	
	}

	private void setupStageUndirected() {
		graph = new AdjacencyListGraph<>(false);
		edges = new ArrayList<>();
	}

	private void setupStageGraphWithIsolatedVertices() {
		for (int i = 0; i < 8; i++) {
			graph.insertVertex(i+1);
		}
		edges = new ArrayList<>();
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
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 1;
		weight = 3;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 3;
		weight = 9;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 7;
		weight = 5;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 8;
		weight = 5;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 5;
		weight = 7;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 5;
		dst = 8;
		weight = 2;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		graph.link(src, dst, weight);

		src = 6;
		dst = 1;
		weight = 8;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
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
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 6;
		dst = 7;
		weight = 2;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 2;
		weight = 9;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 8;
		weight = 6;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 5;
		weight = 8;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 4;
		weight = 5;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		src = 3;
		dst = 4;
		weight = 4;
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(src), graph.containsVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.containsVertex(dst), graph.containsVertex(src), weight));
		graph.link(src, dst, weight);

		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void unlinkVerticesInDirectedGraphTest() {
		linkVerticesInDirectedGraphTest();
		graph.unlink(2, 8);
		assertFalse(graph.getAdjacencyLists().get(2).contains(new AdjacencyListEdge<Integer>(graph.containsVertex(2), graph.containsVertex(8), Integer.MAX_VALUE)), "The edge was not removed");
		graph.unlink(6, 1);
		assertFalse(graph.getAdjacencyLists().get(6).contains(new AdjacencyListEdge<Integer>(graph.containsVertex(1), graph.containsVertex(8), Integer.MAX_VALUE)), "The edge was not removed");
	}

	@Test
	public void unlinkVerticesInUndirectedGraphTest() {
		//TODO implementar
		linkVerticesInUndirectedGraphTest();
		graph.unlink(7, 8);
		assertFalse(graph.getAdjacencyLists().get(7).contains(new AdjacencyListEdge<Integer>(graph.containsVertex(7), graph.containsVertex(8), Integer.MAX_VALUE)), "The edge was not removed");
		assertFalse(graph.getAdjacencyLists().get(8).contains(new AdjacencyListEdge<Integer>(graph.containsVertex(8), graph.containsVertex(7), Integer.MAX_VALUE)), "The edge was not removed");
		graph.unlink(3, 4);
		assertFalse(graph.getAdjacencyLists().get(3).contains(new AdjacencyListEdge<Integer>(graph.containsVertex(3), graph.containsVertex(4), Integer.MAX_VALUE)), "The edge was not removed");
		assertFalse(graph.getAdjacencyLists().get(4).contains(new AdjacencyListEdge<Integer>(graph.containsVertex(4), graph.containsVertex(3), Integer.MAX_VALUE)), "The edge was not removed");

	}

	@Test
	public void BFSInUndirectedGraphTest() throws Exception {
		linkVerticesInUndirectedGraphTest();
		int src = 8;
		graph.BFS(src);
		graph.getVertices().forEach(new BiConsumer<Integer, Vertex<Integer>>() {
			@Override
			public void accept(Integer t, Vertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
			}
		});
		ArrayList<Integer> leastStopsPath = graph.getPath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 5, "It is not the least stops path");

		leastStopsPath = graph.getPath(1);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 1, "It is not the least stops path");

		src = 1;
		graph.BFS(src);
		graph.getVertices().forEach(new BiConsumer<Integer, Vertex<Integer>>() {
			@Override
			public void accept(Integer t, Vertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
			}
		});
		leastStopsPath = graph.getPath(4);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 4, "It is not the least stops path");

		src = 1;
		graph.BFS(src);
		graph.getVertices().forEach(new BiConsumer<Integer, Vertex<Integer>>() {
			@Override
			public void accept(Integer t, Vertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
			}
		});
		leastStopsPath = graph.getPath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
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
		assertTrue(graph.containsVertex(src).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(3).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(5).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(6).getColor() == State.WHITE);
		assertTrue(graph.containsVertex(1).getColor() == State.WHITE);
		assertTrue(graph.containsVertex(2).getColor() == State.WHITE);
		assertTrue(graph.containsVertex(4).getColor() == State.WHITE);
		assertTrue(graph.containsVertex(7).getColor() == State.WHITE);
		ArrayList<Integer> leastStopsPath = graph.getPath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 5, "It is not the least stops path");

		leastStopsPath = graph.getPath(1);
		assertTrue(leastStopsPath.isEmpty(), "Vertex 1 is not reachable from source "+src+" so the path must be empty");

		src = 1;
		graph.BFS(src);
		assertTrue(graph.containsVertex(src).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(3).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(5).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(2).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(7).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(8).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(6).getColor() == State.WHITE);
		assertTrue(graph.containsVertex(4).getColor() == State.WHITE);
		leastStopsPath = graph.getPath(4);
		assertTrue(leastStopsPath.isEmpty(), "Vertex 4 is not reachable from any vertex so the path must be empty");

		src = 6;
		graph.BFS(src);
		assertTrue(graph.containsVertex(src).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(3).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(5).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(2).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(7).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(8).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(6).getColor() == State.BLACK);
		assertTrue(graph.containsVertex(4).getColor() == State.WHITE);
		leastStopsPath = graph.getPath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 1, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 2, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 5, "It is not the least stops path");

		leastStopsPath = graph.getPath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 1, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 2, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 3, "It is not the least stops path");
	}

	@Test
	public void DFSTest() {
		linkVerticesInUndirectedGraphTest();
		graph.DFS();

		graph.getVertices().forEach(new BiConsumer<Integer, Vertex<Integer>>() {
			@Override
			public void accept(Integer t, Vertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
				assertTrue(u.getDiscovered() > 0 && u.getFinished() > 0, "There is a vertex that was not marked as discovered or finished");
			}
		});
	}

	@Test
	public void DFSWithGivenSourceTest() {
		linkVerticesInDirectedGraphTest();
		graph.DFS(8);
		assertTrue(graph.containsVertex(1).getColor() == State.WHITE && graph.containsVertex(1).getDiscovered() == 0 && graph.containsVertex(1).getFinished() == 0);
		assertNull(graph.containsVertex(1).getPredecessor(), "1 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.containsVertex(2).getColor() == State.WHITE && graph.containsVertex(2).getDiscovered() == 0 && graph.containsVertex(2).getFinished() == 0);
		assertNull(graph.containsVertex(2).getPredecessor(), "2 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.containsVertex(3).getColor() == State.BLACK && graph.containsVertex(3).getDiscovered() > 0 && graph.containsVertex(3).getFinished() > 0);
		assertNotNull(graph.containsVertex(3).getPredecessor(), "3 is reachable from 8 so it must have a predecessor");
		assertTrue(graph.containsVertex(4).getColor() == State.WHITE && graph.containsVertex(4).getDiscovered() == 0 && graph.containsVertex(4).getFinished() == 0);
		assertNull(graph.containsVertex(4).getPredecessor(), "4 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.containsVertex(5).getColor() == State.BLACK && graph.containsVertex(5).getDiscovered() > 0 && graph.containsVertex(5).getFinished() > 0);
		assertNotNull(graph.containsVertex(5).getPredecessor(), "5 is reachable from 8 so it must have a predecessor");
		assertTrue(graph.containsVertex(6).getColor() == State.WHITE && graph.containsVertex(6).getDiscovered() == 0 && graph.containsVertex(6).getFinished() == 0);
		assertNull(graph.containsVertex(6).getPredecessor(), "6 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.containsVertex(7).getColor() == State.WHITE && graph.containsVertex(7).getDiscovered() == 0 && graph.containsVertex(7).getFinished() == 0);
		assertNull(graph.containsVertex(7).getPredecessor(), "7 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.containsVertex(8).getColor() == State.BLACK && graph.containsVertex(8).getDiscovered() > 0 && graph.containsVertex(8).getFinished() > 0);
		assertNull(graph.containsVertex(8).getPredecessor(), "8 is the source vertex so it must not have a predecessor");
	}

	@Test
	public void deleteVertexTest() {
		linkVerticesInDirectedGraphTest();
		int orderBeforeDeletion = graph.getOrder();
		assertFalse(graph.deleteVertex(100), "There is not a vertex with key 100 in the graph");
		assertTrue(graph.deleteVertex(8), "The vertex should have been deleted");
		assertNull(graph.containsVertex(8), "The vertex should have been deleted");
		assertTrue(orderBeforeDeletion == graph.getOrder()+1, "The order after insertion should be a unit less");
		removedEdges = new ArrayList<>();
		removedEdges.add(new AdjacencyListEdge<Integer>(new Vertex<Integer>(2), new Vertex<Integer>(8), Integer.MAX_VALUE));
		removedEdges.add(new AdjacencyListEdge<Integer>(new Vertex<Integer>(8), new Vertex<Integer>(3), Integer.MAX_VALUE));
		removedEdges.add(new AdjacencyListEdge<Integer>(new Vertex<Integer>(8), new Vertex<Integer>(5), Integer.MAX_VALUE));
		removedEdges.add(new AdjacencyListEdge<Integer>(new Vertex<Integer>(5), new Vertex<Integer>(8), Integer.MAX_VALUE));
		edges.removeAll(removedEdges);
		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void DijkstraTest() {
		linkVerticesInDirectedGraphTest();
		int src = 6;
		graph.Dijkstra(src);

		ArrayList<Integer> shortestPath = graph.getPath(5);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 1, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 2, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(shortestPath.get(4) == 5, "It is not the shortest path");

		shortestPath = graph.getPath(3);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 1, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 2, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 3, "It is not the shortest path");

		linkVerticesInUndirectedGraphTest();

		src = 8;
		graph.Dijkstra(src);
		shortestPath = graph.getPath(5);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 5, "It is not the shortest path");

		shortestPath = graph.getPath(1);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 6, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 1, "It is not the shortest path");

		src = 1;
		graph.Dijkstra(src);
		shortestPath = graph.getPath(4);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 6, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(shortestPath.get(4) == 4, "It is not the shortest path");

		src = 1;
		graph.Dijkstra(src);
		shortestPath = graph.getPath(3);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(shortestPath.get(1) == 6, "It is not the shortest path");
		assertTrue(shortestPath.get(2) == 7, "It is not the shortest path");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(shortestPath.get(4) == 4, "It is not the shortest path");
		assertTrue(shortestPath.get(5) == 3, "It is not the shortest path");
	}

	@Test
	public void FloydWarshallTest() {
		//TODO implementar
	}

	private void vertexInsertionLoop() {
		int vertexCount = 0;
		for(int i = 0; i < 50; i++) {
			int r = (int)(Math.random()*100);
			if(graph.insertVertex(r)) {
				vertexCount++;
			}
			Vertex<Integer> found = graph.containsVertex(r);
			assertNotNull(found, "The vertex with key "+r+" must have been found as it was added either in a previous iteration of the for loop or in this iteration");
			assertTrue(graph.getOrder() == vertexCount, "The order is not the expected");
			assertTrue(graph.getAdjacencyLists().get(r).isEmpty(), "The vertex just added should not have any edges");
			assertFalse(graph.isEmpty(), "Graph must not be empty after insertion");
		}
		assertNull(graph.containsVertex(200), "No vertex with key 200 was added so it should not have been found");
	}

	private void verifyGraphContainsAllEdgesItShould() {
		for (AdjacencyListEdge<Integer> ale : edges) {
			assertTrue(graph.getAdjacencyLists().get(ale.getSrc().getElement()).contains(ale), "A missing edge was found");
			int weight = 0;
			int times = 0;
			for(AdjacencyListEdge<Integer> ale2 : graph.getAdjacencyLists().get(ale.getSrc().getElement())) {
				if(ale2.equals(ale)) {
					weight = ale2.getWeight();
					times++;
				}
			}
			assertTrue(times == 1, "Edge was found more than once");
			assertTrue(weight == ale.getWeight(), "The edge was found but it does not have the correct weight");
		}
	}
}
