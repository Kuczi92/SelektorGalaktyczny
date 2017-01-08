/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static selektorgalaktyk.WyświetlaczObraz.RodzajeProgowania.BRAK_PROGROWANIA;
import static selektorgalaktyk.WyświetlaczObraz.RodzajeProgowania.EFEKT_PRZYCIEMNAJACY;
import static selektorgalaktyk.WyświetlaczObraz.RodzajeProgowania.EFEKT_ROZJASNIAJACY;
import static selektorgalaktyk.WyświetlaczObraz.RodzajeProgowania.PROGOWANIE;


/**
 *
 * @author Quchi
 */
public class WyświetlaczObraz extends JFrame implements ActionListener {
    private static String Sciezka;
    private final Obraz RGB;
    public  JPanel Wyświetlacz;
 
    public String tutułOkna;
    public String nazwapliku;
    
   
    
    public WyświetlaczObraz(String sciezka, String TytułOkna,String nazwaPliku) {
        RGB = new Obraz(sciezka); 
        this.tutułOkna=TytułOkna;
        initComponents();
        Sciezka=sciezka;
        this.nazwapliku=nazwaPliku;
        
    }
    
   public WyświetlaczObraz(BufferedImage Obraz, String TytułOkna,String nazwaPliku) {
       RGB = new Obraz(Obraz);
       this.tutułOkna=TytułOkna;
       initComponents();
       this.nazwapliku=nazwaPliku;
  
        
    }
  
  
   
   public WyświetlaczObraz(Obraz ObrazEdytowalny, String TytułOkna,String nazwaPliku) {
       this.RGB=ObrazEdytowalny;
       this.tutułOkna=TytułOkna;
       this.nazwapliku=nazwaPliku;

    }
   
