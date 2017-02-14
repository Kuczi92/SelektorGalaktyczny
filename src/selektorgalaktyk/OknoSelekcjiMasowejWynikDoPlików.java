/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.util.ArrayList;

/**
 *
 * @author Quchi
 */
public class OknoSelekcjiMasowejWynikDoPlików extends OknoSelekcjiMasowej implements Runnable {
    String ScieżkaFolderu;
    ArrayList<String>  TempListaPlików;
    AlgorytmSelekcjiMasowejWynikDoPlików Algorytm;
    public OknoSelekcjiMasowejWynikDoPlików(String ScieżkaFolderu,int liczbawątków, ArrayList<String> ListaPlików, double czerwien, double zielen, double niebieski, double kontrast, WyświetlaczObraz.RodzajeProgowania progowanie, int wartoscprogujaca) {
        super(liczbawątków, ListaPlików, czerwien, zielen, niebieski, kontrast, progowanie, wartoscprogujaca);
        this.ScieżkaFolderu = ScieżkaFolderu;
        TempListaPlików=this.ListaPlików;
        ustawTrybPracyZapisuDoPliku(true);
    }

    @Override
    public void run() {
        ArrayList<Thread> Wątki = new ArrayList<>(); 
      for(int wątek = 0 ; wątek <liczbaWątków;wątek++ )
        {
            
            if(wątek<liczbaWątków-1)
            {
                Algorytm = new AlgorytmSelekcjiMasowejWynikDoPlików(ScieżkaFolderu,progressLock,this,new ArrayList<>(TempListaPlików.subList(0, (int) liczbaWszystkichObrazów/liczbaWątków)),czerwony, zielony, niebieski,kontrast,progowanie,Wartosc_Progowa);
                Algorytm.ustawParametrySystemuDecyzyjnego(plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna, plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata, pelny_procent_zapelnienia_jasnymi_prog_karlowata, pelny_procent_zapelnienia_bialymi_prog_karlowata,pelny_procent_zapelnienia_jasnymi_prog_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Spiralna,pelny_liczba_jasnych_obiektow_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,rozmycie_prog_Nieregularna,prog_jasnosci_Nieregularna);
                Algorytm.ustawParametryDlaPreProcessingu(jasnosc,kontrast,Wartosc_Progowa,rozmycie,czulosc,min_wielkoscx,min_wielkoscy);
                TempListaPlików.removeAll(new ArrayList<>(TempListaPlików.subList(0, (int) liczbaWszystkichObrazów/liczbaWątków)));
                Wątki.add(new Thread(Algorytm));
            }
            
            else 
            {
                Algorytm = new AlgorytmSelekcjiMasowejWynikDoPlików(ScieżkaFolderu,progressLock,this,TempListaPlików,czerwony, zielony, niebieski,kontrast,progowanie,Wartosc_Progowa);
                Algorytm.ustawParametrySystemuDecyzyjnego(plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna, plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata, pelny_procent_zapelnienia_jasnymi_prog_karlowata, pelny_procent_zapelnienia_bialymi_prog_karlowata,pelny_procent_zapelnienia_jasnymi_prog_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Spiralna,pelny_liczba_jasnych_obiektow_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,rozmycie_prog_Nieregularna,prog_jasnosci_Nieregularna);
                Algorytm.ustawParametryDlaPreProcessingu(jasnosc,kontrast,Wartosc_Progowa,rozmycie,czulosc,min_wielkoscx,min_wielkoscy);
                Wątki.add(new Thread(Algorytm));
            }
            
           
        }
      //uruchomienie wątków
        for(int wątek = 0;wątek <liczbaWątków;wątek++)
            Wątki.get(wątek).start();
      
    }
    
}
