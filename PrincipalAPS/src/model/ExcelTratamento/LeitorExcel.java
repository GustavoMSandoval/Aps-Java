/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ExcelTratamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import model.ConsumoSetor;
import model.ParticipacaoRenovavel;
import model.ReducaoEmissao;
import model.Verificar_Conectar.ConexaoVerificacao;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Gustavo
 */
public class LeitorExcel {
   
    public void lerExcel(File file) throws SQLException, ClassNotFoundException {
         ConsumoSetor consumo = new ConsumoSetor();  
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            StringBuilder data = new StringBuilder();

              for (int i = 1; i < 10; i++) {
                int Contador = 0;
                Row row = sheet.getRow(i);
                if (row != null) {
                    
                    Cell cell = row.getCell(Contador);
                    Contador++;
                    String valorCell = cell != null ? cell.getStringCellValue() : ""; // Obtém o valor da célula como texto
                    
                    System.out.println("descricao" + valorCell);
                    
                    Cell cell1 = row.getCell(Contador);	
	            Contador++;

                    float valorFloat = cell1 != null ? (float) cell1.getNumericCellValue() : 0.0f; // Obtém o valor da célula como float
                    
                           
                    System.out.println("valor"+valorFloat); 
                    
                    Cell cell2 = row.getCell(Contador);
 		    Contador++;
                    int quantidade = cell2 != null ? (int) cell2.getNumericCellValue() : 0; // Obtém o valor da célula como int
                 
                    System.out.println("qtde"+quantidade); 
                      
                    Cell cell3 = row.getCell(Contador);
 		    Contador++;
                    Date dataInfo = null;
                    if (cell1 != null) {
                        dataInfo = cell3.getDateCellValue(); // Obtém o valor da célula como data
                    }                 
                    
                    System.out.println("data"+dataInfo);
                   
                    
                    Contador++;
                    consumo.setIndicador(valorCell, valorFloat,quantidade,dataInfo);
                    
                    
                    
                    //java.sql.Date sqldate = new java.sql.Date(dataInfo.getTime());
                    
                    
                    
                    ConexaoVerificacao con = new ConexaoVerificacao();
                    con.abrirConexao();
                    con.InsercaoValoresExcel(valorCell, valorFloat,quantidade ,dataInfo);
                    con.fecharConexao();
                    
                  }         
              }
          
            workbook.close();
            
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo Excel: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }  
        
        
    } 
    
    
    public void lerExcelParticipacao(File file) throws SQLException, ClassNotFoundException {
         ParticipacaoRenovavel participacao = new ParticipacaoRenovavel();  
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            StringBuilder data = new StringBuilder();

              for (int i = 1; i < 10; i++) {
                int Contador = 0;
                Row row = sheet.getRow(i);
                if (row != null) {
                    
                    Cell cell = row.getCell(Contador);
                    Contador++;
                    String valorCell = cell != null ? cell.getStringCellValue() : ""; // Obtém o valor da célula como texto
                    
                    System.out.println("descricao" + valorCell);
                    
                    Cell cell1 = row.getCell(Contador);	
	            Contador++;

                    float valorFloat = cell1 != null ? (float) cell1.getNumericCellValue() : 0.0f; // Obtém o valor da célula como float
                    
                           
                    System.out.println("valor"+valorFloat); 
                    
                    Cell cell2 = row.getCell(Contador);
 		    Contador++;
                    int quantidade = cell2 != null ? (int) cell2.getNumericCellValue() : 0; // Obtém o valor da célula como int
                 
                    System.out.println("qtde"+quantidade); 
                      
                    Cell cell3 = row.getCell(Contador);
 		    Contador++;
                    Date dataInfo = null;
                    if (cell1 != null) {
                        dataInfo = cell3.getDateCellValue(); // Obtém o valor da célula como data
                    }                 
                    
                    System.out.println("data"+dataInfo);
                   
                    
                    Contador++;
                    participacao.setIndicador(valorCell, valorFloat,quantidade,dataInfo);
                    
                    
                    
                    //java.sql.Date sqldate = new java.sql.Date(dataInfo.getTime());
                    
                    
                    
                    ConexaoVerificacao con = new ConexaoVerificacao();
                    con.abrirConexao();
                    con.InsercaoValoresExcelParticipacao(valorCell, valorFloat,quantidade ,dataInfo);
                    con.fecharConexao();
                    
                  }         
              }
          
            workbook.close();
            
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo Excel: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
     
    }
        
        
        
        
        public void lerExcelReducao(File file) throws SQLException, ClassNotFoundException {
         ReducaoEmissao reducao = new ReducaoEmissao();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            StringBuilder data = new StringBuilder();

              for (int i = 1; i < 10; i++) {
                int Contador = 0;
                Row row = sheet.getRow(i);
                if (row != null) {
                    
                    Cell cell = row.getCell(Contador);
                    Contador++;
                    String valorCell = cell != null ? cell.getStringCellValue() : ""; // Obtém o valor da célula como texto
                    
                    System.out.println("descricao" + valorCell);
                    
                    Cell cell1 = row.getCell(Contador);	
	            Contador++;

                    float valorFloat = cell1 != null ? (float) cell1.getNumericCellValue() : 0.0f; // Obtém o valor da célula como float
                    
                           
                    System.out.println("valor_bruto"+valorFloat); 
                    
                    Cell cell2 = row.getCell(Contador);
 		    Contador++;
                    int valor_renovavel = cell2 != null ? (int) cell2.getNumericCellValue() : 0; // Obtém o valor da célula como int
                 
                    System.out.println("valor_renovavel"+valor_renovavel); 
                      
                    Cell cell3 = row.getCell(Contador);
 		    Contador++;
                    Date dataInfo = null;
                    if (cell1 != null) {
                        dataInfo = cell3.getDateCellValue(); // Obtém o valor da célula como data
                    }                 
                    
                    System.out.println("data"+dataInfo);
                   
                    
                    Contador++;
                    reducao.setIndicador(valorCell, valorFloat,valor_renovavel,dataInfo);
                    
                    
                    
                    //java.sql.Date sqldate = new java.sql.Date(dataInfo.getTime());
                    
                    
                    
                    ConexaoVerificacao con = new ConexaoVerificacao();
                    con.abrirConexao();
                    con.InsercaoValoresExcelReducao(valorCell, valorFloat,valor_renovavel ,dataInfo);
                    con.fecharConexao();
                    
                  }         
              }
          
            workbook.close();
            
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo Excel: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    
}
}
