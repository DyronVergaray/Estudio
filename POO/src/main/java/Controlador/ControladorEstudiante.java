/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.List;

public class ControladorEstudiante {
    private Estudiante estudiante;

    public ControladorEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    // Matrículación: curso y horario sin conflicto y de la malla curricular
    public boolean matricularCurso(Curso curso) {
        // Verifica si la materia del curso está en la malla curricular del estudiante
        boolean enMalla = false;
        for (Asignacion asignacion : estudiante.getCarrera().getMalla().getAsignaciones()) {
            if (asignacion.getMateria().getId() == curso.getMateria().getId()) {
                enMalla = true;
                break;
            }
        }
        if (!enMalla) return false;

        // Verifica conflicto de horario
        for (Curso c : estudiante.getCursosMatriculados()) {
            if (c.getAula().getFranjaHoraria().equals(curso.getAula().getFranjaHoraria()) &&
                c.getAula().getHoraInicio().equals(curso.getAula().getHoraInicio())) {
                return false; // conflicto de horario
            }
        }

        // Si pasa ambos chequeos, se matricula
        estudiante.matricularCurso(curso);
        curso.agregarEstudiante(estudiante);
        return true;
    }

    // Consultar registro de cursos, profesores, horarios
    public List<Curso> consultarRegistro() {
        return estudiante.getCursosMatriculados();
    }

    // Consultar horario de los cursos
    public String consultarHorario() {
        StringBuilder sb = new StringBuilder();
        for (Curso c : estudiante.getCursosMatriculados()) {
            sb.append(c.getNombre())
              .append(": ")
              .append(c.getAula().getFranjaHoraria())
              .append(" (")
              .append(c.getAula().getHoraInicio())
              .append(" - ")
              .append(c.getAula().getHoraFin())
              .append(")\n");
        }
        return sb.toString();
    }

    // Generar reporte de cursos, docente y notas
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        for (Curso c : estudiante.getCursosMatriculados()) {
            sb.append("Curso: ").append(c.getNombre())
              .append(", Docente: ").append(c.getDocente().getNombre())
              .append(", Notas: ...\n"); // Aquí puedes conectar una lista de notas si lo deseas
        }
        return sb.toString();
    }
}

