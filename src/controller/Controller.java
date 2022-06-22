package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Marker;

import map.Maps;
import model.data_structures.Cola;
import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.VerticeInfo;
import model.logic.MVCModelo;
import model.logic.res5A;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;

	/* Instancia de la Vista*/
	private MVCView view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 * @throws IOException 
	 */
	public Controller () throws IOException
	{
		view = new MVCView();


	}
	
	
	public void run() throws Exception 
	{
		Scanner lector = new Scanner(System.in);
		int lec = 0;
		double lat = 0;
		double lon = 0;
		double lat2 = 0;
		double lon2= 0;
		int temp = 0;
		int id1 = 0;
		int id2 = 0;
		boolean fin = false;
	
		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				System.out.println("Cargando informacion");
				try {
					modelo=new MVCModelo(0);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				System.out.println("Cantidad de arcos: "+modelo.darNumArcos());
				System.out.println("Cantidad de vertices: "+modelo.darNumvertices());
//				Cola<VerticeInfo> res=modelo.verticesMayores();
//				System.out.println("Vertices con mayor numero de componentes conectadas");
//				while(res.isEmpty()) {
//					System.out.println(res.avanzar());
//				}
				
				
				System.out.println("Creando JSON");
				//modelo.crearJson();
				System.out.println("JSON creado");
				break;
			case 4:
				Maps map = new Maps();
				System.out.println("--------- \n Ingrese latitud\n---------");
				lat = lector.nextDouble();
				System.out.println("--------- \n Ingrese longitud\n---------");			
				lon = lector.nextDouble();
				System.out.println("--------- \n Ingrese latitud\n---------");
				lat2 = lector.nextDouble();
				System.out.println("--------- \n Ingrese longitud\n---------");
				lon2 = lector.nextDouble();
				 double cont = 0;
				 
				Iterable<Edge> res4 = modelo.caminoMasCorto(lat, lon, lat2, lon2);
				if(res4 == null){
					System.out.println("hubo error");
				}else{
				Iterator<Edge> itr = res4.iterator();
				while(itr.hasNext()){
					Edge actual= itr.next();
					VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
					VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
					System.out.println(info1.id()+"-"+info2.id());
					System.out.println(info1.lon()+","+info1.lat()+"-"+info2.lon()+","+info2.lat());
					System.out.println(actual.weight(2));
					cont = cont+ actual.weight(1);
					LatLng latlng1 = new LatLng(info1.lat(), info1.lon());
					LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
					map.generateSimplePath(latlng1,latlng2,true);
				}	
				System.out.println("distancia haversian de camino :"+ cont);
				
				map.initFrame("requerimiento4");
				}
				break;
			case 5:
				Maps map1 = new Maps(); 
				System.out.println("--------- \n Ingrese el numero de vertices que desea buscar\n---------");
				lec = lector.nextInt();
				Cola<res5A> res5 = modelo.nVerticesMenorTiempo(lec);
				if(res5== null){
					System.out.println("hubo error");
				}else{
				System.out.println("numero de cc"+res5.darTamano());
				while(!res5.isEmpty()){
					res5A actual = res5.avanzar().darElemento();
					VerticeInfo verti1 = modelo.darGrafo().getInfoVertex(Integer.toString(actual.id()));
					System.out.println(verti1.id());
					System.out.println(verti1.lat());
					System.out.println(verti1.lon());
					LatLng latlng1 = new LatLng(verti1.lat(), verti1.lon());
					Marker mrker = map1.generateMarker(latlng1);
					mrker.setIcon("http://maps.google.com/mapfiles/ms/icons/green-dot.png");
					
					int[] ids = actual.ids();
					for(int i=0;i<ids.length;i++){
						VerticeInfo verti = modelo.darGrafo().getInfoVertex(Integer.toString(ids[i]));
						System.out.println(verti.id());
						System.out.println(verti.lon());
						System.out.println(verti.lat());
						Iterable<Edge> arcos= modelo.darGrafo().adj(verti.id());
						Iterator<Edge> itr2 = arcos.iterator();
						
						while(itr2.hasNext()){
							Edge actual2 = itr2.next();
							VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual2.either()));
							VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual2.other2()));
							LatLng latlng = new LatLng(info1.lat(), info1.lon());
							LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
							map1.generateSimplePath(latlng1,latlng2,true);
							
						}
					}
					
					
				}
				map1.initFrame("requerimiento 5");
				}
				
				break;
			case 6:	
				Maps map2 = new Maps();
				System.out.println("--------- \n Calculando arbol de costo mínimo\n---------");
				long startTime = System.nanoTime();
				Iterable<Edge> res6 = modelo.mtsCDistancia();				
				long endTime   = System.nanoTime();
				long totalTime = endTime - startTime;
				System.out.println("tiemo de ejecución:"+totalTime);
			    int contador=0;
			    double dist = 0;
				Iterator<Edge> itr3 = res6.iterator();
				while(itr3.hasNext()){
					Edge actual = itr3.next();
					contador++;
					dist = dist+actual.weight(1);
					VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
					VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
					System.out.println(info1.id()+"-"+info2.id());
					LatLng latlng = new LatLng(info1.lat(), info1.lon());
					LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
					map2.generateSimplePath(latlng,latlng2,true);
					
				}
				System.out.println("total vertices:"+(contador-1));
				System.out.println("distancia en Km :"+dist);
				map2.initFrame("requerimiento6");
					
				
				
				break;
			case 7:
				Maps map4 = new Maps();
				System.out.println("--------- \n Ingrese latitud\n---------");
				lat = lector.nextDouble();
				System.out.println("--------- \n Ingrese longitud\n---------");			
				lon = lector.nextDouble();
				System.out.println("--------- \n Ingrese latitud\n---------");
				lat2 = lector.nextDouble();
				System.out.println("--------- \n Ingrese longitud\n---------");
				lon2 = lector.nextDouble();
				double cont2 = 0;
				double tiempo = 0;
				Iterable<Edge> res7 = modelo.caminoMasCortoHaversine(lat, lon, lat2, lon2);
				if(res7== null){
					System.out.println("hubo error");
				}else{
					
					Iterator<Edge> itr = res7.iterator();
					while(itr.hasNext()){
						Edge actual= itr.next();
						VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
						VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
						System.out.println(info1.id()+"-"+info2.id());
						System.out.println(info1.lon()+","+info1.lat()+"-"+info2.lon()+","+info2.lat());
						System.out.println(actual.weight(2));
						cont2 = cont2+ actual.weight(1);
						tiempo = tiempo +actual.weight(2);
						LatLng latlng1 = new LatLng(info1.lat(), info1.lon());
						LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
						map4.generateSimplePath(latlng1,latlng2,true);
					}	
					System.out.println("distancia haversian de camino :"+ cont2);
					System.out.println("tiempo total :"+ tiempo);
					
					map4.initFrame("requerimiento7");
					}	
				
				break;
			case 8:
				Maps mapa5 = new Maps();
				System.out.println("--------- \n Ingrese latitud\n---------");
				lat = lector.nextDouble();
				System.out.println("--------- \n Ingrese longitud\n---------");			
				lon = lector.nextDouble();
				System.out.println("--------- \n Ingrese EL tiempo\n---------");			
				temp = lector.nextInt();
				
				Cola<VerticeInfo> res8 = modelo.verticesAlcanzablesTiempo(lat, lon, temp);
				if(res8== null){
					System.out.println("hubo error");
				}else{
					
					VerticeInfo partida =  res8.avanzar().darElemento();
					LatLng latlng4 = new LatLng(partida.lat(), partida.lon());
					Marker mrker4 = mapa5.generateMarker(latlng4);
					mrker4.setIcon("http://maps.google.com/mapfiles/ms/icons/green-dot.png");

					while(!res8.isEmpty()){
						VerticeInfo actual =  res8.avanzar().darElemento();
						System.out.println(actual.id());
						System.out.println(actual.lat());
						System.out.println(actual.lon());
						LatLng latlng1 = new LatLng(actual.lat(), actual.lon());
						Marker mrker = mapa5.generateMarker(latlng1);
					}
					mapa5.initFrame("requerimiento8");
				}
				break;
			case 9:
				Maps map6 = new Maps();
				System.out.println("--------- \n Calculando arbol de costo mínimo\n---------");
				long startTime1 = System.nanoTime();
				Iterable<Edge> res9 = modelo.mtsCDistancia();				
				long endTime1  = System.nanoTime();
				long totalTime1 = endTime1 - startTime1;
				System.out.println("tiemo de ejecución:"+totalTime1);
			    int contador3=0;
			    double dist3 = 0;
				Iterator<Edge> itr5 = res9.iterator();
				while(itr5.hasNext()){
					Edge actual = itr5.next();
					contador3++;
					dist3 = dist3+actual.weight(1);
					VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
					VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
					System.out.println(info1.id()+"-"+info2.id());
					LatLng latlng = new LatLng(info1.lat(), info1.lon());
					LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
					map6.generateSimplePath(latlng,latlng2,true);
					
				}
				System.out.println("total vertices:"+(contador3-1));
				System.out.println("distancia en Km :"+dist3);
				map6.initFrame("requerimiento9");
					
				
				break;
			case 10:
				
				Maps map7 = new Maps();
				System.out.println("--------- \n Creando nuevo grafo\n---------");
				EdgeWeightedGraph<String, VerticeInfo> nuevoGrafo = modelo.grafoSimple();
				if(nuevoGrafo == null){
					System.out.println("hubo error");
				}else{
					
					System.out.println("cant vertices" +nuevoGrafo.V());					
					System.out.println("cant arcos" +nuevoGrafo.E());
					
					Iterable<Edge> arcos1 = nuevoGrafo.edges();
					Iterator<Edge> itr6 =arcos1.iterator();
					while(itr6.hasNext()){
						Edge actual = itr6.next();
						VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
						VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
						LatLng latlng = new LatLng(info1.lat(), info1.lon());
						LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
						map7.generateSimplePath(latlng,latlng2,true);
					}
					
					map7.initFrame("requerimiento10");
				
					
				}

				
				break;
			case 11:
				Maps map8 = new Maps();
				System.out.println("--------- \n Ingrese el origen\n---------");
				id1 = lector.nextInt();
				System.out.println("--------- \n Ingrese el destino\n---------");
				id2 = lector.nextInt();
				EdgeWeightedGraph<String, VerticeInfo> nuevoGrafo2 = modelo.grafoSimple();
				double temp5 = 0;
				long startTime2 = System.nanoTime();
				Iterable<Edge> res11 = modelo.caminoGrafosimple(nuevoGrafo2, id1, id2);		
				long endTime2  = System.nanoTime();
				long totalTime2 = endTime2 - startTime2;
				Iterator<Edge> itr11 = res11.iterator();
				
				while(itr11.hasNext()){
					Edge actual = itr11.next();
					temp5 = temp5 +actual.weight(2);
					VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
					VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
					System.out.println(info1.id()+"-"+info2.id());
					LatLng latlng = new LatLng(info1.lat(), info1.lon());
					LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
					map8.generateSimplePath(latlng,latlng2,true);
					
				}
				System.out.println("tiempo total :"+temp5);
				map8.initFrame("requerimient11");
				
				
				
				break;
			case 12:
				Maps map9 = new Maps();
				System.out.println("--------- \n Ingrese el origen\n---------");
				id1 = lector.nextInt();
				System.out.println("--------- \n Ingrese el destino\n---------");
				id2 = lector.nextInt();
				EdgeWeightedGraph<String, VerticeInfo> nuevoGrafo3 = modelo.grafoSimple();
				double temp4 = 0;
				int con = 0;
				long startTime3 = System.nanoTime();
				Iterable<Edge> res12 = modelo.caminoGrafosimple(nuevoGrafo3, id1, id2);		
				long endTime3  = System.nanoTime();
				long totalTime3 = endTime3 - startTime3;
				Iterator<Edge> itr12 = res12.iterator();
				
				while(itr12.hasNext()){
					Edge actual = itr12.next();
					con++;
					temp4 = temp4 +actual.weight(2);
					VerticeInfo info1 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.either()));
					VerticeInfo info2 = (VerticeInfo) modelo.grafo().getInfoVertex(Integer.toString(actual.other2()));
					System.out.println(info1.id()+"-"+info2.id());
					LatLng latlng = new LatLng(info1.lat(), info1.lon());
					LatLng latlng2 = new LatLng(info2.lat(), info2.lon());
					map9.generateSimplePath(latlng,latlng2,true);
					
				}				
				System.out.println("cant vert :"+(con-1));
				map9.initFrame("requerimient11");
				
				
				break;
			case 13: 
				System.out.println("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
