/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ExcelTratamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
import model.ConsumoSetor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Gustavo
 */
public class LeitorExcel {
   
    public void lerExcel(File file) {
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
                    System.out.println(valorCell);
                    Cell cell1 = row.getCell(Contador);
                    Contador++;
                    
                    Date dataInfo = null;
                    if (cell1 != null) {
                        dataInfo = cell1.getDateCellValue(); // Obtém o valor da célula como data
                    }                 
                    System.out.println(dataInfo);
                    
                    Cell cell2 = row.getCell(Contador);
                    float valorFloat = cell2 != null ? (float) cell2.getNumericCellValue() : 0.0f; // Obtém o valor da célula como float
                    Contador++;
                    System.out.println(valorFloat);        
                    consumo.setIndicador(valorCell, dataInfo, valorFloat);
                  }    
              }
          
            workbook.close();
            
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo Excel: " + e.getMessage(),
                    //"Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
