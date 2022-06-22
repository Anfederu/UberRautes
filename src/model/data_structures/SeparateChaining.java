package model.data_structures;

import java.util.Iterator;

public class SeparateChaining <Key extends Comparable<Key>, Value extends Comparable<Value>> {

	private int tamano;

	private int tamActual;
	//3000017,
	private int[] primos={6000011,12000017,1548685863};

	private int posPrimo=0;
	
	private NodoHash<Value,Key>[] tabla;

	@SuppressWarnings("unchecked")
	public SeparateChaining () {
		this.tamano=primos[posPrimo];
		tabla=new NodoHash[primos[posPrimo]];
		tamActual=0;
	}

	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff)%tamano;
	}


	@SuppressWarnings("unchecked")
	public void put(NodoHash<Value, Key> val,Key key) throws Exception {
		int hash=hash(key);

		if((tamActual+1)/tamano>=5) {
			NodoHash<Value,Key>[] tmp=tabla;
			posPrimo++;
			if(posPrimo<2){
				tamano=primos[posPrimo];
				tamActual=0;
				tabla=new NodoHash[primos[posPrimo]];
				for(int i=0;i<tmp.length;i++) {
					NodoHash<Value,Key> actual=tmp[i];
					while(actual!=null) {
						put(actual, actual.darKey());
						actual=actual.darSiguiente();
					}
				}
			}
			else{
				throw new Exception("Tamaño maximo alcanzado agregue mas primos");
			}
		}

		if(tabla[hash]!=null) {
			NodoHash<Value,Key> actual=tabla[hash];
			while(actual.darSiguiente()!=null) {
				actual=actual.darSiguiente();
			}
			val.cambiarAnterior(actual);;
			actual.cambiarSiguiente(val);
			tamActual++;
		}
		else {

			tabla[hash]=val;
			tamActual++;
		}
	}

	@SuppressWarnings("unchecked")
	public Value get(Key key) {
		Value res=null;
		int hash=hash(key);
		NodoHash<Value,Key> actual=tabla[hash];
		while(actual!=null) {
			if(key.equals(actual.darKey())) {
				res = (Value) actual.darElemento();
				break;
			}
			actual=actual.darSiguiente();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public Cola<Value> getMore(Key key){
		int hash=hash(key);
		NodoHash<Value,Key> actual=tabla[hash];
		Cola<Value> resCola=new Cola<Value>();
		while(actual!=null ) {
			if(key.equals(actual.darKey())) {
				resCola.agregar((Value)actual.darElemento());
			}
			actual=actual.darSiguiente();
		}
		return resCola;
	}

	@SuppressWarnings("unchecked")
	public Value delete(Key key) {
		Value res=null;
		int hash=hash(key);
		NodoHash<Value,Key> actual=tabla[hash];
		while(actual!=null) {
			if(key.equals(actual.darKey())) {
				if(actual.darAnterior()==null) {
					if(actual.darSiguiente()!=null) {
						tabla[hash]=actual.darSiguiente();
						actual.darSiguiente().cambiarAnterior(null);
					}
				}
				else {
					if(actual.darSiguiente()!=null) {
						actual.darAnterior().cambiarSiguiente(actual.darSiguiente());
						actual.darSiguiente().cambiarAnterior(null);
					}
					actual.darAnterior().cambiarSiguiente(null);
				}
				res = (Value) actual.darElemento();
				tamActual--;
				break;
			}
			actual=actual.darSiguiente();
		}
		return res;
	}

	public int darTamActual() {
		return tamActual;
	}

	public Iterator<Key> keys()  {
		Cola<Key> queue = new Cola<Key>();
		for (int i = 0; i < tamano; i++) {
			NodoHash<Value,Key> x = tabla[i];
			while(x!=null) {
				queue.agregar((Key) x.darKey());
				x = x.darSiguiente();
			}        	    
		}
		return queue.iterator();
	}

	@SuppressWarnings("unchecked")
	public NodoHash<Value, Key>[] toArray() {
		if(tamActual!=0) {
			NodoHash<Value, Key>[] vec=(NodoHash<Value, Key>[]) new NodoHash[tamActual];
			int j=0;
			for (int i = 0; i < tamano; i++) {
	        	NodoHash<Value,Key> x = tabla[i];
	        	while(x!=null) {
	        		vec[j]=x;
	        		j++;
	        		x = x.darSiguiente();
	        	}        	    
	        }
			return vec;
		}
		return null;
	}
}
