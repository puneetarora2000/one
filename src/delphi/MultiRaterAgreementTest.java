 
package delphi;

//import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.FleissKappaAgreement;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.ICodingAnnotationStudy;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.CodingAnnotationStudy;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.DelphiStudy;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.FleissKappaAgreement;
import delphi.main.java.de.tudarmstadt.ukp.dkpro.statistics.agreement.coding.PercentageAgreement;
//import junitframework.TestCase;
//import junit.framework.TestCase;



/**
 * Tests for {@link PercentageAgreement} and {@link FleissKappaAgreement}.
 * @author Christian M. Meyer
 */
public class MultiRaterAgreementTest  {
	
	/** Creates an example annotation study.
     * @return  */
	public static DelphiStudy createExample() {
		DelphiStudy study = new DelphiStudy(3);
		
                //Documents -->Word -->Domain Politics :Yes ,No = Relavent or not relavant 
                // 3 People = 3 Rates : 
                
                String words[] = {"W1","W2","W3","W4","W5","W6","W7","W8","W9","W10"};
                
                study.setDocumentName("D1");
                
                study.setDocumentWords(words);
                
                
                // Election , D1  
                // Word2 ,    D1  
                // Election , D1  
                // Election , D1  
                // Election , D1  
                // Election , D1  
                // Election , D1  
                // Election , D1  
                // Election , D1  
                
                
                study.addItem(1, 1, 1);
        	study.addItem(1, 1, 2);
		study.addItem(1, 2, 1);
		study.addItem(2, 1, 1);
		study.addItem(2, 2, 1);
		study.addItem(2, 2, 2);
		study.addItem(2, 2, 2);
		study.addItem(2, 1, 2);
		study.addItem(2, 2, 1);
		study.addItem(2, 2, 1);
                
                if (study.getItemCount()==words.length)
                {
                    return study;
                }else {
                    study = null;
                
                    System.out.println("Number of Words Not Rated Properly");
                    
                    return  study;
                
                }
                
		 
	}    
    
    
    	public static void testExampleMy() {

            
            try{
            
            DelphiStudy study = createExample();
		
                PercentageAgreement pa = new PercentageAgreement((ICodingAnnotationStudy) study);
		double agreement = pa.calculateAgreement();
                System.out.println("==============================");
                System.out.println("The Document ID=" +study.getDocumentName());
                System.out.println("Percentage Agreement :"+agreement);
		
                 
                 FleissKappaAgreement pi = new FleissKappaAgreement((ICodingAnnotationStudy) study);
		
                double oa = pi.calculateObservedAgreement();
                double ea = pi.calculateExpectedAgreement();
                agreement = pi.calculateAgreement();

                System.out.println("Observered :"+oa);
                System.out.println("Expected :"+ea);
                System.out.println("Agreement :"+agreement);
                System.out.println("==============================");
                
                 
            }catch(Exception ex){
            
                System.out.println("Null Error"+ex.getMessage());
            
            }
            
            
                 
                
                
                
                
                
		
	}

    
    
    
	 


	
        public static void main(String args[]){
        
            testExampleMy();
        
        
        }
        
        
}
