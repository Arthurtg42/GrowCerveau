package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.User;

public class InscriptionActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    // VIEW
    private EditText nom;
    private EditText prenom;
    private EditText pseudo;
    private EditText mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void InscriptionActivityInscription(View view){
        // Récupération des views
        nom = findViewById(R.id.InscriptionActivity_Nom);
        prenom = findViewById(R.id.InscriptionActivity_Prenom);
        pseudo = findViewById(R.id.InscriptionActivity_Pseudo);
        mdp = findViewById(R.id.InscriptionActivity_Mot_de_passe);

        // Vérification de la validité des saisies
        if(nom.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Nom", Toast.LENGTH_LONG).show();
        }
        else if(prenom.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Prenom", Toast.LENGTH_LONG).show();
        }
        else if(pseudo.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Pseudo", Toast.LENGTH_LONG).show();
        }
        else if(mdp.getText().toString().trim()+"" == ""){
            Toast.makeText(getApplicationContext(), "Veuillez remplir le Mot de passe", Toast.LENGTH_LONG).show();
        }
        else if(prenom.getText().toString().trim().length() < 3 || prenom.getText().toString().trim().length() >20){
            Toast.makeText(getApplicationContext(), "Le prenom doit faire entre 3 et 20 caractères", Toast.LENGTH_LONG).show();
        }
        else if(nom.getText().toString().trim().length() < 3 || nom.getText().toString().trim().length() >20){
            Toast.makeText(getApplicationContext(), "Le mot de passe doit faire entre 3 et 20 caractères", Toast.LENGTH_LONG).show();
        }
        else if(pseudo.getText().toString().trim().length() < 3 || pseudo.getText().toString().trim().length() >10){
            Toast.makeText(getApplicationContext(), "Le pseudo doit faire entre 3 et 10 caractères", Toast.LENGTH_LONG).show();
        }
        else if(mdp.getText().toString().trim().length() < 3 || mdp.getText().toString().trim().length() >10){
            Toast.makeText(getApplicationContext(), "Le mot de passe doit faire entre 3 et 10 caractères", Toast.LENGTH_LONG).show();
        }
        else{

            /**
             * Création d'une classe asynchrone pour sauvegarder l'utilisateur
             */
            class SaveUser extends AsyncTask<Void, Void, User> {

                @Override
                protected User doInBackground(Void... voids) {

                    // Création du user
                    User user = new User();
                    user.setNom(nom.getText().toString().trim());
                    user.setPrenom(prenom.getText().toString().trim());
                    user.setPseudo(pseudo.getText().toString().trim());
                    user.setMdp(mdp.getText().toString().trim());

                    // Ajout à la base
                    mDb.getAppDatabase()
                            .userDao()
                            .insert(user);

                    return user;
                }

                @Override
                protected void onPostExecute(User user) {
                    super.onPostExecute(user);
                    // Quand le user est enregistré,
                    /////////////////////////
                    // On change de page pour la page de connexion
                    // Création d'une intention
                    Intent intent = new Intent(InscriptionActivity.this, CompteActivity.class);
                    // Lancement de la demande de changement d'activité
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Enregistré", Toast.LENGTH_LONG).show();
                    // on arrête l'activité InscriptionActivity (on l'enleve de la pile d'activités)
                    finish();
                }
            }
            //////////////////////////
            // IMPORTANT bien penser à executer la demande asynchrone
            SaveUser su = new SaveUser();
            su.execute();
        }
    }
}
