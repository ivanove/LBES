package com.lbes.application.Beans;



import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Application extends RealmObject {

   @PrimaryKey
	private int id;
	


	private Parameters parameters;

	private Home_swipe home_swipe;



    private RealmList<MyString> attributes=new RealmList<MyString>();
    private RealmList<MyString> wine_maps =new RealmList<MyString>();
   // private RealmList<MyString> agenda_groups=new RealmList<MyString>();




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }



    public Home_swipe getHome_swipe() {
        return home_swipe;
    }

    public void setHome_swipe(Home_swipe home_swipe) {
        this.home_swipe = home_swipe;
    }





    public RealmList<MyString> getAttributes() {
        return attributes;
    }

    public void setAttributes(RealmList<MyString> attributes) {
        this.attributes = attributes;
    }

    public RealmList<MyString> getWine_maps() {
        return wine_maps;
    }

    public void setWine_maps(RealmList<MyString> wine_maps) {
        this.wine_maps = wine_maps;
    }



    public Application() {    }



}
