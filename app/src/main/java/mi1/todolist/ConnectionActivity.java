package mi1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;

public class ConnectionActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;

    // DATA
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void ConnectionActivityConnexion(View view){

        if(true){
            //La connection est réussie

            ////////////
            // On charge la home page
            // Création d'une intention
            Intent intent = new Intent(this, MainActivity.class);
            // Lancement de la demande de changement d'activité
            startActivityForResult(intent, REQUEST_CODE_ADD);
        }
        else{
            //La connection a échoué
        }

    }

}
