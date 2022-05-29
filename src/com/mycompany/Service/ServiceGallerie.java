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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pidev.edu.souk.entities.Gallerie;

/**
 *
 * @author khali
 */
public class ServiceGallerie {

    public ArrayList<Gallerie> getListGallerie(String json) {

        ArrayList<Gallerie> listGallerie = new ArrayList<Gallerie>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> Gallerie = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(Gallerie);

            List<Map<String, Object>> list = (List<Map<String, Object>>) Gallerie.get("root"); // ALLLOOO SOFIENNNE WTTTTFFF !!!! 2 List imbriqué ?!

            for (Map<String, Object> obj : list) {
                Gallerie G = new Gallerie();

                float id = Float.parseFloat(obj.get("idgallerie").toString());
                String titregallerie = obj.get("titregallerie").toString();
                String TypeGallerie = obj.get("typegallerie").toString();
                String description = obj.get("description").toString();
                String lieugallerie = obj.get("lieugallerie").toString();
                String imgGallerie = obj.get("imggallerie").toString();

                G.setIdGallerie((int) id);
                G.setTitreGallerie(titregallerie);
                G.setDescription(description);
                G.setLieuGallerie(lieugallerie);
                G.setTypeGallerie(TypeGallerie);
                G.setImgGallerie(imgGallerie);

                System.out.println(G);
                listGallerie.add(G);

            }

        } catch (IOException ex) {
        }
        System.out.println(listGallerie);
        return listGallerie;

    }

    ArrayList<Gallerie> listGallerie = new ArrayList<Gallerie>();

    public ArrayList<Gallerie> getListB() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allGallerie");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGallerie ser = new ServiceGallerie();
                listGallerie = ser.getListGallerie(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listGallerie;
    }

    public ArrayList<Gallerie> getListDescriptionByTag(String tag) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/GalleriebyTag/" + tag);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGallerie ser = new ServiceGallerie();
                listGallerie = ser.getListGallerie(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listGallerie;
    }

    public ArrayList<Gallerie> getListByType(String Type) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/GalleriebyType/" + Type);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGallerie ser = new ServiceGallerie();
                listGallerie = ser.getListGallerie(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listGallerie;
    }

    public ArrayList<Gallerie> getListByGouv(String Gouv) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/GalleriebyGouv/" + Gouv);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGallerie ser = new ServiceGallerie();
                listGallerie = ser.getListGallerie(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listGallerie;
    }

    public ArrayList<Gallerie> getListByGouvAndType(String Gouv, String Type) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/GalleriebyGouvAndType/" + Gouv + "/" + Type);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGallerie ser = new ServiceGallerie();
                listGallerie = ser.getListGallerie(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listGallerie;
    }
}
