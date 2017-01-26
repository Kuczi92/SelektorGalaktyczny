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
public class AlgorytmSelekcjiMasowej implements Runnable{
                                          //referencja dla pojedyńczego algorytmu
                                            AlgorytmSelekcji Algorytm;
                                            
                                          //referencja dla   
                                            ArrayList <String> ListaPlików;
                                            
                                            //Referencja dla listyGalaktyk;
                                            //Pierwszy wymiar symbolizuje zdjęcie natomiast drugi listę wykrytych w nich galatkyk
                                            ArrayList <ArrayList<Galaktyka>> ListaGalaktyk;
    
    
    
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
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
