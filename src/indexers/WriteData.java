/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indexers;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

//import com.sleepycat.je.log.FileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

 
public class WriteData {
    
     public static void clearCsv() throws Exception {
        FileWriter fw = new FileWriter("D://out.csv", false); 
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();
        pw.close();
        fw.close();
    }
        
    
    public void WriteDatas(String Data) throws IOException{
    
        String filename = "D:\\result.csv";
        
        
       // CSVReader reader = new CSVReader(filename,); 
        
        BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
        try (CSVWriter writer = new CSVWriter(out)) {
            writer.writeNext(Data);
            writer.flush();
        }
        
    }
   
    
    
public HashMap parseUsingOpenCSV(String filename) throws FileNotFoundException 
{
        
        CSVReader reader;
        java.io.FileReader  readers = new java.io.FileReader(filename);
        double data[] = new double[100];
        
        
        
        HashMap first = new HashMap();  
      
       
        try 
        {
        
          reader = new CSVReader(readers);
        
        String[] row;

        int count = 1;
        
        
        while ((row = reader.readNext()) != null) 
        {
                for (int i = 0; i < row.length; i++) 
                {
                    
                    
                     //String[] tokensVal = str.split(delimiters);
                      
                    String as[] = row[i].split(":");
                    
                   double score =Double.parseDouble(as[0]);
                    data[count]= score;
                   
                    first.put(as[1], score);
                    
                    // System.out.println("Cell column index: " + i);
                    // System.out.println("Cell Value: " + row[i]);
                     //System.out.println("-------------");
                     //System.out.println(WebSiteNames[i]);
                        
                }
        }
        } 
        catch (FileNotFoundException e) 
        {
                System.err.println(e.getMessage());
        }
        catch (IOException e) 
        {
                System.err.println(e.getMessage());
        }
        
          List keys = new ArrayList(first.keySet());   
         List values=new ArrayList(first.values());
        
        TreeSet sortedMap= new TreeSet(values);  
      Object[]sortedSet = sortedMap.toArray();   
      
      
      
      for(int i=0;i<sortedSet.length;i++)  
           {  
                first.put(keys.get(values.indexOf(sortedSet[i])),sortedSet[i]);   
           }  
        
        System.out.println("Que="+first);   
         
        
          return first;       
  
} 


}
 
    
    
    
    
    
  