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
import com.mycompany.Entity.Event;
import com.mycompany.Entity.User;
import com.mycompany.Entity.UserEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS I7
 */
public class ServiceEvent {

    public ArrayList<Event> getListEvent() {
        ArrayList<Event> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/allEvent");
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

                        Event task = new Event();

                        task.setNomEvent(obj.get("nomevent").toString());

                        task.setDescriptionevent(obj.get("descriptionevent").toString());
                        task.setUrlafficheevent(obj.get("urlafficheevent").toString());
                        float nb = Float.parseFloat(obj.get("nbreplace").toString());
                        task.setNbrePlace((int) nb);

                        float nbs = Float.parseFloat(obj.get("nbrestand").toString());
                        float idevent = Float.parseFloat(obj.get("idevent").toString());
                        task.setNbreStand((int) nbs);
                        task.setIdEvent((int) idevent);
                        task.setLieux(obj.get("lieux").toString());
                        //////////////////////////Affichage Date////////////////

                        Map<String, Object> date = null;
                        date = (Map<String, Object>) obj.get("dateevent");
                    
                        try {

                            Date longdate = new Date((long) Float.parseFloat(date.get("timestamp").toString()) * 1000);
                            

                            System.out.println("*************" + longdate);

                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                            String d = formatter.format(longdate);
                            task.setDateEvent(longdate);
                        } catch (NumberFormatException ex) {

                        }

                        listTasks.add(task);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;

    }

    public void inscriEvent(UserEvent i) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/inscri23/"+
                User.user.getId()+"/"+ i.getEventId().getIdEvent()+"?nom=" + i.getNom() + "&prenom=" + i.getPrenom() + "&adressemail=" + i.getAdressemail();

        System.out.println(Url);
        con.setUrl(Url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            System.out.println("oui");
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

}
