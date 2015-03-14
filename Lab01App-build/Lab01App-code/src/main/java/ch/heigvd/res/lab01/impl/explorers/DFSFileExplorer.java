package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
     //throw new UnsupportedOperationException("The student has not implemented this method yet.");

     // Premièrment, on visite l'objet passé en paramètre (fichier ou répertoire)
     vistor.visit(rootDirectory);
     // S'il s'agit d'un répertoire alors on récupère les noms des objets contenus dans ce repertoire
     // S'il s'agit d'un dossier alors vaudra null
     File[] nomObjets = rootDirectory.listFiles();
     
     if (nomObjets != null) {
    
        // On visite tous les fichiers trouvés
        for(int i = 0; i < nomObjets.length; ++i) {
           if (nomObjets[i].isFile()) {
              vistor.visit(nomObjets[i]);
           }
        }
        
        for(int i = 0; i < nomObjets.length; ++i) {
           if (nomObjets[i].isDirectory()) {
              explore(nomObjets[i], vistor);
           }
        }
     }
  }
   
}
