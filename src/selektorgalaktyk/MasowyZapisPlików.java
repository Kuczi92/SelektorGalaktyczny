/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Quchi
 */
public class MasowyZapisPlików extends javax.swing.JFrame implements Runnable {

    ArrayList<ArrayList<Galaktyka>> ListaDoZapisu;
    String ŚcieżkaFolderuWyjściowego;
    /**
     * Creates new form MasowyZapisPlików
     */
    public MasowyZapisPlików(ArrayList<ArrayList<Galaktyka>> ListaDoZapisu) {
        super("Zapisywanie do wybranej ścieżki");
        this.ListaDoZapisu = ListaDoZapisu;
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width/4, height/4);
    }
    public void ustawfolderŹródłowy(String ŚcieżkaFolderuWyjściowego){
        this.ŚcieżkaFolderuWyjściowego = ŚcieżkaFolderuWyjściowego;
        LabelŚcieżkaFolderu.setText(LabelŚcieżkaFolderu.getText()+ " "+ŚcieżkaFolderuWyjściowego);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ProgressBarPostęp = new javax.swing.JProgressBar();
        LabelPostęp = new javax.swing.JLabel();
        LabelZapisanyPlik = new javax.swing.JLabel();
        LabelŚcieżkaFolderu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        LabelPostęp.setText("Postęp: ");

        LabelZapisanyPlik.setText("Zapisywany plik: ");

        LabelŚcieżkaFolderu.setText("Wybrany folder wyjścia:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProgressBarPostęp, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelZapisanyPlik)
                            .addComponent(LabelŚcieżkaFolderu)
                            .addComponent(LabelPostęp, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelPostęp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProgressBarPostęp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelŚcieżkaFolderu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelZapisanyPlik)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelPostęp;
    private javax.swing.JLabel LabelZapisanyPlik;
    private javax.swing.JLabel LabelŚcieżkaFolderu;
    private javax.swing.JProgressBar ProgressBarPostęp;
    // End of variables declaration//GEN-END:variables
    public void zapiszWynikiDoPliku(ArrayList<Galaktyka> ListaGalaktyk,String ScieżkaFolderuZapisu){

         
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
                        LabelZapisanyPlik.setText("Zapisywany plik: "+wynik.replace(ŚcieżkaFolderuWyjściowego,""));
                        writeImage(wynik,ListaGalaktyk.get(wykrytagalaktyka).getObraz());
                    }
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
    
    @Override
    public void run() {
        
        int rozmiarListy=ListaDoZapisu.size();
        for(int zapisywanagalaktyka = 0 ; zapisywanagalaktyka<rozmiarListy ;zapisywanagalaktyka++)
            {
                zapiszWynikiDoPliku(ListaDoZapisu.get(zapisywanagalaktyka),ŚcieżkaFolderuWyjściowego);
                ProgressBarPostęp.setValue((int) (100.0*zapisywanagalaktyka/rozmiarListy));
                LabelPostęp.setText("Postęp: "+String.valueOf(100.0*zapisywanagalaktyka/rozmiarListy)+" %");
            }
        ProgressBarPostęp.setValue(100);
        LabelPostęp.setText("Postęp: "+(100.0)+" %");
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(MasowyZapisPlików.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }
}
