package mi1.roberben_tougmaa_GrowCerveau.db;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = Exercice.class, parentColumns = "id", childColumns = "idE",
        onUpdate = CASCADE, onDelete = CASCADE)})
public class Qas implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "idE")
    private int idE;

    @ColumnInfo(name = "enonce")
    private String enonce;

    @ColumnInfo(name = "reponse")
    private String reponse;

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

    public void setIdE(int id) { idE = id; }

    public String getEnonce() {
        return enonce;
    }

    public String getReponse() {
        return reponse;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

}