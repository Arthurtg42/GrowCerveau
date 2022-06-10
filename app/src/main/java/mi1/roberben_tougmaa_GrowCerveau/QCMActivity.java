package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import mi1.roberben_tougmaa_GrowCerveau.db.DatabaseClient;
import mi1.roberben_tougmaa_GrowCerveau.db.Exercice;
import mi1.roberben_tougmaa_GrowCerveau.db.Matiere;
import mi1.roberben_tougmaa_GrowCerveau.db.Qcm;
import mi1.roberben_tougmaa_GrowCerveau.db.Result;
import mi1.roberben_tougmaa_GrowCerveau.db.SousMatiere;

public class QCMActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private Matiere matiere;
    private SousMatiere sousMatiere;
    private Exercice exercice;
    private Qcm qcm;
    private Result result;
    private ListView listProposition;
    private ListView listProposition2;
    private QcmPropositionsAdapter adapter;
    private QcmPropositionsAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);

        // Recupérer les vues
        listProposition = findViewById(R.id.listPropositionQcm);
        listProposition2 = findViewById(R.id.listPropositionQcm2);

        // Instanciation des attributs
        matiere = new Matiere();
        sousMatiere = new SousMatiere();
        exercice = new Exercice();
        qcm = new Qcm();
        result = new Result();

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // récupération de la matière et la sous-matière pour alléger le code
        matiere = ((MyApplication) this.getApplication()).getMatiere();
        sousMatiere = ((MyApplication) this.getApplication()).getSousMatiere();

        // Récupération des infos de l'intent
        exercice = (Exercice) getIntent().getSerializableExtra(CodeAndKey.EXERCICE_KEY);

        // Mise à jour de la consigne et du button matière
        TextView consigne = (TextView) findViewById(R.id.consigne);
        consigne.setText(exercice.getConsigne());


        // Lier l'adapter au listView
        adapter = new QcmPropositionsAdapter(this, new ArrayList<String>());
        listProposition.setAdapter(adapter);
        adapter2 = new QcmPropositionsAdapter(this, new ArrayList<String>());
        listProposition2.setAdapter(adapter2);

        // Ajouter un événement click à la listView
        listProposition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la matière cliquée à l'aide de l'adapter
                String prop = adapter.getItem(position);

                // Création d'une intention
                Intent intent = new Intent(view.getContext(), ExerciceActivity.class);
                // flag
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // initialisation du result et ajout dans l'intent
                result.setReponse(qcm.getBonneReponse());
                result.setReponse_uti(prop);
                result.setEnonce(qcm.getEnonce());
                intent.putExtra(CodeAndKey.RESULTS_UTI, result);
                // envoi de la réponse avec l'intent
                setResult(RESULT_OK, intent);
                // fin de la QAS
                QCMActivity.super.finish();
            }
        });
        listProposition2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la matière cliquée à l'aide de l'adapter
                String prop = adapter2.getItem(position);

                // Création d'une intention
                Intent intent = new Intent(view.getContext(), ExerciceActivity.class);
                // flag
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // initialisation du result et ajout dans l'intent
                result.setReponse(qcm.getBonneReponse());
                result.setReponse_uti(prop);
                result.setEnonce(qcm.getEnonce());
                intent.putExtra(CodeAndKey.RESULTS_UTI, result);
                // envoi de la réponse avec l'intent
                setResult(RESULT_OK, intent);
                // fin de la QAS
                QCMActivity.super.finish();
            }
        });
        getQcm();
    }

    /**
     *
     *
     */
    private void getQcm() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer le qcm associé à l'exercice et de mettre à jour le listView de l'activité
        class GetQcm extends AsyncTask<Void, Void, Qcm> {

            @Override
            protected Qcm doInBackground(Void... voids) {
                return mDb.getAppDatabase().qcmDao().getQcm(exercice.getId());
            }

            @Override
            protected void onPostExecute(Qcm qcm_DB) {
                super.onPostExecute(qcm_DB);

                // Mettre à jour le qcm et l'enonce
                qcm = qcm_DB;
                TextView enonce = (TextView) findViewById(R.id.qcm_enonce);
                enonce.setText(qcm.getEnonce());

                ArrayList<String> proposition = new ArrayList<>();
                ArrayList<String> proposition2 = new ArrayList<>();

                if(!qcm.getBonneReponse().isEmpty()){
                    proposition2.add(qcm.getBonneReponse());
                }
                if(!qcm.getMauvaiseReponse1().isEmpty()){
                    proposition2.add(qcm.getMauvaiseReponse1());
                }
                if(!qcm.getMauvaiseReponse2().isEmpty()){
                    proposition2.add(qcm.getMauvaiseReponse2());
                }
                if(!qcm.getMauvaiseReponse3().isEmpty()){
                    proposition2.add(qcm.getMauvaiseReponse3());
                }

                // mélange l'ordre des propositions
                Collections.shuffle(proposition2);

                //On réparti les reponses dans les deux listView
                for(int i=0; i<=(proposition2.size()/2); i++) {
                    proposition.add(proposition2.get(i));
                    proposition2.remove(i);
                }

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(proposition);
                adapter2.addAll(proposition2);
                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetQcm et execution de la demande asynchrone
        GetQcm gt = new GetQcm();
        gt.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Mise à jour du qcm
    }

}
