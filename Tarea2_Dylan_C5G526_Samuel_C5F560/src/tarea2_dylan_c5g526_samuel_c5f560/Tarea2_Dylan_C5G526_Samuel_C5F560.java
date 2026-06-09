/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea2_dylan_c5g526_samuel_c5f560;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dylan Lobo
 */
public class Tarea2_Dylan_C5G526_Samuel_C5F560 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // CORRECCIÓN:
       List<Persona> personasCargadas = cargarArchivo();

        // Si la lista no está vacía, mostramos los objetos Persona creados
        if (personasCargadas != null && !personasCargadas.isEmpty()) {
            System.out.println("\n--- ¡Éxito! Objetos Persona creados ---");
            for (Persona p : personasCargadas) {
                System.out.println(p);
            }
        } 
    }
    
    public static List<Persona> cargarArchivo() {
        List<Persona> listaPersonas = new ArrayList<>();

        
        JFileChooser fileChooser = new JFileChooser();
        
        // Filtramos para que el usuario busque idealmente archivos CSV o de texto
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV o Texto", "csv", "txt");
        fileChooser.setFileFilter(filtro);

        // Abrimos la ventana de diálogo
        int seleccion = fileChooser.showOpenDialog(null);

        // 2. Si el usuario selecciona un archivo y presiona "Aceptar"
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            
            // Obtenemos el archivo real que eligió el usuario
            File archivoSeleccionado = fileChooser.getSelectedFile();
            System.out.println("Leyendo archivo desde: " + archivoSeleccionado.getAbsolutePath());

            // 3. PASAMOS EL ARCHIVO AL LECTOR (BufferReader)
            // En vez de pasar una ruta en texto "usuarios.csv", le pasamos el objeto 'archivoSeleccionado'
            try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
                String linea;
                
                // br.readLine(); // Descomenta esta línea si tu archivo tiene títulos en la primera fila

                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    
                    if (datos.length == 3) {
                        String cedula = datos[0].trim();
                        String nombre = datos[1].trim();
                        int edad = Integer.parseInt(datos[2].trim());

                        // Creamos el objeto y lo metemos a la lista
                        Persona nuevaPersona = new Persona(Integer.parseInt(cedula), nombre, edad);
                        listaPersonas.add(nuevaPersona);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Hay un dato de edad que no es un número válido.");
            }
            
            return listaPersonas;

        } else {
            System.out.println("El usuario canceló la búsqueda del archivo.");
            return null;
        }
    }
    
    
    
}
