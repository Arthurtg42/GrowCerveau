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

import mi1.todolist.db.AssociationQ_E;
import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Qas;
import mi1.todolist.db.Result;
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
    private static final String RESULTS_UTI = "results_uti";

    // DATA
    private DatabaseClient mDb;
    private Matiere matiere;
    private SousMatiere sousMatiere;
    private Exercice exercice;
    private Qas qas;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qas);

        // Instanciation des attributs
        matiere = new Matiere();
        sousMatiere = new SousMatiere();
        exercice = new Exercice();
        qas = new Qas();
        result = new Result();

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupération des infos de l'intent
        matiere = (Matiere) getIntent().getSerializableExtra("matiere_key");
        sousMatiere = (SousMatiere) getIntent().getSerializableExtra("sous_matiere_key");
        exercice = (Exercice) getIntent().getSerializableExtra("exercice_key");

        // Mise à jour de la consigne
        TextView consigne = (TextView) findViewById(R.id.consigne);
        consigne.setText(exercice.getConsigne());
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

                // Mettre à jour la qas et l'enonce
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

    }

    public void QASActivity_Valider(View view) {
        EditText reponseUti = (EditText) findViewById(R.id.qas_reponse);
        // Création d'une intention
        Intent intent = new Intent(this, ExerciceActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // initialisation du result et ajout dans l'intent
        result.setReponse(qas.getReponse());
        result.setReponse_uti(reponseUti.getText().toString());
        result.setEnonce(qas.getEnonce());
        intent.putExtra(RESULTS_UTI, result);
        // ajoute l'id à l'intent
        intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
        // envoi de la réponse avec l'intent
        setResult(REQUEST_CODE_ADD, intent);
        // fin de la QAS
        super.finish();
    }
}
