package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaController {
    private static SistemaController instancia;
    
    private List<Usuario> usuarios;
    private List<Carrera> carreras;
    private List<Curso> cursos;
    private List<Aula> aulas;
    private List<Materia> materias;
    private List<MallaCurricular> mallas;
    private List<Matricula> matriculas;
    
    private Usuario usuarioActual;
    private int siguienteIdUsuario;
    private int siguienteIdCarrera;
    private int siguienteIdCurso;
    private int siguienteIdAula;
    private int siguienteIdMateria;
    private int siguienteIdMalla;
    private int siguienteIdMatricula;

    private SistemaController() {
        this.usuarios = new ArrayList<>();
        this.carreras = new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.aulas = new ArrayList<>();
        this.materias = new ArrayList<>();
        this.mallas = new ArrayList<>();
        this.matriculas = new ArrayList<>();
        
        this.siguienteIdUsuario = 1;
        this.siguienteIdCarrera = 1;
        this.siguienteIdCurso = 1;
        this.siguienteIdAula = 1;
        this.siguienteIdMateria = 1;
        this.siguienteIdMalla = 1;
        this.siguienteIdMatricula = 1;
        
        inicializarDatosPrueba();
    }

    public static SistemaController getInstancia() {
        if (instancia == null) {
            instancia = new SistemaController();
        }
        return instancia;
    }

    private void inicializarDatosPrueba() {
        // Crear administrador
        Administrador admin = new Administrador(siguienteIdUsuario++, "Admin Sistema", 
            "admin@uni.edu", "admin123", "999888777");
        usuarios.add(admin);
        
        // Crear malla curricular
        MallaCurricular malla1 = new MallaCurricular(siguienteIdMalla++, 
            "Malla Ingeniería Software", "2024-I");
        mallas.add(malla1);
        
        // Crear materias
        Materia mat1 = new Materia(siguienteIdMateria++, "Programación Orientada a Objetos", 4);
        Materia mat2 = new Materia(siguienteIdMateria++, "Base de Datos I", 4);
        Materia mat3 = new Materia(siguienteIdMateria++, "Estructura de Datos", 4);
        materias.add(mat1);
        materias.add(mat2);
        materias.add(mat3);
        
        malla1.agregarMateria(mat1);
        malla1.agregarMateria(mat2);
        malla1.agregarMateria(mat3);
        
        // Crear carrera
        Carrera carrera1 = new Carrera(siguienteIdCarrera++, "Ingeniería de Software", 
            30, "Carrera enfocada en desarrollo de software", malla1);
        carreras.add(carrera1);
        
        // Crear aulas
        Aula aula1 = new Aula(siguienteIdAula++, "A-301", 35, "Edificio A");
        Aula aula2 = new Aula(siguienteIdAula++, "B-205", 40, "Edificio B");
        aulas.add(aula1);
        aulas.add(aula2);
        
        // Crear docente
        Docente doc1 = new Docente(siguienteIdUsuario++, "Dr. Marco Soto", 
            "marco.soto@uni.edu", "doc123", "987654321", "Programación");
        usuarios.add(doc1);
        
        // Crear cursos
        Curso curso1 = new Curso(siguienteIdCurso++, "POO-2024-A", mat1, doc1, 
            aula1, "Lunes y Miércoles 8:00-10:00", "Mañana", 30);
        doc1.agregarCurso(curso1);
        cursos.add(curso1);
        
        // Crear estudiante de prueba
        Estudiante est1 = new Estudiante(siguienteIdUsuario++, "Juan Pérez", 
            "juan.perez@uni.edu", "est123", "965432187", carrera1, 4);
        usuarios.add(est1);
    }

    // ========== AUTENTICACIÓN ==========
    public boolean iniciarSesion(String correo, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo) && 
                usuario.getContrasena().equals(contrasena)) {
                usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String getTipoUsuarioActual() {
        if (usuarioActual instanceof Administrador) return "ADMINISTRADOR";
        if (usuarioActual instanceof Docente) return "DOCENTE";
        if (usuarioActual instanceof Estudiante) return "ESTUDIANTE";
        return null;
    }

    // ========== GESTIÓN DE USUARIOS ==========
    public boolean registrarEstudiante(String nombre, String correo, String contrasena, 
                                       String telefono, Carrera carrera, int ciclo) {
        if (buscarUsuarioPorCorreo(correo) != null) return false;
        
        Estudiante estudiante = new Estudiante(siguienteIdUsuario++, nombre, correo, 
            contrasena, telefono, carrera, ciclo);
        return usuarios.add(estudiante);
    }

    public boolean registrarDocente(String nombre, String correo, String contrasena, 
                                    String telefono, String especialidad) {
        if (buscarUsuarioPorCorreo(correo) != null) return false;
        
        Docente docente = new Docente(siguienteIdUsuario++, nombre, correo, 
            contrasena, telefono, especialidad);
        return usuarios.add(docente);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarios.stream()
            .filter(u -> u.getCorreo().equals(correo))
            .findFirst()
            .orElse(null);
    }

    public List<Estudiante> listarEstudiantes() {
        return usuarios.stream()
            .filter(u -> u instanceof Estudiante)
            .map(u -> (Estudiante) u)
            .collect(Collectors.toList());
    }

    public List<Docente> listarDocentes() {
        return usuarios.stream()
            .filter(u -> u instanceof Docente)
            .map(u -> (Docente) u)
            .collect(Collectors.toList());
    }

    public boolean eliminarUsuario(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }

    // ========== GESTIÓN DE CARRERAS ==========
    public boolean crearCarrera(String nombre, int vacantes, String descripcion, 
                                MallaCurricular malla) {
        Carrera carrera = new Carrera(siguienteIdCarrera++, nombre, vacantes, 
            descripcion, malla);
        return carreras.add(carrera);
    }

    public List<Carrera> listarCarreras() {
        return new ArrayList<>(carreras);
    }

    public boolean eliminarCarrera(int id) {
        return carreras.removeIf(c -> c.getId() == id);
    }

    // ========== GESTIÓN DE CURSOS ==========
    public boolean crearCurso(String codigo, Materia materia, Docente docente, 
                              Aula aula, String horario, String franjaHoraria, int vacantes) {
        Curso curso = new Curso(siguienteIdCurso++, codigo, materia, docente, 
            aula, horario, franjaHoraria, vacantes);
        docente.agregarCurso(curso);
        return cursos.add(curso);
    }

    public List<Curso> listarCursos() {
        return new ArrayList<>(cursos);
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

    public boolean eliminarCurso(int id) {
        return cursos.removeIf(c -> c.getId() == id);
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

    public List<Matricula> listarMatriculasPorCurso(Curso curso) {
        return matriculas.stream()
            .filter(m -> m.getCurso().getId() == curso.getId())
            .collect(Collectors.toList());
    }

    public List<Matricula> listarMatriculasPorEstudiante(Estudiante estudiante) {
        return new ArrayList<>(estudiante.getMatriculas());
    }

    // ========== GESTIÓN DE AULAS ==========
    public boolean crearAula(String codigo, int capacidad, String edificio) {
        Aula aula = new Aula(siguienteIdAula++, codigo, capacidad, edificio);
        return aulas.add(aula);
    }

    public List<Aula> listarAulas() {
        return new ArrayList<>(aulas);
    }

    // ========== GESTIÓN DE MATERIAS ==========
    public boolean crearMateria(String nombre, int creditos) {
        Materia materia = new Materia(siguienteIdMateria++, nombre, creditos);
        return materias.add(materia);
    }

    public List<Materia> listarMaterias() {
        return new ArrayList<>(materias);
    }

    // ========== GESTIÓN DE MALLAS ==========
    public boolean crearMallaCurricular(String nombre, String version) {
        MallaCurricular malla = new MallaCurricular(siguienteIdMalla++, nombre, version);
        return mallas.add(malla);
    }

    public List<MallaCurricular> listarMallas() {
        return new ArrayList<>(mallas);
    }

    // ========== REPORTES ==========
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

    public String generarReporteEstudiante(Estudiante estudiante) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE ACADÉMICO =====\n");
        sb.append("Estudiante: ").append(estudiante.getNombre()).append("\n");
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
}