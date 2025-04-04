/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication13;

import java.util.Scanner;

/**
 *
 * @author LAB-USR-LNORTE
 */
public class JavaApplication13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int numero, u, d, c, suma;
        
        Scanner lector = new Scanner (System.in);
        
        System.out.println("Ingresa un numero");
        
        numero = lector.nextInt();
        
        if (numero>=100 && numero<=999);
        
        System.out.println("El numero es de tres cifras");
        
        u = numero % 10;
        d = (numero/10) % 10;
        c = numero/100;
        
        suma = u + d + c;
        
        String impresion = """
                           Resumen
                           Numero Ingresado %3d
                           Su unidad es     %3d
                           Su decena es     %3d
                           Su centena es    %3d
                           Suma de digitos;
                           %d + %d + %d = %d
                           """;
        
        System.out.printf(impresion, numero, u, d, c, u, d, c, suma);
        
    }
  
}
