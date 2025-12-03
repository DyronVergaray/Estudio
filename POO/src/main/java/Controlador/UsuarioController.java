package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioController {
    private List<Usuario> usuarios;
    private Usuario usuarioActual;
    private int siguienteIdUsuario;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
        this.siguienteIdUsuario = 1;
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

    // ========== GESTIÓN DE ESTUDIANTES ==========
    public boolean registrarEstudiante(String nombre, String correo, String contrasena, 
                                       String telefono, Carrera carrera, int ciclo) {
        if (buscarUsuarioPorCorreo(correo) != null) return false;
        
        Estudiante estudiante = new Estudiante(siguienteIdUsuario++, nombre, correo, 
            contrasena, telefono, carrera, ciclo);
        return usuarios.add(estudiante);
    }

    public List<Estudiante> listarEstudiantes() {
        return usuarios.stream()
            .filter(u -> u instanceof Estudiante)
            .map(u -> (Estudiante) u)
            .collect(Collectors.toList());
    }

    // ========== GESTIÓN DE DOCENTES ==========
    public boolean registrarDocente(String nombre, String correo, String contrasena, 
                                    String telefono, String especialidad) {
        if (buscarUsuarioPorCorreo(correo) != null) return false;
        
        Docente docente = new Docente(siguienteIdUsuario++, nombre, correo, 
            contrasena, telefono, especialidad);
        return usuarios.add(docente);
    }

    public List<Docente> listarDocentes() {
        return usuarios.stream()
            .filter(u -> u instanceof Docente)
            .map(u -> (Docente) u)
            .collect(Collectors.toList());
    }

    // ========== GESTIÓN DE ADMINISTRADORES ==========
    public boolean registrarAdministrador(String nombre, String correo, String contrasena, 
                                         String telefono) {
        if (buscarUsuarioPorCorreo(correo) != null) return false;
        
        Administrador admin = new Administrador(siguienteIdUsuario++, nombre, correo, 
            contrasena, telefono);
        return usuarios.add(admin);
    }

    // ========== OPERACIONES COMUNES ==========
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarios.stream()
            .filter(u -> u.getCorreo().equals(correo))
            .findFirst()
            .orElse(null);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarios.stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public boolean eliminarUsuario(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        Usuario existente = buscarUsuarioPorId(usuario.getId());
        if (existente == null) return false;
        
        existente.setNombre(usuario.getNombre());
        existente.setContrasena(usuario.getContrasena());
        existente.setTelefono(usuario.getTelefono());
        return true;
    }

    // ========== GETTERS INTERNOS ==========
    List<Usuario> getUsuarios() {
        return usuarios;
    }

    void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    void setSiguienteIdUsuario(int id) {
        this.siguienteIdUsuario = id;
    }
}