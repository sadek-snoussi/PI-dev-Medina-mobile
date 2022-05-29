/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.Storage;
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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author ASUS I7
 */
public class UserForm extends com.codename1.ui.Form {

    public static User UserFacebook = null;

    public UserForm() {

        this(com.codename1.ui.util.Resources.getGlobalResources());
        System.out.println("UserForm");
    }

    public UserForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        showFormElements();
        System.out.println("UserForm2");
    }

//-- DON'T EDIT BELOW THIS LINE!!!
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        System.out.println("initGuiBuilderComponents");

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("Retour", e -> previous.showBack());

        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        //setInlineStylesTheme(resourceObjectInstance);
        // setInlineStylesTheme(resourceObjectInstance);
        setTitle("Connect avec Facebook");
        setName("UserForm");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
    private void showFormElements() {
        /**
         * ***********************************2******************************************
         */

        System.out.println("showFormElements");
        //Pour manajimchi inhabitha wa intala3ha avec souris
        this.setScrollable(false);
        //Selon axe d'abssice
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        showData(this);
    }

    private void showData(UserForm form) {

        /**
         * ***********************************3******************************************
         */
        System.out.println("showData");
        //token pour accede a FB
        String token = (String) Storage.getInstance().readObject("token");
        if (token == null || token.equals("")) {

            showIfNotLoggedIn(form);

            //Ici vide token
            System.out.println(token);
        } else {
            showIfLoggedIn(form);
        }
    }

    private void showIfNotLoggedIn(UserForm form) {

        System.out.println("showIfNotLoggedIn");
        //Remove tout les composant s'il existe
        form.getContentPane().removeAll();
        //token==>name  ""==>objet passer en parametre
        Storage.getInstance().writeObject("token", "");
        //prepare variable pour insere  l'image
        ScaleImageLabel myPic = new ScaleImageLabel();
        //cree l'image
//            Image img = Image.createImage("/anonimo.jpg");

//Dimension  de l'image
//   myPic.setIcon(img);
        Dimension d = new Dimension(200, 200);
//appliquer la dimension
        myPic.setPreferredSize(d);
        Label labelespace = new Label("\n\n\n\n \n");
        Label labelespace1 = new Label("\n\n\n\n \n");
        Label labelespace2 = new Label("\n\n\n\n \n");
        /**
         * ***********************************4******************************************
         */
        form.add(labelespace);
        form.add(labelespace1);
        form.add(labelespace2);
        form.add(myPic);
        form.add(new Label("User n'est pas connecté"));
        Button buttonLogin = new Button("Login");
        buttonLogin.addActionListener((e) -> {
            facebookLogin(form);
        });
        form.add(buttonLogin);
//Re-layout du conteneur, ceci est utile lorsque nous modifions la hiérarchie du
//conteneur et que nous devons refaire la mise en page
        form.revalidate();
//form.show();
    }

    private Resources theme;

    private void showIfLoggedIn(UserForm form) {

        Resources res = UIManager.initFirstTheme("/theme");;
        /**
         * ***********************************6******************************************
         */

        //super.addSideMenu(res);         
        //lir le token
        String token = (String) Storage.getInstance().readObject("token");
        FaceBookAccess.setToken(token);
        System.out.println(token);
        final User me = new User();
        try {
            FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    form.getContentPane().removeAll();
                    System.out.println("showIfLoggedIn" + token);
                    Label labelespace = new Label("\n\n\n\n \n");
                    Label labelespace1 = new Label("\n\n\n\n \n");
                    Label labelespace2 = new Label("\n\n\n\n \n");

                    /**
                     * ***********************************4******************************************
                     */
                    form.add(labelespace);
                    form.add(labelespace1);
                    add(labelespace2);

                    Button buttonLogout = new Button("Logout");
                    buttonLogout.addActionListener((e) -> {
                        facebookLogout(form);
                        showIfNotLoggedIn(form);
                    });
                    System.out.println("");

                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                    URLImage background = URLImage.createToStorage(placeholder, "fbuser.jpg",
                            "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
                    background.fetch();
                    ScaleImageLabel myPic = new ScaleImageLabel();
                    myPic.setIcon(background);
                    System.out.println(background);
                    form.add(myPic);
                    form.add(buttonLogout);
                    System.out.println("form");
                    form.revalidate();
                    //form.show();
                }

            });
        } catch (IOException ex) {
            ex.printStackTrace();
            showIfNotLoggedIn(form);
        }
    }

    private void facebookLogout(UserForm form) {

        String clientId = "560078854379008";
        String redirectURI = "https://localhost/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "f5abd6a76eae82c63bf116f436b28236";
        Login fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        System.out.println("facebookLogout");
        //trigger the login if not already logged in
        fb.doLogout();
        Storage.getInstance().writeObject("token", "");
        showIfNotLoggedIn(form);
    }

    private void facebookLogin(UserForm form) {
        /**
         * ***********************************5******************************************
         */

        System.out.println("facebookLogin");
        // Idetifient de l'application fb ==>pour la simulateur

//oauth2URL:
        //Ceci est une classe utilitaire qui permet l'authentification Oauth2 Cet utilitaire utilise
        //le composant Codename One XHTML pour afficher les pages d'authentification.
        String clientId = "131581007575010";

        //Lezim créer un serveur Web
        String redirectURI = "http://localhost/";
        String clientSecret = "0e3c46d46bb6401d360c3257afb25aa1";

        //The Login abstract base class is used to simplify Oauth2 authentications 
        //Login==> * services.
        Login fb = FacebookConnect.getInstance();

        //L'identifiant client (appid) qui demande à se connecter
        fb.setClientId(clientId);

        //ou on va redirectionne
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);

        //Sets a LoginCallback listener
        //Définit le rappel de connexion qui recevra la notification de rappel d'événement de l'API
        //Paramètres: lc - le rappel de connexion ou null pour supprimer le rappel de connexion existant
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {

                System.out.println("login Failed");
                System.out.println("ouverture de session");
                Storage.getInstance().writeObject("token", "");
                showIfNotLoggedIn(form);
            }

            @Override
            public void loginSuccessful() {

                System.out.println("loginSuccessful");
                System.out.println("Fermeture de session");

                //Le jeton d'accès de ce service
                String token = fb.getAccessToken().getToken();
                Storage.getInstance().writeObject("token", token);
                showIfLoggedIn(form);
            }

        });
        //trigger the login if not already logged in
        if (!fb.isUserLoggedIn()) {
            fb.doLogin();
        } else {
            //get the token and now you can query the facebook API
            String token = fb.getAccessToken().getToken();
            Storage.getInstance().writeObject("token", token);
            showIfLoggedIn(form);
        }
    }

}
