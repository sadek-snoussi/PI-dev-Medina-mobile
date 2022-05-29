/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entity;

import java.util.Collection;
import java.util.Date;


/**
 *
 * @author admin
 */
public class Event{

    
    private Integer idEvent;
    private String nomEvent;
    private String urlafficheevent;
    private Date dateEvent;
    private Integer nbreStand;
    private String objectifEvent;
    private String lieux;
    private Integer nbrePlace;
    private String descriptionevent;
    private String typeEvent;

    
    
    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------    
    
    

    public Event() {
    }

    public Event(Integer idEvent, String nomEvent, String urlafficheevent, Date dateEvent, Integer nbreStand, String objectifEvent, String lieux, Integer nbrePlace, String descriptionevent, String typeEvent) {
        this.idEvent = idEvent;
        this.nomEvent = nomEvent;
        this.urlafficheevent = urlafficheevent;
        this.dateEvent = dateEvent;
        this.nbreStand = nbreStand;
        this.objectifEvent = objectifEvent;
        this.lieux = lieux;
        this.nbrePlace = nbrePlace;
        this.descriptionevent = descriptionevent;
        this.typeEvent = typeEvent;
      
    }

    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------   

    public Event(String nomEvent, String objectifEvent, String lieux, String descriptionevent) {
        this.nomEvent = nomEvent;
        this.objectifEvent = objectifEvent;
        this.lieux = lieux;
       
        this.descriptionevent = descriptionevent;
    }
    

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getUrlafficheevent() {
        return urlafficheevent;
    }

    public void setUrlafficheevent(String urlafficheevent) {
        this.urlafficheevent = urlafficheevent;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public Integer getNbreStand() {
        return nbreStand;
    }

    public void setNbreStand(Integer nbreStand) {
        this.nbreStand = nbreStand;
    }

    public String getObjectifEvent() {
        return objectifEvent;
    }

    public void setObjectifEvent(String objectifEvent) {
        this.objectifEvent = objectifEvent;
    }

    public String getLieux() {
        return lieux;
    }

    public void setLieux(String lieux) {
        this.lieux = lieux;
    }

    public Integer getNbrePlace() {
        return nbrePlace;
    }

    public void setNbrePlace(Integer nbrePlace) {
        this.nbrePlace = nbrePlace;
    }

    public String getDescriptionevent() {
        return descriptionevent;
    }

    public void setDescriptionevent(String descriptionevent) {
        this.descriptionevent = descriptionevent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

  
    
    
    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvent != null ? idEvent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.idEvent == null && other.idEvent != null) || (this.idEvent != null && !this.idEvent.equals(other.idEvent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Event[ idEvent=" + idEvent + " ]";
    }
    
}
