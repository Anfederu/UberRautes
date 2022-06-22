package Infozonas;

public class ChargeManager {
	
	int id;
	double lat;
	double lon;
	int movementId;
	Adyacente[] adj;
	
	
	public ChargeManager(int id, double lat,double lon,int movementId, Adyacente[] adj){
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.adj = new Adyacente [adj.length];
		for(int i = 0; i < adj.length ; i++){
			this.adj[i] = adj[i];
		}
	}
	
	public int id(){
		return this.id;
	}
	
	public Adyacente[] adj(){
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
}
