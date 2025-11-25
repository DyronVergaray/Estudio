package Modelo;

public class Materia {
    private int id;
    private String nombre;
    private int creditos;

    public Materia(int id, String nombre, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCreditos() { return creditos; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    
    @Override
    public String toString() {
        return nombre + " (" + creditos + " créditos)";
    }
}