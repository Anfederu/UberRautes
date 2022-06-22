package model.data_structures;

import java.util.Comparator;

public class VerticeInfo implements Comparable<VerticeInfo>{
	
	int id;
	double lat;
	double lon;
	int movementId;
	int[] adj;
	int componetes=0;
	
	
	public VerticeInfo(int id, double lat,double lon,int movementId, int[] adj){
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		if(adj!=null) {
			this.adj = new int[adj.length];
			for(int i = 0; i < adj.length ; i++){
				this.adj[i] = adj[i];
			}
		}else {
			this.adj=null;
		}
		
		
	}
	
	public VerticeInfo(int id, double lat,double lon,int movementId, int[] adj,int com){
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		if(adj!=null) {
			this.adj = new int[adj.length];
			for(int i = 0; i < adj.length ; i++){
				this.adj[i] = adj[i];
			}
		}else {
			this.adj=null;
		}
		
		componetes=com;
	}
	
	public int id(){
		return this.id;
	}
	
	public int[] adj(){
		return this.adj;
	}
	
	public double lat(){
		return this.lat;
	}
	
	public int movementId(){
		return this.movementId;
	}
	
	public double lon(){
		return this.lon;
	}
	
	public String darKey(){
		return Integer.toString(id);
	}

	public int darComponentes() {
		return componetes;
	}
	
	public String toString() {
		return "id: "+id+" - lati: "+lat+" - longi: "+lon+" - zona_Uber: "+movementId+" - numComConected: "+componetes;
	}

	@Override
	public int compareTo(VerticeInfo o) {
		// TODO Auto-generated method stub
		if(this.id>o.id){
			return 1;
		}else if(this.id<o.id){
			return -1;
		}else{
			return 0;
		}
		
	}
	
	public static class cmpCom implements Comparator<VerticeInfo>{
		/**
		 * metodo de comparacion de los Viajes de Uber por el momento temporal en el que fueron agregados
		 * ya sea por mes o dia o hora segun sea el caso
		 * @return 0 si tiempo = tiempo de com, 1 si tiempo > tiempo de com, -1 si tiempo < tiempo de com
		 */
		public int compare(VerticeInfo o1, VerticeInfo o2) {
			if(o1.darComponentes()-o2.darComponentes()<0){
				return -1;
			}else if(o1.darComponentes()-o2.darComponentes()>0){
				return 1;
			}
			else return 0;
		}
	}
}

