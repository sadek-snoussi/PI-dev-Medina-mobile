/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Gui;


import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.animations.AnimationObject;
import com.codename1.ui.animations.Timeline;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;


/**
 *
 * @author admin
 */
public class SplashScreen {

    Form splashForm;
    
    
    
    public void startApp() throws IOException {
    
        splashForm=new Form();
    
        
      //  Toolbar tb=splashForm.getToolbar();
//        tb.getAllStyles().setBgTransparency(0);
        
       // Label labelToolbar = new Label ("Text");
        //tb.add (BorderLayout.CENTER , labelToolbar);
    splashForm.getToolbar().setUIID("Container");
    splashForm.setLayout(new BorderLayout());

    Image Splash = Image.createImage("/islamic-art (4).png");
    AnimationObject[] animations = new AnimationObject[] {
        AnimationObject.createAnimationImage(Splash, 90, -20)
    };
    
    animations[0].defineOrientation(AnimationObject.MOTION_TYPE_LINEAR, 0, 1500, 0, 359);

    Timeline t = Timeline.createTimeline(1500, animations, new Dimension(Splash.getWidth(), Splash.getHeight()));
    
    
    splashForm.addComponent(BorderLayout.CENTER, new Label(t));
   

    splashForm.show();
    
    
    
    
}
    

    
}
