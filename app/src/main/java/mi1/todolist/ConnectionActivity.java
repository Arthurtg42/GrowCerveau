package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;

public class ConnectionActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    // VIEW
    private EditText pseudo;
    private EditText mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void ConnectionActivityConnexion(View view){
        pseudo = findViewById(R.id.ConnectionActivity_pseudo);
        mdp = findViewById(R.id.ConnectionActivity_mdp);

        class CheckConnection extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                // renvoi la reponse de la DatabaseClient
                return mDb.getAppDatabase().userDao().getLog(pseudo.getText().toString(), mdp.getText().toString());
            }

            @Override
            protected void onPostExecute(Integer idUser) {
                super.onPostExecute(idUser);
                if(idUser != 0){
                    //La connection est réussie

                    // On charge la home page
                    // Création d'une intention
                    Intent intent = new Intent(ConnectionActivity.this, HomePageActivity.class);
                    // Ajout de l'id de l'utilisateur à l'intent
                    intent.putExtra(CodeAndKey.ID_SESSION, idUser);
                    // Lancement de la demande de changement d'activité
                    startActivity(intent);
                    finish();
                }
                else{
                    // La connection a échoué, on affiche l'erreur
                    Toast.makeText(getApplicationContext(), "Mot de passe ou pseudo incorrect", Toast.LENGTH_LONG).show();
                    // Reset du champs mdp
                    mdp.setText("");
                }
            }
        }

        if(pseudo.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Pseudo", Toast.LENGTH_LONG).show();
        }
        else if(mdp.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Mot de passe", Toast.LENGTH_LONG).show();
        }
        else {
            //On execute en async
            CheckConnection ChechC = new CheckConnection();
            ChechC.execute();
        }
    }
}
