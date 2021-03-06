package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mi1.roberben_tougmaa_GrowCerveau.db.DatabaseClient;
import mi1.roberben_tougmaa_GrowCerveau.db.User;

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

        // récupération des views
        pseudo = findViewById(R.id.ConnectionActivity_pseudo);
        mdp = findViewById(R.id.ConnectionActivity_mdp);

        // Récupération du pseudo de l'intent
        String pseudoIntent = (String) getIntent().getStringExtra(CodeAndKey.PSEUDO_KEY);
        if(pseudoIntent != null){
            // Pré-remplissage du champs pseudo
            pseudo.setText(pseudoIntent);
            // rendre l'input non éditable
            pseudo.setEnabled(false);
            // couleur à noir
            pseudo.setTextColor(Color.BLACK);
        }
    }

    public void ConnectionActivityConnexion(View view){

        class CheckConnection extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                Integer idUser = mDb.getAppDatabase().userDao().getIdUser(pseudo.getText().toString(), mdp.getText().toString());
                // renvoi la reponse de la DatabaseClient
                return (idUser != null) ? mDb.getAppDatabase().userDao().getUser(idUser) : null;
            }

            @Override
            protected void onPostExecute(User user_DB) {
                super.onPostExecute(user_DB);
                if(user_DB != null){
                    //La connection est réussie
                    // On charge la home page
                    // Création d'une intention
                    Intent intent = new Intent(ConnectionActivity.this, HomePageActivity.class);
                    // Fixé le user global avec le user récupéré
                    ((MyApplication) ConnectionActivity.this.getApplication()).setUser(user_DB);
                    // Lancement de la demande de changement d'activité
                    startActivity(intent);
                    // La connection a réussi, on notifie le user
                    Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_LONG).show();
                    // fin de la ConnectionActivity
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

    public void GoBack(View view){
        super.finish();
    }
}
