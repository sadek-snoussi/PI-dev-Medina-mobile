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
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
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
import static com.mycompany.Gui.AffichageEvent.rec;
import com.mycompany.Service.ServiceEvent;
import java.io.IOException;

/**
 *
 * @author ASUS I7
 */
public class detailEvent {

    Form f;
    SpanLabel nomE;
    SpanLabel desc;
    SpanLabel lieu;
    SpanLabel nbrP;
    SpanLabel nbrs;

    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;

    private Resources theme;

    public detailEvent() {

        try {
            f = new Form("S'inscrire");

            theme = UIManager.initFirstTheme("/theme");
            f.getToolbar().addCommandToLeftBar(" ", theme.getImage("back-command.png"), e -> {

                AffichageEvent affEvent = new AffichageEvent();
                affEvent.getF().show();
            });

            Container C1 = new Container(new FlowLayout(Component.LEFT));
            Container C2 = new Container(new FlowLayout(0, Component.CENTER));

            ServiceEvent sr = new ServiceEvent();

            // ArrayList<Event> event = sr.getListEvent();
            String url = "http://localhost/Medina_VersionFinale_web/web/uploads/img/" + AffichageEvent.rec.getUrlafficheevent();
            enc = EncodedImage.create("/giphy.gif");
            nomE = new SpanLabel(" " + AffichageEvent.rec.getNomEvent());
            desc = new SpanLabel("Description: " + AffichageEvent.rec.getDescriptionevent());
            lieu = new SpanLabel("Lieux: " + AffichageEvent.rec.getLieux());

            nbrP = new SpanLabel("Places Disponibles:" + AffichageEvent.rec.getNbreStand());
            Button insc = new Button("s'inscrire");

            ////////Affichage Date/////////////////
            SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat Heure = new SimpleDateFormat("HH:mm");

            String d = Date.format(AffichageEvent.rec.getDateEvent());
            String h = Heure.format(AffichageEvent.rec.getDateEvent());
            Label date = new Label("Date:" + d + " à " + h + "h");
            ///////////////////////

            imgs = URLImage.createToStorage(enc, AffichageEvent.rec.getUrlafficheevent(), url, URLImage.RESIZE_SCALE);
            imgs = imgs.scaled(350, 250);
            imgv = new ImageViewer();
            imgv.setImage(imgs);

            C1.add(imgv);

            C1.add(nomE);
            C1.add(date);

            C1.add(desc);

            C1.add(nbrP);

            C1.add(lieu);
            C2.add(insc);

            f.add(C1);
            f.add(C2);

            insc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (AffichageEvent.rec.getNbrePlace() <= 0) {
                        Dialog.show("Info!!", "Stock Epuisé!!", "OK", null);

                    } else {

                        //Form f3 = new Inscription().getF3();
                        Inscription inscri = new Inscription();
                        Inscription.idevent = rec.getIdEvent();
                        inscri.getF3().show();
                        //Toolbar tb = inscri.getF3().getToolbar();
                        //inscri.getF3().show();
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
