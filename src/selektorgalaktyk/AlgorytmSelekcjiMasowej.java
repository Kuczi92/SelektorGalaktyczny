/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Quchi
 */
public class AlgorytmSelekcjiMasowej implements Callable<ArrayList <ArrayList<Galaktyka>>>{
                                           //referencja dla pojedyńczego algorytmu
                                            AlgorytmSelekcji Algorytm;
                                            
                                          //referencja dla   
                                            ArrayList <String> ListaPlików;
                                            
                                            //Referencja dla listyGalaktyk;
                                            //Pierwszy wymiar symbolizuje zdjęcie natomiast drugi listę wykrytych w nich galatkyk
                                            ArrayList <ArrayList<Galaktyka>> ListaGalaktyk;
    
                                          // Rodzaj progowania 
                                            WyświetlaczObraz.RodzajeProgowania progowanie;
                                          // referencja do oknaObrazu
                                          OknoSelekcjiMasowej OknoSelekcjiMasowej;  
                                            //referencja dla semafora potrzebna by stworzyć poprawny progress bar
                                          Semaphore progressLock;
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
                                                
                                          //wartości kanałów RGB 
                                                int niebieski;
                                                int zielony;
                                                int czerwony;
                                            
                                                    
   public AlgorytmSelekcjiMasowej (Semaphore progressLock,OknoSelekcjiMasowej OknoSelekcjiMasowej, ArrayList <String> ListaPlików,double czerwien,double zielen,double niebieski,double kontrast,WyświetlaczObraz.RodzajeProgowania progowanie,int wartoscprogujaca){
       this.ListaPlików = ListaPlików;
       this.czerwony = (int) czerwien;
       this.zielony = (int) zielen;
       this.niebieski = (int) niebieski;
       this.kontrast = kontrast;
       this.progowanie = progowanie;
       this.Wartosc_Progowa = wartoscprogujaca;
       this.OknoSelekcjiMasowej = OknoSelekcjiMasowej;
       this.progressLock = progressLock;
   }                                             
                                                
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
                                                
      public void ustawParametryDlaPreProcessingu( int jasnosc, double kontrast,int Wartosc_Progowa,int rozmycie,int czulosc,int min_wielkoscx,int min_wielkoscy ){
        this.jasnosc = jasnosc;
        this.kontrast = kontrast;
        this.Wartosc_Progowa = Wartosc_Progowa;
        this.rozmycie = rozmycie;
        this.czulosc = czulosc;
        this.min_wielkoscx = min_wielkoscx;
        this.min_wielkoscy = min_wielkoscy;
        }                                          
                                                
   
    @SuppressWarnings("empty-statement")
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
				OknoSelekcjiMasowej.DodajDoProgressu(1,ListaPlików.get(obraz));;
			}
			catch (InterruptedException e)
			{
			}
			finally
			{
				progressLock.release();
			}
                
                ListaGalaktyk.add(Algorytm.ListaWykrytychGalaktyk());
       }
    }

      @Override
      public ArrayList<ArrayList<Galaktyka>> call() throws Exception {
      run();
      return ListaGalaktyk;
    }
    
}
