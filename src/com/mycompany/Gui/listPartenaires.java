/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.PartenaireService;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author amalb
 */
public class listPartenaires {

    Form listForm;
    PartenaireService ps = new PartenaireService();
    ArrayList<User> partenaires;
    private Resources theme;

    public listPartenaires() {
        
        //listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv=new ProductService();
        
      
        partenaires = ps.listPartenaires();
        listForm = new Form("liste", new FlowLayout(Component.CENTER));

        int i = 0;
        for (User u : partenaires) {
            try {
                //System.out.println(theme.toString());
                Container contImage = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                Container contenu = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                Container Big = new Container(new FlowLayout(Component.CENTER));
                //ImageViewer imageView = new ImageViewer();
                Label nomE = new Label();
                Label email = new Label();
                Label spec = new Label();
               
                Label lblnomE = new Label("Nom de l'entreprise :");
                Label lblemail = new Label("Email de l'entreprise :");
                Label lblspec = new Label("Spécialité :");

                EncodedImage encodimage = EncodedImage.create("/giphy.gif");
                ImageViewer imgViewer = new ImageViewer(encodimage);

                
                System.out.println("*******"+u.getUrlLogoPro()+"***********");
                Image logo = URLImage.createToStorage(encodimage, u.getUrlLogoPro(),
                        "http://localhost//Medina_VersionFinale_web//web//uploads//logo//"
                        + u.getUrlLogoPro(), URLImage.RESIZE_SCALE);
                logo = logo.scaled(100, 100);
                //ImageViewer imgViewer=new ImageViewer(image);
                imgViewer.setImage(logo);
                //i++;

                Container c_nomE =new Container(new BoxLayout(BoxLayout.X_AXIS));
                Container c_email =new Container(new BoxLayout(BoxLayout.X_AXIS));
                Container c_spec =new Container(new BoxLayout(BoxLayout.X_AXIS));
              
                nomE.setText(u.getNomEntreprisePro());
                email.setText(u.getEmail());
                spec.setText(u.getSpecialitePart());
                
                c_nomE.add(lblnomE);
                c_nomE.add(nomE);
                
                c_email.add(lblemail);
                c_email.add(email);
                
                c_spec.add(lblspec);
                c_spec.add(spec);
                
                
                contenu.add(c_nomE);
                contenu.add(c_email);
                contenu.add(c_spec);
               
                contImage.add(imgViewer);
                Big.add(contImage);
                Big.add(contenu);
                /* container.add(id);
                container.add(desc);*/

                listForm.add(Big);
                listForm.add(new Slider());
                
            } catch (IOException ex) {

            }

        }
//		
//		  ImageViewer img = new ImageViewer();
//        img.setImage(theme.getImage("back-command.png"));

      

        
        //--------------------------------ToolBar--------------------------------------
        //--------------------------------ToolBar--------------------------------------
        //--------------------------------ToolBar--------------------------------------
        com.codename1.ui.Toolbar tb = listForm.getToolbar();

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
                    ProductService pServ=new ProductService();
                    AllProduct ap=new AllProduct(pServ.getList());
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

                listPartenaires lp = new listPartenaires();
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

    public Form getListForm() {
        return listForm;
    }

    public void setListForm(Form listForm) {
        this.listForm = listForm;
    }

}
