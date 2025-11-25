package Modelo;

public class Aula {
    private int id;
    private String codigo;
    private int capacidad;
    private String edificio;

    public Aula(int id, String codigo, int capacidad, String edificio) {
        this.id = id;
        this.codigo = codigo;
        this.capacidad = capacidad;
        this.edificio = edificio;
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public int getCapacidad() { return capacidad; }
    public String getEdificio() { return edificio; }
    
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setEdificio(String edificio) { this.edificio = edificio; }
    
    @Override
    public String toString() {
        return codigo + " - " + edificio + " (Cap: " + capacidad + ")";
    }
}