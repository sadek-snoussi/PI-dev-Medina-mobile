/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Panier;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServicePanier;
import java.io.IOException;
import java.util.ArrayList;



/**
 *
 * @author admin
 */
public class DetailProduit_Client {
    
    Form detail_form;
    
    EncodedImage encImg;
    Image img;
    ImageViewer imgV;
    
    ArrayList<Produit> produits;
    public static Produit produitPanier = new Produit();
    ProductService serv = new ProductService();


    public DetailProduit_Client(int id) throws IOException {
        
        
        
        detail_form=new Form("Details.",new BoxLayout(BoxLayout.Y_AXIS));
        

        
        Resources theme = UIManager.initFirstTheme("/theme");

         detail_form.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"), (ev)->{try {
             AllProduct Allp=new AllProduct(serv.getList());
             Allp.getF().show();
            } catch (IOException ex) {

            }
          });

                
            
        
        
        Container cont_R=new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Container cont_L=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        Container cont_btn=new Container(new BoxLayout(BoxLayout.X_AXIS));


        
        produits = serv.getList();        
       
        Produit p=new Produit();
        
        
        for(Produit item:produits){
            if(item.getIdProduit()==id){
                p=item;
                produitPanier=p;
            }  
        }
        
        
        
        
        
        //----------------------------Left Container-----------------------------------------
        
        Label nom=new Label(p.getNomProduit());
        cont_L.add(nom);
        
        
        encImg=EncodedImage.create("/giphy.gif");
              
        imgV=new ImageViewer();
        String url="http://localhost/Medina_VersionFinale_web/web/uploads/ImgProduit/"+p.getUrlImgProduit();

        img=URLImage.createToStorage(encImg,p.getUrlImgProduit(),url);
        imgV.setImage(img);        
        
        cont_L.add(imgV);
        
        
        
        //----------------------------Right Container-----------------------------------------

               
        Label ref_title=new Label("Reference Produit : "+p.getReferenceProd()+".");
        cont_R.add(ref_title);

        
        Label date_expo_title=new Label("Date d'Exposition :  --/--/---- .");
        //cont_R.add(date_expo_title);

                
        Label categ_title=new Label("Categorie : " + p.getIdCategorie().getNomCategorie()+".");
        cont_R.add(categ_title);
        
        Label matiere_title=new Label("Matiere : " +p.getMatiereProduit()+".");
        cont_R.add(matiere_title);

        Label prix_title=new Label("Prix : "+p.getPrixVenteProduit()+" DT.");
        cont_R.add(prix_title);

        
        Label quantite_title=new Label("Quantité : "+p.getQteDispoProduit()+" Pièces.");
        cont_R.add(quantite_title);

        

       
        //----------------------------Buttons Container-----------------------------------------

        
        Image cart_icon=Image.createImage("/shopping_cart.png");
        cart_icon.scaled(20,20);
        
        
        Button cart=new Button(" Add to Cart.",cart_icon);

        
        //============================cart button sofienne==============================================
        
            cart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                int idUserConnecte=User.user.getId();
                ServicePanier srpanier = new ServicePanier();
                ArrayList<Panier> listPanierTotale = new ArrayList<Panier>();
                listPanierTotale=srpanier.getListP();
                ArrayList<Panier> listPanierFlagZero = new ArrayList<Panier>();
                for(Panier panier : listPanierTotale){
                    if(panier.getUserId().getId()==idUserConnecte && panier.getFlag()==0){
                        listPanierFlagZero.add(panier);
                    }
                }
                
                try{
                    Panier panier = new Panier();
                    
                    //ServicePanier srpanier2 = new ServicePanier();
                    System.out.println("le produit static est "+produitPanier.getIdProduit());
                    panier=srpanier.findProduitInPanier(idUserConnecte, produitPanier.getIdProduit());
                    System.out.println("before null pointer "+panier.getProduitId().getIdProduit());
                    int x =panier.getQuantiteProduit()+1;
                    panier.setQuantiteProduit(x);
                    srpanier.updateQuantiteProduit(panier);
                    Dialog.show("Quantite", "Quantite modifiée avec succes", "OK", null); 
                    }catch(NullPointerException ex){
                    User userPanier = new User();
                    userPanier.setId(idUserConnecte);
                    Produit produitPaniers = new Produit();
                    produitPaniers.setIdProduit(produitPanier.getIdProduit());
                    Panier ajoutPanier = new Panier();
                    ajoutPanier.setFlag(0);
                    ajoutPanier.setProduitId(produitPaniers);
                    ajoutPanier.setUserId(userPanier);
                    ajoutPanier.setQuantiteProduit(1);
                    srpanier.ajouterPanier(ajoutPanier);
                    Dialog.show("Produit", "Produit ajouté avec succes", "OK", null); 
                    
                }
                
                
            }


        });
        

         //=============================================================================================
         
         
         
         
        Button wishList=new Button("Add to WishList.");
        
        cont_btn.add(cart);
        //cont_btn.add(wishList);

        
         //----------------------------Form Details-----------------------------------------

         
         
         
        detail_form.add(cont_L);
        detail_form.add(cont_R);
        detail_form.add(cont_btn);
       
        detail_form.show();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //--------------------------------------------------------------------------

    public Form getDetail_form() {
        return detail_form;
    }

    public void setDetail_form(Form detail_form) {
        this.detail_form = detail_form;
    }


    
}
