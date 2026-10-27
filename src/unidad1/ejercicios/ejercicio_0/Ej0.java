package unidad1.ejercicios.ejercicio_0;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Ej0 {
	

	    public static void main(String[] args) {
	        Scanner teclado = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("\n==================================================");
	            System.out.println("   MENÚ DE GESTIÓN DE FICHEROS Y DIRECTORIOS");
	            System.out.println("==================================================");
	            System.out.println("1. Mostrar la ruta absoluta de la carpeta actual");
	            System.out.println("2. Consultar existencia, tipo, fecha y tamaño");
	            System.out.println("3. Listar el contenido de una carpeta");
	            System.out.println("4. Crear una nueva carpeta");
	            System.out.println("5. Crear un nuevo fichero");
	            System.out.println("6. Renombrar un fichero");
	            System.out.println("0. Salir");
	            System.out.print("Elige una opción: ");

	            // Lectura de la opción del menú
	            String entrada = teclado.nextLine();

	            try {
	                opcion = Integer.parseInt(entrada);
	            } catch (NumberFormatException e) {
	                System.out.println("Error: Debes introducir un número válido.");
	                opcion = -1; // Le damos un valor inválido para que el switch caiga en el 'default' o repita
	            }
	            // Switch que unifica la llamada exclusiva a los métodos externos
	            switch (opcion) {
	                case 1:
	                    mostrarRutaActual();
	                    break;
	                case 2:
	                    consultarInformacionRuta(teclado);
	                    break;
	                case 3:
	                    listarContenidoCarpeta(teclado);
	                    break;
	                case 4:
	                    crearCarpeta(teclado);
	                    break;
	                case 5:
	                    crearFichero(teclado);
	                    break;
	                case 6:
	                    renombrarFichero(teclado);
	                    break;
	                case 0:
	                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
	                    break;
	                default:
	                    System.out.println("Opción no válida. Inténtalo de nuevo.");
	            }

	        } while (opcion != 0);

	        teclado.close();
	    }

	    // =========================================================================
	    // MÉTODOS AUXILIARES FUERA DEL MAIN
	    // =========================================================================

	    // Apartado 1
	    public static void mostrarRutaActual() {
	        System.out.println("--- 1. Ruta Absoluta del Directorio Actual ---");
	        Path rutaActual = Path.of("").toAbsolutePath();
	        System.out.println("Ruta actual: " + rutaActual);
	    }

	    // Apartado 2
	    public static void consultarInformacionRuta(Scanner teclado) {
	        System.out.println("--- 2. Información de Fichero o Carpeta ---");
	        System.out.print("Introduce la ruta de un fichero o carpeta a consultar: ");
	        String rutaTexto = teclado.nextLine();
	        Path path = Path.of(rutaTexto);

	        if (Files.exists(path)) {
	            System.out.println("-> El elemento existe en el disco.");
	            System.out.println("-> ¿Es un fichero regular? : " + Files.isRegularFile(path));
	            System.out.println("-> ¿Es un directorio?      : " + Files.isDirectory(path));
	            try {
	                System.out.println("-> Fecha de modificación   : " + Files.getLastModifiedTime(path));
	                if (Files.isRegularFile(path)) {
	                    System.out.println("-> Tamaño del fichero      : " + Files.size(path) + " bytes");
	                }
	            } catch (IOException e) {
	                System.err.println("Error al leer los metadatos: " + e.getMessage());
	            }
	        } else {
	            System.out.println("-> El elemento introducido NO existe.");
	        }
	    }

	    // Apartado 3
	    public static void listarContenidoCarpeta(Scanner teclado) {
	        System.out.println("--- 3. Listado de Contenido de una Carpeta ---");
	        System.out.print("Introduce la ruta de la carpeta que deseas listar: ");
	        String rutaTexto = teclado.nextLine();
	        Path path = Path.of(rutaTexto);

	        if (Files.exists(path) && Files.isDirectory(path)) {
	            System.out.println("Contenido directo de la carpeta:");
	            try (Stream<Path> contenido = Files.list(path)) {
	                contenido.forEach(p -> System.out.println("   - " + p.getFileName()));
	            } catch (IOException e) {
	                System.err.println("Error al listar el directorio: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Error: La ruta no existe o no corresponde a una carpeta válida.");
	        }
	    }

	    // Apartado 4
	    public static void crearCarpeta(Scanner teclado) {
	        System.out.println("--- 4. Creación de una Nueva Carpeta ---");
	        System.out.print("Introduce el nombre de la nueva carpeta: ");
	        String nombreCarpeta = teclado.nextLine();
	        Path nuevaCarpeta = Path.of(nombreCarpeta);

	        if (Files.notExists(nuevaCarpeta)) {
	            try {
	                Files.createDirectory(nuevaCarpeta);
	                System.out.println("¡Carpeta creada con éxito en: " + nuevaCarpeta.toAbsolutePath() + "!");
	            } catch (IOException e) {
	                System.err.println("Error al crear la carpeta: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Aviso: La carpeta ya existe previamente. No se ha creado.");
	        }
	    }

	    // Apartado 5
	    public static void crearFichero(Scanner teclado) {
	        System.out.println("--- 5. Creación de un Nuevo Fichero ---");
	        System.out.print("Introduce el nombre del fichero a crear (ej: documento.txt): ");
	        String nombreFichero = teclado.nextLine();
	        Path nuevoFichero = Path.of(nombreFichero);

	        if (Files.notExists(nuevoFichero)) {
	            try {
	                Files.createFile(nuevoFichero);
	                System.out.println("¡Fichero creado con éxito en: " + nuevoFichero.toAbsolutePath() + "!");
	            } catch (IOException e) {
	                System.err.println("Error al crear el fichero: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Aviso: El fichero ya existe previamente. No se ha creado.");
	        }
	    }

	    // Apartado 6
	    public static void renombrarFichero(Scanner teclado) {
	        System.out.println("--- 6. Renombrar un Fichero ---");
	        System.out.print("Introduce la ruta del fichero que deseas renombrar: ");
	        String rutaOrigenStr = teclado.nextLine();
	        Path origen = Path.of(rutaOrigenStr);

	        if (Files.exists(origen) && Files.isRegularFile(origen)) {
	            System.out.print("Introduce el nuevo nombre para el fichero: ");
	            String nuevoNombreStr = teclado.nextLine();
	            
	            Path destino = origen.resolveSibling(nuevoNombreStr);

	            if (Files.notExists(destino)) {
	                try {
	                    Files.move(origen, destino);
	                    System.out.println("¡Fichero renombrado correctamente a: " + destino.getFileName() + "!");
	                } catch (IOException e) {
	                    System.err.println("Error al realizar el cambio de nombre: " + e.getMessage());
	                }
	            } else {
	                System.out.println("Error: El nuevo nombre de fichero ya está en uso en el directorio.");
	            }
	        } else {
	            System.out.println("Error: El fichero de origen especificado no existe o no es válido.");
	        }
	    }
	}