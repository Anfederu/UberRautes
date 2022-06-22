package Infozonas;

public class Feature {
	
	String type;
	Geometry geometry;
	Properties properties;
	
	public Feature(String type, Geometry geo, Properties properties){
		this.type = type;
		this.geometry = geo;
		this.properties = properties;
	}
	
	public String tipo(){
		return this.type;
	}

	public Geometry geometry(){
		return this.geometry;
	}
	
	public Properties properties(){
		return this.properties;
	}
}
