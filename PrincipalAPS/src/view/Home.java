/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import model.ConsumoSetor;
//import model.Graficos.Graficos;
import model.Verificar_Conectar.ConexaoVerificacao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import view.*;

/**
 *
 * @author Gustavo
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        MostraGraficoPizza();
        MostraGraficoBarra();
        MostraGraficoProgresso();
        
    }
        private  void MostraGraficoPizza() {
         
        ConsumoSetor consumoSetor = new ConsumoSetor();
        
        DefaultPieDataset barDataset  = new DefaultPieDataset();
        barDataset.setValue("Consumo", new Float(8.0f));
        barDataset.setValue("Economia", new Float(13.0f));
        barDataset.setValue("Gastos", new Float(29.0f));
       
        
        JFreeChart piechart = ChartFactory.createPieChart
        ("Consumo", barDataset,false,true,false);
        
        PiePlot  piePlot =( PiePlot) piechart.getPlot();
        
        piePlot.setSectionPaint("Consumo", new Color(0,102,102));
        piePlot.setSectionPaint("Economia", new Color(255,0,0));
        piePlot.setSectionPaint("Gastos", new Color(255,255,0));
        
        
        piePlot.setBackgroundPaint(Color.white);
        
        ChartPanel barChartPanel = new ChartPanel(piechart);
        
        painelGrafico2.removeAll();
        painelGrafico2.add(barChartPanel,BorderLayout.CENTER);
        painelGrafico2.validate();
 
      
    
}
        private void MostraGraficoBarra()
        {
            //cria data set
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(289,"Gerado","Consumo");
            dataset.setValue(235,"Gerado","Economia");
            dataset.setValue(456,"Gerado","Gastos");
            
            //cria chart
            JFreeChart lineChart = ChartFactory.createLineChart
        ("Produção ao mês","mês","resultado",dataset,PlotOrientation.VERTICAL,false,true,false);
            
            //cria plot object
            CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();
            
            lineCategoryPlot.setBackgroundPaint(Color.white);
            
            
            //cria objeto render para mostrar o chart
            LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
            Color lineChartColor = new Color(204,0,51);
            lineRenderer.setSeriesPaint(0, lineChartColor);
            
            ChartPanel lineChartPanel = new ChartPanel(lineChart);
            painelGrafico1.removeAll();
            painelGrafico1.add(lineChartPanel,BorderLayout.CENTER);
            painelGrafico1.validate();
      
        }
        private void MostraGraficoProgresso()
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(200, "Amount", "january");
            dataset.setValue(150, "Amount", "february");
            dataset.setValue(18, "Amount", "march");
            dataset.setValue(100, "Amount", "april");
            dataset.setValue(80, "Amount", "may");
            dataset.setValue(250, "Amount", "june");

            JFreeChart chart = ChartFactory.createBarChart("contribution","monthly","amount", 
                    dataset, PlotOrientation.VERTICAL, false,true,false);

            CategoryPlot categoryPlot = chart.getCategoryPlot();
            //categoryPlot.setRangeGridlinePaint(Color.BLUE);
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color clr3 = new Color(204,0,51);
            renderer.setSeriesPaint(0, clr3);

            ChartPanel barpChartPanel = new ChartPanel(chart);
      
            painelGrafico4.removeAll();
            painelGrafico4.add(barpChartPanel, BorderLayout.CENTER);
            painelGrafico4.validate();
      
        }
        
        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        parteSuperior = new javax.swing.JPanel();
        btnFechar = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        usuario = new javax.swing.JLabel();
        ola = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        usuarioHome = new javax.swing.JToggleButton();
        consumoHome = new javax.swing.JToggleButton();
        participacaoHome = new javax.swing.JToggleButton();
        reducaoHome = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        painelGrafico1 = new javax.swing.JPanel();
        painelGrafico4 = new javax.swing.JPanel();
        painelGrafico2 = new javax.swing.JPanel();
        painelGrafico3 = new javax.swing.JPanel();

        jToggleButton1.setText("oiiii");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1930, 1023));

        parteSuperior.setBackground(new java.awt.Color(35, 40, 45));
        parteSuperior.setPreferredSize(new java.awt.Dimension(800, 100));

        btnFechar.setBackground(new java.awt.Color(35, 40, 45));
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete_32px.png"))); // NOI18N
        btnFechar.setBorder(null);
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Home");

        usuario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        usuario.setForeground(new java.awt.Color(204, 204, 204));
        usuario.setText("nome");

        ola.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        ola.setForeground(new java.awt.Color(204, 204, 204));
        ola.setText("Olá");

        javax.swing.GroupLayout parteSuperiorLayout = new javax.swing.GroupLayout(parteSuperior);
        parteSuperior.setLayout(parteSuperiorLayout);
        parteSuperiorLayout.setHorizontalGroup(
            parteSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parteSuperiorLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ola, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnFechar)
                .addGap(23, 23, 23))
        );
        parteSuperiorLayout.setVerticalGroup(
            parteSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parteSuperiorLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(parteSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ola, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(parteSuperiorLayout.createSequentialGroup()
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(35, 40, 45));

        usuarioHome.setBackground(new java.awt.Color(35, 40, 45));
        usuarioHome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        usuarioHome.setForeground(new java.awt.Color(204, 204, 204));
        usuarioHome.setText("Usuário");
        usuarioHome.setBorder(null);
        usuarioHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioHomeActionPerformed(evt);
            }
        });

        consumoHome.setBackground(new java.awt.Color(35, 40, 45));
        consumoHome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        consumoHome.setForeground(new java.awt.Color(204, 204, 204));
        consumoHome.setText("Consumo");
        consumoHome.setBorder(null);
        consumoHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumoHomeActionPerformed(evt);
            }
        });

        participacaoHome.setBackground(new java.awt.Color(35, 40, 45));
        participacaoHome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        participacaoHome.setForeground(new java.awt.Color(204, 204, 204));
        participacaoHome.setText("Participação");
        participacaoHome.setBorder(null);
        participacaoHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                participacaoHomeActionPerformed(evt);
            }
        });

        reducaoHome.setBackground(new java.awt.Color(35, 40, 45));
        reducaoHome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        reducaoHome.setForeground(new java.awt.Color(204, 204, 204));
        reducaoHome.setText("Redução");
        reducaoHome.setBorder(null);
        reducaoHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reducaoHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usuarioHome)
                    .addComponent(reducaoHome)
                    .addComponent(participacaoHome)
                    .addComponent(consumoHome))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(usuarioHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(consumoHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(participacaoHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(reducaoHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(532, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelGrafico1.setPreferredSize(new java.awt.Dimension(400, 400));
        painelGrafico1.setLayout(new java.awt.BorderLayout());
        jPanel2.add(painelGrafico1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 370, 440));

        painelGrafico4.setPreferredSize(new java.awt.Dimension(400, 400));
        painelGrafico4.setLayout(new java.awt.BorderLayout());
        jPanel2.add(painelGrafico4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 950, 380));

        painelGrafico2.setPreferredSize(new java.awt.Dimension(400, 400));
        painelGrafico2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(painelGrafico2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 430, 390));

        painelGrafico3.setPreferredSize(new java.awt.Dimension(400, 400));
        painelGrafico3.setLayout(new java.awt.BorderLayout());
        jPanel2.add(painelGrafico3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 50, 530, 810));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parteSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, 1930, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1715, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(parteSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1930, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    
    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // botão de encerrar o programa
        System.exit(0);
    }//GEN-LAST:event_btnFecharActionPerformed

    private void consumoHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumoHomeActionPerformed
        try {
        view_ConsumoSetor view_ConsumoSetorFrame = new view_ConsumoSetor();
        view_ConsumoSetorFrame.setVisible(true); 
        view_ConsumoSetorFrame.pack();
        view_ConsumoSetorFrame.setLocationRelativeTo(null);
        view_ConsumoSetorFrame.setExtendedState(MAXIMIZED_BOTH);
        this.dispose();
        }catch(Exception e) {System.out.println(e);}
    }//GEN-LAST:event_consumoHomeActionPerformed

    private void participacaoHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_participacaoHomeActionPerformed
        try {
        view_ParticipacaoRenovavel view_ParticipacaoRenovavelFrame = new view_ParticipacaoRenovavel();
        view_ParticipacaoRenovavelFrame.setVisible(true);
        view_ParticipacaoRenovavelFrame.pack();
        view_ParticipacaoRenovavelFrame.setLocationRelativeTo(null);
        view_ParticipacaoRenovavelFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.dispose();
        }catch(Exception e) {System.out.println(e);}
                          
    }//GEN-LAST:event_participacaoHomeActionPerformed

    private void reducaoHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reducaoHomeActionPerformed
        try {
        view_ReducaoEmissao view_ReducaoEmissaoFrame = new view_ReducaoEmissao();
        view_ReducaoEmissaoFrame.setVisible(true);
        view_ReducaoEmissaoFrame.pack();
        view_ReducaoEmissaoFrame.setLocationRelativeTo(null);
        view_ReducaoEmissaoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.dispose();
        }catch(Exception e) {System.out.println(e);}
                          
    }//GEN-LAST:event_reducaoHomeActionPerformed

    private void usuarioHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioHomeActionPerformed
        try {
        view_Usuario view_UsuarioFrame = new view_Usuario();
        view_UsuarioFrame.setVisible(true);
        view_UsuarioFrame.pack();
        view_UsuarioFrame.setLocationRelativeTo(null);
        view_UsuarioFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.dispose();
        }catch(Exception e) {System.out.println(e);}
    }//GEN-LAST:event_usuarioHomeActionPerformed
     
    public void usuario(String nome)
    {
        this.usuario.setText(nome);
    }
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnFechar;
    private javax.swing.JToggleButton consumoHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel ola;
    private javax.swing.JPanel painelGrafico1;
    private javax.swing.JPanel painelGrafico2;
    private javax.swing.JPanel painelGrafico3;
    private javax.swing.JPanel painelGrafico4;
    private javax.swing.JPanel parteSuperior;
    private javax.swing.JToggleButton participacaoHome;
    private javax.swing.JToggleButton reducaoHome;
    private javax.swing.JLabel usuario;
    private javax.swing.JToggleButton usuarioHome;
    // End of variables declaration//GEN-END:variables
}
