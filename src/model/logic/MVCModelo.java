package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import Infozonas.Adyacente;
import Infozonas.ChargeManager;
import Infozonas.cargaGrafo;
import haversine.Haversine;
import model.data_structures.Bag;
import model.data_structures.CC;
import model.data_structures.Cola;
import model.data_structures.DepthFirstSerch;
import model.data_structures.DijkstraUndirectedSP;
import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.HashTable;
import model.data_structures.KruskalMST;
import model.data_structures.MinPQ;
import model.data_structures.NodoHash;
import model.data_structures.Ordenador;
import model.data_structures.Prim;
import model.data_structures.SeparateChaining;
import model.data_structures.VerticeInfo;
import model.data_structures.VerticeInfo.cmpCom;
import model.data_structures.ViajeUber;

public class MVCModelo {


	private EdgeWeightedGraph<String, VerticeInfo> verti;

	private SeparateChaining<String, ViajeUber> viajes;

	private int zonaMayor=0;

	private int numArcos=0;
	
	private int ccBigg=0;

	public MVCModelo(int i) throws Exception {
		if(i==0) {
			cargarViajesUber();
			cargarGrafo();
		}
		if(i==1) {
			cargarJson("data/nodos.json");
		}
	}

	private int ccBigg() {
		if(ccBigg==0) {
			CC<String, VerticeInfo> cc=new CC<String, VerticeInfo>(verti);
			ccBigg=cc.ccBig();
		}
		return ccBigg;
		
	}
	private void cargarViajesUber() throws Exception {
		viajes=new SeparateChaining<String, ViajeUber>();
		BufferedReader reader;
		String lin;

		ViajeUber dat=null;
		String [] linea;
		reader = new BufferedReader(new FileReader("data/bogota-cadastral-2018-1-WeeklyAggregate.csv"));
		reader.readLine();				
		while ((lin = reader.readLine()) != null ) {
			linea=lin.split(",");
			dat=new ViajeUber(Integer.parseInt(linea[0]),Integer.parseInt(linea[1]), Integer.parseInt(linea[2]),Double.parseDouble(linea[3]) , Double.parseDouble(linea[4]), Double.parseDouble(linea[5]), Double.parseDouble(linea[6]),1);
			String key=dat.darKey();
			NodoHash<ViajeUber, String> dato=new NodoHash<ViajeUber, String>(dat, key);					
			viajes.put(dato, key);
			if(Integer.parseInt(linea[0])>zonaMayor||Integer.parseInt(linea[1])>zonaMayor) {
				if(Integer.parseInt(linea[0])>zonaMayor) {
					zonaMayor=Integer.parseInt(linea[0]);
				}
				else {
					zonaMayor=Integer.parseInt(linea[1]);
				}
			}
		}
		reader.close();
	}



	private void cargarGrafo() throws IOException {

		verti = new EdgeWeightedGraph<String, VerticeInfo>(228046);
		String[] linea;
		String[] linea2;
		BufferedReader reader = new BufferedReader(new FileReader("data/Bogota_vertices.txt"));
		BufferedReader reader2 = new BufferedReader(new FileReader("data/Bogota_arcos.txt"));
		String lin;
		String lin2;
		reader.readLine();
		VerticeInfo ver = null;
		while ((lin = reader.readLine()) != null) {
			linea = lin.split(";");
			ver = new VerticeInfo(Integer.parseInt(linea[0]), Double.parseDouble(linea[2]), Double.parseDouble(linea[1]),
					Integer.parseInt(linea[3]), null);
			verti.setInfoVertex(ver.darKey(), ver);
		}
		reader.close();
		reader2.readLine();
		while ((lin2 = reader2.readLine()) != null) {
			linea2 = lin2.split(" ");
			VerticeInfo act = verti.getInfoVertex(linea2[0]);
			if(act!=null) {
				for (int i = 1; i < linea2.length; i++) {
					if(Integer.parseInt(linea2[i])>Integer.parseInt(linea2[0])) {
						VerticeInfo actual = verti.getInfoVertex(linea2[i]);
						if(actual!=null) {
							double cost = Haversine.distance(act.lat(), act.lon(), actual.lat(), actual.lon());
							int sourceid=verti.getInfoVertex(linea2[0]).movementId();
							int dsti=verti.getInfoVertex(linea2[i]).movementId();
							double costo2=tiempoDeViaje(sourceid, dsti);
							verti.addEdge(linea2[0], linea2[i],cost, costo2,cost/costo2);
							numArcos++;
						}
					}			
				}
			}

		}
		reader2.close();
	}

