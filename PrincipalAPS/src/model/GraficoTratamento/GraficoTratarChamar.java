/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.GraficoTratamento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Verificar_Conectar.ConexaoVerificacao;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Gustavo
 */
public class GraficoTratarChamar {
        private String selecionarId;
        
    
        public float MostraGraficoProgresso(Date datainicio,Date datafim,JPanel PainelConsumo,Connection connection,String email){
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
                    
                    
                    
                    
                    
                    
                    
                    String query = 
                     "SELECT * FROM tabelagrafica t ,registro r WHERE t.data >= ? AND t.data <= ? and r.id = "+SelecionarID(email)+" and t.id = "+SelecionarID(email)+"  ORDER BY t.data asc ";
                    

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
      
            PainelConsumo.removeAll();
            PainelConsumo.add(barpChartPanel, BorderLayout.CENTER);
            PainelConsumo.validate();
            //criando chart :) //

            return valor;
        }
    
    public void MostraGraficoProgressoMeta(Date datafim,int meta,float valor,JPanel PainelConsumo){
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
      
            PainelConsumo.removeAll();
            PainelConsumo.add(barpChartPanel, BorderLayout.CENTER);
            PainelConsumo.validate();
           //grafico :)//
    } 
   
    
    public void MostraGraficoReducao(Date datainicio,Date datafim,Connection connection,JPanel PainelReducao,String email){
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
                    
                    String query = "SELECT * FROM tabelareducao t, registro r WHERE t.data >= ? AND t.data <= ? and r.id = "+SelecionarID(email)+" and t.id = "+SelecionarID(email)+"  ORDER BY data asc";

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
    
    
    
    
    
    
    
    
    public void MostraGraficoReducaoMeta(Date datainicio,Date datafim,int meta,int meta2,Connection connection,JPanel PainelReducao,String email){
          
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
                    
                    String query = "SELECT * FROM tabelareducao t , registro r WHERE t.data >= ? AND t.data <= ? and r.id = "+SelecionarID(email)+" and t.id = "+SelecionarID(email)+"  ORDER BY data asc";

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
            PainelReducao.removeAll();
            PainelReducao.add(chartPanel,BorderLayout.CENTER);
            PainelReducao.validate();
      }
          public void MostraGraficoParticipacao(Date datainicio,Date datafim,String descricao,Connection connection,JPanel PainelParticipacao,String email){
        
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
                    
                    String query = "SELECT * FROM tabelaparticipacao t,registro r WHERE t.data >= ? AND t.data <= ? AND t.descricao LIKE ? and r.id = "+SelecionarID(email)+" and t.id = "+SelecionarID(email)+"  ORDER BY data asc";

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
    public void MostraGraficoParticipacaoGasto(Date datainicio,Date datafim,String descricao,Connection connection,JPanel PainelParticipacao,String email){
        
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
                    
                    String query = "SELECT * FROM tabelaparticipacao t,registro r WHERE t.data >= ? AND t.data <= ? AND t.descricao LIKE ? and r.id = "+SelecionarID(email)+" and t.id = "+SelecionarID(email)+"  ORDER BY data asc";

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
        
        PainelParticipacao.removeAll();
        PainelParticipacao.add(barChartPanel,BorderLayout.CENTER);
        PainelParticipacao.validate();
    }
      public void MostraGraficoParticipacaoTabela(Date datainicio,Date datafim,String descricao,Connection connection,JPanel PainelParticipacao,String email){
        JFrame frame = new JFrame("Tabela Participacao");
        // Criar um modelo de tabela
        DefaultTableModel model = new DefaultTableModel();
        frame.getContentPane().setBackground(Color.WHITE);
        // Adicionar colunas ao modelo
        model.addColumn("Descricao Equipamento");
        model.addColumn("Quantidade Equipamento");
        model.addColumn("Valor Equipamento");
        
        //conexao//
        try {       
                if (connection != null) {
                    System.out.println("Conexão bem-sucedida!");

                    Statement statement = connection.createStatement();
                    
                    String query = "SELECT * FROM tabelaparticipacao t,registro r WHERE t.data >= ? AND t.data <= ? AND t.descricao LIKE ? and r.id = "+SelecionarID(email)+" and t.id = "+SelecionarID(email)+"  ORDER BY data asc";

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
        PainelParticipacao.removeAll();
        PainelParticipacao.add(scrollPane,BorderLayout.CENTER);
        PainelParticipacao.validate();

      }
      
    
    
        public  void MostraGraficoPizza(JPanel PainelConsumo) {
         
               
       
       
        
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
        
        PainelConsumo.removeAll();
        PainelConsumo.add(barChartPanel,BorderLayout.CENTER);
        PainelConsumo.validate();
 
      
    
}
        public void MostraGraficoBarra(JPanel PainelConsumo)
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
            PainelConsumo.removeAll();
            PainelConsumo.add(lineChartPanel,BorderLayout.CENTER);
            PainelConsumo.validate();
      
        }
        public void MostraGraficoProgresso(JPanel PainelConsumo)
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(200, "Amount", "january");
            dataset.setValue(150, "Amount", "february");
            dataset.setValue(18, "Amount", "march");
            dataset.setValue(100, "Amount", "april");
            dataset.setValue(80, "Amount", "may");
            dataset.setValue(250, "Amount", "june");

            JFreeChart chart = ChartFactory.createBarChart("contribuição","mensal","amount", 
                    dataset, PlotOrientation.VERTICAL, false,true,false);

            CategoryPlot categoryPlot = chart.getCategoryPlot();
            //categoryPlot.setRangeGridlinePaint(Color.BLUE);
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color clr3 = new Color(204,0,51);
            renderer.setSeriesPaint(0, clr3);

            ChartPanel barpChartPanel = new ChartPanel(chart);
      
            PainelConsumo.removeAll();
            PainelConsumo.add(barpChartPanel, BorderLayout.CENTER);
            PainelConsumo.validate();
      
        }
    
    public String SelecionarID(String email)
    {
        
        ConexaoVerificacao con = new ConexaoVerificacao();
        con.abrirConexao();  
        String query = "Select id From registro where email = '"+email+"' ";
        System.out.println(query);
        try {
           
           
           con.connection = DriverManager.getConnection(con.get$Url(), con.get$Host(), con.get$Pwd());
           Statement stmt = con.connection.createStatement();
           
           ResultSet rs = stmt.executeQuery(query);

           while (rs.next()) 
           {    
               
            this.setSelecionarId(rs.getString(1));
           }
           
           
        } catch (Exception e) {
            System.out.println("erro" + e);
        }

        con.fecharConexao();

       return this.getSelecionarId();
        
    }
    
    
    
    

    public String getSelecionarId() {
        return this.selecionarId;
    }

    public void setSelecionarId(String selecionarId) {
        this.selecionarId = selecionarId;
    }
    
    
    
}
