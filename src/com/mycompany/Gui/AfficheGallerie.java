/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceGallerie;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;
import pidev.edu.souk.entities.Gallerie;

/**
 *
 * @author khali
 */
public class AfficheGallerie {

    Form b;
    Form FRecherche;
    SpanLabel lb;
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    Container xx;

    public static Gallerie Gals = new Gallerie();

    public AfficheGallerie() {
        
        
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv=new ProductService();
        
        
        
        
        
        
        
        
        
        
        
        
        b = new Form("Gallerie", new BoxLayout(BoxLayout.Y_AXIS));
        xx = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        ServiceGallerie servicegallerie = new ServiceGallerie();
        ArrayList<Gallerie> list = new ArrayList<Gallerie>();
        list = servicegallerie.getListB();

        //////////////////////////////Recherche Tag///////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        TextField TFR = new TextField("", "Recherche dans gallerie");

        b.add(TFR);
        
        

        TFR.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
                xx.removeAll();
                ArrayList<Gallerie> list = new ArrayList<Gallerie>();
                       if (TFR.getText().equals("")) {
                    list = servicegallerie.getListB();
                } else {
                    list = servicegallerie.getListDescriptionByTag(TFR.getText());
                }
         
                for (Gallerie gallerie : list) {
                    try {
                        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                        Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                        String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgGallerie/" + gallerie.getImgGallerie();
                        System.out.println(url);

                        enc = EncodedImage.create("/giphy.gif");
                        imgs = URLImage.createToStorage(enc, gallerie.getImgGallerie(), url, URLImage.RESIZE_SCALE);
                        imgs = imgs.scaled(100, 100);

                        imgv = new ImageViewer();
                        imgv.setImage(imgs);
                        // Label l1 = new Label("id : " + t.getIdEvent());
                        SpanLabel TitreGal = new SpanLabel(gallerie.getTitreGallerie());
                        SpanLabel TypeGal = new SpanLabel("Type : " + gallerie.getTypeGallerie());

                        Button txt = new Button();
                        txt.setVisible(false);

                        txt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                Gals = gallerie;
                                DetailGallerie b3 = new DetailGallerie();
                                b3.getF().show();
                            }
                        });

                        C1.add(imgv);
                        C2.add(TitreGal);
                        C2.add(TypeGal);
                        C2.add(txt);

                        C2.setLeadComponent(txt);

                        C3.add(C1);
                        C3.add(C2);

                        Slider SSS = new Slider();
                        SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                       
                        xx.add(C3);
                        xx.add(SSS);
                        

                    } catch (IOException ex) {

                    }
                }
            }

        });

        for (Gallerie gallerie : list) {
            try {
                Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgGallerie/" + gallerie.getImgGallerie();
                System.out.println(url);

                enc = EncodedImage.create("/giphy.gif");
                imgs = URLImage.createToStorage(enc, gallerie.getImgGallerie(), url, URLImage.RESIZE_SCALE);
                imgs = imgs.scaled(100, 100);

                imgv = new ImageViewer(imgs);
                // Label l1 = new Label("id : " + t.getIdEvent());
                SpanLabel nomGal = new SpanLabel(gallerie.getTitreGallerie());
                SpanLabel TypeGal = new SpanLabel("Type : " + gallerie.getTypeGallerie());

                Button txt = new Button();
                txt.setVisible(false);

                txt.addActionListener((ActionListener) (ActionEvent evt) -> {
                    Gals = gallerie;
                    DetailGallerie b3 = new DetailGallerie();
                    b3.getF().show();
                });

                C1.add(imgv);
                C2.add(nomGal);
                C2.add(TypeGal);
                C2.add(txt);

                C2.setLeadComponent(txt);

                C3.add(C1);
                C3.add(C2);

                Slider SSS = new Slider();
                SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                xx.add(C3);
                xx.add(SSS);

            } catch (IOException ex) {
            }

        }

        b.add(xx);

        Toolbar tb = b.getToolbar();

                           //--------------------------------ToolBar--------------------------------------
        //com.codename1.ui.Toolbar tb = f.getToolbar();

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

        tb.addCommandToOverflowMenu("Bons Plans ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheBonplan affBonP=new AfficheBonplan();
                affBonP.getF().show();
            }
        });        
        
        tb.addCommandToOverflowMenu("Guide ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AfficheGuide affGui=new AfficheGuide();
                affGui.getF().show();            }
        });


        tb.addCommandToOverflowMenu("Recherche ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                GallerieRecherche GR = new GallerieRecherche();
                GR.getF().show();

            }
        });

    }

    public Form getF() {
        return b;
    }

    public void setF(Form b) {
        this.b = b;
    }

}
