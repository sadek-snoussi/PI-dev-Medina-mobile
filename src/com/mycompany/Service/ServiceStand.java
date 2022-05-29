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
import com.mycompany.Entity.Stand;
import com.mycompany.Entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS I7
 */
public class ServiceStand {

    public ArrayList<Stand> getListStandDispo() {
        ArrayList<Stand> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/standDispo");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> obj : list) {

                        Stand task = new Stand();

                        float id = Float.parseFloat(obj.get("numstand").toString());
                        task.setNumStand((int) id);

                        task.setSuperficieStand(Double.parseDouble(obj.get("superficiestand").toString()));

                        task.setEmplacementStand(obj.get("emplacementstand").toString());

                        task.setPrix(Double.parseDouble(obj.get("prix").toString()));

                        listTasks.add(task);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;

    }

    public void ReservationStand(Stand s) {
        ConnectionRequest con = new ConnectionRequest();
        User u = new User();
        u.setId(2);
        s.setUserId(u);
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/Reserver/" + s.getNumStand() + "/" + s.getEventstand().getIdEvent() + "/" + s.getUserId().getId();
        con.setUrl(Url);

        System.out.println(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            System.out.println("stttttrrrrrr");
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
