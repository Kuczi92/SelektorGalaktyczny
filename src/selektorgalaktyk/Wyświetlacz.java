/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Quchi
 */
public class Wyświetlacz extends JPanel {
    BufferedImage Obraz;

    Graphics2D g2d;
    Polygon p;
    boolean ustawionyprostokąd = false;
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
	        g2d = (Graphics2D) g;
		g2d.drawImage(Obraz, 0, 0, this);
                
                if(ustawionyprostokąd){
                        Stroke stroke3 = new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                        g2d.setColor(Color.RED);
                        g2d.setStroke(stroke3);
                        g2d.drawPolygon(p);
                }
                
	}
    
    public void UstawProstokąt(int x1, int y1, int x2 , int y2, int x3, int y3,int x4, int y4 ){
         ustawionyprostokąd = true;
         p = new Polygon();
         p.addPoint(x1, y1);
         p.addPoint(x2, y2);
         p.addPoint(x3, y3);
         p.addPoint(x4, y4);
         
    }
}
