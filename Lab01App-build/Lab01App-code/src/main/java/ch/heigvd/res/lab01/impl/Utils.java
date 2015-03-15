package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");
     
     // Initialisation de l'indice qui détecte un séparateur
     int pos = -1;
     
     // On réupère dans pos l'indice du premier séparateur trouvé
     for(int i = 0; i < lines.length(); ++i) {
        if (lines.charAt(i) == '\n') {
           pos = i;
           break;
        }
        else if (lines.charAt(i) == '\r') {
           if (i != lines.length() - 1) {
              if (lines.charAt(i+1) == '\n') {
                 pos = i + 1;
              }
              else {
                 pos = i;
              }
           }
           else {
              pos = i;
           }  
           break;
        }     
     }
     
     String[] res = new String[2];
     
     // Si aucun séparateur n'a été trouvé
     if (pos == - 1) {
        res[0] = "";
        res[1] = lines;
     }
     
     // Sinon le premier élément contient une chaine suivi d'un séparateur
     // Et le second élément contient le reste de la chaine
     else {
        res[0] = lines.substring(0, pos + 1);
        res[1] = lines.substring(pos + 1);
     }
     
     // On retourne le tableau de string     
     return res;
     
  }
}
