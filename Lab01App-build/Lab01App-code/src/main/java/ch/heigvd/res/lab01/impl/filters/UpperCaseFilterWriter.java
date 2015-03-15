package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     out.write(str.substring(off, off + len).toUpperCase());     
     
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     char[] tmp = new char[len];
     
     int j = 0;
     for(int i = off; i < off + len; ++i) {
        // Mise en majuscule du char
        tmp[j] = Character.toUpperCase(cbuf[i]);
        ++j;
     }
     // Ecriture du char
     out.write(tmp);
  }

  @Override
  public void write(int c) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     c = (Character.toUpperCase(c));
     out.write(c);
     
  }

}
