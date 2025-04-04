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
public class Recibo {
    
    public static void main(String[] args) {
        
        Scanner lector = new Scanner (System.in);
        
        int pre, cant, pretotal;
        
        String nom, prod;
        
        System.out.println("Ingresa el nombre del cliente");
        
        nom = lector.nextLine();
        
        System.out.println("Ingresa el nombre del producto");
        
        prod = lector.nextLine();
        
        System.out.println("Ingresa el precio del producto");
        
        pre = lector.nextInt();

        System.out.println("Ingresa la cantidad de " + prod + " a comprar");
        
        cant = lector.nextInt();
        
        pretotal = pre * cant;
        
        String impresion = """
                           
                           ######################################
                                       Tienda ABC
                           ######################################
                           ID : 0000252145
                           
                                         Compras
                                    AV. SAENZ PEÑA 376
                                         CHICLAYO
                                   LOTE:B     TERM :5268
                           ######################################
                           FECHA: 07MAR22            HORA : 15:35
                           VEND: JUAN              CLI: %5S
                           ######################################
                           %d  %S (%d)
                           
                           PAGO TOTAL : S/. %d
                           
                           ######################################
                           VUELVA PRONTO!
                           ######################################
                           """;
        
        System.out.printf(impresion, nom, cant, prod, pre, pretotal);

    }
}
