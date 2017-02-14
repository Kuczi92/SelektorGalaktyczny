/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Quchi
 */
public class AlgorytmSelekcjiMasowejWynikDoPlików extends AlgorytmSelekcjiMasowej implements Runnable {
    String ScieżkaFolderuZapisu;
    public AlgorytmSelekcjiMasowejWynikDoPlików(String SciezkaFolderu,Semaphore progressLock, selektorgalaktyk.OknoSelekcjiMasowej OknoSelekcjiMasowej, ArrayList<String> ListaPlików, double czerwien, double zielen, double niebieski, double kontrast, WyświetlaczObraz.RodzajeProgowania progowanie, int wartoscprogujaca) {
        super(progressLock, OknoSelekcjiMasowej, ListaPlików, czerwien, zielen, niebieski, kontrast, progowanie, wartoscprogujaca);
        this.ScieżkaFolderuZapisu = SciezkaFolderu;
    }


    
    
    
     public void writeImage(String filePath,BufferedImage image){
        try{
            File f = new File(filePath);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            ImageIO.write(image, fileType, f);
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
    }
     
     
     public void zapiszWynikiDoPliku(ArrayList<Galaktyka> ListaGalaktyk){
         
         
         
         
         Path p = Paths.get(ListaGalaktyk.get(0).getŹródło());
               String NazwaPlikuŹródłowego = p.getFileName().toString().replaceFirst("[.][^.]+$", "");
               
               File file = new File(ScieżkaFolderuZapisu+"\\"+NazwaPlikuŹródłowego);
                if (!file.exists()) 
                {
                           if (file.mkdir()) {
                              
                           } else {
                               
                           }
                }
               String rozszerzenie = "";

                int i = ListaGalaktyk.get(0).getŹródło().lastIndexOf('.');
                if (i > 0) 
                {
                            rozszerzenie = ListaGalaktyk.get(0).getŹródło().substring(i+1);
                }
             
               
                @SuppressWarnings("StringBufferMayBeStringBuilder")
               
                int liczbawykrytychgalaktyk =   ListaGalaktyk.size();
                
                
                    for(int wykrytagalaktyka = 0 ;wykrytagalaktyka<liczbawykrytychgalaktyk;wykrytagalaktyka++ ){
                        
                        //dodanie folderu o nazwie sciezki żródłowej
                        StringBuilder strB = new StringBuilder(ScieżkaFolderuZapisu);
                        strB.append("\\");
                        strB.append(NazwaPlikuŹródłowego);
                        strB.append("\\");
                        
                        //tworzenie unikatowej nazwy pliku odpowiadającej poszczególnym parametrom
                        //strB.append(NazwaPlikuŹródłowego);
                        // strB.append("_");
                        strB.append(wykrytagalaktyka);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).LiczbaJasnychPunktów);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).LiczbaJąderGalaktyki);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).PikseleBiałe);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).PikseleJasne);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt1.x);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt1.y);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt2.x);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt2.y);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt3.x);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt3.y);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt4.x);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).Punkt4.y);
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).TypGalaktyki.toString().replace("_", " "));
                        strB.append("_");
                        strB.append(ListaGalaktyk.get(wykrytagalaktyka).WidokNaGalaktyke.toString().replace("_", " "));
                        strB.append(".");
                        strB.append(rozszerzenie);  
                        String wynik = strB.toString();
                        writeImage(wynik,ListaGalaktyk.get(wykrytagalaktyka).getObraz());
                    }
     }
     
    @Override
    public void run() {
       int liczbaplikow= ListaPlików.size();
        ListaGalaktyk = new ArrayList<>();
        for (int obraz = 0 ; obraz < liczbaplikow;obraz ++)
                {
                        AlgorytmSelekcji Algorytm = new AlgorytmSelekcji(ListaPlików.get(obraz),czerwony, zielony, niebieski,kontrast,progowanie,Wartosc_Progowa);
                        Algorytm.ustawParametrySystemuDecyzyjnego(plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna, plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata, pelny_procent_zapelnienia_jasnymi_prog_karlowata, pelny_procent_zapelnienia_bialymi_prog_karlowata,pelny_procent_zapelnienia_jasnymi_prog_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Spiralna,pelny_liczba_jasnych_obiektow_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,rozmycie_prog_Nieregularna,prog_jasnosci_Nieregularna);
                        Algorytm.rozpoznanie(rozmycie,czulosc,min_wielkoscx,min_wielkoscy);
                                try
                                {
                                        progressLock.acquire();
                                        try {
                                            OknoSelekcjiMasowej.DodajDoProgressu(1,ListaPlików.get(obraz));

                                        } catch (ExecutionException ex) {
                                            Logger.getLogger(AlgorytmSelekcjiMasowejWynikDoProgramu.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                }
                                catch (InterruptedException e)
                                {
                                }
                                finally
                                {
                                        progressLock.release();
                                }

                        zapiszWynikiDoPliku(Algorytm.ListaWykrytychGalaktyk());
                        Algorytm = null;
                }
        
  
            
                
                
    }
}
