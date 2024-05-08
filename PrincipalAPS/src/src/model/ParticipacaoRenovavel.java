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
//Participação da energia renovável na empresa
public class ParticipacaoRenovavel extends RelatorioIndicador implements Indicador {
    
    final String tabela = "ParticipacaoRenovavel";
    
    RelatorioIndicador[] dadosIndicador = new RelatorioIndicador[10];
  
   public void setIndicador(String descricao, Date data, float valor_registro){ 
       //parte de inserir dados que vier na tabela definida//
       
       //parte de inserir dados que vier na tabela definida//
       dadosIndicador[0] = new RelatorioIndicador();
       dadosIndicador[0].setRelatorioIndicador(descricao,data,valor_registro);
       
        dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
        System.out.println(dadosIndicador[0].getDescricao());
        System.out.println(dadosIndicador[0].getData());
        System.out.println(dadosIndicador[0].getValor_registro());
      
   }
        
    public RelatorioIndicador[] BuscarIndicador(){
       //parte de buscar dados que vier na tabela definida//
       
       //parte de buscar dados que vier na tabela definida//
        dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
        return dadosIndicador;
    }
    
}
