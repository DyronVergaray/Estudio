package Controlador;

import Modelo.*;
import java.util.List;

/**
 * Controlador principal del sistema que coordina todos los subcontroladores
 * Patrón Singleton + Facade
 */
public class SistemaController {
    private static SistemaController instancia;
    
    // Subcontroladores especializados
    private UsuarioController usuarioController;
    private CursoController cursoController;
    private MatriculaController matriculaController;
    private AcademicoController academicoController;
    private ReporteController reporteController;

    private SistemaController() {
        this.usuarioController = new UsuarioController();
        this.cursoController = new CursoController();
        this.matriculaController = new MatriculaController();
        this.academicoController = new AcademicoController();
        this.reporteController = new ReporteController(matriculaController);
        
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
        usuarioController.registrarAdministrador("Admin Sistema", 
            "admin@uni.edu", "admin123", "999888777");
        
        // Crear malla curricular
        academicoController.crearMallaCurricular("Malla Ingeniería Software", "2024-I");
        MallaCurricular malla1 = academicoController.listarMallas().get(0);
        
        // Crear materias
        academicoController.crearMateria("Programación Orientada a Objetos", 4);
        academicoController.crearMateria("Base de Datos I", 4);
        academicoController.crearMateria("Estructura de Datos", 4);
        
        List<Materia> materias = academicoController.listarMaterias();
        for (Materia m : materias) {
            academicoController.agregarMateriaAMalla(malla1.getId(), m);
        }
        
        // Crear carrera
        academicoController.crearCarrera("Ingeniería de Software", 30, 
            "Carrera enfocada en desarrollo de software", malla1);
        Carrera carrera1 = academicoController.listarCarreras().get(0);
        
        // Crear aulas
        academicoController.crearAula("A-301", 35, "Edificio A");
        academicoController.crearAula("B-205", 40, "Edificio B");
        
        // Crear docente
        usuarioController.registrarDocente("Dr. Marco Soto", 
            "marco.soto@uni.edu", "doc123", "987654321", "Programación");
        Docente doc1 = usuarioController.listarDocentes().get(0);
        
        // Crear cursos
        Aula aula1 = academicoController.listarAulas().get(0);
        Materia mat1 = materias.get(0);
        cursoController.crearCurso("POO-2024-A", mat1, doc1, 
            aula1, "Lunes y Miércoles 8:00-10:00", "Mañana", 30);
        
        // Crear estudiante de prueba
        usuarioController.registrarEstudiante("Juan Pérez", 
            "juan.perez@uni.edu", "est123", "965432187", carrera1, 4);
    }

    // ========== DELEGACIÓN A USUARIOCONTROLLER ==========
    public boolean iniciarSesion(String correo, String contrasena) {
        return usuarioController.iniciarSesion(correo, contrasena);
    }

    public void cerrarSesion() {
        usuarioController.cerrarSesion();
    }

    public Usuario getUsuarioActual() {
        return usuarioController.getUsuarioActual();
    }

    public String getTipoUsuarioActual() {
        return usuarioController.getTipoUsuarioActual();
    }

    public boolean registrarEstudiante(String nombre, String correo, String contrasena, 
                                       String telefono, Carrera carrera, int ciclo) {
        return usuarioController.registrarEstudiante(nombre, correo, contrasena, 
            telefono, carrera, ciclo);
    }

    public boolean registrarDocente(String nombre, String correo, String contrasena, 
                                    String telefono, String especialidad) {
        return usuarioController.registrarDocente(nombre, correo, contrasena, 
            telefono, especialidad);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioController.buscarUsuarioPorCorreo(correo);
    }

    public List<Estudiante> listarEstudiantes() {
        return usuarioController.listarEstudiantes();
    }

    public List<Docente> listarDocentes() {
        return usuarioController.listarDocentes();
    }

    public boolean eliminarUsuario(int id) {
        return usuarioController.eliminarUsuario(id);
    }

    // ========== DELEGACIÓN A CURSOCONTROLLER ==========
    public boolean crearCurso(String codigo, Materia materia, Docente docente, 
                              Aula aula, String horario, String franjaHoraria, int vacantes) {
        return cursoController.crearCurso(codigo, materia, docente, aula, 
            horario, franjaHoraria, vacantes);
    }

    public List<Curso> listarCursos() {
        return cursoController.listarCursos();
    }

    public List<Curso> listarCursosDisponibles(Estudiante estudiante) {
        return cursoController.listarCursosDisponibles(estudiante);
    }

    public boolean eliminarCurso(int id) {
        return cursoController.eliminarCurso(id);
    }

    // ========== DELEGACIÓN A MATRICULACONTROLLER ==========
    public boolean matricularEstudiante(Estudiante estudiante, Curso curso) {
        return matriculaController.matricularEstudiante(estudiante, curso);
    }

    public List<Matricula> listarMatriculasPorCurso(Curso curso) {
        return matriculaController.listarMatriculasPorCurso(curso);
    }

    public List<Matricula> listarMatriculasPorEstudiante(Estudiante estudiante) {
        return matriculaController.listarMatriculasPorEstudiante(estudiante);
    }

    // ========== DELEGACIÓN A ACADEMICOCONTROLLER ==========
    public boolean crearCarrera(String nombre, int vacantes, String descripcion, 
                                MallaCurricular malla) {
        return academicoController.crearCarrera(nombre, vacantes, descripcion, malla);
    }

    public List<Carrera> listarCarreras() {
        return academicoController.listarCarreras();
    }

    public boolean eliminarCarrera(int id) {
        return academicoController.eliminarCarrera(id);
    }

    public boolean crearAula(String codigo, int capacidad, String edificio) {
        return academicoController.crearAula(codigo, capacidad, edificio);
    }

    public List<Aula> listarAulas() {
        return academicoController.listarAulas();
    }

    public boolean crearMateria(String nombre, int creditos) {
        return academicoController.crearMateria(nombre, creditos);
    }

    public List<Materia> listarMaterias() {
        return academicoController.listarMaterias();
    }

    public boolean crearMallaCurricular(String nombre, String version) {
        return academicoController.crearMallaCurricular(nombre, version);
    }

    public List<MallaCurricular> listarMallas() {
        return academicoController.listarMallas();
    }

    // ========== DELEGACIÓN A REPORTECONTROLLER ==========
    public String generarReporteCurso(Curso curso) {
        return reporteController.generarReporteCurso(curso);
    }

    public String generarReporteEstudiante(Estudiante estudiante) {
        return reporteController.generarReporteEstudiante(estudiante);
    }

    public String generarReporteDetalleCurso(Curso curso) {
        return reporteController.generarReporteDetalleCurso(curso);
    }

    public String generarReporteDetalleEstudiante(Estudiante estudiante) {
        return reporteController.generarReporteDetalleEstudiante(estudiante);
    }

    public String generarReporteDocente(Docente docente) {
        return reporteController.generarReporteDocente(docente);
    }

    // ========== ACCESO A SUBCONTROLADORES (OPCIONAL) ==========
    /**
     * Permite acceso directo a los subcontroladores para operaciones avanzadas
     */
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public CursoController getCursoController() {
        return cursoController;
    }

    public MatriculaController getMatriculaController() {
        return matriculaController;
    }

    public AcademicoController getAcademicoController() {
        return academicoController;
    }

    public ReporteController getReporteController() {
        return reporteController;
    }
}