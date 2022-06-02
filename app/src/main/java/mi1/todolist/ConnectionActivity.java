package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.User;

public class ConnectionActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final String ID_SESSION = "id_session";


    // DATA
    private DatabaseClient mDb;

    public EditText Pseudo;
    public EditText Mdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void ConnectionActivityConnexion(View view){

        Pseudo = findViewById(R.id.ConnectionActivity_pseudo);
        Mdp = findViewById(R.id.ConnectionActivity_mdp);

        class CheckConnection extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {

                // adding to database
                return mDb.getAppDatabase().userDao().getLog(Pseudo.getText().toString(), Mdp.getText().toString());

            }

            @Override
            protected void onPostExecute(Integer idUser) {
                super.onPostExecute(idUser);

                setResult(idUser);

                if(idUser != 0){
                    //La connection est réussie

                    ////////////
                    // On charge la home page
                    // Création d'une intention
                    Intent intent = new Intent(ConnectionActivity.this, HomePageActivity.class);
                    // ajoute la matière à l'intent
                    intent.putExtra(ID_SESSION, idUser);
                    // Lancement de la demande de changement d'activité
                    startActivityForResult(intent, REQUEST_CODE_ADD);
                    finish();
                }
                else{
                    //La connection a échoué, on affiche l'erreur
                    Toast.makeText(getApplicationContext(), "Mot de passe ou pseudo incorrect", Toast.LENGTH_LONG).show();
                    //et on reset le champs mdp
                    Mdp.setText("");
                }

            }
        }


        if(Pseudo.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Pseudo", Toast.LENGTH_LONG).show();
        }
        else if(Mdp.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Mot de passe", Toast.LENGTH_LONG).show();
        }
        else {
            //On execute en async
            CheckConnection ChechC = new CheckConnection();
            ChechC.execute();
        }



    }


}
