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
import com.mycompany.Entity.Categorie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class CategoryService {
    
        public ArrayList<Categorie> getListCateg(String json) {
        
            ArrayList<Categorie> categories = new ArrayList<Categorie>();

        try {
            
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> cats = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(cats);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) cats.get("root");

            for (Map<String, Object> obj : list) {
                
                

                //---------------------------------------------------------------------------------------
                //-----------------------------------CATEGORY----------------------------------------------------

                
                Categorie c = new Categorie();
                
                c.setIdCategorie((int)Float.parseFloat(obj.get("idcategorie").toString()));
                
                c.setNomCategorie(obj.get("nomcategorie").toString());
//                p.setReferenceProd(obj.get("referenceProd").toString());   
//                p.setMatiereProduit(obj.get("matiereproduit").toString());   
//                
//                p.setUrlImgProduit(obj.get("urlimgproduit").toString());                   
//                
//                p.setPrixBaseProduit(Double.parseDouble(obj.get("prixbaseproduit").toString()));
//                p.setPrixVenteProduit(Double.parseDouble(obj.get("prixventeproduit").toString()));
//                
//                p.setQteDispoProduit((int)Float.parseFloat(obj.get("qtedispoproduit").toString()));
//                p.setQteVenduProduit((int)Float.parseFloat(obj.get("qtevenduproduit").toString()));
//                
//                p.setValiditeProduit((int)Float.parseFloat(obj.get("validiteProduit").toString()));


                
                categories.add(c);

            }

        } catch (IOException ex) {
        }
        System.out.println(categories);
        return categories;
    
    
    }
    
    ArrayList<Categorie> categories = new ArrayList<Categorie>();
    
    public ArrayList<Categorie> getList(){
       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/Medina_VersionFinale_web/web/app_dev.php/prod/categories");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CategoryService serv = new CategoryService();
                categories = serv.getListCateg(new String(con.getResponseData()));
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(con);
        return categories;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
