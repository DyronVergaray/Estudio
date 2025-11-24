/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.List;

public class ControladorAdministrador {
    private Administrador administrador;
    // Listas simuladas de todas las carreras, cursos y usuarios del sistema. Puedes ajustarlas según cómo manejes datos.
    private List<Carrera> carreras;
    private List<Curso> cursos;
    private List<Usuario> usuarios;

    public ControladorAdministrador(Administrador administrador, List<Carrera> carreras, List<Curso> cursos, List<Usuario> usuarios) {
        this.administrador = administrador;
        this.carreras = carreras;
        this.cursos = cursos;
        this.usuarios = usuarios;
    }

    // Crear carrera
    public void crearCarrera(Carrera carrera) {
        carreras.add(carrera);
    }

    // Modificar carrera
    public void modificarCarrera(int idCarrera, String nuevoNombre, String nuevaDescripcion) {
        for (Carrera c : carreras) {
            if (c.getId() == idCarrera) {
                c.setNombre(nuevoNombre);
                // Si tienes setDescripcion, úsalo:
                // c.setDescripcion(nuevaDescripcion);
                break;
            }
        }
    }

    // Crear curso
    public void crearCurso(Curso curso) {
        cursos.add(curso);
    }

    // Modificar curso
    public void modificarCurso(int idCurso, String nuevoNombre) {
        for (Curso c : cursos) {
            if (c.getId() == idCurso) {
                c.setNombre(nuevoNombre);
                break;
            }
        }
    }

    // Crear usuario (estudiante, docente, admin)
    public void crearUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Modificar usuario
    public void modificarUsuario(int idUsuario, String nuevoNombre, String nuevoCorreo) {
        for (Usuario u : usuarios) {
            if (u.getId() == idUsuario) {
                // dependiendo si es estudiante, docente, admin, puede haber campos particulares
                u.nombre = nuevoNombre;
                u.correo = nuevoCorreo;
                break;
            }
        }
    }
}
