package ch.heigvd.res.lab01.impl.transformers;

import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This abstract class implements the IFileVisitor interface and has the
 * responsibility to open an input text file, to read its content, to apply a
 * number of transformations (via filters) and to write the result in an output
 * text file.
 *
 * The subclasses have to implement the decorateWithFilters method, which
 * instantiates a list of filters and decorates the output writer with them.
 *
 * @author Olivier Liechti
 */
public abstract class FileTransformer implements IFileVisitor {

   private static final Logger LOG = Logger.getLogger(FileTransformer.class.getName());
   private final List<FilterWriter> filters = new ArrayList<>();

  @Override
  public void visit(File file) {
    if (!file.isFile()) {
      return;
    }
    try {
      Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
      Writer writer = new OutputStreamWriter(new FileOutputStream(file.getPath()+ ".out"), "UTF-8"); // the bug fix by teacher
      writer = decorateWithFilters(writer);

   @Override
   public void visit(File file) {
      if (!file.isFile()) {
         return;
      }
      try {
         Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
         Writer writer = new OutputStreamWriter(new FileOutputStream(file.getPath() + ".out"), "UTF-8"); // the bug fix by teacher
         writer = decorateWithFilters(writer);

         /*
          * There is a missing piece here: you have an input reader and an ouput writer (notice how the 
          * writer has been decorated by the concrete subclass!). You need to write a loop to read the
          * characters and write them to the writer.
          */
         while (true) {
            int c = reader.read();

            if (c == -1) {
               break;
            } else {
               writer.write(c);
            }
         }

         reader.close();
         writer.flush();
         writer.close();
      } catch (IOException ex) {
         LOG.log(Level.SEVERE, null, ex);
      }
   }

   public class UpperCaseFilterWriter {

      public UpperCaseFilterWriter(Writer writer) {
         try {
            writer.write(writer.toString().toUpperCase());
         } catch (IOException e) {
            System.out.println("IOException a été levée");
         }
      }

   }
   
   public class FileNumberingFilterWriter {
      
      public FileNumberingFilterWriter(UpperCaseFilterWriter ucfw) {
         //Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
      }
      
   
   }

}
