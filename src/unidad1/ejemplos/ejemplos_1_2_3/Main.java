package unidad1.ejemplos.ejemplos_1_2_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException{
	
		Scanner entrada= new Scanner(System.in);	
		
		
		int opcion= 0;

		do {
		
		System.out.println("Seleccion una opcion: ");
		opcion=entrada.nextInt();
		
		switch (opcion) {
		case 1:
			System.out.println("1");
			
				crearDirectorioCarpetaArchivo();
			
			break;
			
		case 2:
			System.out.println("2");
			
				sobrescribeCopiaRenombraYBorra();
			
			break;
			
		case 3:
			System.out.println("3");
			
				crearCatalogoListarContenido();

			
			break;
			
		case 4:
			System.out.println("4");
			
			System.out.println("Sin ejemplo");
			
			
			break;
			

		default:
			System.out.println("Error");
			break;
		}
		
			
		}while(opcion>=1&&opcion<=4);
		
		
		
		
    
		
		entrada.close();
		
		
		
	}



	private static void crearCatalogoListarContenido() throws IOException {
		Path raiz = Path.of("catalogo");

	        // Listar solo el contenido directo (no recursivo)
	        try (Stream<Path> listado = Files.list(raiz)) {
	            listado.forEach(System.out::println);
	        }

	        // Recorrer TODO el árbol de subdirectorios (recursivo)
	        try (Stream<Path> arbol = Files.walk(raiz)) {
	            arbol.filter(Files::isRegularFile)
	                 .forEach(p -> System.out.println("Fichero encontrado: " + p));
	        }
	}



	private static void sobrescribeCopiaRenombraYBorra() throws IOException {
		Path origen  = Path.of("catalogo/config.txt");
        Path copia   = Path.of("catalogo/config_copia.txt");
        Path destino = Path.of("catalogo/backup/config.txt");

        // Copiar (sobrescribiendo si ya existe)
        Files.copy(origen, copia, StandardCopyOption.REPLACE_EXISTING);

        // Mover / renombrar
        Files.createDirectories(destino.getParent());
        Files.move(copia, destino, StandardCopyOption.REPLACE_EXISTING);

        // Borrar de forma segura
        boolean borrado = Files.deleteIfExists(Path.of("catalogo/fichero_temporal.tmp"));
        System.out.println("¿Se borró el fichero temporal? " + borrado);
	}

	
	
	//Metodo para crear el directorio catalogo con la carpeta imagenes y el archivo txt
	
	private static void crearDirectorioCarpetaArchivo() {
		Path carpetaCatalogo = Path.of("catalogo");
        Path subcarpetaImagenes = carpetaCatalogo.resolve("imagenes");

        try {
            // Crea el directorio "catalogo" si no existe, y también los padres necesarios
            Files.createDirectories(subcarpetaImagenes);
            System.out.println("Directorios creados correctamente.");

            Path ficheroConfig = carpetaCatalogo.resolve("config.txt");
            if (!Files.exists(ficheroConfig)) {
                Files.createFile(ficheroConfig);
            }
        } catch (IOException e) {
            System.err.println("Error al crear la estructura de directorios: " + e.getMessage());
        }
        
        
        
        
        
        
        
        
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
