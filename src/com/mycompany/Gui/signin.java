/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.User;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.UserService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author amalb
 */
public class signin {

    private Resources theme;
    Form login;
    Form error;
    TextField loginT;
    TextField pass;
    Button cnx;
    Button fb;
    String message;
    ConnectionRequest cn;

    public signin() {

        try {
            login = new Form("Log in", new FlowLayout(Component.CENTER, Component.CENTER));
            error = new Form("Error", new FlowLayout(Component.CENTER, Component.CENTER));

            //login.getUnselectedStyle().setBgColor(30);
            Container c = new Container(BoxLayout.y());
            Container c1 = new Container(BoxLayout.y());
            loginT = new TextField("", "login", 20, TextArea.ANY);

            pass = new TextField("", "password", 20, TextArea.PASSWORD);
            cnx = new Button("Se connecter");
            fb = new Button("Connectez avec Facebook", Image.createImage("/fb.png"));
            c.add(loginT);
            c.add(pass);
            c.add(cnx);
            c.add(fb);

            Button signup = new Button("S'inscrire");
            //signup.getUnselectedStyle().setBgTransparency(18);
            signup.getUnselectedStyle().setTextDecoration(125);
            //signup.getAllStyles().setBorder(Border.createEmpty());
            // signup.getAllStyles().setBorder(Border.createLineBorder(3));
            signup.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
            //signup.getAllStyles().setBorder(Bo);

            c1.add(signup);
            login.add(c);
            login.add(c1);
            signup signupForm = new signup();
            cnx.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    String url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/authentification/" + loginT.getText() + "/" + pass.getText();

                    cn = new ConnectionRequest();
                    cn.setUrl(url);
                    cn.addResponseListener((e) -> {

                        String str = new String(cn.getResponseData());
                        System.out.println(str);

                    });

                    System.out.println(url);
                    //cn.setPost(false);

                    cn.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            try {
                                message = new String(cn.getResponseData(), "utf-8");

                                if (message.length() > 3) {
                                    UserService us = new UserService();

                                    User.user = us.findUserByLogin(loginT.getText());

                                    if (User.user.getPartenariat() == 1) {

                                        System.out.println("*****************************");
                                        ProductService serv = new ProductService();
                                        System.out.println(serv.getList());

                                        AllProduct h = new AllProduct(serv.getList());
                                        h.getF().show();

                                    } else {
                                        ProductService serv = new ProductService(); 
                                        AllProduct h = new AllProduct(serv.getList());
                                        h.getF().show();

 

                                    }
                                } else {
                                    Dialog.show("Erreur", "Erreur login", "ok", "cancel");
                                }
                            } catch (UnsupportedEncodingException ex) {

                            } catch (IOException ex) {
                            }
                        }
                    });
                    NetworkManager.getInstance().addToQueueAndWait(cn);
                    System.out.println("heeeeeeeeeeeeeeeereeeeeeeeeeee");
                }

            });
            
            
//            theme = UIManager.initFirstTheme("/theme");
//            ImageViewer img = new ImageViewer();
//            img.setImage(theme.getImage("back-command.png"));
//
//            signupForm.getSignup().getToolbar().addCommandToLeftBar("Back", img.getImage(), new ActionListener<ActionEvent>() {
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    signupForm.getSignup().show();
//                }
//            });

            signup.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    signup su = new signup();
                    su.getSignup().show();
                }
            });
            fb.addActionListener(new ActionListener<ActionEvent>() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    UserForm2 l = new UserForm2();
                    l.show();
                }
            });
        } catch (IOException ex) {

        }

    }

    public Form getLogin() {
        return login;
    }

    public void setLogin(Form login) {
        this.login = login;
    }

    public Form getError() {
        return error;
    }

    public void setError(Form error) {
        this.error = error;
    }

}
