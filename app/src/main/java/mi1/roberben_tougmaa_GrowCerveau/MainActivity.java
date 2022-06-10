package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MainActivityConnexion(View view){
        // Création d'une intention
        Intent intent = new Intent(this, CompteActivity.class);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
    }

    public void MainActivityInscription(View view){
        // Création d'une intention
        Intent intent = new Intent(this, InscriptionActivity.class);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
    }

    public void MainActivityAnonyme(View view){
        // Création d'une intention
        Intent intent = new Intent(this, HomePageActivity.class);
        // Fixé le user global à null car user anonyme
        ((MyApplication) this.getApplication()).setUser(null);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
    }
}
