/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Quchi
 */
public class OknoListyGalaktyk extends JFrame implements ActionListener{
    int galaktyka = 0;
    Wyświetlacz Wyświetlacz;
    Wyświetlacz Miniatura;
    ArrayList<Galaktyka> ListaGalaktyk;
    ArrayList<ArrayList<Galaktyka>> ListaGalaktykMasowa;
    double stosunekDługościOryginału;
    double ProcentXWzględemOryginału;
    double ProcentYWzględemOryginału;
    PanelSterowania Panel;
    /**
     * Creates new form OknoListyGalaktyk
     */
    public OknoListyGalaktyk(ArrayList<Galaktyka> ListaGalaktyk,BufferedImage ObrazOryginalny) {
        super("Lista Galaktyk");
        this.ListaGalaktyk = ListaGalaktyk;
        ustawOryginalInicjacja(ObrazOryginalny);
        ustawMiniaturęInicjacja(ListaGalaktyk.get(0).ObrazGalaktyki);
        initComponents();
        LabelPrzeglądanaGalaktyka.setText("Dana Wykryta galaktyka: "+String.valueOf(galaktyka+1)+" na: "+(ListaGalaktyk.size())+" ");
        Wyświetlacz.UstawProstokąt((int)(ListaGalaktyk.get(galaktyka).Punkt1.x*stosunekDługościOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt1.y*stosunekDługościOryginału),    (int)(ListaGalaktyk.get(galaktyka).Punkt2.x*stosunekDługościOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt2.y*stosunekDługościOryginału),   (int)(ListaGalaktyk.get(galaktyka).Punkt3.x*stosunekDługościOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt3.y*stosunekDługościOryginału),      (int)(ListaGalaktyk.get(galaktyka).Punkt4.x*stosunekDługościOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt4.y*stosunekDługościOryginału));
         
        ustawDane(ListaGalaktyk.get(0));
        
        ListOryginalneZdjęcia.setVisible(false);
        
        LabelListaOryginalnychZdjęć.setVisible(false);
    }
    
    
    public OknoListyGalaktyk(PanelSterowania Panel,ArrayList<ArrayList<Galaktyka>> ListaGalaktyk){
     super("Lista Galaktyk po selekcji masowej");  
     this.ListaGalaktykMasowa =  ListaGalaktyk;
     this.ListaGalaktyk = ListaGalaktykMasowa.get(0);
     this.Panel = Panel;
     ustawOryginalInicjacja(ŁadujObraz(ListaGalaktykMasowa.get(0).get(0).Żródło));
     ustawMiniaturęInicjacja(ListaGalaktykMasowa.get(0).get(0).ObrazGalaktyki);
     initComponents();
     LabelPrzeglądanaGalaktyka.setText("Dana Wykryta galaktyka: "+String.valueOf(galaktyka+1)+" na: "+(ListaGalaktykMasowa.get(0).size())+" ");
     Wyświetlacz.UstawProstokąt((int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt1.x*stosunekDługościOryginału),(int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt1.y*stosunekDługościOryginału),    (int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt2.x*stosunekDługościOryginału),(int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt2.y*stosunekDługościOryginału),   (int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt3.x*stosunekDługościOryginału),(int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt3.y*stosunekDługościOryginału),      (int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt4.x*stosunekDługościOryginału),(int)(ListaGalaktykMasowa.get(0).get(galaktyka).Punkt4.y*stosunekDługościOryginału));
         
     ustawDane(ListaGalaktykMasowa.get(0).get(0));
     InicjacjaListy();
     setVisible(true);
    }
    
    
    public BufferedImage ŁadujObraz(String sciezka){
     BufferedImage img = null;
        try {
            img = ImageIO.read(new File(sciezka));
        } catch (IOException e) {
        }   
     return img;
    }
    
