/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Formularios;

/**
 *
 * @author santiago
 */
import java.io.File;

public class FiltroJPG extends javax.swing.filechooser.FileFilter {
    @Override
        public boolean accept(File file) {
            String filename = file.getName();
            if (filename.endsWith(".jpg")|(filename.endsWith(".jpeg")) | (filename.endsWith(".png"))|(filename.endsWith(".bmp"))){
                return true;
            }          
            if (file.isDirectory())
                return true;
            return false;
        }
    @Override
        public String getDescription() {
            return "*.jpg|jpeg|png|bmp";
        }
}