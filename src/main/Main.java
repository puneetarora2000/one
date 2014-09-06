package main;

import indexers.*;
 import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import Jama.Matrix;
import delphi.MultiRaterAgreementTest;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.DelphiStudy;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.FleissKappaAgreement;
import indexers.IdfIndexer;
import indexers.TfIndexer;
import indexers.VectorGenerator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import moreUtils.PrintUtil;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;
import similarity.CosineSimilarity;
import similarity.JaccardSimilarity;
import similarity.Searcher;
import similarity.Searcher.SearchResult;


public class Main {



    private static VectorGenerator vectorGenerator;
    private  static Map<String,Reader> documents;
  

  //private VectorGenerator vectorGenerator;    
    
    
    
    
    
     
    
    
    

    public  static void setUp() throws Exception {

       PrintUtil.printStar();
        
        
        
    vectorGenerator = new VectorGenerator();
    documents = new LinkedHashMap<String,Reader>();
    BufferedReader reader = new BufferedReader(
      new FileReader("D:\\Dropbox\\sandeep2\\2.txt"));
    
    PrintUtil.printMsg("Reading Document Repository");
    
    
    String line = null;
    while ((line = reader.readLine()) != null) {
    
        String[] docTitleParts = StringUtils.split(line, ";");
        documents.put(docTitleParts[0], new StringReader(docTitleParts[1]));
      
      System.out.println(docTitleParts[0]+ " "+ docTitleParts[1] );
    }
    
    PrintUtil.printMsg("End of Setup");
    PrintUtil.printStar();
       
    
  }
    
    
    public static void  VectorGeneration() throws Exception {
   
            PrintUtil.printStar();
   
        vectorGenerator.generateVector(documents);
    
    prettyPrintMatrix("Term Frequencies", vectorGenerator.getMatrix(), 
      vectorGenerator.getDocumentNames(), vectorGenerator.getWords(), 
      new PrintWriter(System.out, true));
  
    
    PrintUtil.printMsg("End of Vector Generation of Text Tokens");
    PrintUtil.printStar();
    
    
    }
  
    
    
    private static Matrix BuidAdjMatrix(Matrix matrix,PrintWriter writer){
    // Sandeep :Always Check the number of Documents and Words here 
        
        
        int NoofDocuments = 3;
   
        System.out.println("===  Starting Buiding Adjacent  Matrix====================");
        double[][] vals = new double[NoofDocuments][NoofDocuments];
       
        Matrix a = new Matrix(vals);
        
        
    
        for (int i = 0;i<NoofDocuments;i++){
            for(int j = 0;j<NoofDocuments;j++){
         
                
                if (matrix.get(i, j)>0)
                {
                    
                 a.set(i, j, 1);
                
                        writer.printf("%8.4f", a.get(i, j));
                }else{
                
                writer.printf("%8.4f", a.get(i, j));
                
                }
         
                 }
        
        
        }
        
        PrintUtil.printMsg("End of Adj Matrix Building");
        PrintUtil.printStar();
    
        
        return a ;
    
    }
    
    
    
    public static void RunJaccardSimilarityWithTfIdfVector() throws Exception {
     
        PrintUtil.printStar();
        PrintUtil.printMsg("Similarity Matrix Building ");
        
        
        
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
         IdfIndexer indexer = new IdfIndexer();
         PrintUtil.printMsg("Getting Idf Indexer ");
        
         double[][] matrixData = vectorGenerator.getMatrix().getArray();
         PrintUtil.printMsg("Getting Vector Matrix  ");
        
         
         RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
        RealMatrix termDocMatrix =  indexer.transform(m);
        RealMatrix similarity = jaccardSimilarity.transform(termDocMatrix);
            PrintUtil.printMsg("Getting Transformed Similarity Matrix");
        
        prettyPrintRealMatrix("Final Similarity  Matrix  (TF/IDF)", similarity, 
       vectorGenerator.getDocumentNames(), new PrintWriter(System.out, true));
    
    
       PrintUtil.printMsg("End of Similarity Indexes Building");
       PrintUtil.printStar();
  }
    
    
    
