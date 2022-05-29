/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.User;
import com.mycompany.Service.PartenaireService;
import com.mycompany.Service.ProductService;
import java.io.IOException;

/**
 *
 * @author amalb
 */
public class ModifierProfileFree {

    Form modifProfileFree;

    TextField nom;
    TextField prenom;
    TextField email;
    TextField tel;
    TextField specialite;

    Label lblnom;
    Label lblprenom;
    Label lblemail;
    Label lbltel;
    Label lblspecialite;

    Button modifier;

    public ModifierProfileFree() {

        modifProfileFree = new Form("Mon Profile Freelancer", new FlowLayout(Component.CENTER, Component.CENTER));
        
            modifProfileFree.getToolbar().addCommandToOverflowMenu("Statistiques",null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                

                
            }
        });

        nom = new TextField("", "Nom", 20, TextArea.ANY);
        prenom = new TextField("", "Prenom", 20, TextArea.ANY);
        email = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
        tel = new TextField("", "Num de Telephone", 20, TextArea.PHONENUMBER);
        specialite = new TextField();

        nom.setText(User.user.getNomUser());
        prenom.setText(User.user.getPrenomUser());

        email.setText(User.user.getEmail());
        tel.setText(User.user.getTelBureauPro());
        specialite.setText(User.user.getSpecialitePart());

        lblnom = new Label("nom");
        lblprenom = new Label("prenom");
        lblemail = new Label("email");
        lbltel = new Label("numero de telephone");
        lblspecialite = new Label("specialite");

        Button modifier = new Button("Modifier");
        Container c = new Container(BoxLayout.y());

        c.add(lblnom);
        c.add(nom);

        c.add(lblprenom);
        c.add(prenom);

        c.add(lblemail);
        c.add(email);

        c.add(lbltel);
        c.add(tel);

        c.add(lblspecialite);
        c.add(specialite);

        c.add(modifier);
        modifProfileFree.add(c);

        User user = new User();
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Date datep = Date.valueOf(dateNaiss.getText());
                PartenaireService us = new PartenaireService();
                user.setNomUser(nom.getText());
                user.setPrenomUser(prenom.getText());
                user.setEmail(email.getText());
                user.setTelBureauPro(tel.getText());
                user.setSpecialitePart(specialite.getText());
                user.setId(User.user.getId());
                //user.setDateNaissUser(datep);
                us.ModifierProfileFree(user);
                  ToastBar.showMessage("Modification effectuée avec Succées", FontImage.MATERIAL_DONE);
            }
        });

        Resources theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));

        modifProfileFree.getToolbar().addCommandToLeftBar(" ", img.getImage(), new ActionListener<ActionEvent>() {
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
    }

    public Form getModifProfileFree() {
        return modifProfileFree;
    }

    public void setModifProfileFree(Form modifProfileFree) {
        this.modifProfileFree = modifProfileFree;
    }
    
    

}
