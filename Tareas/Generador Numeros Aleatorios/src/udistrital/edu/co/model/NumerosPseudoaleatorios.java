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
    public int semilla;
    public int multiplicador;
    public int modulo;
    public int incremento;
    
    public NumerosPseudoaleatorios(){
        semilla = 5;
        multiplicador = 3;
        modulo = 7;
        incremento = 0;
    }
    
    public void funcion(){
        for(int i=0; i<7; i++){
           semilla = (multiplicador*semilla+incremento)%modulo;
           
            System.out.println(semilla);
        }
        
    }
}
