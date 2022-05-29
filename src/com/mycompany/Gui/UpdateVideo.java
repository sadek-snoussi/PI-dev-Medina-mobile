/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.MediaPlayer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.table.TableLayout;
import java.io.IOException;

/**
 *
 * @author hp
 */
public class UpdateVideo {

    TableLayout tableLayout = new TableLayout(3, 1);
    Form updateForm = new Form("update Form", tableLayout);
    Image playIcon;
    Button updateButton = new Button("Modifier");
    TextField titleTextField = new TextField("", "Titre");
    TextField descTextField = new TextField("", "Description");

    public UpdateVideo() {

        Display.getInstance().callSerially(() -> {
            try {
                Media video = MediaManager.createMedia("file://C:/xampp/htdocs/Medina_VersionFinale_web/web/uploads/videos/" + DetailVideoPartenaire.selectedVideodiy.getVideo(), true);
                MediaPlayer videoPlayer = new MediaPlayer(video);
                videoPlayer.setBackIcon(playIcon);
                videoPlayer.setAutoplay(false);
                updateForm.add(tableLayout.createConstraint().heightPercentage(50).widthPercentage(100), videoPlayer).add(titleTextField).add(descTextField).add(updateButton);
                updateForm.show();
            } catch (IOException ex) {
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/updateVideo/"
                        + videoPartenaireList.selectedVideodiy.getIdVideo()
                        + "/" + titleTextField.getText()
                        + "/" + descTextField.getText()
                );
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        System.out.println("We got response for EDIT action!");
                    }
                });
                con.setFailSilently(true);
                NetworkManager.getInstance().addToQueueAndWait(con);

                videoPartenaireList vidPartenaireList = new videoPartenaireList();
                vidPartenaireList.getF().show();

            }
        });

    }

    public Form getUpdateForm() {
        return updateForm;
    }

    public void setUpdateForm(Form updateForm) {
        this.updateForm = updateForm;
    }

}
