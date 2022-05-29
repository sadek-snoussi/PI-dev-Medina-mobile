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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import static com.mycompany.Gui.AfficheGallerie.Gals;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceGallerie;
import java.io.IOException;
import java.util.ArrayList;
import pidev.edu.souk.entities.Gallerie;

/**
 *
 * @author khali
 */
public class GallerieRecherche {

    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    
    
    private Resources theme;


    Form FRecherche = new Form("Recherche", (new BoxLayout(BoxLayout.Y_AXIS)));
    Container xx = new Container(new BoxLayout(BoxLayout.Y_AXIS));

    public GallerieRecherche() {
        
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv = new ProductService();

        try {
            
            theme = UIManager.initFirstTheme("/theme");
            FRecherche.getToolbar().addCommandToOverflowMenu("<- ", null, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    AfficheGallerie affGal = new AfficheGallerie();
                    affGal.getF().show();
                            
                }
            });
            
            
            
            ServiceGallerie servicegallerie = new ServiceGallerie();
            ArrayList<Gallerie> list = new ArrayList<Gallerie>();
            list = servicegallerie.getListB();

            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container CC1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container CC2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

            ComboBox combo = new ComboBox<>("Tunis", "Ben Arous", "Ariana", "Manouba", "Beja", "Kef", "Jandouba", "Sfax", "Sousse", "Gabes", "Nabeul", "Monastir", "Kairaoun", "Gafsa", "Kasserine", "Kebili", "Médenine", "Mahdia", "Sidi Bouzid", "Tataouine", "Zaghouan", "Bizerte", "Tozeur");
            combo.setRenderer(new GenericListCellRenderer<>(new MultiButton("a"), new MultiButton("")));

            ComboBox combo2 = new ComboBox<>("Textile", "Monument", "Statue", "Personnalité", "bibelot", "Autres creation");
            combo2.setRenderer(new GenericListCellRenderer<>(new MultiButton("a"), new MultiButton("")));

            Button ValidRecherche = new Button("Rechercher");

            Slider SL = new Slider();
            Slider SL2 = new Slider();
            SL.setVisible(false);
            SL2.setVisible(false);
            ///////////////  ///////////////  ///////////////  ///////////////

            Image icon = Image.createImage("/searching-tool.png");
            icon.scaled(1, 1);

            Image icon2 = Image.createImage("/searching-tool.png");
            icon.scaled(1, 1);

            Button loc = new Button(icon);
            Button loc2 = new Button(icon);
            Label GouvLabel = new Label("Gouvernerat :");
            Label TypeLabel = new Label("Type :");

            C1.add(GouvLabel);
            CC1.add(combo);
            CC1.add(loc);
            C1.add(CC1);

            C1.add(SL);

            C1.add(TypeLabel);
            CC2.add(combo2);
            CC2.add(loc2);
            C1.add(CC2);

            C1.add(SL2);

            C1.add(ValidRecherche);
            FRecherche.add(C1);

            loc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    FRecherche.removeAll();
                    xx.removeAll();

                    ArrayList<Gallerie> list = new ArrayList<Gallerie>();
                    list = servicegallerie.getListByGouv((String) combo.getSelectedItem());

