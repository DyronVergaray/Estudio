package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicoController {
    private List<Carrera> carreras;
    private List<Aula> aulas;
    private List<Materia> materias;
    private List<MallaCurricular> mallas;
    
    private int siguienteIdCarrera;
    private int siguienteIdAula;
    private int siguienteIdMateria;
    private int siguienteIdMalla;

    public AcademicoController() {
        this.carreras = new ArrayList<>();
        this.aulas = new ArrayList<>();
        this.materias = new ArrayList<>();
        this.mallas = new ArrayList<>();
        
        this.siguienteIdCarrera = 1;
        this.siguienteIdAula = 1;
        this.siguienteIdMateria = 1;
        this.siguienteIdMalla = 1;
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

    public Carrera buscarCarreraPorId(int id) {
        return carreras.stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public boolean actualizarCarrera(Carrera carrera) {
        Carrera existente = buscarCarreraPorId(carrera.getId());
        if (existente == null) return false;
        
        existente.setNombre(carrera.getNombre());
        existente.setVacantes(carrera.getVacantes());
        existente.setDescripcion(carrera.getDescripcion());
        existente.setMallaCurricular(carrera.getMallaCurricular());
        return true;
    }

    public boolean eliminarCarrera(int id) {
        return carreras.removeIf(c -> c.getId() == id);
    }

    // ========== GESTIÓN DE AULAS ==========
    public boolean crearAula(String codigo, int capacidad, String edificio) {
        Aula aula = new Aula(siguienteIdAula++, codigo, capacidad, edificio);
        return aulas.add(aula);
    }

    public List<Aula> listarAulas() {
        return new ArrayList<>(aulas);
    }

    public Aula buscarAulaPorId(int id) {
        return aulas.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public Aula buscarAulaPorCodigo(String codigo) {
        return aulas.stream()
            .filter(a -> a.getCodigo().equals(codigo))
            .findFirst()
            .orElse(null);
    }

    public boolean actualizarAula(Aula aula) {
        Aula existente = buscarAulaPorId(aula.getId());
        if (existente == null) return false;
        
        existente.setCodigo(aula.getCodigo());
        existente.setCapacidad(aula.getCapacidad());
        existente.setEdificio(aula.getEdificio());
        return true;
    }

    public boolean eliminarAula(int id) {
        return aulas.removeIf(a -> a.getId() == id);
    }

    // ========== GESTIÓN DE MATERIAS ==========
    public boolean crearMateria(String nombre, int creditos) {
        Materia materia = new Materia(siguienteIdMateria++, nombre, creditos);
        return materias.add(materia);
    }

    public List<Materia> listarMaterias() {
        return new ArrayList<>(materias);
    }

    public Materia buscarMateriaPorId(int id) {
        return materias.stream()
            .filter(m -> m.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public boolean actualizarMateria(Materia materia) {
        Materia existente = buscarMateriaPorId(materia.getId());
        if (existente == null) return false;
        
        existente.setNombre(materia.getNombre());
        existente.setCreditos(materia.getCreditos());
        return true;
    }

    public boolean eliminarMateria(int id) {
        return materias.removeIf(m -> m.getId() == id);
    }

    // ========== GESTIÓN DE MALLAS CURRICULARES ==========
    public boolean crearMallaCurricular(String nombre, String version) {
        MallaCurricular malla = new MallaCurricular(siguienteIdMalla++, nombre, version);
        return mallas.add(malla);
    }

    public List<MallaCurricular> listarMallas() {
        return new ArrayList<>(mallas);
    }

    public MallaCurricular buscarMallaPorId(int id) {
        return mallas.stream()
            .filter(m -> m.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public boolean agregarMateriaAMalla(int idMalla, Materia materia) {
        MallaCurricular malla = buscarMallaPorId(idMalla);
        if (malla == null) return false;
        
        malla.agregarMateria(materia);
        return true;
    }

    public boolean removerMateriaDeMalla(int idMalla, Materia materia) {
        MallaCurricular malla = buscarMallaPorId(idMalla);
        if (malla == null) return false;
        
        malla.removerMateria(materia);
        return true;
    }

    public boolean eliminarMalla(int id) {
        return mallas.removeIf(m -> m.getId() == id);
    }

    // ========== GETTERS INTERNOS ==========
    List<Carrera> getCarreras() {
        return carreras;
    }

    void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    List<Aula> getAulas() {
        return aulas;
    }

    void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    List<Materia> getMaterias() {
        return materias;
    }

    void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    List<MallaCurricular> getMallas() {
        return mallas;
    }

    void setMallas(List<MallaCurricular> mallas) {
        this.mallas = mallas;
    }

    void setSiguienteIdCarrera(int id) {
        this.siguienteIdCarrera = id;
    }

    void setSiguienteIdAula(int id) {
        this.siguienteIdAula = id;
    }

    void setSiguienteIdMateria(int id) {
        this.siguienteIdMateria = id;
    }

    void setSiguienteIdMalla(int id) {
        this.siguienteIdMalla = id;
    }
}