    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;
import com.mycompany.Entity.Categorie;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.CategoryService;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;



/**
 *
 * @author admin
 */
public class AllProduct {

    Form f;

    EncodedImage encImg;
    Image img;
    ImageViewer imgV;

    Container global;
    ArrayList<Produit> produits;
    ProductService serv = new ProductService();

    public AllProduct(ArrayList<Produit> prods) throws IOException {

        //signup signup = new signup();
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();

        Toolbar.setGlobalToolbar(true);

        f = new Form("Nos Produits.", new BoxLayout(BoxLayout.Y_AXIS));
        
        if(User.user.getTypeUser().equals("pro")){
            
            
            f.getToolbar().addCommandToOverflowMenu("Mes Produits", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    MesProduits mp=new MesProduits();
                    mp.getF().show();
                } catch (IOException ex) {


                }
            }
        });  
            
            f.getToolbar().addCommandToOverflowMenu("Mon Stock", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    MonStock ms=new MonStock();
                    ms.getF().show();
                } catch (IOException ex) {


                }
            }
        }); 
        }
        //-----------------------------RECHERCHES-----------------------------------------    
        //f.add(slider);
        Image rech_icon = Image.createImage("/rech_icon.png");
        rech_icon.scaled(20, 20);

        //============================================================================================
        //===================================RECHERCHE GLOBALE========================================
        TextField tf_recherche = new TextField("", "Rechercher..");

        Button recherche = new Button(rech_icon);

        //LIKE
        recherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    ProductService prodServ = new ProductService();
                    System.out.println("--------------------12------------------------");
                    System.out.println(prodServ.getListRecherches(tf_recherche.getText()));
                    System.out.println("---------------------12-----------------------");

                    produits = prodServ.getListRecherches(tf_recherche.getText());

                    AllProduct ap = new AllProduct(produits);
                    ap.getF().show();

                    System.out.println("*******************************************");
                    System.out.println(produits);
                    System.out.println("*******************************************");

                } catch (IOException ex) {
                }

            }
        });

        Container rechCont = new Container(new TableLayout(1, 2));

        rechCont.add(recherche);
        rechCont.add(tf_recherche);

        f.add(rechCont);

        //============================================================================================
        CategoryService Catserv = new CategoryService();

        ComboBox<String> comboCat = new ComboBox<String>();
        for (Categorie item : Catserv.getList()) {
            comboCat.addItem(item.getNomCategorie());
        }

        f.add(comboCat);

        //===================================RECHERCHE PAR CATEGORIE========================================
        comboCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    System.out.println("--------------------------------------------");
                    System.out.println(serv.getListParCategorie(comboCat.getSelectedItem()));
                    System.out.println("--------------------------------------------");

                    produits = serv.getListParCategorie(comboCat.getSelectedItem());

                    AllProduct ap = new AllProduct(produits);
                    ap.getF().show();

                    System.out.println("*******************************************");
                    System.out.println(produits);
                    System.out.println("*******************************************");

                } catch (IOException ex) {

                }

            }
        });

        //============================================================================================        
        TextField tf_prixMin = new TextField("", "Prix min");
        TextField tf_PrixMax = new TextField("", "Prix Max");

        Button rechPrix_btn = new Button(rech_icon);

        Container rechPrix = new Container(new GridLayout(1, 3));

        rechPrix.add(tf_prixMin);
        rechPrix.add(tf_PrixMax);
        rechPrix.add(rechPrix_btn);
        f.add(rechPrix);

        //===================================RECHERCHE PAR PRIX==========================================
        rechPrix_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    System.out.println("--------------------------------------------");
                    System.out.println(serv.getListParPrix(
                            Double.parseDouble(tf_prixMin.getText()),
                            Double.parseDouble(tf_PrixMax.getText())
                    ));
                    System.out.println("--------------------------------------------");

                    produits = serv.getListParPrix(
                            Double.parseDouble(tf_prixMin.getText()),
                            Double.parseDouble(tf_PrixMax.getText())
                    );

                    AllProduct ap = new AllProduct(produits);
                    ap.getF().show();

                    System.out.println("*******************************************");
                    System.out.println(produits);
                    System.out.println("*******************************************");

                } catch (IOException ex) {

                }

            }
        });

        //===================================================================================================
        Slider slider = new Slider();
        slider.setPreferredSize(new Dimension(256, 2));
        f.add(slider);
        
        //--------------------------------ToolBar--------------------------------------
        //--------------------------------ToolBar--------------------------------------
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

        //-------------------------------Recherge de listes ------------------------------
        this.listaa(prods);

    }

    //------------------------------------------------------------------------------
    //-----------------------------------FUNCTIONS-----------------------------------
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public void listaa(ArrayList<Produit> produits) throws IOException {

        for (Produit item : produits) {
            if (item.getValiditeProduit() == 2) {

                global = new Container(new BoxLayout(BoxLayout.X_AXIS));

                Container Right = new Container(new FlowLayout(Component.LEFT));
                Container Left = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                global.add(Right);
                global.add(Left);

                f.add(global);
                Slider sld = new Slider();
                sld.setPreferredSize(new Dimension(256, 2));
                f.add(sld);

                Label lab_nom = new Label(item.getNomProduit() + ".");
                Label lab_categorie = new Label(item.getIdCategorie().getNomCategorie() + ".");
                Label lab_prix = new Label(String.valueOf(item.getPrixVenteProduit()) + " DT.");

                Button detail_btn = new Button("Details.");

                detail_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        try {
                            DetailProduit_Client detail_prod;
                            detail_prod = new DetailProduit_Client(item.getIdProduit());
                        } catch (IOException ex) {
                        }

                    }
                });

                encImg = EncodedImage.create("/giphy.gif");
                imgV = new ImageViewer();
                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/ImgProduit/" + item.getUrlImgProduit();

                img = URLImage.createToStorage(encImg, item.getUrlImgProduit(), url);
                img = img.scaled(125, 100);
                imgV.setImage(img);

                Right.add(imgV);

                Left.add(lab_nom);
                Left.add(lab_categorie);
                Left.add(lab_prix);
                Left.add(detail_btn);

            }
        }

    }

}
