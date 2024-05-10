/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Graficos;

import java.awt.BorderLayout;
import java.awt.Color;
import model.ConsumoSetor;
import model.Verificar_Conectar.ConexaoVerificacao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Gustavo
 */
public class Graficos {
    
      private  void MostraGrafico() {
         
        ConsumoSetor consumoSetor = new ConsumoSetor();
        
        DefaultPieDataset barDataset  = new DefaultPieDataset();
        barDataset.setValue("Consumo", new Float(consumoSetor.getValor_registro()));
       
        
        JFreeChart piechart = ChartFactory.createPieChart
        ("Consumo", barDataset,false,true,false);
        
        PiePlot  piePlot =( PiePlot) piechart.getPlot();
        
        piePlot.setSectionPaint("Iphone", new Color(0,102,102));
        
        
        piePlot.setBackgroundPaint(Color.white);
        
        ChartPanel barChartPanel = new ChartPanel(piechart);
        
        painelGrafico.removeAll();
        painelGrafico.add(barChartPanel,BorderLayout.CENTER);
        painelGrafico.validate();
      
    
}
}
