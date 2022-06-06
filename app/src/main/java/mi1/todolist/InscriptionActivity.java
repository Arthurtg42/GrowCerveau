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

public class InscriptionActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    //View
    private EditText Nom;
    private EditText Prenom;
    private EditText Pseudo;
    private EditText Mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void InscriptionActivityInscription(View view){

        //On recupère tous les champs
        Nom = findViewById(R.id.InscriptionActivity_Nom);
        Prenom = findViewById(R.id.InscriptionActivity_Prenom);
        Pseudo = findViewById(R.id.InscriptionActivity_Pseudo);
        Mdp = findViewById(R.id.InscriptionActivity_Mot_de_passe);

        //On check s'ils sont valides (genre pas vides)
        if(Nom.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Nom", Toast.LENGTH_LONG).show();
        }
        else if(Prenom.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Prenom", Toast.LENGTH_LONG).show();
        }
        else if(Pseudo.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Pseudo", Toast.LENGTH_LONG).show();
        }
        else if(Mdp.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Mot de passe", Toast.LENGTH_LONG).show();
        }
        else if(Prenom.getText().toString().trim().length() < 3 || Prenom.getText().toString().trim().length() >20){
            Toast.makeText(getApplicationContext(), "Le prenom doit faire entre 3 et 20 caractères", Toast.LENGTH_LONG).show();
        }
        else if(Nom.getText().toString().trim().length() < 3 || Nom.getText().toString().trim().length() >20){
            Toast.makeText(getApplicationContext(), "Le mot de passe doit faire entre 3 et 20 caractères", Toast.LENGTH_LONG).show();
        }
        else if(Pseudo.getText().toString().trim().length() < 3 || Pseudo.getText().toString().trim().length() >10){
            Toast.makeText(getApplicationContext(), "Le pseudo doit faire entre 3 et 10 caractères", Toast.LENGTH_LONG).show();
        }
        else if(Mdp.getText().toString().trim().length() < 3 || Mdp.getText().toString().trim().length() >10){
            Toast.makeText(getApplicationContext(), "Le mot de passe doit faire entre 3 et 10 caractères", Toast.LENGTH_LONG).show();
        }
        else{

            /**
             * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
             */
            class SaveTask extends AsyncTask<Void, Void, User> {

                @Override
                protected User doInBackground(Void... voids) {

                    // creating a task
                    User user = new User();
                    user.setNom(Nom.getText().toString().trim());
                    user.setPrenom(Prenom.getText().toString().trim());
                    user.setPseudo(Pseudo.getText().toString().trim());
                    user.setMdp(Mdp.getText().toString().trim());

                    // adding to database
                    mDb.getAppDatabase()
                            .userDao()
                            .insert(user);

                    return user;
                }

                @Override
                protected void onPostExecute(User user) {
                    super.onPostExecute(user);

                    // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                    setResult(RESULT_OK);
                    finish();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }

            //////////////////////////
            // IMPORTANT bien penser à executer la demande asynchrone
            SaveTask st = new SaveTask();
            st.execute();

            /////////////////////////
            // On change de page pour la page de connexion
            // Création d'une intention
            Intent intent = new Intent(this, ConnectionActivity.class);
            // Lancement de la demande de changement d'activité
            startActivityForResult(intent, CodeAndKey.REQUEST_CODE_ADD);

        }


    }

}
