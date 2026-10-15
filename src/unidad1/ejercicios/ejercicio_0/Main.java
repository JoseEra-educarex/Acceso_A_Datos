package unidad1.ejercicios.ejercicio_0;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

	private static final		Scanner entrada= new Scanner(System.in);
	
	public static void main(String[] args) {

	/*Ejercicio 0: Realiza un programa (usando Path/Files) que implemente las siguientes acciones:

			1. Mostrar la ruta absoluta de la carpeta actual.
			
			2. Pedir por teclado una ruta de fichero o carpeta y mostrar si lo introducido existe, si es un fichero o una carpeta, 
				la fecha de modificación y el tamaño.
				
			3. Mostrar el contenido de una carpeta cuya ruta se pide por teclado, comprobando que existe y que es una carpeta.
			4. Crear una carpeta cuyo nombre se pide por teclado en la ruta por defecto, comprobando antes que no existe.
			5. Crear un fichero cuyo nombre se pide por teclado, comprobando antes que no existe.
			6. Pedir por teclado una ruta de fichero y un nuevo nombre, y renombrar el fichero original comprobando que existe y que el nuevo nombre no está ya en uso.
*/

		
		int opcion;

		do {
            System.out.println("\n=== MENÚ DE GESTIÓN DE ARCHIVOS (NIO.2) ===");
            System.out.println("1. Mostrar ruta absoluta de la carpeta actual");
            System.out.println("2. Analizar ruta (existe, tipo, fecha, tamaño)");
            System.out.println("3. Listar contenido de una carpeta");
            System.out.println("4. Crear una carpeta");
            System.out.println("5. Crear un fichero");
            System.out.println("6. Renombrar un fichero");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            // Validamos que se introduzca un número entero
            while (!entrada.hasNextInt()) {
                System.out.print("Error. Introduce un número válido: ");
                entrada.next();
            }
            opcion = entrada.nextInt();
            entrada.nextLine(); // Limpiar buffer del scanner

            switch (opcion) {
                case 1 -> mostrarRutaActual();
                case 2 -> analizarRuta();
                case 3 -> listarCarpeta();
                case 4 -> crearCarpeta();
                case 5 -> crearFichero();
                case 6 -> renombrarFichero();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

		entrada.close();
    }

	
	
	
	
	
	
	
	
	// 1. MOSTRAR RUTA ABSOLUTA DE LA CARPETA ACTUAL
	
    private static void mostrarRutaActual() {
    	
        // "." representa el directorio de trabajo actual
    	
        Path rutaActual = Paths.get(".");
        
        try {
            // toRealPath resuelve la ruta absoluta real (elimina . y ..)
        	
            System.out.println("Ruta absoluta: " + rutaActual.toRealPath());
            		
            		
            	
            
        } catch (IOException e) {
        	
            System.out.println("Error al obtener la ruta real: " + e.getMessage());
        }
    }
	
	
    
 // 2. ANALIZAR RUTA (EXISTE, TIPO, FECHA, TAMAÑO)
    
    private static void analizarRuta() {
    	
        System.out.print("Introduce la ruta del fichero o carpeta: ");
        
        String rutaStr = entrada.nextLine();
        
        Path ruta = Paths.get(rutaStr);

        try {
        	
            if (Files.notExists(ruta)) {
            	
                System.out.println(">> La ruta NO existe.");
                
                return;
            }

            System.out.println(">> La ruta SÍ existe.");
            System.out.println("¿Es fichero?: " + Files.isRegularFile(ruta));
            System.out.println("¿Es carpeta?: " + Files.isDirectory(ruta));

            // Obtenemos atributos básicos para fecha y tamaño
            
            // 1. Leer los atributos en una sola operación en bloque
            BasicFileAttributes atributos = Files.readAttributes(ruta, BasicFileAttributes.class);
            System.out.println("Atributos: ");
           // try {
               
             
                // 2. Imprimir cada atributo de forma explícita
                System.out.println("--- Atributos del Archivo ---");
                System.out.println("Fecha de creación:      " + atributos.creationTime());
                System.out.println("Última modificación:   " + atributos.lastModifiedTime());
                System.out.println("Último acceso:         " + atributos.lastAccessTime());
                System.out.println("¿Es un directorio?:    " + atributos.isDirectory());
                System.out.println("¿Es un archivo normal?: " + atributos.isRegularFile());
                System.out.println("¿Es un enlace simbólico?:" + atributos.isSymbolicLink());
                System.out.println("Tamaño (en bytes):     " + atributos.size());
                
            //} catch (Exception e) {
                //System.err.println("Error al leer los atributos: " + e.getMessage());
           // }

            // Fecha de modificación formateada
            
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault());
            
         // Obtener el instante desde el FileTime
            
            String fechaMod = formatter.format(atributos.lastModifiedTime().toInstant());
            
            System.out.println("Fecha de modificación: " + fechaMod);

            // Tamaño (si es carpeta, el tamaño suele ser 0 o el metadata, no el contenido)
            
            System.out.println("Tamaño (bytes): " + atributos.size());

        } catch (IOException e) {
        	
            System.out.println("Error de E/S: " + e.getMessage());
            
        }
    }

    
 // 3. LISTAR CONTENIDO DE UNA CARPETA
    private static void listarCarpeta() {
        System.out.print("Introduce la ruta de la carpeta a listar: ");
        Path rutaCarpeta = Paths.get(entrada.nextLine());

        try {
            // Validaciones previas
            if (Files.notExists(rutaCarpeta)) {
            	
                System.out.println("Error: La ruta no existe.");
                
                return;
            }
            if (!Files.isDirectory(rutaCarpeta)) {
            	
                System.out.println("Error: La ruta no es una carpeta.");
                
                return;
            }

            System.out.println("\nContenido de la carpeta:");
            
            // Files.list devuelve un Stream<Path> que debemos cerrar (try-with-resources)
            /*
            La expresión Lambda (path) -> { ... }
            Esto es lo que hay dentro del paréntesis: path -> { ... }. Se llama expresión lambda.

            path: Es la variable que representa al "archivo actual" que está pasando por la tubería en este instante.
                  Es de tipo Path.

            ->: Significa "ejecuta lo que hay a la derecha con este dato".

            { ... }: Es el bloque de código que se ejecuta para cada archivo.
             */
            try (Stream<Path> stream = Files.list(rutaCarpeta)) {
            	
                stream.forEach(path -> {
                	
                    String tipo = Files.isDirectory(path) ? "[CARPETA]" : "[FICHERO]";
                    
                    System.out.println(tipo + " -> " + path.getFileName());
                    
                });
            }

        } catch (IOException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    }
    
	
    
 // 4. CREAR UNA CARPETA
    private static void crearCarpeta() {
        System.out.print("Nombre de la nueva carpeta: ");
        String nombre = entrada.nextLine();
        Path nuevaCarpeta = Paths.get(nombre);

        try {
            if (Files.exists(nuevaCarpeta)) {
                System.out.println("Error: Ya existe un archivo o carpeta con ese nombre.");
            } else {
                // createDirectory lanza excepción si el directorio padre no existe
                // createDirectories crea también los padres si faltan (más robusto)
                Files.createDirectory(nuevaCarpeta);
                System.out.println("Carpeta creada correctamente en: " + nuevaCarpeta.toAbsolutePath());
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("Error: El archivo ya existe.");
        } catch (IOException e) {
            System.out.println("Error al crear carpeta: " + e.getMessage());
        }
    }

    
    
 // 5. CREAR UN FICHERO
    private static void crearFichero() {
        System.out.print("Nombre del nuevo fichero: ");
        String nombre = entrada.nextLine();
        Path nuevoFichero = Paths.get(nombre);

        try {
            if (Files.exists(nuevoFichero)) {
                System.out.println("Error: El fichero ya existe.");
            } else {
                Files.createFile(nuevoFichero);
                System.out.println("Fichero creado correctamente.");
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("Error: El fichero ya existe.");
        } catch (IOException e) {
            System.out.println("Error al crear fichero: " + e.getMessage());
        }
    }



 // 6. RENOMBRAR UN FICHERO
    private static void renombrarFichero() {
        System.out.print("Ruta del fichero original: ");
        Path origen = Paths.get(entrada.nextLine());

        System.out.print("Nuevo nombre (o ruta completa): ");
        Path destino = Paths.get(entrada.nextLine());

        try {
            // Comprobaciones previas
            if (Files.notExists(origen)) {
                System.out.println("Error: El fichero original no existe.");
                return;
            }
            if (Files.exists(destino)) {
                System.out.println("Error: Ya existe un archivo con el nuevo nombre.");
                return;
            }

            // Renombrar (Move)
            // StandardCopyOption.ATOMIC_MOVE asegura que la operación es atómica (todo o nada)
            Files.move(origen, destino, StandardCopyOption.ATOMIC_MOVE);
            System.out.println("Fichero renombrado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al renombrar: " + e.getMessage());
        }
    }

	

	
	

	
	}

	
	
	
	

