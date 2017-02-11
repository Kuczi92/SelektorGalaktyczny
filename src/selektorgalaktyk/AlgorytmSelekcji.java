/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;


import java.awt.Graphics;
import static java.awt.geom.Point2D.distance;
import org.opencv.core.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.opencv.imgproc.Imgproc;





/**
 *
 * @author Quchi
 */


public class AlgorytmSelekcji {

    ArrayList<String> TypGalaktykiNazwa = new ArrayList<>();
    ArrayList<BufferedImage> ListaGalaktyk=new ArrayList<>();
    ArrayList<BufferedImage> ListaGalaktykBufor=new ArrayList<>();
    ArrayList<String> WczytaneTypGalaktykiNazwa = new ArrayList<>();
    ArrayList<BufferedImage> WczytaneListaGalaktyk=new ArrayList<>();
    ArrayList<String> ZFolderuWczytaneTypGalaktykiNazwa = new ArrayList<>();
    ArrayList<String> ZFolderuWczytaneListaGalaktyk=new ArrayList<>();
    ArrayList<String> ZapisDoObrazu=new ArrayList<>();
    ArrayList<Point> PGorny = new ArrayList<>();
    ArrayList<Point> PDolny = new ArrayList<>();
    ArrayList<Point> PPrawy = new ArrayList<>();
    ArrayList<Point> PLewy = new ArrayList<>();
    int wykrytych_galaktyk=0;
    
