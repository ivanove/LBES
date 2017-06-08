package com.lbes.application.Beans;

/**
 * Created by next on 21/01/17.
 */



import com.lbes.application.utils.JsonSI;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RowInfo extends RealmObject {

    @PrimaryKey
    private String id;
    private RealmList<MyString> cells = null;
    private String cell = null;


    public RowInfo() {
    }

    public RowInfo(String id, RealmList<MyString> cells, String cell) {
        this.id = id;
        this.cells = cells;
        this.cell = cell;
    }

    public RealmList<MyString> getCells() {
        return cells;
    }

    public void setCells(RealmList<MyString> cells) {
        this.cells = cells;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        cells = JsonSI.JsonToString(cell);
        this.cell = cell;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}