/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gustavo
 */
public class Administrador extends Pessoa {
    private boolean AcessoEspecial;
    
    

    public boolean getAcessoEspecial() {
        if(this.getCargo() != false){return true;}
        return false;
        
    }

    
    
    
    
    
}
 