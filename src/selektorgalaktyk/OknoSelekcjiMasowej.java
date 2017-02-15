/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;



public class OknoSelekcjiMasowej extends javax.swing.JFrame {
                                          // wykonawca dla wielowątkowowści
                                            ExecutorService service;
                                          //referencja dla pojedyńczego algorytmu
                                            
                                            Semaphore progressLock = new Semaphore(1);
                                          //referencja dla   
                                            ArrayList <String> ListaPlików;
                                            
                                            //Referencja dla listyGalaktyk;
                                            //Pierwszy wymiar symbolizuje zdjęcie natomiast drugi listę wykrytych w nich galatkyk
                                         volatile  ArrayList <ArrayList<Galaktyka>> ListaGalaktyk;
                                          //zmienna przechowywująca wyniki z wątków
                                           List<Future<ArrayList<ArrayList<Galaktyka>>>> Listy;
                                          // Rodzaj progowania 
                                            WyświetlaczObraz.RodzajeProgowania progowanie;
                                            
                                             // liczba wykonanych selekcji
                                              private volatile double liczbaWykonanychSelekcji = 0;
                                                // liczbawszystkichObrazów do selekcji
                                                double liczbaWszystkichObrazów;
                                          // liczba watków dla kótrych uruchomi się algorytm
                                                int liczbaWątków;
                                            
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
                                           //zmienne potrzebne do zmierzenia czasu obliczeń     
                                                long tStart;
                                                long tEnd;
    /**
     * Creates new form OknoSelekcjiMasowej
     */
    public OknoSelekcjiMasowej(int liczbawątków,ArrayList <String> ListaPlików,double czerwien,double zielen,double niebieski,double kontrast,WyświetlaczObraz.RodzajeProgowania progowanie,int wartoscprogujaca) {
         super("Okno selekcji masowej postęp");
         this.ListaPlików = ListaPlików;
         this.czerwony = (int) czerwien;
         this.zielony = (int) zielen;
         this.niebieski = (int) niebieski;
         this.kontrast = kontrast;
         this.progowanie = progowanie;
         this.Wartosc_Progowa = wartoscprogujaca;
         this.liczbaWątków  = liczbawątków;
         this.liczbaWszystkichObrazów = ListaPlików.size();
         initComponents();
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         int width = (int) screenSize.getWidth();
         int height = (int) screenSize.getHeight();
         setLocation(width/3, height/3);
         this.setVisible(true);
         LabelLiczbaWątków.setText(LabelLiczbaWątków.getText()+" "+liczbawątków);
    }
       public void ustawParametrySystemuDecyzyjnego( int plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata,int plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata,int plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna,int plaskisym_procent_zapelnienia_jasnymi_prog_karlowata,int plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna,int plaskisym_procent_zapelnienia_bialymi_prog_Spiralna,int plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata,int plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata,int pelny_procent_zapelnienia_jasnymi_prog_karlowata,double pelny_procent_zapelnienia_bialymi_prog_karlowata,int pelny_procent_zapelnienia_jasnymi_prog_Spiralna,int pelny_procent_zapelnienia_bialymi_prog_Spiralna,int pelny_liczba_jasnych_obiektow_Spiralna,int pelny_procent_zapelnienia_bialymi_prog_Eliptyczna,int rozmycie_prog_Nieregularna,int prog_jasnosci_Nieregularna){
                                            //dla płaskich pod przekątną
                                            this.plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata=plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata;
                                            Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata.setText(Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata.getText()+" "+plaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata+"%");
                                            
                                            
                                            this.plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata=plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata;
                                            Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata.setText(Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata.getText()+" "+plaskipp_procent_zapelnienia_bialymi_prog_Soczewowata+"%");
                                            
                                  
                                            this.plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna=plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna;
                                            Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna.setText(Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna.getText()+" "+plaskipp_procent_zapelnienia_jasnymi_prog_Spiralna+"%");
                                            
                                            
                                            // dla plaskich symetrycznie     
                                            this.plaskisym_procent_zapelnienia_jasnymi_prog_karlowata=plaskisym_procent_zapelnienia_jasnymi_prog_karlowata;
                                            Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata.setText(Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata.getText()+" "+plaskisym_procent_zapelnienia_jasnymi_prog_karlowata+"%");
                                            
                                            
                                            
                                            this.plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna=plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna;
                                            Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna.setText(Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna.getText()+ " "+plaskisym_procent_zapelnienia_jasnymi_prog_Spiralna+"%");
                                            
                                            
                                            this.plaskisym_procent_zapelnienia_bialymi_prog_Spiralna=plaskisym_procent_zapelnienia_bialymi_prog_Spiralna;
                                            Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna.setText(Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna.getText()+ " "+plaskisym_procent_zapelnienia_bialymi_prog_Spiralna+"%");
                                            
                                            
                                            
                                            this.plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata=plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata;
                                            Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata.setText(Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata.getText()+ " "+plaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata+"%");
                                            
                                            
                                            
                                            this.plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata=plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata; 
                                            Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata.setText(Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata.getText()+" "+plaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata+"%");
                                             // dla pelnego widoku      
                                            this.pelny_procent_zapelnienia_jasnymi_prog_karlowata=pelny_procent_zapelnienia_jasnymi_prog_karlowata;
                                            Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata.setText(Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata.getText()+" "+pelny_procent_zapelnienia_jasnymi_prog_karlowata+"%");
                                            
                                            
                                            
                                            this.pelny_procent_zapelnienia_bialymi_prog_karlowata= pelny_procent_zapelnienia_bialymi_prog_karlowata; 
                                            Labelpelny_procent_zapelnienia_bialymi_prog_karlowata.setText(Labelpelny_procent_zapelnienia_bialymi_prog_karlowata.getText()+" "+pelny_procent_zapelnienia_bialymi_prog_karlowata+"%");
                                            
                                              
                                            this.pelny_procent_zapelnienia_jasnymi_prog_Spiralna=pelny_procent_zapelnienia_jasnymi_prog_Spiralna;
                                            Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna.setText(Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna.getText()+" "+pelny_procent_zapelnienia_jasnymi_prog_Spiralna+"%");
                                            
                   
                                            this.pelny_procent_zapelnienia_bialymi_prog_Spiralna=pelny_procent_zapelnienia_bialymi_prog_Spiralna;
                                            Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna.setText(Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna.getText()+" "+pelny_procent_zapelnienia_bialymi_prog_Spiralna+"%");
                                            
                                            
                                                
                                            this.pelny_liczba_jasnych_obiektow_Spiralna=pelny_liczba_jasnych_obiektow_Spiralna;
                                            Labelpelny_liczba_jasnych_obiektow_Spiralna.setText(Labelpelny_liczba_jasnych_obiektow_Spiralna.getText()+" "+pelny_liczba_jasnych_obiektow_Spiralna);
                                            
                                            
                                            this.pelny_procent_zapelnienia_bialymi_prog_Eliptyczna=pelny_procent_zapelnienia_bialymi_prog_Eliptyczna;
                                            Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna.setText(Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna.getText()+" "+pelny_procent_zapelnienia_bialymi_prog_Eliptyczna);    
                                                
                                           //dla nieregularnych    
                                            this.rozmycie_prog_Nieregularna=rozmycie_prog_Nieregularna;
                                            Labelrozmycie_prog_Nieregularna.setText(Labelrozmycie_prog_Nieregularna.getText()+" "+rozmycie_prog_Nieregularna);
                                            
                                            
                                            this.prog_jasnosci_Nieregularna=prog_jasnosci_Nieregularna; 
                                            Labelprog_jasnosci_Nieregularna.setText(Labelprog_jasnosci_Nieregularna.getText()+" "+prog_jasnosci_Nieregularna);
    }                                             
                                                
