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
import com.mycompany.Entity.Panier;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sofienne
 */
public class ServicePanier {
    
    public ArrayList<Panier> getListPanier(String json) {

        ArrayList<Panier> listPanier = new ArrayList<Panier>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> panier = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(panier);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) panier.get("root");

            for (Map<String, Object> obj : list) {
                Panier pn = new Panier();
                float id = Float.parseFloat(obj.get("id").toString());
                pn.setId((int) id);
                float flag = Float.parseFloat(obj.get("flag").toString());
                pn.setFlag((int) flag);
               float quantiteProduit = Float.parseFloat(obj.get("quantiteproduit").toString());
                pn.setQuantiteProduit((int)quantiteProduit);
                Produit pr = new Produit();
                User u = new User();
                // here is the second MAP
                Map<String, Object> listRecup = null;
                // USER is the foreign key 
                if(obj.get("user")!=null){
                  listRecup=(Map<String, Object>) obj.get("user");
                  u.setId((int)Float.parseFloat(listRecup.get("id").toString()));
                  pn.setUserId(u);
                }
                //aussi Produit est un cle etrangers
                if(obj.get("produit")!=null){
                  listRecup=(Map<String, Object>) obj.get("produit");
                  pr.setIdProduit((int)Float.parseFloat(listRecup.get("idproduit").toString()));
                  pr.setPrixVenteProduit(Double.parseDouble(listRecup.get("prixventeproduit").toString()));
                  pr.setQteDispoProduit((int)Float.parseFloat(listRecup.get("qtedispoproduit").toString()));
                  pr.setQteVenduProduit((int)Float.parseFloat(listRecup.get("qtevenduproduit").toString()));
                  pr.setNomProduit(listRecup.get("nomproduit").toString());
                  pr.setUrlImgProduit(listRecup.get("urlimgproduit").toString());
                  //System.out.println(pr.getUrlImgProduit());
                  pn.setProduitId(pr);
                }
                
                //System.out.println(pn);
                //System.out.println("Finalement :p :p :p + "+ pn.getUserId().getId());
                //System.out.println("le produit dans le panier est : "+pn.getProduitId().getNomProduit()+" "+pn.getProduitId().getPrixVenteProduit()+" "+pn.getProduitId().getQteDispoProduit());
                  listPanier.add(pn);

            }

        } catch (IOException ex) {
        }
        //System.out.println(listPanier);
        return listPanier;

    }
    
    
    
     ArrayList<Panier> listPanier = new ArrayList<Panier>();
    
    public ArrayList<Panier> getListP(){
       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/Paniers");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServicePanier ser = new ServicePanier();
                listPanier = ser.getListPanier(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listPanier;
    }
    
    
    public void updateQuantiteProduit(Panier pn){
      
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/updateQuantite/" + pn.getId() + "/" + pn.getQuantiteProduit();
        con.setUrl(Url);

        System.out.println("tt");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
//            if (str.trim().equalsIgnoreCase("OK")) {
//                f2.setTitle(tlogin.getText());
//             f2.show();
//            }
//            else{
//            Dialog.show("error", "login ou pwd invalid", "ok", null);
//            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void supprimerDuPanier(int idPanier){
                ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/supprimerDuPanier/"+idPanier;
        con.setUrl(Url);
        System.out.println("tt");
        System.out.println(Url);
        con.addResponseListener((e) -> {
          
            String str = new String(con.getResponseData());
     
            System.out.println(str);
            
        });
        System.out.println("bom bom bom");
        con.setFailSilently(true);
        NetworkManager.getInstance().addToQueueAndWait(con);
      
    }
    
     public Panier findProduitInPanier(int idUserConnecte, int idProduit ) {
         Panier panier = new Panier();
         ServicePanier srv = new ServicePanier();
         ArrayList<Panier> list = new ArrayList<>();
         ArrayList<Panier> listinale = new ArrayList<>();
         list=srv.getListP();
         for(Panier pn : list){
             if (pn.getUserId().getId()==idUserConnecte && pn.getProduitId().getIdProduit()==idProduit && pn.getFlag()==0){
               
                 listinale.add(pn);
                 panier=listinale.get(0);
             }

             
         }
    
         return panier;
     }
     
     public void ajouterPanier(Panier pn){
         ConnectionRequest con = new ConnectionRequest();
         int idProduit = pn.getProduitId().getIdProduit();
         int idUser= pn.getUserId().getId();
        String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/ajouterPanier/" + idUser + "/" + idProduit ;
        con.setUrl(Url);

        System.out.println("tt");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
     }
    
}
