/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.mycompany.Entity.Bonplan;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceBonplan;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;

/**
 *
 * @author khali
 */
public class AfficheBonplan {

    Form b;
    Form Ftrie;
    Form Frecherche;

    SpanLabel lb;
    EncodedImage enc;
    Image imgs;
    Image iconUp;
    Image iconDown;
    ImageViewer imgv;

    Container xx;
    Container Xcont;

    public static Bonplan Bps = new Bonplan();

    public AfficheBonplan() {
        
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv=new ProductService();
        
        
        
        
        
        
        
        
        

        b = new Form("Bonplan", new FlowLayout(Component.CENTER));
        Ftrie = new Form("Trie", new FlowLayout(Component.CENTER, Component.CENTER));

        xx = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Xcont = new Container(new BoxLayout(BoxLayout.X_AXIS));

        ComboBox<String> combo = new ComboBox<String>("Salon de thé", "Bien être", "Site naturelle", "Cinéma", "Restaurant");
        combo.setRenderer(new GenericListCellRenderer<>(new MultiButton("a"), new MultiButton("")));

        Label ComboLabel = new Label("Type : ");

        ServiceBonplan servicebonplan = new ServiceBonplan();
        ArrayList<Bonplan> list = new ArrayList<Bonplan>();
        list = servicebonplan.getListB();

        TextField TFR = new TextField("", "Recherche BonPlan");
        b.add(TFR);
        Xcont.add(ComboLabel);
        Xcont.add(combo);
        b.add(Xcont);
        Container EmptyContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                xx.removeAll();
                EmptyContainer.removeAll();
           
                ArrayList<Bonplan> list = new ArrayList<Bonplan>();

                list = servicebonplan.getListByType((String) combo.getSelectedItem());
                SpanLabel Empty = new SpanLabel("pas de bonplan sur le type :  " + ((String) combo.getSelectedItem()));
                if (list.isEmpty()) {

                    EmptyContainer.add(Empty);
                    b.add(EmptyContainer);
                    
                }
                for (Bonplan bonPlan : list) {
                    try {
                        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                        Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                        String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgBonplan/" + bonPlan.getImgBonplan();
                        System.out.println(url);

                        enc = EncodedImage.create("/giphy.gif");
                        imgs = URLImage.createToStorage(enc, bonPlan.getImgBonplan(), url, URLImage.RESIZE_SCALE);
                        imgs = imgs.scaled(120, 130);

                        imgv = new ImageViewer();
                        imgv.setImage(imgs);
                        // Label l1 = new Label("id : " + t.getIdEvent());
                        SpanLabel nomBp = new SpanLabel(bonPlan.getNombonplan());
                        SpanLabel TypeBp = new SpanLabel("Type : " + bonPlan.getTypeBonplan());

                        Button txt = new Button();
                        txt.setVisible(false);

                        txt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                Bps = bonPlan;
                                DetailBonplan b3 = new DetailBonplan();
                                b3.getF().show();
                            }
                        });

                        C1.add(imgv);
                        C2.add(nomBp);
                        C2.add(TypeBp);
                        C2.add(txt);

                        C2.setLeadComponent(txt);
                        C2.add(FlowLayout.encloseCenter(createStarRankSlider(bonPlan)));

                        C3.add(C1);
                        C3.add(C2);

