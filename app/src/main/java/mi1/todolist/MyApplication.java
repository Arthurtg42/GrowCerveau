package mi1.todolist;

import android.app.Application;

import mi1.todolist.db.Matiere;
import mi1.todolist.db.SousMatiere;
import mi1.todolist.db.User;

public class MyApplication extends Application {

    // DATA
    private User user;
    private Matiere matiere;
    private SousMatiere sousMatiere;

    // Getters
    public User getUser(){
        return user;
    }

    public Matiere getMatiere(){
        return matiere;
    }

    public SousMatiere getSousMatiere(){
        return sousMatiere;
    }

    // Setters
    public void setUser(User user){
        this.user = user;
    }

    public void setMatiere(Matiere matiere){
        this.matiere = matiere;
    }

    public void setSousMatiere(SousMatiere sousMatiere){
        this.sousMatiere = sousMatiere;
    }
}
