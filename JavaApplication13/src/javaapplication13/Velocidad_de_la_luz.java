/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication13;

import java.util.Scanner;

/**
 *
 * @author LAB-USR-LNORTE
 */
public class Velocidad_de_la_luz {
    public static void main(String[] args) {
        
        Scanner lector = new Scanner (System.in);
        
        final int vluz = -299792458;
        
        int dias, result;
        
        System.out.println("Ingrese la cantidad de días");
        
        dias = lector.nextInt();
        
        result = 60*60*24*dias*vluz;
        
        String impresion = """
                           
                           -------------------------------   
                           
                           La luz recorre %d metros                     
                           en %d días
                           
                           -------------------------------
                           """;
        
        System.out.printf (impresion, result, dias);
    }
}
