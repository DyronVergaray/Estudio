/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nombre;
    private Materia materia;
    private Docente docente;
    private Aula aula;
    private List<Estudiante> estudiantes = new ArrayList<>();

    public Curso(int id, String nombre, Materia materia, Docente docente, Aula aula) {
        this.id = id;
        this.nombre = nombre;
        this.materia = materia;
        this.docente = docente;
        this.aula = aula;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Materia getMateria() { return materia; }
    public Docente getDocente() { return docente; }
    public Aula getAula() { return aula; }
    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public void agregarEstudiante(Estudiante e) { estudiantes.add(e); }
}

