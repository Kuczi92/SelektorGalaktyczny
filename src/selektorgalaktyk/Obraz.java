/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import java.awt.image.DataBufferByte;

/**
 *
 * @author Quchi
 */
public class Obraz {
    
    
    private BufferedImage image;
    public int[][] KanałRGB;
    private BufferedImage ObrazPrzetworzony;
    public short [][] Czerwony;
    public short [][] Niebieski;
    public short [][] Zielony;
    public short [][] Alpha;
    
   
    
    
    
    public Obraz(int[][] kanałRGB){
        this.KanałRGB=kanałRGB;
        Czerwony  = new short[kanałRGB.length][kanałRGB[0].length];
        Niebieski  = new short[kanałRGB.length][kanałRGB[0].length];
        Zielony  = new short[kanałRGB.length][kanałRGB[0].length];
        Alpha = new short[kanałRGB.length][kanałRGB[0].length];
       
        for(int y = 0 ; y<KanałRGB.length;y++){
            for(int x= 0;x<KanałRGB[0].length;x++ ){
                
                
                
               Alpha[y][x] =  (short) ((KanałRGB[y][x]>>24)&0xff);
               Czerwony[y][x] =  (short) ((KanałRGB[y][x]>>16)&0xff);
               Zielony[y][x] =  (short) ((KanałRGB[y][x]>>8)&0xff);
               Niebieski[y][x] =  (short) (KanałRGB[y][x]&0xff); 
               
               
            }
        }
        
        
        
        
    }
    
    
    public Obraz(BufferedImage obraz){
        this.image=obraz;
        KanałRGB=convertTo2DWithoutUsingGetRGB(image);
        Czerwony  = new short[KanałRGB.length][KanałRGB[0].length];
        Niebieski  = new short[KanałRGB.length][KanałRGB[0].length];
        Zielony  = new short[KanałRGB.length][KanałRGB[0].length];
        Alpha = new short[KanałRGB.length][KanałRGB[0].length];
        
        
        for(int y = 0 ; y<KanałRGB.length;y++){
            for(int x= 0;x<KanałRGB[0].length;x++ ){
                
                
                
               Alpha[y][x] = (short) ((KanałRGB[y][x]>>24)&0xff);
               Czerwony[y][x] =  (short) ((KanałRGB[y][x]>>16)&0xff);
               Zielony[y][x] = (short) ((KanałRGB[y][x]>>8)&0xf);
               Niebieski[y][x] =  (short) (KanałRGB[y][x]&0xff); 
               
               
            }
        }
        
    }

    
    

    public BufferedImage ObrazPrzetworzony(){
        ObrazPrzetworzony = new BufferedImage(KanałRGB[0].length,KanałRGB.length,TYPE_4BYTE_ABGR);
        System.out.println("Wartosci w funckjiRGBTab Y"+KanałRGB.length);
        System.out.println("Wartosci w funckjiRGBTab X"+KanałRGB[0].length);
        
        
        
        System.out.println("Wartosci w funckji X"+ObrazPrzetworzony.getWidth());
        System.out.println("Wartosci w funckji Y"+ObrazPrzetworzony.getHeight());
        
         for (int col = 0; col < KanałRGB.length; col++)
         {
         for (int row = 0; row < KanałRGB[0].length; row++)
         {
             int c = (Alpha[col][row] << 24)
                     | (Czerwony[col][row] << 16)
                     | (Zielony[col][row] << 8)
                     | (Niebieski[col][row]);
             ObrazPrzetworzony.setRGB(row, col, c);
         }
     }
        
        
        
        
        return ObrazPrzetworzony;
    }
    
    
    public void DodajCzerwony(int wartośc)
    {
        for(int x = 0 ; x<Czerwony.length;x++){
            for(int y= 0;y<Czerwony[0].length;y++ ){
               Czerwony[x][y] = (short) (Czerwony[x][y]+wartośc);
            }
        }
    }
    
     public void DodajZielony(int wartośc)
     {
        for(int x = 0 ; x<Zielony.length;x++){
            for(int y= 0;y<Zielony[0].length;y++ ){
               Zielony[x][y] = (short) (Zielony[x][y]+wartośc);
            }
        }
     }
      public void DodajNiebieski(int wartośc)
    {
        for(int x = 0 ; x<Niebieski.length;x++){
            for(int y= 0;y<Niebieski[0].length;y++ ){
               Niebieski[x][y] = (short) (Niebieski[x][y]+wartośc);
            }
        }
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
            result[col][row] = argb;
            row++;
            if (row == width) {
               row = 0;
               col++;
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
            result[col][row] = argb;
            row++;
            if (row == width) {
               row = 0;
               col++;
            }
         }
      }

      return result;
   }
}
