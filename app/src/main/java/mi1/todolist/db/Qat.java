package mi1.todolist.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Qat implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "bloc1")
    private String bloc1;

    @ColumnInfo(name = "bloc2")
    private String bloc2;

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

    public String getBloc1() {
        return bloc1;
    }

    public String getBloc2() {
        return bloc2;
    }

    public String getReponse() {
        return reponse;
    }

    public void setBloc1(String bloc1) {
        this.bloc1 = bloc1;
    }

    public void setBloc2(String bloc2) {
        this.bloc2 = bloc2;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

}