    private  static double[] Agreements(Matrix matrix, 
            String[] documentNames, String[] words) {
    
        double agreement[] = new double[documentNames.length] ;
        
        double  costs[][]  = new double[documentNames.length][words.length];
        
        for(int i=0;i<documentNames.length;i++){
            DelphiStudy study = MultiRaterAgreementTest.createDelphiStudy(words, documentNames[i]);
            
            PrintUtil.printMsg("Calculating agreement for      :"+documentNames[i] +"Document");
            
             FleissKappaAgreement pi = new FleissKappaAgreement(study);
		
                double oa = pi.calculateObservedAgreement();
                double ea = pi.calculateExpectedAgreement();
                  agreement[i] = pi.calculateAgreement();
        
        }
        
        return agreement;   
    
    }
    
    
    private  static void prettyPrintMatrix(String legend, Matrix matrix, 
      
            
       String[] documentNames, String[] words, PrintWriter writer) {
    
        double agreement[] = new double[documentNames.length] ;
        
        double  costs[][]  = new double[documentNames.length][words.length];
        
        for(int i=0;i<documentNames.length;i++){
            DelphiStudy study = MultiRaterAgreementTest.createDelphiStudy(words, documentNames[i]);
            
            PrintUtil.printMsg("Calculating agreement for      :"+documentNames[i] +"Document");
            
             FleissKappaAgreement pi = new FleissKappaAgreement(study);
		
                double oa = pi.calculateObservedAgreement();
                double ea = pi.calculateExpectedAgreement();
                  agreement[i] = pi.calculateAgreement();
        
        }     
        
        PrintUtil.printMsg("Completed Delphi Study");
        System.out.println();
        
        writer.printf("============%s ===%n", legend);
    writer.printf("%15s", " ");
    for (int i = 0; i < documentNames.length; i++) {
      writer.printf("%8s", documentNames[i]);
    }
    writer.println();
    for (int i = 0; i < words.length; i++) {
      writer.printf("%15s", words[i]);
      for (int j = 0; j < documentNames.length; j++) {
       
           //PrintUtil.printMsg("Adding Agreement Value to Term Document Matrix");
           //System.out.println();
           
       
          writer.printf("%8.4f", matrix.get(i, j)+agreement[j]);
         //System.out.println();
        
        
      }
      writer.println();
    }
    
     
    
    writer.flush();
  }

    
    private  static Matrix AdjPrintMatrix(String legend, Matrix matrix, 
      String[] documentNames, String[] words, PrintWriter writer) {
 
        
        writer.printf("=== %s ===%n", legend);
    writer.printf("%15s", " ");
    for (int i = 0; i < documentNames.length; i++) {
      writer.printf("%8s", documentNames[i]);
    }
    writer.println();
    for (int i = 0; i < words.length; i++) {
      writer.printf("%15s", words[i]);
      for (int j = 0; j < documentNames.length; j++) {
        
         if((matrix.get(i, j))>0)
         {
            double answer = matrix.get(i, j);
            answer = 1;
          writer.printf("%8.4f", answer);
         
            // System.out.println(1);
             
         }else{
         
            // System.out.println(0);
          writer.printf("%8.4f", matrix.get(i, j));
          }        
          //writer.printf("%8.4f", matrix.get(i, j));
        
        
      }
      writer.println();
    }
    writer.flush();
    
    return matrix;
  }
    
    
    
    public static Matrix BuildTfIndexer() throws Exception {
 
          PrintUtil.printStar();
        vectorGenerator.generateVector(documents);
    
        TfIndexer indexer = new TfIndexer();

    Matrix tfMatrix = indexer.transform(vectorGenerator.getMatrix());
    
      //BuidAdjMatrix(tfMatrix,new PrintWriter(System.out, true));
    
    
     prettyPrintMatrix("Term Frequency Matrix", tfMatrix, 
      vectorGenerator.getDocumentNames(), vectorGenerator.getWords(), 
      new PrintWriter(System.out, true));
     
     PrintUtil.printStar();
     return tfMatrix; 
  }
    
    

    
    public   static void  SearcherDocuments(String SearchWordorSentence) throws Exception {
    
        PrintUtil.printStar();

// generate the term document matrix via the appropriate indexer
        IdfIndexer indexer = new IdfIndexer();
        double[][] vectorData = vectorGenerator.getMatrix().getArray();
        RealMatrix m = MatrixUtils.createRealMatrix(vectorData);
        RealMatrix termDocMatrix =  indexer.transform(m);
    
     Searcher searcher = new Searcher();
    
    Matrix tdf = new Matrix(m.getData());
    
    double agreements[] = Agreements(tdf,vectorGenerator.getDocumentNames(), vectorGenerator.getWords());
    
    
    
    searcher.setDocuments(vectorGenerator.getDocumentNames());
    searcher.setTerms(vectorGenerator.getWords());
    searcher.setSimilarity(new CosineSimilarity());
    searcher.setTermDocumentMatrix(termDocMatrix);
    searcher.setDelphiAgreement(agreements);
    searcher.setCostMatrix(vectorData);
    searcher.ComputeKuhnMunkresCostAssigment();
    List<SearchResult> results = 
      searcher.search(SearchWordorSentence);
    
    if (results.size()>0)
    {
    
    prettyPrintResults(SearchWordorSentence, results);
    }else{
    
    PrintUtil.printMsg("Sorry Found Not Results !");
    PrintUtil.printline();
    }
    
    
    
    }
    
    
    private static void prettyPrintResults(String query, 
      List<SearchResult> results) {
    System.out.printf("Results for Query String: [%s]%n", query);
    for (SearchResult result : results) {
      System.out.printf("%s (Score = %8.4f)%n", result.title, result.score);
    }
  }
    
     
    
    
    
public static void main(String argsp[]){
        try {
 
            setUp(); 
       // SetUp 
            
            Matrix tfMatrix =   BuildTfIndexer();
       //TDM 
            
            // Now Add Delphi Agreement .
            
            //MultiRaterAgreementTest mr = new MultiRaterAgreementTest();
            
    
       Matrix adjMatrix = AdjPrintMatrix("Adj Term Frequency", tfMatrix, 
      vectorGenerator.getDocumentNames(), vectorGenerator.getWords(), 
      new PrintWriter(System.out, true));
          
            
        
           SearcherDocuments("Binary");
        
          
        } catch (Exception ex) {
            Logger.getLogger(IndexersTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void prettyPrintRealMatrix(String legend, RealMatrix matrix, String[] documentNames, PrintWriter writer) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
     writer.printf("=== %s ===%n", legend);
    writer.printf("%6s", " ");
    for (int i = 0; i < documentNames.length; i++) {
      writer.printf("%8s", documentNames[i]);
    }
    writer.println();
    
    double m[][] = matrix.getData();
    
    for (int i = 0; i < documentNames.length; i++) {
      writer.printf("%6s", documentNames[i]);
      
         for (String documentName : documentNames) {
             writer.printf("%8.4f", m[i][i]);
         }
      writer.println();
    }
    writer.flush();
    }




}
    
    
    
 
 
