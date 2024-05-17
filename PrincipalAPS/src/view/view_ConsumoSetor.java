/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import model.ExcelTratamento.LeitorExcel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.LinkedHashMap;      
import java.util.Map;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author Gustavo
 */
public class view_ConsumoSetor extends javax.swing.JFrame {
    private Connection connection;
    /**
     * Creates new form view_ConsumoSetor
     */
    public view_ConsumoSetor() {
        
        initComponents();   
        setExtendedState(MAXIMIZED_BOTH);
        
        //chamar grafico e abrir conexão//
        abrirConexao();
        MostraGraficoProgresso(null, new Date());
        MostraGraficoProgressoMeta(new Date(),0,0);
        fecharConexao();
        //chamar grafico e abrir conexão//
        
    }
    private float MostraGraficoProgresso(Date datainicio,Date datafim){
        //tipo de libray que é igual array, com essa variavel eu coloco as infos para usar na tabela//
           Map<Integer, Float> somaPorAno = new LinkedHashMap<>();
        //tipo de libray que é igual array, com essa variavel eu coloco as infos para usar na tabela//
            
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();
           
           //criei essa variavel pq preciso da return nela//
           float valor = 0;
           //criei essa variavel pq preciso da return nela//
           
           //conexao inicio//
           try {       
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();

                    String query = "SELECT * FROM tabela_teste WHERE data >= ? AND data <= ? ORDER BY data asc";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    //vereficar se foi passado a dataInicio//
                    if(datainicio == null){
                       datainicio = new Date(2000 - 1900, 0, 1);
                       datafim = new Date();
                    }
                   //vereficar se foi passado a dataInicio//
                   
                   // colocando os valores no lugar do '?' da variavel query//
                   preparedStatement.setDate(1, new java.sql.Date(datainicio.getTime())); 
                   preparedStatement.setDate(2, new java.sql.Date(datafim.getTime()));
                   // colocando os valores no lugar do '?' da variavel query//
                   
                    ResultSet resultSet = preparedStatement.executeQuery();
                    //looping que fica pegando por ordem os valores que veio da query//
                    while (resultSet.next()) {
                        //pegando cada campo que veio da tabela//
                        int Id = resultSet.getInt("id");
                        String descricao = resultSet.getString("descricao");
                        valor = resultSet.getFloat("valor");
                        Date data = resultSet.getDate("data");
                        //pegando cada campo que veio da tabela//
                        
                        //transformar a variavel date para pegar apenas o ano em formatado de Int//
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(data);
                        int ano = calendar.get(Calendar.YEAR);
                        //int mes = calendar.get(Calendar.MONTH) + 1; 
                        int chave = ano;
                        //transformar a variavel date para pegar apenas o ano em formatado de Int//
                        
                        //verefica se já existe o vetor somaPorAno na posicao, senao inserir com posicao ano com o valor//
                        if (!somaPorAno.containsKey(chave)) {
                            somaPorAno.put(chave, 0.0f);
                        }            
                        float somaAtual = somaPorAno.get(chave);
                        somaPorAno.put(chave, somaAtual + valor);  
                        //verefica se já existe o vetor somaPorAno na posicao, senao inserir com posicao ano com o valor//
                    }
                    //looping que fica pegando por ordem os valores que veio da query//
                    
                    //looping que fica rodanda cada posicao da variavel somaPorAno trazendo ano -- valor -- valor//
                    for (Map.Entry<Integer, Float> entry : somaPorAno.entrySet()) {
                        dataset.setValue(entry.getValue(), "Valor", entry.getKey());
                    }
                    //looping que fica rodanda cada posicao da variavel somaPorAno trazendo ano -- valor -- valor//
                    
                    // Fechando recursos
                    resultSet.close();
                    preparedStatement.close();
                    // Fechando recursos
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
           
            //criando chart :) //
            JFreeChart chart = ChartFactory.createBarChart("Consumo do Setor","mes/ano","Gasto kw", dataset, PlotOrientation.VERTICAL, false,true,false);
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            //categoryPlot.setRangeGridlinePaint(Color.BLUE);
            //categoryPlot.setBackgroundPaint(java.awt.Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            java.awt.Color clr3 = new java.awt.Color(204,0,51);
            renderer.setSeriesPaint(0, clr3);
            CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.0"));
            renderer.setBaseItemLabelGenerator(generator);
            renderer.setBaseItemLabelsVisible(true);
            
            ChartPanel barpChartPanel = new ChartPanel(chart);
      
            PainelConsumo1.removeAll();
            PainelConsumo1.add(barpChartPanel, BorderLayout.CENTER);
            PainelConsumo1.validate();
            //criando chart :) //

            return valor;
        }
    
    private void MostraGraficoProgressoMeta(Date datafim,int meta,float valor){
            //tipo de libray que é igual array, com essa variavel eu coloco as infos para usar na tabela//
            Map<Integer, Float> somaPorAno = new LinkedHashMap<>();
            //tipo de libray que é igual array, com essa variavel eu coloco as infos para usar na tabela//
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
           
            //verificando se foi passado o valor anterior, precisamos desse valor para dar a previsao dos anos baseado no ano anterior//
            if(valor != 0){
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                // Obtendo o ano da data
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(datafim);
                int ano = calendar.get(Calendar.YEAR);
                // Obtendo o ano da data
                
                // looping para fazer calculo para os proximos 5 anos baseado na meta e valor//
                int contador = 0;          
                while (contador < 6) {
                    // Calculando o valor com meta//
                    float metaDecimal = meta / 100.0f;
                    
                    float desconto = valor * metaDecimal;
                    
                    valor = valor - desconto;
                    // Calculando o valor com meta//
                    ano++;  
                    
                    //adicionando no vetor//
                    if (!somaPorAno.containsKey(ano)) {
                            somaPorAno.put(ano, 0.0f);
                    }            
                    float somaAtual = somaPorAno.get(ano);
                    somaPorAno.put(ano,valor);  
                    //adicionando no vetor//
                    contador++;
                }
                // looping para fazer calculo para os proximos 5 anos baseado na meta e valor//
                
                //colocando os valores na dataset do grafico//
                 for (Map.Entry<Integer, Float> entry : somaPorAno.entrySet()) {
                        dataset.setValue(entry.getValue(), "Valor", entry.getKey());
                }
                //colocando os valores na dataset do grafico//
                
            }else{
                 dataset.setValue(0, "Valor", "2024");
            }   
           //verificando se foi passado o valor anterior, precisamos desse valor para dar a previsao dos anos baseado no ano anterior//
           
            //grafico :)//
            JFreeChart chart = ChartFactory.createBarChart("Consumo do Setor","mes/ano","Gasto kw", dataset, PlotOrientation.VERTICAL, false,true,false);

            CategoryPlot categoryPlot = chart.getCategoryPlot();
            //categoryPlot.setRangeGridlinePaint(Color.BLUE);
            //categoryPlot.setBackgroundPaint(java.awt.Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            
            java.awt.Color clr3 = new java.awt.Color(64, 255, 51);
            renderer.setSeriesPaint(0, clr3);
            
           CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.0"));
           renderer.setBaseItemLabelGenerator(generator);
           renderer.setBaseItemLabelsVisible(true);
            
            ChartPanel barpChartPanel = new ChartPanel(chart);
      
            PainelConsumo3.removeAll();
            PainelConsumo3.add(barpChartPanel, BorderLayout.CENTER);
            PainelConsumo3.validate();
           //grafico :)//
    }
    private void abrirConexao() {
            try {
            String hostname = "";
            String username = "";
            String password = "";
            String database = "";
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnFechar = new javax.swing.JToggleButton();
        btnVoltar = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        campoDataFinal = new com.toedter.calendar.JDateChooser();
        campoDataInicial = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        importarArquivo = new javax.swing.JToggleButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoMeta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        PainelConsumo1 = new javax.swing.JPanel();
        PainelConsumo3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 243, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(1000, 1000));

        jPanel1.setBackground(new java.awt.Color(35, 40, 45));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));

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
                .addContainerGap(1831, Short.MAX_VALUE)
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1930, 1023));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        campoDataFinal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        campoDataInicial.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButton1.setBackground(new java.awt.Color(0, 204, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEARCH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesquisarConsumoBotao(evt);
            }
        });

        importarArquivo.setText("Importar Arquivo");
        importarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarArquivoActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(35, 40, 45));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consumo por Setor");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("Data Inicial");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setText("Data Final");

        campoMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoMetaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel4.setText("Meta(%)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoMeta, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(342, 342, 342)
                .addComponent(importarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(importarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoMeta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(campoDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        PainelConsumo1.setBackground(new java.awt.Color(0, 0, 0));
        PainelConsumo1.setLayout(new java.awt.BorderLayout());

        PainelConsumo3.setBackground(new java.awt.Color(0, 0, 0));
        PainelConsumo3.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PainelConsumo1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PainelConsumo3, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PainelConsumo3, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PainelConsumo1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(35, 40, 45));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1930, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void importarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarArquivoActionPerformed
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setDialogTitle("Selecione o arquivo Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos Excel", "xls", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            LeitorExcel leitor = new LeitorExcel();
            try {
                leitor.lerExcel(file);
            } catch(Exception ex) {
                Logger.getLogger(view_ConsumoSetor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_importarArquivoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // botão de encerrar o programa
        System.exit(0);
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // Voltar para Home
        Home HomeFrame = new Home(); // Após cadastro será redirecionado para Home
        HomeFrame.setVisible(true);
        HomeFrame.pack();
        HomeFrame.setLocationRelativeTo(null);
        HomeFrame.setExtendedState(MAXIMIZED_BOTH);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void PesquisarConsumoBotao(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesquisarConsumoBotao
         
        abrirConexao();
        
         //criando variavel//
         float valorRespostaGrafico1 = 0;
         //criando variavel//
         
         //chamando grafico dnv com os parametros do filtro//
         valorRespostaGrafico1 = MostraGraficoProgresso(campoDataInicial.getDate(),campoDataFinal.getDate());
         //chamando grafico dnv com os parametros do filtro//
         
         //pegando valor Meta//
         String meta = campoMeta.getText();
         int numeroMeta = Integer.parseInt(meta);
         //pegando valor Meta//
         
         //chamando grafico meta dnv com os parametros do filtro//
         MostraGraficoProgressoMeta(campoDataFinal.getDate(),numeroMeta,valorRespostaGrafico1);
         //chamando grafico meta dnv com os parametros do filtro//
         
         fecharConexao();
         
         
    }//GEN-LAST:event_PesquisarConsumoBotao

    private void campoMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoMetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoMetaActionPerformed

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
            java.util.logging.Logger.getLogger(view_ConsumoSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(view_ConsumoSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(view_ConsumoSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(view_ConsumoSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new view_ConsumoSetor().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelConsumo1;
    private javax.swing.JPanel PainelConsumo3;
    private javax.swing.JToggleButton btnFechar;
    private javax.swing.JToggleButton btnVoltar;
    private com.toedter.calendar.JDateChooser campoDataFinal;
    private com.toedter.calendar.JDateChooser campoDataInicial;
    private javax.swing.JTextField campoMeta;
    private javax.swing.JToggleButton importarArquivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    // End of variables declaration//GEN-END:variables
}
