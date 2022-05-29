/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Videodiy;
import static com.mycompany.Gui.ValidVideoListClient.selectedVideodiy;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sofienne
 */
public class HomeForm {

    private Resources theme;
    Form f;
    ServiceVideo serviceVideo = new ServiceVideo();

    public HomeForm() {
        theme = UIManager.initFirstTheme("/theme");

        signup signup = new signup();

        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
//        AffichagePanier afp = new AffichagePanier();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();

        f = new Form("Souk Mdina");

        displayVideoList(serviceVideo.gettopRatedVideoList());
        Toolbar t1 = f.getToolbar();
        t1.getUnselectedStyle().setBgTransparency(18);
        Image icon = theme.getImage("icon.png");

        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("Votre Profile", "SidemenuTagline"));
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
        t1.addMaterialCommandToSideMenu("Modifier Profile", FontImage.MATERIAL_PORTRAIT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                profile.getModifProfileClient().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Contactez Partenaires", FontImage.MATERIAL_ASSISTANT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                lp.getListForm().show();

            }
        });

        t1.addMaterialCommandToSideMenu("demander Partenariat:", FontImage.MATERIAL_ASSIGNMENT_RETURNED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                demande.getDemandePart().show();

            }
        });
        /* t1.addMaterialCommandToSideMenu("stat", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Statistiques stat = new Statistiques();
                

            }
        });*/
        
         t1.addMaterialCommandToSideMenu("Video DIY", FontImage.MATERIAL_EVENT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                ValidVideoListClient validVideoListClient;
                ArrayList<Videodiy> videolist;
                ServiceVideo serviceVideo = new ServiceVideo();
                videolist = serviceVideo.getVideosList();
                System.err.println("btnAfficheVideo listener triggered => will callconstructor");
                validVideoListClient = new ValidVideoListClient(videolist);
                validVideoListClient.getF().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Actualité", FontImage.MATERIAL_EVENT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichageEvent ev = new AffichageEvent();
                ev.getF().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Reserver", FontImage.MATERIAL_EVENT_NOTE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichageEventReservation reservation = new AffichageEventReservation();
                reservation.getF().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Se déconnecter", FontImage.MATERIAL_EXIT_TO_APP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                signin.getLogin().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Bonplan ", FontImage.MATERIAL_MY_LOCATION,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheBonplan afb = new AfficheBonplan();
                afb.getF().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Gallerie ", FontImage.MATERIAL_PHOTO_FILTER,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AfficheGallerie afb = new AfficheGallerie();
                afb.getF().show();
            }
        });

        t1.addMaterialCommandToSideMenu("Guide", FontImage.MATERIAL_PORTRAIT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheGuide afb = new AfficheGuide();
                afb.getF().show();

            }
        });

        t1.addMaterialCommandToSideMenu("Video DIY", FontImage.MATERIAL_EVENT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                ValidVideoListClient validVideoListClient;
                ArrayList<Videodiy> videolist;
                ServiceVideo serviceVideo = new ServiceVideo();
                videolist = serviceVideo.getVideosList();
                System.err.println("btnAfficheVideo listener triggered => will callconstructor");
                validVideoListClient = new ValidVideoListClient(videolist);
                validVideoListClient.getF().show();

            }
        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public void displayVideoList(ArrayList<Videodiy> videolist) {
        for (Videodiy item : videolist) {

            try {

                Container cellContainer = new Container(BoxLayout.y());
                Container titreContainer = new Container();

                Label titre = new Label("Titre:");
                titre.getAllStyles().setFgColor(0x910b0b);
                titre.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

                Label titreLabel = new Label(item.getTitre());
                titreLabel.getAllStyles().setFgColor(0x383535);

                titreLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

                Label desc = new Label("Description:");
                desc.getAllStyles().setFgColor(0x910b0b);
                desc.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

                Label desclLabel = new Label(item.getDescriptionVideo());
                desclLabel.getAllStyles().setFgColor(0x383535);
                desclLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

                EncodedImage encImg = EncodedImage.create("/giphy.gif");
                ImageViewer imgV = new ImageViewer();

                String url = "http://localhost/Medina_VersionFinale/web/uploads/ImgVideo/" + item.getImageFromVideo();
                Image img = URLImage.createToStorage(encImg, item.getImageFromVideo(), url);
                img = img.scaled(318, 210);
                imgV.setImage(img);

                /**
                 * ***************Rating****************
                 */
                Slider starRank = new Slider();
                starRank.setEditable(false);
                starRank.setMinValue(0);
                starRank.setMaxValue(5);
                double ratingVal = item.getAvgRating();
                System.out.println("avg :" + ratingVal);
                starRank.setProgress((int) ratingVal);
                Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                        derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
                Style s = new Style(0xffff33, 0, fnt, (byte) 0);
                Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
                s.setOpacity(100);
                s.setFgColor(0);
                Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
                initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
                initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
                initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
                initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
                starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));

                /**
                 * ********************************
                 */
                cellContainer.add(imgV);

                titreContainer.add(titreLabel);
                cellContainer.add(titreContainer);
                cellContainer.add(desclLabel);
                cellContainer.add(FlowLayout.encloseCenter(starRank));
                f.add(cellContainer);
                f.add(new Slider());
                titreLabel.addPointerPressedListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        selectedVideodiy = item;
                        Form detailForm = new DetailVideo().getDetailForm();

                        detailForm.getToolbar().addCommandToRightBar("back", null, (ev) -> {
                            HomeForm h = new HomeForm();
                            h.getF().show();
                        });

                        detailForm.show();

                    }
                });

                //cellContainer.add(titreLabel);
                //cellContainer.add(desclLabel);
                f.show();
            } catch (IOException ex) {
            }

        }
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

}