    ArrayList<Galaktyka> DanaGalaktyka = new ArrayList<>();
    
    
    
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }



 
    
    //////////////////////////// Zmienne /////////////////////////////////////
    
    /** Przechowanie obrazu */
    private final BufferedImage image;
    private final BufferedImage origin;
    String Źródło;
    /** Store the image width and height */
    private int width, height;
    
    /** Pixels value - ARGB */
    private int pixels[];
    
    /** Total number of pixel in an image*/
    private final int totalPixels;
    
     /// Ustawiena dla decyzyjnego wyboru z jaka galaktyka mamy do czynienia
                                    
                                         // dla plaskich po przekatnej
                                                int plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata=80;
                                                int plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata=10;
                                                int plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna=60;
                                                
                                        // dla plaskich symetrycznie     
                                                int plaskisym_procent_zapelnienia_jasnymi_prog_karlowata=10;
                                                int plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna=85;
                                                int plaskisym_procent_zapelnienia_bialymi_prog_Spiralna=5;
                                                int plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata=90;
                                                int plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata=10; 
                                               
                                          // dla pelnego widoku      
                                                int pelny_procent_zapelnienia_jasnymi_prog_karlowata=16;
                                              double pelny_procent_zapelnienia_bialymi_prog_karlowata= 0.5; 
                                              
                                                int pelny_procent_zapelnienia_jasnymi_prog_Spiralna=75;
                                                int pelny_procent_zapelnienia_bialymi_prog_Spiralna=10;
                                                
                                                int pelny_liczba_jasnych_obiektow_Spiralna=3;
                                                int pelny_procent_zapelnienia_bialymi_prog_Eliptyczna=10;
                                                
                                                
                                           //dla nieregularnych    
                                                int rozmycie_prog_Nieregularna=2;
                                                int prog_jasnosci_Nieregularna=80;
                                                
                                                
                                                 int rozmycie=30;
                                                 int czulosc=40;
                                                 int value = 5;
                                                 int min_wielkoscx=50;
                                                 int min_wielkoscy=50;
                                                 
                                                int jasnosc=-65;
                                                double kontrast=5.05;
                                                int Wartosc_Progowa=74;
    

    ////////////////////////////////// CONSTRUCTOR ////////////////////////////
    
    /** Default constructor */
    /** 
     * Constructor to create a new AlgorytmSelekcji object
     * 
     * @param Źródło źródło ścieżka obrazu z którego pobrany będzie obraz do selekcji 
     * @param czerwien ustawienie kanału czerwieni dla maski wykrywającej galaktyki
     * @param zielen ustawienie kanału zieleni dla maski wykrywającej galaktyki
     * @param niebieski ustawienie kanału niebieskiego dla maski wykrywającej galaktyki
     * @param kontrast ustawienie kontrastu dla maski wykrywającej galaktyki
     * @param progowanie rodzaj Progowania dla maski wykrywającej galaktyki
     * @param wartoscprogujaca wartość od której będzie progowanie
     */
    public AlgorytmSelekcji(String Źródło,double czerwien,double zielen,double niebieski,double kontrast,WyświetlaczObraz.RodzajeProgowania progowanie,int wartoscprogujaca){
    this.origin = readImage(Źródło);
    this.height = origin.getHeight();
    this.width = origin.getWidth();
    this.totalPixels = height* width;  
    this.pixels = new int[width * height];
    this.Źródło = Źródło;
    int TablicaObrazu[][]=convertTo2DWithoutUsingGetRGB(origin);
    for(int y = 0; y < this.height; y++){
        System.arraycopy(TablicaObrazu[y], 0, this.pixels, y*this.width, this.width);
        }
    this.pixels = ModyfikujKoloryWKanaleRGB(czerwien,zielen,niebieski,kontrast,progowanie,wartoscprogujaca);
    this.image = ZwróćObrazZTablicyJednowymiarowej(ModyfikujKoloryWKanaleRGB(czerwien,zielen,niebieski,kontrast,progowanie,wartoscprogujaca));
    }
    
 
    /** 
     * Zmiana parametrów służacych do selekcji galaktyk
     * 
     * @param plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata płaski pod przekątną próg zapełnienia jasnymi dla Soczewkowatych Galaktyk 
     * @param plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata płaski pod przekątną próg zapełnienia białymi dla Soczewkowatych Galaktyk
     * @param plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna płaski pod przekątną próg zapełnienia jasnymi dla Spiralnych Galaktyk
     * @param plaskisym_procent_zapelnienia_jasnymi_prog_karlowata płaski symetrycznie próg zapełnienia jasnymi dla karłowatych
     * @param plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna płaski symetrycznie próg zapełnienia jasnymi dla Spiralnych galaktyk
     * @param plaskisym_procent_zapelnienia_bialymi_prog_Spiralna płaski symetrycznie próg zapełnienia białymi dla Spiralnych galaktyk
     * @param plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata płaski symetrycznie próg zapełnienia jasnymi dla Soczewkowatych galaktyk
     * @param plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata płaski symetrycznie próg zapełnienia białymi dla Soczewkowatych galaktyk
     * @param pelny_procent_zapelnienia_jasnymi_prog_karlowata pełny próg zapełnienia jasnymi dla karłowatych galaktyk
     * @param pelny_procent_zapelnienia_bialymi_prog_karlowata pełny próg zapełnienia białymi dla karłowatych galaktyk
     * @param pelny_procent_zapelnienia_jasnymi_prog_Spiralna pełny próg zapełnienia jasnymi dla Spiralnych galaktyk
     * @param pelny_procent_zapelnienia_bialymi_prog_Spiralna pełny próg zapełnienia białymi dla Spiralnych galaktyk
     * @param pelny_liczba_jasnych_obiektow_Spiralna pełny próg ilość  jasnych dobiektów dla Spiralnych galaktyk
     * @param pelny_procent_zapelnienia_bialymi_prog_Eliptyczna pełny próg zapełnienia białymi dla Eliptycznych galaktyk
     * @param rozmycie_prog_Nieregularna rozmycie dla przypadku gdy galatkyka jest niregularna
     * @param prog_jasnosci_Nieregularna próg jasności dla galaktyk nieregularnych
     */
    public void ustawParametrySystemuDecyzyjnego( int plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,int plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,int plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,int plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,int plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna,int plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,int plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,int plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata,int pelny_procent_zapelnienia_jasnymi_prog_karlowata,double pelny_procent_zapelnienia_bialymi_prog_karlowata,int pelny_procent_zapelnienia_jasnymi_prog_Spiralna,int pelny_procent_zapelnienia_bialymi_prog_Spiralna,int pelny_liczba_jasnych_obiektow_Spiralna,int pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,int rozmycie_prog_Nieregularna,int prog_jasnosci_Nieregularna){
                                            //dla płaskich pod przekątną
                                            this.plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata=plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata;
                                            this.plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata=plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata;
                                            this.plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna=plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna;
                                                
                                            // dla plaskich symetrycznie     
                                            this.plaskisym_procent_zapelnienia_jasnymi_prog_karlowata=plaskisym_procent_zapelnienia_jasnymi_prog_karlowata;
                                            this.plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna=plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna;
                                            this.plaskisym_procent_zapelnienia_bialymi_prog_Spiralna=plaskisym_procent_zapelnienia_bialymi_prog_Spiralna;
                                            this.plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata=plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata;
                                            this.plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata=plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata; 
                                               
                                             // dla pelnego widoku      
                                            this.pelny_procent_zapelnienia_jasnymi_prog_karlowata=pelny_procent_zapelnienia_jasnymi_prog_karlowata;
                                            this.pelny_procent_zapelnienia_bialymi_prog_karlowata= pelny_procent_zapelnienia_bialymi_prog_karlowata; 
                                              
                                            this.pelny_procent_zapelnienia_jasnymi_prog_Spiralna=pelny_procent_zapelnienia_jasnymi_prog_Spiralna;
                                            this.pelny_procent_zapelnienia_bialymi_prog_Spiralna=pelny_procent_zapelnienia_bialymi_prog_Spiralna;
                                                
                                            this.pelny_liczba_jasnych_obiektow_Spiralna=pelny_liczba_jasnych_obiektow_Spiralna;
                                            this.pelny_procent_zapelnienia_bialymi_prog_Eliptyczna=pelny_procent_zapelnienia_bialymi_prog_Eliptyczna;
                                                
                                                
                                           //dla nieregularnych    
                                            this.rozmycie_prog_Nieregularna=rozmycie_prog_Nieregularna;
                                            this.prog_jasnosci_Nieregularna=prog_jasnosci_Nieregularna;   
    }
    
    
    public BufferedImage PobierzOryginał(){
        return origin;
    }
    public BufferedImage PobierzZmodyfikowanyObraz(){
        return image;
    }

 
    int Najjasniejszy(BufferedImage src ,int rozmyciex,int rozmyciey)
                                                    {
                                                      int Max=0;
                                                      if(rozmyciex==0||rozmyciey==0)
                                                      {
                                                      rozmyciex=1;
                                                      rozmyciey=1;
                                                      }
                                                      
                                                      byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
                                                      Mat opencv = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC3);
                                                      opencv.put(0, 0, data);
                                                      
                                                      Imgproc.blur(opencv, opencv, new Size(rozmyciex, rozmyciey));
                                                       
                                                      
                                                      
                                                      
                                                      for (int y = 0; y <(int)src.getHeight()/2; y++) 
                                                                                                {
                                                                                                  for (int x = 0; x<src.getWidth(); x++) 
                                                                                                  {
                                                                                                    double[] piksel = opencv.get(y, x);
                                                                                                    double r = piksel[0];
                                                                                                    double g = piksel[1];
                                                                                                    double b = piksel[2];
                                                                                                    if((0.2989 * r + 0.5870 * g + 0.1140 * b) >Max){
                                                                                                        Max= (int)((0.2989 * r + 0.5870 * g + 0.1140 * b));
                                                                                                    }
                                                                                                   
                                                                                                  }
                                                                                                }
  
                                                          return Max;
                                                        }
    
    
    
    

    boolean asymetryczny (BufferedImage src, int czulosc )
                                            
                                            {   
                                                  int Height = src.getHeight();
                                                  int Width = src.getWidth();

                                                                   int TablicaObrazu[][]=convertTo2DWithoutUsingGetRGB(src);
                                                                   
                                                                   
                                                                   int prawa_strona=0;
                                                                   int lewa_strona=0;
                                                                   int dolna_strona=0;
                                                                   int gorna_strona=0;

                                                                  
                                                                  
                                                                  
                                                                                            for (int y = 0; y <(int)Height/2; y++) 
                                                                                                {
                                                                                                  for (int x = 0; x<Width; x++) 
                                                                                                  {
                                                                                                 
                                                                                                   
                                                                                                    int r = (TablicaObrazu[y][x] >> 16) & 0xFF; 
                                                                                                    int g = (TablicaObrazu[y][x] >> 8) & 0xFF; 
                                                                                                    int b = TablicaObrazu[y][x] & 0xFF;
                                                                                                    if((0.2989 * r + 0.5870 * g + 0.1140 * b)>czulosc)
                                                                                                    {
                                                                                                      gorna_strona++;
                                                                                                    }
                                                                                                  }
                                                                                                }
                                                                  
                                                                                              
                                                                                           for (int y = (int)Height/2; y <Height; y++) 
                                                                                              {
                                                                                                 for (int x = 0; x<Width; x++) 
                                                                                                  {
                                                                                                  //double[] piksel = mat.get(y, x);
                                                                                                    int r = (TablicaObrazu[y][x] >> 16) & 0xFF; 
                                                                                                    int g = (TablicaObrazu[y][x] >> 8) & 0xFF; 
                                                                                                    int b = TablicaObrazu[y][x] & 0xFF;
                                                                                                  if((0.2989 * r + 0.5870 * g + 0.1140 * b)>czulosc)
                                                                                                  {
                                                                                                    dolna_strona++;
                                                                                                  }
                                                                                                }
                                                                                              }
                                                                  
                                                                  
                                                                                                  
                                                                                            for (int y = 0; y <Height; y++) 
                                                                                                  {
                                                                                                    for ( int x = 0; x<(int)Width/2; x++) 
                                                                                                    {
                                                                                                       //double[] piksel = mat.get(y, x);
                                                                                                        int r = (TablicaObrazu[y][x] >> 16) & 0xFF; 
                                                                                                    int g = (TablicaObrazu[y][x] >> 8) & 0xFF; 
                                                                                                    int b = TablicaObrazu[y][x] & 0xFF;
                                                                                                     if((0.2989 * r + 0.5870 * g + 0.1140 * b)>czulosc)
                                                                                                      {
                                                                                                        lewa_strona++;
                                                                                                      }
                                                                                                    }
                                                                                                  }
                                                                  
                                                                  
                                                                  
                                                                                             for ( int y = 0; y <Height; y++) 
                                                                                                  {
                                                                                                    for ( int x = (int)Width/2; x<Width; x++) 
                                                                                                    {
                                                                                                        //double[] piksel = mat.get(y, x);
                                                                                                    int r = (TablicaObrazu[y][x] >> 16) & 0xFF; 
                                                                                                    int g = (TablicaObrazu[y][x] >> 8) & 0xFF; 
                                                                                                    int b = TablicaObrazu[y][x] & 0xFF;
                                                                                                        if((0.2989 * r + 0.5870 * g + 0.1140 * b)>czulosc)
                                                                                                              {
                                                                                                                prawa_strona++;
                                                                                                              }
                                                                                                    }
                                                                                                  }
                                                                                                  
                                                                  
                                                                  
                                                                  boolean asymetria=false; 
                                                                  
                                                                  if(prawa_strona<lewa_strona*0.66)
                                                                  {
                                                                    asymetria =  true;
                                                                  }
                                                                 else if(prawa_strona*0.66>lewa_strona)
                                                                  {
                                                                    asymetria =  true;
                                                                  }  
                                                                 else if(dolna_strona<gorna_strona*0.66)
                                                                  {
                                                                    asymetria =  true;
                                                                  }
                                                                 else if(dolna_strona*0.66>gorna_strona)
                                                                  { 
                                                                    asymetria =  true;
                                                                  }
                                                                  
                                                                    return asymetria;
                          }

    
    
   public  int liczba_jader(BufferedImage src,int rozmyciex,int rozmyciey ,int czulosc,int min_wielkoscx ,int min_wielkoscy)
                                                                          {
                                                                          
                                                                            int iloscjader = 0;
                                                                            int MinX = src.getWidth();
                                                                            int MinY = src.getHeight();
                                                                            int MaxX=0;
                                                                            int MaxY=0;
                                                                            
                                                                            
                                                                            //inicjalizacja 
                                                                            ArrayList<MatOfPoint> contours = new ArrayList<>(); 
                                                                            Mat hierarchy = new Mat();
                       
                                                                            byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
                                                                            
                                                                            Mat opencv = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC3);
                                                                            opencv.put(0, 0, data);
                                                                            
                                                                             
                                                                           // image = MatDoBufferedImage(opencv); 
                                                                                    
                                                                                      if(!(rozmyciex==0||rozmyciey==0))
                                                                                                              {
                                                                                                              Imgproc.blur(opencv, opencv, new Size(rozmyciex, rozmyciey));
                                                                                                              }
                                                                                      
                                                                                      
                                                                            // macierz w skali szarosci          
                                                                            Mat grayscaleMat  = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC1); 
                                                                            
                                                                            //konwert z koloru do szarosci
                                                                            Imgproc.cvtColor(opencv, grayscaleMat, Imgproc.COLOR_BGR2GRAY);  
                                                                            //inicjacja maski binarnej
                                                                            Mat maskaobrazu = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC1);
                                                                            // fuckja tresholde wykorzystywana do wyodrebnienia obiektow         
                                                                            Imgproc.threshold(grayscaleMat,maskaobrazu , czulosc,  255, Imgproc.THRESH_BINARY);
                                                                            // funkcja find contours do znajodownia punktow oraz obiektow      
                                                                          
                                                                            Imgproc.findContours(maskaobrazu, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE); 
                                                                            
                                                                            
                                                                           
                                                                   
                                                                            
                                                                            
                                                                                for( MatOfPoint mop: contours )
                                                                                {
                                                                                                    // for each contour, display it in blue
                                                                                                    
                                                                                                           
                                                                                        for( Point p: mop.toList() )
                                                                                              {
                                                                                                   if(MaxX<p.x)
                                                                                                   {
                                                                                                    MaxX=(int)p.x;
                                                                                                   }
                                                                                                   if(MaxY<p.y){
                                                                                                   MaxY=(int)p.y;
                                                                                                   }
                                                                                                   if(MinX>p.x)
                                                                                                   {
                                                                                                    MinX=(int)p.x;
                                                                                                   }
                                                                                                   if(MinY>p.y)
                                                                                                   {
                                                                                                   MinY=(int)p.y; 
                                                                                                   }                                                      
                                                                                              }


                                                                                                 int Szerokosc=MaxX-MinX;
                                                                                                 int Wysokosc=MaxY-MinY;
                                                                                                       if(min_wielkoscx<=Szerokosc||min_wielkoscy<=Wysokosc)
                                                                                                           {      
                                                                                                             iloscjader++;
                                                                                                           }

                                                                             }
                                                                                                    
                                                                                                    
                                                                                                    
                                                                                               //System.out.println("MinX "+MinX+" MaxX "+MaxX+" MinY "+MinY+" MaxY "+MaxY); 
                                                                                              
                                                                                                  
                                                                                               
                                                                                                       
                                                                                      
                                                                           
                                                                          
                                                                          return iloscjader;
                                                                          }
     
   
   int rozpoznanie(int rozmycie, int czulosc,int min_wielkoscx ,int min_wielkoscy)
{
                                                
                               if(PGorny.size()>0)
                                                    {
                                                      
                                                                 PGorny.clear();
                                                                 PDolny.clear();
                                                                 PPrawy.clear();
                                                                 PLewy.clear();
                                                                 TypGalaktykiNazwa.clear();
                                                                 ListaGalaktyk.clear();
                                                                 ListaGalaktykBufor.clear();
                                                                           
                                                    }
                                                
                                                
                                                int iloscgalaktyk=0;
                                                
                                        ArrayList<MatOfPoint> contours = new ArrayList<>(); 
                                                                            Mat hierarchy = new Mat();
                       
                                                                            byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                                                                            
                                                                            Mat opencv = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
                                                                            opencv.put(0, 0, data);
                                                                            
                                                                                    
                                                                                      if(!(rozmycie==0))
                                                                                                              {
                                                                                                              Imgproc.blur(opencv, opencv, new Size(rozmycie, rozmycie));
                                                                                                              }
                                                                                      
                                                                                     
                                                                            // macierz w skali szarosci          
                                                                            Mat grayscaleMat  = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC1); 
                                                                            //konwert z koloru do szarosci
                                                                            Imgproc.cvtColor(opencv, grayscaleMat, Imgproc.COLOR_BGR2GRAY);  
                                                                            //inicjacja maski binarnej
                                                                            Mat maskaobrazu = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC1);
                                                                            // fuckja tresholde wykorzystywana do wyodrebnienia obiektow         
                                                                            Imgproc.threshold(grayscaleMat,maskaobrazu , czulosc,  czulosc, Imgproc.THRESH_BINARY);
                                                                            // funkcja find contours do znajodownia punktow oraz obiektow      
                                                                            Imgproc.findContours(maskaobrazu, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);          
                                                                                      
