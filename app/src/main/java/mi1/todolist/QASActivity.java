package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Qas;
import mi1.todolist.db.Result;
import mi1.todolist.db.SousMatiere;

public class QASActivity extends AppCompatActivity {

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

        // récupération de la matière et la sous-matière pour alléger le code
        matiere = ((MyApplication) this.getApplication()).getMatiere();
        sousMatiere = ((MyApplication) this.getApplication()).getSousMatiere();

        // Récupération des infos de l'intent
        exercice = (Exercice) getIntent().getSerializableExtra(CodeAndKey.EXERCICE_KEY);
        qas = (Qas) getIntent().getSerializableExtra(CodeAndKey.QAS_KEY);

        // Mise à jour de la consigne
        TextView consigne = (TextView) findViewById(R.id.consigne);
        // s'il n'y a pas de qas dans l'intent alors ce n'est pas un exercice de mathématiques
        if(qas == null){
            consigne.setText(exercice.getConsigne());
        }
        else {
            consigne.setText("Resolver le calcul.");
            TextView enonce = (TextView) findViewById(R.id.qas_enonce);
            enonce.setText(qas.getEnonce());
            // On force l'input a être un number
            EditText saisieUti = (EditText) findViewById(R.id.qas_reponse);
            saisieUti.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    /**
     *
     *
     */
    private void getQas() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer la qas associée à l'exercice
        class GetQas extends AsyncTask<Void, Void, Qas> {

            @Override
            protected Qas doInBackground(Void... voids) {
                return mDb.getAppDatabase().qasDao().getQas(exercice.getId());
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
        // Création d'un objet de type GetQas et execution de la demande asynchrone
        GetQas gt = new GetQas();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Mise à jour de la qas si elle n'était pas déjà dans l'intent
        if (qas == null){
            getQas();
        }
    }

    public void QASActivity_Valider(View view) {
        // Récupération de la saisie utilisateur
        EditText reponseUti = (EditText) findViewById(R.id.qas_reponse);
        // Création d'une intention
        Intent intent = new Intent(this, ExerciceActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // initialisation du result et ajout dans l'intent
        result.setReponse(qas.getReponse());
        result.setReponse_uti(reponseUti.getText().toString());
        result.setEnonce(qas.getEnonce());
        intent.putExtra(CodeAndKey.RESULTS_UTI, result);
        // envoi de la réponse avec l'intent
        setResult(RESULT_OK, intent);
        // fin de la QAS
        super.finish();
    }
}
