/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
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
public class BilanTotal {
    
        
        Form f;
        
        Double somme=0.0;
        ArrayList<Produit> produits;
        
        
    public BilanTotal() {
        
        f = new Form("Total des Gains.");     
     
        Resources theme = UIManager.initFirstTheme("/theme");
        
             f.getToolbar().addCommandToLeftBar("",theme.getImage("back-command.png"), (ev)->{try {
             MonStock ms=new MonStock();
             ms.getF().show();
            } catch (IOException ex) {

            }
          });

        Container global_Sup=new Container(new GridLayout(3, 2));
        Container global_Inf=new Container(new GridLayout(1, 2));


        f.add(global_Sup);
        f.add(global_Inf);
        

        
        ProductService serv=new ProductService();
        produits=serv.getList();
        
        for( Produit item:produits){
            if(item.getIdUser().getId()==User.user.getId()){
                
                
        Label ref=new Label(item.getReferenceProd()+"    - ");
        Label nomProd=new Label(item.getNomProduit()+".");
        
        Label gain=new Label("Gain Produit "); 
        Label gainProd=new Label(":  "+
                (item.getPrixVenteProduit()-item.getPrixBaseProduit())*item.getQteVenduProduit()
                +" DT.");                
        
        
        
        global_Sup.add(ref);
        global_Sup.add(nomProd);
        global_Sup.add(gain);
        global_Sup.add(gainProd);
        global_Sup.add(new Label(" "));
        Slider sld=new Slider();
        sld.setPreferredSize(new Dimension(256, 2));
        global_Sup.add(sld);
        
                
        somme=somme+((item.getPrixVenteProduit()-item.getPrixBaseProduit())*item.getQteVenduProduit());       

                
            }
        }
        
        
        
        Label total=new Label("Total des Gains ");
        Label totalProd=new Label(":  "+somme+" DT.");
        
        global_Inf.add(total);
        global_Inf.add(totalProd);
        
        

        
        
        
        
        
//        f.getToolbar().addCommandToLeftBar("", theme.getImage("cal_left_arrow.png"), (ev)->{try {
//            MonStock ms=new MonStock();
//            ms.getF().show();
//            } catch (IOException ex) {
//            }
//          });
//        
        
        
    }
    
    
    
    

    
    
  
            //---------------------------------------------------------------------
    
    
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    

    
}
