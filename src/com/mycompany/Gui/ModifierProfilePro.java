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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.User;
import com.mycompany.Service.PartenaireService;
import com.mycompany.Service.ProductService;
import java.io.IOException;
import com.mycompany.Service.UserService;



/**
 *
 * @author amalb
 */
public class ModifierProfilePro {

    Form modifProfilePro;

    TextField nom;
    TextField prenom;
    TextField nomE;
    TextField email;
    TextField tel;
    TextField grade;
    TextField specialite;
    TextField adresse;

    Label lblnom;
    Label lblprenom;
    Label lblnomE;
    Label lblemail;
    Label lbltel;
    Label lblgrade;
    Label lblspecialite;
    Label lbladresse;

    Button modifier;

    public ModifierProfilePro() {

        modifProfilePro = new Form("Mon Profile Pro", new FlowLayout(Component.CENTER, Component.CENTER));
        
        
        
        modifProfilePro.getToolbar().addCommandToOverflowMenu("Statistiques",null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                

                BarChartPart stat =new BarChartPart();
               
                
            }
        });

        nom = new TextField("", "Nom", 20, TextArea.ANY);
        prenom = new TextField("", "Prenom", 20, TextArea.ANY);
        nomE = new TextField("", "nom de l'entreprise", 20, TextArea.ANY);
        email = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
        tel = new TextField("", "Num de Telephone", 20, TextArea.PHONENUMBER);
        adresse = new TextField();
        grade = new TextField();
        specialite = new TextField();

        Picker p = new Picker();
        Picker p_spec = new Picker();
        
        String[] grades = {"platinuim", "silver", "gold"};
        p.setStrings(grades);
        p.setSelectedString(grades[0]);

        String[] specialites = {"Patisserie", "bijouterie", "Habillement", "epicerie", "tapis", "decoration"};
        p_spec.setStrings(specialites);
        p_spec.setSelectedString(specialites[0]);

        nom.setText(User.user.getNomUser());
        prenom.setText(User.user.getPrenomUser());
        nomE.setText(User.user.getNomEntreprisePro());
        email.setText(User.user.getEmail());
        tel.setText(User.user.getTelBureauPro());
        adresse.setText(User.user.getAdresse());
        System.out.println("**********"+User.user.getAdresse()+"****************");
        grade.setText(User.user.getGradePro());
        specialite.setText(User.user.getSpecialitePart());

        lblnom = new Label("nom");
        lblprenom = new Label("prenom");
        lblnomE = new Label("Nom de l'entreprise");
        lblemail = new Label("email");
        lbltel = new Label("numero de telephone");
        lbladresse = new Label("adresse");
        lblgrade = new Label("grade");
        lblspecialite = new Label("specialite");

        Button modifier = new Button("Modifier");
        Container c = new Container(BoxLayout.y());

        c.add(lblnom);
        c.add(nom);

        c.add(lblprenom);
        c.add(prenom);

        c.add(lblemail);
        c.add(email);

        c.add(lblnomE);
        c.add(nomE);

        c.add(lbltel);
        c.add(tel);

        c.add(lbladresse);
        c.add(adresse);

        c.add(lblgrade);
        c.add(p);

        c.add(lblspecialite);
        c.add(p_spec);

        c.add(modifier);
        modifProfilePro.add(c);

        User user = new User();
        
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Date datep = Date.valueOf(dateNaiss.getText());
                PartenaireService us = new PartenaireService();
                user.setNomUser(nom.getText());
                user.setPrenomUser(prenom.getText());
                user.setNomEntreprisePro(nomE.getText());
                user.setEmail(email.getText());
                user.setTelBureauPro(tel.getText());
                user.setAdresse(adresse.getText());
                user.setGradePro(p.getText());
                user.setSpecialitePart(p_spec.getText());
                user.setId(User.user.getId());
                //user.setDateNaissUser(datep);
                us.ModifierProfilePro(user);
                   ToastBar.showMessage("Modification effectuée avec Succées", FontImage.MATERIAL_DONE);
            }
        });

        Resources theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));

        modifProfilePro.getToolbar().addCommandToLeftBar(" ", img.getImage(), new ActionListener<ActionEvent>() {
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

    public Form getModifProfilePro() {
        return modifProfilePro;
    }

    public void setModifProfilePro(Form modifProfilePro) {
        this.modifProfilePro = modifProfilePro;
    }

}
