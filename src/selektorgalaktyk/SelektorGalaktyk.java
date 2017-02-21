/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 *
 * @author Norbert
 */
public class SelektorGalaktyk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        addLibraryPath("lib\\dlls");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        PanelSterowania PanelGlowny = new PanelSterowania();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        PanelGlowny.setLocation(width/4, height/4);
        PanelGlowny.setVisible(true);
    }
    
   public static void addLibraryPath(String pathToAdd) throws Exception {
   final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
    usrPathsField.setAccessible(true);
 
    //get array of paths
    final String[] paths = (String[])usrPathsField.get(null);
 
    //check if the path to add is already present
    for(String path : paths) {
        if(path.equals(pathToAdd)) {
            return;
        }
    }
 
    //add the new path
    final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
    newPaths[newPaths.length-1] = pathToAdd;
    usrPathsField.set(null, newPaths);
}    
}
    
