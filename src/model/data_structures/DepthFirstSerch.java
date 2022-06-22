package model.data_structures;

public class DepthFirstSerch<Key extends Comparable<Key>,Value extends Comparable<Value>> {
	private boolean[] marked;    // marked[v] = is there an s-v path?
	private int count;           // number of vertices connected to s

	/**
	 * Computes the vertices in graph {@code G} that are
	 * connected to the source vertex {@code s}.
	 * @param G the graph
	 * @param s the source vertex
	 * @throws IllegalArgumentException unless {@code 0 <= s < V}
	 */
	public DepthFirstSerch(EdgeWeightedGraph<Key, Value> G, int s) {
		marked = new boolean[G.V()];
		validateVertex(s);
		dfs(G, s);
	}

	// depth first search from v
	private void dfs(EdgeWeightedGraph<Key, Value> G, int v) {
		count++;
		marked[v] = true;
		for (Edge w : G.adj(v)) {
			int w2=w.other(v);
			if (!marked[w2]) {
				dfs(G, w2);
			}
		}
	}

	/**
	 * Is there a path between the source vertex {@code s} and vertex {@code v}?
	 * @param v the vertex
	 * @return {@code true} if there is a path, {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean marked(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns the number of vertices connected to the source vertex {@code s}.
	 * @return the number of vertices connected to the source vertex {@code s}
	 */
	public int count() {
		return count;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
	
	public boolean[] marcados() {
		return marked;
	}
}

