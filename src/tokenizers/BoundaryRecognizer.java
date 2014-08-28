/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenizers;

/**
 *
 * @author Puneet
 */
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

 
import tokenizers.Token.TokenType;

 

public class BoundaryRecognizer implements IRecognizer {

  private Pattern whitespacePattern;
  private Pattern punctuationPattern;
  
  public void init() throws Exception {
    this.whitespacePattern = Pattern.compile("\\s+");
    this.punctuationPattern = Pattern.compile("\\p{Punct}");
  }

  public List<Token> recognize(List<Token> tokens) {
    List<Token> extractedTokens = new LinkedList<Token>();
    for (Token token : tokens) {
      String value = token.getValue();
      TokenType type = token.getType();
      if (type != TokenType.UNKNOWN) {
        // we already know what this is, continue
        extractedTokens.add(token);
        continue;
      }
      // whitespace
      Matcher whitespaceMatcher = whitespacePattern.matcher(value);
      if (whitespaceMatcher.matches()) {
        extractedTokens.add(new Token(value, TokenType.WHITESPACE));
        continue;
      }
      // punctuation
      Matcher punctuationMatcher = punctuationPattern.matcher(value);
      if (punctuationMatcher.matches()) {
        extractedTokens.add(new Token(value, TokenType.PUNCTUATION));
        continue;
      }
      // we came this far, then its still UNKNOWN
      extractedTokens.add(token);
    }
    return extractedTokens;
  }
}
