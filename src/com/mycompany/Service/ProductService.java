/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Service;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entity.Categorie;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author admin
 */
public class ProductService {

    public ArrayList<Produit> getListProd(String json) {

        ArrayList<Produit> produits = new ArrayList<Produit>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> prods = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(prods);

            List<Map<String, Object>> list = (List<Map<String, Object>>) prods.get("root");

            for (Map<String, Object> obj : list) {

                //---------------------------------------------------------------------------------------
                //-----------------------------------USER----------------------------------------------------
                Map<String, Object> listRecupUser = null;
                User u = new User();
                if (obj.get("iduser") != null) {

                    listRecupUser = (Map<String, Object>) obj.get("iduser");

                    u.setId((int) Float.parseFloat(listRecupUser.get("id").toString()));
                    u.setUsername(listRecupUser.get("username").toString());
                    u.setMailUser(listRecupUser.get("email").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------------CATEGORY----------------------------------------------------
                Map<String, Object> listRecupCategory = null;

                Categorie c = new Categorie();
                if (obj.get("idcategorie") != null) {

                    listRecupCategory = (Map<String, Object>) obj.get("idcategorie");

                    c.setIdCategorie((int) Float.parseFloat(listRecupCategory.get("idcategorie").toString()));
                    c.setNomCategorie(listRecupCategory.get("nomcategorie").toString());
                    c.setTypeCategorie(listRecupCategory.get("typecategorie").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------PRODUCT----------------------------------------------------------
                Produit p = new Produit();

                p.setIdProduit((int) Float.parseFloat(obj.get("idproduit").toString()));

                p.setNomProduit(obj.get("nomproduit").toString());
                p.setReferenceProd(obj.get("referenceProd").toString());
                p.setMatiereProduit(obj.get("matiereproduit").toString());

                p.setUrlImgProduit(obj.get("urlimgproduit").toString());

                p.setPrixBaseProduit(Double.parseDouble(obj.get("prixbaseproduit").toString()));
                p.setPrixVenteProduit(Double.parseDouble(obj.get("prixventeproduit").toString()));

                p.setQteDispoProduit((int) Float.parseFloat(obj.get("qtedispoproduit").toString()));
                p.setQteVenduProduit((int) Float.parseFloat(obj.get("qtevenduproduit").toString()));

                p.setValiditeProduit((int) Float.parseFloat(obj.get("validiteProduit").toString()));

                p.setIdUser(u);
                p.setIdCategorie(c);

                produits.add(p);

            }

        } catch (IOException ex) { 
            
        }
        System.out.println(produits);
        return produits;

    }

    ArrayList<Produit> produits = new ArrayList<Produit>();

    public ArrayList<Produit> getList() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/Products");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ProductService serv = new ProductService();
                produits = serv.getListProd(new String(con.getResponseData()));
            }
        });
        con.setFailSilently(true);
        NetworkManager.getInstance().addToQueueAndWait(con);
        return produits;
    }

    public void saveFile(String input,String output) throws IOException {

        OutputStream outStream = null;
        InputStream stream = null;

            //------------------------------------TO STORAGE-------------------------------------
            
            stream = FileSystemStorage.getInstance().openInputStream(input);
            OutputStream out =Storage.getInstance().createOutputStream(output);
            Util.copy(stream, out);
            Util.cleanup(stream);
            Util.cleanup(out);
            System.out.println("the file is copied successfully in the storage !");
            

            
            



             

         }
        
        
