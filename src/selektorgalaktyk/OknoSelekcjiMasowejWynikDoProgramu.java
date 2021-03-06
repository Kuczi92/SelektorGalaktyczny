/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Quchi
 */
public class OknoSelekcjiMasowejWynikDoProgramu extends OknoSelekcjiMasowej implements Callable <List<Future<ArrayList<ArrayList<Galaktyka>>>>> {
    AlgorytmSelekcjiMasowejWynikDoProgramu Algorytm;
    PanelSterowania Panel;
    public OknoSelekcjiMasowejWynikDoProgramu(PanelSterowania Panel,int liczbawątków, ArrayList<String> ListaPlików, double czerwien, double zielen, double niebieski, double kontrast, WyświetlaczObraz.RodzajeProgowania progowanie, int wartoscprogujaca) {
        super(liczbawątków, ListaPlików, czerwien, zielen, niebieski, kontrast, progowanie, wartoscprogujaca);
        this.Panel=Panel;
        ustawTrybPracyZapisuDoPliku(false);
    }

    

    @Override
    public List<Future<ArrayList<ArrayList<Galaktyka>>>> call() throws Exception {
      service = Executors.newFixedThreadPool(liczbaWątków);
        ArrayList<AlgorytmSelekcjiMasowejWynikDoProgramu> listaWątków = new ArrayList<>();
        for(int wątek = 0 ; wątek <liczbaWątków;wątek++ )
        {
            
            if(wątek<liczbaWątków-1)
            {
            Algorytm = new AlgorytmSelekcjiMasowejWynikDoProgramu(Panel,progressLock,this,new ArrayList<>(ListaPlików.subList(0, (int) liczbaWszystkichObrazów/liczbaWątków)),czerwony, zielony, niebieski,kontrast,progowanie,Wartosc_Progowa);
            Algorytm.ustawParametrySystemuDecyzyjnego(plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna, plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata, pelny_procent_zapelnienia_jasnymi_prog_karlowata, pelny_procent_zapelnienia_bialymi_prog_karlowata,pelny_procent_zapelnienia_jasnymi_prog_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Spiralna,pelny_liczba_jasnych_obiektow_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,rozmycie_prog_Nieregularna,prog_jasnosci_Nieregularna);
            Algorytm.ustawParametryDlaPreProcessingu(jasnosc,kontrast,Wartosc_Progowa,rozmycie,czulosc,min_wielkoscx,min_wielkoscy);
            ListaPlików.removeAll(new ArrayList<>(ListaPlików.subList(0, (int) liczbaWszystkichObrazów/liczbaWątków)));
            listaWątków.add(Algorytm);    
            }
            
            else 
            {
              Algorytm = new AlgorytmSelekcjiMasowejWynikDoProgramu(Panel,progressLock,this,ListaPlików,czerwony, zielony, niebieski,kontrast,progowanie,Wartosc_Progowa);
              Algorytm.ustawParametrySystemuDecyzyjnego(plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna, plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata, pelny_procent_zapelnienia_jasnymi_prog_karlowata, pelny_procent_zapelnienia_bialymi_prog_karlowata,pelny_procent_zapelnienia_jasnymi_prog_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Spiralna,pelny_liczba_jasnych_obiektow_Spiralna,pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,rozmycie_prog_Nieregularna,prog_jasnosci_Nieregularna);
              Algorytm.ustawParametryDlaPreProcessingu(jasnosc,kontrast,Wartosc_Progowa,rozmycie,czulosc,min_wielkoscx,min_wielkoscy);
              listaWątków.add(Algorytm);    
            }
           
        }
       
         Listy = service.invokeAll(listaWątków);
         PrzenieśDaneDoArrayList(); 
     return Listy;
    }
    
}
