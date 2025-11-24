/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario {
    private Carrera carrera;
    private List<Curso> cursosMatriculados = new ArrayList<>();

    public Estudiante(int id, String nombre, String correo, String contrasena, Carrera carrera) {
        super(id, nombre, correo, contrasena);
        this.carrera = carrera;
    }

    public Carrera getCarrera() { return carrera; }
    public List<Curso> getCursosMatriculados() { return cursosMatriculados; }
    public void matricularCurso(Curso curso) { cursosMatriculados.add(curso); }
}
