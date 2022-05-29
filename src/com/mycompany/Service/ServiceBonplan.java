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
import com.mycompany.Entity.Bonplan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author khali
 */
public class ServiceBonplan {

    public ArrayList<Bonplan> getListBonplan(String json) {

        ArrayList<Bonplan> listBonplan = new ArrayList<Bonplan>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> Bonplan = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(Bonplan);

            List<Map<String, Object>> list = (List<Map<String, Object>>) Bonplan.get("root");

            for (Map<String, Object> obj : list) {
                Bonplan Bp = new Bonplan();

                float id = Float.parseFloat(obj.get("idbonplan").toString());
                String NomBonplan = obj.get("nombonplan").toString();
                String Addresse = obj.get("adressebonplan").toString();
                String typeBonplan = obj.get("typebonplan").toString();
                String imgbonplan = obj.get("imgbonplan").toString();
                Double AvgRating = Double.parseDouble(obj.get("avgrating").toString());
                Double Longitude = Double.parseDouble(obj.get("longitude").toString());
                Double latitude = Double.parseDouble(obj.get("latitude").toString());

                Bp.setNombonplan(NomBonplan);
                Bp.setIdBonplan((int) id);
                Bp.setAdresseBonplan(Addresse);
                Bp.setTypeBonplan(typeBonplan);
                Bp.setImgBonplan(imgbonplan);
                Bp.setAvgRating(AvgRating);
                Bp.setLongitude(Longitude);
                Bp.setLatitude(latitude);

                System.out.println(Bp);
                listBonplan.add(Bp);

            }

        } catch (IOException ex) {
        }
        System.out.println(listBonplan);
        return listBonplan;

    }

    ArrayList<Bonplan> listbonplan = new ArrayList<Bonplan>();

    public ArrayList<Bonplan> getListB() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allBonplan");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBonplan ser = new ServiceBonplan();
                listbonplan = ser.getListBonplan(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listbonplan;
    }

    public ArrayList<Bonplan> getListByTag(String tag) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/byTagBonplan/" + tag);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBonplan ser = new ServiceBonplan();
                listbonplan = ser.getListBonplan(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listbonplan;
    }

    public ArrayList<Bonplan> getListByDesc() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allRatingDescBonplan");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBonplan ser = new ServiceBonplan();
                listbonplan = ser.getListBonplan(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listbonplan;
    }

    public ArrayList<Bonplan> getListByAsc() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allRatingAscBonplan");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBonplan ser = new ServiceBonplan();
                listbonplan = ser.getListBonplan(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listbonplan;
    }

      int c = 0;
    public ArrayList<Bonplan> getListByType(String Type) {

        ConnectionRequest con = new ConnectionRequest();

      
        if (Type.equalsIgnoreCase("Salon de thé")) {
             c = 1;
        } else if (Type.equalsIgnoreCase("Bien être")) {
             c = 2;
        } else if (Type.equalsIgnoreCase("Site naturelle")) {
             c = 3;
        } else if (Type.equalsIgnoreCase("Cinéma")) {
             c = 4;
        } else if (Type.equalsIgnoreCase("Restaurant")) {
             c = 5;

        }
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/byType/" + c);

        System.out.println("Type var has : " + Type);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBonplan ser = new ServiceBonplan();
                listbonplan = ser.getListBonplan(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listbonplan;
    }

}
