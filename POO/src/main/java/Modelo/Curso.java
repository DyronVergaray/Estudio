// Modelo/Curso.java
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String codigo;
    private Materia materia;
    private Docente docente;
    private Aula aula;
    private String horario;
    private String franjaHoraria;
    private int vacantes;
    private List<Matricula> matriculas;

    public Curso(int id, String codigo, Materia materia, Docente docente, 
                 Aula aula, String horario, String franjaHoraria, int vacantes) {
        this.id = id;
        this.codigo = codigo;
        this.materia = materia;
        this.docente = docente;
        this.aula = aula;
        this.horario = horario;
        this.franjaHoraria = franjaHoraria;
        this.vacantes = vacantes;
        this.matriculas = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public Materia getMateria() { return materia; }
    public Docente getDocente() { return docente; }
    public Aula getAula() { return aula; }
    public String getHorario() { return horario; }
    public String getFranjaHoraria() { return franjaHoraria; }
    public int getVacantes() { return vacantes; }
    public List<Matricula> getMatriculas() { return matriculas; }
    
    public void setDocente(Docente docente) { this.docente = docente; }
    public void setAula(Aula aula) { this.aula = aula; }
    public void setHorario(String horario) { this.horario = horario; }
    public void setVacantes(int vacantes) { this.vacantes = vacantes; }
    
    public void agregarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
    }
    
    public int getMatriculados() {
        return matriculas.size();
    }
    
    public boolean tieneVacantes() {
        return getMatriculados() < vacantes;
    }
    
    @Override
    public String toString() {
        return codigo + " - " + materia.getNombre();
    }
}