package model.data_structures;

import java.util.Comparator;

public class Ordenador<T extends Comparable<T>> {

	/**
	 * Metodo que organiza el arreglo dado
	 * @param a Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 * @return ultima poscicion en la que hubo un intercambio
	 */
	private int partition(T[] a,int lo, int hi,Comparator<T> cmp) {		
		int i = lo+1; 
		int j = hi;		
		if(cmp!=null) {
			while(true) {
				while(cmp.compare(a[i], a[lo])<0) {
					i++;
					if(i==hi) {
						break;
					}
				}
				while(cmp.compare(a[lo], a[j])<0) {
					j--;
					if(j==lo) {
						break;
					}
				}
				if(i>=j)break;
				T tmp=a[i];
				a[i]=a[j];
				a[j]=tmp;
			}
			T tmp=a[lo];
			a[lo]=a[j];
			a[j]=tmp;
		}
		else {

			while(true) {
				while(a[i].compareTo(a[lo])<0) {
					i++;
					if(i==hi) {
						break;
					}
				}
				while(a[lo].compareTo(a[j])<0) {
					j--;
					if(j==lo) {
						break;
					}
				}
				if(i>=j) {
					break;
				}
				T tmp=a[i];
				a[i]=a[j];
				a[j]=tmp;
			}
			T tmp=a[lo];
			a[lo]=a[j];
			a[j]=tmp;
		}
		return j;
	}

	/**
	 * Metodo que organiza el arreglo dado por parametro con el aloritmo QuickSort
	 * @param a Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 */
	public void quickSort(T[] a,int lo, int hi, Comparator<T> cmp) {
		ramdom(a, hi);
		sort(a, lo, hi, cmp);
	}

	/**
	 * Mettodo recursivo que usa el algoritmo QuickSort para ordenar el arreglo dado por parametro
	 * @param a Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 */
	private void sort(T[] a, int lo, int hi,Comparator<T> cmp) {
		if(hi>lo) {
			int j=partition(a, lo, hi, cmp);
			sort(a,lo,j-1,cmp);
			sort(a,j+1,hi,cmp);
		}
	}

	/**
	 * Metodo que baraja los elementos del arreglo dado por parametro
	 * @param a Arreglo que se va a barajar
	 * @param hi limite superior del arreglo
	 */
	private void ramdom(T[] a,int hi) {
		T tmp;
		int j=0;
		int i=0;
		while(j<hi/2) {
			i=(int) (hi*Math.random());
			tmp=a[j];
			a[j]=a[i];
			a[i]=tmp;
			j++;
		}
	}

	/**
	 * Metodo que ordena el arreglo dado por parametro de forma descendente con el algoritmo de MergeSort
	 * @param a Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 * @param aux Arreglo auxiliar que se usara en el ordenamiento
	 */
	public void mergeSortMayor(T[] a, int left,int right,Comparator<T> cmp, T[] aux) {

		if(left<right) {
			int mid = left + (right - left) / 2;
			//se divide el arreglo en dos partes de forma recursiva
			mergeSortMayor(a, left, mid, cmp, aux);
			mergeSortMayor(a, mid+1, right, cmp, aux);
			//se mezclan las dos partes obtenidas de la division y se ordena
			mergeMayor(a, left, mid, right, cmp, aux);
		}
	}

	/**
	 * Metodo que mezcla las partes ordenadas del arreglo dado por parametro de forma descendente con el algoritmo de MergeSort
	 * @param a Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 * @param aux Arreglo auxiliar que se usara en el ordenamiento
	 */
	private void mergeMayor(T[] a, int left,int mid,int right,Comparator<T> cmp, T[] aux) {

		int i = left;//Indice de la parte izquierda
		int j = mid+1;//Indice de la parte derecha
		int k = left;
		if(cmp!=null) {
			while(k<=right) {
				aux[k]=a[k];
				k++;
			}
			k=left;
			while(i<=mid && j<=right) {
				if(cmp.compare(aux[i], aux[j])>=0) {
					a[k]=aux[i];
					k++;
					i++;
				}
				else {
					a[k]=aux[j];
					k++;
					j++;
				}
			}
			while(i<=mid) {
				a[k]=aux[i];
				i++;
				k++;
			}

			while(j<=right) {
				a[k]=aux[j];
				j++;
				k++;
			}

		}
		else {
			while(k<=right) {
				aux[k]=a[k];
				k++;
			}
			k=left;
			while(i<=mid && j<=right) {
				if(aux[i].compareTo(aux[j])>=0) {
					a[k]=aux[i];
					k++;
					i++;
				}
				else {
					a[k]=aux[j];
					k++;
					j++;
				}
			}
			while(i<=mid) {
				a[k]=aux[i];
				i++;
				k++;
			}

			while(j<=right) {
				a[k]=aux[j];
				j++;
				k++;
			}

		}
	}


