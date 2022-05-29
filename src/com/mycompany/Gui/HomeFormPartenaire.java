/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.User;

/**
 *
 * @author amalb
 */
public class HomeFormPartenaire {
     private Resources theme;
    Form f;

    public HomeFormPartenaire() {
        
        
          theme = UIManager.initFirstTheme("/theme");

        signup signup = new signup();
         ModifierProfilePro profilePro = new ModifierProfilePro();
         ModifierProfileFree profileFree = new ModifierProfileFree();
        listPartenaires lp = new listPartenaires();
       // AffichagePanier afp = new AffichagePanier();
        signin signin = new signin();
        

        f = new Form("Souk Mdina");

        Toolbar t1 = f.getToolbar();

        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));
        
           t1.addMaterialCommandToSideMenu("Modifier Profile", FontImage.MATERIAL_ASSISTANT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                

                if(User.user.getTypeUser().equals("pro"))
                {
                profilePro.getModifProfilePro().show();

                    System.out.println("prooooooooooooooooooo");
                }
                else
                {
                    profileFree.getModifProfileFree().show();
                     System.out.println("freeeeeeeeeeeee");
                }
            }
        });
        
        
       
        t1.addMaterialCommandToSideMenu("Contactez Partenaires", FontImage.MATERIAL_ASSISTANT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                

                lp.getListForm().show();

            }
        });
        
          t1.addMaterialCommandToSideMenu("Mes Videos", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                videoPartenaireList videoList = new videoPartenaireList();
                videoList.getF().show();


            }
        });
		
		 t1.addMaterialCommandToSideMenu("Se déconnecter", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                signin.getLogin().show();

            }
        });
     
        
      
         
       
          
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
    
   
}
