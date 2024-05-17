/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import java.awt.Color;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import model.ExcelTratamento.LeitorExcel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 *
 * @author Gustavo
 */
public class view_ReducaoEmissao extends javax.swing.JFrame {
     private Connection connection;
    /**
     * Creates new form view_ReducaoEmissao
     */
    public view_ReducaoEmissao() {
        initComponents();
        abrirConexao();
        MostraGraficoReducao(null, new Date());
        MostraGraficoReducaoMeta(null, new Date(),0,0);
        fecharConexao();
    }
     private void MostraGraficoReducao(Date datainicio,Date datafim){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
         // os array, para o valor bruto e renovavel//
         Map<Integer, Float> somaBrutoPorAno = new LinkedHashMap<>();
         Map<Integer, Float> somaRenovavelPorAno = new LinkedHashMap<>();
         // os array, para o valor bruto e renovavel//
        
         //conexao//
         try {       
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();
                    
                    String query = "SELECT * FROM ideia_2 WHERE data >= ? AND data <= ? ORDER BY data asc";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    
                    // vereficando se as datas sao nulas//
                    if(datainicio == null){
                       datainicio = new Date(2000 - 1900, 0, 1);
                    }
                    
                    if(datafim == null){
                        datafim = new Date();
                    }
                    // vereficando se as datas sao nulas//
                    
                     // colocando os valores no lugar do '?' da variavel query//
                    preparedStatement.setDate(1, new java.sql.Date(datainicio.getTime())); 
                    preparedStatement.setDate(2, new java.sql.Date(datafim.getTime()));
                    // colocando os valores no lugar do '?' da variavel query//
                    
                    ResultSet resultSet = preparedStatement.executeQuery();
                    //looping do resultado da query//
                    while (resultSet.next()) {       
                        int userId = resultSet.getInt("id");
                        String descricaoResultado = resultSet.getString("descricao");
                        float valor_bruto = resultSet.getFloat("valor_bruto");
                        float valor_renovavel = resultSet.getFloat("valor_renovavel");
                        Date data = resultSet.getDate("data");  
                        
                        //pegando o data para apenas o ano//
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(data);
                        int ano = calendar.get(Calendar.YEAR);
                        int chave = ano;                   
                        //pegando o data para apenas o ano//
                        
                        //colocando os valores brutos e renovaveis em vetores//
                        if (!somaBrutoPorAno.containsKey(chave)) {
                            somaBrutoPorAno.put(chave, 0.0f);
                        }  
                        if (!somaRenovavelPorAno.containsKey(chave)) {
                            somaRenovavelPorAno.put(chave, 0.0f);
                        } 
                        float somaAtualBruto = somaBrutoPorAno.get(chave);
                        somaBrutoPorAno.put(chave, somaAtualBruto + valor_bruto); 
                        
                        float somaAtualRenovavel = somaRenovavelPorAno.get(chave);
                        somaRenovavelPorAno.put(chave, somaAtualRenovavel + valor_renovavel);   
                        //colocando os valores brutos e renovaveis em vetores//
                        
                    }
                    //looping do resultado da query//
                    
                    //colocando os valores brutos e renovaveis nas linhas do grafico//
                     for (Map.Entry<Integer, Float> entry : somaBrutoPorAno.entrySet()) {
                       
                        dataset.addValue(entry.getValue(), "Gasto Bruto", entry.getKey());
                    }
                      for (Map.Entry<Integer, Float> entry : somaRenovavelPorAno.entrySet()) {
                       dataset.addValue(entry.getValue(), "Gasto Renovavel", entry.getKey());
                    }
                    //colocando os valores brutos e renovaveis nas linhas do grafico//

                    // Fechando recursos
                    resultSet.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
           //conexao//

            // Criando o gráfico de linha
            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Comparativo dos Gastos",
                    "Mês",
                    "Valor",
                    dataset
            );

            // Obtendo o plot do gráfico
            CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();

            // Configurando o fundo do plot
            //lineCategoryPlot.setBackgroundPaint(Color.white);

            // Criando um objeto render para mostrar o gráfico
            LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();

            // Configurando as cores para cada série
            lineRenderer.setSeriesPaint(0, Color.RED); // Cor para a série "Gastos"
            lineRenderer.setSeriesPaint(1, Color.GREEN); // Cor para a série "Lucro"

            // Criando um painel para o gráfico
            ChartPanel lineChartPanel = new ChartPanel(lineChart);
            PainelReducao.removeAll();
            PainelReducao.add(lineChartPanel,BorderLayout.CENTER);
            PainelReducao.validate();
     }
     
