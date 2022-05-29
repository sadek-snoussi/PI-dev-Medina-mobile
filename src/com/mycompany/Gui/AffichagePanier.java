/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Panier;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServicePanier;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sofienne
 */
public class AffichagePanier {

    Form f;
    private Resources theme;
    SpanLabel lb;
    private EncodedImage image;
    private Image img;

    public AffichagePanier() {
        
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv=new ProductService();
        
        
        
        theme = UIManager.initFirstTheme("/theme");
        f = new Form("Mon Panier",new BoxLayout(BoxLayout.Y_AXIS));
        Container cont_R=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont_L=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont_btn=new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        f.getToolbar().addCommandToOverflowMenu("Commander", theme.getImage("back-command.png"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AffichageCommande afficherCommande = new AffichageCommande();
                afficherCommande.getF().show();
            }
        });
        
        
      //--------------------------------ToolBar--------------------------------------
      
      
      com.codename1.ui.Toolbar tb = f.getToolbar();

        tb.addMaterialCommandToSideMenu("   Souk El-Medina", FontImage.MATERIAL_SHOPPING_BASKET,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        
        
            tb.addMaterialCommandToSideMenu("Mon Profile", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                

                if(User.user.getTypeUser().equals("pro"))
                {
                profilePro.getModifProfilePro().show();

                    System.out.println("prooooooooooooooooooo");
                }
                else if(User.user.getTypeUser().equals("freelancer"))
                {
                    profileFree.getModifProfileFree().show();
                     System.out.println("freeeeeeeeeeeee");
                }
                else{
                   profile.getModifProfileClient().show();
                }
            }
        });
        

        tb.addMaterialCommandToSideMenu("Produits", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                try {
                    AllProduct ap=new AllProduct(serv.getList());
                    ap.getF().show();
                } catch (IOException ex) {
                }

            }
        });
  
        tb.addMaterialCommandToSideMenu("Mon Panier.", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichagePanier affichePanier = new AffichagePanier();
                affichePanier.getF().show();

            }
        });

        tb.addMaterialCommandToSideMenu("Nos Partenaires", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                lp.getListForm().show();

            }
        });
        
        tb.addMaterialCommandToSideMenu("Actualités", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichageEvent ev = new AffichageEvent();
                ev.getF().show();

            }
        });

        tb.addMaterialCommandToSideMenu("Découvertes ", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheBonplan afb = new AfficheBonplan();
                afb.getF().show();

            }
        });


        tb.addMaterialCommandToSideMenu("Videos DIY", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
            new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                ValidVideoListClient validVideoListClient;
                ArrayList<Videodiy> videolist;
                ServiceVideo serviceVideo = new ServiceVideo();
                videolist = serviceVideo.getVideosList();
                System.err.println("btnAfficheVideo listener triggered => will callconstructor");
                validVideoListClient = new ValidVideoListClient(videolist);
                validVideoListClient.getF().show();

            }
        });

        tb.addMaterialCommandToSideMenu("Se déconnecter", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, 
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                signin.getLogin().show();

            }
        });
    
    // ////////////////////////////////////////////// end Toolbar ///////////////////////////////////////////
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        f.getToolbar().addCommandToOverflowMenu("Home", theme.getImage("back-command.png"), new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                try {
//                    ProductService serv=new ProductService();
//                    AllProduct h = new AllProduct(serv.getList());
//                    h.getF().show();
//                } catch (IOException ex) {
//                    ex.getMessage();
//                }
//            }
//        });

        lb = new SpanLabel("");
        f.add(lb);
        ServicePanier servicePanier = new ServicePanier();
        ArrayList<Panier> listPanier = new ArrayList<Panier>();
        listPanier = servicePanier.getListP();
        ArrayList<Panier> listPanierfinale = new ArrayList<Panier>();
        
        
        
        int idUserConnecte = User.user.getId();
        
        
        
        for (Panier p : listPanier) {
            if (p.getUserId().getId() == idUserConnecte) {
                listPanierfinale.add(p);
            }
        }
        for (Panier pn : listPanierfinale) {
            try {
                Container c = new Container();
                Label lbNomProduit = new Label();
                Label lbPrixProduit = new Label();
                TextField txtQuantiteProduit = new TextField(TextArea.NUMERIC);
                Button btnSupprimer = new Button("Delete");
                image = EncodedImage.create("/giphy.gif");
                System.out.println("l url de l'image est " + pn.getProduitId().getUrlImgProduit());
                img = URLImage.createToStorage(image, pn.getProduitId().getUrlImgProduit(), "http://localhost//Medina//web//uploads//ImgProduit//" + pn.getProduitId().getUrlImgProduit());
                ImageViewer imgViewer = new ImageViewer(img);
                lbNomProduit.setText(pn.getProduitId().getNomProduit());
                lbPrixProduit.setText(String.valueOf(pn.getProduitId().getPrixVenteProduit()) + " DTN");
                txtQuantiteProduit.setText(String.valueOf(pn.getQuantiteProduit()));

                // ******************************************************************************************************************************
                //*********************** MODIFICATION DE LA QUANTITE DE PRODUIT 
                txtQuantiteProduit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Panier panier = new Panier();
                        panier.setId(pn.getId());
                        panier.setQuantiteProduit(Integer.valueOf(txtQuantiteProduit.getText()));
                        servicePanier.updateQuantiteProduit(panier);
                        Dialog.show("Quantite", "Quantite modifiée avec succes", "OK", null);                   
                        System.out.println("modifié");
                    }
                });

                // ******************************************************************************************************************************
                //*********************** SUPPRESSION DU PANIER COURANT
                btnSupprimer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("l'id du panier est " + pn.getId());
                        servicePanier.supprimerDuPanier(pn.getId());
                        System.out.println("delete avec succes ");
                        Dialog.show("Suppression", "Produit supprimer du panier avec Succes", "OK", null);
                        AffichagePanier aff = new AffichagePanier();
                        aff.getF().show();
                        //f.refreshTheme();

                    }
                });

                // ******************************************************************************************************************************
                //*********************** ********************************************************************
                f.add(imgViewer);
                f.add(lbNomProduit);
                f.add(lbPrixProduit);
                f.add(txtQuantiteProduit);
                f.add(btnSupprimer);
                f.add(new Slider());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        // lb.setText(listPanierfinale.toString());
        // lb.setText(servicePanier.getListP().toString());
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
