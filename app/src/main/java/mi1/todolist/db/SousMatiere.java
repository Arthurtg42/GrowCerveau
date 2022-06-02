package mi1.todolist.db;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = SousMatiere.class, parentColumns = "id", childColumns = "idM",
        onUpdate = CASCADE, onDelete = CASCADE)})
public class SousMatiere implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "idM")
    private int idM;

    @ColumnInfo(name = "nom")
    private String nom;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdM() { return idM; }

    public void setIdM(int id) {
        idM = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) { this.nom = nom; }

}