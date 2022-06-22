package model.logic;

import java.util.Comparator;

public class res5A implements Comparable<res5A>{
	private double tim;
	private int id;
	private int[] ids;
	private int con;
	public res5A(double tim,int id,int[] ids,int con) {
		this.tim=tim;
		this.ids=ids;
		this.id=id;
		this.con=con;
	}
	public int id() {
		return id;
	}
	public double tim() {
		return tim;
	}
	public int[] ids() {
		return ids;
	}
	public int con() {
		return con;
	}
	public int compareTo(res5A b) {
		if(tim<b.tim()) {
			return -1;
		}
		else if(tim>b.tim) {
			return 1;
		}
		return 0;
	}
	
	public static class cmpCC implements Comparator<res5A>{
		/**
		 * metodo de comparacion de los Viajes de Uber por el momento temporal en el que fueron agregados
		 * ya sea por mes o dia o hora segun sea el caso
		 * @return 0 si tiempo = tiempo de com, 1 si tiempo > tiempo de com, -1 si tiempo < tiempo de com
		 */
		public int compare(res5A o1, res5A o2) {
			if(o1.con()-o2.con()<0){
				return -1;
			}else if(o1.con()-o2.con()>0){
				return 1;
			}
			else return 0;
		}
	}
}
