/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Norbert
 */
public class SelektorGalaktyk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        PanelSterowania PanelGlowny = new PanelSterowania();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        PanelGlowny.setLocation(width/4, height/4);
        PanelGlowny.setVisible(true);
      
        
    }
    
}
