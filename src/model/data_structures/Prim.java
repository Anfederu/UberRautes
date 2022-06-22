package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;

public class Prim {
	private static final double FLOATING_POINT_EPSILON = 1E-12;
	private double weight;       // total weight of MST
    private Queue<Edge> mst;     // edges in the MST
    private boolean[] marked;    // marked[v] = true iff v on tree
    private MinPQ<Edge> pq;      // edges with one endpoint in tree
    private int peso;
    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     * @throws Exception 
     */
    public Prim(EdgeWeightedGraph<?, ?> G,Comparator<Edge> cmp,int peso) throws Exception {
        this.peso=peso;
    	mst = new Queue<Edge>();
        pq = new MinPQ<Edge>(G.E(),cmp);
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)     // run Prim from all vertices to
            if (!marked[v]) prim(G, v);     // get a minimum spanning forest

        // check optimality conditions
        assert check(G);
    }
    
    public Prim(EdgeWeightedGraph<?, ?> G,Comparator<Edge> cmp,int peso, int ini) throws Exception {
        this.peso=peso;
    	mst = new Queue<Edge>();
        pq = new MinPQ<Edge>(G.E(),cmp);
        marked = new boolean[G.V()];
        prim(G, ini);     // get a minimum spanning forest

        // check optimality conditions
        assert check(G);
    }
    // run Prim's algorithm
    private void prim(EdgeWeightedGraph<?, ?> G, int s) throws Exception {
        scan(G, s);
        while (!pq.isEmpty()) {                        // better to stop when mst has V-1 edges
            Edge e = pq.delMin();                      // smallest edge on pq
            int v = e.either(), w = e.other(v);        // two endpoints
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) continue;      // lazy, both v and w already scanned
            mst.enqueue(e);                            // add e to MST
            weight += e.weight(peso);
            if (!marked[v]) scan(G, v);               // v becomes part of tree
            if (!marked[w]) scan(G, w);               // w becomes part of tree
        }
    }

    // add all edges e incident to v onto pq if the other endpoint has not yet been scanned
    private void scan(EdgeWeightedGraph<?, ?> G, int v) {
        assert !marked[v];
        marked[v] = true;
        Iterator<Edge> it=G.adj(v).iterator();
        while(it.hasNext()) {
        	Edge e=it.next();
            if (!marked[e.other(v)]) pq.insert(e);
        }
    }
        
    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph<?, ?> G) throws Exception {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight(peso);
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        
        for (Edge e : (Iterable<Edge>)G.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f :(Iterable<Edge>) G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight(peso) < e.weight(peso)) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }
}
