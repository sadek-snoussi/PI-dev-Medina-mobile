/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Commande;
import com.mycompany.Entity.Panier;
import com.mycompany.Entity.User;
import com.mycompany.Service.ServiceCommande;
import com.mycompany.Service.ServicePanier;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sofienne
 */
public class AffichageCommande {

    int idUser = User.user.getId();
    User user = new User();
    Commande commande = new Commande();
    Form f;
    private Resources theme;

    // ****************** Label ,Button , textfields ...  *****************************
    Label prixTotaleLabel = new Label("Votre totale prix est");
    Label prixTotale = new Label();
    Button btnValiderCommande = new Button("Valider Commande");
    TextField txfNom = new TextField("", "Nom");
    TextField txfPrenom = new TextField("", "Prenom");
    TextField txfEmail = new TextField("", "foulen@email.com");
    TextField txfTel = new TextField("", "Tel");
    TextField txfAdresse = new TextField("", "Adresse");
    TextField txfCodePostale = new TextField("", "Code Postale");

    // ********************************************************************************
    double totalePrix;
    ServiceCommande src = new ServiceCommande();

    public AffichageCommande() {
        theme = UIManager.initFirstTheme("/theme");
        f = new Form("Ma Commande");
        
       Resources theme = UIManager.initFirstTheme("/theme");
        f.getToolbar().addCommandToLeftBar("", theme.getImage("cal_left_arrow.png"), (ev) -> {
            AffichagePanier myp = new AffichagePanier();
            myp.getF().show();
        });
        
        totalePrix = src.calculPrixTotale(idUser);
        prixTotale.setText(totalePrix + " TDN");

        btnValiderCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                src.updateQuantiteProduit(idUser);
                ServicePanier srvPanier = new ServicePanier();
                ServiceCommande srcCommande = new ServiceCommande();
                ArrayList<Panier> list = new ArrayList<Panier>();
                list = srvPanier.getListP();
                for (Panier pn : list) {
                    user.setId(idUser);
                    commande.setUserId(user);
                    commande.setNom(txfNom.getText());
                    commande.setPrenom(txfPrenom.getText());
                    commande.setTel(txfTel.getText());
                    commande.setEmail(txfEmail.getText());
                    commande.setAdresse(txfAdresse.getText());
                    commande.setCodepostale(txfCodePostale.getText());
                    commande.setPanierId(pn);
                    commande.setTotalPrixCommande(totalePrix);
                     
                    srcCommande.ajouterCommande(commande);
                    
                    
                }
                ArrayList<Commande> listc = new ArrayList<Commande>();
                listc=src.getListCommande(totalePrix);
                Commande commandess = new Commande();
                commandess=listc.get(0);
                src.updateFlag(idUser);
                System.out.println("la commande "+ commandess.getNom());
                ToastBar.showMessage("Commande ajouter avec succes", FontImage.MATERIAL_INFO);
                Display.getInstance().execute("http://localhost/Medina_VersionFinale_web/web/app_dev.php/pdfM/"+commandess.getIdCommande());
                //*************************************************************************
                //************** SMS *****************************************************
                
                /*AuthMethod auth = new TokenAuthMethod("38dc754a", "nAg1o0HT5RG6khYd");  // (api_key,api_secret)
                        NexmoClient client = new NexmoClient(auth);
                        SmsSubmissionResult[] responses;
                        User userf = new User();
                        UserService us = new UserService();
                        userf=us.findUserByiD(idUser);
                       
               try {
                   
                   responses = client.getSmsClient().submitMessage(new TextMessage(
                           "Souk Medina",
                           "+21623040149",
                           "Madmae/Mr "+userf.getNomUser()+" "+userf.getPrenomUser()+" votre commande est effectue avec succés"));
                    for (SmsSubmissionResult response : responses) {
                                System.out.println(response);
                            }
               } catch (IOException ex) {
                
               } catch (NexmoClientException ex) {
                  
               }*/
                
                //*************************************************************************
                //*************************************************************************
                
                System.out.println("successsssssssssssssssssssssssssssssssssssssssssss");
            }
        });
      

        f.add(prixTotaleLabel);
        f.add(prixTotale);
        f.add(txfNom);
        f.add(txfPrenom);
        f.add(txfEmail);
        f.add(txfTel);
        f.add(txfAdresse);
        f.add(txfCodePostale);
        f.add(btnValiderCommande);


    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
