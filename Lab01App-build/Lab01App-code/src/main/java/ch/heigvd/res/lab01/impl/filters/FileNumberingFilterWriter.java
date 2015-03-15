package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import ch.heigvd.res.lab01.impl.Utils;

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
  
  // Numérotation des lignes (débute à 1)
  private int numeroLigne = 1;
  // Flag indiquant qu'on se trouve en début de ligne (sous certaine condition)
  private boolean debutLigne = true;
  // Flag qui indique que le séparateur '\r' a été détecté
  private boolean r = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");

     // On récupère la chaine sous forme de tableau de char
     char[] tab = str.substring(off, off + len).toCharArray();
     
     write(tab);
           
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     // Parcours du tableau de char
     for(int i = off; i < off + len; ++i) {
        
        // Si on est en début de ligne
        if (debutLigne) {
           // On écrit le numéro de la ligne suivi d'un tab
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
        
        // Ecriture du char
        out.write(cbuf[i]);
     
        // Si le caractère est un retour à la ligne
        if (cbuf[i] == '\n' || cbuf[i] == '\r') {
           // Si le prochain caractère est un \n
           if (cbuf[i] == '\r' && cbuf[i+1] == '\n' ) {
              // On écrit le \n
              out.write(cbuf[i+1]);
              ++i;              
           }
           
           // Ecriture du numéro de ligne + tab
           ++numeroLigne;
           out.write(numeroLigne + "\t");
           debutLigne = false;
        }
     }
  }

  @Override
  public void write(int c) throws IOException {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     // Si \r a été détecté
     if (r == true) {
        r = false;
        
        // Si le char est un \n
        if (c == '\n') {
           out.write("\r");
           out.write("\n");
           ++numeroLigne;
           out.write(numeroLigne + "\t");
           return;
        }
        
        // Sinon on écrit le \r
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
     
     // Si c'est un \r
     if (c == '\r') {
        r = true;
        return;
     }
     
     // Ecriture du char     
     out.write(c);
     
  }

}