// petla glowna dla rysowania wsztstkich galaktyk 


                                    
                                                                            int MinX = image.getWidth();
                                                                            int MinY = image.getHeight();
                                                                            int MaxX=0;
                                                                            int MaxY=0;                   
                                                                            Point Góra = null;
                                                                            Point Dół = null;
                                                                            Point Prawo = null;
                                                                            Point Lewo = null;
                                                                            int PoczatekX,PoczatekY,Wysokosc,Szerokosc;
                                                                                                      
                                                                                                    
                               for( MatOfPoint mop: contours )
                                    {
                                        
                                     for( Point p: mop.toList() )
                                            {
                                                        if(MaxX<p.x)
                                                        {
                                                        MaxX=(int)p.x;
                                                        Prawo=p;
                                                        }
                                                        if(MaxY<p.y)
                                                        {
                                                        MaxY=(int)p.y;
                                                        Dół = p;
                                                        }
                                                        if(MinX>p.x)
                                                        {
                                                        MinX=(int)p.x;
                                                        Lewo = p;
                                                        }
                                                        if(MinY>p.y)
                                                        {
                                                        MinY=(int)p.y;
                                                        Góra = p;
                                                        }                                                      
                                            } 
                                            PoczatekX=MinX;
                                            PoczatekY=MinY;
                                            Szerokosc=MaxX-MinX;
                                            Wysokosc=MaxY-MinY;
                                            
                                             
                                           
                                           
                                           
                                           
                                           if(min_wielkoscx<Szerokosc||min_wielkoscy<Wysokosc)
                                         
                                                                   {
                                                                       
                                                                      ListaGalaktykBufor.add(PobierzWycinekObrazu(MatDoBufferedImage(opencv),PoczatekX, PoczatekY, Szerokosc, Wysokosc));
                                                                      ListaGalaktyk.add(PobierzWycinekObrazu(origin,PoczatekX, PoczatekY, Szerokosc, Wysokosc));
                                                                      PGorny.add(Góra);
                                                                      PDolny.add(Dół);
                                                                      PPrawy.add(Prawo);
                                                                      PLewy.add(Lewo);
                                                                      iloscgalaktyk++;
                                                                   }
                                                                          
                                          MinX = image.getWidth();
                                          MinY = image.getHeight();
                                          MaxX=0;
                                          MaxY=0;                                
                                                                          
                                     }
                                                                                                    
                                                                                                    
                                                                                                    
                                            
                                          
                                            
                                           
                                    
                          
                 
                            for(int i=0 ; i<ListaGalaktyk.size() ; i++)
                                    {
                                                Galaktyka WykrytaGalaktyka = new Galaktyka(Źródło,ListaGalaktyk.get(i));                                        
                                                WykrytaGalaktyka.setPunkty(PGorny.get(i), PPrawy.get(i), PDolny.get(i), PLewy.get(i));
                                                String widok;
                                                double prawygorny =  distance(PGorny.get(i).x,PGorny.get(i).y, PPrawy.get(i).x, PPrawy.get(i).y);
                                                double lewygorny  = distance(PGorny.get(i).x,PGorny.get(i).y, PLewy.get(i).x, PLewy.get(i).y);
                                                double prawydolny = distance(PDolny.get(i).x,PDolny.get(i).y, PPrawy.get(i).x, PPrawy.get(i).y);
                                                double lewydolny  = distance(PDolny.get(i).x,PDolny.get(i).y, PLewy.get(i).x, PLewy.get(i).y);
                                                double przekatnapion  = distance(PDolny.get(i).x,PDolny.get(i).y, PGorny.get(i).x, PGorny.get(i).y);
                                                double przekatnapoziom = distance(PPrawy.get(i).x,PPrawy.get(i).y, PLewy.get(i).x, PLewy.get(i).y);
                                                
                                                ///od tąd trza posprawdzac;
                                                
                                                
                                                //fragment kodu do znajdowania małych wyraznych gwiazd
                                                int najasniejszypkt=Najjasniejszy(ListaGalaktyk.get(i),(int)(ListaGalaktyk.get(i).getWidth()*0.03),(int)(ListaGalaktyk.get(i).getHeight()*0.03));
                                                int liczba_jader=liczba_jader(ListaGalaktyk.get(i),1,1,najasniejszypkt,(int)(ListaGalaktyk.get(i).getWidth()*0.01),(int)(ListaGalaktyk.get(i).getHeight()*0.01));
                                                
                                                
                                                //fragment odpowiedzialny za znajdowania centra galaktyki
                                                int najasniejszypkt_centrum=Najjasniejszy(ListaGalaktyk.get(i),1,1);
                                                int liczba_jader_centrum=liczba_jader(ListaGalaktyk.get(i),1,1,najasniejszypkt_centrum,0,0);
                                                            if(prawygorny>2*prawydolny&&lewydolny>2*lewygorny)
                                                               {
                                                                 widok="Płaski pod przekątną"; 
                                                                 WykrytaGalaktyka.setWidokNaGalaktyke(Widok.Płaski_pod_przekątną);
                                                               }
                                                             else if(prawydolny>2*prawygorny&&lewygorny>2*lewydolny)
                                                               {
                                                                 widok="Płaski pod przekątną"; 
                                                                 WykrytaGalaktyka.setWidokNaGalaktyke(Widok.Płaski_pod_przekątną);
                                                               }
                                                             else if(przekatnapion>2*przekatnapoziom||przekatnapoziom>2*przekatnapion)
                                                               {
                                                                 widok="Płaski symetrycznie";  
                                                                 WykrytaGalaktyka.setWidokNaGalaktyke(Widok.Płaski_symetrycznie);
                                                               }
                                                            else
                                                               {
                                                                 widok = "Pełny";
                                                                 WykrytaGalaktyka.setWidokNaGalaktyke(Widok.Pełny);
                                                               }
                                                   
                                                   
                                                   
                                                   
                                                  int progowanie = 90;
                                                  double pixele_jasne=0;
                                                  double pixele_biale=0;
                                                  
                                                   
                                                   //tu sie będzie trza pomęczyć 
                                                  
                                                  
                                                  int Width =ListaGalaktyk.get(i).getWidth();
                                                  int Height =ListaGalaktyk.get(i).getHeight();
                                                  
                                                  
                                                  int[][] TablicaMaskiObrazu = convertTo2DWithoutUsingGetRGB(ListaGalaktykBufor.get(i));
                                                  int[][] TablicaOryginalnegoPobranegoObrazu = convertTo2DWithoutUsingGetRGB(ListaGalaktyk.get(i));
                                                  
                                                  double  liczbapixeliobiektu=0;
                                                   for (int y = 0; y <Height; y++) 
                                                            {
                                                                  for (int x = 0; x<Width; x++) 
                                                                      {
                                                                       
                                                                                  int r = (TablicaMaskiObrazu[y][x] >> 16) & 0xFF; 
                                                                                  int g = (TablicaMaskiObrazu[y][x] >> 8) & 0xFF; 
                                                                                  int b = TablicaMaskiObrazu[y][x] & 0xFF;  
                                                                                    //System.out.print(r+" "+g+" "+b+" ");
                                                                                                if((0.2989 * r + 0.5870 * g + 0.1140 * b)>0){
                                                                                                            liczbapixeliobiektu++;         
                                                                                                                    
                                                                                                                   r = (TablicaOryginalnegoPobranegoObrazu[y][x] >> 16) & 0xFF; 
                                                                                                                   g = (TablicaOryginalnegoPobranegoObrazu[y][x] >> 8) & 0xFF; 
                                                                                                                   b = TablicaOryginalnegoPobranegoObrazu[y][x] & 0xFF; 

                                                                                                                          if((0.2989 * r + 0.5870 * g + 0.1140 * b)>progowanie)    
                                                                                                                               {
                                                                                                                                   //System.out.print(x+" "+ y);
                                                                                                                                   pixele_jasne++;
                                                                                                                               }


                                                                                                                          if((0.2989 * r + 0.5870 * g + 0.1140 * b)>220)    
                                                                                                                               {
                                                                                                                                   pixele_biale++;
                                                                                                                               }


                                                                                                                }
                                                                      }
                                                                  //System.out.println();
                                                            }
                                                  
                                                 // int  liczbapixeliobiektu=0;
                                                   
                                               
                                                  
                                                  
                                                //aż do tąd   
                                                double procent_zapelnienia_jasnymi=pixele_jasne/liczbapixeliobiektu*100;
                                                double procent_zapelnienia_bialymi=pixele_biale/liczbapixeliobiektu*100;
                                                WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Niezakwalifikowany);
                                                String typ_galaktyki="Niezakwalifikowany";
                                                wykrytych_galaktyk++;
                                                
                                                
                                                
                                                 // ustalanie rodzaju galaktyki 
                                                
                                                 
                                                        if(WykrytaGalaktyka.getWidokNaGalaktyke()==Widok.Płaski_pod_przekątną)
                                                        {
                                                                      if(procent_zapelnienia_jasnymi<plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna)
                                                                                {
                                                                                 WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Spiralna);   
                                                                                 typ_galaktyki="Spiralna";
                                                                                  if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                                      else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Soczewkowata);
                                                                                      typ_galaktyki="Soczewkowata";
                                                                                      }
                                                                                
                                                                      else if (procent_zapelnienia_jasnymi>plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata)
                                                                                {
                                                                                WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Soczewkowata);    
                                                                                typ_galaktyki="Soczewkowata";
                                                                                 if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                                                
                                                                                else if (procent_zapelnienia_bialymi>plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata)
                                                                                {
                                                                                WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Soczewkowata);
                                                                                typ_galaktyki="Soczewkowata";
                                                                                 if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                                                 
                                                                               
                                                                     else   
                                                                                {
                                                                                WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Spiralna);
                                                                                typ_galaktyki="Spiralna";
                                                                                 if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                        }
                                                     
                                                      if(WykrytaGalaktyka.getWidokNaGalaktyke()==Widok.Płaski_symetrycznie)
                                                      {
                                                                                if(procent_zapelnienia_jasnymi<plaskisym_procent_zapelnienia_jasnymi_prog_karlowata)
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Karłowata);     
                                                                                      typ_galaktyki="Karłowata";
                                                                                       if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                      }
                                                                                else if(procent_zapelnienia_jasnymi<plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna&&procent_zapelnienia_bialymi<plaskisym_procent_zapelnienia_bialymi_prog_Spiralna)
                                                                                      {
                                                                                       WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Spiralna);   
                                                                                       typ_galaktyki="Spiralna";
                                                                                        if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                      }
                                                                                else if (procent_zapelnienia_bialymi>plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata||procent_zapelnienia_jasnymi>plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata) 
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Soczewkowata);      
                                                                                      typ_galaktyki="Soczewkowata";
                                                                                       if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                      
                                                                                      }
                                                                                       else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Soczewkowata);     
                                                                                      typ_galaktyki="Soczewkowata";
                                                                                      }
                                                                                      else {
                                                                                        
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Spiralna);
                                                                                      typ_galaktyki="Spiralna";
                                                                                      
                                                                                      }
                                                      }
                                                       
                                                       
                                                       
                                                       
                                                       
                                                       
                                                       if(WykrytaGalaktyka.getWidokNaGalaktyke()==Widok.Pełny)
                                                       {
                                                                             if(procent_zapelnienia_jasnymi<pelny_procent_zapelnienia_jasnymi_prog_karlowata&&procent_zapelnienia_bialymi<pelny_procent_zapelnienia_bialymi_prog_karlowata)
                                                                             {
                                                                                    typ_galaktyki="Nieregularna";
                                                                                    WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Nieregularna);
                                                                                         if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                             }
                                                                             
                                                                             else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Eliptyczna);    
                                                                                      typ_galaktyki="Eliptyczna";
                                                                                      }
                                                                             
                                                                           else if(procent_zapelnienia_jasnymi < pelny_procent_zapelnienia_jasnymi_prog_Spiralna &&  procent_zapelnienia_bialymi<pelny_procent_zapelnienia_bialymi_prog_Spiralna&&liczba_jader>pelny_liczba_jasnych_obiektow_Spiralna&&!(liczba_jader_centrum==1&&liczba_jader==1))
                                                                                      {
                                                                                       WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Spiralna);    
                                                                                       typ_galaktyki="Spiralna";
                                                                                         if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                       
                                                                                      }
                                                                                else if(procent_zapelnienia_bialymi>pelny_procent_zapelnienia_bialymi_prog_Eliptyczna)
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Eliptyczna);     
                                                                                      typ_galaktyki="Eliptyczna";
                                                                                         if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                            
                                                                                      }
                                                                                  
                                                                                  else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                     WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Eliptyczna);        
                                                                                      typ_galaktyki="Eliptyczna";
                                                                                      }    
                                                                               
                                                                                      else 
                                                                                      {
                                                                                      WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Spiralna);         
                                                                                      typ_galaktyki="Spiralna";
                                                                                      }
                                                                      
                                                                    
                                                        }
                                                      
                                                 
                                                
                                                //System.out.println(ListaGalaktyk.get(i).getWidth()+ " Szerokosc");
                                                //System.out.println(ListaGalaktyk.get(i).getHeight()+ " Wysokosc");
                                                if(asymetryczny ( ListaGalaktykBufor.get(i), prog_jasnosci_Nieregularna ))
                                                {
                                                WykrytaGalaktyka.setTypGalaktyki(TypGalaktyki.Nieregularna);       
                                                typ_galaktyki="Nieregularna";
                                                }
                                                ZapisDoObrazu.add("W-"+widok+" T-"+typ_galaktyki+" PJ-"+String.format("%.2f", (float)procent_zapelnienia_jasnymi)+" PB-"+String.format("%.2f", (float)procent_zapelnienia_bialymi)+" LP-"+liczba_jader+" JG-"+liczba_jader_centrum); 
                                                TypGalaktykiNazwa.add("Widok: "+widok+" Pixele Jasne:"+String.format("%.2f", (float)procent_zapelnienia_jasnymi)+" % Pixele białe:"+String.format("%.2f", (float)procent_zapelnienia_bialymi)+" %\nRodzaj Galaktyki: "+typ_galaktyki+"\nLiczba jasnych punktów: "+liczba_jader+" Jądra galaktyk: "+liczba_jader_centrum);
                                                WykrytaGalaktyka.setPikseleBiałe(procent_zapelnienia_bialymi);
                                                WykrytaGalaktyka.setPikseleJasne(procent_zapelnienia_jasnymi);
                                                WykrytaGalaktyka.setLiczbaJasnychPunktów(liczba_jader);
                                                WykrytaGalaktyka.setLiczbaJąderGalaktyki(liczba_jader_centrum);
                                                DanaGalaktyka.add(WykrytaGalaktyka);
                                                
                                    
                                    }
 

      
         
 
            for(int t = 0; t<ListaGalaktyk.size() ; t++)
                          {
                           WczytaneListaGalaktyk.add(ListaGalaktyk.get(t));
                           WczytaneTypGalaktykiNazwa.add(TypGalaktykiNazwa.get(t));
                          }
                          
                          return iloscgalaktyk;
  
}
   public ArrayList<Galaktyka> ListaWykrytychGalaktyk(){
       return DanaGalaktyka;
   }
   
   private static BufferedImage PobierzWycinekObrazu(BufferedImage Obraz ,int startX, int startY, int endX,int  endY){
       BufferedImage img = Obraz.getSubimage(startX, startY, endX, endY); //fill in the corners of the desired crop location here
       BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
       Graphics g = copyOfImage.createGraphics();
       g.drawImage(img, 0, 0, null);
       return copyOfImage;
   }
   
   
   
   
    public static BufferedImage MatDoBufferedImage(Mat matrix)
{
    
    BufferedImage bimg;
    if ( matrix != null ) { 
        int cols = matrix.cols();  
        int rows = matrix.rows();  
        int elemSize = (int)matrix.elemSize();  
        byte[] data = new byte[cols * rows * elemSize];  
        int type;  
        matrix.get(0, 0, data);  
        switch (matrix.channels()) {  
        case 1:  
            type = BufferedImage.TYPE_BYTE_GRAY;  
            break;  
        case 3:  
            type = BufferedImage.TYPE_3BYTE_BGR;  
            // bgr to rgb  
            byte b;  
            for(int i=0; i<data.length; i=i+3) {  
                b = data[i];  
                data[i] = data[i+2];  
                data[i+2] = b;  
            }  
            break;  
        default:  
            return null;  
        }  

        // Reuse existing BufferedImage if possible
        
            bimg = new BufferedImage(cols, rows, type);
               
        bimg.getRaster().setDataElements(0, 0, cols, rows, data);
    } else { // mat was null
        bimg = null;
    }
    return bimg;  
}   
    /////////////////////////////////////// METHODS ////////////////////////////
    
    /**
     * This method will modify the image object.
     * 
     * @param width The width of the new image.
     * @param height The height of the new image.
     * @param bi The new image that will replace the old image.
     */
    
    
    /** 
     * Read the image using the image file path passed
     * 
     * @param filePath the path of the image file
     * Example filePath = "D:\\LogoJava.jpg"
     */
    public BufferedImage readImage(String filePath){
        BufferedImage image = null;
        try{
            File f = new File(filePath);
            image = ImageIO.read(f);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            
            this.width = image.getWidth();
            this.height = image.getHeight();
           
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
        return image;
    }
    
    /**
     * Write the content of the BufferedImage object 'image' to a file
     * 
     * @param filePath complete file path of the output image file to be created
     * Example filePath = "D:\\Output.jpg"
     */
    public void writeImage(String filePath){
        try{
            File f = new File(filePath);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            ImageIO.write(image, fileType, f);
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
    }
    
 
   
    
    /**
     * This method will check for equality of two images.
     * If we have two image img1 and img2
     * Then calling img1.isEqual(img2) will return TRUE if img1 and img2 are equal
     * else it will return FALSE.
     * 
     * @param img The image to check with.
     * @return TRUE if two images are equal else FALSE.
     */
    public boolean isEqual(AlgorytmSelekcji img){
        //check dimension
        if(this.width != img.getImageWidth() || this.height != img.getImageHeight()){
            return false;
        }
        
        //check pixel values
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                if(this.pixels[x+y*this.width] != img.getPixel(x, y)){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /////////////////////////// GET, SET AND UPDATE METHODS ////////////////////
    
    /**
     * Return the image width
     * 
     * @return the width of the image
     */
    public int getImageWidth(){
        return width;
    }
    
    /**
     * Return the image height
     * 
     * @return the height of the image
     */
    public int getImageHeight(){
        return height;
    }
    
    /**
     * Return total number of pixels in the image
     * 
     * @return the total number of pixels
     */
    public int PobierzLiczbeWszystkichPikseliWObrazie(){
        return totalPixels;
    }
    
    /**
     * This method will return the amount of alpha value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of alpha (transparency)
     * 
     * 0 means transparent
     * 255 means opaque
     */
    public int getAlpha(int x, int y){
        return (pixels[x+(y*width)] >> 24) & 0xFF;
    }
    
    /**
     * This method will return the amount of red value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of red
     * 
     * 0 means none
     * 255 means fully red
     */
    public int getRed(int x, int y){
        return (pixels[x+(y*width)] >> 16) & 0xFF;
    }
    
    /**
     * This method will return the amount of green value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel 
     * @return the amount of green
     * 
     * 0 means none
     * 255 means fully green
     */
    public int getGreen(int x, int y){
        return (pixels[x+(y*width)] >> 8) & 0xFF;
    }
    
    /**
     * This method will return the amount of blue value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the amount of blue
     * 
     * 0 means none
     * 255 means fully blue
     */
    public int getBlue(int x, int y){
        return pixels[x+(y*width)] & 0xFF;
    }
    
    /**
     * This method will return the pixel value of the pixel at the coordinate (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the pixel value in integer [Value can be negative and positive.]
     */
    public int getPixel(int x, int y){
        return pixels[x+(y*width)];
    }
    
    /**
     * This method will return the pixel value of the image as 1D array.
     * 
     * @return 1D array containing value of each pixels of the image.
     */
    public int[] getPixelArray(){
        return pixels;
    }
        
    /**
     * This method will set the amount of alpha value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param alpha the alpha value to set
     * 
     * 0 means transparent
     * 255 means opaque
     */
    public void setAlpha(int x, int y, int alpha){
        pixels[x+(y*width)] = (alpha<<24) | (pixels[x+(y*width)] & 0x00FFFFFF);
        ZaktualizujPikselWKoordynacie(x,y);
    }
    
    /**
     * This method will set the amount of red value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param red the red value to set
     * 
     * 0 means none
     * 255 means fully red
     */
    public void setRed(int x, int y, int red){
        pixels[x+(y*width)] = (red<<16) | (pixels[x+(y*width)] & 0xFF00FFFF);
        ZaktualizujPikselWKoordynacie(x,y);
    }
    
    /**
     * This method will set the amount of green value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param green the green value to set
     * 
     * 0 means none
     * 255 means fully green
     */
    public void setGreen(int x, int y, int green){
        pixels[x+(y*width)] = (green<<8) | (pixels[x+(y*width)] & 0xFFFF00FF);
        ZaktualizujPikselWKoordynacie(x,y);
    }
    
    /**
     * This method will set the amount of blue value between 0-255 at the pixel (x,y)
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param blue the blue value to set
     * 
     * 0 means none
     * 255 means fully blue
     */
    public void setBlue(int x, int y, int blue){
        pixels[x+(y*width)] = blue | (pixels[x+(y*width)] & 0xFFFFFF00);
        ZaktualizujPikselWKoordynacie(x,y);
    }
    
    /**
     * This method will set pixel(x,y) to ARGB value.
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param alpha the alpha value to set [value from 0-255]
     * @param red the red value to set [value from 0-255]
     * @param green the green value to set [value from 0-255]
     * @param blue the blue value to set  [value from 0-255]
     */
    public void setPixel(int x, int y, int alpha, int red, int green, int blue){
        pixels[x+(y*width)] = (alpha<<24) | (red<<16) | (green<<8) | blue;
        ZaktualizujPikselWKoordynacie(x,y);
    }
    
    /**
     * This method will set pixel (x,y) to pixelValue.
     * 
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param pixelValue the pixel value to set
     */
    public void setPixelToValue(int x, int y, int pixelValue){
        pixels[x+(y*width)] = pixelValue;
        ZaktualizujPikselWKoordynacie(x,y);
    }
    
    /**
     * This method will set the image pixel array to the value of the 1D pixelArray.
     * 
     * @param pixelArray 1D array containing values that is set to the image pixel array.77
     */
    public void setPixelArray(int pixelArray[]){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixels[x+y*width] = pixelArray[x+y*width];
                ZaktualizujPikselWKoordynacie(x,y);
            }
        }
    }
    
    
    
    /**
     * This method will update the image pixel at coordinate (x,y)
     * 
     * @param x the x coordinate of the pixel that is set
     * @param y the y coordinate of the pixel that is set
     */
    private void ZaktualizujPikselWKoordynacie(int x, int y){
        image.setRGB(x, y, pixels[x+(y*width)]);
    }
 
    public BufferedImage getImage(){
        return image;
    }
    
    
    private BufferedImage ZwróćObrazZTablicyJednowymiarowej(int pixelArray[]){
        BufferedImage Out = new BufferedImage( width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixels[x+y*width] = pixelArray[x+y*width];
               Out.setRGB(x, y, pixels[x+(y*width)]);
            }
        }
        return Out;
    }
    
@SuppressWarnings("empty-statement")
   public int[] ModyfikujKoloryWKanaleRGB(double czerwien,double zielen,double niebieski,double kontrast,WyświetlaczObraz.RodzajeProgowania progowanie,int wartoscprogujaca){
       
       int height = getImageHeight();
       int width = getImageWidth();
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KanalAlpha [] = new int[PobierzLiczbeWszystkichPikseliWObrazie()];;
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KolorCzerwony[] = new int[PobierzLiczbeWszystkichPikseliWObrazie()];;
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KolorZielony[] = new int[PobierzLiczbeWszystkichPikseliWObrazie()];;
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int KolorNiebieski[] = new int[PobierzLiczbeWszystkichPikseliWObrazie()];;
       
       
       
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                KanalAlpha[x+width*y]= (pixels[x+(y*width)] >> 24) & 0xFF;
                KolorCzerwony[x+width*y]= (pixels[x+(y*width)] >> 16) & 0xFF;
                KolorZielony[x+width*y]= (pixels[x+(y*width)] >> 8) & 0xFF;
                KolorNiebieski[x+width*y]= pixels[x+(y*width)] & 0xFF;
                }
            }
      
       
       
       @SuppressWarnings("MismatchedReadAndWriteOfArray")
       int WyjsciowyRGB[] = new int[PobierzLiczbeWszystkichPikseliWObrazie()];
        
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
  
  
   public int[][] PobierzTabliceZKanałami(){
        
       int[][] Tablica = new int[PobierzLiczbeWszystkichPikseliWObrazie()][3];
       for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Tablica[x+width*y][0]= (pixels[x+(y*width)] >> 24) & 0xFF;
                Tablica[x+width*y][1]= (pixels[x+(y*width)] >> 16) & 0xFF;
                Tablica[x+width*y][2]= (pixels[x+(y*width)] >> 8) & 0xFF;
                Tablica[x+width*y][3]= pixels[x+(y*width)] & 0xFF;
                }
            }
          return Tablica;
        }
   
   
   
   
   
   
          public int[] PobierzTabliceZKanałemAlfa(){
        
        int[] Tablica = new int[PobierzLiczbeWszystkichPikseliWObrazie()];
       for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Tablica[x+width*y]= (pixels[x+(y*width)] >> 24) & 0xFF;
                }
            }
          return Tablica;
        } 
          
          
          
          
          
          
          
           public int[] PobierzTabliceZKanałemCzerwieni(){
        
        int[] Tablica = new int[PobierzLiczbeWszystkichPikseliWObrazie()];
       for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Tablica[x+width*y]= (pixels[x+(y*width)] >> 16) & 0xFF;
                }
            }
          return Tablica;
        } 
           
           public int[] PobierzTabliceZKanałemZieleni(){
        
        int[] Tablica = new int[PobierzLiczbeWszystkichPikseliWObrazie()];
       for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Tablica[x+width*y]= (pixels[x+(y*width)] >> 8) & 0xFF;
                }
            }
          return Tablica;
        }
           
           public int[] PobierzTabliceZKanałemNiebieskim(){
        
        int[] Tablica = new int[PobierzLiczbeWszystkichPikseliWObrazie()];
       for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Tablica[x+width*y]= pixels[x+(y*width)] & 0xFF;
                }
            }
          return Tablica;
        }
           
           
           
           
           
          
 private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
      if(image.getType()!=BufferedImage.TYPE_4BYTE_ABGR){
      BufferedImage convertedImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      convertedImg.getGraphics().drawImage(image, 0, 0, null);
      image= convertedImg;
      }
      
      
      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      int[][] result = new int[height][width];
      
         final int pixelLength = 4;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      return result;
   }    
      
      
    }
    
    
    
    
            
