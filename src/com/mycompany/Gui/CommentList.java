/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Commentaire;
import static com.mycompany.Gui.DetailVideo.idVideo;
import com.mycompany.Service.ServiceVideo;
import javafx.scene.image.ImageView;

/**
 *
 * @author hp
 */
public class CommentList {

    ServiceVideo serviceVideo = new ServiceVideo();

    int idVideo1 = DetailVideo.idVideo;
    Form commentListForm = new Form(BoxLayout.y());
    private Resources theme;

    public CommentList() {

        theme = UIManager.initFirstTheme("/theme");

        for (Commentaire item : serviceVideo.getCommentList(idVideo1)) {

            Container cellContainer = new Container(new GridLayout(2, 1));
            Container userContainer = new Container();
            Container commentContainer = new Container();

            Label userNameLabel = new Label();

           // FontImage.setMaterialIcon(userNameLabel, FontImage.MATERIAL_ACCOUNT_CIRCLE);
            Image userImage = theme.getImage("user.png");
            ImageViewer userImageViewer = new ImageViewer(userImage);
            Label commentLabel = new Label();
            commentLabel.getAllStyles().setFgColor(0x3b4049);
            userNameLabel.setText(item.getIdUser().getNomUser());
            userNameLabel.getAllStyles().setFgColor(0x3b4049);
            commentLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));

            userContainer.add(userImageViewer).add(userNameLabel);
            commentLabel.setText(item.getContenuCommentaire());
            commentLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            commentContainer.add(commentLabel);
            cellContainer
                    .add(userContainer).add(commentContainer);
            commentListForm.add(cellContainer);
            commentListForm.add(new Slider());

        }

    }

    public Form getCommentListForm() {
        return commentListForm;
    }

    public void setCommentListForm(Form commentListForm) {
        this.commentListForm = commentListForm;
    }

}
