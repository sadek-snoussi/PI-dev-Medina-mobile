/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.AbstractChart;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.Entity.Produit;
import com.mycompany.Service.PartenaireService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amalb
 */
public class StatProduits_Videos {
       Form f;
       PartenaireService ps =new PartenaireService();
       
       
       
       private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        return renderer;
    }

    /**
     * Builds a category series using the provided values.
     *
     * @param titles the series titles
     * @param values the values
     * @return the category series
     */
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
      
        series.add("quantité dispo Produit",values[0]);
        series.add("quantité vendu Produit",values[1]);

        return series;
    }

    public Form createPieChartForm() {
        // Generate the values
      

         ArrayList<Produit>produits=ps.listProduitByPart();
         int qtedispo=0,qtevendu=0;
         for (Produit produit : produits) {
             qtedispo+=produit.getQteDispoProduit();
             qtevendu+=produit.getQteVenduProduit();
            
        }
         
           double[] values = new double[]{qtedispo, qtevendu};
        // Set up the renderer
        int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN, 2};
        DefaultRenderer renderer = buildCategoryRenderer(colors);

        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(true);
        r.setGradientStart(0, ColorUtil.BLUE);
        r.setGradientStop(0, ColorUtil.GREEN);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        f = new Form("Budget", new BorderLayout());
        f.add(BorderLayout.CENTER, c);
        return f;

    }

    public void StatistiqueTest() {

        f = createPieChartForm();

        f.getToolbar().addCommandToRightBar("back", null, (ev) -> {
            HomeForm h = new HomeForm();
            h.getF().show();
        });
        f.show();

    }

    public StatProduits_Videos() {
        
        StatistiqueTest();
    }
    
    

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    
    
}
