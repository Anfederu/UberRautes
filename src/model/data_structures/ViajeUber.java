package model.data_structures;

import java.util.Comparator;

public class ViajeUber implements Comparable<ViajeUber> {

	
	/**
	 * sourceid: Identificador �nico de la zona de origen de los viajes relacionados
	 */
	private int sourceid;
	
	/**
	 * dstid: Identificador �nico de la zona de destino de los viajes relacionados
	 */
	private int dstid;
	
	
	private int trimestre;
	
	/**
	 * month: n�mero del mes de los viajes relacionados
	 */
	private	int hod;
	
	/**
	 * mean_travel_time: tiempo promedio en segundos de los viajes relacionados
	 */
	private double mTravelTime;
	
	/**
	 * standard_deviation_travel_time: desviaci�n est�ndar de los viajes relacionados
	 */
	private double sDeviationTime;
	
	/**
	 * geometric_mean_travel_time: tiempo promedio geom�trico en segundos de los viajes relacionados
	 */
	private double gMTravelTime;
	
	/**
	 * geometric_standard_deviation_travel_time: desviaci�n est�ndar geom�trica de los viajes relacionados
	 */
	private double gSDeviationTime;
	
	
	public ViajeUber(int sour,int dsti,int hora, double MTavelT, double SDeviationT, double GMTravelT,double GSDeviationT,int trim) {
		sourceid=sour;
		dstid=dsti;
		hod=hora;
		mTravelTime=MTavelT;
		sDeviationTime=SDeviationT;
		gMTravelTime=GMTravelT;
		gSDeviationTime=GSDeviationT;
		trimestre=trim;
		
	}

	/**
	 * metodo que retorna el momento temporal en la que se agrego el viaje puede ser mes o dia o hora
	 * @return momento temporal en que se agrego el viaje
	 */
	public int darTiempo() {
		return hod;
	}
	
	/**
	 * metodo que retorna la zona de origen del viaje
	 * @return identificador de la zona de origen
	 */
	public int darZonaOrigen() {
		return sourceid;
	}

	/**
	 * metodo que retorna la zona de destino del viaje
	 * @return identificador de la zona de destino
	 */
	public int darDestino(){
		return dstid;
	}

	/**
	 * metodo que retorna el tiempo promedio geom�trico en segundos de los viajes relacionados
	 * @return tiempo promedio geom�trico en segundos de los viajes relacionados
	 */
	public double darGMTravelTime(){
		return gMTravelTime;
	}

	/**
	 metod que retorna la desviaci�n est�ndar geom�trica de los viajes relacionados
	 * @return desviaci�n est�ndar geom�trica de los viajes relacionados
	 */
	public double darGSDeviationTime(){
		return gSDeviationTime;
	}

	/**
	 * metodo que retorna la desviaci�n est�ndar de los viajes relacionados
	 * @return desviaci�n est�ndar de los viajes relacionados
	 */
	public double darSDeviationTime(){
		return sDeviationTime;
	}

	/**
	 * metodo que retorna el tiempo promedio en segundos de los viajes relacionados
	 * @return tiempo promedio en segundos de los viajes relacionados
	 */
	public double darMTravelTime(){
		return mTravelTime;
	}
	

	/**
	 * Clase que define la comparacion de los Viajes de Uber por el momento en el que fueron agregados
	 * ya sea por mes o dia o hora segun sea el caso
	 */
	public static class cmpTiempo implements Comparator<ViajeUber>{
		/**
		 * metodo de comparacion de los Viajes de Uber por el momento temporal en el que fueron agregados
		 * ya sea por mes o dia o hora segun sea el caso
		 * @return 0 si tiempo = tiempo de com, 1 si tiempo > tiempo de com, -1 si tiempo < tiempo de com
		 */
		public int compare(ViajeUber o1, ViajeUber o2) {
			if(o1.darTiempo()-o2.darTiempo()<0){
				return -1;
			}else if(o1.darTiempo()-o2.darTiempo()>0){
				return 1;
			}
			else return 0;
		}
	}
	
