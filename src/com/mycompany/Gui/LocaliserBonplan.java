/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import com.codename1.googlemaps.MapContainer;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Service.ProductService;

/**
 *
 * @author khali
 */
public class LocaliserBonplan {

    listPartenaires lp = new listPartenaires();
    ModifierProfileClient profile = new ModifierProfileClient();
    choosePartenariat demande = new choosePartenariat();
    signin signin = new signin();
    ModifierProfilePro profilePro = new ModifierProfilePro();
    ModifierProfileFree profileFree = new ModifierProfileFree();
    ProductService serv = new ProductService();
    private Resources theme;

    Form f;
    private static final String HTML_API_KEY = "AIzaSyAi6eh0HqBK-VL_NL27RsXRr8eh0DBpRU0";
    private Form current;

   

    public LocaliserBonplan() {

        Location position = LocationManager.getLocationManager().getCurrentLocationSync();
        System.out.println(position.getLatitude() + "    " + position.getLongitude());

        //////////////////////////// Declaration et parametre de base ////////////////////////////////////
        final MapContainer cnt = new MapContainer(HTML_API_KEY);
        Double LatitudeBP = AfficheBonplan.Bps.getLatitude();
        Double LongitudeBP = AfficheBonplan.Bps.getLongitude();
        Coord cord = new Coord(LatitudeBP, LongitudeBP);
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        cnt.zoom(cord, 17);
        cnt.setShowMyLocation(true);
        cnt.setCameraPosition(cord);
        Button btnAddPath = new Button("Itinéraire");
        btnAddPath.setVisible(false);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(1));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////
        f = new Form("Localisation");
        f.setLayout(new BorderLayout());
        
        
             /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////// Navigation Entre les PAGES /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
             f.getToolbar().addCommandToLeftBar("<-",null, (ev)->{
                 
                 DetailBonplan bnp = new DetailBonplan();
                 bnp.getF().show();
          });

        if (current != null) {
            current.show();
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////TEST HERE /////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////     
        ///////////////////////////////////////////////////////////////////////////////////////////////
        //Location position = LocationManager.getLocationManager().getCurrentLocationSync();
        //Check if location is turned on and your app is allowed to use it.
        System.out.println(position.getDirection());
        System.out.println(position.getLatitude());
        System.out.println(position.getLongitude());
        ////////////////////////////////////////// Ajout Position Bonplan //////////////////////////////////
        Button btnAddMarker = new Button("Afficher Position");
        btnAddMarker.addActionListener(e -> {

            btnAddPath.setVisible(true);

            cnt.setCameraPosition(new Coord(LatitudeBP, LongitudeBP));

            cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCameraPosition(),
                    AfficheBonplan.Bps.getNombonplan(),
                    "Addresse : " + AfficheBonplan.Bps.getAdresseBonplan(),
                    evt -> {
                        ToastBar.showMessage(AfficheBonplan.Bps.getAdresseBonplan(), FontImage.MATERIAL_PLACE);
                    }
            );

        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////Afficher Position Bonplan Par Default/////////////////////////////////////////////
        cnt.setCameraPosition(new Coord(LatitudeBP, LongitudeBP));
        cnt.addMarker(
                EncodedImage.createFromImage(markerImg, false),
                cnt.getCameraPosition(),
                AfficheBonplan.Bps.getNombonplan(),
                "Addresse : " + AfficheBonplan.Bps.getAdresseBonplan(),
                evt -> {
                    ToastBar.showMessage(AfficheBonplan.Bps.getAdresseBonplan(), FontImage.MATERIAL_PLACE);
                }
        );

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        Button btnMoveCamera = new Button("Move Camera");
        btnMoveCamera.addActionListener(e -> {
            cnt.setCameraPosition(new Coord(LongitudeBP, LatitudeBP));
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////Ajouter Path entre Localisation user et Bonplan/////////////////////////////////////
        btnAddPath.addActionListener(e -> {
            Coord src = new Coord(position.getLatitude(), position.getLongitude());
            Coord dest = cord;

            String encoded = getRoutesEncoded(src, dest);
            System.out.println("decode:" + encoded);
            Coord[] coords = decode(encoded);
            cnt.addPath(coords);
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////       
        ///////////////////////////////////Clear All//////////////////////////////////////////////
        Button btnClearAll = new Button("Clear All");
        btnClearAll.addActionListener(e -> {
            cnt.clearMapLayers();
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        Container root = LayeredLayout.encloseIn(
                BorderLayout.center(cnt),
                BorderLayout.south(
                        FlowLayout.encloseBottom(btnAddPath, btnAddMarker)
                )
        );

        f.add(BorderLayout.CENTER, root);
        f.show();

        theme = UIManager.initFirstTheme("/theme");

        f.getToolbar().addCommandToLeftBar(" ", theme.getImage("back-command.png"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                DetailBonplan b3 = new DetailBonplan();
                b3.getF().show();

            }
        });

    }

    public Form getF() {
        return f;
    }

    public static Coord[] decode(final String encodedPath) {
        int len = encodedPath.length();
        final ArrayList<Coord> path = new ArrayList<Coord>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new Coord(lat * 1e-5, lng * 1e-5));
        }
        Coord[] p = new Coord[path.size()];
        for (int i = 0; i < path.size(); i++) {
            p[i] = path.get(i);
        }

        return p;
    }

    public void setF(Form f) {
        this.f = f;
    }

    private String getRoutesEncoded(Coord src, Coord dest) {
        String ret = "";
        try {

            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/directions/json", false);
            request.addArgument("key", HTML_API_KEY);
            request.addArgument("origin", src.getLatitude() + "," + src.getLongitude());
            request.addArgument("destination", dest.getLatitude() + "," + dest.getLongitude());
            System.out.println(src.getLatitude() + "," + src.getLongitude());
            System.out.println(dest.getLatitude() + "," + dest.getLongitude());
            System.out.println(HTML_API_KEY);
            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("routes") != null) {
                ArrayList routes = (ArrayList) response.get("routes");
                if (routes.size() > 0) {
                    ret = ((LinkedHashMap) ((LinkedHashMap) ((ArrayList) response.get("routes")).get(0)).get("overview_polyline")).get("points").toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    



}
