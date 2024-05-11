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
    
    public void Conexao( String $User, String $Pwd)
    {
        this.set$User($User);
        this.set$Pwd($Pwd);
        this.set$Url("jdbc:mysql://localhost:"+this.get$Host()+"/"+this.get$DB()+"");
    }
    
    public void InsercaoValoresForm(String email,String nome,String senha) throws SQLException, ClassNotFoundException 
    {
        //consulta e inserção de valores
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.get$Url(),this.get$User(),this.get$Pwd());
        Statement st = con.createStatement();
        String query = "INSERT INTO registro(email,nome,senha)"
                + "VALUES('"+email+"','"+nome+"','"+senha+"')";
        st.execute(query);
        
    }
    public void InsercaoValoresUsuario(String email,float valor) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.get$Url(),this.get$User(),this.get$Pwd());
        Statement st = con.createStatement();
        String query = "INSERT INTO consumo(email,valor)"
                + "VALUES('"+email+"','"+valor+"')";
        st.execute(query);
        
    }
    
    public void ConsultaValoresUsuarios() throws SQLException, ClassNotFoundException
    {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.get$Url(),this.get$User(),this.get$Pwd());
        Statement st = null;
        st = con.createStatement();
        String query = " SELECT valor from consumo where email = 'a' ";
       
        ResultSet rs = st.executeQuery(query);
        ConsumoSetor cons = new ConsumoSetor();
        
        float valor = cons.getValor_registro();
        System.out.println(valor);
                    
        
            
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Getters    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
