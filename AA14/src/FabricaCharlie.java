import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class FabricaCharlie implements Produccion{

	public static void main(String[] args) throws IOException {

		URL url = new URL("https://www.el-tiempo.net/api/json/v2/provincias/28");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		Produccion produccion = new FabricaCharlie();
		
		int tiempoRespuesta = conn.getResponseCode();
		
		if(tiempoRespuesta == 200) {
			
			Scanner sn = new Scanner(url.openStream());
			StringBuilder sb = new StringBuilder();
			
			
			while(sn.hasNext()) {
				sb.append(sn.nextLine());
			}
			
			String respuesta = sb.toString();
			JSONObject json = new JSONObject(respuesta);
			JSONArray jsonArray = new JSONArray(json.get("ciudades").toString());
			JSONObject jsonCiudad = new JSONObject(jsonArray.get(0).toString());
			JSONObject jsonTemperatures = new JSONObject(jsonCiudad.get("temperatures").toString());
			int temperaturaActual = jsonTemperatures.getInt("max");
			
			produccion.produccionActiva(temperaturaActual);
			
			//Prueba temperatura superior a 40
			//produccion.produccionActiva(42);
			
			
			
		}
		

	}

	@Override
	public void produccionActiva(int temperaturaActual) {
		
		if(temperaturaActual >= 40) {
			
			System.out.println("Hoy no se pudo producir chocolate...");
			
			File archivo = new File("Produccion_Chocolate_"+LocalDate.now()+".txt");
			FileWriter fw;
			try {
				fw = new FileWriter(archivo);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write("Hoy no se pudo producir chocolate pues la temperatura fue de " +temperaturaActual);
				
				bw.close();
				fw.close();
				
				crearJenkinsSinProd();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}else {
			
			System.out.println("Hoy " +LocalDate.now()+ " se produjo chocolate!\n\n");
			
			File archivo = new File("Produccion_Chocolate_"+LocalDate.now()+".txt");
			FileWriter fw;
			try {
				fw = new FileWriter(archivo);
				BufferedWriter bw = new BufferedWriter(fw);
				List<Chocolate> chocolatesProducidos = new ArrayList<>();
				chocolatesProducidos.add(new Chocolate("CharlieFabrick", 20, "Blanco", 1000));
				chocolatesProducidos.add(new Chocolate("CharlieFabrick", 15, "Negro", 1500));
				chocolatesProducidos.add(new Chocolate("CharlieFabrick", 17.5, "Con Almendras", 1200));
				chocolatesProducidos.add(new Chocolate("CharlieFabrick", 25, "Con Castañas de Caju", 1300));
				chocolatesProducidos.add(new Chocolate("CharlieFabrick", 10, "En Rama", 100));
				chocolatesProducidos.add(new Chocolate("CharlieFabrick", 15, "Con 70% de cacao", 1500));
				
				bw.write("Temperatura máximo de hoy "+ LocalDate.now()+" : " +temperaturaActual+ "\n");
				for(Chocolate chocolate : chocolatesProducidos) {
					bw.write("Chocolate " +chocolate.getTipo() + " - Cantidad Producida: " +chocolate.getCantidadProducida()+ " - Precio: " +chocolate.getPrecio()+ "$\n");
					System.out.println("Chocolate " +chocolate.getTipo() + " - Cantidad Producida: " +chocolate.getCantidadProducida()+ " - Precio: " +chocolate.getPrecio()+ "\n");
				}
				
				bw.close();
				fw.close();
				
				crearJenkins(chocolatesProducidos);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
		public static void crearJenkins(List<Chocolate> chocolatesProducidos) throws IOException {
		
		
		FileWriter fw = new FileWriter(new File("Jenkinsfile"));
		BufferedWriter bw = new BufferedWriter(fw);
		
		StringBuilder Jenkins = new StringBuilder();
        Jenkins.append("import java.time.LocalDate\r\n");
        Jenkins.append("pipeline{\r\n");
        Jenkins.append("agent any \r\n");
        Jenkins.append("stages{ \r\n");
        Jenkins.append("stage('Main'){ \r\n");
        Jenkins.append("steps{ \r\n");
        Jenkins.append("script{ \r\n");
        Jenkins.append("println LocalDate.now() \r\n");
        for(Chocolate chocolate : chocolatesProducidos) {
        	Jenkins.append("println '"+"Chocolate " +chocolate.getTipo() + " - Cantidad Producida: " +chocolate.getCantidadProducida()+ " - Precio: " +chocolate.getPrecio()+"'   \r\n");
        }
        Jenkins.append("} \r\n");
        Jenkins.append("} \r\n");
        Jenkins.append("} \r\n");
        Jenkins.append("} \r\n");
        Jenkins.append("} \r\n");

        bw.write(Jenkins.toString());
		bw.close();
		fw.close();
	}
		
		public static void crearJenkinsSinProd() throws IOException {
			
			
			FileWriter fw = new FileWriter(new File("Jenkinsfile"));
			BufferedWriter bw = new BufferedWriter(fw);
			
			StringBuilder Jenkins = new StringBuilder();
	        Jenkins.append("import java.time.LocalDate\r\n");
	        Jenkins.append("pipeline{\r\n");
	        Jenkins.append("agent any \r\n");
	        Jenkins.append("stages{ \r\n");
	        Jenkins.append("stage('Main'){ \r\n");
	        Jenkins.append("steps{ \r\n");
	        Jenkins.append("script{ \r\n");
	        Jenkins.append("println LocalDate.now() \r\n");
	        Jenkins.append("println 'Hoy no se pudo producir chocolate debido a las altas temperaturas' \r\n");
	        Jenkins.append("} \r\n");
	        Jenkins.append("} \r\n");
	        Jenkins.append("} \r\n");
	        Jenkins.append("} \r\n");
	        Jenkins.append("} \r\n");

	        bw.write(Jenkins.toString());
			bw.close();
			fw.close();
		} 

}