      private void MostraGraficoReducaoMeta(Date datainicio,Date datafim,int meta,int meta2){
          
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            //aplicando os vetores//
            Map<Integer, Float> somaBrutoPorAno = new LinkedHashMap<>();
            Map<Integer, Float> somaMetaPorAno = new LinkedHashMap<>();
             //aplicando os vetores//
             
         //conexao//
         try {   
                
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();
                    
                    String query = "SELECT * FROM ideia_2 WHERE data >= ? AND data <= ? ORDER BY data asc";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    
                    //vereficando se as datas são nulas//
                    if(datainicio == null){
                       datainicio = new Date(2000 - 1900, 0, 1);
                    }
                    
                    if(datafim == null){
                        datafim = new Date();
                    }
                    //vereficando se as datas são nulas//
                    
                    //aplicando busca//
                    preparedStatement.setDate(1, new java.sql.Date(datainicio.getTime())); 
                    preparedStatement.setDate(2, new java.sql.Date(datafim.getTime()));
                     //aplicando busca//
                     
                    ResultSet resultSet = preparedStatement.executeQuery();
                    
                    //looping do resultado da query//
                    while (resultSet.next()) {       
                        int userId = resultSet.getInt("id");
                        String descricaoResultado = resultSet.getString("descricao");
                        float valor_bruto = resultSet.getFloat("valor_bruto");
                        float valor_renovavel = resultSet.getFloat("valor_renovavel");
                        Date data = resultSet.getDate("data");  
                        
                        //data pegando para formar apenas o ano//
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(data);
                        int ano = calendar.get(Calendar.YEAR);
                        //int mes = calendar.get(Calendar.MONTH) + 1; 
                        int chave = ano;
                         //data pegando para formar apenas o ano//

                        //setando as info nos vetores//
                        if (!somaMetaPorAno.containsKey(chave)) {
                            somaMetaPorAno.put(chave, 0.0f);
                        }  
                        if (!somaBrutoPorAno.containsKey(chave)) {
                            somaBrutoPorAno.put(chave, 0.0f);
                        } 
                        
                        
                        
                        
                         // Calculando o desconto
                        float metaDecimal_1 = meta2 / 100.0f;
                        
                        float metaDecima_2 = meta / 100.0f;  
                        
                        float desconto_1 = valor_renovavel * metaDecimal_1; 
                        float somaAtualMeta = somaMetaPorAno.get(chave);
                        somaMetaPorAno.put(chave, somaAtualMeta + valor_renovavel+desconto_1); 
                        
                        float desconto_2 = valor_bruto * metaDecima_2;
                        float somaAtualBruto = somaBrutoPorAno.get(chave);
                        somaBrutoPorAno.put(chave, somaAtualBruto + valor_bruto-desconto_2);   
                      // Calculando o desconto
                    }
                    //looping do resultado da query//
                   
                    //loopings para inserir os valores do array nos graficos//
                    for (Map.Entry<Integer, Float> entry : somaMetaPorAno.entrySet()) { 
                         dataset.addValue(entry.getValue(), "Aumento Renovavel", entry.getKey());
                    }
                    for (Map.Entry<Integer, Float> entry : somaBrutoPorAno.entrySet()) {
                      dataset.addValue(entry.getValue(), "Gasto Não Renovavel", entry.getKey());
                        
                    }
                    //loopings para inserir os valores do array nos graficos//
                    // Fechando recursos
                    resultSet.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
           //conexao//
           

            // Criação do gráfico de pirâmide populacional
            JFreeChart chart = ChartFactory.createStackedBarChart(
                    "Relação anual de Gastos / Meta", // Título do gráfico
                    "Ano", // Rótulo do eixo X
                    "Valor Total", // Rótulo do eixo Y
                    dataset,
                    PlotOrientation.VERTICAL, // Orientação do gráfico
                    true, // Incluir legenda
                    true, // Incluir tooltips
                    false // Incluir URLs
            );

          
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.GREEN); // Série renovavel
            renderer.setSeriesPaint(1, Color.RED); // Série nao renovavel

            ChartPanel chartPanel = new ChartPanel(chart);
            PainelReducao1.removeAll();
            PainelReducao1.add(chartPanel,BorderLayout.CENTER);
            PainelReducao1.validate();
      }
    private void abrirConexao() {
            try {
            String hostname = "177.53.140.227";
            String username = "wtooltestebrscom";
            String password = "de8#$Ul%bpJ2";
            String database = "wtooltestebrscom_estacionamento";
            String url = "jdbc:mysql://" + hostname + "/" + database;
            connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                // Trate qualquer exceção de conexão aqui
                e.printStackTrace();
            }
    }
    