	public double tiempoDeViaje(int sourceid,int dsti) {
		if(sourceid==dsti) {
			double acum=0;
			int total=0;
			for(int i=0;i<=zonaMayor;i++) {
				Cola<ViajeUber> via=viajes.getMore("1-"+i+"-"+i);
				if(via.isEmpty()) {
					acum+=10;
					total++;
				}else {
					while(!via.isEmpty()) {
						acum+=via.avanzar().darElemento().darGMTravelTime();
						total++;
					}
				}
			}
			return acum/total;
		}
		else {
			Cola<ViajeUber> via=viajes.getMore("1-"+sourceid+"-"+dsti);
			double acum=0;
			int total=via.darTamano();
			while(!via.isEmpty()) {
				acum+=via.avanzar().darElemento().darGMTravelTime();
			}
			return acum/total;
		}

	}

	public EdgeWeightedGraph<String, VerticeInfo> darGrafo(){
		return verti;
	}

	@SuppressWarnings("unchecked")
	public void crearJson() throws Exception {
		JSONObject obj1 = new JSONObject();
		JSONArray list1 = new JSONArray();
		for (int i = 0; i <= verti.V(); i++) {
			VerticeInfo vertice = (VerticeInfo) verti.getInfoVertex(Integer.toString(i));
			JSONObject obj = new JSONObject();
			if (vertice != null) {
				obj.put("id", vertice.id());
				obj.put("latitud", vertice.lat());
				obj.put("longitud", vertice.lon());
				obj.put("movementId", vertice.movementId());

			} else {
				obj.put("id", "");
				obj.put("latitud", 0);
				obj.put("longitud", 0);
				obj.put("movementId", 0);
			}

			JSONArray list = new JSONArray();
			Iterable<Edge> iterable = verti.adj(i);
			if (iterable != null) {
				Iterator<Edge> itr = iterable.iterator();
				while (itr.hasNext()) {
					JSONObject obj2 = new JSONObject();
					Edge arco = itr.next();
					int adj = arco.other(i);
					double distancia = arco.weight(1);
					double tiempo = arco.weight(2);
					double velocidad = arco.weight(3);
					obj2.put("id", adj);
					obj2.put("distancia", distancia);
					obj2.put("tiempo", tiempo);
					obj2.put("velocidad", velocidad);
					list.add(obj2);
				}
			}
			obj.put("adyacentes", list);
			list1.add(obj);
		}
		obj1.put("vertices",list1);
		try {
			FileWriter file = new FileWriter("data/nodos.json");
			file.write(obj1.toString());
			file.flush();

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarJson(String rutaArchivo) throws Exception {
		verti = new EdgeWeightedGraph<String, VerticeInfo>(228046);
		cargaGrafo actual = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
			actual = new Gson().fromJson(reader, cargaGrafo.class);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "error");
		}

		for (int i = 0; i < actual.vertices().length; i++) {

			int id = actual.vertices()[i].id();
			double latitud = actual.vertices()[i].lat();
			double longitud = actual.vertices()[i].lon();
			int movementId = actual.vertices()[i].movementId();
			Adyacente[] adyacentes = actual.vertices()[i].adj();

			int[] adj = new int[adyacentes.length];
			for (int j = 0; i < adyacentes.length; j++) {
				adj[j] = adyacentes[j].id();
				verti.addEdge(Integer.toString(i), Integer.toString(j), adyacentes[j].distancia(),
						adyacentes[j].tiempo(), adyacentes[j].velocidad());
			}

			VerticeInfo vi = new VerticeInfo(id, latitud, longitud, movementId, adj);
			verti.setInfoVertex(vi.darKey(), vi);

		}
	}

	public Cola<VerticeInfo> verticesMayores(){
		Iterator<String> it=verti.llaves();
		String llave="";
		Cola<VerticeInfo> manipulable=new Cola<VerticeInfo>();
		while(it.hasNext()) {
			llave=it.next();
			int componentes=verti.componetesConectadasVertice(llave);
			VerticeInfo actual=verti.getInfoVertex(llave);
			VerticeInfo v= new VerticeInfo(actual.id(), actual.lat(), actual.lon(), actual.movementId(), actual.adj(),componentes);
			verti.setInfoVertex(llave, v);
			manipulable.agregar(v);
		}

		Ordenador<VerticeInfo> or=new Ordenador<VerticeInfo>();
		VerticeInfo[] a=manipulable.toArray();
		VerticeInfo[] aux=new VerticeInfo[a.length];
		cmpCom cmpC=new cmpCom();
		or.mergeSortMayor(a, 0, a.length-1, cmpC, aux);
		manipulable=new Cola<VerticeInfo>();
		for (int i=0;i<5;i++) {
			manipulable.agregar(a[i]);
		}
		return manipulable;
	}

