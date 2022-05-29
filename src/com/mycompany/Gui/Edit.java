/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.FontImage;

import com.codename1.ui.Form;

import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Categorie;
import com.mycompany.Entity.Produit;
import com.mycompany.Service.CategoryService;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class Edit {
    
      Form f;
    
    
    com.codename1.io.File file;

    
    public Edit(Produit p) {

        f = new Form("Edit Product.");
        
        Resources theme = UIManager.initFirstTheme("/theme");
        f.getToolbar().addCommandToLeftBar("",theme.getImage("cal_left_arrow.png"), (ev) -> {
            try {
                
                MesProduits myp = new MesProduits();
                myp.getF().show();
            } catch (IOException ex) {

            }
        });
        
        CategoryService serv=new CategoryService();
        
        //-------------------------------Components-------------------------------------
        
        TextField tf_nom=new TextField(p.getNomProduit(), "Nom Produit");
        TextField tf_ref=new TextField(p.getReferenceProd(), "Reference Produit");
//      ComboBox Categorie
        ComboBox<String> comboCat=new ComboBox<String>();
        comboCat.setSelectedIndex(p.getIdCategorie().getIdCategorie()-1);
        for(Categorie item:serv.getList()){
        comboCat.addItem(item.getNomCategorie());
        }
//      ComboBox Categorie
        TextField tf_mat=new TextField(p.getMatiereProduit(), "Matiére Produit");
//      Date Picker
        Picker date=new Picker();
        //date.setText(p.getDateExpirationProduit());
//      Date Picker
        SpanLabel nb=new SpanLabel("Veuiller Remplir Tout Les Champs.*");
        Button edit=new Button("Edit");

        
   //=======================================================================================
   //=====================================TRAITEMENT========================================
   
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
     
                    
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/edit/"
                            +p.getIdProduit()
                            +"/"+tf_ref.getText()
                            +"/"+tf_nom.getText()
                            +"/"+comboCat.getSelectedItem()
                            +"/"+tf_mat.getText()
                    );
                      
                    
                    
                    System.out.println("************************");
                    System.out.println(tf_ref.getText()
                            +"/"+tf_nom.getText()
                            +"/"+comboCat.getSelectedItem()
                            +"/"+tf_mat.getText()
                            );
                    System.out.println("************************");
        
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            
                        System.out.println("Le Produit a été modifié avec succées.");
                         ToastBar.showMessage("Produit modifié avec succées.",FontImage.MATERIAL_DONE);

                        
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


   //=======================================================================================
   //=======================================================================================
        f.add(tf_nom);
        f.add(tf_ref);
        f.add(comboCat);
        f.add(tf_mat);
        f.add(date);
        f.add(nb);
        f.add(edit);
        
        
        
        f.show();
    }

    //---------------------------------------------------------------------
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
    
}