    public void InicjacjaListy(){
        ArrayList<String> ListaPlików = new ArrayList<>();
        int rozmiar =  ListaGalaktykMasowa.size();
        
        for(int i = 0 ; i < rozmiar;i++){
            ListaPlików.add(ListaGalaktykMasowa.get(i).get(0).getŹródło());
        }
       String[] stringsa = ListaPlików.toArray(new String[ListaPlików.size()]) ;
        
        ListOryginalneZdjęcia.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = stringsa;
            @Override
            public int getSize() { return strings.length; }
            @Override
            public String getElementAt(int i) { return strings[i]; }
        }); 
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        PanelWyświetlacz = Wyświetlacz;
        ButtonPoprzedni = new javax.swing.JButton();
        ButtonNastępny = new javax.swing.JButton();
        LabelPrzeglądanaGalaktyka = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        PanelMiniatura = Miniatura;
        jLabel2 = new javax.swing.JLabel();
        LabelŹródło = new javax.swing.JLabel();
        LabelWidok = new javax.swing.JLabel();
        LabelTypGalaktyki = new javax.swing.JLabel();
        LabelPikseleJasne = new javax.swing.JLabel();
        LabelLiczbaJasnychPunktow = new javax.swing.JLabel();
        LabelPikseleJasneWartość = new javax.swing.JLabel();
        LabelPikseleBiałe = new javax.swing.JLabel();
        LabelPikseleBiałeWartość = new javax.swing.JLabel();
        LabelLiczbaJasnychPunktowWartość = new javax.swing.JLabel();
        LabelLiczbaWykrytychJąderGalaktyki = new javax.swing.JLabel();
        LabelLiczbaWykrytychJąderGalaktykiWartość = new javax.swing.JLabel();
        LabelŹródłoWartość = new javax.swing.JLabel();
        LabelWidokWartość = new javax.swing.JLabel();
        LabelTypGalaktykiWartość = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListOryginalneZdjęcia = new javax.swing.JList<>();
        LabelListaOryginalnychZdjęć = new javax.swing.JLabel();
        ButtonZapisz = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout PanelWyświetlaczLayout = new javax.swing.GroupLayout(PanelWyświetlacz);
        PanelWyświetlacz.setLayout(PanelWyświetlaczLayout);
        PanelWyświetlaczLayout.setHorizontalGroup(
            PanelWyświetlaczLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelWyświetlaczLayout.setVerticalGroup(
            PanelWyświetlaczLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );

        ButtonPoprzedni.setText("Poprzedni");
        ButtonPoprzedni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonPoprzedniMouseClicked(evt);
            }
        });

        ButtonNastępny.setText("Następny");
        ButtonNastępny.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonNastępnyMouseClicked(evt);
            }
        });

        LabelPrzeglądanaGalaktyka.setText("Dana Wykryta galaktyka: ");

        javax.swing.GroupLayout PanelMiniaturaLayout = new javax.swing.GroupLayout(PanelMiniatura);
        PanelMiniatura.setLayout(PanelMiniaturaLayout);
        PanelMiniaturaLayout.setHorizontalGroup(
            PanelMiniaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        PanelMiniaturaLayout.setVerticalGroup(
            PanelMiniaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jLabel2.setText("Miniatura Danej Galaktyki");

        LabelŹródło.setText("Źródło z którego wykryto galaktykę: ");

        LabelWidok.setText("Widok na galaktykę:");

        LabelTypGalaktyki.setText("Typ galaktyki:  ");

        LabelPikseleJasne.setText("Piksele jasne:");

        LabelLiczbaJasnychPunktow.setText("Liczba jasnych punktów:");

        LabelPikseleJasneWartość.setText("0");

        LabelPikseleBiałe.setText("Piksele białe:");

        LabelPikseleBiałeWartość.setText("Piksele białe:");

        LabelLiczbaJasnychPunktowWartość.setText("1");

        LabelLiczbaWykrytychJąderGalaktyki.setText("Liczba wykrytych jąder galaktyki: ");

        LabelLiczbaWykrytychJąderGalaktykiWartość.setText("0");

        LabelŹródłoWartość.setText("jLabel3");

        LabelWidokWartość.setText("jLabel3");

        LabelTypGalaktykiWartość.setText("jLabel3");

        ListOryginalneZdjęcia.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ListOryginalneZdjęcia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListOryginalneZdjęciaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ListOryginalneZdjęcia);

        LabelListaOryginalnychZdjęć.setText("Lista oryginalnych zdjęć oraz wyników zawartych z ich poszczególnej analizy:");

        ButtonZapisz.setText("Zapisz Obraz wykrytej galaktyki");
        ButtonZapisz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonZapiszMouseClicked(evt);
            }
        });

        jLabel3.setText("-");

        jLabel4.setText("-");

        jLabel5.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelŹródło)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelWidok)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelWidokWartość))
                            .addComponent(LabelTypGalaktyki))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelŹródłoWartość, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 273, Short.MAX_VALUE)
                                .addComponent(LabelListaOryginalnychZdjęć))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonPoprzedni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonNastępny)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelPrzeglądanaGalaktyka)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelLiczbaWykrytychJąderGalaktyki)
                            .addComponent(LabelLiczbaJasnychPunktow)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelLiczbaJasnychPunktowWartość, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelLiczbaWykrytychJąderGalaktykiWartość, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPikseleBiałe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelPikseleBiałeWartość))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelPikseleJasne)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelPikseleJasneWartość))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTypGalaktykiWartość)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(PanelMiniatura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonZapisz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(PanelWyświetlacz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelWyświetlacz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonPoprzedni)
                    .addComponent(ButtonNastępny)
                    .addComponent(LabelPrzeglądanaGalaktyka)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelMiniatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonZapisz))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelŹródło)
                            .addComponent(LabelŹródłoWartość))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelWidok)
                            .addComponent(LabelWidokWartość)
                            .addComponent(LabelListaOryginalnychZdjęć))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelTypGalaktyki)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelTypGalaktykiWartość)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelPikseleJasne)
                                    .addComponent(LabelPikseleJasneWartość))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelPikseleBiałe)
                                    .addComponent(LabelPikseleBiałeWartość))
                                .addGap(26, 26, 26)
                                .addComponent(LabelLiczbaWykrytychJąderGalaktyki)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelLiczbaWykrytychJąderGalaktykiWartość)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelLiczbaJasnychPunktow)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelLiczbaJasnychPunktowWartość)
                                    .addComponent(jLabel4)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonPoprzedniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonPoprzedniMouseClicked
        galaktyka--;
        if(galaktyka<0){
            galaktyka = 0;
        }
       ustawMiniaturę(ListaGalaktyk.get(galaktyka).getObraz());
       ustawDane(ListaGalaktyk.get(galaktyka));
       LabelPrzeglądanaGalaktyka.setText("Dana Wykryta galaktyka: "+String.valueOf(galaktyka+1)+" na: "+(ListaGalaktyk.size())+" ");
       Wyświetlacz.UstawProstokąt((int)(ListaGalaktyk.get(galaktyka).Punkt1.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt1.y*ProcentYWzględemOryginału),    (int)(ListaGalaktyk.get(galaktyka).Punkt2.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt2.y*ProcentYWzględemOryginału),   (int)(ListaGalaktyk.get(galaktyka).Punkt3.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt3.y*ProcentYWzględemOryginału),      (int)(ListaGalaktyk.get(galaktyka).Punkt4.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt4.y*ProcentYWzględemOryginału));
       Odswierzenie();
    }//GEN-LAST:event_ButtonPoprzedniMouseClicked

    
    
    private void ButtonNastępnyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonNastępnyMouseClicked
       galaktyka++; 
       if(galaktyka>=ListaGalaktyk.size()-1){
            galaktyka = ListaGalaktyk.size()-1;
        }
       ustawMiniaturę(ListaGalaktyk.get(galaktyka).getObraz());
       ustawDane(ListaGalaktyk.get(galaktyka));
       LabelPrzeglądanaGalaktyka.setText("Dana Wykryta galaktyka: "+String.valueOf(galaktyka+1)+" na: "+(ListaGalaktyk.size())+" ");
       Wyświetlacz.UstawProstokąt((int)(ListaGalaktyk.get(galaktyka).Punkt1.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt1.y*ProcentYWzględemOryginału),    (int)(ListaGalaktyk.get(galaktyka).Punkt2.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt2.y*ProcentYWzględemOryginału),   (int)(ListaGalaktyk.get(galaktyka).Punkt3.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt3.y*ProcentYWzględemOryginału),      (int)(ListaGalaktyk.get(galaktyka).Punkt4.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt4.y*ProcentYWzględemOryginału));
       Odswierzenie();
    }//GEN-LAST:event_ButtonNastępnyMouseClicked

    private void ListOryginalneZdjęciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListOryginalneZdjęciaMouseClicked
        ustawOryginal(ŁadujObraz(ListOryginalneZdjęcia.getSelectedValue()));
        this.ListaGalaktyk = ListaGalaktykMasowa.get(ListOryginalneZdjęcia.getSelectedIndex());
        galaktyka=0;
        ustawMiniaturę(ListaGalaktyk.get(galaktyka).getObraz());
        ustawDane(ListaGalaktyk.get(galaktyka));
        LabelPrzeglądanaGalaktyka.setText("Dana Wykryta galaktyka: "+String.valueOf(galaktyka+1)+" na: "+(ListaGalaktyk.size())+" ");
        Wyświetlacz.UstawProstokąt((int)(ListaGalaktyk.get(galaktyka).Punkt1.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt1.y*ProcentYWzględemOryginału),    (int)(ListaGalaktyk.get(galaktyka).Punkt2.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt2.y*ProcentYWzględemOryginału),   (int)(ListaGalaktyk.get(galaktyka).Punkt3.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt3.y*ProcentYWzględemOryginału),      (int)(ListaGalaktyk.get(galaktyka).Punkt4.x*ProcentXWzględemOryginału),(int)(ListaGalaktyk.get(galaktyka).Punkt4.y*ProcentYWzględemOryginału));
        Odswierzenie();
    }//GEN-LAST:event_ListOryginalneZdjęciaMouseClicked

    private void ButtonZapiszMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonZapiszMouseClicked
       ZapisPliku ZapisObrazu = new ZapisPliku(ListaGalaktyk.get(galaktyka).getŹródło(),galaktyka);
       
        int returnVal = ZapisObrazu.WybieraczZapis.showSaveDialog(null);
           if(returnVal == JFileChooser.APPROVE_OPTION) {
           Panel.Konsola.append("\nWybrałeś tą ściężkę na wyjściowy zapis wykrytego obrazu galaktyki: " +
            ZapisObrazu.WybieraczZapis.getSelectedFile().getAbsolutePath()+"\n");
            
           }
        writeImage(ZapisObrazu.WybieraczZapis.getSelectedFile().getAbsolutePath(),ListaGalaktyk.get(galaktyka).ObrazGalaktyki); // TODO add your handling code here:
    }//GEN-LAST:event_ButtonZapiszMouseClicked
    
    public void writeImage(String filePath,BufferedImage image){
        try{
            File f = new File(filePath);
            String fileType = filePath.substring(filePath.lastIndexOf('.')+1);
            ImageIO.write(image, fileType, f);
        }catch(IOException e){
            System.out.println("Error Occurred!\n"+e);
        }
    }
    public void ustawDane(Galaktyka Galaktyka){
        LabelŹródłoWartość.setText(Galaktyka.getŹródło());
        LabelLiczbaJasnychPunktowWartość.setText(String.valueOf(Galaktyka.getLiczbaJasnychPunktów()));
        LabelLiczbaWykrytychJąderGalaktykiWartość.setText(String.valueOf(Galaktyka.getLiczbaJąderGalaktyki()));
        LabelPikseleBiałeWartość.setText(String.format("%.2f", (float)Galaktyka.getPikseleBiałe())+"%");
        LabelPikseleJasneWartość.setText(String.format("%.2f", (float)Galaktyka.getPikseleJasne())+"%");
        LabelTypGalaktykiWartość.setText(String.valueOf(Galaktyka.getTypGalaktyki()));
        LabelWidokWartość.setText(String.valueOf(Galaktyka.getWidokNaGalaktyke()).replace('_', ' '));
    }
    
    public void ustawMiniaturę(BufferedImage img){
        if(img.getWidth()<=200&&img.getHeight()<=200){
            Miniatura.UstawNowyObraz(img);
        }
        
        else if(img.getWidth()>=img.getHeight())
                {
            int NowyY = img.getHeight()*200/img.getWidth();
            Miniatura.UstawNowyObraz(ZmieńRozmiarObrazu(img,200,NowyY));
        }
        
        else if(img.getHeight()>=img.getWidth())
                {
            int NowyX = img.getWidth()*200/img.getHeight();
            Miniatura.UstawNowyObraz(ZmieńRozmiarObrazu(img,NowyX,200));
        }
         
    }
    public void ustawMiniaturęInicjacja(BufferedImage img){
        if(img.getWidth()<=200&&img.getHeight()<=200){
            Miniatura = new Wyświetlacz(img);
        }
        else if(img.getWidth()>=img.getHeight())
                {
            int NowyY = img.getHeight()*200/img.getWidth();
            Miniatura= new Wyświetlacz(ZmieńRozmiarObrazu(img,200,NowyY));
        }
        
        else if(img.getHeight()>=img.getWidth())
                {
            int NowyX = img.getWidth()*200/img.getHeight();
            Miniatura = new Wyświetlacz(ZmieńRozmiarObrazu(img,NowyX,200));
        }
         
    }
    
     public void ustawOryginal(BufferedImage img){
         if(img.getWidth()<=900&&img.getHeight()<=434){
            Wyświetlacz.UstawNowyObraz(img);
            stosunekDługościOryginału = 1.0;
            ProcentXWzględemOryginału = 1.0;
            ProcentYWzględemOryginału =1.0;
        }
        else if(img.getWidth()>=img.getHeight())
                {
                    stosunekDługościOryginału =(double) img.getWidth()/img.getHeight();
                    int NowyY = (int) (900*stosunekDługościOryginału);

                    if(NowyY>434){

                        int NowyX =  (int) (434*stosunekDługościOryginału);   
                        Wyświetlacz.UstawNowyObraz(ZmieńRozmiarObrazu(img,NowyX,434));
                        ProcentXWzględemOryginału =(double) NowyX/img.getWidth();
                        ProcentYWzględemOryginału =(double) 434.0/img.getHeight();
                        stosunekDługościOryginału= ProcentYWzględemOryginału;
                        }
                    else{
                        Wyświetlacz.UstawNowyObraz(ZmieńRozmiarObrazu(img,900,NowyY));
                        ProcentXWzględemOryginału =(double) 900.0/img.getWidth();
                        ProcentYWzględemOryginału =(double) NowyY/img.getHeight();
                    }
           
        }
        
        else if(img.getHeight()>=img.getWidth())
                {
                    stosunekDługościOryginału =(double) img.getHeight()/img.getWidth();
                    int NowyX =  (int) (434*stosunekDługościOryginału);
                        if(NowyX>900){
                            int NowyY = (int) (900*stosunekDługościOryginału);
                            Wyświetlacz.UstawNowyObraz(ZmieńRozmiarObrazu(img,900,NowyY));
                            ProcentXWzględemOryginału =(double) 900/img.getWidth();
                            ProcentYWzględemOryginału =(double) NowyY/img.getHeight();
                        }
                        else{
                             Wyświetlacz.UstawNowyObraz(ZmieńRozmiarObrazu(img,NowyX,434));
                             ProcentXWzględemOryginału =(double) NowyX/img.getWidth();
                             ProcentYWzględemOryginału =(double) 434/img.getHeight();
                        }
           
        }
     }
    
    
    
    
    
    public void ustawOryginalInicjacja(BufferedImage img){
        
        
        
        if(img.getWidth()<=900&&img.getHeight()<=434){
            Wyświetlacz = new Wyświetlacz(img);
            stosunekDługościOryginału = 1.0;
            ProcentXWzględemOryginału = 1.0;
            ProcentYWzględemOryginału =1.0;
        }
        else if(img.getWidth()>=img.getHeight())
                {
                    stosunekDługościOryginału =(double) img.getWidth()/img.getHeight();
                    int NowyY = (int) (900*stosunekDługościOryginału);

                    if(NowyY>434){

                        int NowyX =  (int) (434*stosunekDługościOryginału);   
                        Wyświetlacz= new Wyświetlacz(ZmieńRozmiarObrazu(img,NowyX,434));
                        ProcentXWzględemOryginału =(double) NowyX/img.getWidth();
                        ProcentYWzględemOryginału =(double) 434.0/img.getHeight();
                        stosunekDługościOryginału= ProcentYWzględemOryginału;
                        }
                    else{
                        Wyświetlacz= new Wyświetlacz(ZmieńRozmiarObrazu(img,900,NowyY));
                        ProcentXWzględemOryginału =(double) 900.0/img.getWidth();
                        ProcentYWzględemOryginału =(double) NowyY/img.getHeight();
                    }
           
        }
        
        else if(img.getHeight()>=img.getWidth())
                {
                    stosunekDługościOryginału =(double) img.getHeight()/img.getWidth();
                    int NowyX =  (int) (434*stosunekDługościOryginału);
                        if(NowyX>900){
                            int NowyY = (int) (900*stosunekDługościOryginału);
                            Wyświetlacz = new Wyświetlacz(ZmieńRozmiarObrazu(img,900,NowyY));
                            ProcentXWzględemOryginału =(double) 900/img.getWidth();
                            ProcentYWzględemOryginału =(double) NowyY/img.getHeight();
                        }
                        else{
                             Wyświetlacz = new Wyświetlacz(ZmieńRozmiarObrazu(img,NowyX,434));
                             ProcentXWzględemOryginału =(double) NowyX/img.getWidth();
                             ProcentYWzględemOryginału =(double) 434/img.getHeight();
                        }
           
        }
         
    }
    public static BufferedImage ZmieńRozmiarObrazu(BufferedImage img, int newW, int newH) { 
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

    return dimg;
}
    
    
    
    public void Odswierzenie(){
                                      this.repaint();
        }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OknoListyGalaktyk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        //</editor-fold>

        /* Create and display the form */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonNastępny;
    private javax.swing.JButton ButtonPoprzedni;
    private javax.swing.JButton ButtonZapisz;
    private javax.swing.JLabel LabelLiczbaJasnychPunktow;
    private javax.swing.JLabel LabelLiczbaJasnychPunktowWartość;
    private javax.swing.JLabel LabelLiczbaWykrytychJąderGalaktyki;
    private javax.swing.JLabel LabelLiczbaWykrytychJąderGalaktykiWartość;
    private javax.swing.JLabel LabelListaOryginalnychZdjęć;
    private javax.swing.JLabel LabelPikseleBiałe;
    private javax.swing.JLabel LabelPikseleBiałeWartość;
    private javax.swing.JLabel LabelPikseleJasne;
    private javax.swing.JLabel LabelPikseleJasneWartość;
    private javax.swing.JLabel LabelPrzeglądanaGalaktyka;
    private javax.swing.JLabel LabelTypGalaktyki;
    private javax.swing.JLabel LabelTypGalaktykiWartość;
    private javax.swing.JLabel LabelWidok;
    private javax.swing.JLabel LabelWidokWartość;
    private javax.swing.JLabel LabelŹródło;
    private javax.swing.JLabel LabelŹródłoWartość;
    private javax.swing.JList<String> ListOryginalneZdjęcia;
    private javax.swing.JPanel PanelMiniatura;
    private javax.swing.JPanel PanelWyświetlacz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
 
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
