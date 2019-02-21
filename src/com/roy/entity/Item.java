/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roy.entity;

import java.sql.Date;

/**
 *
 * @author royparsaoran 1772044
 */
public class Item {

    private int id;
    private String name;
    private double price;
    private String description;
    private boolean recomended;
    private String photo;
    private Date created;
    private Category categor;

    public Item(int id, String name, double price, String description,
            boolean recomended, String photo, Date created, Category categor) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.recomended = recomended;
        this.photo = photo;
        this.created = created;
        this.categor = categor;
    }

    public Category getCategor() {
        return categor;
    }

    public void setCategor(Category categor) {
        this.categor = categor;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecomended() {
        return recomended;
    }

    public void setRecomended(boolean recomended) {
        this.recomended = recomended;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
