package dataStructures;

import dataStructures.Vertex.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


public class AdjacencyListGraphTest {
	private AdjacencyListGraph<Integer> graph;
	private ArrayList<Edge<Integer>> edges;
	private ArrayList<Edge<Integer>> removedEdges;


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
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 1;
		weight = 3;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 3;
		weight = 9;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 7;
		weight = 5;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 2;
		dst = 8;
		weight = 5;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 5;
		weight = 7;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 5;
		dst = 8;
		weight = 2;
		edges.add(new Edge<Integer>(src, dst, weight));
		graph.link(src, dst, weight);

		src = 6;
		dst = 1;
		weight = 8;
		edges.add(new Edge<Integer>(src, dst, weight));
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
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 6;
		dst = 7;
		weight = 2;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 2;
		weight = 9;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 8;
		weight = 6;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 7;
		dst = 5;
		weight = 8;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 3;
		weight = 10;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 8;
		dst = 4;
		weight = 5;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		src = 3;
		dst = 4;
		weight = 4;
		edges.add(new Edge<Integer>(src, dst, weight));
		edges.add(new Edge<Integer>(dst, src, weight));
		graph.link(src, dst, weight);

		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void unlinkVerticesInDirectedGraphTest() {
		linkVerticesInDirectedGraphTest();
		graph.unlink(2, 8);
		assertFalse(graph.containsEdge(2, 8), "The edge was not removed");
		graph.unlink(6, 1);
		assertFalse(graph.containsEdge(6, 1), "The edge was not removed");
	}

	@Test
	public void unlinkVerticesInUndirectedGraphTest() {
		linkVerticesInUndirectedGraphTest();
		graph.unlink(7, 8);
		assertFalse(graph.containsEdge(7, 8), "The edge was not removed");
		assertFalse(graph.containsEdge(8, 7), "The edge was not removed");
		graph.unlink(3, 4);
		assertFalse(graph.containsEdge(3, 4), "The edge was not removed");
		assertFalse(graph.containsEdge(3, 4), "The edge was not removed");

	}

	@Test
	public void BFSInUndirectedGraphTest() {
		linkVerticesInUndirectedGraphTest();
		int src = 8;
		graph.BFS(src);
		for(int i = 0; i < 8; i++) {
			if(i+1 == src ) {
				assertTrue(graph.getVertexColor(i+1) == Color.BLACK && graph.getSingleSourceDistance(i+1) == 0);
				assertNull(graph.getSingleSourcePredecessor(i+1), (i+1)+" is the src so its predecessor must be null");
			} else {
				assertTrue(graph.getVertexColor(i+1) == Color.BLACK && graph.getSingleSourceDistance(i+1) > 0);
				assertNotNull(graph.getSingleSourcePredecessor(i+1), (i+1)+" is reachable from "+(src)+" so it must have a predecessor");
			}			
		}
		ArrayList<Integer> leastStopsPath = graph.getSingleSourcePath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 5, "It is not the least stops path");