                    if (list.isEmpty()) {

                        Container EmptyContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));

                        Label Empty = new Label("pas de gallerie trouvée à  " + ((String) combo.getSelectedItem()));
                        EmptyContainer.add(Empty);
                        FRecherche.add(EmptyContainer);
                    }

                    for (Gallerie gallerie : list) {
                        try {
                            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                            Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                            Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                            String url = "http://localhost//Medina//web//uploads//imgGallerie//" + gallerie.getImgGallerie();
                            System.out.println(url);

                            enc = EncodedImage.create("/giphy.gif");
                            imgs = URLImage.createToStorage(enc, gallerie.getImgGallerie(), url, URLImage.RESIZE_SCALE);
                            imgs = imgs.scaled(100, 100);

                            imgv = new ImageViewer(imgs);
                            // Label l1 = new Label("id : " + t.getIdEvent());
                            SpanLabel TitreGal = new SpanLabel(gallerie.getTitreGallerie());
                            SpanLabel TypeGal = new SpanLabel("Type : " + gallerie.getTypeGallerie());

                            Button txt = new Button();
                            txt.setVisible(false);

                            txt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {

                                    Gals = gallerie;
                                    DetailGallerie b3 = new DetailGallerie();
                                    b3.getF().show();
                                }
                            });

                            C1.add(imgv);
                            C2.add(TitreGal);
                            C2.add(TypeGal);
                            C2.add(txt);

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
                    FRecherche.add(xx);

                }
            });

            loc2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    FRecherche.removeAll();
                    xx.removeAll();
                    FRecherche.removeAll();

                    ArrayList<Gallerie> list = new ArrayList<Gallerie>();
                    list = servicegallerie.getListByType((String) combo2.getSelectedItem());
                       if (list.isEmpty()) {

                        Container EmptyContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));

                        Label Empty = new Label("pas d'article sur le type : " + ((String) combo2.getSelectedItem()));
                        EmptyContainer.add(Empty);
                        FRecherche.add(EmptyContainer);
                    }
                    

                    for (Gallerie gallerie : list) {
                        try {
                            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                            Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                            Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                            String url = "http://localhost//Medina//web//uploads//imgGallerie//" + gallerie.getImgGallerie();
                            System.out.println(url);

                            enc = EncodedImage.create("/giphy.gif");
                            imgs = URLImage.createToStorage(enc, gallerie.getImgGallerie(), url, URLImage.RESIZE_SCALE);
                            imgs = imgs.scaled(100, 100);

                            imgv = new ImageViewer(imgs);
                            // Label l1 = new Label("id : " + t.getIdEvent());
                            SpanLabel TitreGal = new SpanLabel(gallerie.getTitreGallerie());
                            SpanLabel TypeGal = new SpanLabel("Type : " + gallerie.getTypeGallerie());

                            Button txt = new Button();
                            txt.setVisible(false);

                            txt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {

                                    Gals = gallerie;
                                    DetailGallerie b3 = new DetailGallerie();
                                    b3.getF().show();
                                }
                            });

                            C1.add(imgv);
                            C2.add(TitreGal);
                            C2.add(TypeGal);
                            C2.add(txt);

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
                    FRecherche.add(xx);

                }
            });

            ValidRecherche.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    FRecherche.removeAll();
                    xx.removeAll();
                    ArrayList<Gallerie> list = new ArrayList<Gallerie>();
                    list = servicegallerie.getListByGouvAndType((String) combo.getSelectedItem(), (String) combo2.getSelectedItem());
                    
                    
                    if (list.isEmpty()) {

                        Container EmptyContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));

                        Label Empty = new Label("pas de résultat");
                        EmptyContainer.add(Empty);
                        FRecherche.add(EmptyContainer);
                    }

                    for (Gallerie gallerie : list) {
                        try {
                            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                            Container C2 = new Container(new FlowLayout(Component.CENTER, Component.TOP));
                            Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
                            String url = "http://localhost//Medina//web//uploads//imgGallerie//" + gallerie.getImgGallerie();
                            System.out.println(url);

                            enc = EncodedImage.create("/giphy.gif");
                            imgs = URLImage.createToStorage(enc, gallerie.getImgGallerie(), url, URLImage.RESIZE_SCALE);
                            imgs = imgs.scaled(100, 100);

                            imgv = new ImageViewer(imgs);
                            // Label l1 = new Label("id : " + t.getIdEvent());
                            SpanLabel TitreGal = new SpanLabel(gallerie.getTitreGallerie());
                            SpanLabel TypeGal = new SpanLabel("Type : " + gallerie.getTypeGallerie());

                            Button txt = new Button();
                            txt.setVisible(false);

                            txt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {

                                    Gals = gallerie;
                                    DetailGallerie b3 = new DetailGallerie();
                                    b3.getF().show();
                                }
                            });

                            C1.add(imgv);
                            C2.add(TitreGal);
                            C2.add(TypeGal);
                            C2.add(txt);

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
                    FRecherche.add(xx);
                }
            });


        } catch (IOException ex) {
        }

    }

    public Form getF() {
        return FRecherche;
    }

    public void setF(Form b) {
        this.FRecherche = b;
    }

}
