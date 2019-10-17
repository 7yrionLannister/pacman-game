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
	
	
	private void setupStageDirectedAndWeighted() {
		graph = new AdjacencyListGraph<>(true, true);
		edges = new ArrayList<>();	
	}

	private void setupStageUndirectedAndUnweighted() {
		graph = new AdjacencyListGraph<>(false, false);
		edges = new ArrayList<>();
	}

	private void setupStageDirectedAndUnweighted() {
		graph = new AdjacencyListGraph<>(true, false);
		edges = new ArrayList<>();
	}

	private void setupStageUndirectedAndWeighted() {
		graph = new AdjacencyListGraph<>(false, true);
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
		setupStageDirectedAndWeighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		vertexInsertionLoop();
		setupStageUndirectedAndUnweighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		vertexInsertionLoop();
		setupStageDirectedAndUnweighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		vertexInsertionLoop();
		setupStageUndirectedAndWeighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		vertexInsertionLoop();
	}

	@Test
	public void linkVerticesInDirectedAndWeightedGraphTest() {
		setupStageDirectedAndWeighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		setupStageGraphWithIsolatedVertices();
		
		int src = 1;
		int dst = 2;
		int weight = 4;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 2;
		dst = 1;
		weight = 3;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 2;
		dst = 3;
		weight = 9;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 2;
		dst = 7;
		weight = 5;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 2;
		dst = 8;
		weight = 5;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 8;
		dst = 5;
		weight = 7;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 5;
		dst = 8;
		weight = 2;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		src = 6;
		dst = 1;
		weight = 8;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		graph.link(src, dst, weight);
		
		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void linkVerticesInUndirectedAndWeightedGraphTest() {
		setupStageUndirectedAndWeighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		setupStageGraphWithIsolatedVertices();
		
		int src = 1;
		int dst = 6;
		int weight = 1;
		edges = new ArrayList<>();
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 6;
		dst = 7;
		weight = 2;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 7;
		dst = 2;
		weight = 9;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 7;
		dst = 8;
		weight = 6;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 7;
		dst = 5;
		weight = 8;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 8;
		dst = 4;
		weight = 5;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		src = 3;
		dst = 4;
		weight = 4;
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(src), graph.searchVertex(dst), weight));
		edges.add(new AdjacencyListEdge<Integer>(graph.searchVertex(dst), graph.searchVertex(src), weight));
		graph.link(src, dst, weight);
		
		verifyGraphContainsAllEdgesItShould();
	}
	
	@Test
	public void unlinkVerticesTestInDirectedAndWeightedGraphTest() {
		//TODO implementar
		linkVerticesInDirectedAndWeightedGraphTest();
	}
	
	@Test
	public void unlinkVerticesTestInUndirectedAndWeightedGraphTest() {
		//TODO implementar
		linkVerticesInUndirectedAndWeightedGraphTest();
	}
	
	@Test
	public void BFSInUndirectedGraphTest() throws Exception {
		linkVerticesInUndirectedAndWeightedGraphTest();
		int src = 8;
		graph.BFS(src);
		graph.getVertices().forEach(new BiConsumer<Integer, AdjacencyListVertex<Integer>>() {
			@Override
			public void accept(Integer t, AdjacencyListVertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
			}
		});
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
		graph.getVertices().forEach(new BiConsumer<Integer, AdjacencyListVertex<Integer>>() {
			@Override
			public void accept(Integer t, AdjacencyListVertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
			}
		});
		leastStopsPath = graph.getPath(4);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 4, "It is not the least stops path");
		
		src = 1;
		graph.BFS(src);
		graph.getVertices().forEach(new BiConsumer<Integer, AdjacencyListVertex<Integer>>() {
			@Override
			public void accept(Integer t, AdjacencyListVertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
			}
		});
		leastStopsPath = graph.getPath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc().getElement() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 3, "It is not the least stops path");
	}
	
	@Test
	public void BFSInDirectedGraphTest() throws Exception {
		linkVerticesInDirectedAndWeightedGraphTest();
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
		linkVerticesInUndirectedAndWeightedGraphTest();
		graph.DFS();
		
		graph.getVertices().forEach(new BiConsumer<Integer, AdjacencyListVertex<Integer>>() {
			@Override
			public void accept(Integer t, AdjacencyListVertex<Integer> u) {
				assertTrue(u.getColor() == State.BLACK);
				assertTrue(u.getDiscovered() > 0 && u.getFinished() > 0, "There is a vertex that was not marked as discovered or finished");
			}
		});
	}

	@Test
	public void DFSWithGivenSourceTest() {
		linkVerticesInDirectedAndWeightedGraphTest();
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
		linkVerticesInDirectedAndWeightedGraphTest();
		int orderBeforeDeletion = graph.getOrder();
		assertFalse(graph.deleteVertex(100), "There is not a vertex with key 100 in the graph");
		assertTrue(graph.deleteVertex(8), "The vertex should have been deleted");
		assertNull(graph.searchVertex(100), "The vertex should have been deleted");
		assertTrue(orderBeforeDeletion == graph.getOrder()+1, "The order after insertion should be a unit less");
		removedEdges = new ArrayList<>();
		removedEdges.add(new AdjacencyListEdge<Integer>(new AdjacencyListVertex<Integer>(2), new AdjacencyListVertex<Integer>(8), Integer.MAX_VALUE));
		removedEdges.add(new AdjacencyListEdge<Integer>(new AdjacencyListVertex<Integer>(8), new AdjacencyListVertex<Integer>(3), Integer.MAX_VALUE));
		removedEdges.add(new AdjacencyListEdge<Integer>(new AdjacencyListVertex<Integer>(8), new AdjacencyListVertex<Integer>(5), Integer.MAX_VALUE));
		removedEdges.add(new AdjacencyListEdge<Integer>(new AdjacencyListVertex<Integer>(5), new AdjacencyListVertex<Integer>(8), Integer.MAX_VALUE));
		edges.removeAll(removedEdges);
		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void DijkstraTest() {
		linkVerticesInDirectedAndWeightedGraphTest();
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
		
		linkVerticesInUndirectedAndWeightedGraphTest();
		
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
		//TODO implementar
	}

	private void vertexInsertionLoop() {
		int vertexCount = 0;
		for(int i = 0; i < 50; i++) {
			int r = (int)(Math.random()*100);
			if(graph.insertVertex(r)) {
				vertexCount++;
			}
			AdjacencyListVertex<Integer> found = graph.searchVertex(r);
			assertNotNull(found, "The vertex with key "+r+" must have been found as it was added either in a previous iteration of the for loop or in this iteration");
			assertTrue(graph.getOrder() == vertexCount, "The order is not the expected");
			assertTrue(found.getEdges().isEmpty(), "The vertex just added should not have any edges");
			assertFalse(graph.isEmpty(), "Graph must not be empty after insertion");
		}
		assertNull(graph.searchVertex(200), "No vertex with key 200 was added so it should not have been found");
	}
	
	private void verifyGraphContainsAllEdgesItShould() {
		for (AdjacencyListEdge<Integer> ale : edges) {
			assertTrue(graph.searchVertex(ale.getSrc().getElement()).getEdges().contains(ale), "A missing edge was found");
			int weight = 0;
			for(AdjacencyListEdge<Integer> ale2 : graph.searchVertex(ale.getSrc().getElement()).getEdges()) {
				if(ale2.equals(ale)) {
					weight = ale2.getWeight();
					break;
				}
			}
			assertTrue(weight == ale.getWeight(), "The edge was found but it does not have the correct weight");
		}
	}
}
