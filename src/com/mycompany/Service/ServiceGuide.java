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
import pidev.edu.souk.entities.Guide;

/**
 *
 * @author khali
 */
public class ServiceGuide {
    public ArrayList<Guide> getListGuide(String json) {

        ArrayList<Guide> listGuide = new ArrayList<Guide>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> Guide = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(Guide);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) Guide.get("root");

            for (Map<String, Object> obj : list) {
                Guide guide = new Guide();

                float id = Float.parseFloat(obj.get("idguide").toString());
                String NomGuide = obj.get("nomguide").toString();
                String PrenomGuide = obj.get("prenomguide").toString();
                String telGuide = obj.get("telguide").toString();
                String VilleGuide = obj.get("villeguide").toString();
                String MailGuide = obj.get("mailguide").toString();
                  String imgGuide = obj.get("imgguide").toString();
                
                guide.setIdGuide((int)id);
                guide.setNomGuide(NomGuide);
                guide.setPrenomGuide(PrenomGuide);
                guide.setTelGuide(telGuide);
                guide.setVilleGuide(VilleGuide);
                guide.setMailguide(MailGuide);
                guide.setImgGuide(imgGuide);
                
              
                System.out.println(guide);
                listGuide.add(guide);

            }

        } catch (IOException ex) {
        }
        System.out.println(listGuide);
        return listGuide;

    }
    
    ArrayList<Guide> listguide = new ArrayList<Guide>();
    
        public ArrayList<Guide> getListB(){
       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allGuide");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGuide ser = new ServiceGuide();
                listguide = ser.getListGuide(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listguide;
    }
        
           public ArrayList<Guide> getListByGouv(String Gouv) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/GuidebyGouv/" + Gouv);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceGuide ser = new ServiceGuide();
                listguide= ser.getListGuide(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listguide;
    }
    
}
