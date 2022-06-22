package model.data_structures;

public class GraphChargeManager {
	
	String id;
	String lat;
	String lon;
	String[] adj;
	String[] infractions;
	
	public GraphChargeManager(String id, String lat, String lon, String[] adj, String[] infractions){
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.adj = new String[adj.length];
		for(int i = 0; i < adj.length ; i++){
			this.adj[i] = adj[i];
		}
		
		this.infractions = new String[infractions.length];
		for(int i = 0; i < infractions.length ; i++){
			this.infractions[i] = infractions[i];
		}
	}
	
	public String id(){
		return this.id;
	}
	
	public String lat(){
		return this.lat;
	}
	
	public String lon(){
		return this.lon;
	}
	
	public String[] adj(){
		return this.adj;
	}
	
	public String[] infractions(){
		return this.infractions;
	}

}
