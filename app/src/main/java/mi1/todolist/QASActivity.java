package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Qas;
import mi1.todolist.db.SousMatiere;

public class QASActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final String ID_SESSION = "id_session";
    private static final String ENONCE = "enonce_uti";
    private static final String REPONSE_UTI = "reponse_uti";
    private static final String REPONSE = "reponse";
    private static final String MATIERE_KEY = "matiere_key";
    private static final String SOUS_MATIERE_KEY = "sous_matiere_key";
    private static final String NB_QUEST_KEY = "nb_quest_key";

    // DATA
    private DatabaseClient mDb;
    private Exercice exercice;
    private Qas qas;
    private ArrayList<String> repsUti;
    private ArrayList<String> repsExercice;
    private ArrayList<String> enonceExercice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qas);

        // Instanciation des attributs
        exercice = new Exercice();
        qas = new Qas();

        repsUti = new ArrayList<>();
        repsExercice = new ArrayList<>();
        enonceExercice = new ArrayList<>();
        //On remplis nos listes de reponses toutes les reponses précédentes
        if(getIntent().getSerializableExtra(REPONSE_UTI) != null){
            repsUti = (ArrayList<String>) getIntent().getSerializableExtra(REPONSE_UTI);
        }
        if(getIntent().getSerializableExtra(REPONSE) != null){
            repsExercice = (ArrayList<String>) getIntent().getSerializableExtra(REPONSE);
        }
        if(getIntent().getSerializableExtra(ENONCE) != null){
            enonceExercice = (ArrayList<String>) getIntent().getSerializableExtra(ENONCE);
        }

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupération de la sous-matière
        exercice = (Exercice) getIntent().getSerializableExtra("exercice_key");

    }

    /**
     *
     *
     */
    private void getQas() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetQas extends AsyncTask<Void, Void, Qas> {

            @Override
            protected Qas doInBackground(Void... voids) {
                Qas qas_DB = mDb.getAppDatabase().qasDao().getQas(exercice.getId());
                return qas_DB;
            }

            @Override
            protected void onPostExecute(Qas qas_DB) {
                super.onPostExecute(qas_DB);

                // Mettre à jour la qas
                qas = qas_DB;
                TextView enonce = (TextView) findViewById(R.id.qas_enonce);
                enonce.setText(qas.getEnonce());
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetQas gt = new GetQas();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour la qas
        getQas();

        TextView consigne = (TextView) findViewById(R.id.consigne);
        consigne.setText(exercice.getConsigne());


    }

    public void QASActivity_Valider(View view){
        if((Integer) getIntent().getIntExtra(NB_QUEST_KEY, 5) > 0) {
            EditText reponseUti = (EditText) findViewById(R.id.qas_reponse);
            // Création d'une intention
            Intent intent = new Intent(this, ExerciceActivity.class);
            // flag
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // reponses
            repsUti.add(reponseUti.getText().toString().trim());
            repsExercice.add(qas.getReponse());
            enonceExercice.add(qas.getEnonce());
            intent.putExtra(REPONSE_UTI, repsUti);
            intent.putExtra(REPONSE, repsExercice);
            intent.putExtra(ENONCE, enonceExercice);
            // ajoute la réponse à l'intent
            intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
            intent.putExtra(SOUS_MATIERE_KEY, (SousMatiere) getIntent().getSerializableExtra(SOUS_MATIERE_KEY));
            intent.putExtra(NB_QUEST_KEY, (Integer) getIntent().getIntExtra(NB_QUEST_KEY, 5));
            intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
            // Lancement de la demande de changement d'activité
            startActivityForResult(intent, REQUEST_CODE_ADD);
            super.finish();
        }
        else{
            EditText reponseUti = (EditText) findViewById(R.id.qas_reponse);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
            // reponses
            repsUti.add(reponseUti.getText().toString().trim());
            repsExercice.add(qas.getReponse());
            enonceExercice.add(qas.getEnonce());
            intent.putExtra(REPONSE_UTI, repsUti);
            intent.putExtra(REPONSE, repsExercice);
            intent.putExtra(ENONCE, enonceExercice);
            //
            intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
            intent.putExtra(SOUS_MATIERE_KEY, (SousMatiere) getIntent().getSerializableExtra(SOUS_MATIERE_KEY));
            startActivityForResult(intent, REQUEST_CODE_ADD);
            super.finish();
        }
    }


}
