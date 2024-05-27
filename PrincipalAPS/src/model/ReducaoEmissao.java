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
public class ReducaoEmissao extends RelatorioIndicador implements Indicador  {
    
    final String tabela = "tabelareducao";
    
    RelatorioIndicador[] dadosIndicador = new RelatorioIndicador[10];
  
   
   public void setIndicador(String descricao, float valor_registro,int quantidade, Date data){ 
       //parte de inserir dados que vier na tabela definida//
       
       //parte de inserir dados que vier na tabela definida//
       dadosIndicador[0] = new RelatorioIndicador();
       dadosIndicador[0].setRelatorioIndicador(descricao,valor_registro,quantidade,data);
       
        dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
        System.out.println(dadosIndicador[0].getDescricao());
        System.out.println(dadosIndicador[0].getValor_registro());
        System.out.println(dadosIndicador[0].getQuantidade());
        System.out.println(dadosIndicador[0].getData());
       
      
   }
        
    public RelatorioIndicador[] BuscarIndicador(){
       //parte de buscar dados que vier na tabela definida//
       
       //parte de buscar dados que vier na tabela definida//
        dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
        return dadosIndicador;
    }
    
}
