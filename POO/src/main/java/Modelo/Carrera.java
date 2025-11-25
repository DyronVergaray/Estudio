// Modelo/Carrera.java
package Modelo;

public class Carrera {
    private int id;
    private String nombre;
    private int vacantes;
    private String descripcion;
    private MallaCurricular mallaCurricular;

    public Carrera(int id, String nombre, int vacantes, String descripcion, 
                   MallaCurricular mallaCurricular) {
        this.id = id;
        this.nombre = nombre;
        this.vacantes = vacantes;
        this.descripcion = descripcion;
        this.mallaCurricular = mallaCurricular;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getVacantes() { return vacantes; }
    public String getDescripcion() { return descripcion; }
    public MallaCurricular getMallaCurricular() { return mallaCurricular; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setVacantes(int vacantes) { this.vacantes = vacantes; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setMallaCurricular(MallaCurricular mallaCurricular) { 
        this.mallaCurricular = mallaCurricular; 
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
