/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import static java.lang.Math.round;

/**
 *
 * @author khali
 */
public class NoterBonplan {

    Form f;

    public NoterBonplan() {
        f = new Form("Note", new FlowLayout(Component.CENTER, Component.CENTER));

        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container C2 = new Container(new FlowLayout(Component.TOP, Component.TOP));

        Button valider = new Button("Valider");

        ////////////////////: Tamwih ////////////////
        Slider Sli = new Slider();
        Button valider2 = new Button("Valider");
        Sli.setVisible(false);
        valider2.setVisible(false);

        SpanLabel SL = new SpanLabel("Noter " + AfficheBonplan.Bps.getNombonplan());

        Slider SliderStar = createStarRankSlider();

        C1.add(SL);
        C1.add(valider2);
        C1.add(FlowLayout.encloseCenter(SliderStar));
        C1.add(Sli);
        C1.add(valider);

        f.add(C1);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////// Ajout Note ////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        valider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                int rateVal = SliderStar.getProgress();

                System.out.println("rate value :" + rateVal);
                System.out.println("Affi" + AfficheBonplan.Bps.getIdBonplan());

                ConnectionRequest con = new ConnectionRequest();

                con.setUrl("http://127.0.0.1:8000/api/AjoutNote/"
                        + "42/"
                        + AfficheBonplan.Bps.getIdBonplan()
                        + "/" + rateVal
                );

                con.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        System.out.println("We got response for EDIT action!");
                    }
                });

                con.setFailSilently(true);

                NetworkManager.getInstance().addToQueueAndWait(con);

                if (Dialog.show("Confirmation", "Votre note a bien était enregistré", "Ok", null)) {
                    DetailBonplan b3 = new DetailBonplan();
                    b3.getF().show();
                }
            }

        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// Navigation Entre les PAGES /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar tb = f.getToolbar();

        tb.addMaterialCommandToSideMenu("Souk El-Medina", FontImage.MATERIAL_SHOPPING_BASKET,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });

        tb.addMaterialCommandToSideMenu("Bonplan ", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheBonplan afb = new AfficheBonplan();
                afb.getF().show();

            }
        });

        tb.addMaterialCommandToSideMenu("Gallerie ", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AfficheGallerie afb = new AfficheGallerie();
                afb.getF().show();
            }
        });

        tb.addMaterialCommandToSideMenu("Guide", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheGuide afb = new AfficheGuide();
                afb.getF().show();

            }
        });

        tb.addCommandToOverflowMenu("Localiser " + AfficheBonplan.Bps.getNombonplan(), null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                LocaliserBonplan LB = new LocaliserBonplan();
                LB.getF().show();
            }
        });
        tb.addCommandToOverflowMenu("Consulter " + AfficheBonplan.Bps.getNombonplan(), null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                DetailBonplan b3 = new DetailBonplan();
                b3.getF().show();
            }
        });

    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {

        Slider starRank = new Slider();

        // starRank.setProgress(1);
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        starRank.setRenderValueOnTop(true);
        starRank.setRenderPercentageOnTop(true);
        Font fnt = Font.createTrueTypeFont("native:MainThin", "native:MainThin").
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
        return starRank;
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
