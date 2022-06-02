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
import mi1.todolist.db.Qas;
import mi1.todolist.db.SousMatiere;

public class QASActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final String REPONSE_UTI = "reponse_uti";
    private static final String REPONSE = "reponse";

    // DATA
    private DatabaseClient mDb;
    private Exercice exercice;
    private Qas qas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qas);

        // Instanciation des attributs
        exercice = new Exercice();
        qas = new Qas();

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

        TextView enonce = (TextView) findViewById(R.id.qas_enonce);
        enonce.setText(qas.getEnonce());
    }

    public void QASActivity_Valider(View view){
        EditText reponseUti = (EditText) findViewById(R.id.qas_reponse);
        // Création d'une intention
        Intent intent = new Intent(this, ExerciceActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // ajoute la réponse uti à l'intent
        intent.putExtra(REPONSE_UTI, reponseUti.getText());
        // ajoute la réponse à l'intent
        intent.putExtra(REPONSE, qas.getReponse());
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }
}