    private void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Trate qualquer exceção de fechamento de conexão aqui
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnFechar = new javax.swing.JToggleButton();
        btnVoltar = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        importarArquivo = new javax.swing.JToggleButton();
        metaField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        metaField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        PainelReducao = new javax.swing.JPanel();
        PainelReducao1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1930, 1023));

        jPanel3.setBackground(new java.awt.Color(35, 40, 45));

        btnFechar.setBackground(new java.awt.Color(35, 40, 45));
        btnFechar.setForeground(new java.awt.Color(204, 204, 204));
        btnFechar.setText("Fechar");
        btnFechar.setBorder(null);
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(35, 40, 45));
        btnVoltar.setForeground(new java.awt.Color(204, 204, 204));
        btnVoltar.setText("Voltar");
        btnVoltar.setBorder(null);
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVoltar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFechar)
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFechar, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(35, 40, 45));

        jLabel3.setBackground(new java.awt.Color(35, 40, 45));
        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Redução de Emissão");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jDateChooser2.setMinimumSize(new java.awt.Dimension(82, 31));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel6.setText("Data Final");

        jDateChooser1.setMinimumSize(new java.awt.Dimension(82, 31));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel7.setText("Data Inicial");

        jButton4.setBackground(new java.awt.Color(0, 204, 102));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("SEARCH");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSearchFiltro(evt);
            }
        });

        importarArquivo.setText("Importar Arquivo");
        importarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarArquivoActionPerformed(evt);
            }
        });

        metaField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metaField2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("Meta Renovavel (%)");

        metaField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metaField1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("Meta Não Renovavel (%)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(metaField1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(metaField2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(importarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(importarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(metaField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(metaField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );

        jPanel6.setBackground(new java.awt.Color(35, 40, 45));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 979, Short.MAX_VALUE)
        );

        PainelReducao.setBackground(new java.awt.Color(0, 0, 0));
        PainelReducao.setLayout(new java.awt.BorderLayout());

        PainelReducao1.setBackground(new java.awt.Color(0, 0, 0));
        PainelReducao1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PainelReducao, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(PainelReducao1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PainelReducao1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addComponent(PainelReducao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void importarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarArquivoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos Excel", "xls", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            LeitorExcel leitor = new LeitorExcel();
            try {
                leitor.lerExcel(file);
            } catch (Exception ex) {
                Logger.getLogger(view_ReducaoEmissao.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
    }//GEN-LAST:event_importarArquivoActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // Voltar para Home
        Home HomeFrame = new Home(); // Após cadastro será redirecionado para Home
        HomeFrame.setVisible(true);
        HomeFrame.pack();
        HomeFrame.setLocationRelativeTo(null);
        HomeFrame.setExtendedState(MAXIMIZED_BOTH);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // botão de encerrar o programa
        System.exit(0);
    }//GEN-LAST:event_btnFecharActionPerformed

    private void botaoSearchFiltro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSearchFiltro
         abrirConexao();
        
         //chamando graficos com os filtros//
         MostraGraficoReducao(jDateChooser2.getDate(),jDateChooser1.getDate()); 
         //chamando graficos com os filtros//
         
         //vereficando se as metas passadas são nulas//
         String meta = metaField1.getText();
         if (meta == null || meta.trim().isEmpty()) {
            meta = "0";
        }
         String meta2 = metaField2.getText();
        if (meta2 == null || meta2.trim().isEmpty()) {
            meta2 = "0";
        }    
        int numeroMeta = Integer.parseInt(meta);
        int numeroMeta2 = Integer.parseInt(meta2);
        //vereficando se as metas passadas são nulas//
        
        //chamando graficos com os filtros//
         MostraGraficoReducaoMeta(jDateChooser2.getDate(),jDateChooser1.getDate(),numeroMeta,numeroMeta2);
        //chamando graficos com os filtros//
        
         fecharConexao();
    }//GEN-LAST:event_botaoSearchFiltro

    private void metaField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metaField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_metaField2ActionPerformed

    private void metaField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metaField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_metaField1ActionPerformed

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
            java.util.logging.Logger.getLogger(view_ReducaoEmissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(view_ReducaoEmissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(view_ReducaoEmissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(view_ReducaoEmissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new view_ReducaoEmissao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelReducao;
    private javax.swing.JPanel PainelReducao1;
    private javax.swing.JToggleButton btnFechar;
    private javax.swing.JToggleButton btnVoltar;
    private javax.swing.JToggleButton importarArquivo;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField metaField1;
    private javax.swing.JTextField metaField2;
    // End of variables declaration//GEN-END:variables
}
