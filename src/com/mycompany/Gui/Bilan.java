/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import com.mycompany.Service.ProductService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Bilan {
    
        Form f;
        
        
        ArrayList<Produit> produits;
        
        
    public Bilan() {
        
        
        Resources theme = UIManager.initFirstTheme("/theme");
        
        f = new Form("Gains Par Produit.");
        
            f.getToolbar().addCommandToLeftBar("",theme.getImage("back-command.png"), (ev)->{try {
             
             MonStock ms=new MonStock();
             ms.getF().show();
            } catch (IOException ex) {

            }
          });
        
        TextField tf_recherche=new TextField("", "Rechercher ..");

     
     
        Container global=new Container(new GridLayout(6, 2));
        

        f.add(tf_recherche);
        f.add(global);

        
        ProductService serv=new ProductService();
        produits=serv.getList();
        
        for( Produit item:produits){
            if(item.getIdUser().getId()== User.user.getId()){
                
                
        Label nom=new Label("Nom    ");
        Label nomProd=new Label(":  "+item.getNomProduit()+".");
        
        Label prix_base=new Label("Prix de Base "); 
        Label prix_baseProd=new Label(":  "+item.getPrixBaseProduit()+" DT.");
        
        Label prix_vente=new Label("Prix de Vente");        
        Label prix_venteProd=new Label(":  "+item.getPrixVenteProduit()+" DT.");
        
        Label quantity=new Label("Quantité Vendue");
        Label quantityProd=new Label(":  "+item.getQteVenduProduit()+" Piéce(s).");        
                
        SpanLabel gain=new SpanLabel("Gain Potentiel");
        Label gainProd=new Label(":  "+
                (item.getPrixVenteProduit()-item.getPrixBaseProduit())*item.getQteVenduProduit()
                + " DT.");        
                
                
                
        global.add(nom);
        global.add(nomProd);
        
        global.add(prix_base);
        global.add(prix_baseProd);
        
        global.add(prix_vente);
        global.add(prix_venteProd);
         
        global.add(quantity);
        global.add(quantityProd);

        global.add(gain);
        global.add(gainProd);
        
        Slider slider1=new Slider();
        slider1.setPreferredSize(new Dimension(256, 2));
        
        Slider slider2=new Slider();
        slider2.setPreferredSize(new Dimension(256, 2));
        
        global.add(slider1);
        global.add(slider2);

        
                
                
                
                
            }

        }
        
//        f.getToolbar().addCommandToLeftBar("", theme.getImage("cal_left_arrow.png"), (ev)->{try {
//            MonStock ms=new MonStock();
//            ms.getF().show();
//            } catch (IOException ex) {
//            }
//          });        
        
        
        
          
    }

    
        //---------------------------------------------------------------------
    
    
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
