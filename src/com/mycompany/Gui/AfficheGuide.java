/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceGuide;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;
import pidev.edu.souk.entities.Guide;

/**
 *
 * @author khali
 */
public class AfficheGuide {

    Form b;
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    ComboBox CB;

    public static Guide Guids = new Guide();

    public AfficheGuide() {
        
        
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv=new ProductService();
        
        
        
        
        
        
        
        
        
        
        b = new Form("Guide", new BoxLayout(BoxLayout.Y_AXIS));

        ServiceGuide serviceguide = new ServiceGuide();
        ArrayList<Guide> list = new ArrayList<Guide>();
        list = serviceguide.getListB();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////// Combo Box Gouvernerat //////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ComboBox combo = new ComboBox<>("Tunis", "Ben Arous", "Ariana", "Manouba", "Beja", "Kef", "Jandouba", "Sfax", "Sousse", "Gabes", "Nabeul", "Monastir", "Kairaoun", "Gafsa", "Kasserine", "Kebili", "Médenine", "Mahdia", "Sidi Bouzid", "Tataouine", "Zaghouan", "Bizerte", "Tozeur");
        combo.setRenderer(new GenericListCellRenderer<>(new MultiButton("a"), new MultiButton("")));
        b.add(combo);
        b.add(new Slider());
        Container xx = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                b.removeAll();
                xx.removeAll();
                b.add(combo);
                ArrayList<Guide> list = new ArrayList<Guide>();

                list = serviceguide.getListByGouv((String) combo.getSelectedItem());
                 if(list.isEmpty())
                {
                    
                    Container EmptyContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
                    
                    Label Empty = new Label("pas de guide trouvée à " + ((String) combo.getSelectedItem()) );
                    EmptyContainer.add(Empty);
                    b.add(EmptyContainer);
                }

                for (Guide guide : list) {
                    try {
                        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                        Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));

                        String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgGuide/" + guide.getImgGuide();
                        System.out.println(url);

                        enc = EncodedImage.create("/giphy.gif");
                        imgs = URLImage.createToStorage(enc, guide.getImgGuide(), url, URLImage.RESIZE_SCALE);
                        imgs = imgs.scaled(100, 100);

                        imgv = new ImageViewer();
                        imgv.setImage(imgs);

                        SpanLabel PrenomGuide = new SpanLabel(guide.getPrenomGuide());
                        SpanLabel GouvGuide = new SpanLabel("Gouvernerat : " + guide.getVilleGuide());

                        Button txt = new Button();
                        txt.setVisible(false);

                        txt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                Guids = guide;
                                DetailGuide b3 = new DetailGuide();
                                b3.getF().show();
                            }
                        });

                        C1.add(imgv);
                        C2.add(PrenomGuide);
                        C2.add(GouvGuide);
                        C2.add(txt);

                        C2.setLeadComponent(txt);

                        C3.add(C1);
                        C3.add(C2);

                        Slider SSS = new Slider();
                        SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                        b.add(C3);
                        b.add(SSS);

                    } catch (IOException ex) {
                    }
                }
            }
        });

        for (Guide guide : list) {
            try {
                Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));

                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgGuide/" + guide.getImgGuide();
                System.out.println(url);

                enc = EncodedImage.create("/giphy.gif");
                imgs = URLImage.createToStorage(enc, guide.getImgGuide(), url, URLImage.RESIZE_SCALE);
                imgs = imgs.scaled(100, 100);

                imgv = new ImageViewer();
                imgv.setImage(imgs);

                SpanLabel PrenomGuide = new SpanLabel(guide.getPrenomGuide());
                SpanLabel GouvGuide = new SpanLabel("Gouvernerat : " + guide.getVilleGuide());

                Button txt = new Button();
                txt.setVisible(false);

                txt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        Guids = guide;
                        DetailGuide b3 = new DetailGuide();
                        b3.getF().show();
                    }
                });

                C1.add(imgv);
                C2.add(PrenomGuide);
                C2.add(GouvGuide);
                C2.add(txt);

                C2.setLeadComponent(txt);

                C3.add(C1);
                C3.add(C2);

                Slider SSS = new Slider();
                SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                b.add(C3);
                b.add(SSS);

            } catch (IOException ex) {
            }
        }

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
        
        tb.addCommandToOverflowMenu("Gallerie ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                 AfficheGallerie affGal=new AfficheGallerie();
                affGal.getF().show();               
                
                
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
