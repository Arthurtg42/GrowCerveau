package mi1.todolist.db;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = Exercice.class, parentColumns = "id", childColumns = "idE",
        onUpdate = CASCADE, onDelete = CASCADE)})
public class Qcm implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "idE")
    private int idE;

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

    public int getIdE() { return idE; }

    public void setIdE(int id) {
        idE = id;
    }

    public String getBonneReponse() {
        return bonneReponse;
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

    public void setBonneReponse1(String bonneReponse) {
        this.bonneReponse = bonneReponse;
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