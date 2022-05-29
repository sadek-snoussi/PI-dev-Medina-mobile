/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.User;
import com.mycompany.Service.PartenaireService;
import com.mycompany.Service.ProductService;
import com.mycompany.Service.UserService;
import java.io.IOException;
import java.util.Date;


/**
 *
 * @author amalb
 */
public class demandePartPro {

    private Resources theme;
    Form demandePartPro;

    TextField nomE;
    TextField numE;
    TextField specialite;
    TextField email;
    TextField grade;
    TextField adresse;
    Label logo;

    Label lblnomE;
    Label lblnumE;
    Label lblspecialite;
    Label lblemail;

    Label lblgrade;
    Button logoB;
    Label lbllogo;
    Label lbladresse;

    Button demanderPart;

    public demandePartPro() {

        demandePartPro = new Form("Demander Partenariat Pro", new FlowLayout(Component.CENTER, Component.CENTER));

        nomE = new TextField("", "Nom de l'entreprise", 20, TextArea.ANY);
        numE = new TextField("", "numero de l'entreprise", 20, TextArea.ANY);
        specialite = new TextField("", "specialité", 20, TextArea.ANY);
        grade = new TextField("", "grade", 20, TextArea.ANY);
        email = new TextField("", "E-Mail", 20, TextArea.ANY);
        adresse = new TextField("", "adresse", 20, TextArea.PHONENUMBER);
        logo = new Label();
        logoB = new Button("insérer Logo");

        Picker p = new Picker();
        Picker p_spec = new Picker();

        String[] grades = {"platinuim", "silver", "gold"};
        p.setStrings(grades);
        p.setSelectedString(grades[0]);

        String[] specialites = {"Patisserie", "bijouterie", "Habillement", "epicerie", "tapis", "decoration"};
        p_spec.setStrings(specialites);
        p_spec.setSelectedString(specialites[0]);
        lblnomE = new Label("nom de l'entrprise");
        lblnumE = new Label("numero de l'entreprise");
        lblspecialite = new Label("Spécialité");
        lblgrade = new Label("grade");
        lblemail = new Label("email");
        lbladresse = new Label("Adresse");
        lbllogo = new Label("Logo de l'entrprise ");

        demanderPart = new Button("Demander");
        Container c = new Container(BoxLayout.y());

        c.add(lblnomE);
        c.add(nomE);

        c.add(lblnumE);
        c.add(numE);

        c.add(lblspecialite);
        c.add(p_spec);

        c.add(lblemail);
        c.add(email);

        c.add(lblgrade);
        c.add(p);

        c.add(lbladresse);
        c.add(adresse);

        c.add(lbllogo);
        c.add(logoB);
        c.add(logo);

        c.add(demanderPart);

        demandePartPro.add(c);
        logoB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String fileNameInServer = "";
                    MultipartRequest cr = new MultipartRequest();
                    String filepath = Capture.capturePhoto(-1, -1);
                    cr.setUrl("http://localhost/Medina_VersionFinale_web/web/php/uploadimage.php");

                    cr.setPost(true);
                    String mime = "image/jpeg";
                    cr.addData("file", filepath, mime);
                    String out = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    cr.setFilename("file", out + ".jpg");//any unique name you want

                    fileNameInServer += out + ".jpg";
                    System.err.println("path2 =" + fileNameInServer);
                    logo.setText(fileNameInServer);
                    InfiniteProgress prog = new InfiniteProgress();
                    Dialog dlg = prog.showInifiniteBlocking();
                    cr.setDisposeOnCompletion(dlg);
                    NetworkManager.getInstance().addToQueueAndWait(cr);
                } catch (IOException ex) {
                }
            }
        });
        User user = new User();
        demanderPart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Date datep = Date.valueOf(dateNaiss.getText());
                PartenaireService ps = new PartenaireService();
                user.setNomEntreprisePro(nomE.getText());
                user.setTelBureauPro(numE.getText());
                user.setSpecialitePart(p_spec.getText());
                user.setEmail(email.getText());
                user.setGradePro(p.getText());
                user.setAdresse(adresse.getText());
                user.setUrlLogoPro(logo.getText());
                user.setId(User.user.getId());
                //user.setDateNaissUser(datep);
                ps.DemandePartPro(user);
                ToastBar.showMessage("demande de partenariat envoyée avec succées", FontImage.MATERIAL_DONE);
            }
        });

        theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));

        demandePartPro.getToolbar().addCommandToLeftBar("Back", img.getImage(), new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    ProductService pServ = new ProductService();
                    AllProduct ap;
                    ap = new AllProduct(pServ.getList());
                    ap.getF().show();
                } catch (IOException ex) {
                   
                }

            }
        });

    }

    public Form getDemandePartPro() {
        return demandePartPro;
    }

    public void setDemandePartPro(Form demandePartPro) {
        this.demandePartPro = demandePartPro;
    }

}