   static String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }
   public String pobierzNazwePliku(){
       return stripExtension (nazwapliku);
   }
    public void zapiszObraz(String Sciezka){
       RGB.writeImage(Sciezka);
   }
   public String pobierzSciezkePliku(){
        return Sciezka;
    }
   public int[] pobierzTabliceRGB(){
       return RGB.getPixelArray();
   }
   public int pobierzX(){
       
        return RGB.getImageWidth();
   }
   public int pobierzY()
   {
       return RGB.getImageHeight();
   }
   
   public void UstawTablicePikseli(int tablica[]){
       RGB.setPixelArray(tablica);
   }
   
   public BufferedImage PobierzObraz(){
       return RGB.Image();
   }

    void UstawObraz(ArrayList<BufferedImage> ListaGalaktyk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
   public enum RodzajeProgowania {
    EFEKT_ROZJASNIAJACY, EFEKT_PRZYCIEMNAJACY, PROGOWANIE,BRAK_PROGROWANIA;
    }
   
   public int[] ModyfikujKoloryWKanaleRGB(double czerwien,double zielen,double niebieski,double kontrast,RodzajeProgowania progowanie,int wartoscprogujaca){
       
       int height = pobierzX();
       int width = pobierzY();
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KanalAlpha [] = RGB.getAlphaTable();
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KolorCzerwony[] = RGB.getRedTable();
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KolorZielony[] = RGB.getGreenTable();
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KolorNiebieski[] = RGB.getBlueTable();
       
       
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int WyjsciowyRGB[] = new int[RGB.getImageTotalPixels()];
        
       for(int y = 0; y < height; y++)
       
       {
            for(int x = 0; x < width; x++)
            {
                
             ///Blok Dla koloru czerwonego   
                            //blok kodu odpowiedzialny za zabezpieczenia które mają za zadanie zapobiec przepełnienia liczby poza skale 8 bitow
                            
                                      //obliczenie wartosci pikseli oraz operacje progowania  
                                      if(null != progowanie)  
                                        switch (progowanie) {
                                            case BRAK_PROGROWANIA:
                                                
                                                
                                                                    if((KolorCzerwony[x+width*y]+czerwien)*kontrast>255)
                                                                            {
                                                                               KolorCzerwony[x+width*y]=255;
                                                                            }
                                                                    else if((KolorCzerwony[x+width*y]+czerwien)*kontrast<0)
                                                                            { 
                                                                              KolorCzerwony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorCzerwony[x+width*y]=(int) ((KolorCzerwony[x+width*y]+czerwien)*kontrast);
                                                                            }
                                                
                                                
                                                                    if((KolorZielony[x+width*y]+zielen)*kontrast>255)
                                                                            {
                                                                                KolorZielony[x+width*y]=255;
                                                                            }
                                                                    else if((KolorZielony[x+width*y]+zielen)*kontrast<0)
                                                                            {
                                                                                KolorZielony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                                KolorZielony[x+width*y]=(int) ((KolorZielony[x+width*y]+zielen)*kontrast);
                                                                            } 
                                                                    
                                                                    
                                                                    
                                                                    if((KolorNiebieski[x+width*y]+niebieski)*kontrast>255)
                                                                            {
                                                                               KolorNiebieski[x+width*y]=255;
                                                                            }
                                                                    else if((KolorNiebieski[x+width*y]+niebieski)*kontrast<0)
                                                                            { 
                                                                               KolorNiebieski[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorNiebieski[x+width*y]=(int) ((KolorNiebieski[x+width*y]+niebieski)*kontrast);
                                                                            }
                                                break;
                                                
                                               
                                               
                                            case PROGOWANIE:
                                              
                                                if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                                {   
                                                                    if(((255+czerwien)*kontrast)>255)
                                                                            {
                                                                               KolorCzerwony[x+width*y]=255;
                                                                            }
                                                                    else if(((255+czerwien)*kontrast)<0)
                                                                            { 
                                                                              KolorCzerwony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorCzerwony[x+width*y]=(int) ((255+czerwien)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    if(((255+zielen)*kontrast)>255)
                                                                            {
                                                                                KolorZielony[x+width*y]=255;
                                                                            }
                                                                    else if(((255+zielen)*kontrast)<0)
                                                                            {
                                                                                KolorZielony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                                KolorZielony[x+width*y]=(int) ((255+zielen)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    if(((255+niebieski)*kontrast)>255)
                                                                            {
                                                                               KolorNiebieski[x+width*y]=255;
                                                                            }
                                                                    else if(((255+niebieski)*kontrast)<0)
                                                                            { 
                                                                               KolorNiebieski[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorNiebieski[x+width*y]=(int) ((255+niebieski)*kontrast);
                                                                            }
                                                                    
                                                    
                                                }
                                                
                                                
                                                
                                                else
                                                {
                                                                    if(((0+czerwien)*kontrast)>255)
                                                                            {
                                                                               KolorCzerwony[x+width*y]=255;
                                                                            }
                                                                    else if(((0+czerwien)*kontrast)<0)
                                                                            { 
                                                                              KolorCzerwony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorCzerwony[x+width*y]=(int) ((0+czerwien)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    if(((0+zielen)*kontrast)>255)
                                                                            {
                                                                                KolorZielony[x+width*y]=255;
                                                                            }
                                                                    else if(((0+zielen)*kontrast)<0)
                                                                            {
                                                                                KolorZielony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                                KolorZielony[x+width*y]=(int) ((0+zielen)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    if(((0+niebieski)*kontrast)>255)
                                                                            {
                                                                               KolorNiebieski[x+width*y]=255;
                                                                            }
                                                                    else if(((0+niebieski)*kontrast)<0)
                                                                            { 
                                                                               KolorNiebieski[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorNiebieski[x+width*y]=(int) ((0+niebieski)*kontrast);
                                                                            }
                                                }     
                                                
                                                
                                                break;
                                            case EFEKT_ROZJASNIAJACY:
                                                if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                                {
                                                    KolorCzerwony[x+width*y]= 255;
                                                    KolorZielony[x+width*y]=255;
                                                    KolorNiebieski[x+width*y]=255;
                                                }
                                                else
                                                {
                                                                    if(((KolorCzerwony[x+width*y]+czerwien)*kontrast)>255)
                                                                            {
                                                                               KolorCzerwony[x+width*y]=255;
                                                                            }
                                                                    else if(((KolorCzerwony[x+width*y]+czerwien)*kontrast)<0)
                                                                            { 
                                                                              KolorCzerwony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorCzerwony[x+width*y]=(int) ((KolorCzerwony[x+width*y]+czerwien)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    if(((KolorZielony[x+width*y]+zielen)*kontrast)>255)
                                                                            {
                                                                                KolorZielony[x+width*y]=255;
                                                                            }
                                                                    else if(((KolorZielony[x+width*y]+zielen)*kontrast)<0)
                                                                            {
                                                                                KolorZielony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                                KolorZielony[x+width*y]=(int) ((KolorZielony[x+width*y]+zielen)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    if(((KolorNiebieski[x+width*y]+niebieski)*kontrast)>255)
                                                                            {
                                                                               KolorNiebieski[x+width*y]=255;
                                                                            }
                                                                    else if(((KolorNiebieski[x+width*y]+niebieski)*kontrast)<0)
                                                                            { 
                                                                               KolorNiebieski[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorNiebieski[x+width*y]=(int) ((KolorNiebieski[x+width*y]+niebieski)*kontrast);
                                                                            }
                                                                    
                                                }    
                                                break;
                                            case EFEKT_PRZYCIEMNAJACY:
                                                if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                                {
                                                    if(((KolorCzerwony[x+width*y]+czerwien)*kontrast)>255)
                                                                            {
                                                                               KolorCzerwony[x+width*y]=255;
                                                                            }
                                                                    else if(((KolorCzerwony[x+width*y]+czerwien)*kontrast)<0)
                                                                            { 
                                                                              KolorCzerwony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorCzerwony[x+width*y]=(int) ((KolorCzerwony[x+width*y]+czerwien)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    if(((KolorZielony[x+width*y]+zielen)*kontrast)>255)
                                                                            {
                                                                                KolorZielony[x+width*y]=255;
                                                                            }
                                                                    else if(((KolorZielony[x+width*y]+zielen)*kontrast)<0)
                                                                            {
                                                                                KolorZielony[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                                KolorZielony[x+width*y]=(int) ((KolorZielony[x+width*y]+zielen)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    if(((KolorNiebieski[x+width*y]+niebieski)*kontrast)>255)
                                                                            {
                                                                               KolorNiebieski[x+width*y]=255;
                                                                            }
                                                                    else if(((KolorNiebieski[x+width*y]+niebieski)*kontrast)<0)
                                                                            { 
                                                                               KolorNiebieski[x+width*y]=0;
                                                                            }
                                                                    else
                                                                            {
                                                                               KolorNiebieski[x+width*y]=(int) ((KolorNiebieski[x+width*y]+niebieski)*kontrast);
                                                                            }
                                                                    
                                                                    
                                                                    
                                                }
                                                else
                                                {
                                                    KolorCzerwony[x+width*y]= 0;
                                                    KolorZielony[x+width*y]= 0;
                                                    KolorNiebieski[x+width*y]= 0;
                                                }     break;
                                            default:
                                                break;  
                                        }
                  
                             
                WyjsciowyRGB[x+width*y]= (KanalAlpha [x+width*y]<<24) | (KolorCzerwony[x+width*y]<<16) | (KolorZielony[x+width*y]<<8) | KolorNiebieski[x+width*y];
            }
            
            
            
         }
       
       
       
          return WyjsciowyRGB;
        } 
        
  
   
   
        public void Odswierzenie(){
                                      
                                      Graphics g  = RGB.Image().getGraphics();
                                      g.drawImage(RGB.Image(), 0, 0, this);
                                      g.dispose();
                                      this.repaint();
        }
       
        
        
        
   
   public void UstawObraz(BufferedImage Obraz){
       RGB.setImage(Obraz);
   }
   
   
   
     private void initComponents() {
       Wyświetlacz = RGB;
       setResizable(true);
       setPreferredSize(new Dimension(pobierzX(),pobierzY()));
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setTitle(tutułOkna);
       setLayout(new BorderLayout());
       add(Wyświetlacz, BorderLayout.CENTER);
       setLocation(100, 100);
       pack();

   }
     
     

  
    
    public void mouseDragged(MouseEvent e) {
      
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
