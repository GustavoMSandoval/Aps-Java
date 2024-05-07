/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Verificar_Conectar;

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
    
    public ConexaoVerificacao(String $Url, String $User, String $Pwd)
    {
        this.set$User($User);
        this.set$Pwd($Pwd);
        this.set$Url("jdbc:mysql:localhost:"+this.get$Host()+"//"+this.get$DB()+"");
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
