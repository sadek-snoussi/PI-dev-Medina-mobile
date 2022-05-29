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
import com.mycompany.Entity.Commande;
import com.mycompany.Entity.Panier;
import com.mycompany.Entity.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sofienne
 */
public class ServiceCommande {

    public void updateFlag(int idUser) {          
            ConnectionRequest con = new ConnectionRequest();
            String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/updateFlag/" + idUser;
            con.setUrl(Url);
            System.out.println("tt");
            System.out.println(Url);
            con.addResponseListener((e) -> {

                String str = new String(con.getResponseData());

                System.out.println(str);

            });
            NetworkManager.getInstance().addToQueueAndWait(con);
            System.out.println("post : flag mis a jour avec succes");
    }
    public double calculPrixTotale(int idUser){
        double prixTotale=0;
         ServicePanier srv = new ServicePanier();
         ArrayList<Panier> listFinale = new ArrayList<>();
         ArrayList<Panier> list = new ArrayList<>();
         list=srv.getListP();
         for(Panier pn : list){
             if(pn.getUserId().getId()==idUser && pn.getFlag()==0){
                 listFinale.add(pn);
             }
         }
         for (Panier pn : listFinale){
             prixTotale = prixTotale + (pn.getQuantiteProduit()*pn.getProduitId().getPrixVenteProduit()) ;
         }
        return prixTotale;
    }
    
    public void updateQuantiteProduit(int idUser){
         int quantitePtoduit=0;
         int quantiteVendu=0;
         ServicePanier srv = new ServicePanier();
         ArrayList<Panier> listFinale = new ArrayList<>();
         ArrayList<Panier> list = new ArrayList<>();
         list=srv.getListP();
         for (Panier pn : list){
             if(pn.getUserId().getId()==idUser){
                 listFinale.add(pn);
             }
         }
         for(Panier pn : listFinale){
             Produit pr = new Produit();
             quantitePtoduit=pn.getProduitId().getQteDispoProduit()-pn.getQuantiteProduit();
             quantiteVendu=pn.getProduitId().getQteVenduProduit()+pn.getQuantiteProduit();
             pr.setIdProduit(pn.getProduitId().getIdProduit());
             pr.setQteDispoProduit(quantitePtoduit);
             pr.setQteVenduProduit(quantiteVendu);
             ConnectionRequest con = new ConnectionRequest();
            String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/updateQuantiteProduit/"+pr.getIdProduit()+"/"+pr.getQteDispoProduit()+"/"+pr.getQteVenduProduit();
            con.setUrl(Url);
            System.out.println("tt");
            System.out.println(Url);
            con.addResponseListener((e) -> {

                String str = new String(con.getResponseData());

                System.out.println(str);

            });
            //con.setFailSilently(true);
            NetworkManager.getInstance().addToQueueAndWait(con);
            System.out.println("Commande ajouter avec succes");
             
             
         }
    }
    
    public void ajouterCommande(Commande commande){
          ConnectionRequest con = new ConnectionRequest();
            String Url = "http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/ajouterCommande?nom="+commande.getNom()+"&prenom="+commande.getPrenom()+"&tel="+commande.getTel()+"&email="+commande.getEmail()+"&addresse="+commande.getAdresse()+"&codePostale="+commande.getCodepostale()+"&idUser="+commande.getUserId().getId()+"&idPanier="+commande.getPanierId().getId()+"&totale="+commande.getTotalPrixCommande();
            con.setUrl(Url);
            System.out.println("tt");
            System.out.println(Url);
            con.addResponseListener((e) -> {

                String str = new String(con.getResponseData());

                System.out.println(str);

            });
            //con.setFailSilently(true);
            NetworkManager.getInstance().addToQueueAndWait(con);
            System.out.println("Commande ajouter avec succes");
    }
    
     public ArrayList<Commande> getListCommandes(String json) {

        ArrayList<Commande> listPanier = new ArrayList<Commande>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> panier = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(panier);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) panier.get("root");

            for (Map<String, Object> obj : list) {
                Commande pn = new Commande();
                float id = Float.parseFloat(obj.get("idcommande").toString());
                pn.setIdCommande((int)id);
                pn.setTotalPrixCommande(Double.parseDouble(obj.get("totalprixcommande").toString()));
                pn.setAdresse(obj.get("adresse").toString());
                pn.setNom(obj.get("nom").toString());
                pn.setPrenom(obj.get("prenom").toString());
                pn.setEmail(obj.get("email").toString());
                pn.setTel(obj.get("tel").toString());
                pn.setCodepostale(obj.get("codepostale").toString());
                listPanier.add(pn);

            }

        } catch (IOException ex) {
        }
        //System.out.println(listPanier);
        return listPanier;

    }
    
    
    ArrayList<Commande> listCommande = new ArrayList<Commande>();
    public ArrayList<Commande> getListCommande(double totalePrix){
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/commande/"+totalePrix);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceCommande serC = new ServiceCommande();
                listCommande = serC.getListCommandes(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listCommande;
    }

}
