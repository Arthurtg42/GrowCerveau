package mi1.todolist;

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
        Intent intent = new Intent(this, ConnectionActivity.class);
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
        // Ajout de l'ID_SESSION (fixé à 0 dans l'intent car anonyme)
        intent.putExtra(CodeAndKey.ID_SESSION, 0);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
    }
}
