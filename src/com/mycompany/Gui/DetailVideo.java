/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.io.FileSystemStorage;
import java.io.InputStream;
import com.codename1.ui.util.Resources;
import com.codename1.components.MediaPlayer;
import com.codename1.components.ShareButton;
import com.codename1.components.ToastBar;
import com.codename1.io.BufferedInputStream;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;
import com.codename1.io.Log;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Commentaire;
import com.mycompany.Entity.Rating;
import com.mycompany.Entity.Videodiy;
import static com.mycompany.Gui.ValidVideoListClient.selectedVideodiy;
import static com.mycompany.Entity.User.user;
import com.mycompany.Service.ServiceVideo;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class DetailVideo {

    Image playIcon;
    TableLayout tableLayout = new TableLayout(3, 1);
    Form detailForm = new Form("Detail Video",tableLayout);
    Container commentsContainer = new Container(BoxLayout.y());

    Label commentUserLabel = new Label();
    ServiceVideo serviceVideo = new ServiceVideo();
    Container rateShareContainer = new Container(new GridLayout(1, 1));

    Button commentButton = new Button("Poster");
    Button partageButton = new Button("Partager");
    int rateVal;
    static int idVideo = ValidVideoListClient.selectedVideodiy.getIdVideo();
    Rating rating = serviceVideo.getRatingValList(idVideo, user.getId());
    Button commentList = new Button("voir les commentaires");
 
    double ratingVal = rating.getRating();
    private Resources theme;

    public DetailVideo() {

        theme = UIManager.initFirstTheme("/theme");
        detailForm.setScrollableY(true);
        Container titreContainer = new Container();

        TableLayout commenttableLayout = new TableLayout(1, 2);
        Container addcommentContainer = new Container(commenttableLayout);
        Image commentImage = theme.getImage("chat.png");
        TextField commenTextField = new TextField("", "Commenter");
        ImageViewer commentImageViewer = new ImageViewer(commentImage);
        addcommentContainer.add(commenttableLayout.createConstraint().widthPercentage(85), commenTextField).add(commentImageViewer);

        String videoName = ValidVideoListClient.selectedVideodiy.getVideo();
        Label titreLabel = new Label(ValidVideoListClient.selectedVideodiy.getTitre());
        titreLabel.getAllStyles().setFgColor(0x910b0b);
        titreLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
        Label desclLabel = new Label(ValidVideoListClient.selectedVideodiy.getDescriptionVideo());
        desclLabel.getAllStyles().setFgColor(0x383535);
        desclLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        commentList.getAllStyles().setBorder(Border.createEmpty(), true);
        commentList.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        commentList.getAllStyles().setFgColor(0xffffff);

        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        starRank.setProgress((int) ratingVal);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));

        starRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                rateVal = starRank.getProgress();
                System.out.println("rate value :" + rateVal);
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://127.0.0.1:8000/api/addRating/"
                        + ValidVideoListClient.selectedVideodiy.getIdVideo()
                        + "/" + rateVal
                        + "/"+ user.getId()
                );
                System.out.println("id user"+user.getId());
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        System.out.println("We got response for EDIT action!");
                    }
                });
                con.setFailSilently(true);
                NetworkManager.getInstance().addToQueueAndWait(con);
                 ToastBar.showMessage("Note Ajoutée ", FontImage.MATERIAL_DONE);

            }
        });

        Image shareImage = theme.getImage("Facebook-Share.png");

        ImageViewer sharImageViewer = new ImageViewer(shareImage);
