package Infozonas;

public class cargaGrafo {
	
	private ChargeManager[] vertices;
	
	public cargaGrafo( ChargeManager[] vertices){
		for(int i = 0 ;i<vertices.length;i++){
			this.vertices[i]=vertices[i];
		}
	}
	
	public ChargeManager[] vertices(){
		return this.vertices;
	}

}
