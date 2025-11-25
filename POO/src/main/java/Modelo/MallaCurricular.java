package Modelo;

import java.util.ArrayList;
import java.util.List;

public class MallaCurricular {
    private int id;
    private String nombre;
    private String version;
    private List<Materia> materias;

    public MallaCurricular(int id, String nombre, String version) {
        this.id = id;
        this.nombre = nombre;
        this.version = version;
        this.materias = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getVersion() { return version; }
    public List<Materia> getMaterias() { return materias; }
    
    public void agregarMateria(Materia materia) {
        this.materias.add(materia);
    }
    
    public void removerMateria(Materia materia) {
        this.materias.remove(materia);
    }
    
    @Override
    public String toString() {
        return nombre + " - " + version;
    }
}