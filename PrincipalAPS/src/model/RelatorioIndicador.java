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
   private float valor_registro;
   private int quantidade;
   private Date data;

   
   public RelatorioIndicador getRelatorioIndicador(){
       return this;
   }
   
   public RelatorioIndicador() {
        this.descricao =  this.descricao;
        this.data =  this.data;
        this.quantidade = this.quantidade;
        this.valor_registro =  this.valor_registro;
   }
   

   // Método de definição para atribuir valores ao objeto
   public void setRelatorioIndicador(String descricao, float valor_registro,int quantidade, Date data){
       this.descricao = descricao;
       this.data = data;
       this.quantidade = quantidade;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
