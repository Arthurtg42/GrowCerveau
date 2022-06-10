package mi1.roberben_tougmaa_GrowCerveau.db;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(primaryKeys = {"idE", "idQ"},
        foreignKeys = {@ForeignKey(entity = Qas.class, parentColumns = "id", childColumns = "idQ",
                        onUpdate = CASCADE, onDelete = CASCADE),
                        @ForeignKey(entity = Exercice.class, parentColumns = "id", childColumns = "idE",
                        onUpdate = CASCADE, onDelete = CASCADE)})
public class AssociationQ_E implements Serializable {

    @ColumnInfo(name = "idQ")
    private int idQ;

    @ColumnInfo(name = "idE")
    private int idE;

    /*
     * Getters and Setters
     * */
    public int getIdQ() {
        return idQ;
    }

    public void setIdQ(int id) {
        idQ = id;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int id) {
        idE = id;
    }

}