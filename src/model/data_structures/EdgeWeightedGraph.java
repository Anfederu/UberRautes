package model.data_structures;

import java.util.Iterator;

public class EdgeWeightedGraph <Key extends Comparable<Key>,Value extends Comparable<Value>> {
	private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<Edge>[] adj;
    private HashTable<Key, Value> hash;
    private DepthFirstSerch<Key, Value> dfs;
    private CC<Key, Value> cc;
    
    
    /**
     * Initializes an empty edge-weighted graph with {@code V} vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    @SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V) {
    	hash=new HashTable<Key, Value>(V,false);
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
        
    }
    public Bag<Edge>[] adjacentes(){
    	return this.adj;
    }
    
    public HashTable<Key, Value> hash(){
    	return this.hash;     
    }

    public Value getInfoVertex(Key k) {
    	return hash.get(k);
    }
    
    public void setInfoVertex(Key k,Value v) {
    	hash.put(k, v);
    }
    
    public double getCost(Key k,Key f) throws Exception {
    	int kn=hash.hash(k);
    	int fn=hash.hash(f);
    	Iterator<Edge> it=adj(kn).iterator();
    	Edge actual=null;
    	while(it.hasNext()) {
    		actual=it.next();
    		if(actual.either()==kn && actual.other(kn)==fn) {
    			return actual.weight(1);
    		}
    	}
    	return -1;
    }
    
    public void setCost(Key k,Key f,double cost) {
    	int kn=hash.hash(k);
    	int fn=hash.hash(f);
    	Iterator<Edge> it=adj(kn).iterator();
    	Edge actual=null;
    	while(it.hasNext()) {
    		actual=it.next();
    		if(actual.either()==kn && actual.other(kn)==fn) {
    			actual.setWeight(cost);
    		}
    	}
    }
    
    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this edge-weighted graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        Iterator<Edge> it=adj[v].iterator();
        boolean isAlredy=false;
        while(it.hasNext() && !isAlredy) {
        	Edge ed=it.next();
        	if(ed.either()==v) {
        		if(ed.other(v)==w) {
        			isAlredy=true;
        		}
        	}
        	if(ed.either()==w) {
        		if(ed.other(w)==v) {
        			isAlredy=true;
        		}
        	}
        }
        if(!isAlredy) {
        	adj[v].add(e);
            adj[w].add(e);
            E++;
        }
        
    }
    
    public void addEdge(Key e,Key e2,double cost,double cost2,double cost3 ) {
        int v = hash.hash(e);
        int w = hash.hash(e2);
        Edge nue=new Edge(v, w, cost,cost2,cost3);
        addEdge(nue);
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}               
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        return adj[v].size();
    }

    
    
    /**
     * Returns all edges in this edge-weighted graph.
     * To iterate over the edges in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    public void dFS(Key s) {
    	dfs=new DepthFirstSerch<Key, Value>(this, hash.hash(s));
    }
    
    public int cC() {
    	cc=new CC<Key, Value>(this);
    	return cc.count();
    }
    
    public int hashT(Key k) {
    	return hash.hash(k);
    }
    
    /**
     * Returns a string representation of the edge-weighted graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
    
    public Iterator<Key> llaves(){
    	return hash.iterator();
    }
    
    public int componetesConectadasVertice(Key ke) {
    	dfs=new DepthFirstSerch<Key, Value>(this, hash.hash(ke));
    	return dfs.count();
    }
    
}