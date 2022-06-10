package mi1.roberben_tougmaa_GrowCerveau.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Result implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "reponse")
    private String reponse;

    @ColumnInfo(name = "reponse_uti")
    private String reponse_uti;

    @ColumnInfo(name = "enonce")
    private String enonce;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public String getReponse() {
        return reponse;
    }

    public String getEnonce() {
        return enonce;
    }

    public String getReponse_uti() {
        return reponse_uti;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReponse(String rep) {
        this.reponse = rep;
    }

    public void setReponse_uti(String rep) {
        this.reponse_uti = rep;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public boolean isCorrect(){
        return (reponse.toLowerCase().compareTo(reponse_uti.toLowerCase())==0);
    }
}