package model.data_structures;

import java.util.Comparator;

public class Edge implements Comparable<Edge> { 

    private final int v;
    private final int w;
    private double weight_1;
    private double weight_2;
    private double weight_3;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} 
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double weight,double weight2,double weight3) {
        if (v < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (w < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight_1 = weight;
        weight_2=weight2;
        weight_3=weight3;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     * @throws Exception 
     */
    
    public double weight(int i) throws Exception {
    	if(i==1) {
    		return weight_1;
    	}
    	else if(i==2) {
    		return weight_2;
    	}
    	else if(i==3) {
    		return weight_3;
    	}
    	else throw new Exception("Peso invalido");
        
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public int other2() {
        return w;
    }
    
    public void setWeight(double we) {
    	weight_1=we;
    	
    }
    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight_1, that.weight_1);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight_1);
    }

    public static class cmp2 implements Comparator<Edge>{
		/**
		 * metodo de comparacion de los Viajes de Uber por el momento temporal en el que fueron agregados
		 * ya sea por mes o dia o hora segun sea el caso
		 * @return 0 si tiempo = tiempo de com, 1 si tiempo > tiempo de com, -1 si tiempo < tiempo de com
		 */
		public int compare(Edge o1, Edge o2) {
			if(o1.weight_2-o2.weight_2<0){
				return -1;
			}else if(o1.weight_2-o2.weight_2>0){
				return 1;
			}
			else return 0;
		}
	}
}