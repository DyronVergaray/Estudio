/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class MallaCurricular {
    private int id;
    private String nombre;
    private List<Asignacion> asignaciones;

    public MallaCurricular(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.asignaciones = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Asignacion> getAsignaciones() { return asignaciones; }
    public void agregarAsignacion(Asignacion asignacion) {
        asignaciones.add(asignacion);
    }
}
