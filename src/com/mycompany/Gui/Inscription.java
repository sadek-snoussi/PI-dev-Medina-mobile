/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Event;
import com.mycompany.Entity.User;
import com.mycompany.Entity.UserEvent;
import com.mycompany.Service.ServiceEvent;
import com.mycompany.Service.UserService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ASUS I7
 */
public class Inscription {

    Form f3;
    Label nomE;
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    Form freussi;
    Label reussite;
    public static int idevent = 0;
    private Resources theme;

    public Inscription() {

        try {
            f3 = new Form("Inscription");
            
            
            theme = UIManager.initFirstTheme("/theme");
            
            f3.getToolbar().addCommandToLeftBar(" ",theme.getImage("back-command.png"), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    detailEvent detEvent=new detailEvent();
                    detEvent.getF().show();
                            
                }
            });
            
            
            
            freussi = new Form("Succes", BoxLayout.y());
            Label reussi = new Label("inscription Effectueé avec succes");

            ServiceEvent sr = new ServiceEvent();
            ArrayList<Event> event = sr.getListEvent();

            nomE = new Label(event.get(0).getNomEvent());
            String url = "http://localhost//Medina_VersionFinale//web//uploads//img//" + event.get(0).getUrlafficheevent();
            enc = EncodedImage.create("/load.png");
            imgs = URLImage.createToStorage(enc, "ons", url, URLImage.RESIZE_SCALE_TO_FILL);
            imgv = new ImageViewer(imgs);

            TextField t1 = new TextField("", "nom");
            TextField t2 = new TextField("", "Prenom");
            TextField t3 = new TextField("", "Email");
            Button btn = new Button("Valider");
            
            Container c = new Container(new FlowLayout(Component.CENTER));
            c.add(nomE);
            c.add(t1);
            c.add(t2);
            c.add(t3);
            c.add(btn);

        
            freussi.add(reussi);
            
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    
                    UserEvent inscri = new UserEvent();
                    ServiceEvent s = new ServiceEvent();
                    inscri.setNom(t1.getText());
                    inscri.setPrenom(t2.getText());
                    inscri.setAdressemail(t3.getText());

                    Event e = new Event();
                    e.setIdEvent(idevent);
                    UserService us = new UserService();

                    User u = User.user;
                    inscri.setUserId(u);
                    inscri.setEventId(e);
                    
                    s.inscriEvent(inscri);
                    
                    Dialog.show("Inscription", "Inscription Effectuée avec succes", "OK", null);
                    AffichageEvent ae=new AffichageEvent();
                    ae.getF().show();
//                    Message m = new Message("Body of message");
//                    Display.getInstance().sendMessage(new String[]{inscri.getAdressemail()}, "Zanimaux", m);
                    // freussi.show();
                    
                    
                    System.out.println("le id event est " + idevent);
                }

            });

            //f3.add(imgv);
//        f3.add(t1);
//        f3.add(t2);
//        f3.add(t3);
//        f3.add(btn);
            f3.add(c);
        } catch (IOException ex) {

        }

    }

    public Form getF3() {
        return f3;
    }

    public void setF3(Form f3) {
        this.f3 = f3;
    }

}
