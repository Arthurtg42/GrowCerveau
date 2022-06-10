package mi1.roberben_tougmaa_GrowCerveau;

import android.app.Application;

import mi1.roberben_tougmaa_GrowCerveau.db.Matiere;
import mi1.roberben_tougmaa_GrowCerveau.db.SousMatiere;
import mi1.roberben_tougmaa_GrowCerveau.db.User;

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
