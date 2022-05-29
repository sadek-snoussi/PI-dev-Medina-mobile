/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.InteractionDialog;
import com.codename1.components.MediaPlayer;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Videodiy;
import static com.mycompany.Gui.videoPartenaireList.selectedVideodiy;
import com.mycompany.Service.ServiceVideo;
import java.io.IOException;

/**
 *
 * @author hp
 */
public class DetailVideoPartenaire {

    Image playIcon;
    TableLayout tableLayout = new TableLayout(3, 1);
    Form detailForm = new Form(tableLayout);
    public static Videodiy selectedVideodiy = new Videodiy();
    private Resources theme;

    public DetailVideoPartenaire() {

        theme = UIManager.initFirstTheme("/theme");
        String videoName = videoPartenaireList.selectedVideodiy.getVideo();
        Label titreLabel = new Label("Titre:" + videoPartenaireList.selectedVideodiy.getTitre());
        Label desclLabel = new Label("Description:" + videoPartenaireList.selectedVideodiy.getDescriptionVideo());

        titreLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        desclLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        Button updateButton = new Button("Modifier");
        // updateButton.getAllStyles().setFgColor(0x03660e);
        updateButton.getAllStyles().setBgColor(0x03660e);
        Button deleteButton = new Button("Supprimer");
        FontImage.setMaterialIcon(deleteButton, FontImage.MATERIAL_DELETE);
        FontImage.setMaterialIcon(updateButton, FontImage.MATERIAL_DESCRIPTION);

        deleteButton.getAllStyles().setBgColor(0x910000);

        Display.getInstance().callSerially(() -> {
            try {
                Media video = MediaManager.createMedia("file://C:/xampp/htdocs/Medina_VersionFinale_web/web/uploads/videos/" + videoName, true);
                MediaPlayer videoPlayer = new MediaPlayer(video);
                videoPlayer.setBackIcon(playIcon);
                videoPlayer.setAutoplay(false);
                detailForm.add(tableLayout.createConstraint().heightPercentage(50).widthPercentage(100), videoPlayer).add(titreLabel).add(desclLabel).add(updateButton).add(deleteButton);
            } catch (IOException ex) {
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                   Dialog dlg = new Dialog("Warning !!");
               
                // dlg.setLayout(new BorderLayout());
                dlg.add(new SpanLabel("etes vous sur de supprimer votre video?"));

                
                Button close = new Button("Non");
                Button delete = new Button("Oui");

                close.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        dlg.dispose();
                    }
                });
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                             ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/deleteVideo/" + videoPartenaireList.selectedVideodiy.getIdVideo());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        System.out.println("We got response for deletion action!");
                    }
                });
                con.setFailSilently(true);
                NetworkManager.getInstance().addToQueueAndWait(con);
                videoPartenaireList vidPartenaireList = new videoPartenaireList();
                vidPartenaireList.getF().show();
                    }
                });
              dlg.add(delete);
                dlg.add(close);
                
              //  dlg.setHeight(200);
             //    dlg.setPreferredSize(new Dimension(250, 200));
                // dlg.addComponent( delete);
               // Dimension pre = dlg.getContentPane().getPreferredSize();
                dlg.show();
                
           

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                selectedVideodiy = videoPartenaireList.selectedVideodiy;
                Form updateForm = new UpdateVideo().getUpdateForm();

                detailForm.getToolbar().addCommandToRightBar("back", null, (ev) -> {
                    videoPartenaireList vList = new videoPartenaireList();
                    vList.getF().show();
                  //  HomeForm h = new HomeForm();
                  //  h.getF().show();
                });

                detailForm.show();

            }
        });

       
    }

    public Form getDetailForm() {
        return detailForm;
    }

    public void setDetailForm(Form detailForm) {
        this.detailForm = detailForm;
    }

}
