package Controlador;

import Modelo.*;
import java.util.List;

public class ReporteController {
    private MatriculaController matriculaController;

    public ReporteController(MatriculaController matriculaController) {
        this.matriculaController = matriculaController;
    }

    // ========== REPORTES DE CURSOS ==========
    public String generarReporteCurso(Curso curso) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE DEL CURSO =====\n");
        sb.append("Código: ").append(curso.getCodigo()).append("\n");
        sb.append("Materia: ").append(curso.getMateria().getNombre()).append("\n");
        sb.append("Docente: ").append(curso.getDocente().getNombre()).append("\n");
        sb.append("Horario: ").append(curso.getHorario()).append("\n");
        sb.append("Aula: ").append(curso.getAula().getCodigo()).append("\n");
        sb.append("Matriculados: ").append(curso.getMatriculados())
          .append("/").append(curso.getVacantes()).append("\n\n");
        
        sb.append("ESTUDIANTES MATRICULADOS:\n");
        int contador = 1;
        for (Matricula m : curso.getMatriculas()) {
            sb.append(contador++).append(". ")
              .append(m.getEstudiante().getNombre())
              .append(" - Promedio: ").append(String.format("%.2f", m.calcularPromedio()))
              .append("\n");
        }
        
        return sb.toString();
    }

    public String generarReporteDetalleCurso(Curso curso) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE DETALLADO DEL CURSO =====\n");
        sb.append("Código: ").append(curso.getCodigo()).append("\n");
        sb.append("Materia: ").append(curso.getMateria().getNombre()).append("\n");
        sb.append("Créditos: ").append(curso.getMateria().getCreditos()).append("\n");
        sb.append("Docente: ").append(curso.getDocente().getNombre()).append("\n");
        sb.append("Especialidad: ").append(curso.getDocente().getEspecialidad()).append("\n");
        sb.append("Horario: ").append(curso.getHorario()).append("\n");
        sb.append("Franja: ").append(curso.getFranjaHoraria()).append("\n");
        sb.append("Aula: ").append(curso.getAula().getCodigo())
          .append(" - ").append(curso.getAula().getEdificio()).append("\n");
        sb.append("Capacidad Aula: ").append(curso.getAula().getCapacidad()).append("\n");
        sb.append("Matriculados: ").append(curso.getMatriculados())
          .append("/").append(curso.getVacantes()).append("\n\n");
        
        sb.append("LISTA DE ESTUDIANTES Y CALIFICACIONES:\n");
        sb.append(String.format("%-5s %-30s %-6s %-6s %-6s %-6s %-10s %-12s\n",
            "No.", "Nombre", "PC1", "PC2", "PC3", "PCF", "Promedio", "Asistencia"));
        sb.append("=".repeat(90)).append("\n");
        
        int contador = 1;
        double sumaPromedios = 0.0;
        for (Matricula m : curso.getMatriculas()) {
            double promedio = m.calcularPromedio();
            sumaPromedios += promedio;
            
            sb.append(String.format("%-5d %-30s %-6.2f %-6.2f %-6.2f %-6.2f %-10.2f %-12d%%\n",
                contador++,
                m.getEstudiante().getNombre(),
                m.getPc1(),
                m.getPc2(),
                m.getPc3(),
                m.getPcf(),
                promedio,
                m.getAsistencia()
            ));
        }
        
        if (curso.getMatriculados() > 0) {
            double promedioGeneral = sumaPromedios / curso.getMatriculados();
            sb.append("\n").append("=".repeat(90)).append("\n");
            sb.append("PROMEDIO GENERAL DEL CURSO: ").append(String.format("%.2f", promedioGeneral));
        }
        
        return sb.toString();
    }

    // ========== REPORTES DE ESTUDIANTES ==========
    public String generarReporteEstudiante(Estudiante estudiante) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE ACADÉMICO =====\n");
        sb.append("Estudiante: ").append(estudiante.getNombre()).append("\n");
        sb.append("Correo: ").append(estudiante.getCorreo()).append("\n");
        sb.append("Carrera: ").append(estudiante.getCarrera().getNombre()).append("\n");
        sb.append("Ciclo: ").append(estudiante.getCiclo()).append("\n");
        sb.append("Total de cursos: ").append(estudiante.getMatriculas().size()).append("\n\n");
        
        sb.append("CURSOS MATRICULADOS:\n");
        double sumaPromedios = 0.0;
        for (Matricula m : estudiante.getMatriculas()) {
            double promedio = m.calcularPromedio();
            sumaPromedios += promedio;
            sb.append("- ").append(m.getCurso().getMateria().getNombre())
              .append(" | Promedio: ").append(String.format("%.2f", promedio))
              .append("\n");
        }
        
        if (!estudiante.getMatriculas().isEmpty()) {
            double promedioGeneral = sumaPromedios / estudiante.getMatriculas().size();
            sb.append("\nPROMEDIO GENERAL: ").append(String.format("%.2f", promedioGeneral));
        }
        
        return sb.toString();
    }

    public String generarReporteDetalleEstudiante(Estudiante estudiante) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE DETALLADO DEL ESTUDIANTE =====\n");
        sb.append("ID: ").append(estudiante.getId()).append("\n");
        sb.append("Nombre: ").append(estudiante.getNombre()).append("\n");
        sb.append("Correo: ").append(estudiante.getCorreo()).append("\n");
        sb.append("Teléfono: ").append(estudiante.getTelefono()).append("\n");
        sb.append("Carrera: ").append(estudiante.getCarrera().getNombre()).append("\n");
        sb.append("Ciclo: ").append(estudiante.getCiclo()).append("\n\n");
        
        int totalCreditos = 0;
        double sumaPromedios = 0.0;
        int cursosAprobados = 0;
        int cursosDesaprobados = 0;
        
        sb.append("DETALLE DE CURSOS:\n");
        sb.append(String.format("%-30s %-10s %-6s %-6s %-6s %-6s %-10s %-10s\n",
            "Materia", "Créditos", "PC1", "PC2", "PC3", "PCF", "Promedio", "Estado"));
        sb.append("=".repeat(95)).append("\n");
        
        for (Matricula m : estudiante.getMatriculas()) {
            Curso curso = m.getCurso();
            double promedio = m.calcularPromedio();
            sumaPromedios += promedio;
            totalCreditos += curso.getMateria().getCreditos();
            
            String estado = promedio >= 10.5 ? "Aprobado" : (promedio > 0 ? "Desaprobado" : "Sin notas");
            if (promedio >= 10.5) cursosAprobados++;
            else if (promedio > 0) cursosDesaprobados++;
            
            sb.append(String.format("%-30s %-10d %-6.2f %-6.2f %-6.2f %-6.2f %-10.2f %-10s\n",
                curso.getMateria().getNombre(),
                curso.getMateria().getCreditos(),
                m.getPc1(),
                m.getPc2(),
                m.getPc3(),
                m.getPcf(),
                promedio,
                estado
            ));
        }
        
        sb.append("\n").append("=".repeat(95)).append("\n");
        sb.append("RESUMEN:\n");
        sb.append("Total de cursos: ").append(estudiante.getMatriculas().size()).append("\n");
        sb.append("Total de créditos: ").append(totalCreditos).append("\n");
        sb.append("Cursos aprobados: ").append(cursosAprobados).append("\n");
        sb.append("Cursos desaprobados: ").append(cursosDesaprobados).append("\n");
        
        if (!estudiante.getMatriculas().isEmpty()) {
            double promedioGeneral = sumaPromedios / estudiante.getMatriculas().size();
            sb.append("PROMEDIO GENERAL: ").append(String.format("%.2f", promedioGeneral)).append("\n");
        }
        
        return sb.toString();
    }

    // ========== REPORTES DE DOCENTES ==========
    public String generarReporteDocente(Docente docente) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE DEL DOCENTE =====\n");
        sb.append("Nombre: ").append(docente.getNombre()).append("\n");
        sb.append("Correo: ").append(docente.getCorreo()).append("\n");
        sb.append("Especialidad: ").append(docente.getEspecialidad()).append("\n");
        sb.append("Total de cursos asignados: ").append(docente.getCursosAsignados().size()).append("\n\n");
        
        sb.append("CURSOS ASIGNADOS:\n");
        int totalEstudiantes = 0;
        
        for (Curso curso : docente.getCursosAsignados()) {
            totalEstudiantes += curso.getMatriculados();
            sb.append("- ").append(curso.getCodigo()).append(" - ")
              .append(curso.getMateria().getNombre())
              .append(" | Estudiantes: ").append(curso.getMatriculados())
              .append("/").append(curso.getVacantes())
              .append("\n");
        }
        
        sb.append("\nTOTAL DE ESTUDIANTES: ").append(totalEstudiantes);
        
        return sb.toString();
    }

    // ========== REPORTES GENERALES ==========
    public String generarReporteGeneral() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE GENERAL DEL SISTEMA =====\n");
        sb.append("Fecha de generación: ").append(java.time.LocalDate.now()).append("\n\n");
        
        // Esta funcionalidad requiere acceso a los otros controladores
        sb.append("Para generar un reporte general completo, use el SistemaController principal.\n");
        
        return sb.toString();
    }
}