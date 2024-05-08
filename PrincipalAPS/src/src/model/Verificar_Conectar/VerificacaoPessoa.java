/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Verificar_Conectar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Pessoa;
import model.Pessoa;
import view.Home;
/**
 *
 * @author Gustavo
 */
public class VerificacaoPessoa extends Pessoa{
    private boolean Aprovado;

    public VerificacaoPessoa(String nome, String email, String senha) {
        Pessoa pessoa = new Pessoa(nome,email,senha);
       
       
        if("".equals(pessoa.getNome())||" ".equals(pessoa.getNome())) // verifica se a opção está vazia, caso esteja uma mensagem de erro será enviada.
        {JOptionPane.showMessageDialog
        (new JFrame(),"Nome completo é necessário","ERRO",JOptionPane.ERROR_MESSAGE);
        setAprovado(false);}
           
        else if("".equals(pessoa.getEmail()))
        {JOptionPane.showMessageDialog
        (new JFrame(),"E-mail é necessário","ERRO",JOptionPane.ERROR_MESSAGE);
        setAprovado(false);}
           
        else if("".equals(pessoa.getSenha()))
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
