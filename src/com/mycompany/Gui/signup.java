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
import com.codename1.ui.Dialog;
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
import com.codename1.util.regex.RE;
import com.mycompany.Entity.User;

import com.mycompany.Service.UserService;

/**
 *
 * @author amalb
 */
public class signup {

    Form signup;

    private Resources theme;
    
    
    TextField nom;
    TextField prenom;
    TextField username;
    TextField email;
    TextField password;
    TextField confirmPass;
    TextField tel;
    Picker dateNaiss = new Picker();

    
   // TextField dateNaiss;
    TextField adresse;

    Label lblnom;
    Label lblprenom;
    Label lblusername;
    Label lblusernametest;
    Label lblemail;
    Label lblemailtest;
    Label lblpassword;
    Label lblconfirmPass;
    Label lbltel;
    Label lblteltest;
    Label lbldateNaiss;
    Label lbladresse;

    Button ajouter;

    public signup() {

        signup = new Form("Sign up", new FlowLayout(Component.CENTER, Component.CENTER));
        nom = new TextField("", "First Name", 20, TextArea.ANY);
        prenom = new TextField("", "First Name", 20, TextArea.ANY);
        username = new TextField("", "First Name", 20, TextArea.ANY);
        email = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
        password = new TextField("", "password", 20, TextArea.PASSWORD);
        confirmPass = new TextField("", "password", 20, TextArea.PASSWORD);
        tel = new TextField("", "Phone", 20, TextArea.PHONENUMBER);
        dateNaiss.setType(Display.PICKER_TYPE_DATE);
        //dateNaiss = new TextField();
        adresse = new TextField();
        lblusernametest = new Label();
        lblemailtest = new Label();
        lblteltest = new Label();
        lblemailtest.getAllStyles().setFgColor(0xe00b0b);
        lblusernametest.getAllStyles().setFgColor(0xe00b0b);
        lblteltest.getAllStyles().setFgColor(0xe00b0b);
//Validator v = new Validator();
//v.addConstraint(firstName, new LengthConstraint(2)).
//        addConstraint(surname, new LengthConstraint(2)).
//        addConstraint(url, RegexConstraint.validURL()).
//        addConstraint(email, RegexConstraint.validEmail()).
//        addConstraint(phone, new RegexConstraint(phoneRegex, "Must be valid phone number")).
//        addConstraint(num1, new LengthConstraint(4)).
//        addConstraint(num2, new LengthConstraint(4)).
//        addConstraint(num3, new LengthConstraint(4)).
//        addConstraint(num4, new LengthConstraint(4));
//
//v.addSubmitButtons(submit);
        lblnom = new Label("nom");
        lblprenom = new Label("prenom");
        lblusername = new Label("username");
        lblemail = new Label("email");
        lblpassword = new Label("Mot de Passe");
        lblconfirmPass = new Label("confirmer Mot de Passe");
        lbltel = new Label("numero de telephone");
        lbldateNaiss = new Label("date de Naissance");
        lbladresse = new Label("adresse");

        Button ajouter = new Button("S'inscrire");
        Container c = new Container(BoxLayout.y());

        c.add(lblnom);
        c.add(nom);

        c.add(lblprenom);
        c.add(prenom);

        c.add(lblemail);
        c.add(email);
        c.add(lblemailtest);

        c.add(lblusername);
        c.add(username);
        c.add(lblusernametest);

        c.add(lblpassword);
        c.add(password);

        c.add(lblconfirmPass);
        c.add(confirmPass);

        c.add(lbltel);
        c.add(tel);
        c.add(lblteltest);

        c.add(lbladresse);
        c.add(adresse);

        c.add(lbldateNaiss);
        c.add(dateNaiss);

        c.add(ajouter);
        signup.add(c);
        User user = new User();
        System.out.println("dateeeeee      "+dateNaiss.getDate());
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("***length*********"+dateNaiss.getDate().toString().length());
                if (testMail() == false) {

                    lblemailtest.setText("verifier votre email");
                }
                if (controlUsername() == false) {
                    lblusernametest.setText("username existant");
                }
                if (controlNumero() == false) {
                    lblteltest.setText("numero invalide");
                }
                
//                if(dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()-1).equals("2018")||
//                        dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2017")
//                        ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2016")
//                        ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2015")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2014")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2013")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2012")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2011")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2010")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2009")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2008")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2007")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2006")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2005")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2004")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2003")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2002")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2001")
//                         ||dateNaiss.getDate().toString().substring(30, dateNaiss.getDate().toString().length()).equals("2000"))
//                {
//                         Dialog.show("Echec", "Vous ne Pouvez pas choisir cette date", "ok", "");
//                }
                else {
                     lblteltest.setText("");
                    lblemailtest.setText("");
                    lblusernametest.setText("");
                    UserService us = new UserService();
                    user.setNomUser(nom.getText());
                    System.out.println("user*************************************"+user.getNomUser());
                    user.setPrenomUser(prenom.getText());
                    user.setUsername(username.getText());
                    user.setUsernameCanonical(username.getText());
                    user.setEmail(email.getText());
                    user.setEmailCanonical(email.getText());
                    user.setPassword(password.getText());
                    user.setTelUser(tel.getText());
                    user.setAdresse(adresse.getText());
                    
                   // user.setDateNaissUser(dateNaiss.getDate());
                    //System.out.println("user dateeee"+user.getDateNaissUser());
                    user.setEnabled(true);
                    
                    //user.setDateNaissUser(datep);
                    us.inscription(user);
                    ToastBar.showMessage("Votre inscription a été effectuée avec succées", FontImage.MATERIAL_DONE);
                }
            }
        });

        theme = UIManager.initFirstTheme("/theme");

        theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));


        signup.getToolbar().addCommandToLeftBar(" ",img.getImage(), new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                signin si = new signin();
                si.getLogin().show();
            }
        });

    }

    public Form getSignup() {
        return signup;
    }

    public void setSignup(Form signup) {
        this.signup = signup;
    }

    Boolean verifMail = false;
    Boolean verifUsername = false;

    private Boolean testMail() {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        RE pat = new RE(emailRegex);
        if (email.getText() == null) {
            System.out.println("emaaiill1111111");
            return false;
        } else if (pat.match(email.getText()) == false) {
            System.out.println("emaaii22222222");
            return false;

        } else {
            System.out.println("emaaiil333333333");
            return true;
        }

    }

    private Boolean controlUsername() {

        UserService ps = new UserService();
        System.out.println(username.getText());
        if (ps.findUserByLogin(username.getText().trim()).getId() == null) {
            verifUsername = true;
            System.out.println("control22" + ps.findUserByLogin(username.getText().trim()));
            return true;
        } else {

            verifUsername = false;
            System.out.println("control1" + ps.findUserByLogin(username.getText().trim()));
            return false;
        }

    }

    private Boolean controlNumero() {

        System.out.println(tel.getText().trim().length());
        if (tel.getText().trim().length() == 8) {

            String telRegex = "^[0-9]+$";
            RE pat = new RE(telRegex);

            if (pat.match(tel.getText()) == false) {

                System.out.println("1111");
                return false;

            } else {

                System.out.println("2222222");
                return true;

            }

        } else {

            System.out.println("333333333");
            return false;
        }

    }
    
    
}
