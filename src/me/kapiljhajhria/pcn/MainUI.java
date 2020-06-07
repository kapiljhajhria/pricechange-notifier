/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.kapiljhajhria.pcn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.management.Notification;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author kapil jhajhria
 */
public class MainUI extends javax.swing.JFrame {

    AmazonProduct product = new AmazonProduct();
    Notifications notification = new Notifications();
    int scheduledTime = 300000; // 5 min
    Timer timer = new Timer(scheduledTime, new ActionListener() {//60000 event will occur every minute

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Scheduled Task Started");
            product.fetchProductData();
            lblFetchedProductName.setText(product.productName);
            lblFetchedProductPrice.setText(product.productLatestPrice);
            lblDateTimeOfFetchedPrice.setText(product.dateTimeOfFetchedPrice);
        }

    });

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();
        System.out.println("hello there");
    }

    public class AmazonProduct {

        String productUrl;
        String productLatestPrice = "";
        String productName = "";
        String dateTimeOfFetchedPrice;
        String productOldPrice = "";

        /**
         *
         * @param filename
         * @param productUrl
         */
        public Element fetchCorrectPrice(Document document) {
            Element priceElement = document.getElementById("priceblock_dealprice");
            if (priceElement == null) {
                priceElement = document.getElementById("priceblock_ourprice");
            }
            if (priceElement == null) {
                priceElement = document.getElementById("priceblock_saleprice");
            }
            return priceElement;
        }

        public void fetchProductData() {
            if (this.productUrl.isEmpty()) {
                notification.playSound("/res/error.wav");
                JOptionPane.showMessageDialog(MainUI.this, "Yoou have entered emtpy URL, please enter amazon product url", "Empty URL", HEIGHT);
                return;
            } else if (!this.productUrl.contains("amazon.in")) {

                notification.playSound("/res/error.wav");
                JOptionPane.showMessageDialog(MainUI.this, "This is not valid URL", "Invalid URL", HEIGHT);
                return;
            }
            try {
                System.out.println("will scrap data for url: " + this.productUrl);
                //---get whole html document
                Document document = Jsoup.connect(this.productUrl).userAgent("Chrome").get();
                System.out.println(document.outerHtml().substring(1, 50));

                //-----get product price
                Element priceElement = fetchCorrectPrice(document);

                this.productLatestPrice = priceElement.wholeText();

                //---get product name
                Element productNameElement = document.getElementById("productTitle");
                this.productName = productNameElement.wholeText().strip();

                System.out.println("product Name is : " + this.productName + "  productPrice: " + this.productLatestPrice);
                this.dateTimeOfFetchedPrice = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()).trim();
//                return "Sucess";

                if (this.productOldPrice.isEmpty()) {
                    this.productOldPrice = this.productLatestPrice;
                } else if (!this.productOldPrice.equals(this.productLatestPrice)) {
                    notification.playSound("/res/tada.wav");
                    System.out.println("oldprice length:" + this.productOldPrice.getClass() + " latestprice length" + this.productLatestPrice.length());
                    JOptionPane.showMessageDialog(MainUI.this, "Price changed from: " + this.productOldPrice + " to " + this.productLatestPrice, "Price Changed", HEIGHT);
                }
            } catch (Exception ex) {
                ex.printStackTrace();//
                JOptionPane.showMessageDialog(null, "Error Fetching product Data", "Fetch Error", HEIGHT);
//                return "Error";
            }
        }

    }

    public class Notifications {

        public void playSound(String resUrl) {
            URL relPath = MainUI.class.getResource(resUrl);
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(relPath));
                clip.start();
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
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

        lblAmazonProductUrl = new javax.swing.JLabel();
        txtBoxAmazonProductUrl = new javax.swing.JTextField();
        btnFetchProductDetails = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblProductName = new javax.swing.JLabel();
        lblProductPrice = new javax.swing.JLabel();
        lblFetchedDetailsTitle = new javax.swing.JLabel();
        lblFetchedProductName = new javax.swing.JLabel();
        lblFetchedProductPrice = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblDateTimeOfFetchedPrice = new javax.swing.JLabel();
        btnPriceNotification = new javax.swing.JButton();
        lblStopScheduledTask = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Amazon Price Change Notifier");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblAmazonProductUrl.setText("Amazon Product URL");

        txtBoxAmazonProductUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBoxAmazonProductUrlActionPerformed(evt);
            }
        });

        btnFetchProductDetails.setText("Go");
        btnFetchProductDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFetchProductDetailsActionPerformed(evt);
            }
        });

        lblProductName.setText("Name");

        lblProductPrice.setText("Price");

        lblFetchedDetailsTitle.setText("Fetched Details");

        lblFetchedProductName.setText("Product Name");

        lblFetchedProductPrice.setText("Product Price");

        jLabel1.setText("on");

        btnPriceNotification.setText("Get Notified On Price Change");
        btnPriceNotification.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnPriceNotificationStateChanged(evt);
            }
        });
        btnPriceNotification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPriceNotificationActionPerformed(evt);
            }
        });

        lblStopScheduledTask.setText("Stop Task");
        lblStopScheduledTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblStopScheduledTaskActionPerformed(evt);
            }
        });

        jMenu1.setText("New Window");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        menuBar.add(jMenu1);

        aboutMenu.setText("About");
        aboutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutMenuMouseClicked(evt);
            }
        });
        menuBar.add(aboutMenu);

        menuExit.setText("Exit");
        menuExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuExitMouseClicked(evt);
            }
        });
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuBar.add(menuExit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBoxAmazonProductUrl)
                        .addGap(18, 18, 18)
                        .addComponent(btnFetchProductDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAmazonProductUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblProductPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblProductName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFetchedProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFetchedProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblDateTimeOfFetchedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPriceNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(lblStopScheduledTask)))))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(lblFetchedDetailsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblAmazonProductUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBoxAmazonProductUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFetchProductDetails))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFetchedDetailsTitle)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(lblFetchedProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProductPrice)
                            .addComponent(lblFetchedProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(lblDateTimeOfFetchedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(185, 185, 185))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPriceNotification)
                            .addComponent(lblStopScheduledTask))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuExitMouseClicked
        timer.stop();
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_menuExitMouseClicked

    private void txtBoxAmazonProductUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBoxAmazonProductUrlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBoxAmazonProductUrlActionPerformed

    private void btnFetchProductDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFetchProductDetailsActionPerformed
        // TODO add your handling code here:
        product.productUrl = txtBoxAmazonProductUrl.getText();

        System.out.println("product url is" + product.productUrl);
        product.fetchProductData();

        lblFetchedProductName.setText(product.productName);
        lblFetchedProductPrice.setText(product.productLatestPrice);
        lblDateTimeOfFetchedPrice.setText(product.dateTimeOfFetchedPrice);
    }//GEN-LAST:event_btnFetchProductDetailsActionPerformed

    private void btnPriceNotificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPriceNotificationActionPerformed
        if (product.productLatestPrice.isEmpty()) {
            notification.playSound("/res/error.wav");
            JOptionPane.showMessageDialog(null, "Please enter the URL first and then click on 'go' button to fetch product data", "No Data", HEIGHT);
        } else {
            timer.setInitialDelay(0);//60000 means 1 minute//
            timer.start();
        }


    }//GEN-LAST:event_btnPriceNotificationActionPerformed

    private void lblStopScheduledTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblStopScheduledTaskActionPerformed
        timer.stop();
        System.out.println("Scheduled Task Stopped");
    }//GEN-LAST:event_lblStopScheduledTaskActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println("closing window now");
        timer.stop();// TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void btnPriceNotificationStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnPriceNotificationStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPriceNotificationStateChanged

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
        // delete 
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        new MainUI().setVisible(true); 
        // delete // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1MouseClicked

    private void aboutMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutMenuMouseClicked
    System.out.println("about us");
    JOptionPane.showMessageDialog(MainUI.this, "Name : Kapil Jhajhria \n Email : jhajhria44@gmail.com", "Developer Details", HEIGHT);
    }//GEN-LAST:event_aboutMenuMouseClicked

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JButton btnFetchProductDetails;
    private javax.swing.JButton btnPriceNotification;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAmazonProductUrl;
    private javax.swing.JLabel lblDateTimeOfFetchedPrice;
    private javax.swing.JLabel lblFetchedDetailsTitle;
    private javax.swing.JLabel lblFetchedProductName;
    private javax.swing.JLabel lblFetchedProductPrice;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductPrice;
    private javax.swing.JButton lblStopScheduledTask;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuExit;
    private javax.swing.JTextField txtBoxAmazonProductUrl;
    // End of variables declaration//GEN-END:variables
}
