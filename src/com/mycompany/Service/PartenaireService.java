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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import com.mycompany.Entity.Videodiy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author amalb
 */
public class PartenaireService {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Produit> produits = new ArrayList<>();
    ArrayList<Videodiy> videos = new ArrayList<>();

    public ArrayList<User> listPartenaires() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/Partenaires/all");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                PartenaireService ps = new PartenaireService();
                users = ps.getListPartenaires(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return users;
    }

    public ArrayList<User> getListPartenaires(String json) {

        ArrayList<User> listPartenaires = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> partenaires = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) partenaires.get("root");

            for (Map<String, Object> obj : list) {
                User user = new User();

                float id = Float.parseFloat(obj.get("id").toString());
                System.out.println(id);
                user.setId((int) id);
                //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
                user.setUsername(obj.get("username").toString());
                user.setNomUser(obj.get("nomuser").toString());
                user.setPrenomUser(obj.get("prenomuser").toString());
                user.setNomEntreprisePro(obj.get("nomentreprisepro") != null ? obj.get("nomentreprisepro").toString() : null);
                user.setEmail(obj.get("email").toString());
                user.setGradePro(obj.get("gradepro") != null ? obj.get("gradepro").toString() : null);
                user.setSpecialitePart(obj.get("specialitepart") != null ? obj.get("specialitepart").toString() : null);
                user.setTelBureauPro(obj.get("telbureaupro") != null ? obj.get("telbureaupro").toString() : null);
                user.setUrlLogoPro(obj.get("urllogopro") != null ? obj.get("urllogopro").toString() : null);
                user.setAdresse(obj.get("adresse") != null ? obj.get("adresse").toString() : null);

                user.setTypeUser(obj.get("typeuser").toString());

                listPartenaires.add(user);

            }

        } catch (IOException ex) {
        }
        System.out.println(listPartenaires);
        return listPartenaires;

    }

    public void DemandePartPro(User user) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/DemanderPart/Pro/"
                + user.getId() + "?nomEntreprisePro="
                + user.getNomEntreprisePro() + "&telBureauPro="
                + user.getTelBureauPro() + "&specialitePart="
                + user.getSpecialitePart() + "&gradePro="
                + user.getGradePro() + "&adresse=" + user.getAdresse()
                + "&urlLogoPro=" + user.getUrlLogoPro() + "&email=" + user.getEmail() + "&typeUser=pro";

        con.setUrl(Url);
        con.addResponseListener((e) -> {

            String str = new String(con.getResponseData());

        });
        con.setFailSilently(true);
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void DemandePartFreelancer(User user) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/DemanderPart/Free/"
                + user.getId() + "?telBureauPro="
                + user.getTelBureauPro() + "&specialitePart="
                + user.getSpecialitePart() + "&urlLogoPro=" + user.getUrlLogoPro()
                + "&typeUser=freelancer";

        con.setUrl(Url);
        con.addResponseListener((e) -> {

            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void ModifierProfilePro(User user) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/modifProfilePro/" + user.getId() + "?nomUser="
                + user.getNomUser() + "&email=" + user.getEmail() + "&prenomUser=" + user.getPrenomUser()
                + "&nomEntreprisePro=" + user.getNomEntreprisePro() + "&telBureauPro=" + user.getTelBureauPro()
                + "&adresse=" + user.getAdresse() + "&gradePro=" + user.getGradePro() + "&specialitePart=" + user.getSpecialitePart();

        con.setUrl(Url);
        con.addResponseListener((e) -> {

            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void ModifierProfileFree(User user) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/modifProfileFree/" + user.getId() + "?nomUser="
                + user.getNomUser() + "&email="
                + user.getEmail() + "&prenomUser=" + user.getPrenomUser()
                + "&telBureauPro=" + user.getTelBureauPro()
                + "&specialitePart=" + user.getSpecialitePart();

        con.setUrl(Url);
        con.addResponseListener((e) -> {

            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public ArrayList<Produit> listProduitByPart() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/produits/all/" + User.user.getId());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                PartenaireService ps = new PartenaireService();
                produits = ps.getListProduits(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return produits;

    }

    public ArrayList<Produit> getListProduits(String json) {

        ArrayList<Produit> listProduits = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> list = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> mapproduits = (List<Map<String, Object>>) list.get("root");

            for (Map<String, Object> obj : mapproduits) {
                Produit produit = new Produit();

                float id = Float.parseFloat(obj.get("idproduit").toString());
                float qtedispoproduit = Float.parseFloat(obj.get("qtedispoproduit").toString());
                float qtevenduproduit = Float.parseFloat(obj.get("qtevenduproduit").toString());
                produit.setIdProduit((int) id);
                produit.setQteDispoProduit((int) qtedispoproduit);
                produit.setQteVenduProduit((int) qtevenduproduit);
                Map<String, Object> date = null;
                date = (Map<String, Object>) obj.get("dateexpoproduit");
                Date londdate = new Date((long) Float.parseFloat(date.get("timestamp").toString()) * 1000);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String d = formatter.format(londdate);
                produit.setDateExpoProduit(londdate);
                User u = new User();
                // here is the second MAP
                Map<String, Object> listRecupUser = null;
                // USER is the foreign key 
                if (obj.get("user") != null) {
                    listRecupUser = (Map<String, Object>) obj.get("user");
                    u.setId((int) Float.parseFloat(listRecupUser.get("id").toString()));
                    produit.setIdUser(u);
                }

                listProduits.add(produit);

            }

        } catch (IOException ex) {
        }
        System.out.println(listProduits);
        return listProduits;

    }

    public ArrayList<Videodiy> listVideosByPart() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/videos/all/" + User.user.getId());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                PartenaireService ps = new PartenaireService();
                videos = ps.getListVideos(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return videos;

    }

    public ArrayList<Videodiy> getListVideos(String json) {

        ArrayList<Videodiy> listVideos = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> list = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> mapvideos = (List<Map<String, Object>>) list.get("root");

            for (Map<String, Object> obj : mapvideos) {
                Videodiy video = new Videodiy();

                float id = Float.parseFloat(obj.get("idvideo").toString());
                video.setIdVideo((int) id);
                
                Map<String, Object> date = null;
                date = (Map<String, Object>) obj.get("dateexpovideo");
                Date londdate = new Date((long) Float.parseFloat(date.get("timestamp").toString()) * 1000);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String d = formatter.format(londdate);
                video.setDateExpoVideo(londdate);
                User u = new User();
                // here is the second MAP
                Map<String, Object> listRecupUser = null;
                // USER is the foreign key 
                if (obj.get("user") != null) {
                    listRecupUser = (Map<String, Object>) obj.get("user");
                    u.setId((int) Float.parseFloat(listRecupUser.get("id").toString()));
                    video.setIdUser(u);
                }
                listVideos.add(video);

            }

        } catch (IOException ex) {
        }
        System.out.println(listVideos);
        return listVideos;

    }
}
