/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gustavo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Verificar_Conectar.ConexaoVerificacao;

public class ConsumoSetor extends RelatorioIndicador implements Indicador {
    
    final String tabela = "tabelagrafica";
    
    RelatorioIndicador[] dadosIndicador = new RelatorioIndicador[10];
  
   public void setIndicador(String descricao, float valor_registro,int quantidade, Date data){ 
           
       dadosIndicador[0] = new RelatorioIndicador();
       
       dadosIndicador[0].setRelatorioIndicador(descricao,valor_registro,quantidade,data);
       
       dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
            
        
        try {
            SalvarIndicadorBD(dadosIndicador[0].getDescricao(),dadosIndicador[0].getValor_registro(),dadosIndicador[0].getQuantidade(),dadosIndicador[0].getData());
        } catch (SQLException ex) {
            System.out.println(ex + "erro consumo");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex + "consumosetor");
        }
        
        System.out.println(dadosIndicador[0].getDescricao());
        
        System.out.println(dadosIndicador[0].getData());
        
        System.out.println(dadosIndicador[0].getValor_registro());
        
        System.out.println(dadosIndicador[0].getQuantidade());
      
   }
 
        
    public RelatorioIndicador[] BuscarIndicador(){
       //parte de buscar dados que vier na tabela definida//
       
       //parte de buscar dados que vier na tabela definida//
        dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
        return dadosIndicador;
    }
    
    public void SalvarIndicadorBD(String descricao, float valor_registro,int quantidade, Date data) throws SQLException, ClassNotFoundException{
        
            
      
            
            
            ConexaoVerificacao con = new ConexaoVerificacao();
            con.abrirConexao();
            java.sql.Date sqldate = new java.sql.Date(data.getTime());
            con.InsercaoValoresExcel(descricao, valor_registro, quantidade, sqldate);
            con.fecharConexao();
            
    }
}
    
   

