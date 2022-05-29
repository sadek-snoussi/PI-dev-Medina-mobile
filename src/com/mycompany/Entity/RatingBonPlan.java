/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entity;

//import java.util.Objects;





/**
 *
 * @author khali
 */
public class RatingBonPlan {

    private Integer id_rating;
    private Double rating;
    private Bonplan id_bonplan;
    private User id_user;

    public RatingBonPlan() {
    }



    public RatingBonPlan(Integer id_rating, Double rating, Bonplan id_bonplan, User id_user) {
        this.id_rating = id_rating;
        this.rating = rating;
        this.id_bonplan = id_bonplan;
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "RatingBonPlan{" + "id_rating=" + id_rating + ", rating=" + rating + ", id_bonplan=" + id_bonplan + ", id_user=" + id_user + '}';
    }

    public Integer getId_rating() {
        return id_rating;
    }

    public void setId_rating(Integer id_rating) {
        this.id_rating = id_rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Bonplan getId_bonplan() {
        return id_bonplan;
    }

    public void setId_bonplan(Bonplan id_bonplan) {
        this.id_bonplan = id_bonplan;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

}
