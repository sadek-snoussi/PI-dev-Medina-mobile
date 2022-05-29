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
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;





/**
 *
 * @author admin
 */
public class MesProduits {
    
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();

    
    Form f;
    Label lab_status;

    EncodedImage encImg;
    Image img;
    ImageViewer imgV;    
    Image videImg;
    
    
    public static int idUser=User.user.getId();

    
    
    ArrayList<Produit> produits;
    ProductService serv = new ProductService();
    public MesProduits() throws IOException {
        
        f=new Form("Mes Produits",new FlowLayout(Component.CENTER, 0));
        Toolbar.setGlobalToolbar(true);

        
        
  
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
        

        
        Container rechEtAjout=new Container();

        
        
        f.getToolbar().addCommandToOverflowMenu("Add Product",videImg, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Add add=new Add();
                add.getF().show();
            }
        });
        
        TextField tf_recherche=new TextField("", "Rechercher..");
        
        rechEtAjout.add(tf_recherche);
        
        f.add(rechEtAjout);
        
        

        
        
        
        produits = serv.getList();
        
        
        
        
        for(Produit item:produits){
            if(item.getIdUser().getId()==idUser){
                
        Container global=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container Right=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container Left=new Container(new BoxLayout(BoxLayout.X_AXIS));
                
        global.add(Right);
        global.add(Left);
        f.add(global);    
        
        
        Label lab_ref=new Label("Reference :  "+item.getReferenceProd()+".");        
        Label lab_nom=new Label("Nom Produits :  "+item.getNomProduit()+".");
        Label lab_categorie=new Label("Ctagorie :  "+item.getIdCategorie().getNomCategorie()+".");
        Label lab_mat=new Label("Matiere :  "+item.getMatiereProduit()+".");
        
        if(item.getValiditeProduit()==0){
        lab_status=new Label("En Cours.");
        lab_status.getAllStyles().setFgColor(0xf1c40f,true);
        }
        if(item.getValiditeProduit()==1){
        lab_status=new Label("Non-Verifié.");
        lab_status.getAllStyles().setFgColor(0xab1919,true);
        }
        if(item.getValiditeProduit()==2){
        lab_status=new Label("Verifié.");
        lab_status.getAllStyles().setFgColor(0x2ecc71,true);
        }
        Label lab_vide=new Label("                  ");

    //-------------------------------Buttons-----------------------------------    
        Image detail_icon=Image.createImage("/detail_icon.png");
        detail_icon.scaled(20, 20);
        Button detail_btn=new Button(detail_icon);
        
        detail_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    DetailsProduit_part detail_prod;
                    detail_prod=new DetailsProduit_part(item.getIdProduit());
                } catch (IOException ex) {
                }
               
                
            }
        });
        
        
        Image Edit_icon=Image.createImage("/Edit_icon.png");
        Edit_icon.scaled(20, 20);        
        Button Edit_btn=new Button(Edit_icon);
        
        
         Dialog diag_edit=new Dialog("Attention !");
         diag_edit.setVisible(false);
         SpanLabel msg_edit=new SpanLabel("Le Produit Sera de Nouveau en cours de Vérification. Voulez-Vous Continuer ?");
         msg_edit.getAllStyles().setFgColor(0xf1c40f,true);
         diag_edit.add(msg_edit);
                
         Button oui_btn=new Button("Oui");
         
         oui_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                Edit edit=new Edit(item);
                
            }
        });
         
         
         
         
         
         Button annule_btn =new Button("Annuler");
         
         annule_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                diag_edit.dispose();
            }
        });
         
         
         
         
         diag_edit.add(oui_btn);
         diag_edit.add(annule_btn);
        
        Edit_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                //Edit edit=new Edit(item);
                diag_edit.show();


                
            }
        });
        
        
        
        
        
        
         Dialog diag_remove=new Dialog("Attention !");
         diag_remove.setVisible(false);
         SpanLabel msg_remove=new SpanLabel("Le Produit Sera Supprimer d'une manière Définitive.Etes-Vous Sûr de vouloir Continuer ?");
         msg_remove.getAllStyles().setFgColor(0xffffff,true);
         diag_remove.add(msg_remove);
        
        Button oui_remove=new Button("Oui");
         
         oui_remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                try {
                    
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/delete/"+item.getIdProduit());
                    System.out.println(item.getIdProduit());
                            
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            
                            System.out.println("Le Produit a été supprimé avec succées.");
                            ToastBar.showMessage("Produit Supprimé avec succées.",FontImage.MATERIAL_DONE);

                        }
                    });
                    
                    con.setFailSilently(true);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    


                    
                    
                    MesProduits List = new MesProduits();
                    List.getF().show();
                    
                } catch (IOException ex) {
                }
                
            }
        });
         
         diag_remove.add(oui_remove);
         
         
         Button annule_remove=new Button("Annuler");
         
         annule_remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                diag_remove.dispose();
            }
        });
         
         
         diag_remove.add(annule_remove);
         
         
         
         
         
        Image remove_icon=Image.createImage("/remove_icon.png");
        remove_icon.scaled(20, 20);
        Button remove_btn=new Button(remove_icon);
        
        remove_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                diag_remove.show();

               /* try {
                    
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/WebService_oumayma/WebService/web/app_dev.php/api/delete/"+item.getIdProduit());
                    System.out.println(item.getIdProduit());
                            
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            
                            //System.out.println("Le Produit "+item.getNomProduit()+" a été supprimé avec succées.");

                        }
                    });
                    
                    con.setFailSilently(true);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    
                    ToastBar.showMessage("Produit Supprimé avec succées.",FontImage.MATERIAL_DONE);


                    
                    
                    MesProduits List = new MesProduits();
                    List.getF().show();
                    
                } catch (IOException ex) {
                }
                */
            }
        });
        
        //----------------------------------------------------------------------

       encImg=EncodedImage.create("/giphy.gif");
       
       System.out.println(encImg.getHeight()+" "+encImg.getWidth());
       
       imgV=new ImageViewer();
       String url="http://localhost/Medina_VersionFinale_web/web/uploads/ImgProduit/"+item.getUrlImgProduit();
        
        img=URLImage.createToStorage(encImg,item.getUrlImgProduit(),url);                

        imgV.setImage(img);
        
        
        
        
        
        
        
        
        Right.add(lab_vide);
        
        Right.add(imgV);

        Right.add(lab_ref);
        Right.add(lab_nom);
        Right.add(lab_categorie);
        Right.add(lab_mat);
        Right.add(lab_status);
        
        Left.add(new Label("          "));
        Left.add(detail_btn);
        Left.add(Edit_btn);
        Left.add(remove_btn);

        
        
        
        
        
        
        
                
            }
        
        }
        
        
        
        
    }
    

    //---------------------------------------------------------------------
    
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
    
    
}
