package mi1.todolist.db;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = SousMatiere.class, parentColumns = "id", childColumns = "idSM",
        onUpdate = CASCADE, onDelete = CASCADE)})
public class Exercice implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "idSM")
    private int idSM;

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

    public int getIdSM() { return idSM; }

    public void setIdSM(int id) {
        idSM = id;
    }

    public String getConsigne() {
        return consigne;
    }

    public String getType() {
        return type;
    }

    public String getMatiere() {
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