/**
 * 
 */
package com.lbes.application.Beans;



import com.lbes.application.utils.Utils1;

import java.io.IOException;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ElementSwipe  extends RealmObject {

   @PrimaryKey
   private int id;
/*
	private int id_generated;
*/


	private Home_swipe home_swipe;


	private String image;
	private String caption;
	private int page_id;


    public Home_swipe getHome_swipe() {
        return home_swipe;
    }

    public void setHome_swipe(Home_swipe home_swipe) {
        this.home_swipe = home_swipe;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        this.image = image;


        }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }



    public ElementSwipe() {
    }

    public ElementSwipe(int id, Home_swipe home_swipe, String image, String caption, int page_id) {
        this.id = id;
        this.home_swipe = home_swipe;
        this.image = image;
        this.caption = caption;
        this.page_id = page_id;
    }
}