//*****************************************RECHERCHE GLOBALE**************************************        

        public ArrayList<Produit> getListProdRecherches(String json) {

        ArrayList<Produit> produits = new ArrayList<Produit>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> prods = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(prods);

            List<Map<String, Object>> list = (List<Map<String, Object>>) prods.get("root");

            for (Map<String, Object> obj : list) {

                //---------------------------------------------------------------------------------------
                //-----------------------------------USER----------------------------------------------------
                Map<String, Object> listRecupUser = null;
                User u = new User();
                if (obj.get("iduser") != null) {

                    listRecupUser = (Map<String, Object>) obj.get("iduser");

                    u.setId((int) Float.parseFloat(listRecupUser.get("id").toString()));
                    u.setUsername(listRecupUser.get("username").toString());
                    u.setMailUser(listRecupUser.get("email").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------------CATEGORY----------------------------------------------------
                Map<String, Object> listRecupCategory = null;

                Categorie c = new Categorie();
                if (obj.get("idcategorie") != null) {

                    listRecupCategory = (Map<String, Object>) obj.get("idcategorie");

                    c.setIdCategorie((int) Float.parseFloat(listRecupCategory.get("idcategorie").toString()));
                    c.setNomCategorie(listRecupCategory.get("nomcategorie").toString());
                    c.setTypeCategorie(listRecupCategory.get("typecategorie").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------PRODUCT----------------------------------------------------------
                Produit p = new Produit();

                p.setIdProduit((int) Float.parseFloat(obj.get("idproduit").toString()));

                p.setNomProduit(obj.get("nomproduit").toString());
                p.setReferenceProd(obj.get("referenceProd").toString());
                p.setMatiereProduit(obj.get("matiereproduit").toString());

                p.setUrlImgProduit(obj.get("urlimgproduit").toString());

                p.setPrixBaseProduit(Double.parseDouble(obj.get("prixbaseproduit").toString()));
                p.setPrixVenteProduit(Double.parseDouble(obj.get("prixventeproduit").toString()));

                p.setQteDispoProduit((int) Float.parseFloat(obj.get("qtedispoproduit").toString()));
                p.setQteVenduProduit((int) Float.parseFloat(obj.get("qtevenduproduit").toString()));

                p.setValiditeProduit((int) Float.parseFloat(obj.get("validiteProduit").toString()));

                p.setIdUser(u);
                p.setIdCategorie(c);

                produits.add(p);

            }

        } catch (IOException ex) { 
            
        }
        System.out.println(produits);
        return produits;

    }


        ArrayList<Produit> produitsRecherches = new ArrayList<Produit>();
        
    public ArrayList<Produit> getListRecherches(String tag) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/recherche/"
                + tag);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ProductService serv = new ProductService();
                produitsRecherches = serv.getListProdRecherches(new String(con.getResponseData()));
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
        return produitsRecherches;
    }
    
    
    //********************************recherche par categorie****************************************
    
            public ArrayList<Produit> getListProdParCategorie(String json) {

        ArrayList<Produit> produits = new ArrayList<Produit>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> prods = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(prods);

            List<Map<String, Object>> list = (List<Map<String, Object>>) prods.get("root");

            for (Map<String, Object> obj : list) {

                //---------------------------------------------------------------------------------------
                //-----------------------------------USER----------------------------------------------------
                Map<String, Object> listRecupUser = null;
                User u = new User();
                if (obj.get("iduser") != null) {

                    listRecupUser = (Map<String, Object>) obj.get("iduser");

                    u.setId((int) Float.parseFloat(listRecupUser.get("id").toString()));
                    u.setUsername(listRecupUser.get("username").toString());
                    u.setMailUser(listRecupUser.get("email").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------------CATEGORY----------------------------------------------------
                Map<String, Object> listRecupCategory = null;

                Categorie c = new Categorie();
                if (obj.get("idcategorie") != null) {

                    listRecupCategory = (Map<String, Object>) obj.get("idcategorie");

                    c.setIdCategorie((int) Float.parseFloat(listRecupCategory.get("idcategorie").toString()));
                    c.setNomCategorie(listRecupCategory.get("nomcategorie").toString());
                    c.setTypeCategorie(listRecupCategory.get("typecategorie").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------PRODUCT----------------------------------------------------------
                Produit p = new Produit();

                p.setIdProduit((int) Float.parseFloat(obj.get("idproduit").toString()));

                p.setNomProduit(obj.get("nomproduit").toString());
                p.setReferenceProd(obj.get("referenceProd").toString());
                p.setMatiereProduit(obj.get("matiereproduit").toString());

                p.setUrlImgProduit(obj.get("urlimgproduit").toString());

                p.setPrixBaseProduit(Double.parseDouble(obj.get("prixbaseproduit").toString()));
                p.setPrixVenteProduit(Double.parseDouble(obj.get("prixventeproduit").toString()));

                p.setQteDispoProduit((int) Float.parseFloat(obj.get("qtedispoproduit").toString()));
                p.setQteVenduProduit((int) Float.parseFloat(obj.get("qtevenduproduit").toString()));

                p.setValiditeProduit((int) Float.parseFloat(obj.get("validiteProduit").toString()));

                p.setIdUser(u);
                p.setIdCategorie(c);

                produits.add(p);

            }

        } catch (IOException ex) { 
            
        }
        System.out.println(produits);
        return produits;

    }


        ArrayList<Produit> produitsParCategorie = new ArrayList<Produit>();
        
    public ArrayList<Produit> getListParCategorie(String cat) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/rechercheParCategorie/"
                + cat);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ProductService serv = new ProductService();
                produitsParCategorie = serv.getListProdParCategorie(new String(con.getResponseData()));
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
        return produitsParCategorie;
    }
    
    
    
     //********************************recherche par Prix****************************************
   
        public ArrayList<Produit> getListProdParPrix(String json) {

        ArrayList<Produit> produits = new ArrayList<Produit>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> prods = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(prods);

            List<Map<String, Object>> list = (List<Map<String, Object>>) prods.get("root");

            for (Map<String, Object> obj : list) {

                //---------------------------------------------------------------------------------------
                //-----------------------------------USER----------------------------------------------------
                Map<String, Object> listRecupUser = null;
                User u = new User();
                if (obj.get("iduser") != null) {

                    listRecupUser = (Map<String, Object>) obj.get("iduser");

                    u.setId((int) Float.parseFloat(listRecupUser.get("id").toString()));
                    u.setUsername(listRecupUser.get("username").toString());
                    u.setMailUser(listRecupUser.get("email").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------------CATEGORY----------------------------------------------------
                Map<String, Object> listRecupCategory = null;

                Categorie c = new Categorie();
                if (obj.get("idcategorie") != null) {

                    listRecupCategory = (Map<String, Object>) obj.get("idcategorie");

                    c.setIdCategorie((int) Float.parseFloat(listRecupCategory.get("idcategorie").toString()));
                    c.setNomCategorie(listRecupCategory.get("nomcategorie").toString());
                    c.setTypeCategorie(listRecupCategory.get("typecategorie").toString());

                }

                //---------------------------------------------------------------------------------------
                //-----------------------------PRODUCT----------------------------------------------------------
                Produit p = new Produit();

                p.setIdProduit((int) Float.parseFloat(obj.get("idproduit").toString()));

                p.setNomProduit(obj.get("nomproduit").toString());
                p.setReferenceProd(obj.get("referenceProd").toString());
                p.setMatiereProduit(obj.get("matiereproduit").toString());

                p.setUrlImgProduit(obj.get("urlimgproduit").toString());

                p.setPrixBaseProduit(Double.parseDouble(obj.get("prixbaseproduit").toString()));
                p.setPrixVenteProduit(Double.parseDouble(obj.get("prixventeproduit").toString()));

                p.setQteDispoProduit((int) Float.parseFloat(obj.get("qtedispoproduit").toString()));
                p.setQteVenduProduit((int) Float.parseFloat(obj.get("qtevenduproduit").toString()));

                p.setValiditeProduit((int) Float.parseFloat(obj.get("validiteProduit").toString()));

                p.setIdUser(u);
                p.setIdCategorie(c);

                produits.add(p);

            }

        } catch (IOException ex) { 
            
        }
        System.out.println(produits);
        return produits;

    }


        ArrayList<Produit> produitsParPrix = new ArrayList<Produit>();
        
    public ArrayList<Produit> getListParPrix(Double min,Double max) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/rechercheParPrix/"
                + min
                +"/"+ max);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ProductService serv = new ProductService();
                produitsParPrix = serv.getListProdParPrix(new String(con.getResponseData()));
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
        return produitsParPrix;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
