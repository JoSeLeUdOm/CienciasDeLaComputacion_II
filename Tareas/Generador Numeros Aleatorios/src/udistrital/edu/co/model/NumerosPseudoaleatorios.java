/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.edu.co.model;

/**
 *
 * @author josep
 */
public class NumerosPseudoaleatorios {
    public double semilla;
    public int multiplicador;
    public int modulo;
    public int incremento;

   
    
    public NumerosPseudoaleatorios(){
        semilla = 520;
        multiplicador = 21;
        modulo = 10000;
        incremento = 7;
        
    }
    
    public void funcion() {

        for (int i = 0; i < 20; i++) {

            semilla = (semilla * semilla
                + multiplicador * semilla
                + incremento) % modulo;

         System.out.println(semilla);
    }
}
}
