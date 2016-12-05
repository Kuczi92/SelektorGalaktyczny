/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;


import java.awt.Color;
import org.opencv.core.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.opencv.imgproc.Imgproc;





/**
 *
 * @author Quchi
 */


public class AlgorytmSelekcji {
    
static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

AlgorytmSelekcji(BufferedImage Obraz){
    
}


 
    
    //////////////////////////// Zmienne /////////////////////////////////////
    
    /** Przechowanie obrazu */
    private BufferedImage image;
    
    /** Store the image width and height */
    private int width, height;
    
    /** Pixels value - ARGB */
    private int pixels[];
    
    /** Total number of pixel in an image*/
    private int totalPixels;
    
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
    
    
    
    
    
   public  BufferedImage liczba_jader(BufferedImage src,int rozmyciex,int rozmyciey ,int czulosc,int min_wielkoscx ,int min_wielkoscy)
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
                                                                                               Core.line(opencv, Prawo, Góra, new Scalar(250, 0, 0),2);
                                                                                               Core.line(opencv, Góra, Lewo, new Scalar(250, 0, 0),2);
                                                                                               Core.line(opencv, Lewo, Dół, new Scalar(250, 0, 0),2);
                                                                                               Core.line(opencv, Dół, Prawo, new Scalar(250, 0, 0),2);
                                                                                               int Szerokosc=MaxX-MinX;
                                                                                               int Wysokosc=MaxY-MinY;
                                                                                               if(min_wielkoscx<Szerokosc||min_wielkoscy<Wysokosc)
                                                                                               {      
                                                                                                              iloscjader++;
                                                                                               }   
                                                                                               
                                                                                            }           
                                                                                      
                                                                           
                                                                          return MatDoBufferedImage(opencv);
                                                                          }
     
     
     
   public BufferedImage MatDoBufferedImage(Mat mat)
    {
        
        
    BufferedImage Obraz = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);

    // Get the BufferedImage's backing array and copy the pixels directly into it
     byte[] data = ((DataBufferByte) Obraz.getRaster().getDataBuffer()).getData();
     mat.get(0, 0, data);
     return  Obraz;  
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
                            if((KolorCzerwony[x+width*y]+czerwien)*kontrast>255)
                                    {
                                       KolorCzerwony[x+width*y]=255;
                                    }
                            else if((KolorCzerwony[x+width*y]+czerwien)*kontrast<0)
                                    { 
                                      KolorCzerwony[x+width*y]=0;
                                    }
                
                
                            //Gdy wartość jest pomiędzy 0 - 255 jest tu ona zapisywana
                            else
                                    {

                                      //obliczenie wartosci pikseli oraz operacje progowania  
                                      if(null != progowanie)  
                                        switch (progowanie) {
                                            case BRAK_PROGROWANIA:
                                                KolorCzerwony[x+width*y]=(int) ((KolorCzerwony[x+width*y]+czerwien)*kontrast);
                                                break;
                                            case PROGOWANIE:
                                                if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                                {
                                                    KolorCzerwony[x+width*y]=(int) ((255+czerwien)*kontrast);
                                                }
                                                else
                                                {
                                                    KolorCzerwony[x+width*y]=(int) ((0+czerwien)*kontrast);
                                                }     break;
                                            case EFEKT_ROZJASNIAJACY:
                                                if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                                {
                                                    KolorCzerwony[x+width*y]= 255;
                                                }
                                                else
                                                {
                                                    KolorCzerwony[x+width*y]=(int) ((KolorCzerwony[x+width*y]+czerwien)*kontrast);
                                                }     break;
                                            case EFEKT_PRZYCIEMNAJACY:
                                                if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                                {
                                                    KolorCzerwony[x+width*y]=(int) ((KolorCzerwony[x+width*y]+czerwien)*kontrast);
                                                }
                                                else
                                                {
                                                    KolorCzerwony[x+width*y]= 0;
                                                }     break;
                                            default:
                                                break;  
                                        }
                  
                             }
             ///Koniec bloku dla koloru Czerwonego    
                  
                                 
                
                
                
             ///Blok odpowiedzialny za kolor Zielony   
                            //blok kodu odpowiedzialny za zabezpieczenia które mają za zadanie zapobiec przepełnienia liczby poza skale 8 bitow
                           if((KolorZielony[x+width*y]+zielen)*kontrast>255)
                                    {
                                       KolorZielony[x+width*y]=255;
                                    }
                           else if((KolorZielony[x+width*y]+zielen)*kontrast<0)
                                    {

                                      KolorZielony[x+width*y]=0;
                                    }

                           //Gdy wartość jest pomiędzy 0 - 255 jest tu ona zapisywana
                           else
                           {
                                    //obliczenie wartosci pikseli oraz operacje progowania    
                                   if(null != progowanie)  
                                     switch (progowanie) {
                                         case BRAK_PROGROWANIA:
                                             KolorZielony[x+width*y]=(int) ((KolorZielony[x+width*y]+zielen)*kontrast);
                                             break;
                                         case PROGOWANIE:
                                             if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                             {
                                                 KolorZielony[x+width*y]=(int) ((255+zielen)*kontrast);
                                             }
                                             else
                                             {
                                                 KolorZielony[x+width*y]=(int) ((0+zielen)*kontrast);
                                             }     break;
                                         case EFEKT_ROZJASNIAJACY:
                                             if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                             {
                                                 KolorZielony[x+width*y]= 255;
                                             }
                                             else
                                             {
                                                 KolorZielony[x+width*y]=(int) ((KolorZielony[x+width*y]+zielen)*kontrast);
                                             }     break;
                                         case EFEKT_PRZYCIEMNAJACY:
                                             if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                             {
                                                 KolorZielony[x+width*y]=(int) ((KolorZielony[x+width*y]+zielen)*kontrast);
                                             }
                                             else
                                             {
                                                 KolorZielony[x+width*y]= 0;
                                             }     break;
                                         default:
                                             break;  
                                     } 
                           }
                
                
             ////Koniec bloku dla koloru zielonego   
                
             ///Początek bloku dla koloru niebieskiego    
                 //blok kodu odpowiedzialny za zabezpieczenia które mają za zadanie zapobiec przepełnienia liczby poza skale 8 bitow
                if((KolorNiebieski[x+width*y]+niebieski)*kontrast>255)
                        {
                            KolorNiebieski[x+width*y]=255;
                        }
                else if((KolorNiebieski[x+width*y]+niebieski)*kontrast<0)
                        { 
                           KolorNiebieski[x+width*y]=0;
                        }
                
                
                
                 //Gdy wartość jest pomiędzy 0 - 255 jest tu ona zapisywana
                else
                        {
                          if(null != progowanie)  
                            switch (progowanie) {
                                case BRAK_PROGROWANIA:
                                    KolorNiebieski[x+width*y]=(int) ((KolorNiebieski[x+width*y]+niebieski)*kontrast);
                                    break;
                                case PROGOWANIE:
                                    if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                    {
                                        KolorNiebieski[x+width*y]=(int) ((255+niebieski)*kontrast);
                                    }
                                    else
                                    {
                                        KolorNiebieski[x+width*y]=(int) ((0+niebieski)*kontrast);
                                    }     break;
                                case EFEKT_ROZJASNIAJACY:
                                    if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                    {
                                        KolorNiebieski[x+width*y]= 255;
                                    }
                                    else
                                    {
                                        KolorNiebieski[x+width*y]=(int) ((KolorNiebieski[x+width*y]+niebieski)*kontrast);
                                    }     break;
                                case EFEKT_PRZYCIEMNAJACY:
                                    if((KolorCzerwony[x+width*y]+KolorZielony[x+width*y]+KolorNiebieski[x+width*y])/3 > wartoscprogujaca)
                                    {
                                        KolorNiebieski[x+width*y]=(int) ((KolorNiebieski[x+width*y]+niebieski)*kontrast);
                                    }
                                    else
                                    {
                                        KolorNiebieski[x+width*y]= 0;
                                    }     break;
                                default:
                                    break; 
                             }

                        }
            ///Koniec bloku odpowiedzialnego za kolor niebieski     
                
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
           
           
           
}
