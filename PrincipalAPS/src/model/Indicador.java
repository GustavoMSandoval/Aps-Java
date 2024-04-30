/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Gustavo
 */
public interface Indicador {
    
    void setIndicador(String descricao, Date data, double valor_registro);
        
    RelatorioIndicador[] BuscarIndicador();
    
}
