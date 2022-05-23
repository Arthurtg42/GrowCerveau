package mi1.todolist.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Exercice implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "consigne")
    private String consigne;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "matiere")
    private String matiere;

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
        return consigne;
    }

    public String getType() {
        return type;
    }

    public String getPreom() {
        return matiere;
    }

    public void setConsigne(String consigne) {
        this.consigne = consigne;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

}