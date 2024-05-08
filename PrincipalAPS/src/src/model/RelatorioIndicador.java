/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gustavo
 */
import java.util.Date;
public class RelatorioIndicador {
   private String descricao;
   private Date data;
   private float valor_registro;
   

   public RelatorioIndicador getRelatorioIndicador(){
       return this;
   }
   
   public RelatorioIndicador() {
        this.descricao =  this.descricao;
        this.data =  this.data;
        this.valor_registro =  this.valor_registro;
   }

   // Método de definição para atribuir valores ao objeto
   public void setRelatorioIndicador(String descricao, Date data, float valor_registro){
       this.descricao = descricao;
       this.data = data;
       this.valor_registro = valor_registro;
   }
   
   // Métodos de acesso (getters e setters)
   public String getDescricao() {
       return descricao;
   }

   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }

   public Date getData() {
       return data;
   }

   public void setData(Date data) {
       this.data = data;
   }

   public float getValor_registro() {
       return valor_registro;
   }

   public void setValor_registro(float valor_registro) {
       this.valor_registro = valor_registro;
   }
}
