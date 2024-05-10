/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Verificar_Conectar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public void Conexao( String $User, String $Pwd)
    {
        this.set$User($User);
        this.set$Pwd($Pwd);
        this.set$Url("jdbc:mysql://localhost:"+this.get$Host()+"/"+this.get$DB()+"");
    }
    
    public void Consulta(String email,String nome,String senha) throws SQLException, ClassNotFoundException 
    {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.get$Url(),this.get$User(),this.get$Pwd());
        Statement st = con.createStatement();
        String query = "INSERT INTO registro(email,nome,senha)"
                + "VALUES('"+email+"','"+nome+"','"+senha+"')";
        st.execute(query);
        
    }
    
    //Getters
    public String get$Url() {return $Url;}
    
    
    public String get$DB() {return $DB;}
    
    
    public String get$Host() {return $Host;}
    
    
    public String get$User() {return $User;}
    
    
    public String get$Pwd() {return $Pwd;}
    
    
    //Setters
    public void set$Url(String $Url) {this.$Url = $Url;}

    
    public void set$DB(String $DB) {this.$DB = $DB;}

    
    public void set$Host(String $Host) {this.$Host = $Host;}


    public void set$User(String $User) {this.$User = $User;}


    public void set$Pwd(String $Pwd) {this.$Pwd = $Pwd;}
    
}
