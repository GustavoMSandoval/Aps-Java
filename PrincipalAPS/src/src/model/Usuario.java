/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gustavo
 */
public class Usuario extends Pessoa{
    private boolean permissao;

    public boolean getPermissao() {
        if(this.getCargo() != true){return false;}
        return true;
        
    }

   

    

        
}
