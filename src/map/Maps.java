package map;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxmaps.Circle;
import com.teamdev.jxmaps.CircleOptions;
import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

public class Maps extends MapView {

	// Objeto Google Maps
	private Map map;
	
	//Coordenadas del camino a mostrar (secuencia de localizaciones (Lat, Long))
	private LatLng[] locations = {new LatLng(4.6285797,-74.0649341), new LatLng(4.608550, -74.076443), new LatLng(4.601363, -74.0661), new LatLng(4.5954979,-74.068708) }; //Coordenadas de los vertices inicio, intermedio y fin.		

	public Maps()
	{	
		setOnMapReadyHandler( new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus status)
				{
			         if ( status == MapStatus.MAP_STATUS_OK )
			         {
			        	 map = getMap();

			        	 // marker at the start of path
			        	 Marker startPath = new Marker(map);
			        	 startPath.setPosition(locations[0]);

			        	 // Configuracion de localizaciones intermedias del path (circulos)
			        	 CircleOptions middleLocOpt= new CircleOptions(); 
			        	 middleLocOpt.setFillColor("#00FF00");  // color de relleno
			        	 middleLocOpt.setFillOpacity(0.5);
			        	 middleLocOpt.setStrokeWeight(1.0);

			        	 // Localizacion intermedia 1
			        	 Circle middleLoc1 = new Circle(map);
			        	 middleLoc1.setOptions(middleLocOpt);
			        	 middleLoc1.setCenter(locations[1]); 
			        	 middleLoc1.setRadius(20); //Radio del circulo

			        	 // marker at Uniandes
			        	 Marker markerUniandes = new Marker(map);
			        	 markerUniandes.setPosition(locations[2]);
			        	 // Info at Marker
			        	 InfoWindow infoWindow = new InfoWindow(map);
			        	 infoWindow.setContent("Uniandes");
			        	 infoWindow.open(map, markerUniandes);

			        	 // Localizacion intermedia 2 (Uniandes)
			        	 Circle middleLoc2 = new Circle(map);
			        	 middleLoc2.setOptions(middleLocOpt);
			        	 middleLoc2.setCenter(locations[2]); 
			        	 middleLoc2.setRadius(25); //Radio del circulo

			        	 // marker at the end of path
			        	 Marker endPath = new Marker(map);
			        	 endPath.setPosition(locations[3]);

			        	 //Configuracion de la linea del camino
			        	 PolylineOptions pathOpt = new PolylineOptions();
			        	 pathOpt.setStrokeColor("#0000FF");	  // color de linea	
			        	 pathOpt.setStrokeOpacity(1.75);
			        	 pathOpt.setStrokeWeight(1.5);
			        	 pathOpt.setGeodesic(false);

			        	 // Linea del camino
			        	 Polyline path = new Polyline(map); 														
			        	 path.setOptions(pathOpt); 
			        	 path.setPath(locations);

			        	 initMap( map );
			         }
				}

		} );
		
				
	}
	
	public void initMap(Map map)
	{
		MapOptions mapOptions = new MapOptions();
		MapTypeControlOptions controlOptions = new MapTypeControlOptions();
		controlOptions.setPosition(ControlPosition.BOTTOM_LEFT);
		mapOptions.setMapTypeControlOptions(controlOptions);

		map.setOptions(mapOptions);
        map.setCenter(locations[2]);
		map.setZoom(14.0);
		
	}
	
	public Marker generateMarker(LatLng pos) {
		Marker marker = null;

		marker = new Marker(map);
		marker.setPosition(pos);
		System.out.println("Graficado marker");
		return marker;
	}

	public void generateSimplePath(LatLng start, LatLng end, Boolean markers) {
		LatLng[] path = { start, end };
		Polyline polyline = new Polyline(map);
		polyline.setPath(path);
		if (markers) {
			generateMarker(start);
			generateMarker(end);
		}
	}
	
	public void initFrame(String titulo)
	{
		JFrame frame = new JFrame(titulo);
		frame.setSize(800, 800);
		frame.add(this, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public static void main(String [] args)
	{
		Maps maps = new Maps();
		maps.initFrame("Requerimiento X");
	}
}
