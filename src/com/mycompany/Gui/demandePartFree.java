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
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author amalb
 */
public class demandePartFree {

    private Resources theme;
    Form demandePartFree;

    TextField numE;
    TextField specialite;

    Label logo;
    Button logoB;

    Label lblnumE;
    Label lblspecialite;

    Label lbllogo;

    Button demanderPart;

    public demandePartFree() {
        demandePartFree = new Form("Demander Partenariat Free", new FlowLayout(Component.CENTER, Component.CENTER));

        numE = new TextField("", "Votre Numero:", 20, TextArea.ANY);
        specialite = new TextField("", "Specialité", 20, TextArea.ANY);
        logo = new Label();
        logoB = new Button("insérer votre photo");

       
        lblspecialite = new Label("Spécialité");

        lbllogo = new Label("Photo: ");

        Picker p_spec = new Picker();
        String[] specialites = {"Patisserie", "bijouterie", "Habillement", "epicerie", "tapis", "decoration"};
        p_spec.setStrings(specialites);
        p_spec.setSelectedString(specialites[0]);
        demanderPart = new Button("Demander");
        Container c = new Container(BoxLayout.y());


        c.add(lblspecialite);
        c.add(p_spec);

        c.add(lbllogo);
        c.add(logoB);
        c.add(logo);

        c.add(demanderPart);
        demandePartFree.add(c);
        
          logoB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String fileNameInServer = "";
                    MultipartRequest cr = new MultipartRequest();
                    String filepath = Capture.capturePhoto(-1, -1);
                    cr.setUrl("http://localhost/Medina_VersionFinale/web/php/uploadimage.php");

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

                user.setTelBureauPro(numE.getText());
                user.setSpecialitePart(p_spec.getText());

                user.setUrlLogoPro(logo.getText());
                user.setId(User.user.getId());

                ps.DemandePartFreelancer(user);
                ToastBar.showMessage("demande de partenariat envoyée avec succées", FontImage.MATERIAL_DONE);
            }
        });

        theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));

        demandePartFree.getToolbar().addCommandToLeftBar("Back", img.getImage(), new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeForm hf = new HomeForm();
                hf.getF().show();
            }
        });

    }

    public Form getDemandePartFree() {
        return demandePartFree;
    }

    public void setDemandePartFree(Form demandePartFree) {
        this.demandePartFree = demandePartFree;
    }
    
    

}
