/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Aula {
    private int id;
    private String nombre;
    private String franjaHoraria; // ejemplo: "Mañana" o "Tarde"
    private String horaInicio;
    private String horaFin;

    public Aula(int id, String nombre, String franjaHoraria, String horaInicio, String horaFin) {
        this.id = id;
        this.nombre = nombre;
        this.franjaHoraria = franjaHoraria;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() { return id; }
    public String getFranjaHoraria() { return franjaHoraria; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
}
