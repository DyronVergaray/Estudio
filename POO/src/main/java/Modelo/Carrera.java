/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Carrera {
    private int id;
    private String nombre;
    private int vacantes;
    private String descripcion;
    private MallaCurricular malla;

    public Carrera(int id, String nombre, int vacantes, String descripcion, MallaCurricular malla) {
        this.id = id;
        this.nombre = nombre;
        this.vacantes = vacantes;
        this.descripcion = descripcion;
        this.malla = malla;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public MallaCurricular getMalla() { return malla; }
}
