/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;
import org.opencv.core.Point;

/**
 *
 * @author Quchi
 */

public class Galaktyka {
    String Żródło;
    BufferedImage ObrazGalaktyki;
    Widok WidokNaGalaktyke;
    TypGalaktyki TypGalaktyki;
    double PikseleJasne;
    double PikseleBiałe;
    int LiczbaJasnychPunktów;
    int LiczbaJąderGalaktyki;
    
    Point Punkt1;
    Point Punkt2;
    Point Punkt3;
    Point Punkt4;
    
    
    
   public Galaktyka(String Żródło,BufferedImage ObrazGalaktyki,Widok WidokNaGalaktyke,TypGalaktyki TypGalaktyki,double PikseleJasne,double PikseleBiałe,int LiczbaJasnychPunktów,int LiczbaJąderGalaktyki){
      this.LiczbaJasnychPunktów =  LiczbaJasnychPunktów;
      this.LiczbaJąderGalaktyki = LiczbaJąderGalaktyki;
      this.ObrazGalaktyki = ObrazGalaktyki;
      this.PikseleBiałe = PikseleBiałe;
      this.PikseleJasne = PikseleJasne;
      this.TypGalaktyki = TypGalaktyki;
      this.WidokNaGalaktyke = WidokNaGalaktyke;
      this.Żródło = Żródło;        
   }
   
   
    public Galaktyka(String Żródło,BufferedImage ObrazGalaktyki){
       this.Żródło = Żródło; 
       this.ObrazGalaktyki = ObrazGalaktyki;
    } 
    
    
    
    
    /*
    Poniżej wszystkie możliwe settery
    */
    public void setLiczbaJasnychPunktów(int LiczbaJasnychPunktów){
        this.LiczbaJasnychPunktów = LiczbaJasnychPunktów;
    }
    
    public void setLiczbaJąderGalaktyki(int LiczbaJąderGalaktyki){
        this.LiczbaJąderGalaktyki = LiczbaJąderGalaktyki;
    }
    
    public void setWidokNaGalaktyke(Widok Widok){
       this.WidokNaGalaktyke = Widok; 
    }
    
    public void setTypGalaktyki(TypGalaktyki TypGalaktyki){
        this.TypGalaktyki = TypGalaktyki;
    }
    
    public void setPikseleBiałe(double setPikseleBiałe){
        this.PikseleBiałe = setPikseleBiałe;
    }
    
    public void setPikseleJasne(double setPikseleJasne){
        this.PikseleJasne = setPikseleJasne;
    }
    
    public void setPunkty(Point P1,Point P2,Point P3,Point P4){
        this.Punkt1 = P1;
        this.Punkt2 = P2;
        this.Punkt3 = P3;
        this.Punkt4 = P4; 
    }
    
    /*
    Poniżej wszystki możliwe gettery
    */
     public Widok getWidokNaGalaktyke(){
      return WidokNaGalaktyke; 
    }
    
    public TypGalaktyki getTypGalaktyki(){
        return TypGalaktyki;
    }
    
    public int getLiczbaJasnychPunktów(){
        return LiczbaJasnychPunktów;
    }
    
    public int getLiczbaJąderGalaktyki(){
        return LiczbaJąderGalaktyki;
    }
    
    public double getPikseleBiałe(){
        return PikseleBiałe;
    }
    
    public double getPikseleJasne(){
        return PikseleJasne;
    }
    public String getŹródło(){
        return Żródło;
    }
    public BufferedImage getObraz(){
        return ObrazGalaktyki;
    }
    
  public BufferedImage  ZwróćinnyRozmiarObrazuGalaktyki( int newW, int newH) { 
    Image tmp = ObrazGalaktyki.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return dimg;
}

 
    public void  ZmieńRozmiarObrazuGalaktyki( int newW, int newH) { 
    Image tmp = ObrazGalaktyki.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    ObrazGalaktyki = dimg;
}
    
   
}