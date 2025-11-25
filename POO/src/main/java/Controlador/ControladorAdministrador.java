/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Administrador;
import Modelo.Aula;
import Modelo.Carrera;
import Modelo.Curso;
import Modelo.Docente;
import Modelo.Usuario;
import java.util.List;

public class ControladorAdministrador {
    private Administrador admin;
    private List<Carrera> carreras;
    private List<Curso> cursos;
    private List<Usuario> usuarios;

    public ControladorAdministrador(Administrador admin, List<Carrera> carreras, List<Curso> cursos, List<Usuario> usuarios) {
        this.admin = admin;
        this.carreras = carreras;
        this.cursos = cursos;
        this.usuarios = usuarios;
    }

    public void crearCarrera(Carrera carrera) {
        carreras.add(carrera);
    }

    public void crearCurso(Curso curso) {
        cursos.add(curso);
    }

    public void crearUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void modificarCarrera(int id, String nuevoNombre, int nuevasVacantes, String nuevaDescripcion) {
        for (Carrera carrera : carreras) {
            if (carrera.getId() == id) {
                // setters para actualizar los valores si lo deseas
            }
        }
    }

    public void modificarCurso(int id, String nuevoNombre, Docente nuevoDocente, Aula nuevaAula) {
        for (Curso curso : cursos) {
            if (curso.getId() == id) {
                // setters para actualizar los valores si existen
            }
        }
    }

    public void modificarUsuario(int id, String nuevoNombre, String nuevaContrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setNombre(nuevoNombre);
                usuario.setContrasena(nuevaContrasena);
            }
        }
    }
}
