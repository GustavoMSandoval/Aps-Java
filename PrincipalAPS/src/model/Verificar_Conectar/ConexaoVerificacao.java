/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Verificar_Conectar;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.ConsumoSetor;

/**
 *
 * @author Gustavo
 */
public class ConexaoVerificacao {
    private String $Url;
    private String $DB;
    private String $Host;
    private String $User;
    private String $Pwd;
    public Connection connection;
    
    

    public void InsercaoValoresForm(String nome,String email,String senha) throws SQLException, ClassNotFoundException 
    {
        //consulta e inserção de valores
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.get$Url(),this.get$User(),this.get$Pwd());
        Statement st = con.createStatement();
        String query = "INSERT INTO registro(nome,email,senha)"
                + "VALUES('"+nome+"','"+email+"','"+senha+"')";
        st.execute(query);
        
    }
   
    
    public boolean ConsultaValoresUsuarios(String nome,String email,String senha) throws SQLException, ClassNotFoundException
    {
        int notFound = 0;
        String sen = null;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.get$Url(),this.get$User(),this.get$Pwd());
        Statement st = null;
        st = con.createStatement();
        try {
        
        String query = 
        "SELECT nome,email,senha FROM registro WHERE nome = '"+nome+"' and email = '"+email+"' and senha = '"+senha+"'";            
        
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()) {sen = senha;notFound = 1;} 
        if (notFound == 1 && senha.equals(sen)){return true;}
        
        
        
        } catch (Exception e) {
            System.out.println(e);
        }
        
      
        return false;
           
    }
    
    
    public void InsercaoValoresExcel(String descricao,float valor,int quantidade,Date data) throws SQLException, ClassNotFoundException 
    {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String s = formatter.format(data); // Convertendo a data para uma string formatada
        //consulta e inserção de valores
      
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                        
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb") ;   
            
            String sql =  "INSERT INTO tabelagrafica (id,descricao,valor,quantidade,data) VALUES (6,?,?,?,?)";
          
           
                   

            
                
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
          
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");                       
                //Statement statement = connection.createStatement();
                pstmt.setString(1, descricao);
                pstmt.setFloat(2, valor); 
                pstmt.setInt(3,quantidade);
                  try {
                    java.util.Date myDate = formatter.parse(s); // Convertendo a string formatada de volta para uma data
                    java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
                    pstmt.setDate(4, sqlDate);
                } catch (ParseException e) {
                    // Lidar com a exceção, por exemplo, imprimir uma mensagem de erro
                    System.err.println("Erro ao fazer o parsing da data: " + e.getMessage()); 
                }             
                          
                //String query = "INSERT INTO tabela_teste (descricao, data, valor) VALUES ('"+ descricao+ "',"+s+", '"+valor_registro+"')";
                //int linhasAfetadas = statement.executeUpdate(query);
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                System.out.println("Dados inseridos com sucesso!");
                } else {
                System.out.println("Nenhum dado foi inserido.");
            }
                // Fechando recursos       
               // statement.close();
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
    
    public void InsercaoValoresExcelParticipacao(String descricao,float valor,int quantidade,Date data) throws SQLException, ClassNotFoundException 
    {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String s = formatter.format(data); // Convertendo a data para uma string formatada
        //consulta e inserção de valores
      
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                        
            
                    
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb") ;       

            String sql =  "INSERT INTO tabelaparticipacao (id,descricao,valor,quantidade,data) VALUES (6,?,?,?,?)";
          
            
            
            
                
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
          
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");                       
                //Statement statement = connection.createStatement();
                pstmt.setString(1, descricao);
                pstmt.setFloat(2, valor); 
                pstmt.setInt(3,quantidade);
                  try {
                    java.util.Date myDate = formatter.parse(s); // Convertendo a string formatada de volta para uma data
                    java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
                    pstmt.setDate(4, sqlDate);
                } catch (ParseException e) {
                    // Lidar com a exceção, por exemplo, imprimir uma mensagem de erro
                    System.err.println("Erro ao fazer o parsing da data: " + e.getMessage()); 
                }             
                          
               
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                System.out.println("Dados inseridos com sucesso!");
                } else {
                System.out.println("Nenhum dado foi inserido.");
            }
                // Fechando recursos       
               // statement.close();
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


       public void InsercaoValoresExcelReducao(String descricao,float valor_bruto,int valor_renovavel,Date data) throws SQLException, ClassNotFoundException 
    {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String s = formatter.format(data); // Convertendo a data para uma string formatada
        //consulta e inserção de valores
      
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                        
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb", "root", "");
            
            String sql =  "INSERT INTO tabelareducao (id,descricao,valor_bruto,valor_renovavel,data) VALUES (6,?,?,?,?)";
          
            

            
                
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
          
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");                       
                //Statement statement = connection.createStatement();
                pstmt.setString(1, descricao);
                pstmt.setFloat(2, valor_bruto); 
                pstmt.setInt(3,valor_renovavel);
                  try {
                    java.util.Date myDate = formatter.parse(s); // Convertendo a string formatada de volta para uma data
                    java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
                    pstmt.setDate(4, sqlDate);
                } catch (ParseException e) {
                    // Lidar com a exceção, por exemplo, imprimir uma mensagem de erro
                    System.err.println("Erro ao fazer o parsing da data: " + e.getMessage()); 
                }             
                          
                //String query = "INSERT INTO tabela_teste (descricao, data, valor) VALUES ('"+ descricao+ "',"+s+", '"+valor_registro+"')";
                //int linhasAfetadas = statement.executeUpdate(query);
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                System.out.println("Dados inseridos com sucesso!");
                } else {
                System.out.println("Nenhum dado foi inserido.");
            }
                // Fechando recursos       
               // statement.close();
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
    //Getters
    public String get$Url() {return $Url;}   
    
    public String get$DB() {return $DB;}    
    
    public String get$Host() {return $Host;}    
    
    public String get$User() {return $User;}    
    
    public String get$Pwd() {return $Pwd;}
    
    public Connection  getConnection() {return connection;}
    
    //Setters
    public void set$Url(String $Url) {this.$Url = $Url;}
    
    public void set$DB(String $DB) {this.$DB = $DB;}
    
    public void set$Host(String $Host) {this.$Host = $Host;}

    public void set$User(String $User) {this.$User = $User;}

    public void set$Pwd(String $Pwd) {this.$Pwd = $Pwd;}
    
    
    public void abrirConexao() {
            try {
            final String hostname = "localhost:3307";
            this.set$Host("root");
            this.set$Pwd(""); 
            this.set$DB("mydb");
            this.set$Url("jdbc:mysql://" + hostname + "/" + get$DB());
            connection = DriverManager.getConnection(get$Url(), get$Host(), get$Pwd());
            } catch (SQLException e) {
                // Trate qualquer exceção de conexão aqui
                e.printStackTrace();
            }
    }
    
    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Trate qualquer exceção de fechamento de conexão aqui
            e.printStackTrace();
        }
    }

    
     
}
