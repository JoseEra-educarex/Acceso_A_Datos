package unidad1.ejemplos.ejemplo_5;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EscritorTexto {
	
	static Scanner  entrada = new Scanner (System.in);
	
    public static void main(String[] args) {
    	
 
    	
    	String producto;
    	
        List<String> ListaProductos = new ArrayList<>();
        
        
        
	for(int i=0; i>=ListaProductos.size();i++) {
    		
		System.out.println("Introduce producto para la lista: ");
		
		producto= entrada.nextLine();
		
		ListaProductos.add(producto);
		
		if(producto.equalsIgnoreCase("salir")) {
			
			ListaProductos.removeLast();

			
			System.out.println("LISTA DE LA COMPRA");
			System.out.println("____________");
			System.out.println(ListaProductos);
			
			break;
			}
		
		
    	}

        try (BufferedWriter bw = new BufferedWriter(
        		
                new FileWriter("catalogo/productos.txt", StandardCharsets.UTF_8))) {

            for (String produc : ListaProductos) {
            	
                bw.write(produc);
                bw.newLine(); // salto de línea multiplataforma
            }
        } catch (IOException e) {
        	
            System.err.println("Error de escritura: " + e.getMessage());
        }
        entrada.close();
    }
}