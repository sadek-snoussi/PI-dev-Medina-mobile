/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
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
public class MonStock {
    
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv = new ProductService();
        
    Form f;
    
    Image videImg;
    ArrayList<Produit> produits;

    
    public MonStock() throws IOException {
        
        f=new Form("Mon Stock.",new FlowLayout(Component.CENTER,Component.TOP));
        
        f.getToolbar().addCommandToOverflowMenu("Bilan par Produit",videImg, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Bilan bilan=new Bilan();
                bilan.getF().show();
            }
        });
        
        
            f.getToolbar().addCommandToOverflowMenu("Bilan Total",videImg, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                BilanTotal bt=new BilanTotal();
                bt.getF().show();
                
            }
        });
        
     
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

        TextField tf_recherche=new TextField("", "Rechercher..");
                
        //f.add(tf_recherche);
        
        
        //ProductService Prodserv = new ProductService();
        produits = serv.getList();        
        
        
        for(Produit item:produits){
            if(item.getIdUser().getId()==User.user.getId()){
        
        Container refContainer=new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container prixBase=new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container prixVente=new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container quantite=new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        

        
        Label lab_ref=new Label(item.getReferenceProd()+" - ");        
        Label lab_nom=new Label(item.getNomProduit()+".");
        

        refContainer.add(lab_ref);
        refContainer.add(lab_nom);
        
        Label p_base=new Label("Prix de Base  :");
        TextField tf_pBase=new TextField();
        tf_pBase.setText(String.valueOf(item.getPrixBaseProduit()));
        tf_pBase.isEditable();
        
        prixBase.add(p_base);
        prixBase.add(tf_pBase);

                
        
        
                                
        Label p_vente=new Label("Prix de Vente :");
        TextField tf_pVente=new TextField();
        tf_pVente.setText(String.valueOf(item.getPrixVenteProduit()));
        tf_pVente.isEditable();



        prixVente.add(p_vente);
        prixVente.add(tf_pVente);        
             

        
        
        Label quantity=new Label("Quantité      :   ");
        TextField tf_quantity=new TextField();
        tf_quantity.setText(item.getQteDispoProduit().toString());
        tf_quantity.isEditable();



        quantite.add(quantity);
        quantite.add(tf_quantity);                
               
        
        Image Edit_icon=Image.createImage("/Edit_icon.png");
        Edit_icon.scaled(20, 20);          
        Button ok_btn=new Button("Edit",Edit_icon);
        
        
        
      //=================================================================================
        
        ok_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                try {
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/editStock/"
                            +item.getIdProduit()
                            +"/"+tf_pBase.getText()
                            +"/"+tf_pVente.getText()
                            +"/"+tf_quantity.getText()
                    );
                    System.out.println("************************");
                    System.out.println("" );
                    System.out.println("************************");
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            
                            System.out.println("Le Stock a été modifié avec succées.");
                            ToastBar.showMessage("Stock modifié avec succées.",FontImage.MATERIAL_DONE);
                            
                        }
                    });
                    
                    con.setFailSilently(true);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    MonStock stock = new MonStock();                
                    stock.getF().show();
                    
                } catch (IOException ex) {
                }
                
                
                
                
                
                
                
                
            }
        });

     //=================================================================================
     
     
        Container btnContainer=new Container(new GridLayout(1, 3));
        
        
        btnContainer.add(new Label(" "));
        btnContainer.add(new Label(" "));
        
        btnContainer.add(ok_btn);
                
        
        f.add(refContainer);
        f.add(prixBase);
        f.add(prixVente);
        f.add(quantite);
        
        //f.add(new Slider());
        f.add(btnContainer);


                
                
                
                
                
                
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
