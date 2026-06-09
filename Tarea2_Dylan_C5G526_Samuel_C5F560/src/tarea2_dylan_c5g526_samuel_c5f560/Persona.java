/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea2_dylan_c5g526_samuel_c5f560;

/**
 *
 * @author Dylan Lobo & Samuel Gonzales
 */
public class Persona implements Comparable<Persona> {

    private int cedula;
    private String nombre;
    private int edad;

    public Persona(int cedula, String nombre, int edad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public int compareTo(Persona otra) {
        // Retorna un valor negativo, cero o positivo según la cédula
        return Integer.compare(this.cedula, otra.cedula);
    }

    @Override
    public String toString() {
        return "Cédula: " + cedula + " | Nombre: " + nombre + " | Edad: " + edad;
    }
}