	/**
	 * Metodo que ordena el arreglo dado por parametro de forma ascendente con el algoritmo de MergeSort
	 * @param mer Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 * @param aux Arreglo auxiliar que se usara en el ordenamiento
	 */
	public void mergeSortMenor(T[] a, int left,int right,Comparator<T> cmp, T[] aux) {

		if(left<right) {
			int mid = left + (right - left) / 2;
			//se divide el arreglo en dos partes de forma recursiva
			mergeSortMenor(a, left, mid, cmp, aux);
			mergeSortMenor(a, mid+1, right, cmp, aux);
			//se mezclan las dos partes obtenidas de la division y se ordena
			mergeMenor(a, left, mid, right, cmp, aux);
		}

	}

	/**
	 * Metodo que mezcla las partes ordenadas del arreglo dado por parametro de forma ascendente con el algoritmo de MergeSort
	 * @param mer Arreglo a ordenar
	 * @param lo Limite inferior de la parte del arreglo que se quiere ordenar
	 * @param hi Limite superior de la parte del arreglo que se quiere ordenar
	 * @param cmp Comparador que se usara para ordenar el arreglo en caso de ser null se usara la comparacion natural
	 * @param aux Arreglo auxiliar que se usara en el ordenamiento
	 */
	private void mergeMenor(T[] a, int left,int mid,int right,Comparator<T> cmp,T[] aux) {

		int i = left;//Indice de la parte izquierda
		int j = mid+1;//Indice de la parte derecha
		int k = left;
		if(cmp!=null) {
			while(k<=right) {
				aux[k]=a[k];
				k++;
			}
			k=left;
			while(i<=mid && j<=right) {
				if(cmp.compare(aux[i], aux[j])<=0) {
					a[k]=aux[i];
					k++;
					i++;
				}
				else {
					a[k]=aux[j];
					k++;
					j++;
				}
			}
			while(i<=mid) {
				a[k]=aux[i];
				i++;
				k++;
			}

			while(j<=right) {
				a[k]=aux[j];
				j++;
				k++;
			}

		}
		else {
			while(k<=right) {
				aux[k]=a[k];
				k++;
			}
			k=left;
			while(i<=mid && j<=right) {
				if(aux[i].compareTo(aux[j])<=0) {
					a[k]=aux[i];
					k++;
					i++;
				}
				else {
					a[k]=aux[j];
					k++;
					j++;
				}
			}
			while(i<=mid) {
				a[k]=aux[i];
				i++;
				k++;
			}

			while(j<=right) {
				a[k]=aux[j];
				j++;
				k++;
			}

		}
	}





	public void ShellSort( T[] res) {
		int salto, i;

		boolean cambios;
		for(salto=res.length/2; salto!=0; salto/=2){
			cambios=true;
			while(cambios){ 
				cambios=false;
				for(i=salto; i< res.length; i++){ 
					T A1 = res[i];
					T B1 = res[i-salto];
					if(less(A1,B1)){
						exchange(res,i,i-salto);
						cambios=true;
					}
				}
			}
		}
	}

	private boolean less(T a1, T b1) {
		int cont = a1.compareTo(b1);
		if(cont > 0)
			return true;
		else return false;
	}

	private void exchange(T[] res, int i, int j) {
		T aux;
		aux=res[i]; 
		res[i]=res[j];
		res[j]=aux;

	}
}
