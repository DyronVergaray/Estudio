/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Curso;
import Modelo.Docente;
import Modelo.Estudiante;
import java.util.List;

public class ControladorDocente {
    private Docente docente;

    public ControladorDocente(Docente docente) {
        this.docente = docente;
    }

    // Registrar nota de estudiante
    public void registrarNota(Curso curso, Estudiante estudiante, int nota) {
        // Aquí puedes crear un Map<Estudiante, Integer> notas en Curso para guardar las notas.
        // ejemplo: curso.registrarNota(estudiante, nota);
    }

    // Modificar nota previamente registrada
    public void modificarNota(Curso curso, Estudiante estudiante, int nuevaNota) {
        // curso.modificarNota(estudiante, nuevaNota);
    }

    // Registrar asistencia
    public void registrarAsistencia(Curso curso, Estudiante estudiante, boolean presente) {
        // curso.registrarAsistencia(estudiante, presente);
    }

    // Consultar lista de estudiantes
    public List<Estudiante> consultarLista(Curso curso) {
        return curso.getEstudiantes();
    }

    // Consultar cursos asignados
    public List<Curso> consultarCursos() {
        return docente.getCursosAsignados();
    }
}

