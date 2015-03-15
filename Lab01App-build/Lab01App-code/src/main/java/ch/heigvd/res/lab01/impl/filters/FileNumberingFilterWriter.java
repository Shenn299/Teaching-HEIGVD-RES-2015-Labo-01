package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  
  private int numeroLigne = 1;
  private boolean debutLigne = true;
  // \r détecté (pour le \r\n de windows)
  private boolean r = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     char[] tab = str.substring(off, off + len).toCharArray();

     for(int i = 0; i < tab.length; ++i) {
        
        // Si on est en début de ligne
        if (debutLigne) {
           // On écrit le numéro de la ligne suivi d'un tab
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
        
        out.write(tab[i]);
     
        // Si le caractère est un retour à la ligne
        if (tab[i] == '\n' || tab[i] == '\r') {
           if (tab[i] == '\r' && tab[i+1] == '\n') {
              out.write(tab[i+1]);
              ++i;              
           }
           
           ++numeroLigne;
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
     }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     for(int i = off; i < off + len; ++i) {
        
        // Si on est en début de ligne
        if (debutLigne) {
           // On écrit le numéro de la ligne suivi d'un tab
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
        
        out.write(cbuf[i]);
     
        // Si le caractère est un retour à la ligne
        if (cbuf[i] == '\n' || cbuf[i] == '\r') {
           if (cbuf[i] == '\r' && cbuf[i+1] == '\n' ) {
              out.write(cbuf[i+1]);
              ++i;              
           }
           
           ++numeroLigne;
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
     }
  }

  @Override
  public void write(int c) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     if (r == true) {
        r = false;
        
        if (c == '\n') {
           out.write("\r");
           out.write("\n");
           ++numeroLigne;
           out.write(numeroLigne + "\t");
      
           return;
        }
        else {
           out.write("\r");
           ++numeroLigne;
           debutLigne = true;
        }
     }
     
     // Si on est en début de ligne
     if (debutLigne) {
        // On écrit le numéro de la ligne suivi d'un tab
        out.write(numeroLigne + "\t");
        debutLigne = false;
     }
     
     // Si le caractère est un retour à la ligne
     if (c == '\n' && r == false) {
        out.write(c);
        ++numeroLigne;
        out.write(numeroLigne + "\t");        
        return;
     }
     
     if (c == '\r') {
        r = true;
        return;
     }
     
     out.write(c);
     
  }

}