		leastStopsPath = graph.getSingleSourcePath(1);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 1, "It is not the least stops path");

		src = 1;
		graph.BFS(src);
		for(int i = 0; i < 8; i++) {
			if(i+1 == src ) {
				assertTrue(graph.getVertexColor(i+1) == Color.BLACK && graph.getSingleSourceDistance(i+1) == 0);
				assertNull(graph.getSingleSourcePredecessor(i+1), (i+1)+" is the src so its predecessor must be null");
			} else {
				assertTrue(graph.getVertexColor(i+1) == Color.BLACK && graph.getSingleSourceDistance(i+1) > 0);
				assertNotNull(graph.getSingleSourcePredecessor(i+1), (i+1)+" is reachable from "+(src)+" so it must have a predecessor");
			}			
		}
		leastStopsPath = graph.getSingleSourcePath(4);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 4, "It is not the least stops path");

		src = 1;
		graph.BFS(src);
		for(int i = 0; i < 8; i++) {
			if(i+1 == src ) {
				assertTrue(graph.getVertexColor(i+1) == Color.BLACK && graph.getSingleSourceDistance(i+1) == 0);
				assertNull(graph.getSingleSourcePredecessor(i+1), (i+1)+" is the src so its predecessor must be null");
			} else {
				assertTrue(graph.getVertexColor(i+1) == Color.BLACK && graph.getSingleSourceDistance(i+1) > 0);
				assertNotNull(graph.getSingleSourcePredecessor(i+1), (i+1)+" is reachable from "+(src)+" so it must have a predecessor");
			}			
		}
		leastStopsPath = graph.getSingleSourcePath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 6, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 7, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 3, "It is not the least stops path");
	}

	@Test
	public void BFSInDirectedGraphTest() {
		linkVerticesInDirectedGraphTest();
		int src = 8;
		graph.BFS(src);
		assertTrue(graph.getVertexColor(src) == Color.BLACK && graph.getSingleSourceDistance(src) == 0);
		assertTrue(graph.getVertexColor(3) == Color.BLACK && graph.getSingleSourceDistance(3) > 0);
		assertTrue(graph.getVertexColor(5) == Color.BLACK && graph.getSingleSourceDistance(5) > 0);
		assertTrue(graph.getVertexColor(6) == Color.WHITE && graph.getSingleSourceDistance(6) == Integer.MAX_VALUE);
		assertTrue(graph.getVertexColor(1) == Color.WHITE && graph.getSingleSourceDistance(1) == Integer.MAX_VALUE);
		assertTrue(graph.getVertexColor(2) == Color.WHITE && graph.getSingleSourceDistance(2) == Integer.MAX_VALUE);
		assertTrue(graph.getVertexColor(4) == Color.WHITE && graph.getSingleSourceDistance(4) == Integer.MAX_VALUE);
		assertTrue(graph.getVertexColor(7) == Color.WHITE && graph.getSingleSourceDistance(7) == Integer.MAX_VALUE);
		ArrayList<Integer> leastStopsPath = graph.getSingleSourcePath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 5, "It is not the least stops path");

		leastStopsPath = graph.getSingleSourcePath(1);
		assertTrue(leastStopsPath.isEmpty(), "Vertex 1 is not reachable from source "+src+" so the path must be empty");

		src = 1;
		graph.BFS(src);
		assertTrue(graph.getVertexColor(src) == Color.BLACK && graph.getSingleSourceDistance(src) == 0);
		assertTrue(graph.getVertexColor(3) == Color.BLACK && graph.getSingleSourceDistance(3) > 0);
		assertTrue(graph.getVertexColor(5) == Color.BLACK && graph.getSingleSourceDistance(5) > 0);
		assertTrue(graph.getVertexColor(2) == Color.BLACK && graph.getSingleSourceDistance(2) > 0);
		assertTrue(graph.getVertexColor(7) == Color.BLACK && graph.getSingleSourceDistance(7) > 0);
		assertTrue(graph.getVertexColor(8) == Color.BLACK && graph.getSingleSourceDistance(8) > 0);
		assertTrue(graph.getVertexColor(6) == Color.WHITE && graph.getSingleSourceDistance(6) == Integer.MAX_VALUE);
		assertTrue(graph.getVertexColor(4) == Color.WHITE && graph.getSingleSourceDistance(4) == Integer.MAX_VALUE);
		leastStopsPath = graph.getSingleSourcePath(4);
		assertTrue(leastStopsPath.isEmpty(), "Vertex 4 is not reachable from any vertex so the path must be empty");

		src = 6;
		graph.BFS(src);
		assertTrue(graph.getVertexColor(src) == Color.BLACK);
		assertTrue(graph.getVertexColor(3) == Color.BLACK);
		assertTrue(graph.getVertexColor(5) == Color.BLACK);
		assertTrue(graph.getVertexColor(2) == Color.BLACK);
		assertTrue(graph.getVertexColor(7) == Color.BLACK);
		assertTrue(graph.getVertexColor(8) == Color.BLACK);
		assertTrue(graph.getVertexColor(6) == Color.BLACK);
		assertTrue(graph.getVertexColor(4) == Color.WHITE);
		leastStopsPath = graph.getSingleSourcePath(5);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 1, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 2, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 8, "It is not the least stops path");
		assertTrue(leastStopsPath.get(4) == 5, "It is not the least stops path");

		leastStopsPath = graph.getSingleSourcePath(3);
		assertTrue(leastStopsPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(leastStopsPath.get(1) == 1, "It is not the least stops path");
		assertTrue(leastStopsPath.get(2) == 2, "It is not the least stops path");
		assertTrue(leastStopsPath.get(3) == 3, "It is not the least stops path");
	}

	@Test
	public void DFSTest() {
		linkVerticesInUndirectedGraphTest();
		graph.DFS();

		TreeMap<Integer, Integer> parenthesis = new TreeMap<>(); //We put the vertices in a treemap so that the keys
		                                                         //will be the time stamps and the values will be the 
		                                                         //vertices. As the treemap keeps the keys in order, 
		                                                         //if DFS worked well, there will be a valid 
		                                                         //parenthesis structure.
		for(int i = 0; i < 8; i++) {
			assertTrue(graph.getVertexColor(i+1) == Color.BLACK);
			parenthesis.put(graph.getDFSDiscoveredTime(i+1), i+1);
			parenthesis.put(graph.getDFSFinishedTime(i+1), i+1);
		}

		Stack<Integer> stack = new Stack<>();
		ArrayList<Integer> list = new ArrayList<>();
		for(Integer i : parenthesis.values()) {
			if(!list.contains(i)) {
				stack.push(i);
				list.add(i);
			} else if(!stack.isEmpty()){
				stack.pop();
				list.remove(i);
			} else {
				stack.push(Integer.MAX_VALUE); //add an element to notice the parenthesis structure is not valid in the assert
				break;
			}
		}
		assertTrue(stack.isEmpty(), "DFS should result in a valid parenthesis structure of time-stamps");
	}

	@Test
	public void DFSWithGivenSourceTest() {
		linkVerticesInDirectedGraphTest();
		graph.DFS(8);
		assertTrue(graph.getVertexColor(1) == Color.WHITE && graph.getDFSDiscoveredTime(1) == 0 && graph.getDFSFinishedTime(1) == 0);
		assertNull(graph.getSingleSourcePredecessor(1), "1 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.getVertexColor(2) == Color.WHITE && graph.getDFSDiscoveredTime(2) == 0 && graph.getDFSFinishedTime(2) == 0);
		assertNull(graph.getSingleSourcePredecessor(2), "2 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.getVertexColor(3) == Color.BLACK && graph.getDFSDiscoveredTime(3) > 0 && graph.getDFSFinishedTime(3) > 0);
		assertNotNull(graph.getSingleSourcePredecessor(3), "3 is reachable from 8 so it must have a predecessor");
		assertTrue(graph.getVertexColor(4) == Color.WHITE && graph.getDFSDiscoveredTime(4) == 0 && graph.getDFSFinishedTime(4) == 0);
		assertNull(graph.getSingleSourcePredecessor(4), "4 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.getVertexColor(5) == Color.BLACK && graph.getDFSDiscoveredTime(5) > 0);
		assertNotNull(graph.getSingleSourcePredecessor(5), "5 is reachable from 8 so it must have a predecessor");
		assertTrue(graph.getVertexColor(6) == Color.WHITE && graph.getDFSDiscoveredTime(6) == 0 && graph.getDFSFinishedTime(6) == 0);
		assertNull(graph.getSingleSourcePredecessor(6), "6 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.getVertexColor(7) == Color.WHITE && graph.getDFSDiscoveredTime(7) == 0 && graph.getDFSFinishedTime(7) == 0);
		assertNull(graph.getSingleSourcePredecessor(7), "7 is not reachable from 8 so it must not have a predecessor");
		assertTrue(graph.getVertexColor(8) == Color.BLACK && graph.getDFSDiscoveredTime(8) > 0 && graph.getDFSFinishedTime(8) > 0);
		assertNull(graph.getSingleSourcePredecessor(8), "8 is the source vertex so it must not have a predecessor");
	}

	@Test
	public void deleteVertexTest() {
		linkVerticesInDirectedGraphTest();
		int orderBeforeDeletion = graph.getOrder();
		assertFalse(graph.deleteVertex(100), "There is not a vertex with key 100 in the graph");
		assertTrue(graph.deleteVertex(8), "The vertex should have been deleted");
		assertFalse(graph.containsVertex(8), "The vertex should have been deleted");
		assertTrue(orderBeforeDeletion == graph.getOrder()+1, "The order after insertion should be a unit less");
		removedEdges = new ArrayList<>();
		removedEdges.add(new Edge<Integer>(2, 8, Integer.MAX_VALUE));
		removedEdges.add(new Edge<Integer>(8, 3, Integer.MAX_VALUE));
		removedEdges.add(new Edge<Integer>(8, 5, Integer.MAX_VALUE));
		removedEdges.add(new Edge<Integer>(5, 8, Integer.MAX_VALUE));
		edges.removeAll(removedEdges);
		verifyGraphContainsAllEdgesItShould();
	}

	@Test
	public void DijkstraTest() {
		linkVerticesInDirectedGraphTest();
		int src = 6;
		graph.Dijkstra(src);

		ArrayList<Integer> shortestPath = graph.getSingleSourcePath(5);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(graph.getSingleSourceDistance(src) == 0, "Wrong shortest distance");
		assertNull(graph.getSingleSourcePredecessor(src), "Wrong predecessor");
		assertTrue(shortestPath.get(1) == 1, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(1) == 8, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(1) == src, "Wrong predecessor");
		assertTrue(shortestPath.get(2) == 2, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(2) == 12, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(2) == 1, "Wrong predecessor");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(8) == 17, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(8) == 2, "Wrong predecessor");
		assertTrue(shortestPath.get(4) == 5, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(5) == 24, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(5) == 8, "Wrong predecessor");

		shortestPath = graph.getSingleSourcePath(3);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(graph.getSingleSourceDistance(src) == 0, "Wrong shortest distance");
		assertNull(graph.getSingleSourcePredecessor(src), "Wrong predecessor");
		assertTrue(shortestPath.get(1) == 1, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(1) == 8, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(1) == src, "Wrong predecessor");
		assertTrue(shortestPath.get(2) == 2, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(2) == 12, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(2) == 1, "Wrong predecessor");
		assertTrue(shortestPath.get(3) == 3, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(3) == 21, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(3) == 2, "Wrong predecessor");

		linkVerticesInUndirectedGraphTest();

		src = 8;
		graph.Dijkstra(src);
		shortestPath = graph.getSingleSourcePath(5);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(graph.getSingleSourceDistance(src) == 0, "Wrong shortest distance");
		assertNull(graph.getSingleSourcePredecessor(src), "Wrong predecessor");
		assertTrue(shortestPath.get(1) == 7, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(7) == 6, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(7) == src, "Wrong predecessor");
		assertTrue(shortestPath.get(2) == 5, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(5) == 14, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(5) == 7, "Wrong predecessor");

		shortestPath = graph.getSingleSourcePath(1);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(graph.getSingleSourceDistance(src) == 0, "Wrong shortest distance");
		assertNull(graph.getSingleSourcePredecessor(src), "Wrong predecessor");
		assertTrue(shortestPath.get(1) == 7, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(7) == 6, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(7) == src, "Wrong predecessor");
		assertTrue(shortestPath.get(2) == 6, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(6) == 8, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(6) == 7, "Wrong predecessor");
		assertTrue(shortestPath.get(3) == 1, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(1) == 9, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(1) == 6, "Wrong predecessor");

		src = 1;
		graph.Dijkstra(src);
		shortestPath = graph.getSingleSourcePath(4);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(graph.getSingleSourceDistance(src) == 0, "Wrong shortest distance");
		assertNull(graph.getSingleSourcePredecessor(src), "Wrong predecessor");
		assertTrue(shortestPath.get(1) == 6, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(6) == 1, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(6) == src, "Wrong predecessor");
		assertTrue(shortestPath.get(2) == 7, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(7) == 3, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(7) == 6, "Wrong predecessor");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(8) == 9, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(8) == 7, "Wrong predecessor");
		assertTrue(shortestPath.get(4) == 4, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(4) == 14, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(4) == 8, "Wrong predecessor");

		src = 1;
		graph.Dijkstra(src);
		shortestPath = graph.getSingleSourcePath(3);
		assertTrue(shortestPath.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(graph.getSingleSourceDistance(src) == 0, "Wrong shortest distance");
		assertNull(graph.getSingleSourcePredecessor(src), "Wrong predecessor");
		assertTrue(shortestPath.get(1) == 6, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(6) == 1, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(6) == src, "Wrong predecessor");
		assertTrue(shortestPath.get(2) == 7, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(7) == 3, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(7) == 6, "Wrong predecessor");
		assertTrue(shortestPath.get(3) == 8, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(8) == 9, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(8) == 7, "Wrong predecessor");
		assertTrue(shortestPath.get(4) == 4, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(4) == 14, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(4) == 8, "Wrong predecessor");
		assertTrue(shortestPath.get(5) == 3, "It is not the shortest path");
		assertTrue(graph.getSingleSourceDistance(3) == 18, "Wrong shortest distance");
		assertTrue(graph.getSingleSourcePredecessor(3) == 4, "Wrong predecessor");
	}

	@Test
	public void FloydWarshallTest() {

		linkVerticesInDirectedGraphTest();
		
		int src = 6;
		graph.Dijkstra(src);
		graph.FloydWarshall();
		
		ArrayList<Integer> byDijkstra = graph.getSingleSourcePath(5);
		ArrayList<Integer> byFW = graph.getPath(src, 5);
		assertTrue(byDijkstra.get(0) == byFW.get(0), "Source is not the expected");
		assertTrue(byDijkstra.get(1) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(2) == byFW.get(2), "It is not the shortest path");
		assertTrue(byDijkstra.get(3) == byFW.get(3), "It is not the shortest path");
		assertTrue(byDijkstra.get(4) == byFW.get(4), "It is not the shortest path");
		
		byFW = graph.getPath(1, 5);
		assertTrue(byDijkstra.get(1) == byFW.get(0), "It is not the shortest path");
		assertTrue(byDijkstra.get(2) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(3) == byFW.get(2), "It is not the shortest path");
		assertTrue(byDijkstra.get(4) == byFW.get(3), "It is not the shortest path");
		
		byFW = graph.getPath(2, 5);
		assertTrue(byDijkstra.get(2) == byFW.get(0), "It is not the shortest path");
		assertTrue(byDijkstra.get(3) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(4) == byFW.get(2), "It is not the shortest path");

		byDijkstra = graph.getSingleSourcePath(3);
		byFW = graph.getPath(src, 3);
		assertTrue(byDijkstra.get(0) == src && graph.getLastSrc() == src, "Source is not the expected");
		assertTrue(byDijkstra.get(1) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(2) == byFW.get(2), "It is not the shortest path");
		assertTrue(byDijkstra.get(3) == byFW.get(3), "It is not the shortest path");
		
		byFW = graph.getPath(8, 4);
		assertTrue(byFW.isEmpty(), "The path must be empty as 4 is unreachable from 8");
		
		linkVerticesInUndirectedGraphTest();
		graph.FloydWarshall();
		
		src = 8;
		graph.Dijkstra(src);
		byDijkstra = graph.getSingleSourcePath(5);
		byFW = graph.getPath(src, 5);
		assertTrue(byDijkstra.get(0) == byFW.get(0), "It is not the shortest path");
		assertTrue(byDijkstra.get(1) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(2) == byFW.get(2), "It is not the shortest path");

		byDijkstra = graph.getSingleSourcePath(1);
		byFW = graph.getPath(7, 1);
		assertTrue(byDijkstra.get(1) == byFW.get(0));
		assertTrue(byDijkstra.get(2) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(3) == byFW.get(2), "It is not the shortest path");

		src = 1;
		graph.Dijkstra(src);
		byDijkstra = graph.getSingleSourcePath(3);
		byFW = graph.getPath(1, 3);
		assertTrue(byDijkstra.get(0) == byFW.get(0), "It is not the shortest path");
		assertTrue(byDijkstra.get(1) == byFW.get(1), "It is not the shortest path");
		assertTrue(byDijkstra.get(2) == byFW.get(2), "It is not the shortest path");
		assertTrue(byDijkstra.get(3) == byFW.get(3), "It is not the shortest path");
		assertTrue(byDijkstra.get(4) == byFW.get(4), "It is not the shortest path");
		assertTrue(byDijkstra.get(5) == byFW.get(5), "It is not the shortest path");
	}
	
	@Test
	public void primTest() {
		linkVerticesInUndirectedGraphTest();
		int src = 7;
		ArrayList<Edge<Integer>> mst = graph.primMinimumSpanningTree(src);
		int weight = 0;
		for(Edge<Integer> ed : mst) {
			weight += ed.getWeight();
		}
		assertTrue(weight == 35, "It is not a minimum spanning tree");
		src = 4;
		mst = graph.primMinimumSpanningTree(src);
		weight = 0;
		for(Edge<Integer> ed : mst) {
			weight += ed.getWeight();
		}
		assertTrue(weight == 35, "It is not a minimum spanning tree");
		mst = graph.primMinimumSpanningTree(src);
		weight = 0;
		for(Edge<Integer> ed : mst) {
			weight += ed.getWeight();
		}
		assertTrue(weight == 35, "It is not a minimum spanning tree");
		src = 3;
		mst = graph.primMinimumSpanningTree(src);
		weight = 0;
		for(Edge<Integer> ed : mst) {
			weight += ed.getWeight();
		}
		assertTrue(weight == 35, "It is not a minimum spanning tree");
	}
	
	@Test
	public void kruskalTest() {
		linkVerticesInUndirectedGraphTest();
		ArrayList<Edge<Integer>> mst = graph.kruskalMinimumSpannigTree();
		int weight = 0;
		for(Edge<Integer> ed : mst) {
			weight += ed.getWeight();
		}
		assertTrue(weight == 35, "It is not a minimum spanning tree");
	}

	private void vertexInsertionLoop() {
		int vertexCount = 0;
		for(int i = 0; i < 50; i++) {
			int r = (int)(Math.random()*100);
			if(graph.insertVertex(r)) {
				vertexCount++;
			}
			assertTrue(graph.containsVertex(r), "The vertex with key "+r+" must have been found as it was added either in a previous iteration of the for loop or in this iteration");
			assertTrue(graph.getOrder() == vertexCount, "The order is not the expected");
			assertTrue(graph.getAdjacent(r).isEmpty(), "The vertex just added should not have any edges");
			assertFalse(graph.isEmpty(), "Graph must not be empty after insertion");
		}
		assertFalse(graph.containsVertex(200), "No vertex with key 200 was added so it should not have been found");
	}

	private void verifyGraphContainsAllEdgesItShould() {
		for (Edge<Integer> ale : edges) {
			assertTrue(graph.containsEdge(ale.getSrc(), ale.getDst()), "A missing edge was found");
			assertTrue(graph.getEdgeWeight(ale.getSrc(), ale.getDst()) == ale.getWeight(), "The edge was found but it does not have the correct weight");
		}
	}
}
