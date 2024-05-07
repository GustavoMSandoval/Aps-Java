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

public class ConsumoSetor extends RelatorioIndicador implements Indicador {
    
    final String tabela = "ConsumoSetor";
    
    RelatorioIndicador[] dadosIndicador = new RelatorioIndicador[10];
  
   public void setIndicador(String descricao, Date data, float valor_registro){ 
           
       dadosIndicador[0] = new RelatorioIndicador();
       
       dadosIndicador[0].setRelatorioIndicador(descricao,data,valor_registro);
       
       dadosIndicador[0] = dadosIndicador[0].getRelatorioIndicador();
            
        
        SalvarIndicadorBD(dadosIndicador[0].getDescricao(),dadosIndicador[0].getData(),dadosIndicador[0].getValor_registro());
        
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
    
    public void SalvarIndicadorBD(String descricao, Date data, float valor_registro){
        
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String s = formatter.format(data); // Convertendo a data para uma string formatada
      
            System.out.println(s);
            String hostname = "";
            String username = "";
            String password = "";
            String database = "";
       
            String url = "jdbc:mysql://" + hostname + "/" + database;

            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String sql = "INSERT INTO tabela_teste (descricao, data, valor) VALUES (?, ?, ?)";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
          
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");                       
                //Statement statement = connection.createStatement();
                pstmt.setString(1, descricao);
                  try {
                    java.util.Date myDate = formatter.parse(s); // Convertendo a string formatada de volta para uma data
                    java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
                    pstmt.setDate(2, sqlDate);
                } catch (ParseException e) {
                    // Lidar com a exceção, por exemplo, imprimir uma mensagem de erro
                    System.err.println("Erro ao fazer o parsing da data: " + e.getMessage()); 
                }             
                pstmt.setFloat(3, valor_registro);            
                //String query = "INSERT INTO tabela_teste (descricao, data, valor) VALUES ('"+ descricao+ "',"+s+", '"+valor_registro+"')";
                //int linhasAfetadas = statement.executeUpdate(query);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                System.out.println("Dados inseridos com sucesso!");
                } else {
                System.out.println("Nenhum dado foi inserido.");
            }
                // Fechando recursos       
                //statement.close();
                connection.close();
            }
            } catch (ClassNotFoundException e) {
                System.out.println("Driver JDBC não encontrado.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
   }

    
   
}
