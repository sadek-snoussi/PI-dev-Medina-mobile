/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Bonplan;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author khali
 */
public class DetailBonplan {

    Form f;
    private Resources theme;
    SpanLabel NomBonplan;
    SpanLabel adressebonplan;
    SpanLabel Typebonplan;
    SpanLabel Note;
    ImageViewer imgv;
    EncodedImage enc;
    Image icon;
    Image imgs;

    public DetailBonplan() {

        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv = new ProductService();

        try {

            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            f = new Form("Details ");
            Container LOCCON = new Container(new GridLayout(1, 3));

            String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgBonplan/" + AfficheBonplan.Bps.getImgBonplan();
            enc = EncodedImage.create("/giphy.gif");
            imgs = URLImage.createToStorage(enc, AfficheBonplan.Bps.getImgBonplan(), url, URLImage.RESIZE_SCALE).scaled(350, 250);
            imgs = imgs.scaled(350, 250);

            NomBonplan = new SpanLabel("Nom : " + AfficheBonplan.Bps.getNombonplan());
            Typebonplan = new SpanLabel("Type : " + AfficheBonplan.Bps.getTypeBonplan());
            adressebonplan = new SpanLabel("Adresse : " + AfficheBonplan.Bps.getAdresseBonplan());
            imgv = new ImageViewer(imgs);

            icon = Image.createImage("/placeholder-on-map-paper-in-perspective.png");
            icon.scaled(5, 5);

            Button loc = new Button(icon);
            loc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    LocaliserBonplan LB = new LocaliserBonplan();
                    LB.getF().show();
                }
            });

            ////////////////////: Tamwih ////////////////
//            Slider Sli = new Slider();
//            Button valider2 = new Button("Valider");
//            Sli.setVisible(false);
//            valider2.setVisible(false);
            //SpanLabel SL = new SpanLabel("Noter " + AfficheBonplan.Bps.getNombonplan());
            Slider SliderStar = createStarRankSlider();

            C1.add(imgv);

            C1.add(NomBonplan);
            C1.add(Typebonplan);
            C1.add(adressebonplan);

            C1.add(FlowLayout.encloseCenter(SliderStar));
            //C1.add(loc);

            f.add(C1);

            LOCCON.add(new Label("                      "));
            LOCCON.add(loc);
            LOCCON.add(new Label(""));

            f.add(LOCCON);

            ////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////// Ajout Note ////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////
            SliderStar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    int rateVal = SliderStar.getProgress();

                    System.out.println("rate value :" + rateVal);
                    System.out.println("Affi" + AfficheBonplan.Bps.getIdBonplan());

                    ConnectionRequest con = new ConnectionRequest();

                    con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/AjoutNote/"
                            + "1/"
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

        } catch (IOException ex) {
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// Navigation Entre les PAGES /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
             f.getToolbar().addCommandToRightBar("<-",null, (ev)->{
                 
                 AfficheBonplan bnp = new AfficheBonplan();
                 bnp.getF().show();
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
