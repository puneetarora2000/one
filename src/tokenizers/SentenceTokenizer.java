/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenizers;

/**
 *
 * @author Puneet
 */
import java.text.BreakIterator;
import java.util.Locale;

public class SentenceTokenizer {

  private String text;
  private int index = 0;
  private BreakIterator breakIterator;
  
  public SentenceTokenizer() {
    super();
    this.breakIterator = BreakIterator.getSentenceInstance(
      Locale.getDefault());
  }
  
  public void setText(String text) {
    this.text = text;
    this.breakIterator.setText(text);
    this.index = 0;
  }
  
  public String nextSentence() {
    int end = breakIterator.next();
    if (end == BreakIterator.DONE) {
      return null;
    }
    String sentence = text.substring(index, end);
    index = end;
    return sentence;
  }
}
