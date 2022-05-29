/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
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
public class DetailGuide {
       listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv = new ProductService();

    Form f;
    private Resources theme;

    ImageViewer imgv;
    EncodedImage enc;
    Image imgs;

    public DetailGuide() {

        try {
            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            f = new Form("Details ");

            String url = "http://localhost/Medina_VersionFinale_web/web/uploads/imgGuide/" + AfficheGuide.Guids.getImgGuide();
            enc = EncodedImage.create("/giphy.gif");
            imgs = URLImage.createToStorage(enc, AfficheGuide.Guids.getImgGuide(), url, URLImage.RESIZE_SCALE).scaled(350, 250);
            imgs = imgs.scaled(350, 250);

            SpanLabel NomGuide = new SpanLabel("Nom : " + AfficheGuide.Guids.getNomGuide());
            SpanLabel PrenomGuide = new SpanLabel("Prenom : " + AfficheGuide.Guids.getPrenomGuide());
            SpanLabel GouvGuide = new SpanLabel(" Gouvernerat : " + AfficheGuide.Guids.getVilleGuide());
            SpanLabel MailGuide = new SpanLabel(" Email : " + AfficheGuide.Guids.getMailguide());
            SpanLabel NumGLabel = new SpanLabel(" Tel : " + AfficheGuide.Guids.getTelGuide());

            imgv = new ImageViewer(imgs);

            C1.add(imgv);

            C1.add(NomGuide);
            C1.add(PrenomGuide);
            C1.add(GouvGuide);
            C1.add(MailGuide);
            C1.add(NumGLabel);

            f.add(C1);

        } catch (IOException ex) {
        }

          /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// Navigation Entre les PAGES /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar tb = f.getToolbar();
             f.getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, (ev)->{
                 AfficheGuide bnp = new AfficheGuide();
             bnp.getF().show();
          });
        
        

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
