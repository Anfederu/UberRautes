package model.data_structures;

import java.util.Comparator;

public class NodoHash<T extends Comparable<T>,Key> {

	/**
	 * Nodo siguiente
	 */
	private NodoHash<T,Key> siguiente;

	/**
	 * Nodo anterior
	 */
	private NodoHash<T,Key> anterior;

	/**
	 * Elemento nodo
	 */
	private Comparable<T> elemento;

	private Key key;

	public NodoHash(NodoHash<T,Key> nuevo,Key pKey) {
		elemento=nuevo.darElemento();
		key=pKey;
		siguiente=null;
		anterior=null;
	}

	/**
	 * Construir un nodo
	 * @param elem elemento del nodo
	 * @param ant Nodo anterior
	 * @param sig Nodo siguiente
	 */
	public NodoHash(T elem, NodoHash<T,Key> sig,NodoHash<T,Key> ant,Key pkey) {
		elemento=elem;
		key=pkey;
		siguiente=sig;
		anterior=ant;
	}

	public NodoHash(T elem,Key pKey){
		elemento=elem;
		key=pKey;
		siguiente=null;
		anterior=null;
	}
	public NodoHash<T,Key> darSiguiente() {
		return siguiente;
	}
	
	public NodoHash<T, Key> darAnterior(){
		return anterior;
	}

	public Comparable<T> darElemento() {
		return elemento;
	}

	public void cambiarSiguiente(NodoHash<T,Key> sig) {
		siguiente=sig;
	}

	public void cambiarAnterior(NodoHash<T, Key> ant) {
		anterior=ant;
	}
	public int compareTo(T com) {
		return elemento.compareTo(com);
	}

	public void cambiarValor(T com) {
		elemento =com;
	}
	
	public int compareTo(Comparable<T> com, Comparator<Comparable<T>> cmp) {
		return cmp.compare( elemento, com);
	}

	public Key darKey() {
		return key;
	}
}
