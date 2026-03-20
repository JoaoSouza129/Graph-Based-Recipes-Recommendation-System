
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {
	private List<Edge> edges;
	private Set<String> nodes;
	private Map<String, Set<String>> adj;

	@SuppressWarnings("unchecked")
	/*
	 * Construtor da classe Graph
	 */
	public Graph() {
		this.edges = new ArrayList<>();
		this.nodes = new HashSet<>();
		this.adj = new HashMap<>();

	}

	/*
	 * Método para adicionar uma aresta ao grafo
	 * @param e a aresta a ser adicionada
	 */
	public void addEdge(Edge e) {
		edges.add(new Edge(e.from, e.to, e.idReceita));
		edges.add(e.reverse());
		nodes.add(e.from);
		nodes.add(e.to);

		// Atualizar lista de adjacência
		adj.computeIfAbsent(e.from, k -> new HashSet<>()).add(e.to);
	}
	/*
	 * Método para verificar se existe um caminho entre dois nós
	 * @param from nó de origem
	 * @param to nó de destino
	 * @return true se existir caminho, false caso contrário
	 */
	public boolean temCaminho(String from, String to) {
		if (from.equals(to)) {
			return true;
		}

		Set<String> visitado = new HashSet<>();
		Queue<String> fila = new LinkedList<>();
		fila.offer(from);
		visitado.add(from);

		while (!fila.isEmpty()) {
			String atual = fila.poll();

			if (atual.equals(to)) {
				return true;
			}

			Set<String> vizinhos = adj.getOrDefault(atual, new HashSet<>());
			for (String vizinho : vizinhos) {
				if (!visitado.contains(vizinho)) {
					visitado.add(vizinho);
					fila.offer(vizinho);
				}
			}
		}

		return false;
	}
	/*
	 * Método para imprimir o grafo
	 */
	public void imprimirGrafo() {
		System.out.println("Nós do grafo: " + nodes);
		System.out.println("\nConexões:");
		for (String node : nodes) {
			Set<String> vizinhos = adj.getOrDefault(node, new HashSet<>());
			if (!vizinhos.isEmpty()) {
				System.out.println(node + " -> " + vizinhos);
			}
		}
	}
	
	/*
	 * Método para obter os de nós no grafo
	 * @return o conjunto de nós
	 */
	public Set<String> getNodes() {
		return new HashSet<>(nodes);
	}
	/*
	 * Método para obter as arestas do grafo
	 * @return a lista de arestas
	 */
	public List<Edge> getEdges() {
		return new ArrayList<>(edges);
	}
}