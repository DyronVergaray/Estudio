/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.List;

public class ControladorDocente {
    private Docente docente;

    public ControladorDocente(Docente docente) {
        this.docente = docente;
    }

    // Registrar notas de estudiantes
    public void registrarNota(Curso curso, Estudiante estudiante, double nota) {
        // Supón que el curso tiene un mapa de estudiante a calificaciones
        // curso.registrarNota(estudiante, nota);
    }

    // Modificar notas
    public void modificarNota(Curso curso, Estudiante estudiante, double nuevaNota) {
        // curso.modificarNota(estudiante, nuevaNota);
    }

    // Registrar asistencia
    public void registrarAsistencia(Curso curso, Estudiante estudiante, boolean presente) {
        // curso.registrarAsistencia(estudiante, presente);
    }

    // Consultar lista de estudiantes por curso
    public List<Estudiante> consultarLista(Curso curso) {
        return curso.getEstudiantes();
    }

    // Consultar cursos asignados
    public List<Curso> consultarCursos() {
        return docente.getCursosAsignados();
    }
}

