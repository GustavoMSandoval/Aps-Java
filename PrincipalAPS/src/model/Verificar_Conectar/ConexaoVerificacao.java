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
