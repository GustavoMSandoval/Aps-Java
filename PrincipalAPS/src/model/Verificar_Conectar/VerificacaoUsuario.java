/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Verificar_Conectar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Pessoa;
import model.Pessoa;
import model.Usuario;
import view.Home;
/**
 *
 * @author Gustavo
 */
public class VerificacaoUsuario extends Pessoa{
    private boolean Aprovado;

    public VerificacaoUsuario(String nome, String email, String senha) {
           Usuario usuario = new Usuario(nome,email,senha);
       
       
        if("".equals(usuario.getNome())||" ".equals(usuario.getNome())) // verifica se a opção está vazia, caso esteja uma mensagem de erro será enviada.
        {JOptionPane.showMessageDialog
        (new JFrame(),"Nome completo é necessário","ERRO",JOptionPane.ERROR_MESSAGE);
        setAprovado(false);}
           
        else if("".equals(usuario.getEmail()))
        {JOptionPane.showMessageDialog
        (new JFrame(),"E-mail é necessário","ERRO",JOptionPane.ERROR_MESSAGE);
        setAprovado(false);}
           
        else if("".equals(usuario.getSenha()))
        {JOptionPane.showMessageDialog
        (new JFrame(),"Senha é necessária","ERRO",JOptionPane.ERROR_MESSAGE);
        setAprovado(false);}
        
        else
        {
            setAprovado(true);
            
        }    
          
    }

public boolean getAprovado(){return this.Aprovado;}
public void setAprovado(boolean aprovado){this.Aprovado = aprovado;}
            
}
