/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author ASUS I7
 */
public class HomeLogin {

    private Resources theme;
    Form f;

    public HomeLogin() {
        theme = UIManager.initFirstTheme("/theme");

        signup signup = new signup();

        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
//        AffichagePanier afp = new AffichagePanier();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();

        f = new Form("Souk Mdina");

        Toolbar t1 = f.getToolbar();
        t1.getUnselectedStyle().setBgTransparency(18);
        Image icon = theme.getImage("icon.png");

        Container topBar = BorderLayout.east(new Label(icon));

        topBar.setUIID("SideCommand");
        t1.addComponentToSideMenu(topBar);

        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));

        /*  Label picture = new Label("", "Container");
        f.add(picture);
        f.getUnselectedStyle().setBgColor(0xff0000);
        f.getUnselectedStyle().setBgTransparency(255);
        Toolbar.setGlobalToolbar(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        Image camera = FontImage.createMaterial(FontImage.MATERIAL_CAMERA, s);
       
        f.getToolbar().addCommandToRightBar("", camera, (ev) -> {
            try {
                int width = Display.getInstance().getDisplayWidth();
                Image capturedImage = Image.createImage(Capture.capturePhoto(width, -1));
                Image roundMask = Image.createImage(width, capturedImage.getHeight(), 0xff000000);
                Graphics gr = roundMask.getGraphics();
                gr.setColor(0xffffff);
                gr.fillArc(0, 0, width, width, 0, 360);
                Object mask = roundMask.createMask();
                capturedImage = capturedImage.applyMask(mask);
                picture.setIcon(capturedImage);
                f.revalidate();
            } catch (IOException err) {
                Log.e(err);
            }
        });*/
            t1.addMaterialCommandToSideMenu("Inscrivez-vous", FontImage.MATERIAL_ACCESSIBILITY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                signup s=new signup();
                s.getSignup().show();

            }
        });
        t1.addMaterialCommandToSideMenu("Actualité", FontImage.MATERIAL_EVENT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichageEvent ev = new AffichageEvent();
                ev.getF().show();

            }
        });

        t1.addMaterialCommandToSideMenu("demander Partenariat:", FontImage.MATERIAL_ASSIGNMENT_RETURNED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                signup s = new signup();
                s.getSignup().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Contactez Partenaires :", FontImage.MATERIAL_ASSISTANT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                signup s = new signup();
                s.getSignup().show();

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
