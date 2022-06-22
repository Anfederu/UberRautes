package model.data_structures;

import java.util.Comparator;

public class Nodo<T extends Comparable<T>> {

	
	/**
	 * Nodo siguiente
	*/
	private Nodo<T> siguiente;
	
	/**
	 * Nodo anterior
	 */
	private Nodo<T> anterior;
	
	/**
	 * Elemento nodo
	 */
	private T elemento;
	
	public Nodo(Nodo<T> nuevo) {
		elemento=nuevo.darElemento();
		siguiente=null;
		anterior=null;
	}
	
	/**
	 * Construir un nodo
	 * @param elem elemento del nodo
	 * @param ant Nodo anterior
	 * @param sig Nodo siguiente
	 */
	public Nodo(T elem, Nodo<T> sig,Nodo<T> ant) {
		elemento=elem;
		siguiente=sig;
		anterior=ant;
	}
	
	public Nodo(T elem){
		elemento=elem;
		siguiente=null;
		anterior=null;
	}
	
	/**
	 * Retorna el nodo siguiente
	 * @return nodo siguiente
	 */
	public Nodo<T> darSiguiente() {
		return  siguiente;
	}
	
	public Nodo<T> darAnteriror(){
		return anterior;
	}

	/**
	 * Retorna el elemento del nodo
	 * @return elemento del nodo
	 */
	public T darElemento() {
		return elemento;
	}


	/**
	 * Cambia el nodod siguiente por el nodo dado por parametro
	 * @param sig Nuevo nodo siguiente
	 */
	public void cambiarSiguiente(Nodo<T> sig) {
		siguiente=sig;
		
	}
	
	public void cambiarAnterior(Nodo<T> ant) {
		anterior=ant;
		
	}
	

	/**
	 * Compara el elemento del nodo con el elemento dado por parametro
	 * @param com elemento a ser comparado
	 * @return 0 si com = elemento del nodo, 1 si com < elemento del nodo, -1 si com > elemento del nodo
	 */
	public int compareTo(T com) {
		return elemento.compareTo(com);
	}
	
	/**
	 * Compara el elemento del nodo con el elemento dado por parametro usando el comparador dado por parametro
	 * @param com elemento con el que se va a compara
	 * @param cmp Comparador usado para la comparaacion
	 * @return 0 si elemento del nodo = com, 1 si elemento del nodo > com, -1 si elemento del nodo < com
	 */
	public int compareTo(T com,Comparator<T> cmp){
		return cmp.compare((T) elemento	, com);
	}
	
}
