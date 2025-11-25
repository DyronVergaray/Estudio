package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario {
    private Carrera carrera;
    private int ciclo;
    private List<Matricula> matriculas;

    public Estudiante(int id, String nombre, String correo, String contrasena, 
                      String telefono, Carrera carrera, int ciclo) {
        super(id, nombre, correo, contrasena, telefono);
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.matriculas = new ArrayList<>();
    }

    public Carrera getCarrera() { return carrera; }
    public int getCiclo() { return ciclo; }
    public List<Matricula> getMatriculas() { return matriculas; }
    
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }
    public void setCiclo(int ciclo) { this.ciclo = ciclo; }
    
    public void agregarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
    }
}