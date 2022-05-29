/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
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
import com.mycompany.Service.ProductService;
import com.mycompany.Service.UserService;
import java.io.IOException;

/**
 *
 * @author amalb
 */
public class ModifierProfileClient {


    Form modifProfileClient;

    TextField nom;
    TextField prenom;
    TextField username;
    TextField email;

    TextField tel;
    Picker dateNaiss = new Picker();
    TextField adresse;

    Label lblnom;
    Label lblprenom;
    Label lblusername;
    Label lblemail;

    Label lbltel;
    Label lbldateNaiss;
    Label lbladresse;

    Button modifier;

    public ModifierProfileClient() {
        modifProfileClient = new Form("Modifier Mon Profile", new FlowLayout(Component.CENTER, Component.CENTER));

        modifProfileClient.getToolbar().addCommandToOverflowMenu("Demander Partenariat",null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                choosePartenariat choose=new choosePartenariat();
                choose.getDemandePart().show();
            }
        });
        
        
        
        
        
        
        nom = new TextField("", "Nom", 20, TextArea.ANY);
        prenom = new TextField("", "Prenom", 20, TextArea.ANY);
        username = new TextField("", "Nom de l'utilisateur", 20, TextArea.ANY);
        email = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
        tel = new TextField("", "Num de Telephone", 20, TextArea.PHONENUMBER);
        adresse = new TextField();
       // dateNaiss.setType(Display.PICKER_TYPE_DATE);

        nom.setText(User.user.getNomUser());
        prenom.setText(User.user.getPrenomUser());
        username.setText(User.user.getUsername());
        email.setText(User.user.getEmail());
        tel.setText(User.user.getTelUser());
        adresse.setText(User.user.getAdresse());
        SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy");
        //SimpleDateFormat Heure = new SimpleDateFormat("HH:mm");

        String d = Date.format(User.user.getDateNaissUser());
       // String h = Heure.format(User.user.getDateNaissUser());
        //Label date = new Label("" + d);
        //System.out.println("amaaaaaaaaaaaaaaal"+User.user.getDateNaissUser());
       //dateNaiss.setDate(User.user.getDateNaissUser());

        lblnom = new Label("nom");
        lblprenom = new Label("prenom");
        lblusername = new Label("username");
        lblemail = new Label("email");
        lbltel = new Label("numero de telephone");
        lbladresse = new Label("adresse");
        //lbldateNaiss = new Label("date de Naissance");

        Button modifier = new Button("Modifier");
        Container c = new Container(BoxLayout.y());

        c.add(lblnom);
        c.add(nom);

        c.add(lblprenom);
        c.add(prenom);

        c.add(lblemail);
        c.add(email);

        c.add(lblusername);
        c.add(username);

        c.add(lbltel);
        c.add(tel);

        c.add(lbladresse);
        c.add(adresse);

       /* c.add(lbldateNaiss);
        c.add(dateNaiss);*/

        c.add(modifier);
        modifProfileClient.add(c);

        User user = new User();
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Date datep = Date.valueOf(dateNaiss.getText());
                UserService us = new UserService();
                user.setNomUser(nom.getText());
                user.setPrenomUser(prenom.getText());
                user.setUsername(username.getText());
                user.setEmail(email.getText());
                user.setTelUser(tel.getText());
                user.setAdresse(adresse.getText());
                user.setId(User.user.getId());
                //user.setDateNaissUser(dateNaiss.getDate());

                System.out.println("form modif client" + user.toString());
                us.ModifierProfileClient(user);
                  ToastBar.showMessage("Modification effectu�e avec Succ�es", FontImage.MATERIAL_DONE);

            }
        });

        Resources theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));

        modifProfileClient.getToolbar().addCommandToLeftBar(" ", img.getImage(), new ActionListener<ActionEvent>() {
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

    public Form getModifProfileClient() {
        return modifProfileClient;
    }

    public void setModifProfileClient(Form modifProfileClient) {
        this.modifProfileClient = modifProfileClient;
    }

}
