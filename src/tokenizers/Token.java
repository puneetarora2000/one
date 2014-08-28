/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenizers;

/**
 *
 * @author Puneet
 */
public class Token {
    
    
    
 public enum TokenType {
  ABBREVIATION, 
  COMBINED, 
  COLLOCATION, 
  EMOTICON, 
  INTERNET, 
  WORD,
  NUMBER, 
  WHITESPACE,
  PUNCTUATION, 
  PLACE, 
  ORGANIZATION,
  MARKUP, 
  UNKNOWN,
   PHRASE,
  CONTENT_WORD
 
 ,      STOP_WORD}
    
    
    public Token() {
    super();
  }
    
    
    @Override
  public String toString() {
    return value + " (" + type + ")";
  }
    
    private String value;
  private TokenType type;

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
  
  public Token(String value, TokenType type) {
    this();
    setValue(value);
    setType(type);
  }
 }
 