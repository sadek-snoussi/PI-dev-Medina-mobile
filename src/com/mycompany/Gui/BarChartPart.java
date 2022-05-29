/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entity.Produit;
import com.mycompany.Entity.Videodiy;
import com.mycompany.Service.PartenaireService;
import com.mycompany.Service.ProductService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amalb
 */
public class BarChartPart extends AbstractDemoChart {

    Form barchart = new Form("Statistiques");

    public String getName() {
        return "Sales horizontal bar chart";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "";
    }

    @Override
    public Component getChartModelEditor() {
        return null;
    }

    @Override
    public String getChartTitle() {
        return "";
    }

    @Override
    public Component execute() {
        String[] titles = new String[]{"quantité Produit dispo", "quantité Produit vendues", "nombre de videos postées"};
        List<double[]> Bars = new ArrayList<double[]>();
        PartenaireService ps = new PartenaireService();
        ArrayList<Produit> lisproduits = ps.listProduitByPart();
        ArrayList<Videodiy> lisvideos = ps.listVideosByPart();
        int qtedispoMar = 0, qtevenduMar = 0, nbrevideosJan = 0,
                qtedispoJan = 0, qtevenduJan = 0, nbrevideosFev = 0,
                qtedispoFev = 0, qtevenduFev = 0, nbrevideosMar = 0,
                qtedispoAvr = 0, qtevenduAvr = 0, nbrevideosAvr = 0,
                qtedispoMai = 0, qtevenduMai = 0, nbrevideosMai = 0,
                qtedispoJuin = 0, qtevenduJuin = 0, nbrevideosJuin = 0;

        String date;
        String subdate;
        for (Produit produit : lisproduits) {

            date = produit.getDateExpoProduit().toString().trim();
            subdate = date.substring(4, 7);

            if (subdate.equals("Jan")) {
                qtedispoJan += produit.getQteDispoProduit();
                qtevenduJan += produit.getQteVenduProduit();
            } else if (subdate.equals("Feb")) {
                qtedispoFev += produit.getQteDispoProduit();
                qtevenduFev += produit.getQteVenduProduit();
            } else if (subdate.equals("Mar")) {
                qtedispoMar += produit.getQteDispoProduit();
                qtevenduMar += produit.getQteVenduProduit();

            } else if (subdate.equals("Apr")) {

                qtedispoAvr += produit.getQteDispoProduit();
                qtevenduAvr += produit.getQteVenduProduit();
            } else if (subdate.equals("May")) {
                qtedispoMai += produit.getQteDispoProduit();
                qtevenduMai += produit.getQteVenduProduit();
            } else if (subdate.equals("Jun")) {
                qtedispoJuin += produit.getQteDispoProduit();
                qtevenduJuin += produit.getQteVenduProduit();
            }
        }

        for (Videodiy video : lisvideos) {
            date = video.getDateExpoVideo().toString().trim();
            subdate = date.substring(4, 7);

            if (subdate.equals("Jan")) {
                nbrevideosJan++;

            } else if (subdate.equals("Feb")) {
                nbrevideosFev++;
            } else if (subdate.equals("Mar")) {
                nbrevideosMar++;

            } else if (subdate.equals("Apr")) {
                nbrevideosAvr++;

            } else if (subdate.equals("May")) {
                nbrevideosMai++;
            } else if (subdate.equals("Jun")) {
                nbrevideosJuin++;
            }
        }

        Bars.add(new double[]{qtedispoJan, qtedispoFev, qtedispoMar, qtedispoAvr, qtedispoMai, qtedispoJuin});
        Bars.add(new double[]{qtevenduJan, qtevenduFev, qtevenduMar, qtevenduAvr, qtevenduMai, qtevenduJuin});
        Bars.add(new double[]{nbrevideosJan, nbrevideosFev, nbrevideosMar, nbrevideosAvr, nbrevideosMai, nbrevideosJuin});
        int[] colors = new int[]{ColorUtil.CYAN, ColorUtil.BLUE, ColorUtil.GREEN};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        setChartSettings(renderer, "Produits et Videos ", "Mois", "Unités ", 0,
                6, 0, 150, ColorUtil.BLACK, ColorUtil.MAGENTA);
        renderer.setXLabels(1);
        renderer.setYLabels(10);
        renderer.addXTextLabel(1, "Jan");
        renderer.addXTextLabel(2, "Fev");
        renderer.addXTextLabel(3, "Mar");
        renderer.addXTextLabel(4, "Apr");
        renderer.addXTextLabel(5, "May");
        renderer.addXTextLabel(6, "Jun");
        initRendererer(renderer);
        int length = renderer.getSeriesRendererCount();
        for (int i = 1; i < length; i++) {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }

        com.codename1.charts.views.BarChart chart = new com.codename1.charts.views.BarChart(buildBarDataset(titles, Bars), renderer,
                com.codename1.charts.views.BarChart.Type.DEFAULT);
        return newChart(chart);

    }

    public BarChartPart() {

        Resources theme;
        theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer();
        img.setImage(theme.getImage("back-command.png"));
        
           barchart.getToolbar().addCommandToLeftBar(" ", img.getImage(), new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    ProductService pServ=new ProductService();
                    AllProduct ap=new AllProduct(pServ.getList());
                    ap.getF().show();
                } catch (IOException ex) {
                }
            }
        });

      

        barchart.add(execute());
        barchart.show();

    }
}
