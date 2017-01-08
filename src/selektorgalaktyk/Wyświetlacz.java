/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Quchi
 */
public class Wyświetlacz extends JPanel {
    BufferedImage Obraz;
    
    
    Wyświetlacz(BufferedImage Obraz){
        super();
        this.Obraz = Obraz;
    }

    Wyświetlacz() {
     super(); //
     Obraz = new BufferedImage(700,422,BufferedImage.TYPE_3BYTE_BGR);
    }

    public void UstawNowyObraz(BufferedImage Obraz){
        this.Obraz = Obraz;
    }
    public BufferedImage PobierzObraz(){
        return Obraz;
    }
    
    @Override
    public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Obraz, 0, 0, this);
	}
}
