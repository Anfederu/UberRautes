package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;


public class Cola<T extends Comparable<T>> implements Comparable<Cola <T>> {

	/**
	 * Atributo que representa el primer elemnto en la cola
	 */
	private Nodo<T> inicioCola;

	/**
	 * Atributo que representa el ultimo elemnto en la cola
	 */
	private Nodo<T> finCola;

	/**
	 * Atributo que representa el tamaño de la cola
	 */
	private int tamaNo;

	/**
	 * Crea una nueva cola definiendo el primer elemento como el elemnto dado por parametro
	 * @param first primer Nodo de la cola
	 */
	public Cola(Nodo<T> first){		
		inicioCola=first;
		finCola=inicioCola;
		tamaNo=1;
	}

	/**
	 * Crea una nueva cola con todos sus elemento iniciados en null
	 */
	public Cola(){
		inicioCola=null;
		finCola=null;
		tamaNo=0;

	}

	/**
	 * Retorna el tamano de la lista
	 * @return entero que representa el tamano de la cola
	 */
	public int darTamano(){
		return tamaNo;
	}

	/**
	 * Metod que retorna un booleano con la informacion de la lista si esta vacia
	 * @return boolean true si lista esta vacia, false si no esta vacia
	 */
	public boolean isEmpty(){
		if(tamaNo==0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Se agrega un dato a la cola de la lista
	 * @param dato Dato a ser agregado
	 */
	public void agregar(Nodo<T> dato){

		if(inicioCola==null){
			inicioCola=dato;
			finCola=inicioCola;
			tamaNo++;
		}
		else{
			finCola.cambiarSiguiente(dato);
			finCola=finCola.darSiguiente();
			finCola.cambiarSiguiente(null);
			tamaNo++;
		}
	}

	public void agregar(T dato){

		Nodo<T>nue =new Nodo<T>(dato);
		if(inicioCola==null){
			inicioCola=nue;
			finCola=inicioCola;
			tamaNo++;
		}
		else{
			finCola.cambiarSiguiente(nue);
			finCola=finCola.darSiguiente();
			finCola.cambiarSiguiente(null);
			tamaNo++;
		}
	}

	/**
	 * Avanzar un nodo en la lista (actualizar el nodo actual al pr�ximo nodo. El nodo actual se convierte 
	 * en null si no hay un elemento siguiente.)
	 * @return nodo en la ultima poscicion de la cola
	 */
	public Nodo<T> avanzar(){
		Nodo<T> res=inicioCola;
		if(inicioCola!=null) {
			inicioCola=inicioCola.darSiguiente();
		}
		else {
			return null;
		}
		tamaNo--;
		return res;
	}

	/**
	 * Metodo que retorna el primer elemento de la cola
	 * @return Nodo<T> que representa el primer elemento de la cola
	 */
	public Nodo<T> darInicioCola(){
		return inicioCola;
	}

	/**
	 * Reinicia la cola
	 */
	public void reiniciarCola(){
		inicioCola=null;
		finCola=null;
		tamaNo=0;
	}

	/**
	 * Metodo que retorna el ultimo elemento de la cola
	 * @return Nodo<T> que representa el ultimo elemento de la cola
	 */
	public Nodo<T> darActual() {
		return finCola;
	}

	/**
	 * Elimina todos los elementos de la cola que cumplan la condicion
	 * @throws Exception 
	 */
	public void eliminar(T dato, Comparator<T> cmp) throws Exception {
		Cola<T> copia= new Cola<T>();
		Nodo<T> actual=null;
		if(!isEmpty()) {			
			while(!isEmpty()) {
				actual=new Nodo<T>(avanzar());
				if(actual.compareTo(dato, cmp)==0){

				}
				else{
					copia.agregar(actual);
				}
			}
			reiniciarCola();
			inicioCola=copia.darInicioCola();
			finCola=copia.darActual();
		}
		else throw new Exception("La lista esta vacia no hay nada para eliminar");
	}

	/**
	 * Retorna el elemento dada la pocision indicada
	 * @param i Pocision del elemento buscado
	 * @return elemento buscado
	 * @throws Exception 
	 */
	public Nodo<T> darElementoPocision(int i) throws Exception {
		Cola<T> copia= new Cola<T>();
		Nodo<T> actual=null;
		Nodo<T> res=null;
		if(!isEmpty()) {
			int j=0;
			actual=avanzar();
			while(j<tamaNo) {
				if(j==i){
					res=actual;
				}
				copia.agregar(actual);
				actual=avanzar();
				j++;
			}
			inicioCola=copia.darInicioCola();
			finCola=copia.darActual();
		}
		else throw new Exception("La lista esta vacia no hay nada para eliminar");
		return res;
	}



	/**
	 * Buscar los datos en el arreglo que cumplan la condicion.
	 * @param dato Objeto de busqueda en la cola
	 * @return lista de elementos buscados. null si no se encontro ningun dato.
	 * @throws Exception 
	 */
	public Cola<T> buscar(T dato, Comparator<T> cmp) throws Exception {
		Cola<T> copia= new Cola<T>();
		Cola<T> res=new Cola<T>();
		Nodo<T> actual=null;
		Nodo<T> agreRes=null;
		if(!isEmpty()) {
			if(cmp!=null) {
				while(!isEmpty()) {
					actual=new Nodo<T>(avanzar());					
					if(actual.compareTo(dato, cmp)==0){
						agreRes=new Nodo<T>(actual);
						res.agregar(agreRes);					
					}
					copia.agregar(actual);
				}
				reiniciarCola();
				inicioCola=copia.darInicioCola();
				finCola=copia.darActual();
				tamaNo=copia.darTamano();

			}
			else {
				while(!isEmpty()) {
					actual=new Nodo<T>(avanzar());
					if(actual.compareTo(dato)==0){
						agreRes=new Nodo<T>(actual);
						res.agregar(agreRes);				
					}
					copia.agregar(actual);
				}
				reiniciarCola();
				inicioCola=copia.darInicioCola();
				finCola=copia.darActual();
				tamaNo=copia.darTamano();
			}
		}
		else throw new Exception("La lista esta vacia no hay nada para buscar");
		return res;
	}

	/**
	 * Buscar los datos en el arreglo que cumplan la condicion.
	 * @param dato Objeto de busqueda en la cola
	 * @return Nodo de elemento buscado. null si no se encontro ningun dato.
	 * @throws Exception 
	 */
	public Nodo<T> buscarPrimero(T dato, Comparator<T> cmp) {
		Cola<T> copia= new Cola<T>();
		Nodo<T> res=null;
		Nodo<T> actual=null;
		boolean encontrado=false;
		if(!isEmpty()) {
			if(cmp!=null) {
				while(!isEmpty()) {
					actual=new Nodo<T>(avanzar());
					if(!encontrado && actual.compareTo(dato, cmp)==0){
						res= actual;
						encontrado=true;
					}
					else {
						copia.agregar(actual);
					}
				}
				inicioCola=copia.darInicioCola();
				finCola=copia.darActual();
				tamaNo=copia.darTamano();
			}
			else {
				while(!isEmpty()) {
					actual=new Nodo<T>(avanzar());
					if(!encontrado && actual.compareTo(dato)==0){
						res=actual;	
						encontrado=true;
					}
					copia.agregar(actual);
				}
				inicioCola=copia.darInicioCola();
				finCola=copia.darActual();
				tamaNo=copia.darTamano();
			}
		}

		return res;
	}


	public T[] toArray() {

		if(!isEmpty()) {
			@SuppressWarnings("unchecked")
			T[] vec=(T[])new Comparable[tamaNo];
			Nodo<T> actual=null;
			int i=0;
			while(!isEmpty()) {
				actual=avanzar();
				actual.cambiarSiguiente(null);
				vec[i]=actual.darElemento();
				i++;
			}
			return vec;
		}
		return null;
	}

	public Iterator<T> iterator()  {
		return new ListIterator(inicioCola);  
	}


	private class ListIterator implements Iterator<T> {
		private Nodo<T> current;

		public ListIterator(Nodo<T> prim) {
			current = prim;
		}


		public boolean hasNext() {
			if(current.darSiguiente()!=null) {
				return true;
			}
			return false;
		}


		public T next() {
			if (hasNext());{
				T item = current.darElemento();
				current = current.darSiguiente(); 
				return item;
			}       
		}

	}


	public int compareTo(Cola<T> o) {
		if(tamaNo-o.darTamano()<0) {
			return -1;
		}else if(tamaNo-o.darTamano()>0) {
			return 1;
		}
		return 0;
	}
}
