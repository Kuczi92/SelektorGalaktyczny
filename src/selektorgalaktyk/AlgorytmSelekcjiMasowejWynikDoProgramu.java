/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quchi
 */
public class AlgorytmSelekcjiMasowejWynikDoProgramu extends AlgorytmSelekcjiMasowej implements Callable<ArrayList <ArrayList<Galaktyka>>>{
    PanelSterowania Panel;
    public AlgorytmSelekcjiMasowejWynikDoProgramu(PanelSterowania Panel,Semaphore progressLock, selektorgalaktyk.OknoSelekcjiMasowej OknoSelekcjiMasowej, ArrayList<String> ListaPlików, double czerwien, double zielen, double niebieski, double kontrast, WyświetlaczObraz.RodzajeProgowania progowanie, int wartoscprogujaca) {
        super(progressLock, OknoSelekcjiMasowej, ListaPlików, czerwien, zielen, niebieski, kontrast, progowanie, wartoscprogujaca);
        this.Panel=Panel;
    }

      @Override
      public synchronized ArrayList<ArrayList<Galaktyka>> call() throws Exception {
      int liczbaplikow= ListaPlików.size();
        ListaGalaktyk = new ArrayList<>();
        for (int obraz = 0 ; obraz < liczbaplikow;obraz ++)
        {
                AlgorytmSelekcji Algorytm = new AlgorytmSelekcji(Panel,ListaPlików.get(obraz),czerwony, zielony, niebieski,kontrast,progowanie,Wartosc_Progowa);
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
                
                ListaGalaktyk.add(Algorytm.ListaWykrytychGalaktyk());
       }
      return ListaGalaktyk;
    }
    
}