	public int darNumArcos() {
		return numArcos;
	}

	public int darNumvertices() {
		return verti.V();
	}

	public Cola<VerticeInfo> nodosDelimitados(double lonM, double lonMa, double latM, double latMa) {
		Cola<VerticeInfo> res = new Cola<VerticeInfo>();

		for (int i = 0; i < verti.V(); i++) {
			VerticeInfo actual = verti.getInfoVertex(Integer.toString(i));
			if (actual != null) {
				if (actual.lon() <= lonMa && actual.lon() >= lonM) {
					if (actual.lat() <= latM && actual.lat() >= latM) {
						res.agregar(actual);
					}
				}
			}

		}

		return res;
	}

	public Cola<Edge> arcosDelimitados(Cola<VerticeInfo> cola,double lonM,double lonMa,double latM,double latMa){
		Cola<VerticeInfo> copia=cola;
		Cola<Edge> res=new Cola<Edge>();
		for(int i=0;i<cola.darTamano();i++) {
			int vertice=verti.hashT(copia.avanzar().darElemento().darKey());
			Iterator<Edge> it=verti.adj(vertice).iterator();
			while(it.hasNext()) {
				Edge actual=it.next();
				VerticeInfo actualV=verti.getInfoVertex(Integer.toString(actual.other(vertice)));
				if(actualV!=null) {
					if(actualV.lon()<=lonMa&&actualV.lon()>=lonM) {
						if(actualV.lat()<=latM&&actualV.lat()>=latM) {
							res.agregar(actual);
						}
					}	
				}
			}
		}
		return res;
	}

	public EdgeWeightedGraph<String, VerticeInfo> grafo() {
		return this.verti;
	}


	// requerimiento 8B
	public Cola<VerticeInfo> verticesAlcanzablesTiempo(double latitud,double longitud,int tiempo) throws Exception{
		Cola<VerticeInfo> res = new Cola<VerticeInfo>();
		double distMIN = Integer.MAX_VALUE;
		VerticeInfo vertAprox = null; 
		String[] vertices = verti.hash().getKeys();
		for(int i = 0 ;i<vertices.length;i++){
			VerticeInfo actual = verti.getInfoVertex(vertices[i]);			
			double dist = Haversine.distance(latitud, longitud, actual.lat(), actual.lon());
			if(dist<distMIN){
				distMIN= dist;
				vertAprox = actual;
			}			
		}

		Bag<Edge> adyacentes = verti.adjacentes()[vertAprox.id()];
		for(Edge e : adyacentes){
			if(e.weight(2)<=tiempo){
				String id = Integer.toString(e.other(vertAprox.id()));
				res.agregar(verti.getInfoVertex(id));
			}
		}

		return res;						
	}


	//requerimiento 4A
	public Iterable<Edge> caminoMasCorto(double lat1,double long1,double lat2,double long2) throws Exception{
		double distMIN = Integer.MAX_VALUE;
		VerticeInfo vertAprox = null;
		VerticeInfo vertAprox2 = null;
		String[] vertices = verti.hash().getKeys();
		for(int i = 0 ;i<vertices.length;i++){
			VerticeInfo actual = verti.getInfoVertex(vertices[i]);			
			double dist = Haversine.distance(lat1, long1, actual.lat(), actual.lon());
			double dist2 = Haversine.distance(lat2, long2, actual.lat(), actual.lon());
			if(dist<distMIN){
				distMIN= dist;
				vertAprox = actual;
			}
			if(dist2<distMIN) {
				distMIN= dist2;
				vertAprox2 = actual;
			}
		}
		DijkstraUndirectedSP djk=new DijkstraUndirectedSP(verti, vertAprox.id(), 2);
		if(djk.hasPathTo(vertAprox2.id())) {
			return djk.pathTo(vertAprox2.id());
		}
		return null;

	}

