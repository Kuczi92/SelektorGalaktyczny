/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selektorgalaktyk;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Quchi
 */

    public class ImageProcessing extends JFrame implements ActionListener {

    private  JPanel imagePanel;
    private  JPanel buttonPanel;
    private JButton binaryButton;
    private  JButton loadButton;    
    private BufferedImage image;    
    private final String WINDOW_TITLE = "Image Processing";

    public ImageProcessing() {
        createWindow();
    }

    private void createWindow() {
        this.setTitle(WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
        this.setSize(500, 500);

        imagePanel = new ImagePanel();
        buttonPanel = new JPanel();
        this.add(imagePanel, BorderLayout.CENTER);

        loadButton = new JButton("Load image");
        loadButton.addActionListener(this);
        buttonPanel.add(loadButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        binaryButton = new JButton("binary");
        binaryButton.addActionListener(this);
        buttonPanel.add(binaryButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == this.loadButton) {
           String filePath = getImageFile();
        if (filePath != null) {
          try {
            image = ImageIO.read(new File(filePath));
           // imageBackup = image;
        } catch (IOException e1) {
        }
          this.repaint();
        }
       } else if (e.getSource() == this.binaryButton) 
                                                       {
               BufferedImage mask = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
                                      Graphics g  = mask.getGraphics();
                                      g.drawImage(image, 0, 0, this);
                                      g.dispose();
                                      image = mask;
                                      this.repaint();
                                                       }
    }
    
     public void actionPerformed() {
      
                                                       
               BufferedImage mask = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
                                      Graphics g  = mask.getGraphics();
                                      g.drawImage(image, 0, 0, this);
                                      g.dispose();
                                      image = mask;
                                      this.repaint();
                                                       
    }

    private String getImageFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
          file = chooser.getSelectedFile();
          return file.getPath();
        } else
          return null;
    }       


    class ImagePanel extends JPanel {
       public void paint(Graphics g) {
          g.drawImage(image, 0, 0, this);
       }        
    }
}

