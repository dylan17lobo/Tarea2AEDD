package tarea2_dylan_c5g526_samuel_c5f560;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dylan Lobo & Samuel Gonzales
 */
public class Tarea2_Dylan_C5G526_Samuel_C5F560 {

    /**
     * @param args the command line arguments
     */
    private static ArrayList<Persona> listaOriginal = new ArrayList<>();
    private static Persona[] vectorOrdenado = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("      MENU ORDENAMIENTO         ");
            System.out.println("1. Cargar archivo");
            System.out.println("2. Ordenar mediante Burbuja Mejorado");
            System.out.println("3. Ordenar mediante Insercion Directa");
            System.out.println("4. Ordenar mediante Seleccion");
            System.out.println("5. Ordenar mediante Quicksort");
            System.out.println("6. Ordenar mediante Mergesort");
            System.out.println("7. Ordenar mediante Shellsort");
            System.out.println("8. Buscar en el vector ordenado (Binaria)");
            System.out.println("9. Imprimir vector ordenado");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opcion: ");

            while (!sc.hasNextInt()) {
                System.out.print("Por favor, ingrese un número valido: ");
                sc.next();
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    cargarDatos();
                    break;
                case 2:
                    ejecutarOrdenamiento("Burbuja Mejorado");
                    break;
                case 3:
                    ejecutarOrdenamiento("Inserción Directa");
                    break;
                case 4:
                    ejecutarOrdenamiento("Seleccion");
                    break;
                case 5:
                    ejecutarOrdenamiento("Quicksort");
                    break;
                case 6:
                    ejecutarOrdenamiento("Mergesort");
                    break;
                case 7:
                    ejecutarOrdenamiento("Shellsort");
                    break;
                case 8:
                    buscarPersona(sc);
                    break;
                case 9:
                    imprimirVector();
                    break;
                case 10:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opcion inválida. Intente de nuevo.");
            }
        } while (opcion != 10);
        sc.close();
    }

    // --- MÉTODO DE CARGA DE ARCHIVOS ---
    private static void cargarDatos() {
        JFileChooser selector = new JFileChooser();
        int resultado = selector.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            listaOriginal.clear(); // Reemplaza por completo si ya había datos
            vectorOrdenado = null; // Resetea cualquier ordenamiento previo

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        int cedula = Integer.parseInt(partes[0].trim());
                        String nombre = partes[1].trim();
                        int edad = Integer.parseInt(partes[2].trim());
                        listaOriginal.add(new Persona(cedula, nombre, edad));
                    }
                }
                System.out.println("¡Exito! Se cargaron " + listaOriginal.size() + " personas.");
            } catch (Exception e) {
                System.out.println("Error al procesar el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Carga cancelada por el usuario.");
        }
    }

    // --- CONTROLADOR DE TIEMPOS Y COPÌAS ---
    private static void ejecutarOrdenamiento(String algoritmo) {
        if (listaOriginal.isEmpty()) {
            System.out.println("Error: Debe cargar un archivo de datos primero.");
            return;
        }

        // Creamos una copia fresca del vector original para cumplir la restricción
        Persona[] copia = listaOriginal.toArray(new Persona[0]);

        // Medimos estrictamente el tiempo del algoritmo
        long inicio = System.nanoTime();

        switch (algoritmo) {
            case "Burbuja Mejorado":
                burbujaMejorado(copia);
                break;
            case "Inserción Directa":
                insercionDirecta(copia);
                break;
            case "Selección":
                seleccion(copia);
                break;
            case "Quicksort":
                quicksort(copia, 0, copia.length - 1);
                break;
            case "Mergesort":
                mergesort(copia, 0, copia.length - 1);
                break;
            case "Shellsort":
                shellsort(copia);
                break;
        }

        long fin = System.nanoTime();

        // Guardamos el vector resultante para búsquedas o impresión
        vectorOrdenado = copia;
        System.out.println("Algoritmo [" + algoritmo + "] ejecutado en: " + (fin - inicio) + " nanosegundos.");
    }

    // --- ALGORITMOS DE ORDENAMIENTO ---
    private static void burbujaMejorado(Persona[] arr) {
        int n = arr.length;
        boolean intercambiado;
        for (int i = 0; i < n - 1; i++) {
            intercambiado = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    Persona temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambiado = true;
                }
            }
            if (!intercambiado) {
                break; // Optimización si ya está ordenado
            }
        }
    }

    private static void insercionDirecta(Persona[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            Persona clave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareTo(clave) > 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = clave;
        }
    }

    private static void seleccion(Persona[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[min_idx]) < 0) {
                    min_idx = j;
                }
            }
            Persona temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    private static void quicksort(Persona[] arr, int low, int high) {
    while (low < high) {
        // Obtenemos el índice de la partición
        int pi = partition(arr, low, high);

        // Evaluamos cuál mitad es más pequeña para procesarla recursivamente primero
        if (pi - low < high - pi) {
            quicksort(arr, low, pi - 1);
            low = pi + 1; // El bucle se encarga de la mitad derecha (ahorramos una llamada a la pila)
        } else {
            quicksort(arr, pi + 1, high);
            high = pi - 1; // El bucle se encarga de la mitad izquierda
        }
    }
}

    private static void buscarPersona(Scanner sc) {
        if (vectorOrdenado == null || vectorOrdenado.length == 0) {
            System.out.println("Error: Debe ordenar los datos primero antes de buscar.");
            return;
        }

        System.out.print("Ingrese la cédula a buscar: ");
        while (!sc.hasNextInt()) {
            System.out.print("Por favor, ingrese un número valido: ");
            sc.next();
        }
        int cedula = sc.nextInt();

        Persona clave = new Persona(cedula, "", 0);
        int low = 0;
        int high = vectorOrdenado.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = vectorOrdenado[mid].compareTo(clave);
            if (cmp == 0) {
                System.out.println("Persona encontrada: " + vectorOrdenado[mid]);
                return;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Persona no encontrada.");
    }

    // Imprime el vector ordenado en pantalla
    private static void imprimirVector() {
        if (vectorOrdenado == null || vectorOrdenado.length == 0) {
            System.out.println("No hay datos ordenados para mostrar.");
            return;
        }
        System.out.println("Vector ordenado:");
        for (Persona p : vectorOrdenado) {
            System.out.println(p);
        }
    }

    // Implementación de partition para Quicksort (Lomuto)
    private static int partition(Persona[] arr, int low, int high) {
        Persona pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                Persona temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Persona temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // Mergesort y su método merge
    private static void mergesort(Persona[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergesort(arr, left, mid);
            mergesort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Persona[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Persona[] L = new Persona[n1];
        Persona[] R = new Persona[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // Shellsort clásico
    private static void shellsort(Persona[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Persona temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap].compareTo(temp) > 0) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }
}
