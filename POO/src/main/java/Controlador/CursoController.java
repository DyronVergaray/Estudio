package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoController {
    private List<Curso> cursos;
    private int siguienteIdCurso;

    public CursoController() {
        this.cursos = new ArrayList<>();
        this.siguienteIdCurso = 1;
    }

    // ========== GESTIÓN DE CURSOS ==========
    public boolean crearCurso(String codigo, Materia materia, Docente docente, 
                              Aula aula, String horario, String franjaHoraria, int vacantes) {
        // Verificar que el código no exista
        if (buscarCursoPorCodigo(codigo) != null) return false;
        
        Curso curso = new Curso(siguienteIdCurso++, codigo, materia, docente, 
            aula, horario, franjaHoraria, vacantes);
        docente.agregarCurso(curso);
        return cursos.add(curso);
    }

    public List<Curso> listarCursos() {
        return new ArrayList<>(cursos);
    }

    public List<Curso> listarCursosPorDocente(Docente docente) {
        return cursos.stream()
            .filter(c -> c.getDocente().getId() == docente.getId())
            .collect(Collectors.toList());
    }

    public List<Curso> listarCursosPorMateria(Materia materia) {
        return cursos.stream()
            .filter(c -> c.getMateria().getId() == materia.getId())
            .collect(Collectors.toList());
    }

    public List<Curso> listarCursosPorAula(Aula aula) {
        return cursos.stream()
            .filter(c -> c.getAula().getId() == aula.getId())
            .collect(Collectors.toList());
    }

    public List<Curso> listarCursosPorFranja(String franjaHoraria) {
        return cursos.stream()
            .filter(c -> c.getFranjaHoraria().equalsIgnoreCase(franjaHoraria))
            .collect(Collectors.toList());
    }

    public List<Curso> listarCursosConVacantes() {
        return cursos.stream()
            .filter(Curso::tieneVacantes)
            .collect(Collectors.toList());
    }

    public List<Curso> listarCursosDisponibles(Estudiante estudiante) {
        Carrera carrera = estudiante.getCarrera();
        if (carrera == null || carrera.getMallaCurricular() == null) {
            return new ArrayList<>();
        }
        
        List<Materia> materiasCarrera = carrera.getMallaCurricular().getMaterias();
        List<Integer> cursosMatriculadosIds = estudiante.getMatriculas().stream()
            .map(m -> m.getCurso().getId())
            .collect(Collectors.toList());
        
        return cursos.stream()
            .filter(c -> materiasCarrera.contains(c.getMateria()))
            .filter(c -> !cursosMatriculadosIds.contains(c.getId()))
            .filter(Curso::tieneVacantes)
            .collect(Collectors.toList());
    }

    public Curso buscarCursoPorId(int id) {
        return cursos.stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public Curso buscarCursoPorCodigo(String codigo) {
        return cursos.stream()
            .filter(c -> c.getCodigo().equals(codigo))
            .findFirst()
            .orElse(null);
    }

    public boolean actualizarCurso(Curso curso) {
        Curso existente = buscarCursoPorId(curso.getId());
        if (existente == null) return false;
        
        // Actualizar docente (remover del anterior, agregar al nuevo)
        if (existente.getDocente().getId() != curso.getDocente().getId()) {
            existente.getDocente().removerCurso(existente);
            curso.getDocente().agregarCurso(existente);
        }
        
        existente.setDocente(curso.getDocente());
        existente.setAula(curso.getAula());
        existente.setHorario(curso.getHorario());
        existente.setVacantes(curso.getVacantes());
        return true;
    }

    public boolean cambiarDocente(int idCurso, Docente nuevoDocente) {
        Curso curso = buscarCursoPorId(idCurso);
        if (curso == null) return false;
        
        Docente docenteAnterior = curso.getDocente();
        docenteAnterior.removerCurso(curso);
        
        curso.setDocente(nuevoDocente);
        nuevoDocente.agregarCurso(curso);
        
        return true;
    }

    public boolean cambiarAula(int idCurso, Aula nuevaAula) {
        Curso curso = buscarCursoPorId(idCurso);
        if (curso == null) return false;
        
        // Verificar que la capacidad del aula sea suficiente
        if (nuevaAula.getCapacidad() < curso.getMatriculados()) {
            return false;
        }
        
        curso.setAula(nuevaAula);
        return true;
    }

    public boolean cambiarHorario(int idCurso, String nuevoHorario, String nuevaFranja) {
        Curso curso = buscarCursoPorId(idCurso);
        if (curso == null) return false;
        
        curso.setHorario(nuevoHorario);
        // Nota: setFranjaHoraria no existe en el modelo original, 
        // necesitarías agregarlo si quieres cambiar la franja
        return true;
    }

    public boolean eliminarCurso(int id) {
        Curso curso = buscarCursoPorId(id);
        if (curso != null) {
            // Remover curso del docente
            curso.getDocente().removerCurso(curso);
            
            // Si tiene estudiantes matriculados, no permitir eliminar
            if (curso.getMatriculados() > 0) {
                return false;
            }
        }
        return cursos.removeIf(c -> c.getId() == id);
    }

    // ========== ESTADÍSTICAS ==========
    public int contarCursosTotales() {
        return cursos.size();
    }

    public int contarCursosConVacantes() {
        return (int) cursos.stream()
            .filter(Curso::tieneVacantes)
            .count();
    }

    public int contarCursosLlenos() {
        return (int) cursos.stream()
            .filter(c -> !c.tieneVacantes())
            .count();
    }

    public double calcularPromedioMatriculados() {
        if (cursos.isEmpty()) return 0.0;
        
        int totalMatriculados = cursos.stream()
            .mapToInt(Curso::getMatriculados)
            .sum();
        
        return (double) totalMatriculados / cursos.size();
    }

    // ========== GETTERS INTERNOS ==========
    List<Curso> getCursos() {
        return cursos;
    }

    void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    void setSiguienteIdCurso(int id) {
        this.siguienteIdCurso = id;
    }
}