                        Slider SSS = new Slider();
                        SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                        xx.add(C3);
                        xx.add(SSS);

                    } catch (IOException ex) {

                    }
                }
            }
        });

        TFR.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
                xx.removeAll();
                ArrayList<Bonplan> list = new ArrayList<Bonplan>();
                if (TFR.getText().equals("")) {
                    list = servicebonplan.getListB();
                } else {
                    list = servicebonplan.getListByTag(TFR.getText());
                }
                for (Bonplan bonPlan : list) {
                    try {
                        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                        Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                        String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgBonplan/" + bonPlan.getImgBonplan();
                        System.out.println(url);

                        enc = EncodedImage.create("/giphy.gif");
                        imgs = URLImage.createToStorage(enc, bonPlan.getImgBonplan(), url, URLImage.RESIZE_SCALE);
                        imgs = imgs.scaled(120, 130);

                        imgv = new ImageViewer(imgs);
                        // Label l1 = new Label("id : " + t.getIdEvent());
                        SpanLabel nomBp = new SpanLabel(bonPlan.getNombonplan());
                        SpanLabel TypeBp = new SpanLabel("Type : " + bonPlan.getTypeBonplan());

                        Button txt = new Button();
                        txt.setVisible(false);

                        txt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                Bps = bonPlan;
                                DetailBonplan b3 = new DetailBonplan();
                                b3.getF().show();
                            }
                        });

                        C1.add(imgv);
                        C2.add(nomBp);
                        C2.add(TypeBp);
                        C2.add(txt);

                        C2.add(FlowLayout.encloseCenter(createStarRankSlider(bonPlan)));

                        C2.setLeadComponent(txt);

                        C3.add(C1);
                        C3.add(C2);

                        Slider SSS = new Slider();
                        SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                        xx.add(C3);
                        xx.add(SSS);

                    } catch (IOException ex) {

                    }
                }
            }

        });

        for (Bonplan bonPlan : list) {
            try {
                Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgBonplan/" + bonPlan.getImgBonplan();
                System.out.println(url);

                enc = EncodedImage.create("/giphy.gif");
                imgs = URLImage.createToStorage(enc, bonPlan.getImgBonplan(), url, URLImage.RESIZE_SCALE);
                imgs = imgs.scaled(120, 130);

                imgv = new ImageViewer(imgs);
                // Label l1 = new Label("id : " + t.getIdEvent());
                SpanLabel nomBp = new SpanLabel(bonPlan.getNombonplan());
                SpanLabel TypeBp = new SpanLabel("Type : " + bonPlan.getTypeBonplan());

                ///////////////////////////// Rating ////////////
                Button txt = new Button();
                txt.setVisible(false);

                txt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        Bps = bonPlan;
                        DetailBonplan b3 = new DetailBonplan();
                        b3.getF().show();
                    }
                });

                C1.add(imgv);
                C2.add(nomBp);
                C2.add(TypeBp);

                C2.add(FlowLayout.encloseCenter(createStarRankSlider(bonPlan)));

                C2.add(txt);

                C2.setLeadComponent(txt);

                C3.add(C1);
                C3.add(C2);
                xx.add(C3);
                Slider SSS = new Slider();
                SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));

                xx.add(SSS);

            } catch (IOException ex) {

            }

        }

        b.add(xx);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// TRIE /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Container CC1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label NoteLabel = new Label("Trie par note");
        NoteLabel.getAllStyles().setAlignment(1);
        Button Up = new Button("Ordre croissant ");
        Button Down = new Button("Ordre décroissant ");

        Up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                b.show();

                // xx.removeAll();
                ArrayList<Bonplan> list = new ArrayList<Bonplan>();
                list = servicebonplan.getListByAsc();
                xx.removeAll();

                for (Bonplan bonPlan : list) {
                    try {
                        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                        Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                        String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgBonplan/" + bonPlan.getImgBonplan();
                        System.out.println(url);

                        enc = EncodedImage.create("/giphy.gif");
                        imgs = URLImage.createToStorage(enc, bonPlan.getImgBonplan(), url, URLImage.RESIZE_SCALE);
                        imgs = imgs.scaled(120, 130);

                        imgv = new ImageViewer(imgs);
                        // Label l1 = new Label("id : " + t.getIdEvent());
                        SpanLabel nomBp = new SpanLabel(bonPlan.getNombonplan());
                        SpanLabel TypeBp = new SpanLabel("Type : " + bonPlan.getTypeBonplan());

                        Button txt = new Button();
                        txt.setVisible(false);

                        txt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                Bps = bonPlan;
                                DetailBonplan b3 = new DetailBonplan();
                                b3.getF().show();
                            }
                        });

                        C1.add(imgv);
                        C2.add(nomBp);
                        C2.add(TypeBp);
                        C2.add(txt);
                        C2.add(FlowLayout.encloseCenter(createStarRankSlider(bonPlan)));
                        C2.setLeadComponent(txt);

                        C3.add(C1);
                        C3.add(C2);
                        
                           Slider SSS = new Slider();
                        SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));
                        
                        xx.add(C3);
                        xx.add(SSS);

                    } catch (IOException ex) {

                    }
                }

            }
        });

        Down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                b.show();

                // xx.removeAll();
                ArrayList<Bonplan> list = new ArrayList<Bonplan>();
                list = servicebonplan.getListByDesc();
                xx.removeAll();

                for (Bonplan bonPlan : list) {
                    try {
                        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                        Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                        String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgBonplan/" + bonPlan.getImgBonplan();
                        System.out.println(url);

                        enc = EncodedImage.create("/giphy.gif");
                        imgs = URLImage.createToStorage(enc, bonPlan.getImgBonplan(), url, URLImage.RESIZE_SCALE);
                        imgs = imgs.scaled(120, 130);

                        imgv = new ImageViewer(imgs);
                        // Label l1 = new Label("id : " + t.getIdEvent());
                        SpanLabel nomBp = new SpanLabel(bonPlan.getNombonplan());
                        SpanLabel TypeBp = new SpanLabel("Type : " + bonPlan.getTypeBonplan());

                        Button txt = new Button();
                        txt.setVisible(false);

                        txt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                Bps = bonPlan;
                                DetailBonplan b3 = new DetailBonplan();
                                b3.getF().show();
                            }
                        });

                        C1.add(imgv);
                        C2.add(nomBp);
                        C2.add(TypeBp);
                        C2.add(txt);
                        C2.add(FlowLayout.encloseCenter(createStarRankSlider(bonPlan)));

                        C2.setLeadComponent(txt);

                        C3.add(C1);
                        C3.add(C2);
                        
                        
                           Slider SSS = new Slider();
                        SSS.setPreferredSize(new Dimension(SSS.getWidth() * 5, 2));
                        
                        xx.add(C3);
                        xx.add(SSS);

                    } catch (IOException ex) {

                    }
                }

            }
        });

        ////////////////////////////////////// Design ///////////////////////////////////////////
        NoteLabel.setTextPosition(Component.RIGHT);
        Style NoteLabelStyle = NoteLabel.getAllStyles();

        FontImage.setMaterialIcon(Down, FontImage.MATERIAL_KEYBOARD_ARROW_DOWN);
        FontImage.setMaterialIcon(Up, FontImage.MATERIAL_KEYBOARD_ARROW_UP);

        Slider Slid = new Slider();
        Slid.setVisible(false);
        Slider Slid2 = new Slider();
        Slid2.setVisible(false);
        Slid2.setHeight(1);
        ///////////////////////////////////////////////////////////////////////////////////////

        CC1.add(NoteLabel);
        CC1.add(Slid);
        CC1.add(Up);
        CC1.add(Slid2);
        CC1.add(Down);

        Ftrie.add(CC1);