	/**
	 * Clase que define la comparacion de los Viajes de Uber por el tiempo promedio de viaje
	 */
	public static class cmpTiempoViaje implements Comparator<ViajeUber>{
		/**
		 * metodo de comparacion de los Viajes de Uber por el tiempo promedio de viaje
		 * @return 0 si mTravelTime = mTravelTime de com, 1 si mTravelTime > mTravelTime de com, -1 si mTravelTime < mTravelTime de com
		 */
		public int compare(ViajeUber o1, ViajeUber o2) {
			if(o1.darMTravelTime()-o2.darMTravelTime()<0){
				return -1;
			}else if(o1.darMTravelTime()-o2.darMTravelTime()>0){
				return 1;
			}
			else return 0;
		}
	}
	
	public static class cmpSDeviationTime implements Comparator<ViajeUber>{
		/**
		 * metodo de comparacion de los Viajes de Uber por el tiempo promedio de viaje
		 * @return 0 si mTravelTime = mTravelTime de com, 1 si mTravelTime > mTravelTime de com, -1 si mTravelTime < mTravelTime de com
		 */
		public int compare(ViajeUber o1, ViajeUber o2) {
			if(o1.darSDeviationTime()-o2.darSDeviationTime()<0){
				return -1;
			}else if(o1.darSDeviationTime()-o2.darSDeviationTime()>0){
				return 1;
			}
			else return 0;
		}
	}

	/**
	 * Clase que define la comparacion de los Viajes de Uber por la zona de destino
	 */
	public static class cmpZonaDestino implements Comparator<ViajeUber>{
		/**
		 * metodo de comparacion de los Viajes de Uber por la zona de destino
		 * @return 0 si dstid = dstid de com, 1 si dstid > dstid de com, -1 si dstid < dstid de com
		 */
		public int compare(ViajeUber o1, ViajeUber o2) {
			if(o1.darDestino()-o2.darDestino()<0){
				return -1;
			}else if(o1.darDestino()-o2.darDestino()>0){
				return 1;
			}
			else return 0;
		}
	}
	
	/**
	 * Clase que define la comparacion de los Viajes de Uber por la zona de destino
	 */
	public static class cmpZonaOrigenDestino implements Comparator<ViajeUber>{
		/**
		 * metodo de comparacion de los Viajes de Uber por la zona de destino
		 * @return 0 si dstid = dstid de com, 1 si dstid > dstid de com, -1 si dstid < dstid de com
		 */
		public int compare(ViajeUber o1, ViajeUber o2) {
			if(o1.darDestino()-o2.darDestino()<0){
				return -1;
			}else if(o1.darDestino()-o2.darDestino()>0){
				return 1;
			}else if(o1.darZonaOrigen()-o2.darZonaOrigen()<0) {
				return -1;
			}else if(o1.darZonaOrigen()-o2.darZonaOrigen()>0) {
				return 1;
			}
			else return 0;
		}
	}

	/**
	 * metodo que retorna el string que representa el viaje de uber
	 * @return string que representa el viaje de uber
	 */
	public String toString() {
		return sourceid+", "+dstid+", "+hod+", "+mTravelTime;
	}
	
	public String toStringDeviationtion() {
		return sourceid+", "+dstid+", "+hod+", "+sDeviationTime;
	}
	
	public String toStringTiempoViaje() {
		return "Tiempo promedio= "+mTravelTime+" de "+sourceid+" a"+dstid;
	}

	/**
	 * metodo de comparacion de los Viajes de Uber por la zona de origen del viaje
	 * @return 0 si sourceid = sourceid de com, 1 si sourceid > sourceid de com, -1 si sourceid < sourceid de com
	 */
	public int compareTo(ViajeUber com) {
		if(sourceid==com.darZonaOrigen()){
			return 0;
		}
		else if(sourceid<com.darZonaOrigen()){
			return -1;
		}
		else return 1;
	}
	
	public String darKey() {
		return trimestre+"-"+sourceid+"-"+dstid;
	}
	
}