      public void ustawParametryDlaPreProcessingu( int jasnosc, double kontrast,int Wartosc_Progowa,int rozmycie,int czulosc,int min_wielkoscx,int min_wielkoscy ){
          
        this.jasnosc = jasnosc;
        LabelJasność.setText(LabelJasność.getText()+" "+jasnosc);
        
        this.kontrast = kontrast;
        LabelKontrast.setText(LabelKontrast.getText()+" "+kontrast);
        
        
        this.Wartosc_Progowa = Wartosc_Progowa;
        LabelWartośćProgowa.setText(LabelWartośćProgowa.getText()+" "+Wartosc_Progowa);
        
        this.rozmycie = rozmycie;
        LabelRozmycie.setText(LabelRozmycie.getText()+" "+rozmycie);
        
        
        this.czulosc = czulosc;
        LabelCzułość.setText(LabelCzułość.getText()+" "+czulosc);
        
        
        this.min_wielkoscx = min_wielkoscx;
        LabelMinimalnaWielkośćWOsiX.setText(LabelMinimalnaWielkośćWOsiX.getText()+" "+min_wielkoscx);
        
        
        this.min_wielkoscy = min_wielkoscy;
        LabelMinimalnaWielkośćWOsiY.setText(LabelMinimalnaWielkośćWOsiY.getText()+" "+min_wielkoscy);
        
        } 
 
