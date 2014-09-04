/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package similarity;

import Jama.Matrix;
import indexers.IdfIndexer;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ecologic
 */
public class TestSimilarity {
    
    
     
    
    public static void main(String args[]){
    
        String documents[] ={"","",""};
        String query = "query";
        Searcher searcher = new Searcher();
       searcher.setDocuments(documents);
       //searcher.setTermDocumentMatrix(tftmatrix);
        List<Searcher.SearchResult>  results =   searcher.search(query);
        
       
        
    
    }
    
}
