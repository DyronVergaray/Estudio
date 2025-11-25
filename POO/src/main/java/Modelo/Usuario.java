package Modelo;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected String correo;
    protected String contrasena;
    protected String telefono;

    public Usuario(int id, String nombre, String correo, String contrasena, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getTelefono() { return telefono; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}