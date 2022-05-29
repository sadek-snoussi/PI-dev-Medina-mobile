/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.Storage;
import com.codename1.facebook.User;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import java.io.IOException;

/**
 *
 * @author ASUS I7
 */
/**
 * GUI builder created Form
 *
 * @author Tiburcio
 */
public class UserForm2 extends com.codename1.ui.Form {

    public static User UserFacebook = null;

    public UserForm2() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    public UserForm2(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        showFormElements();
    }

//-- DON'T EDIT BELOW THIS LINE!!!
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//-- DON'T EDIT ABOVE THIS LINE!!!
    private void showFormElements() {
        this.setScrollable(false);
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        showData(this);
    }

    private void showData(UserForm2 form) {
        String token = (String) Storage.getInstance().readObject("token");
        if (token == null || token.equals("")) {
            showIfNotLoggedIn(form);
        } else {
            try {
                showIfLoggedIn(form);
            } catch (IOException ex) {

            }
        }
    }

    private void showIfNotLoggedIn(UserForm2 form) {
        try {
            form.getContentPane().removeAll();
            Storage.getInstance().writeObject("token", "");
            ScaleImageLabel myPic = new ScaleImageLabel();
            //            Image img = Image.createImage("/anonimo.jpg");
            //  myPic.setIcon(img);
            Dimension d = new Dimension(50, 50);
            myPic.setPreferredSize(d);
            Label labelespace = new Label("\n\n\n\n \n");
            Label labelespace1 = new Label("\n\n\n\n \n");
            Label labelespace2 = new Label("\n\n\n\n \n");
            form.add(labelespace);
            form.add(labelespace1);
            form.add(labelespace2);
            form.add(myPic);
            
            Button buttonLogin = new Button("Se Connecter", Image.createImage("/fb.png"));
            buttonLogin.addActionListener((e) -> {
                facebookLogin(form);
            });
            form.add(buttonLogin);
            form.revalidate();
            //form.show();
        } catch (IOException ex) {
         
        }
    }

    private void showIfLoggedIn(UserForm2 form) throws IOException {
        String token = (String) Storage.getInstance().readObject("token");
        FaceBookAccess.setToken(token);
        final User me = new User();
        FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String miNombre = me.getName();

                form.getContentPane().removeAll();

                form.add(new Label(miNombre));

                Button buttonLogout = new Button("Logout");
                buttonLogout.addActionListener((e) -> {
                    facebookLogout(form);
                    showIfNotLoggedIn(form);
                });

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                URLImage background = URLImage.createToStorage(placeholder, "fbuser.jpg",
                        "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
                background.fetch();
                ScaleImageLabel myPic = new ScaleImageLabel();
                myPic.setIcon(background);

                form.add(myPic);
                form.add(buttonLogout);

                form.revalidate();
                //form.show();
            }

        });
    }

    private void facebookLogout(UserForm2 form) {
        String clientId = "560078854379008";
        String redirectURI = "https://www.google.tn/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "f5abd6a76eae82c63bf116f436b28236";
        Login fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);

        //trigger the login if not already logged in
        fb.doLogout();
        Storage.getInstance().writeObject("token", "");
        showIfNotLoggedIn(form);
    }

    private void facebookLogin(UserForm2 form) {
        //use your own facebook app identifiers here   
        //These are used for the Oauth2 web login process on the Simulator.
        String clientId = "560078854379008";
        String redirectURI = "https://www.google.tn/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "f5abd6a76eae82c63bf116f436b28236";
        Login fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        //Sets a LoginCallback listener
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
                System.out.println("login Failed");
                Storage.getInstance().writeObject("token", "");
                showIfNotLoggedIn(form);
            }

            @Override
            public void loginSuccessful() {
               
                    System.out.println("Funcionó el login");
                    String token = fb.getAccessToken().getToken();
                    Storage.getInstance().writeObject("token", token);
                   HomeLogin h=new HomeLogin();
                   h.getF().show();
                
            }

        });
        //trigger the login if not already logged in
        if (!fb.isUserLoggedIn()) {
            fb.doLogin();
        } else {
           
            try {
                //get the token and now you can query the facebook API
                String token = fb.getAccessToken().getToken();
                Storage.getInstance().writeObject("token", token);
                showIfLoggedIn(form);
            } catch (IOException ex) {
               
            }
            }
        
    }

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {
        System.out.println("initGuiBuilderComponents");

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("Retour", e -> previous.showBack());

        setLayout(new com.codename1.ui.layouts.LayeredLayout());

        setInlineStylesTheme(resourceObjectInstance);
        setInlineStylesTheme(resourceObjectInstance);
        setTitle("Connect avec Facebook");
        setName("UserForm");
    }

    public void setInlineStylesTheme(Resources resourceObjectInstance) {
 
    }
    
  
    
    

}
