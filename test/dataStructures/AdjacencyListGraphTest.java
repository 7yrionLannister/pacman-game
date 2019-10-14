package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AdjacencyListGraphTest {
	private AdjacencyListGraph<Integer> graph;

	private void setupStageDirectedAndWeighted() {
		graph = new AdjacencyListGraph<>(true, true);
	}

	private void setupStageUndirectedAndUnweighted() {
		graph = new AdjacencyListGraph<>(false, false);
	}

	private void setupStageDirectedAndUnweighted() {
		graph = new AdjacencyListGraph<>(true, false);
	}

	private void setupStageUndirectedAndWeighted() {
		graph = new AdjacencyListGraph<>(false, true);
	}

	private void setupStageGraphWithIsolatedVertices() {
		for (int i = 0; i < 8; i++) {
			graph.insertVertex(i+1);
		}
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
		//TODO aqui enlazar vertices y probar que se cumplan las propiedades
	}

	@Test
	public void linkVerticesInUndirectedAndWeightedGraphTest() {
		setupStageUndirectedAndWeighted();
		assertTrue(graph.isEmpty(), "Graph must be initially empty");
		setupStageGraphWithIsolatedVertices();
		//TODO aqui enlazar vertices y probar que se cumplan las propiedades
	}
	
	@Test
	public void unlinkVerticesTestInDirectedAndWeightedGraphTest() {
		//TODO implementar
	}
	
	@Test
	public void unlinkVerticesTestInUndirectedAndWeightedGraphTest() {
		//TODO implementar
	}

	//TODO de aqui en adelante es indiferente que tipo de grafo se use,
	//asi que se haran con grafos dirigidos y con peso
	
	@Test
	public void BFSTest() {
		//TODO implementar
	}

	@Test
	public void DFSTest() {
		//TODO implementar
	}

	@Test
	public void DFSWithGivenSourceTest() {
		//TODO implementar
	}

	@Test
	public void deleteVertexTest() {
		//TODO implementar
	}

	@Test
	public void getBFSPathTest() {
		//TODO implementar
	}

	@Test
	public void DijkstraTest() {
		//TODO implementar
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
}
