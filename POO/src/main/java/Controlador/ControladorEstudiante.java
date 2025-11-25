/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Asignacion;
import Modelo.Curso;
import Modelo.Estudiante;
import Modelo.Materia;
import java.util.List;

public class ControladorEstudiante {
    private Estudiante estudiante;

    public ControladorEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    // Matricularse a un curso: revisa conflictos de horario y malla
    public boolean matricularse(Curso curso) {
        if (!esMateriaDeMalla(curso.getMateria())) {
            System.out.println("No corresponde a la malla curricular");
            return false;
        }
        if (hayConflictoHorario(curso)) {
            System.out.println("¡Conflicto de horario!");
            return false;
        }
        estudiante.matricularCurso(curso);
        curso.agregarEstudiante(estudiante);
        return true;
    }

    // Verificación
    private boolean esMateriaDeMalla(Materia materia) {
        for (Asignacion asignacion : estudiante.getCarrera().getMalla().getAsignaciones()) {
            if (asignacion.getMateria().getId() == materia.getId()) return true;
        }
        return false;
    }

    // Revisa solapamiento de horario
    private boolean hayConflictoHorario(Curso nuevoCurso) {
        for (Curso existente : estudiante.getCursosMatriculados()) {
            if (existente.getAula().getFranjaHoraria().equals(nuevoCurso.getAula().getFranjaHoraria())) {
                return true;
            }
        }
        return false;
    }

    // Consultar matrícula
    public List<Curso> consultarRegistro() {
        return estudiante.getCursosMatriculados();
    }

    // Consultar horario
    public String consultarHorario() {
        StringBuilder sb = new StringBuilder();
        for (Curso curso : estudiante.getCursosMatriculados()) {
            sb.append(curso.getNombre())
              .append(" - ")
              .append(curso.getAula().getFranjaHoraria())
              .append("\n");
        }
        return sb.toString();
    }

    // Reporte de notas y docente
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        for (Curso curso : estudiante.getCursosMatriculados()) {
            sb.append("Curso: ").append(curso.getNombre())
              .append(" | Docente: ").append(curso.getDocente().getNombre())
              .append(" | Materia: ").append(curso.getMateria().getNombre())
              .append("\n");
        }
        return sb.toString();
    }
}