/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
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
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Event;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceEvent;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author ASUS I7
 */
public class AffichageEvent {

    Form f;
    Form f2;
    Form f3;

    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    
    Image videImg;


    public static Event rec = new Event();
    private Resources theme;

    public AffichageEvent() {
        
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv=new ProductService();
        
        
        
        theme = UIManager.initFirstTheme("/theme");
        f = new Form("Evenements", BoxLayout.y());
        f2 = new Form("Detail Evenement", BoxLayout.y());
        ServiceEvent serviceTask = new ServiceEvent();
        ArrayList<Event> lis = serviceTask.getListEvent();

        for (Event t : lis) {

            try {
                Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Container C3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                C3.setWidth(100);
                Container C4 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/img/" + t.getUrlafficheevent();
                System.out.println(url);
                Button detail = new Button("Detail");

                System.out.println(imgs);

                enc = EncodedImage.create("/giphy.gif");
                imgs = URLImage.createToStorage(enc, t.getUrlafficheevent(), url, URLImage.RESIZE_SCALE);
                
                imgs = imgs.scaled(350, 350);
                imgv = new ImageViewer();
                imgv.setImage(imgs);

                // Label l1 = new Label("id : " + t.getIdEvent());
                SpanLabel nom = new SpanLabel(t.getNomEvent());

                SpanLabel l2 = new SpanLabel(t.getDescriptionevent());
                SpanLabel l3 = new SpanLabel(t.getLieux());
                SpanLabel nbp = new SpanLabel(String.valueOf(t.getNbrePlace()));
                SpanLabel nbs = new SpanLabel(String.valueOf(t.getNbreStand()));
                ////////Affichage Date/////////////////

                SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat Heure = new SimpleDateFormat("HH:mm");

                String d = Date.format(t.getDateEvent());
                String h = Heure.format(t.getDateEvent());
                Label date = new Label("" + d + " à " + h);
                ///////////////////////////////////////

                rec.setIdEvent(t.getIdEvent());

                nbp.setVisible(false);
                nbs.setVisible(false);

                C1.add(imgv);
                C2.add(nom);
                C2.add(date);

                C2.add(l2);
                C2.add(l3);

                C1.setLeadComponent(l2);
                C4.add(detail);
                //C3.add(insc);

                f.add(C1);

                f.add(C2);
                f.add(C3);
                f.add(new Slider());

                C2.setLeadComponent(detail);
                //C3.setLeadComponent(insc);

                detail.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        rec = t; 
                        Form f2 = new detailEvent().getF();
                        Toolbar tb_detail = f2.getToolbar();
                        //tb_detail.addMaterialCommandToLeftBar(" ", theme, e -> {

                            //f.showBack();

                      //  });
                        f2.show();
                    }

                });
                /* insc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        rec = t;
                        //Form f3 = new Inscription().getF3();
                        Inscription inscri = new Inscription();
                        Inscription.idevent = rec.getIdEvent();
                        inscri.getF3().show();
                        Toolbar tb = inscri.getF3().getToolbar();

                        tb.addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_ARROW_BACK, e
                                -> {

                            f.showBack();

                        });

                        inscri.getF3().show();
                    }
                });*/

            } catch (IOException ex) {

            }
        }
        Toolbar tb = f.getToolbar();
        Toolbar.setGlobalToolbar(true);
        
            f.getToolbar().addCommandToOverflowMenu("Reserver Stand ",videImg, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                    AffichageEventReservation aff_reserv =new AffichageEventReservation();
                    aff_reserv.getF().show();
                
            }
        });
        
        
        
        

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

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
