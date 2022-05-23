package mi1.todolist.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Qas implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    public String getConsigne() {
        return enonce;
    }

    public String getType() {
        return reponse;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

}