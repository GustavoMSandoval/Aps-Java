/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Gustavo
 */
public interface Indicador {
    
    void setIndicador(String descricao , float valor_registro,int quantidade,Date data)throws ParseException;
        
    RelatorioIndicador[] BuscarIndicador();
    
}