//        ShareButton sharefbButton =new ShareButton();
//        sharefbButton.setText("Partager");
//        sharefbButton.setTextToShare("aaaaaaaaaa");
//  
//        detailForm.add(sharefbButton);

        rateShareContainer.add(sharImageViewer);

        sharImageViewer.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    String video = "file://C:/xampp/htdocs/Medina_VersionFinale_web/web/uploads/videos/" + videoName;
                    String titre = ValidVideoListClient.selectedVideodiy.getTitre();
                    String description = ValidVideoListClient.selectedVideodiy.getDescriptionVideo();

                    String token = "EAAZAZAuxum0X0BAMhrnPU2EYlnkinZA7zH8kc2H65hQ8kmSuczOse2a33RkfxzOkvkLaT2bcubZCsvtQ1IsD97EuZC4mME3XdFppQmnS4bAFZAzTXsqj60WqSkIKrQ4BFYVk5CatafXYRxM34mR8rlOXLg4HrN9ls67oWBEIw6SK6aZCX1i6hXX5Qe2yrepAQV2TxPMGhZBcIQZDZD";

                    FacebookClient fb = new DefaultFacebookClient(token);

                    InputStream fs = FileSystemStorage.getInstance().openInputStream(video);
                    FacebookType publishPhotoResponse = fb.publish("me/videos", FacebookType.class,
                            BinaryAttachment.with(video, fs),
                            Parameter.with("message", titre + " " + description));
                    ToastBar.showMessage("video", FontImage.MATERIAL_DONE);
                    System.out.println("video partagerrrrrr");

                } catch (IOException ex) {
                }
            }
        });
        Display.getInstance().callSerially(() -> {
            try {
                Media video = MediaManager.createMedia("file://C:/xampp/htdocs/Medina_VersionFinale_web/web/uploads/videos/" + videoName, true);
                MediaPlayer videoPlayer = new MediaPlayer(video);
                videoPlayer.setBackIcon(playIcon);
                videoPlayer.setAutoplay(false);
                detailForm.add(tableLayout.createConstraint().heightPercentage(40).widthPercentage(100), videoPlayer).add(titreLabel).add(desclLabel).add(FlowLayout.encloseCenter(starRank)).add(rateShareContainer).add(addcommentContainer).add(commentList)/*.add(commentsContainer)*/;
            } catch (IOException ex) {
            }
        });

        commentImageViewer.addPointerReleasedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://127.0.0.1:8000/api/addComment/"
                        + ValidVideoListClient.selectedVideodiy.getIdVideo()
                        + "/" + commenTextField.getText()
                        + "/" + user.getId()
                );
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        System.out.println("We got response for EDIT action!");
                    }
                });
                con.setFailSilently(true);
                NetworkManager.getInstance().addToQueueAndWait(con);
                 ToastBar.showMessage("commentaire ajouté", FontImage.MATERIAL_DONE);

            }
        });
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://127.0.0.1:8000/api/addComment/"
                        + ValidVideoListClient.selectedVideodiy.getIdVideo()
                        + "/" + commenTextField.getText()
                        + "/" + user.getId()
                );
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        System.out.println("We got response for EDIT action!");
                    }
                });
                con.setFailSilently(true);
                NetworkManager.getInstance().addToQueueAndWait(con);

            }
        });
//      
        detailForm.getToolbar().addCommandToLeftBar("<-", null, (ev) -> {
            ValidVideoListClient validVideoListClient;
            ArrayList<Videodiy> videolist;
            ServiceVideo serviceVideo = new ServiceVideo();
            videolist = serviceVideo.getVideosList();
            System.err.println("btnAfficheVideo listener triggered => will callconstructor");
            validVideoListClient = new ValidVideoListClient(videolist);
            validVideoListClient.getF().show();
        });

        commentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Form commentForm = new CommentList().getCommentListForm();

                commentForm.getToolbar().addCommandToLeftBar("<-", null, (ev) -> {
                    DetailVideo detailVideo = new DetailVideo();
                    detailVideo.getDetailForm().show();

                });

                commentForm.show();
            }
        });
        for (Commentaire item : serviceVideo.getCommentList(idVideo)) {

            Container cellContainer = new Container();
            Container userContainer = new Container(new GridLayout(1, 1));
            Container commentContainer = new Container();

            Label userNameLabel = new Label();
            FontImage.setMaterialIcon(userNameLabel, FontImage.MATERIAL_PERSON);

            Label commentLabel = new Label();
            userNameLabel.setText(item.getIdUser().getNomUser());
            userContainer.add(userNameLabel);
            commentLabel.setText(item.getContenuCommentaire());
            commentContainer.add(commentLabel);

            cellContainer.add(userContainer).add(commentContainer);
            commentsContainer.add(cellContainer);

        }
    }

    public Form getDetailForm() {
        return detailForm;
    }

    public void setDetailForm(Form detailForm) {
        this.detailForm = detailForm;
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(10);

        rateVal = starRank.getProgress();

        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }

}
