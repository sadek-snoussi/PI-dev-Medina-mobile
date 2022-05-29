/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Event;

import com.mycompany.Entity.Stand;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.ServiceStand;
import java.util.ArrayList;

/**
 *
 * @author ASUS I7
 */
public class AffichageStandDispo {
        listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
           ModifierProfilePro profilePro = new ModifierProfilePro();
         ModifierProfileFree profileFree = new ModifierProfileFree();
         ProductService serv=new ProductService();

    Form f;
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;

    public static Stand rec = new Stand();
    public static int idevent = 0;
    private Resources theme;

    public AffichageStandDispo() {
        theme = UIManager.initFirstTheme("/theme");
        f = new Form("Stand Disponibles", BoxLayout.y());
        
        
                        f.getToolbar().addCommandToLeftBar(" ",theme.getImage("back-command.png"), e-> {

                            AffichageEventReservation affEvent=new AffichageEventReservation();
                            affEvent.getF().show();
                        });
        
        
        
        
        
        
        Form f2 = new Form("reserver Stand");

        ServiceStand serviceTask = new ServiceStand();
        ArrayList<Stand> lis = serviceTask.getListStandDispo();

        for (Stand t : lis) {

            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            Label superficie = new Label("Superficie :" + t.getSuperficieStand());

            Label emplacement = new Label("Emplacement: " + t.getEmplacementStand());
            Label prix = new Label("Prix: " + t.getPrix());
            C1.add(superficie);

            C1.add(emplacement);
            C1.add(prix);
            //  C1.add(imgv);
            Button reserver = new Button("Reserver");
            C1.add(reserver);

            C1.setLeadComponent(reserver);

            f.add(C1);
            f.add(new Slider());

            reserver.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Stand s = new Stand();
                    s.setEtat(t.getEtat());
                    s.setNumStand(t.getNumStand());
                    System.out.println(t.getNumStand());
                    Event e = new Event();
                    e.setIdEvent(idevent);
                    System.out.println("mmmmmmmmm" + idevent);
                    s.setEventstand(e);
                    //System.out.println("hhh"+e);

                    ServiceStand s1 = new ServiceStand();
                    s1.ReservationStand(s);
                    //System.out.println("kkkkkkoook");
                    Dialog.show("Reservation", "Reservation Effectuée avec succes", "OK", null);
                    AffichageEvent events = new AffichageEvent();
                    events.getF().show();
                }
            });

        }
    
   

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
