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

    // DATA
    private DatabaseClient mDb;

    private EditText Pseudo;
    private EditText Mdp;


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

                Log.d("QUERY", "GOOOOOOOOOOOOOOOOOOOOOD4");
                if(Pseudo.getText().toString().trim()+"" == ""){
                    Toast.makeText(getApplicationContext(), "Veuillez remplir le Pseudo", Toast.LENGTH_LONG).show();
                }
                else if(Mdp.getText().toString().trim()+"" == ""){
                    Toast.makeText(getApplicationContext(), "Veuillez remplir le Mot de passe", Toast.LENGTH_LONG).show();
                }
                else {
                    return mDb.getAppDatabase().userDao().getLog(Pseudo.getText().toString(), Mdp.getText().toString());
                }
                Log.d("QUERY", "GOOOOOOOOOOOOOOOOOOOOOD3");
                return 0;

            }

            @Override
            protected void onPostExecute(Integer idUser) {
                super.onPostExecute(idUser);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(idUser);
                finish();

                Log.d("QUERY", "GOOOOOOOOOOOOOOOOOOOOOD2");
            }
        }

        //On execute
        CheckConnection st = new CheckConnection();
        st.execute();
        /*
        st.get

        if(idUser != 0){
            //La connection est réussie

            ////////////
            // On charge la home page
            // Création d'une intention
            Intent intent = new Intent(this, HomePageActivity.class);
            // Lancement de la demande de changement d'activité
            startActivityForResult(intent, REQUEST_CODE_ADD);
            Log.d("QUERY", "GOOOOOOOOOOOOOOOOOOOOOD");
        }
        else{
            //La connection a échoué
            Toast.makeText(getApplicationContext(), "Mot de passe ou pseudo incorrect", Toast.LENGTH_LONG).show();
        }

         */

    }


}
