// Modelo/Matricula.java
package Modelo;

import java.time.LocalDate;

public class Matricula {
    private int id;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fecha;
    private String estado;
    private int asistencia;
    private double pc1;
    private double pc2;
    private double pc3;
    private double pcf;

    public Matricula(int id, Estudiante estudiante, Curso curso) {
        this.id = id;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fecha = LocalDate.now();
        this.estado = "Matriculado";
        this.asistencia = 0;
        this.pc1 = 0.0;
        this.pc2 = 0.0;
        this.pc3 = 0.0;
        this.pcf = 0.0;
    }

    public int getId() { return id; }
    public Estudiante getEstudiante() { return estudiante; }
    public Curso getCurso() { return curso; }
    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public int getAsistencia() { return asistencia; }
    public double getPc1() { return pc1; }
    public double getPc2() { return pc2; }
    public double getPc3() { return pc3; }
    public double getPcf() { return pcf; }
    
    public void setEstado(String estado) { this.estado = estado; }
    public void setAsistencia(int asistencia) { this.asistencia = asistencia; }
    public void setPc1(double pc1) { this.pc1 = pc1; }
    public void setPc2(double pc2) { this.pc2 = pc2; }
    public void setPc3(double pc3) { this.pc3 = pc3; }
    public void setPcf(double pcf) { this.pcf = pcf; }
    
    public double calcularPromedio() {
        return (pc1 + pc2 + pc3 + pcf) / 4.0;
    }
}