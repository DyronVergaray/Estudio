package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Docente extends Usuario {
    private String especialidad;
    private List<Curso> cursosAsignados;

    public Docente(int id, String nombre, String correo, String contrasena, 
                   String telefono, String especialidad) {
        super(id, nombre, correo, contrasena, telefono);
        this.especialidad = especialidad;
        this.cursosAsignados = new ArrayList<>();
    }

    public String getEspecialidad() { return especialidad; }
    public List<Curso> getCursosAsignados() { return cursosAsignados; }
    
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public void agregarCurso(Curso curso) {
        this.cursosAsignados.add(curso);
    }
    
    public void removerCurso(Curso curso) {
        this.cursosAsignados.remove(curso);
    }
}