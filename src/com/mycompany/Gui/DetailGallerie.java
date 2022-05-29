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
import com.codename1.ui.Slider;
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
public class DetailGallerie {

    Form f;
    private Resources theme;

    ImageViewer imgv;
    EncodedImage enc;
    Image imgs;

    public DetailGallerie() {
        
            listPartenaires lp = new listPartenaires();
        ModifierProfileClient profile = new ModifierProfileClient();
        choosePartenariat demande = new choosePartenariat();
        signin signin = new signin();
        ModifierProfilePro profilePro = new ModifierProfilePro();
        ModifierProfileFree profileFree = new ModifierProfileFree();
        ProductService serv = new ProductService();

        try {
            Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

            f = new Form("Details ");

            String url = "http://localhost/Medina_VersionFinale_web/web//uploads/imgGallerie/" + AfficheGallerie.Gals.getImgGallerie();
            enc = EncodedImage.create("/giphy.gif");
            imgs = URLImage.createToStorage(enc, AfficheGallerie.Gals.getImgGallerie(), url, URLImage.RESIZE_SCALE).scaled(350, 250);
            imgs = imgs.scaled(350, 250);

            SpanLabel TitreGallerie = new SpanLabel("Titre : " + AfficheGallerie.Gals.getTitreGallerie());
            SpanLabel TypeGallerie = new SpanLabel("Type : " + AfficheGallerie.Gals.getTypeGallerie());
            SpanLabel LieuGallerie = new SpanLabel("Lieu : " + AfficheGallerie.Gals.getLieuGallerie());
            SpanLabel TitreDesc = new SpanLabel("Description : ");
            SpanLabel DescGallerie = new SpanLabel(AfficheGallerie.Gals.getDescription());

            imgv = new ImageViewer(imgs);

            C1.add(imgv);

            C1.add(TitreGallerie);
            C1.add(TypeGallerie);
            C1.add(LieuGallerie);
            C1.add(new Slider());
            C1.add(TitreDesc);
            C2.add(DescGallerie);

            f.add(C1);
            f.add(C2);
        } catch (IOException ex) {
        }
        
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// Navigation Entre les PAGES /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar tb = f.getToolbar();
             f.getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, (ev)->{
                 AfficheGallerie bnp = new AfficheGallerie();
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
