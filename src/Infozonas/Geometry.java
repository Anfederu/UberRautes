package Infozonas;

public class Geometry {
	
	String type;
	Double[][][][] coordinates;
	
	public Geometry(String type, Double[][][][] coordenadas){
		
		this.type = type;
		this.coordinates = coordenadas;
	}
	
	public String type(){
		return this.type;
		
	}

	public Double[][][][] coordinates(){
		return this.coordinates;
	}
}
