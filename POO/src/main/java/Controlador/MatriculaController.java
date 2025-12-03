package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatriculaController {
    private List<Matricula> matriculas;
    private int siguienteIdMatricula;

    public MatriculaController() {
        this.matriculas = new ArrayList<>();
        this.siguienteIdMatricula = 1;
    }

    // ========== GESTIÓN DE MATRÍCULAS ==========
    public boolean matricularEstudiante(Estudiante estudiante, Curso curso) {
        // Verificar que el curso tenga vacantes
        if (!curso.tieneVacantes()) {
            return false;
        }
        
        // Verificar que no esté ya matriculado
        boolean yaMatriculado = estudiante.getMatriculas().stream()
            .anyMatch(m -> m.getCurso().getId() == curso.getId());
        if (yaMatriculado) {
            return false;
        }
        
        // Crear matrícula
        Matricula matricula = new Matricula(siguienteIdMatricula++, estudiante, curso);
        estudiante.agregarMatricula(matricula);
        curso.agregarMatricula(matricula);
        matriculas.add(matricula);
        
        return true;
    }

    public boolean retirarMatricula(int idMatricula) {
        Matricula matricula = buscarMatriculaPorId(idMatricula);
        if (matricula == null) return false;
        
        // Remover de las listas relacionadas
        matricula.getEstudiante().getMatriculas().remove(matricula);
        matricula.getCurso().getMatriculas().remove(matricula);
        
        return matriculas.removeIf(m -> m.getId() == idMatricula);
    }

    public List<Matricula> listarMatriculasPorCurso(Curso curso) {
        return matriculas.stream()
            .filter(m -> m.getCurso().getId() == curso.getId())
            .collect(Collectors.toList());
    }

    public List<Matricula> listarMatriculasPorEstudiante(Estudiante estudiante) {
        return matriculas.stream()
            .filter(m -> m.getEstudiante().getId() == estudiante.getId())
            .collect(Collectors.toList());
    }

    public Matricula buscarMatriculaPorId(int id) {
        return matriculas.stream()
            .filter(m -> m.getId() == id)
            .findFirst()
            .orElse(null);
    }

    // ========== GESTIÓN DE NOTAS ==========
    public boolean registrarNotas(int idMatricula, double pc1, double pc2, 
                                  double pc3, double pcf, int asistencia) {
        Matricula matricula = buscarMatriculaPorId(idMatricula);
        if (matricula == null) return false;
        
        matricula.setPc1(pc1);
        matricula.setPc2(pc2);
        matricula.setPc3(pc3);
        matricula.setPcf(pcf);
        matricula.setAsistencia(asistencia);
        
        return true;
    }

    public boolean actualizarEstado(int idMatricula, String estado) {
        Matricula matricula = buscarMatriculaPorId(idMatricula);
        if (matricula == null) return false;
        
        matricula.setEstado(estado);
        return true;
    }

    // ========== REPORTES Y ESTADÍSTICAS ==========
    public double calcularPromedioGeneral(Estudiante estudiante) {
        List<Matricula> matriculasEstudiante = listarMatriculasPorEstudiante(estudiante);
        if (matriculasEstudiante.isEmpty()) return 0.0;
        
        double suma = matriculasEstudiante.stream()
            .mapToDouble(Matricula::calcularPromedio)
            .sum();
        
        return suma / matriculasEstudiante.size();
    }

    public List<Matricula> listarMatriculasAprobadas(Estudiante estudiante) {
        return listarMatriculasPorEstudiante(estudiante).stream()
            .filter(m -> m.calcularPromedio() >= 10.5)
            .collect(Collectors.toList());
    }

    public List<Matricula> listarMatriculasDesaprobadas(Estudiante estudiante) {
        return listarMatriculasPorEstudiante(estudiante).stream()
            .filter(m -> m.calcularPromedio() > 0 && m.calcularPromedio() < 10.5)
            .collect(Collectors.toList());
    }

    // ========== GETTERS INTERNOS ==========
    List<Matricula> getMatriculas() {
        return matriculas;
    }

    void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    void setSiguienteIdMatricula(int id) {
        this.siguienteIdMatricula = id;
    }
}