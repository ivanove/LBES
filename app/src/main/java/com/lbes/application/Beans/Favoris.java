package com.lbes.application.Beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nizar on 07/06/17.
 */

public class Favoris extends RealmObject {
    @PrimaryKey
    private int id;
    private int image;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Favoris() {
    }

    public Favoris(int id, int image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }
}
