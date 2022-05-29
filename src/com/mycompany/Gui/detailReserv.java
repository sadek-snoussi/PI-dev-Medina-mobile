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
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import static com.mycompany.Gui.AffichageEventReservation.rec;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceEvent;
import java.io.IOException;

/*
 * @author ASUS I7
 */
public class detailReserv {

    Form f;
    SpanLabel nomE;
    SpanLabel desc;
    SpanLabel lieu;
    SpanLabel nbrP;
    SpanLabel nbrs;

    private Resources theme;

    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;

    public detailReserv() {

        try {
            f = new Form("Reservations", new FlowLayout());
            
            theme = UIManager.initFirstTheme("/theme");
                f.getToolbar().addCommandToLeftBar(" ", theme.getImage("back-command.png"), new ActionListener<ActionEvent>() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    AffichageEventReservation aff=new AffichageEventReservation();
                    aff.getF().show();

                }
            });
            
            
            
            
            
            
            Container C1 = new Container(new FlowLayout());
            Container C2 = new Container(new FlowLayout());
            ServiceEvent sr = new ServiceEvent();
            Button Res = new Button("Reserver");

            // ArrayList<Event> event = sr.getListEvent();
            String url = "http://localhost/Medina_VersionFinale_web/web/uploads/img/" + AffichageEvent.rec.getUrlafficheevent();
            enc = EncodedImage.create("/giphy.gif");
            nomE = new SpanLabel("Nom Evenement : " + AffichageEventReservation.rec.getNomEvent());

            nbrs = new SpanLabel("Stand Disponibles " + AffichageEventReservation.rec.getNbreStand());
            desc = new SpanLabel("Description : " + AffichageEventReservation.rec.getDescriptionevent());
            lieu = new SpanLabel("Lieux : " + AffichageEventReservation.rec.getLieux());

            ////////Affichage Date/////////////////
            SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat Heure = new SimpleDateFormat("HH:mm");

            String d = Date.format(AffichageEventReservation.rec.getDateEvent());
            String h = Heure.format(AffichageEventReservation.rec.getDateEvent());
            Label date = new Label("" + d + " à " + h+"h");
            ///////////////////////

            imgs = URLImage.createToStorage(enc, AffichageEventReservation.rec.getUrlafficheevent(), url, URLImage.RESIZE_SCALE).scaled(400, 300);
            imgs = imgs.scaled(350, 250);
            imgv = new ImageViewer();
            imgv.setImage(imgs);
            
            
            C1.add(imgv);

            C1.add(nomE);
            C1.add(date);

            C1.add(desc);
            C1.add(nbrs);
            C1.add(lieu);

            C2.add(Res);


            f.add(C1);
            f.add(C2);

            Res.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (AffichageEventReservation.rec.getNbreStand() <= 0) {
                        Dialog.show("Info!!", "Stock Epuisé!!", "OK", null);

                    } else {
                        AffichageStandDispo affispo = new AffichageStandDispo();
                        AffichageStandDispo.idevent = rec.getIdEvent();
                        System.out.println("lilii");
                        affispo.getF().show();
                    
                    }
                }
            });



        } catch (IOException ex) {

        }

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