	//requerimiento 7B
	public Iterable<Edge> caminoMasCortoHaversine(double lat1,double long1,double lat2, double long2 ) throws Exception{
		int idVertice1 = -23;
		int idVertice2 = -24;
		double distMin1 =Integer.MAX_VALUE;
		double distMin2 = Integer.MAX_VALUE;
		String[] vertices = verti.hash().getKeys();
		for(int i = 0; i< vertices.length;i++){
			VerticeInfo actual = verti.getInfoVertex(vertices[i]);	
			double dist = Haversine.distance(lat1, long1, actual.lat(), actual.lon());
			if(dist<distMin1){
				distMin1 = dist;
				idVertice1 = actual.id();
			}

		}
		for(int j= 0; j< vertices.length;j++){
			VerticeInfo actual = verti.getInfoVertex(vertices[j]);	
			double dist = Haversine.distance(lat2, long2, actual.lat(), actual.lon());
			if(dist<distMin2){
				distMin2 = dist;
				idVertice2 = actual.id();
			}

		}
		DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(verti, idVertice1, 1);
		Iterable<Edge> camino = dijkstra.pathTo(idVertice2);
		return camino;
	}

	//5A
	public Cola<res5A> nVerticesMenorTiempo(int n) throws Exception{

		Cola<res5A> temp=new Cola<res5A>();
		for(int i=0;i<verti.V();i++) {
			double acum=0;
			int cont=0;
			for(Edge e: (Iterable<Edge>) verti.adj(i)) {
				acum+=e.weight(3);
				cont++;
			}
			DepthFirstSerch<String, VerticeInfo> df=new DepthFirstSerch<String, VerticeInfo>(verti, i);
			int[] ad=new int[df.count()];
			int k=0;
			for(int j=0;j<df.marcados().length;j++) {
				if(df.marcados()[j]) {
					ad[k]=j;
					k++;
				}
			}
			acum=acum/cont;
			res5A r=new res5A(acum, i,ad,df.count());
			temp.agregar(r);
		}

		Ordenador<res5A> or=new Ordenador<res5A>();
		res5A[] a=temp.toArray();
		res5A[] aux=new res5A[a.length];
		or.mergeSortMenor(a, 0, a.length-1, null, aux);
		temp=new Cola<res5A>();
		for(int i=0;i<n;i++) {
			temp.agregar(a[i]);
		}
		return temp;
	}

	public Iterable<Edge> mtsCDistancia() throws Exception{
		int ini=ccBigg();
		Prim pr=new Prim(verti, null, 1,ini);	
		return pr.edges();
	}
	
	public EdgeWeightedGraph<String, VerticeInfo> grafoSimple(){
		EdgeWeightedGraph<String, VerticeInfo> res=new EdgeWeightedGraph<String, VerticeInfo>(1160);
		for(int i=0;i<verti.V();i++) {
			Iterator<Edge> it=verti.adj(i).iterator();
			while(it.hasNext()) {
				Edge ec=it.next();
				VerticeInfo ac=verti.getInfoVertex(Integer.toString(i));
				VerticeInfo ac2=verti.getInfoVertex(Integer.toString(ec.other(i)));
				if(ac.movementId()!=ac2.movementId()) {
					double weight=tiempoDeViaje(ac.movementId(), ac2.movementId());
					if(weight==0) {
						weight=200;
					}
					Edge e=new Edge(ac.movementId(),ac2.movementId() , weight, 0, 1);
					if(!res.adj(ac.movementId()).iterator().hasNext()) {
						VerticeInfo nuevo=new VerticeInfo(ac.movementId(), ac.lat(), ac.lon(), ac.movementId(), null);
						res.setInfoVertex(Integer.toString(ac.movementId()), nuevo);
					}
					res.addEdge(e);
				}
			}			
		}
		return res;
	}

	
	public Iterable<Edge> caminoGrafosimple(EdgeWeightedGraph<String, VerticeInfo> g, int sourceid,int dst) throws Exception{
		DijkstraUndirectedSP djk=new DijkstraUndirectedSP(g, sourceid, 1);
		if(djk.hasPathTo(dst)) {
			return djk.pathTo(dst);
		}
		return null;
	}
	
	public Iterable<Edge> camnioMasLargo(EdgeWeightedGraph<String, VerticeInfo> g, int sourceid) throws Exception{
		DijkstraUndirectedSP djk=new DijkstraUndirectedSP(g, sourceid, 3);
		double mayor=0;
		
		Iterable<Edge> respuesta=null;
		for(int i=0;i<g.V();i++) {
			if(i!=sourceid) {
				if(djk.hasPathTo(i)) {
					double acum=0;
					Iterator<Edge> it=djk.pathTo(i).iterator();
					while(it.hasNext()) {
						acum+=it.next().weight(3);
					}
					if(acum>mayor) {
						respuesta=djk.pathTo(i);
						mayor=acum;
					}
				}
			}
		}
		return respuesta;
	}
	
	public Iterable<Edge> mtsKrusCDistancia() throws Exception{
		int ini=ccBigg();
		KruskalMST ks=new KruskalMST(verti, 1, ini);
		return ks.edges();
	}
}

