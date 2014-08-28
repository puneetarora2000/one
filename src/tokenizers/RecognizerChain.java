 
package tokenizers;

 
import java.util.LinkedList;
import java.util.List;

 
public class RecognizerChain implements IRecognizer {

  private List<IRecognizer> recognizers;
  
  public RecognizerChain(List<IRecognizer> recognizers) {
    super();
    setRecognizers(recognizers);
  }
  
  public void setRecognizers(List<IRecognizer> recognizers) {
    this.recognizers = recognizers;
  }

  public void init() throws Exception {
    for (IRecognizer recognizer : recognizers) {
      recognizer.init();
    }
  }
  
  /**
   * Applies a chain of IEntityExtractor implementations to the input Token
   * List and transforms it into another Token List.
   * @param a List of Tokens.
   * @return another List of Tokens.
   */
  public List<Token> recognize(final List<Token> tokens) {
    List<Token> recognizedTokens = new LinkedList<Token>();
    recognizedTokens.addAll(tokens);
    for (IRecognizer recognizer : recognizers) {
      recognizedTokens = recognizer.recognize(recognizedTokens);
    }
    return recognizedTokens;
  }
}