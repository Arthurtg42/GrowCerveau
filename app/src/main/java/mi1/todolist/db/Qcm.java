package mi1.todolist.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Qcm implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "BonneReponse")
    private String bonneReponse;

    @ColumnInfo(name = "MauvaiseReponse1")
    private String mauvaisReponse1;

    @ColumnInfo(name = "MauvaiseReponse2")
    private String mauvaisReponse2;

    @ColumnInfo(name = "MauvaiseReponse3")
    private String mauvaisReponse3;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMauvaiseReponse1() {
        return mauvaisReponse1;
    }

    public String getMauvaiseReponse2() {
        return mauvaisReponse2;
    }

    public String getMauvaiseReponse3() {
        return mauvaisReponse3;
    }

    public void setMauvaiseReponse1(String mauvaisReponse1) {
        this.mauvaisReponse1 = mauvaisReponse1;
    }

    public void setMauvaiseReponse2(String mauvaisReponse2) {
        this.mauvaisReponse2 = mauvaisReponse2;
    }

    public void setMauvaiseReponse3(String mauvaisReponse3) {
        this.mauvaisReponse3 = mauvaisReponse3;
    }

}