package Infozonas;

public class Adyacente {
	
	int id;
	double distancia;
	double tiempo;
	double velocidad;
	
	public  Adyacente(int id,double distancia,double tiempo,double velocidad){
		this.id = id;
		this.distancia = distancia;
		this.tiempo = tiempo;
		this.velocidad = velocidad;
	}
	
	public int id(){
		return this.id;
	}
	
	public double distancia(){
		return this.distancia;
	}
	
	public double tiempo(){
	   return this.tiempo;
	}
	
	public double velocidad(){
		return this.velocidad;
	}

}