/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// Recherche  /////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// Navigation Entre les PAGES /////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar tb = b.getToolbar();

        Toolbar tb3 = Ftrie.getToolbar();

                    //--------------------------------ToolBar--------------------------------------
        //com.codename1.ui.Toolbar tb = f.getToolbar();

        tb.addMaterialCommandToSideMenu("   Souk El-Medina", FontImage.MATERIAL_SHOPPING_BASKET,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        
        
            tb.addMaterialCommandToSideMenu("Mon Profile", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                

                if(User.user.getTypeUser().equals("pro"))
                {
                profilePro.getModifProfilePro().show();

                    System.out.println("prooooooooooooooooooo");
                }
                else if(User.user.getTypeUser().equals("freelancer"))
                {
                    profileFree.getModifProfileFree().show();
                     System.out.println("freeeeeeeeeeeee");
                }
                else{
                   profile.getModifProfileClient().show();
                }
            }
        });
        

        tb.addMaterialCommandToSideMenu("Produits", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                try {
                    AllProduct ap=new AllProduct(serv.getList());
                    ap.getF().show();
                } catch (IOException ex) {
                }

            }
        });
  
        tb.addMaterialCommandToSideMenu("Mon Panier.", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichagePanier affichePanier = new AffichagePanier();
                affichePanier.getF().show();

            }
        });

        tb.addMaterialCommandToSideMenu("Nos Partenaires", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                lp.getListForm().show();

            }
        });
        
        tb.addMaterialCommandToSideMenu("Actualités", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AffichageEvent ev = new AffichageEvent();
                ev.getF().show();

            }
        });

        tb.addMaterialCommandToSideMenu("Découvertes ", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AfficheBonplan afb = new AfficheBonplan();
                afb.getF().show();

            }
        });


        tb.addMaterialCommandToSideMenu("Videos DIY", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT,
            new ActionListener() {
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

        tb.addMaterialCommandToSideMenu("Se déconnecter", FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, 
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                signin.getLogin().show();

            }
        });
    
    // ////////////////////////////////////////////// end Toolbar ///////////////////////////////////////////

        tb.addCommandToOverflowMenu("Gallerie ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                    
                AfficheGallerie affGal=new AfficheGallerie();
                affGal.getF().show();

            }
        });        
        
        tb.addCommandToOverflowMenu("Guide ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                AfficheGuide affGui=new AfficheGuide();
                affGui.getF().show();
                
            }
        });
        
                
            tb.addCommandToOverflowMenu("Trie Bons Plans ", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Ftrie.show();
            }
        });



    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider(Bonplan b) {
        Double AvgNote = b.getAvgRating();
//AfficheBonplan.Bps.getAvgRating();

        System.out.println(AvgNote);

        Slider starRank = new Slider();

        int c = (int) round(AvgNote);

        starRank.setEditable(false);
        starRank.setProgress(c);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        starRank.setRenderValueOnTop(true);
        starRank.setRenderPercentageOnTop(true);
        Font fnt = Font.createTrueTypeFont("native:MainThin", "native:MainThin").
                derive(Display.getInstance().convertToPixels(3, true), Font.STYLE_PLAIN);
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
        return b;
    }

    public void setF(Form b) {
        this.b = b;
    }

}
