package mi1.todolist.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class AssociationQ_E implements Serializable {

    @PrimaryKey
    private int idQ;

    @PrimaryKey
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