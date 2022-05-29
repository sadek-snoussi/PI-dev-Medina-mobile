/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Categorie;
import com.mycompany.Entity.User;
import com.mycompany.Service.CategoryService;
import com.mycompany.Service.ProductService;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class Add {

    Form f;

    com.codename1.io.File file;
    String fileName;
    
    
    public static int idUser=User.user.getId();
    
        TextField tf_nom;
        TextField tf_ref;
        ComboBox<String> comboCat;
        TextField tf_mat;
        TextField tf_prixBase;
        TextField tf_prixVente;
//      Date Picker
        Picker date;
//      Date Picker
        TextField tf_quantite ;

        
    public Add() {

        f = new Form("Add Product.",new BoxLayout(BoxLayout.Y_AXIS));
        
        

        Resources theme = UIManager.initFirstTheme("/theme");
        f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"), (ev) -> {
            try {

                MesProduits myp = new MesProduits();
                myp.getF().show();
                
            } catch (IOException ex) {

            }
        });

        CategoryService serv = new CategoryService();
        ProductService prodServ = new ProductService();

        //-------------------------------Components-------------------------------------
        tf_nom = new TextField("", "Nom Produit");
       
        
        
        
        
        tf_ref = new TextField("", "Reference Produit - EXP : 1234");
        
//      ComboBox Categorie
        comboCat = new ComboBox<String>();
        for (Categorie item : serv.getList()) {
        comboCat.addItem(item.getNomCategorie());
        }
//      ComboBox Categorie

        tf_mat = new TextField("", "Matiére Produit");
        
        tf_prixBase = new TextField("", "Prix de Base - EXP : 1234");
         
         tf_prixVente = new TextField("", "Prix de Vente - EXP : 1234");

         tf_quantite = new TextField("", "Quantité Disponible - EXP : 1234");
 
        //      upload 
                Container uploadCont = new Container(new GridLayout(1, 2));
                Button upload = new Button("upload");
                ImageViewer imgV = new ImageViewer();
                imgV.setPreferredSize(new Dimension(75,75));
                Label img_lab = new Label(" ");
                img_lab.setVisible(false);
                //imgV.setVisible(false);
                uploadCont.add(upload);
                uploadCont.add(imgV);
                uploadCont.add(img_lab);

                upload.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        ActionListener actionListner = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ev) {

                                if (ev != null && ev.getSource() != null) {

                                    String filePath = (String) ev.getSource();
                                    System.out.println(filePath);

                                    // file=(File) ev.getSource();
                                    int fileNameIndex = filePath.lastIndexOf("/") + 1;
                                    fileName = filePath.substring(fileNameIndex, filePath.length() - 4) + "jpg";

                                    System.out.println(fileName);

                                    Image img = null;

                                    try {

                                        img = Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath));
                                        imgV.setImage(img);
                                        img_lab.setText(filePath);
                                        //imgV.setVisible(true);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        };

                        Display.getInstance().openGallery(actionListner, Display.GALLERY_IMAGE);

                    }
                });
        //      upload        

        SpanLabel nb = new SpanLabel("Veuiller Remplir Tout Les Champs.*");
        Button add = new Button("Ajouter");
        
        
        

        //=======================================================================================
        //=====================================TRAITEMENT========================================
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                if (controle_AntiNull()==true 
                && Controle_reference() == true 
                && Controle_prixBase() == true
                && Controle_prixVente() == true 
                && Controle_quantite() == true ) {

                try {

                    prodServ.saveFile(img_lab.getText(), fileName);

                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/add/"
                            + "RF"+tf_ref.getText()+idUser
                            + "/" + tf_nom.getText()
                            + "/" + comboCat.getSelectedItem()
                            + "/" + tf_mat.getText()
                            + "/" + tf_prixBase.getText()
                            + "/" + tf_prixVente.getText()
                            + "/" + tf_quantite.getText()
                            + "/" + fileName
                            + "/" + idUser);

                    System.out.println("************************");
                    System.out.println(tf_ref.getText()
                            + "/" + tf_nom.getText()
                            + "/" + comboCat.getSelectedItem()
                            + "/" + tf_mat.getText()
                            + "/" + tf_prixBase.getText()
                            + "/" + tf_prixVente.getText()
                            + "/" + tf_quantite.getText()
                            + "/" + img_lab.getText()
                            + "/" + 1);
                    System.out.println("************************");

                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {

                            System.out.println("Le Produit a été ajouté avec succées.");
                            
                            ToastBar.showMessage("Produit ajouté avec succées.",FontImage.MATERIAL_DONE);


                        }
                    });

                    con.setFailSilently(true);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    
                    //-----------------------------NOTIFICATION-----------------------------
                    
                    
                                                
//                            LocalNotification n = new LocalNotification();
//                            n.setId("demo-notification");
//                            n.setAlertBody("Le Produit a été ajouté avec succées.");
//                            n.setAlertTitle("Success.");
//                            //n.setAlertSound("beep-01a.mp3");
//                            System.out.println("*******************************");
//                            Display.getInstance().scheduleLocalNotification(
//                                    n,
//                                    System.currentTimeMillis() + 10 * 1000, // fire date/time
//                                    LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
//                            );
                            
                            
                //----------------------------------------------------------
        

                    MesProduits List = new MesProduits();
                    List.getF().show();

                } catch (IOException ex) {
                }
                        }
            }
        });

        //=======================================================================================
        //=======================================================================================
        f.add(tf_nom);
        f.add(tf_ref);
        f.add(comboCat);
        f.add(tf_mat);
        f.add(tf_prixBase);
        f.add(tf_prixVente);
        //f.add(dateCont);
        f.add(tf_quantite);
        f.add(uploadCont);
        f.add(nb);
        f.add(add);

    }

    //---------------------------------------------------------------------
    //                           CONTROLE DE SAISIE
    //---------------------------------------------------------------------
    
    
    private boolean Controle_reference() {
            
        for (int i = 0; i < tf_ref.getText().length(); i++) {
            if (!Character.isDigit(tf_ref.getText().charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
        
    private boolean Controle_prixBase() {

        for (int i = 0; i < tf_prixBase.getText().length(); i++) {
            if (!Character.isDigit(tf_prixBase.getText().charAt(i))) {
                return false;
            }
        }
        return true;
    }

    
    private boolean Controle_prixVente() {

        for (int i = 0; i < tf_prixVente.getText().length(); i++) {
            if (!Character.isDigit(tf_prixVente.getText().charAt(i))) {
                return false;
            }
        }
        return true;

    }

    
    private boolean Controle_quantite() {

        for (int i = 0; i < tf_quantite.getText().length(); i++) {
            if (!Character.isDigit(tf_quantite.getText().charAt(i))) {
                return false;
            }
        }

        return true;
    }
    
    
    
    
        private Boolean controle_AntiNull(){
        
                if(tf_nom.getText()!=null
                && tf_prixBase.getText()!=null
                && tf_prixVente.getText()!=null
                && comboCat.getSelectedItem()!=null
                && tf_mat.getText()!=null
                &&tf_ref.getText()!=null
                && fileName !=null){
            
            
            return true;
        }
    
                return false;
        }
    
    //---------------------------------------------------------------------
    
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
