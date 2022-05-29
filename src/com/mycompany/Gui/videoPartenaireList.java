/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MediaPlayer;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;
import static com.mycompany.Entity.User.user;
import com.mycompany.Service.ProductService;


/**
 *
 * @author hp
 */
public class videoPartenaireList {

    Form f;
    Form f1;

    Image playIcon;
    public static Videodiy selectedVideodiy = new Videodiy();
    TableLayout tableLayout = new TableLayout(3, 1);

    public videoPartenaireList() {
        try {
            f = new Form("Mes videos",BoxLayout.y());

            ServiceVideo serviceVideo = new ServiceVideo();

            for (Videodiy item : serviceVideo.getVideosProoList(user.getId())) {

                Container cellContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label validLabel = new Label();
                int validValue = item.getValid();

                if (validValue == 0) {
                    validLabel.setText("En cours");
                    validLabel.getAllStyles().setFgColor(0xF27935);
                    validLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

                } else if (validValue == 1) {
                    validLabel.setText("Validée");
                    validLabel.getAllStyles().setFgColor(0x00B16A);

                    validLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

                } else {

                    validLabel.setText("Rejetée");
                    validLabel.getAllStyles().setFgColor(0xD91E18);
                    validLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

                }

                Label titre = new Label("Titre:");
            titre.getAllStyles().setFgColor(0x910b0b);
            titre.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

            Label titreLabel = new Label(item.getTitre());
            titreLabel.getAllStyles().setFgColor(0x383535);

            titreLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

            Label desc = new Label("Description:");
            desc.getAllStyles().setFgColor(0x910b0b);
            desc.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

            Label desclLabel = new Label(item.getDescriptionVideo());
            desclLabel.getAllStyles().setFgColor(0x383535);
            desclLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

                EncodedImage encImg = EncodedImage.create("/giphy.gif");
                ImageViewer imgV = new ImageViewer();

                String url = "http://localhost/Medina_VersionFinale_web/web/uploads/ImgVideo/" + item.getImageFromVideo();
                Image img = URLImage.createToStorage(encImg, item.getImageFromVideo(), url);
                img = img.scaled(280, 200);
                imgV.setImage(img);

                cellContainer.add(imgV);
                cellContainer.add(titreLabel);
                cellContainer.add(desclLabel);
                cellContainer.add(validLabel);
                f.add(cellContainer);
                f.add(new Slider());

                titreLabel.addPointerPressedListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        selectedVideodiy = item;
                        Form detailForm = new DetailVideoPartenaire().getDetailForm();

                        detailForm.getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, (ev) -> {
                          videoPartenaireList vidList=new videoPartenaireList();
//  HomeForm h = new HomeForm();
                            vidList.getF().show();
                        });

                        detailForm.show();

                    }
                });

                f.show();
            }
            f.getToolbar().addCommandToLeftBar("back", null, (ev) -> {
                try {
                    ProductService serv = new ProductService();
                    
                    AllProduct Allp=new AllProduct(serv.getList());
                    Allp.getF().show();
                } catch (IOException ex) {
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
