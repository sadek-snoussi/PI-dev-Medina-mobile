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

import java.util.ArrayList;
import java.util.List;

import com.mycompany.Entity.User;
import java.io.IOException;

import java.util.Date;
//import java.util.Date;
import java.util.Map;

/**
 *
 * @author amalb
 */
public class UserService {

    // ArrayList<User> users = new ArrayList<>();
    ArrayList<User> usersLogin = new ArrayList<User>();

    public void inscription(User user) {

        ConnectionRequest con = new ConnectionRequest();

        System.out.println("serviiiiiiiiiiiiice" + user.getDateNaissUser());
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/userAdd/add?nomUser=" + user.getNomUser() + "&email="
                + user.getEmail() + "&prenomUser=" + user.getPrenomUser() + "&username=" + user.getUsername()
                + "&password=" + user.getPassword() 
                + "&telUser=" + user.getTelUser() + "&adresse=" + user.getAdresse() + "&email_canonical="
                + user.getEmail() + "&username_canonical=" + user.getUsername()
                + "&enabled=1&partenariat=0&nbrPointFidelite=0";

        con.setUrl(Url);

        con.addResponseListener((e) -> {

            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void ModifierProfileClient(User user) {
        ConnectionRequest con = new ConnectionRequest();
        System.out.println("daaaaaate" + user.getDateNaissUser());
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/modifProfileClient/" + user.getId() + "?nomUser="
                + user.getNomUser() + "&email=" + user.getEmail() 
                + "&prenomUser=" + user.getPrenomUser() + "&username=" + user.getUsername()
                + "&telUser=" + user.getTelUser() + "&adresse=" + user.getAdresse();

        System.out.println("***********" + Url);
        con.setUrl(Url);
        con.addResponseListener((e) -> {

            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public ArrayList<User> getListUsers(String json) {

        ArrayList<User> listUsers = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> Users = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) Users.get("root");

            for (Map<String, Object> obj : list) {
                User user = new User();

                float id = Float.parseFloat(obj.get("id").toString());

                user.setId((int) id);
                user.setUsername(obj.get("username").toString());
                user.setNomUser(obj.get("nomuser").toString() != null ? obj.get("nomuser").toString() : null);
                user.setPrenomUser(obj.get("prenomuser") != null ? obj.get("prenomuser").toString() : null);
                user.setPassword(obj.get("password").toString());
                user.setEmail(obj.get("email").toString());

                user.setAdresse(obj.get("adresse") != null ? obj.get("adresse").toString() : null);
                user.setTelUser(obj.get("teluser") != null ? obj.get("teluser").toString() : null);
                user.setTypeUser(obj.get("typeuser").toString());
                // user.setDateNaissUser(obj.get("dateNaissUser").);
                user.setGradePro(obj.get("gradepro") != null ? obj.get("gradepro").toString() : null);
                user.setSpecialitePart(obj.get("specialitepart") != null ? obj.get("specialitepart").toString() : null);
                user.setTelBureauPro(obj.get("telbureaupro") != null ? obj.get("telbureaupro").toString() : null);
                user.setNomEntreprisePro(obj.get("nomentreprisepro") != null ? obj.get("nomentreprisepro").toString() : null);
                //user.setAdresse(obj.get("adresse") != null ? obj.get("adresse").toString() : null);
                float nb = Float.parseFloat(obj.get("partenariat").toString());
                user.setPartenariat((int) nb);

                Map<String, Object> date = null;
                date = (Map<String, Object>) obj.get("datenaissuser");

                Date londdate = new Date((long) Float.parseFloat(date.get("timestamp").toString()) * 1000);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                String d = formatter.format(londdate);

                user.setDateNaissUser(londdate);

                listUsers.add(user);

            }

        } catch (IOException ex) {
        }
        System.out.println(listUsers);
        return listUsers;

    }

    public ArrayList<User> getUsers() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/users/all");

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                UserService usservice = new UserService();

                usersLogin = usservice.getListUsers(new String(con.getResponseData()));

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return usersLogin;
    }

    public User findUserByLogin(String login) {
        User user = new User();
        UserService userservice = new UserService();
        ArrayList<User> listUsers = new ArrayList<User>();
        listUsers = userservice.getUsers();
        ArrayList<User> listFinale = new ArrayList<User>();
        for (User u : listUsers) {
            if (u.getUsername().equals(login) || u.getEmail().equals(login)) {
                listFinale.add(u);
                user = listFinale.get(0);

            }
        }

        return user;
    }

    //    public User FindUserByUsername(String login) {
//        ConnectionRequest con = new ConnectionRequest();
//        con.setUrl("http://127.0.0.1:8000/api/auth/" + login);
//        System.out.println("fiiiiinnnnnnnnnddddd" + con.getUrl());
//        con.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                UserService ser = new UserService();
//
//                System.out.println("fiiiiinnnnnnnnnddddd" + con.getResponseData().toString());
//                usersLogin = ser.getListUsers(new String(con.getResponseData()));
//                
//                System.out.println("userr*******findbyusername*" + users.get(0));
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(con);
//        
//        return usersLogin.get(0);
//
//    }
}
