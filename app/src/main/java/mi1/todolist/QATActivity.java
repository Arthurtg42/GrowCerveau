package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Qat;
import mi1.todolist.db.Result;
import mi1.todolist.db.SousMatiere;

public class QATActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private Matiere matiere;
    private SousMatiere sousMatiere;
    private Exercice exercice;
    private Qat qat;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qat);

        // Instanciation des attributs
        matiere = new Matiere();
        sousMatiere = new SousMatiere();
        exercice = new Exercice();
        qat = new Qat();
        result = new Result();

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // récupération de la matière et la sous-matière pour alléger le code
        matiere = ((MyApplication) this.getApplication()).getMatiere();
        sousMatiere = ((MyApplication) this.getApplication()).getSousMatiere();

        // Récupération des infos de l'intent
        exercice = (Exercice) getIntent().getSerializableExtra(CodeAndKey.EXERCICE_KEY);

        // Mise à jour de la consigne
        TextView consigne = (TextView) findViewById(R.id.consigne);
        consigne.setText(exercice.getConsigne());
    }

    /**
     *
     *
     */
    private void getQat() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer la qat associée à l'exercice
        class GetQat extends AsyncTask<Void, Void, Qat> {

            @Override
            protected Qat doInBackground(Void... voids) {
                return mDb.getAppDatabase().qatDao().getQat(exercice.getId());
            }

            @Override
            protected void onPostExecute(Qat qat_DB) {
                super.onPostExecute(qat_DB);

                // Mettre à jour la qat et l'enonce
                qat = qat_DB;
                TextView bloc1 = (TextView) findViewById(R.id.qat_bloc1);
                bloc1.setText(qat.getBloc1());
                TextView bloc2 = (TextView) findViewById(R.id.qat_bloc2);
                bloc2.setText(qat.getBloc2());
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetQat et execution de la demande asynchrone
        GetQat gt = new GetQat();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Mise à jour la qat
        getQat();
    }

    public void QATActivity_Valider(View view){
        EditText reponseUti = (EditText) findViewById(R.id.qat_reponse);
        // Création d'une intention
        Intent intent = new Intent(this, ExerciceActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // initialisation du result et ajout dans l'intent
        result.setReponse(qat.getReponse());
        result.setReponse_uti(reponseUti.getText().toString());
        result.setEnonce(qat.getEnonce());
        intent.putExtra(CodeAndKey.RESULTS_UTI, result);
        // envoi de la réponse avec l'intent
        setResult(RESULT_OK, intent);
        // fin de la QAS
        super.finish();
    }
}
