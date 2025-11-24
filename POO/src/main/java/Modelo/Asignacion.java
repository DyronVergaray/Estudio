/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Asignacion {
    private int id; // ID único de la asignación
    private MallaCurricular malla;
    private Materia materia;

    public Asignacion(int id, MallaCurricular malla, Materia materia) {
        this.id = id;
        this.malla = malla;
        this.materia = materia;
    }

    public MallaCurricular getMalla() { return malla; }
    public Materia getMateria() { return materia; }
}
