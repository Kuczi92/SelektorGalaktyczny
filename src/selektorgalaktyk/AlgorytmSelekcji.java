/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;


import static java.awt.geom.Point2D.distance;
import org.opencv.core.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelGrabber;
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
    
    
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }



 
    
    //////////////////////////// Zmienne /////////////////////////////////////
    
    /** Przechowanie obrazu */
    private BufferedImage image;
    
    /** Store the image width and height */
    private int width, height;
    
    /** Pixels value - ARGB */
    private int pixels[];
    
    /** Total number of pixel in an image*/
    private int totalPixels;
    
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
                                                int rozmycie_prog_Nieregularna=20;
                                                int prog_jasnosci_Nieregularna=128;
                                                
                                                
                                                 int rozmycie=30;
                                                 int czulosc=40;
                                                 int value = 5;
                                                 int min_wielkoscx=50;
                                                 int min_wielkoscy=50;
                                                 
                                                int jasnosc=-65;
                                                double kontrast=5.05;
                                                int Wartosc_Progowa=74;
    
    /** 
     * Image type example: jpg|png 
     * JPG does not support alpha (transparency is lost) while PNG supports alpha.
     */
    private enum ImageType{
        JPG, PNG
    };
    
    private ImageType imgType;
    
    ////////////////////////////////// CONSTRUCTORS ////////////////////////////
    
    /** Default constructor */
    public AlgorytmSelekcji(){
    
    }
    
    /** 
     * Constructor to create a new image object
     * 
     * @param width width of the image passed by the user
     * @param height height of the image passed by the user
     */
    public AlgorytmSelekcji(int width, int height){
        
         
        this.width = width;
        this.height = height;
        this.totalPixels = this.width * this.height;
        this.pixels = new int[this.totalPixels];
        image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.imgType = ImageType.PNG;
        initPixelArray();
    }
    
    /** 
     * Constructor to create a copy of a previously created image object.
     * 
     * @param img The image object whose copy is created.
     */
    public AlgorytmSelekcji(AlgorytmSelekcji img){
       
        this.width = img.getImageWidth();
        this.height = img.getImageHeight();
        this.totalPixels = this.width * this.height;
        this.pixels = new int[this.totalPixels];
        
        this.imgType = img.imgType;
        if(this.imgType == ImageType.PNG){
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }else{
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }
        
        //copy original image pixels value to new image and pixels array
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                this.image.setRGB(x, y, img.getPixel(x, y));
                this.pixels[x+y*this.width] = img.getPixel(x, y);
            }
        }
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
                                                                                                    float r = (float) piksel[0];
                                                                                                    float g = (float) piksel[1];
                                                                                                    float b = (float) piksel[2];
                                                                                                    if((r+g+b)/3 >Max){
                                                                                                        Max= (int)(r+g+b)/3;
                                                                                                    }
                                                                                                   
                                                                                                  }
                                                                                                }
  
                                                          return Max;
                                                        }
    
    
    
    

    boolean asymetryczny (BufferedImage src,int rozmycie, int czulosc )
                                            
                                            {   
                                                                  
                                                                    byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
                                                                    Mat mat = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC3);
                                                                    mat.put(0, 0, data);
                                                                    Imgproc.blur(mat, mat, new Size(rozmycie, rozmycie));
                                                                    
                                                                  
                                                                    
                                                                   int prawa_strona=0;
                                                                   int lewa_strona=0;
                                                                   int dolna_strona=0;
                                                                   int gorna_strona=0;

                                                                  
                                                                  
                                                                  
                                                                                            for (int y = 0; y <(int)src.getHeight()/2; y++) 
                                                                                                {
                                                                                                  for (int x = 0; x<src.getWidth(); x++) 
                                                                                                  {
                                                                                                    double[] piksel = mat.get(y, x);
                                                                                                    float r = (float) piksel[0];
                                                                                                    float g = (float) piksel[1];
                                                                                                    float b = (float) piksel[2];
                                                                                                    if((r+g+b)/3>czulosc)
                                                                                                    {
                                                                                                      gorna_strona++;
                                                                                                    }
                                                                                                  }
                                                                                                }
                                                                  
                                                                                              
                                                                                           for (int y = (int)src.getHeight()/2; y <src.getHeight(); y++) 
                                                                                              {
                                                                                                 for (int x = 0; x<src.getWidth(); x++) 
                                                                                                  {
                                                                                                  double[] piksel = mat.get(y, x);
                                                                                                    float r = (float) piksel[0];
                                                                                                    float g = (float) piksel[1];
                                                                                                    float b = (float) piksel[2];
                                                                                                  if((r+g+b)/3>czulosc)
                                                                                                  {
                                                                                                    dolna_strona++;
                                                                                                  }
                                                                                                }
                                                                                              }
                                                                  
                                                                  
                                                                                                  
                                                                                            for (int y = 0; y <src.getHeight(); y++) 
                                                                                                  {
                                                                                                    for ( int x = 0; x<(int)src.getWidth()/2; x++) 
                                                                                                    {
                                                                                                        double[] piksel = mat.get(y, x);
                                                                                                        float r = (float) piksel[0];
                                                                                                        float g = (float) piksel[1];
                                                                                                        float b = (float) piksel[2];
                                                                                                     if((r+g+b)/3>czulosc)
                                                                                                      {
                                                                                                        lewa_strona++;
                                                                                                      }
                                                                                                    }
                                                                                                  }
                                                                  
                                                                  
                                                                  
                                                                                             for ( int y = 0; y <src.getHeight(); y++) 
                                                                                                  {
                                                                                                    for ( int x = (int)src.getWidth()/2; x<src.getWidth(); x++) 
                                                                                                    {
                                                                                                        double[] piksel = mat.get(y, x);
                                                                                                        float r = (float) piksel[0];
                                                                                                        float g = (float) piksel[1];
                                                                                                        float b = (float) piksel[2];
                                                                                                        if((r+g+b)/3>czulosc)
                                                                                                              {
                                                                                                                prawa_strona++;
                                                                                                              }
                                                                                                    }
                                                                                                  }
                                                                                                  System.out.println("prawa połowa"+prawa_strona);
                                                                                                  System.out.println("lewa połowa"+lewa_strona);
                                                                                                  System.out.println("gorna połowa"+gorna_strona);
                                                                                                  System.out.println("dolna połowa"+dolna_strona);
                                                                  
                                                                  
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
                                                                            
                                                                            Point Góra = null;
                                                                            Point Dół = null;
                                                                            Point Prawo = null;
                                                                            Point Lewo = null;
                                                                            //inicjalizacja 
                                                                            ArrayList<MatOfPoint> contours = new ArrayList<>(); 
                                                                            Mat hierarchy = new Mat();
                       
                                                                            byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();  
                                                                            Mat opencv = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC3);
                                                                            opencv.put(0, 0, data);
                                                                          
                                                                                     
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
                                                                                      
                                                                                      
                                                                                   
                                                                             
                                                                            
                                                                            
                                                                            
                                                                             if (hierarchy.size().height > 0 && hierarchy.size().width > 0)
                                                                                            {
                                                                                                    // for each contour, display it in blue
                                                                                                    for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
                                                                                                    {
                                                                                                           
                                                                                                           Imgproc.drawContours(opencv, contours, idx, new Scalar(250, 0, 0));
                                                                                                           System.out.println(contours.get(idx).size());
                                                                                       
                                                                                                           for( MatOfPoint mop: contours ){
                                                                                                                        for( Point p: mop.toList() )
                                                                                                                        {
                                                                                                                           if(MaxX<p.x)
                                                                                                                            {
                                                                                                                             MaxX=(int)p.x;
                                                                                                                             Prawo=p;
                                                                                                                            }
                                                                                                                            if(MaxY<p.y){
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
                                                                                                                    }
                                                                                                    
                                                                                                    
                                                                                                    }
                                                                                               System.out.println("MinX "+MinX+" MaxX "+MaxX+" MinY "+MinY+" MaxY "+MaxY); 
                                                                                              
                                                                                               int Szerokosc=MaxX-MinX;
                                                                                               int Wysokosc=MaxY-MinY;
                                                                                               if(min_wielkoscx<Szerokosc||min_wielkoscy<Wysokosc)
                                                                                               {      
                                                                                                              iloscjader++;
                                                                                               }   
                                                                                               
                                                                                            }           
                                                                                      
                                                                           
                                                                          
                                                                          return iloscjader;
                                                                          }
     
     
     
   public BufferedImage MatDoBufferedImage(Mat mat)
    {
        
        
    BufferedImage Obraz = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);

    // Get the BufferedImage's backing array and copy the pixels directly into it
     byte[] data = ((DataBufferByte) Obraz.getRaster().getDataBuffer()).getData();
     mat.get(0, 0, data);
     return  Obraz;  
    }
   
   
   
   
   
   
   
   int rozpoznanie(BufferedImage origin,BufferedImage src,int rozmycie, int czulosc,int min_wielkoscx ,int min_wielkoscy)
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
                       
                                                                            byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
                                                                            
                                                                            Mat opencv = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC3);
                                                                            opencv.put(0, 0, data);
                                                                          
                                                                                     
                                                                                      if(!(rozmycie==0))
                                                                                                              {
                                                                                                              Imgproc.blur(opencv, opencv, new Size(rozmycie, rozmycie));
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
                                                                                      
// petla glowna dla rysowania wsztstkich galaktyk 
                                    if (hierarchy.size().height > 0 && hierarchy.size().width > 0)
                                                                                            {
                                                                            int MinX = src.getWidth();
                                                                            int MinY = src.getHeight();
                                                                            int MaxX=0;
                                                                            int MaxY=0;                   
                                                                            Point Góra = null;
                                                                            Point Dół = null;
                                                                            Point Prawo = null;
                                                                            Point Lewo = null;
                                                                            int PoczatekX,PoczatekY,Wysokosc,Szerokosc;
                                                                                                    // for each contour, display it in blue
                                                                                                    for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
                                                                                                    {
                                                                                                           
                                                                                                           //Imgproc.drawContours(opencv, contours, idx, new Scalar(250, 0, 0));
                                                                                                           //System.out.println(contours.get(idx).size());
                                                                                       
                                                                                                           for( MatOfPoint mop: contours ){
                                                                                                                        for( Point p: mop.toList() )
                                                                                                                        {
                                                                                                                           if(MaxX<p.x)
                                                                                                                            {
                                                                                                                             MaxX=(int)p.x;
                                                                                                                             Prawo=p;
                                                                                                                            }
                                                                                                                            if(MaxY<p.y){
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
                                                                                                                    }
                                                                                                    
                                                                                                    
                                                                                                    }
                                            PoczatekX=MinX;
                                            PoczatekY=MinY;
                                            Szerokosc=MaxX-MinX;
                                            Wysokosc=MaxY-MinY;
                                         
                                           
                                           if(min_wielkoscx<Szerokosc||min_wielkoscy<Wysokosc)
                                         
                                                                   {
                                                                      ListaGalaktykBufor.add(MatDoBufferedImage(opencv).getSubimage(PoczatekX, PoczatekY, Szerokosc, Wysokosc));
                                                                      ListaGalaktyk.add(origin.getSubimage(PoczatekX, PoczatekY, Szerokosc, Wysokosc));
                                                                      PGorny.add(Góra);
                                                                      PDolny.add(Dół);
                                                                      PPrawy.add(Prawo);
                                                                      PLewy.add(Lewo);
                                                                      iloscgalaktyk++;
                                                                   }
                                            
                                            
                                           
                                    }
                          
                 
                            for(int i=0 ; i<ListaGalaktyk.size() ; i++)
                                    {
                                                String widok;
                                                double prawygorny =  distance(PGorny.get(i).x,PGorny.get(i).y, PPrawy.get(i).x, PPrawy.get(i).y);
                                                double lewygorny  = distance(PGorny.get(i).x,PGorny.get(i).y, PLewy.get(i).x, PLewy.get(i).y);
                                                double prawydolny = distance(PDolny.get(i).x,PDolny.get(i).y, PPrawy.get(i).x, PPrawy.get(i).y);
                                                double lewydolny  = distance(PDolny.get(i).x,PDolny.get(i).y, PLewy.get(i).x, PLewy.get(i).y);
                                                double przekatnapion  = distance(PDolny.get(i).x,PDolny.get(i).y, PGorny.get(i).x, PGorny.get(i).y);
                                                double przekatnapoziom = distance(PPrawy.get(i).x,PPrawy.get(i).y, PLewy.get(i).x, PLewy.get(i).y);
                                                
                                                ///od tąd trza posprawdzac;
                                                
                                                
                                                //fragment kodu do znajdowania małych wyraznych gwiazd
                                                int najasniejszypkt=Najjasniejszy(ListaGalaktyk.get(i),(int)(ListaGalaktyk.get(i).getWidth()*0.01),(int)(ListaGalaktyk.get(i).getHeight()*0.01));
                                                int liczba_jader=liczba_jader(ListaGalaktyk.get(i),1,1,najasniejszypkt,(int)(ListaGalaktyk.get(i).getWidth()*0.01),(int)(ListaGalaktyk.get(i).getHeight()*0.01));
                                                
                                                
                                                //fragment odpowiedzialny za znajdowania centra galaktyki
                                                int najasniejszypkt_centrum=Najjasniejszy(ListaGalaktyk.get(i),0,0);
                                                int liczba_jader_centrum=liczba_jader(ListaGalaktyk.get(i),(int)(ListaGalaktyk.get(i).getWidth()*0.1),(int)(ListaGalaktyk.get(i).getHeight()*0.1),najasniejszypkt_centrum,(int)(ListaGalaktyk.get(i).getWidth()*0.3),(int)(ListaGalaktyk.get(i).getHeight()*0.3));
                                                            if(prawygorny>2*prawydolny&&lewydolny>2*lewygorny)
                                                               {
                                                                 widok="Płaski pod przekątną";     
                                                               }
                                                             else if(prawydolny>2*prawygorny&&lewygorny>2*lewydolny)
                                                               {
                                                                 widok="Płaski pod przekątną";     
                                                               }
                                                             else if(przekatnapion>2*przekatnapoziom||przekatnapoziom>2*przekatnapion)
                                                               {
                                                                 widok="Płaski symetrycznie";     
                                                               }
                                                            else
                                                               {
                                                                 widok = "Pełny";
                                                               }
                                                   
                                                   
                                                   
                                                   
                                                  int progowanie = 100;
                                                  double pixele_jasne=0;
                                                  double pixele_biale=0;
                                                  
                                                   
                                                   //tu sie będzie trza pomęczyć 
                                                  byte[] Maska = ((DataBufferByte) ListaGalaktykBufor.get(i).getRaster().getDataBuffer()).getData();  
                                                  Mat maska = new Mat(ListaGalaktykBufor.get(i).getHeight(), ListaGalaktykBufor.get(i).getWidth(), CvType.CV_8UC3);
                                                  maska.put(0, 0, Maska);
                                                  
                                                  byte[] Obraz = ((DataBufferByte) ListaGalaktyk.get(i).getRaster().getDataBuffer()).getData();  
                                                  Mat Obiekt = new Mat(ListaGalaktyk.get(i).getHeight(), ListaGalaktyk.get(i).getWidth(), CvType.CV_8UC3);
                                                  Obiekt.put(0, 0, Obraz);
                                                  
                                                  int Width =ListaGalaktyk.get(i).getWidth();
                                                  int Height =ListaGalaktyk.get(i).getHeight();
                                                  
                                                  System.out.print(ListaGalaktyk.get(i).getType());
                                                 // int[][] TablicaMaskiObrazu = convertTo2DWithoutUsingGetRGB(ListaGalaktykBufor.get(i));
                                                  //int[][] TablicaOryginalnegoPobranegoObrazu = convertTo2DWithoutUsingGetRGB(ListaGalaktyk.get(i));
                                                  
                                                  double  liczbapixeliobiektu=0;
                                                   for (int y = 0; y <Height; y++) 
                                                            {
                                                                  for (int x = 0; x<Width; x++) 
                                                                      {
                                                                       
                                                                                  double r = (ListaGalaktykBufor.get(i).getRGB(x, y) >> 16) & 0xFF; 
                                                                                  double g = (ListaGalaktykBufor.get(i).getRGB(x, y) >> 8) & 0xFF; 
                                                                                  double b = ListaGalaktykBufor.get(i).getRGB(x, y) & 0xFF;  
                                                                                    System.out.print(r+" "+g+" "+b+" ");
                                                                                                if((r+g+b)/3.0>0){
                                                                                                            liczbapixeliobiektu++;         
                                                                                                                    
                                                                                                                   r = (ListaGalaktyk.get(i).getRGB(x, y) >> 16) & 0xFF; 
                                                                                                                   g = (ListaGalaktyk.get(i).getRGB(x, y) >> 8) & 0xFF; 
                                                                                                                   b = ListaGalaktyk.get(i).getRGB(x, y) & 0xFF; 

                                                                                                                          if((r+g+b)/3>progowanie)    
                                                                                                                               {
                                                                                                                                   //System.out.print(x+" "+ y);
                                                                                                                                   pixele_jasne++;
                                                                                                                               }


                                                                                                                          if((r+g+b)/3>240)    
                                                                                                                               {
                                                                                                                                   pixele_biale++;
                                                                                                                               }


                                                                                                                }
                                                                      }
                                                                  System.out.println();
                                                            }
                                                  
                                                 // int  liczbapixeliobiektu=0;
                                                   
                                               
                                                  
                                                  
                                                //aż do tąd   
                                                double procent_zapelnienia_jasnymi=pixele_jasne/liczbapixeliobiektu*100;
                                                double procent_zapelnienia_bialymi=pixele_biale/liczbapixeliobiektu*100;
                                                String typ_galaktyki="Niezakwalifikowany";
                                                wykrytych_galaktyk++;
                                                
                                                
                                                
                                                 // ustalanie rodzaju galaktyki 
                                                
                                                 
                                                        if(widok=="Płaski pod przekątną")
                                                        {
                                                                      if(procent_zapelnienia_jasnymi<plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna)
                                                                                {
                                                                                 typ_galaktyki="Spiralna";
                                                                                  if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                                      else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      typ_galaktyki="Soczewkowata";
                                                                                      }
                                                                                
                                                                      else if (procent_zapelnienia_jasnymi>plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata)
                                                                                {
                                                                                typ_galaktyki="Soczewkowata";
                                                                                 if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                                                
                                                                                else if (procent_zapelnienia_bialymi>plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata)
                                                                                {
                                                                                typ_galaktyki="Soczewkowata";
                                                                                 if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                                                 
                                                                               
                                                                     else   
                                                                                {
                                                                                typ_galaktyki="Spiralna";
                                                                                 if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                }
                                                        }
                                                     
                                                      if( widok == "Płaski symetrycznie")
                                                      {
                                                                                if(procent_zapelnienia_jasnymi<plaskisym_procent_zapelnienia_jasnymi_prog_karlowata)
                                                                                      {
                                                                                      typ_galaktyki="Karłowata";
                                                                                       if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                      }
                                                                                else if(procent_zapelnienia_jasnymi<plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna&&procent_zapelnienia_bialymi<plaskisym_procent_zapelnienia_bialymi_prog_Spiralna)
                                                                                      {
                                                                                       typ_galaktyki="Spiralna";
                                                                                        if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                      }
                                                                                else if (procent_zapelnienia_bialymi>plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata||procent_zapelnienia_jasnymi>plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata) 
                                                                                      {
                                                                                      typ_galaktyki="Soczewkowata";
                                                                                       if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                      
                                                                                      }
                                                                                       else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      typ_galaktyki="Soczewkowata";
                                                                                      }
                                                                                      else {
                                                                                        
                                                                                        
                                                                                      typ_galaktyki="Spiralna";
                                                                                      
                                                                                      }
                                                      }
                                                       
                                                       
                                                       
                                                       
                                                       
                                                       
                                                       if(widok =="Pełny")
                                                       {
                                                                             if(procent_zapelnienia_jasnymi<pelny_procent_zapelnienia_jasnymi_prog_karlowata&&procent_zapelnienia_bialymi<pelny_procent_zapelnienia_bialymi_prog_karlowata)
                                                                             {
                                                                                    typ_galaktyki="Nieregularna";
                                                                                         if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                             }
                                                                             
                                                                             else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      typ_galaktyki="Eliptyczna";
                                                                                      }
                                                                             
                                                                           else if(procent_zapelnienia_jasnymi < pelny_procent_zapelnienia_jasnymi_prog_Spiralna &&  procent_zapelnienia_bialymi<pelny_procent_zapelnienia_bialymi_prog_Spiralna&&liczba_jader>pelny_liczba_jasnych_obiektow_Spiralna&&!(liczba_jader_centrum==1&&liczba_jader==1))
                                                                                      {
                                                                                       typ_galaktyki="Spiralna";
                                                                                         if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                       
                                                                                      }
                                                                                else if(procent_zapelnienia_bialymi>pelny_procent_zapelnienia_bialymi_prog_Eliptyczna)
                                                                                      {
                                                                                      typ_galaktyki="Eliptyczna";
                                                                                         if(liczba_jader_centrum>1)
                                                                                            {
                                                                                              typ_galaktyki=typ_galaktyki+" galaktyka wielokrotna";
                                                                                            }
                                                                                            
                                                                                      }
                                                                                  
                                                                                  else if(liczba_jader_centrum==1&&liczba_jader==1)
                                                                                      {
                                                                                      typ_galaktyki="Eliptyczna";
                                                                                      }    
                                                                               
                                                                                      else 
                                                                                      {
                                                                                      typ_galaktyki="Spialna";
                                                                                      }
                                                                      
                                                                    
                                                                  }
                                                      
                                                 
                                                
                                                System.out.println(ListaGalaktyk.get(i).getWidth()+ " Szerokosc");
                                                System.out.println(ListaGalaktyk.get(i).getHeight()+ " Wysokosc");
                                                if(asymetryczny ( ListaGalaktyk.get(i),rozmycie_prog_Nieregularna, prog_jasnosci_Nieregularna ))
                                                {
                                                typ_galaktyki="Nieregularna";
                                                }
                                                ZapisDoObrazu.add("W-"+widok+" T-"+typ_galaktyki+" PJ-"+String.format("%.2f", (float)procent_zapelnienia_jasnymi)+" PB-"+String.format("%.2f", (float)procent_zapelnienia_bialymi)+" LP-"+liczba_jader+" JG-"+liczba_jader_centrum); 
                                                TypGalaktykiNazwa.add("Widok: "+widok+" Pixele Jasne:"+String.format("%.2f", (float)procent_zapelnienia_jasnymi)+" % Pixele białe:"+String.format("%.2f", (float)procent_zapelnienia_bialymi)+" %\nRodzaj Galaktyki: "+typ_galaktyki+"\nLiczba jasnych punktów: "+liczba_jader+" Jądra galaktyk: "+liczba_jader_centrum);
                                                
                              
                                    
                                    }
 

         //loadPixels();
        // src.loadPixels();
        /// wynik = createImage(src.width, src.height, RGB);
         //wynik.loadPixels();
         
 
            for(int t = 0; t<ListaGalaktyk.size() ; t++)
                          {
                           WczytaneListaGalaktyk.add(ListaGalaktyk.get(t));
                           WczytaneTypGalaktykiNazwa.add(TypGalaktykiNazwa.get(t));
                          }
                          
                          return iloscgalaktyk;
  
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
    public void readImage(String filePath){
        try{
            File f = new File(filePath);
            image = ImageIO.read(f);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            if("jpg".equals(fileType)){
                imgType = ImageType.JPG;
            }else{
                imgType = ImageType.PNG;
            }
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.totalPixels = this.width * this.height;
            this.pixels = new int[this.totalPixels];
            initPixelArray();
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
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
     * Initialize the pixel array
     * Image origin is at coordinate (0,0)
     * (0,0)--------> X-axis
     *     |
     *     |
     *     |
     *     v
     *   Y-axis
     * 
     * This method will store the value of each pixels of a 2D image in a 1D array.
     */
    private void initPixelArray(){
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try{
            pg.grabPixels();
        }catch(InterruptedException e){
            System.out.println("Error Occurred: "+e);
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

      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] result = new int[height][width];
      if (hasAlphaChannel) {
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
      } else {
         final int pixelLength = 3;
         for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            result[row][col] = argb;
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
         }
      }

      return result;
   }    
      
      
    
    
    
    
     public static  int[][][] PobierzKanałyARGB(BufferedImage image){
       int[][][] Tablica = new int[image.getHeight()][image.getWidth()][4];
       int[][] tablicaZObrazu =  convertTo2DWithoutUsingGetRGB(image);
       
       System.out.println("Nowy Obraz");
       for(int y = 0; y < image.getHeight(); y++){
           System.out.print(y);
           
            for(int x = 0; x < image.getWidth(); x++){
                Tablica[y][x][0]=(tablicaZObrazu[y][x] >> 24) & 0xFF;
                Tablica[y][x][1]=(tablicaZObrazu[y][x] >> 16) & 0xFF;
                Tablica[y][x][2]=(tablicaZObrazu[y][x] >> 8) & 0xFF;
                Tablica[y][x][3]=(tablicaZObrazu[y][x]) & 0xFF;
                System.out.print(Tablica[y][x][0]+" ");
                System.out.print(Tablica[y][x][1]+" ");
                System.out.print(Tablica[y][x][2]+" ");
                System.out.print(Tablica[y][x][3]+" ");
                
                }
            System.out.println();
            }
       return Tablica;
    }       
}
