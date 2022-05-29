/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entity.Commentaire;
import com.mycompany.Entity.Rating;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public class ServiceVideo {

    ArrayList<Videodiy> listVideo = new ArrayList<Videodiy>();
    ArrayList<Commentaire> listCommentaires = new ArrayList<Commentaire>();
    ArrayList<Rating> ratingList = new ArrayList<Rating>();
    Rating rating = new Rating();

    public ArrayList<Videodiy> getVideoList(String json) {
        ArrayList<Videodiy> listVideodiys = new ArrayList<Videodiy>();
        try {
            System.out.println(json);
            JSONParser j = new JSONParser();
            Map<String, Object> videosMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(videosMap);
            List<Map<String, Object>> list = (List<Map<String, Object>>) videosMap.get("root");
            for (Map<String, Object> obj : list) {

                Videodiy videodiy = new Videodiy();
                float id = Float.parseFloat(obj.get("idvideo").toString());
                System.out.println(id);
                videodiy.setIdVideo((int) id);
                videodiy.setTitre(obj.get("titre").toString());

               // if (obj.get("avgRating") == null) {
                  //  videodiy.setAvgRating(0.0);
               // } else {
                    float avgRating = Float.parseFloat(obj.get("avgRating").toString());
                    videodiy.setAvgRating((double) avgRating);

              //  }

                videodiy.setDescriptionVideo(obj.get("descriptionvideo").toString());
                videodiy.setVideo(obj.get("video").toString());
                videodiy.setImageFromVideo(obj.get("imageFromVideo").toString());
                listVideodiys.add(videodiy);
            }
        } catch (IOException ex) {
        }
        System.out.println(listVideodiys);
        return listVideodiys;

    }

     public ArrayList<Videodiy> getVideoProList(String json) {
        ArrayList<Videodiy> listVideodiys = new ArrayList<Videodiy>();
        try {
            System.out.println(json);
            JSONParser j = new JSONParser();
            Map<String, Object> videosMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(videosMap);
            List<Map<String, Object>> list = (List<Map<String, Object>>) videosMap.get("root");
            for (Map<String, Object> obj : list) {

                Videodiy videodiy = new Videodiy();
                float id = Float.parseFloat(obj.get("idvideo").toString());
                System.out.println(id);
                videodiy.setIdVideo((int) id);
                videodiy.setTitre(obj.get("titre").toString());
                float avgRating = Float.parseFloat(obj.get("avgRating").toString());
                
                videodiy.setAvgRating((double) avgRating);

                videodiy.setDescriptionVideo(obj.get("descriptionvideo").toString());
                videodiy.setVideo(obj.get("video").toString());
                videodiy.setImageFromVideo(obj.get("imageFromVideo").toString());
                 float valid = Float.parseFloat(obj.get("valid").toString());
                
                videodiy.setValid((int)valid);
                listVideodiys.add(videodiy);
            }
        } catch (IOException ex) {
        }
        System.out.println(listVideodiys);
        return listVideodiys;

    }

    public ArrayList<Videodiy> getVideosList() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allVideo");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVideo serviceVideo = new ServiceVideo();
                listVideo = serviceVideo.getVideoList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVideo;
    }
    
       public ArrayList<Videodiy> gettopRatedVideoList() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/topRated");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVideo serviceVideo = new ServiceVideo();
                listVideo = serviceVideo.getVideoList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVideo;
    }
    
    
     public ArrayList<Videodiy> getVideosProoList(int iduser) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allProVideo/"+iduser);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVideo serviceVideo = new ServiceVideo();
                listVideo = serviceVideo.getVideoProList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVideo;
    }

    public ArrayList<Videodiy> getSearchList(String tag) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/search/" + tag);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVideo serviceVideo = new ServiceVideo();
                listVideo = serviceVideo.getVideoList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVideo;
    }

    public ArrayList<Commentaire> getListCommentaires(String json) {

        ArrayList<Commentaire> listCommentaires = new ArrayList<Commentaire>();
        Videodiy videodiy = new Videodiy();
        User u = new User();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> panier = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(panier);

            List<Map<String, Object>> list = (List<Map<String, Object>>) panier.get("root");

            for (Map<String, Object> obj : list) {
                // Panier pn = new Panier();
                Commentaire commentaire = new Commentaire();
                float id = Float.parseFloat(obj.get("idcommentaire").toString());
                commentaire.setIdcommentaire((int) id);
                commentaire.setContenuCommentaire(obj.get("contenucommentaire").toString());

                // here is the second MAP
                Map<String, Object> listRecup = null;
                // USER is the foreign key 
                if (obj.get("idUser") != null) {
                    listRecup = (Map<String, Object>) obj.get("idUser");
                    u.setId((int) Float.parseFloat(listRecup.get("id").toString()));
                    u.setNomUser(listRecup.get("nomuser").toString());

                    commentaire.setIdUser(u);
                }
                //aussi Produit est un cle etrangers
                if (obj.get("idVideo") != null) {
                    listRecup = (Map<String, Object>) obj.get("idVideo");
                    videodiy.setIdVideo((int) Float.parseFloat(listRecup.get("idvideo").toString()));
                    commentaire.setIdVideo(videodiy);

                }

                listCommentaires.add(commentaire);

            }

        } catch (IOException ex) {
        }
        //System.out.println(listPanier);
        return listCommentaires;

    }

    public ArrayList<Commentaire> getCommentList(int idVideo) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allComment/" + idVideo);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVideo serviceVideo = new ServiceVideo();
                listCommentaires = serviceVideo.getListCommentaires(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listCommentaires;
    }

    public Rating getRatingList(String json) {

        ArrayList<Rating> ratingList = new ArrayList<Rating>();
        Videodiy videodiy = new Videodiy();
        User u = new User();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> panier = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println("aaaaaaaaa" + panier);

            List<Map<String, Object>> list = (List<Map<String, Object>>) panier.get("root");
            System.out.println("9bal boucle for");
            

            float id = Float.parseFloat(panier.get("ratingId").toString());

            rating.setRatingId((int) id);
            float ratingVal = Float.parseFloat(panier.get("ratingValue").toString());
            rating.setRating((double) ratingVal);
            // here is the second MAP
            Map<String, Object> listRecup = null;
            // USER is the foreign key 
            if (panier.get("user") != null) {
                listRecup = (Map<String, Object>) panier.get("user");
                u.setId((int) Float.parseFloat(listRecup.get("id").toString()));
                rating.setUserId(u);
            }
            //aussi Produit est un cle etrangers
            if (panier.get("video") != null) {
                listRecup = (Map<String, Object>) panier.get("video");
                videodiy.setIdVideo((int) Float.parseFloat(listRecup.get("idvideo").toString()));
                rating.setVideoId(videodiy);

            }

        } catch (IOException ex) {
        }
        //System.out.println(listPanier);
        return rating;

    }

    public Rating getRatingValList(int idVideo, int idUser) {

        ConnectionRequest con = new ConnectionRequest();
        
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/ratingVal/" + idVideo + "/" + idUser);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVideo serviceVideo = new ServiceVideo();
                rating = serviceVideo.getRatingList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return rating;
    }

}
