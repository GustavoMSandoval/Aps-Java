/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import model.ExcelTratamento.LeitorExcel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Gustavo
 */
public class view_ParticipacaoRenovavel extends javax.swing.JFrame {
    private Connection connection;
    /**
     * Creates new form view_ParticipacaoRenovavel
     */
    public view_ParticipacaoRenovavel() {
        initComponents();
        abrirConexao();
        //chamandos os graficos nulos pois n foi passado filtro//
        MostraGraficoParticipacao(null, new Date(),null);
        MostraGraficoParticipacaoGasto(null, new Date(),null);
        MostraGraficoParticipacaoTabela(null, new Date(),null);
        //chamandos os graficos nulos pois n foi passado filtro//
        fecharConexao();
    }
    private void MostraGraficoParticipacao(Date datainicio,Date datafim,String descricao){
        
           //vetor para armazenar os valores//
           Map<String, Integer> somaPorEquip = new LinkedHashMap<>();
           //vetor para armazenar os valores//
           
           DefaultPieDataset barDataset  = new DefaultPieDataset();  
            
           float valor = 0;
           
           //conexao//
           try {       
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();
                    
                    String query = "SELECT * FROM ideias_1 WHERE data >= ? AND data <= ? AND descricao LIKE ? ORDER BY data asc";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    // vereficando se as datas são nulas//
                    if(datainicio == null){
                       datainicio = new Date(2000 - 1900, 0, 1);
                    }
                    
                    if(datafim == null){
                        datafim = new Date();
                    }
   
                    if(descricao == null){
                        descricao = "%";
                    }
                    // vereficando se as datas são nulas//
                    
                    //setandos os valores para busca da query//
                    preparedStatement.setDate(1, new java.sql.Date(datainicio.getTime())); 
                    preparedStatement.setDate(2, new java.sql.Date(datafim.getTime()));
                    if(descricao != null){
                        preparedStatement.setString(3, "%"+descricao+"%");
                    }else{
                        preparedStatement.setString(3, descricao);
                    }
                    //setandos os valores para busca da query//
                    
                    ResultSet resultSet = preparedStatement.executeQuery();
                    
                    //looping para apresentar os dados da query//
                    while (resultSet.next()) {       
                        int userId = resultSet.getInt("id");
                        String descricaoResultado = resultSet.getString("descricao");
                        valor = resultSet.getFloat("valor");
                        int quantidade = resultSet.getInt("quantidade");
                        Date data = resultSet.getDate("data");
                        
                        //inserindo as somas no vetor//
                        if (!somaPorEquip.containsKey(descricaoResultado)) {
                            somaPorEquip.put(descricaoResultado, 0);
                        }            
                        int somaAtual = somaPorEquip.get(descricaoResultado);
                        somaPorEquip.put(descricaoResultado, somaAtual + quantidade); 
                        //inserindo as somas no vetor//
                    }
                    //looping para apresentar os dados da query//
                    
                    //looping para apresentar os valores do vetor//
                    for (Map.Entry<String, Integer> entry : somaPorEquip.entrySet()) {
                        barDataset.setValue(entry.getKey(),entry.getValue());
                    }
                    //looping para apresentar os valores do vetor//
                    
                    // Fechando recursos
                    resultSet.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
          //conexao//
          
        JFreeChart piechart = ChartFactory.createPieChart
        ("Quantidade Participação Solar", barDataset,false,true,false);
        
        PiePlot  piePlot =( PiePlot) piechart.getPlot();

        //piePlot.setBackgroundPaint(Color.white);
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: {1}", new DecimalFormat("0.0"), new DecimalFormat("0"));
        piePlot.setLabelGenerator(generator);
        ChartPanel barChartPanel = new ChartPanel(piechart);
        
        PainelParticipacao.removeAll();
        PainelParticipacao.add(barChartPanel,BorderLayout.CENTER);
        PainelParticipacao.validate();
    }
    private void MostraGraficoParticipacaoGasto(Date datainicio,Date datafim,String descricao){
        
           //vetor para armazenar os valores//
           Map<String, Float> somaPorValorEquip = new LinkedHashMap<>();
           DefaultPieDataset barDataset  = new DefaultPieDataset();  
           //vetor para armazenar os valores//
           
           float valor = 0;
           
           //conexao//
           try {       
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();
                    
                    String query = "SELECT * FROM ideias_1 WHERE data >= ? AND data <= ? AND descricao LIKE ? ORDER BY data asc";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    
                    //vereficando se as datas são nulas//
                    if(datainicio == null){
                       datainicio = new Date(2000 - 1900, 0, 1);
                    }
                    
                    if(datafim == null){
                        datafim = new Date();
                    }
   
                    if(descricao == null){
                        descricao = "%";
                    }
                    //vereficando se as datas são nulas//
                    
                    //setando valores para busca//
                    preparedStatement.setDate(1, new java.sql.Date(datainicio.getTime())); 
                    preparedStatement.setDate(2, new java.sql.Date(datafim.getTime()));
                    if(descricao != null){
                        preparedStatement.setString(3, "%"+descricao+"%");
                    }else{
                        preparedStatement.setString(3, descricao);
                    }
                    //setando valores para busca//
                    
                    ResultSet resultSet = preparedStatement.executeQuery();
                    
                    //looping do result da query//
                    while (resultSet.next()) {       
                        int userId = resultSet.getInt("id");
                        String descricaoResultado = resultSet.getString("descricao");
                        valor = resultSet.getFloat("valor");
                        int quantidade = resultSet.getInt("quantidade");
                        Date data = resultSet.getDate("data");
                        
                        //colocando os valores no vetor//
                        if (!somaPorValorEquip.containsKey(descricaoResultado)) {
                            somaPorValorEquip.put(descricaoResultado, 0.0f);
                        }            
                        float somaAtual = somaPorValorEquip.get(descricaoResultado);
                        somaPorValorEquip.put(descricaoResultado, somaAtual + (valor*quantidade));  
                        //colocando os valores no vetor//
                    }
                    //looping do result da query//
                    
                    //looping para setar dados no grafico diante o vetor//
                    for (Map.Entry<String, Float> entry : somaPorValorEquip.entrySet()) {
                        barDataset.setValue(entry.getKey(),entry.getValue());
                    }
                    //looping para setar dados no grafico diante o vetor//
                    
                    // Fechando recursos
                    resultSet.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
           //conexao//
        JFreeChart piechart = ChartFactory.createPieChart
        ("Gasto Participação Solar", barDataset,false,true,false);
        
        PiePlot  piePlot =( PiePlot) piechart.getPlot();

       
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: {1}", new DecimalFormat("0.0"), new DecimalFormat("0"));
        piePlot.setLabelGenerator(generator);
        ChartPanel barChartPanel = new ChartPanel(piechart);
        
        PainelParticipacao1.removeAll();
        PainelParticipacao1.add(barChartPanel,BorderLayout.CENTER);
        PainelParticipacao1.validate();
    }
      private void MostraGraficoParticipacaoTabela(Date datainicio,Date datafim,String descricao){
        JFrame frame = new JFrame("Tabela Participacao");
        // Criar um modelo de tabela
        DefaultTableModel model = new DefaultTableModel();
        getContentPane().setBackground(Color.WHITE);
        // Adicionar colunas ao modelo
        model.addColumn("Descricao Equipamento");
        model.addColumn("Quantidade Equipamento");
        model.addColumn("Valor Equipamento");
        
        //conexao//
        try {       
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();
                    
                    String query = "SELECT * FROM ideias_1 WHERE data >= ? AND data <= ? AND descricao LIKE ? ORDER BY data asc";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    
                    //vereficando se as datas são nulas//
                    if(datainicio == null){
                       datainicio = new Date(2000 - 1900, 0, 1);
                    }
                    
                    if(datafim == null){
                        datafim = new Date();
                    }
   
                    if(descricao == null){
                        descricao = "%";
                    }
                    //vereficando se as datas são nulas//
                    
                    //setando os valores para pesquisa da query//
                    preparedStatement.setDate(1, new java.sql.Date(datainicio.getTime())); 
                    preparedStatement.setDate(2, new java.sql.Date(datafim.getTime()));
                    
                    if(descricao != null){
                        preparedStatement.setString(3, "%"+descricao+"%");
                    }else{
                        preparedStatement.setString(3, descricao);
                    }
                    //setando os valores para pesquisa da query//
                    ResultSet resultSet = preparedStatement.executeQuery();
                    
                    //looping do resultado da query//
                    while (resultSet.next()) {       
                        int userId = resultSet.getInt("id");
                        String descricaoResultado = resultSet.getString("descricao");
                        float valor = resultSet.getFloat("valor");
                        int quantidade = resultSet.getInt("quantidade");
                        Date data = resultSet.getDate("data");  
                        model.addRow(new Object[]{descricaoResultado,quantidade,valor});
                    }
                    //looping do resultado da query//
                    // Fechando recursos
                    resultSet.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
           //conexao//

        // Adicionar linhas ao modelo (apenas um exemplo)
      

        // Criar uma tabela com o modelo
        JTable table = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        PainelParticipacao3.removeAll();
        PainelParticipacao3.add(scrollPane,BorderLayout.CENTER);
        PainelParticipacao3.validate();

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
        importarArquivo1 = new javax.swing.JToggleButton();
        campoDataInicial = new com.toedter.calendar.JDateChooser();
        campoDataFinal = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        descricaoField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        PainelParticipacao = new javax.swing.JPanel();
        PainelParticipacao1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        PainelParticipacao3 = new javax.swing.JPanel();

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

        jPanel2.setBackground(new java.awt.Color(35, 40, 45));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 985, Short.MAX_VALUE)
        );

        importarArquivo1.setText("Importar Arquivo");
        importarArquivo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarArquivo1ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(35, 40, 45));

        jLabel3.setBackground(new java.awt.Color(35, 40, 45));
        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Particapação Renovavel ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(19, 19, 19))
        );

        jButton1.setBackground(new java.awt.Color(0, 204, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEARCH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSearchParticipacao(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel7.setText("Data Inicial");

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel6.setText("Data Final");

        descricaoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descricaoFieldActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("Descrição");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(descricaoField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(importarArquivo1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(campoDataInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importarArquivo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(descricaoField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        PainelParticipacao.setBackground(new java.awt.Color(0, 0, 0));
        PainelParticipacao.setLayout(new java.awt.BorderLayout());

        PainelParticipacao1.setBackground(new java.awt.Color(0, 0, 0));
        PainelParticipacao1.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        PainelParticipacao3.setBackground(new java.awt.Color(204, 204, 255));
        PainelParticipacao3.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PainelParticipacao3, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PainelParticipacao3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PainelParticipacao1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addComponent(PainelParticipacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(PainelParticipacao, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(PainelParticipacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void importarArquivo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarArquivo1ActionPerformed
        
    }//GEN-LAST:event_importarArquivo1ActionPerformed

    private void BotaoSearchParticipacao(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSearchParticipacao
        
        abrirConexao();
         
        //chamandos os graficos com o filtro//
         MostraGraficoParticipacao(campoDataInicial.getDate(),campoDataFinal.getDate(),descricaoField.getText());
         MostraGraficoParticipacaoGasto(campoDataInicial.getDate(),campoDataFinal.getDate(),descricaoField.getText());
         //chamandos os graficos com o filtro//
         
         fecharConexao();
    }//GEN-LAST:event_BotaoSearchParticipacao

    private void descricaoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descricaoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descricaoFieldActionPerformed

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
            java.util.logging.Logger.getLogger(view_ParticipacaoRenovavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(view_ParticipacaoRenovavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(view_ParticipacaoRenovavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(view_ParticipacaoRenovavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new view_ParticipacaoRenovavel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelParticipacao;
    private javax.swing.JPanel PainelParticipacao1;
    private javax.swing.JPanel PainelParticipacao3;
    private javax.swing.JToggleButton btnFechar;
    private javax.swing.JToggleButton btnVoltar;
    private com.toedter.calendar.JDateChooser campoDataFinal;
    private com.toedter.calendar.JDateChooser campoDataInicial;
    private javax.swing.JTextField descricaoField;
    private javax.swing.JToggleButton importarArquivo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    // End of variables declaration//GEN-END:variables
}
