/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Event;
import com.mycompany.Service.ServiceEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ASUS I7
 */
public class AffichageEventReservation {

    Form f;
    Form f2;
    Form f3;

    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;

    public static Event rec = new Event();
    private Resources theme;

    public AffichageEventReservation() {
        theme = UIManager.initFirstTheme("/theme");
        f = new Form("Reserver Stand", BoxLayout.y());
        
                        f.getToolbar().addCommandToLeftBar(" ", theme.getImage("back-command.png"), e -> {

                            AffichageEvent affEvent=new AffichageEvent();
                            affEvent.getF().show();

                        });
        
        
        
        f2 = new Form("Detail Evenement", BoxLayout.y());

        ServiceEvent serviceTask = new ServiceEvent();
        ArrayList<Event> lis = serviceTask.getListEvent();

        for (Event t : lis) {

            try {
                Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Container C3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container C4 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/img/" + t.getUrlafficheevent();
                System.out.println(url);
                Button detail = new Button("Detail");
                // Button reserver = new Button("Reserver");

                System.out.println(imgs);

                enc = EncodedImage.create("/load.png");
                imgs = URLImage.createToStorage(enc, t.getUrlafficheevent(), url, URLImage.RESIZE_SCALE);
                imgs = imgs.scaled(350, 350);
                imgv = new ImageViewer();
                imgv.setImage(imgs);

                // Label l1 = new Label("id : " + t.getIdEvent());
                SpanLabel nom = new SpanLabel(t.getNomEvent());

                Label l2 = new Label(t.getDescriptionevent());
                Label l3 = new Label(t.getLieux());
                Label nbp = new Label(String.valueOf(t.getNbrePlace()));
                Label nbs = new Label(String.valueOf(t.getNbreStand()));

                ////////Affichage Date/////////////////
                SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat Heure = new SimpleDateFormat("HH:mm");

                String d = Date.format(t.getDateEvent());
                String h = Heure.format(t.getDateEvent());
                Label date = new Label("" + d + " à " + h);
                ///////////////////////////////////////

                nbp.setVisible(false);
                nbs.setVisible(false);

                C1.add(imgv);
                C2.add(nom);

                C2.add(l2);
                C2.add(l3);
                C2.add(date);

                C1.setLeadComponent(l2);
                C4.add(detail);
                //  C3.add(reserver);

                f.add(C1);

                f.add(C2);
                f.add(C3);
                f.add(new Slider());

                C2.setLeadComponent(detail);
                // C3.setLeadComponent(reserver);

                detail.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        rec = t;
                        Form f2 = new detailReserv().getF();
                        f2.show();
                    }

                });

                /* reserver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        rec = t;
                        AffichageStandDispo affispo = new AffichageStandDispo();
                        AffichageStandDispo.idevent = rec.getIdEvent();
                        System.out.println("lilii");
                        affispo.getF().show();
                        // Form f4 = new AffichageStandDispo().getF();
                        Toolbar tb = affispo.getF().getToolbar();
                        tb.addMaterialCommandToLeftBar("Back", FontImage.MATERIAL_ARROW_BACK, e -> {

                            f.showBack();

                        });
                        affispo.getF().show();
                    }
                });*/
            } catch (IOException ex) {

            }
        }

//        Toolbar tb = f.getToolbar();
//        Toolbar.setGlobalToolbar(true);
//
//        tb.addMaterialCommandToSideMenu("Souk MEdina", FontImage.MATERIAL_HOME, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                HomeForm h = new HomeForm();
//                h.getF().show();
//            }
//        });
//
//        tb.addMaterialCommandToSideMenu("Actualité", FontImage.MATERIAL_EVENT, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                AffichageEvent ev = new AffichageEvent();
//                ev.getF().show();
//            }
//        });
//
//        tb.addMaterialCommandToSideMenu("Reservation", FontImage.MATERIAL_EVENT_NOTE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                AffichageEventReservation reservation = new AffichageEventReservation();
//                reservation.getF().show();
//            }
//        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
