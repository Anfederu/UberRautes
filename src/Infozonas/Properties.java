package Infozonas;

public class Properties {
	
	int cartodb_id;
	String scacodigo ;
	int scartipo;
	String scanombre;
	double shape_leng;
	double shape_area;
	int MOVEMENT_ID;
	String DISPLAY_NAME;
	
	public Properties(int cartod_id, String scarCodigo,int scarTipo,String scarNombre,double shape_leng,double shape_area,int movement_id,String display_name ){
	
		this.cartodb_id = cartod_id;
		this.scacodigo = scarCodigo;
		this.scartipo = scarTipo;
		this.scanombre = scarNombre;
		this.shape_leng = shape_leng;
		this.shape_area = shape_area;
		this.MOVEMENT_ID = movement_id;
		this.DISPLAY_NAME = display_name;
	}
	
	public int movement_id(){
		return this.MOVEMENT_ID;
	}
	
	public String scanombre(){
		return this.scanombre;
	}
	
	public double shape_leng(){
		return this.shape_leng;
	}
	
	public double shape_area(){
		return this.shape_area;
	}
	
	
	

}
