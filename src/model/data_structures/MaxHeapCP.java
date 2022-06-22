package model.data_structures;

public class MaxHeapCP<T extends Comparable<T>> {

	private int tamano;

	private int tamanoMax;

	private T[] elementos;

	/**
	 * 
	 * @param tam
	 */
	@SuppressWarnings("unchecked")
	public MaxHeapCP(int tam){
		tamanoMax=tam;
		tamano=0;
		elementos=(T[]) new Comparable[tamanoMax+1];
	}

	/**
	 * 
	 * @return
	 */
	public T darActual() {
		return elementos[tamano];
	}

	/**
	 * 
	 * @param pos
	 * @return
	 * @throws Exception
	 */
	private int darPapa(int pos)throws Exception {
		if(pos>1) {
			return pos/2;
		}
		else {
			throw new Exception("La pocision es la raiz no tiene papa");
		}
	}

	/**
	 * 
	 * @param pos
	 * @return
	 */
	private int darHijoL(int pos) {
		return pos*2;
	}

	/**
	 * 
	 * @param pos
	 * @return
	 */
	private int darHijoR(int pos) {
		return (pos*2)+1;
	}

	/**
	 * 
	 * @param pos
	 * @return
	 */
	private boolean esHoja(int pos) {
		if(pos <= tamano && pos >= tamano/2) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param posN
	 * @param posC
	 */
	private void cambiar(int posN,int posC) {
		T temp=elementos[posN];
		elementos[posN]=elementos[posC];
		elementos[posC]=temp;
	}

	/**
	 * 
	 * @param dato
	 */
	@SuppressWarnings("unchecked")
	public void agregar(T dato) {
		if(tamano+1>tamanoMax) {
			T[] temp=elementos;
			tamanoMax*=2;
			elementos=(T[]) new Comparable[tamanoMax+1];
			for(int i=0;i<temp.length;i++) {
				elementos[i]=temp[i];
			}
		}
		tamano++;
		elementos[tamano]=dato;
		int posActual = tamano; 
		try {
			while (elementos[posActual].compareTo(elementos[darPapa(posActual)])<0) { 
				cambiar(posActual, darPapa(posActual)); 
				posActual = darPapa(posActual);
			} 
		}
		catch (Exception e) {
			return;		
		}

	}

	private void heapMax(int pos) {
		if(esHoja(pos)) {

		}
		else {
			if (elementos[pos].compareTo(elementos[darHijoL(pos)])<0||  
					elementos[pos].compareTo(elementos[darHijoR(pos)])<0) { 

				if (elementos[darHijoL(pos)].compareTo(elementos[darHijoR(pos)])>0) { 
					cambiar(pos, darHijoL(pos)); 
					heapMax(darHijoL(pos));
				} 
				else { 
					cambiar(pos, darHijoR(pos)); 
					heapMax(darHijoR(pos)); 
				} 
			} 
		}
	}

	public T extraerMax() {
		T res=elementos[1];
		elementos[1]=elementos[tamano];
		tamano--;
		heapMax(1);
		return res;

	}

	public T buscarmax() {
		return elementos[1];
	}
	
	public int darTamano() {
		return tamano;
	}

	public boolean isEmpty() {
		if(tamano==0)
			return true;
		return false;
	}

	public Nodo<T> darElementoPocision(int i) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
