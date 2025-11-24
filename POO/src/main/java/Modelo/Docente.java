/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Docente extends Usuario {
    private List<Curso> cursosAsignados = new ArrayList<>();

    public Docente(int id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
    }

    public List<Curso> getCursosAsignados() { return cursosAsignados; }
    public void agregarCurso(Curso curso) { cursosAsignados.add(curso); }
}