     synchronized public void DodajDoProgressu(int i,String przetwozonyobraz) throws  InterruptedException, ExecutionException{
          if(liczbaWykonanychSelekcji==0){
           tStart = System.currentTimeMillis();
          }
          else if(liczbaWszystkichObrazów-1==liczbaWykonanychSelekcji)
          {
           tEnd = System.currentTimeMillis();
           long tDelta = tEnd - tStart;
           double elapsedSeconds = tDelta / 1000.0;
           LabelCzasObliczeń.setText(LabelCzasObliczeń.getText()+" "+elapsedSeconds+" sekund");
           
          }
          liczbaWykonanychSelekcji+=i;
          jProgressBar1.setValue((int) (100*liczbaWykonanychSelekcji/liczbaWszystkichObrazów));
          LabelPostępProcentowy.setText("Procentowy postęp: "+" "+(int) (100*liczbaWykonanychSelekcji/liczbaWszystkichObrazów)+"%");
          LabelPrzetwozonyObraz.setText(przetwozonyobraz);
         
          
         
          
      }
    public ArrayList <ArrayList<Galaktyka>> PobierzListęGalaktyk(){
       return ListaGalaktyk; 
    }
     
     public void PrzenieśDaneDoArrayList() throws InterruptedException, ExecutionException{
         ListaGalaktyk = new ArrayList<>();
        for(int wątek = 0 ; wątek < liczbaWątków;wątek++){
            
                 ListaGalaktyk.addAll(Listy.get(wątek).get());
            
        } 
     }
     
