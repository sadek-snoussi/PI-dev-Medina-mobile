/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.SpanLabel;
import com.codename1.contacts.Contact;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;

/**
 *
 * @author amalb
 */
public class choosePartenariat {

    Form demandePart;
    Button Pro;
    SpanLabel lblPro;
    SpanLabel lblFree;
    Button Free;
    demandePartFree demandeFree = new demandePartFree();
    demandePartPro demandePro = new demandePartPro();

    public choosePartenariat() {

        demandePart = new Form("Demander Partenariat", new FlowLayout(Component.CENTER, Component.CENTER));
        Pro = new Button("Professionnel");
        SpanLabel lblPro=new SpanLabel();
        SpanLabel lblFree=new SpanLabel();
        Free = new Button("Freelancer");
        Container c = new Container();
        c.add(lblPro);
        c.add(Pro);
        c.add(lblFree);
        c.add(Free);

        demandePart.add(c);

        Pro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                demandePro.getDemandePartPro().show();
            }
        });

        Free.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                demandeFree.getDemandePartFree().show();
            }
        });

    }

    public Form getDemandePart() {
        return demandePart;
    }

    public void setDemandePart(Form demandePart) {
        this.demandePart = demandePart;
    }

}
