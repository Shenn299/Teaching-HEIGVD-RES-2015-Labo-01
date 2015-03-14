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
     
        // Si le caractère est un retour à la ligne
        if (tab[i] == '\n') {
           // On passe à la ligne suivante
           out.write(tab[i]);
           ++numeroLigne;
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
        else {
           // Sinon on écrit le caractère
           out.write(tab[i]);
        }
     }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     char c;
     for(int i = off; i < off + len; ++i) {
        c = cbuf[i];
        
        // Si on est en début de ligne
        if (debutLigne) {
           // On écrit le numéro de la ligne suivi d'un tab
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
     
        // Si le caractère est un retour à la ligne
        if (c == '\n') {
           // On passe à la ligne suivante
           out.write(c);
           ++numeroLigne;
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
        else {
           // Sinon on écrit le caractère
           out.write(c);
        }
     }
  }

  @Override
  public void write(int c) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     // Si on est en début de ligne
     if (debutLigne) {
        // On écrit le numéro de la ligne suivi d'un tab
        out.write(numeroLigne + "\t");
        debutLigne = false;
     }
     
     // Si le caractère est un retour à la ligne
     if (c == '\n') {
        // On passe à la ligne suivante
        out.write(c);
        ++numeroLigne;
        out.write(numeroLigne + "\t");
        debutLigne = false;
     }
     else {
        // Sinon on écrit le caractère
        out.write(c);
     }
     
  }

}