     public void ustawTrybPracyZapisuDoPliku(boolean b){
         if(b){
             LabelZapisWyniku.setText(LabelZapisWyniku.getText()+" plików");
         }
         else{
             LabelZapisWyniku.setText(LabelZapisWyniku.getText()+" programu");
         }
         
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        LabelPostępProcentowy = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LabelPrzetwozonyObraz = new javax.swing.JLabel();
        LabelJasność = new javax.swing.JLabel();
        LabelKontrast = new javax.swing.JLabel();
        LabelWartośćProgowa = new javax.swing.JLabel();
        LabelRozmycie = new javax.swing.JLabel();
        LabelCzułość = new javax.swing.JLabel();
        LabelMinimalnaWielkośćWOsiX = new javax.swing.JLabel();
        LabelMinimalnaWielkośćWOsiY = new javax.swing.JLabel();
        Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata = new javax.swing.JLabel();
        Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata = new javax.swing.JLabel();
        Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna = new javax.swing.JLabel();
        Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata = new javax.swing.JLabel();
        Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna = new javax.swing.JLabel();
        Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna = new javax.swing.JLabel();
        Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata = new javax.swing.JLabel();
        Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata = new javax.swing.JLabel();
        Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata = new javax.swing.JLabel();
        Labelpelny_procent_zapelnienia_bialymi_prog_karlowata = new javax.swing.JLabel();
        Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna = new javax.swing.JLabel();
        Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna = new javax.swing.JLabel();
        Labelpelny_liczba_jasnych_obiektow_Spiralna = new javax.swing.JLabel();
        Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna = new javax.swing.JLabel();
        Labelrozmycie_prog_Nieregularna = new javax.swing.JLabel();
        Labelprog_jasnosci_Nieregularna = new javax.swing.JLabel();
        LabelCzasObliczeń = new javax.swing.JLabel();
        LabelLiczbaWątków = new javax.swing.JLabel();
        LabelZapisWyniku = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        LabelPostępProcentowy.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelPostępProcentowy.setText("Procentowy postęp: ");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Ustawione parametry Pre - Processingu:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Ustawienia parametrów Setekcji systemu ekspertowego:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Przetwożony obraz:");

        LabelPrzetwozonyObraz.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelPrzetwozonyObraz.setText("brak przetwozonego obrazu");

        LabelJasność.setText("Jasność: ");

        LabelKontrast.setText("Kontrast: ");

        LabelWartośćProgowa.setText("Wartość  progowa: ");

        LabelRozmycie.setText("Rozmycie: ");

        LabelCzułość.setText("Czułość: ");

        LabelMinimalnaWielkośćWOsiX.setText("Minimalna wielkość w osi X : ");

        LabelMinimalnaWielkośćWOsiY.setText("Minimalna wielkość w osi Y: ");

        Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata.setText(" - Płaski pod przekątną procent zapełnienia jasnymi próg dla Soczewowatych: ");

        Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata.setText(" - Płaski pod przekątną procent zapełnienia białymi próg dla Soczewowatych: ");

        Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna.setText(" - Płaski pod przekątną procent zapełnienia jasnymi próg dla Spiralnych: ");

        Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata.setText(" - Płaski symetrycznie procent zapełnienia jasnymi próg dla Karłowatych: ");

        Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna.setText(" - Płaski symetrycznie procent zapełnienia jasnymi próg dla Spiralnych: ");

        Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna.setText(" - Płaski symetrycznie procent zapełnienia białymi próg dla Spiralnych:");

        Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata.setText(" - Płaski symetrycznie procent zapełnienia jasnymi próg dla Soczewkowatych:");

        Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata.setText(" - Płaski symetrycznie procent zapełnienia białymi próg dla Soczewkowata: ");

        Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata.setText(" - Pełny procent zapełnienia jasnymi próg dla Karłowatych:");

        Labelpelny_procent_zapelnienia_bialymi_prog_karlowata.setText(" - Pełny procent zapełnienia białymi próg dla Karlowatych: ");

        Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna.setText(" - Pełny procent zapełnienia jasnymi próg dla Spiralnych: ");

        Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna.setText(" - Pełny procent zapełnienia białymi próg dla Spiralnych: ");

        Labelpelny_liczba_jasnych_obiektow_Spiralna.setText(" - Pełny procent liczba jasnych obiektów dla Spiralnych: ");

        Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna.setText(" - Pełny procent zapełnienia białymi próg dla Eliptycznych:");

        Labelrozmycie_prog_Nieregularna.setText(" - Pikselowe rozmycie ustawienie dla Nieregularnych: ");

        Labelprog_jasnosci_Nieregularna.setText(" - Próg jasności dla Nieregularnych: ");

        LabelCzasObliczeń.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelCzasObliczeń.setText("Czas obliczenia: ");

        LabelLiczbaWątków.setText("Liczba wykorzystywanych wątków: ");

        LabelZapisWyniku.setText("Zapis wyniku do:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelPrzetwozonyObraz))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(LabelJasność)
                                    .addComponent(LabelKontrast)
                                    .addComponent(LabelWartośćProgowa)
                                    .addComponent(LabelRozmycie)
                                    .addComponent(LabelCzułość)
                                    .addComponent(LabelMinimalnaWielkośćWOsiX)
                                    .addComponent(LabelMinimalnaWielkośćWOsiY)
                                    .addComponent(LabelLiczbaWątków)
                                    .addComponent(LabelZapisWyniku))
                                .addGap(181, 181, 181)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata)
                                    .addComponent(Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna)
                                    .addComponent(Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna)
                                    .addComponent(Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata)
                                    .addComponent(Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna)
                                    .addComponent(Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata)
                                    .addComponent(Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata)
                                    .addComponent(jLabel1)
                                    .addComponent(Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata)
                                    .addComponent(Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata)
                                    .addComponent(Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna)
                                    .addComponent(Labelpelny_procent_zapelnienia_bialymi_prog_karlowata)
                                    .addComponent(Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna)
                                    .addComponent(Labelpelny_liczba_jasnych_obiektow_Spiralna)
                                    .addComponent(Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna)
                                    .addComponent(Labelrozmycie_prog_Nieregularna)
                                    .addComponent(Labelprog_jasnosci_Nieregularna)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPostępProcentowy)
                                .addGap(283, 283, 283)
                                .addComponent(LabelCzasObliczeń)))
                        .addGap(0, 145, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelPostępProcentowy)
                    .addComponent(LabelCzasObliczeń))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(LabelPrzetwozonyObraz))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(LabelLiczbaWątków))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata)
                    .addComponent(LabelZapisWyniku))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata)
                    .addComponent(LabelJasność))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna)
                    .addComponent(LabelKontrast))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna)
                    .addComponent(LabelWartośćProgowa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata)
                    .addComponent(LabelRozmycie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata)
                    .addComponent(LabelCzułość))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata)
                    .addComponent(LabelMinimalnaWielkośćWOsiX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Labelpelny_procent_zapelnienia_bialymi_prog_karlowata)
                    .addComponent(LabelMinimalnaWielkośćWOsiY))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelpelny_liczba_jasnych_obiektow_Spiralna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelrozmycie_prog_Nieregularna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labelprog_jasnosci_Nieregularna)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelCzasObliczeń;
    private javax.swing.JLabel LabelCzułość;
    private javax.swing.JLabel LabelJasność;
    private javax.swing.JLabel LabelKontrast;
    private javax.swing.JLabel LabelLiczbaWątków;
    private javax.swing.JLabel LabelMinimalnaWielkośćWOsiX;
    private javax.swing.JLabel LabelMinimalnaWielkośćWOsiY;
    private javax.swing.JLabel LabelPostępProcentowy;
    private javax.swing.JLabel LabelPrzetwozonyObraz;
    private javax.swing.JLabel LabelRozmycie;
    private javax.swing.JLabel LabelWartośćProgowa;
    private javax.swing.JLabel LabelZapisWyniku;
    private javax.swing.JLabel Labelpelny_liczba_jasnych_obiektow_Spiralna;
    private javax.swing.JLabel Labelpelny_procent_zapelnienia_bialymi_prog_Eliptyczna;
    private javax.swing.JLabel Labelpelny_procent_zapelnienia_bialymi_prog_Spiralna;
    private javax.swing.JLabel Labelpelny_procent_zapelnienia_bialymi_prog_karlowata;
    private javax.swing.JLabel Labelpelny_procent_zapelnienia_jasnymi_prog_Spiralna;
    private javax.swing.JLabel Labelpelny_procent_zapelnienia_jasnymi_prog_karlowata;
    private javax.swing.JLabel Labelplaskipp_procent_zapelnienia_bialymi_prog_Soczewowata;
    private javax.swing.JLabel Labelplaskipp_procent_zapelnienia_jasnymi_prog_Soczewowata;
    private javax.swing.JLabel Labelplaskipp_procent_zapelnienia_jasnymi_prog_Spiralna;
    private javax.swing.JLabel Labelplaskisym_procent_zapelnienia_bialymi_prog_Soczewkowata;
    private javax.swing.JLabel Labelplaskisym_procent_zapelnienia_bialymi_prog_Spiralna;
    private javax.swing.JLabel Labelplaskisym_procent_zapelnienia_jasnymi_prog_Soczewkowata;
    private javax.swing.JLabel Labelplaskisym_procent_zapelnienia_jasnymi_prog_Spiralna;
    private javax.swing.JLabel Labelplaskisym_procent_zapelnienia_jasnymi_prog_karlowata;
    private javax.swing.JLabel Labelprog_jasnosci_Nieregularna;
    private javax.swing.JLabel Labelrozmycie_prog_Nieregularna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

 
   
}
