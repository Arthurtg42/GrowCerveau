package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Result;
import mi1.todolist.db.SousMatiere;

public class ExerciceActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private SousMatiere sousMatiere;
    private Integer nbQuestRestante;
    private ArrayList<Result> results;
    private ArrayList<Exercice> exerciceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        // Instanciation des attributs
        exerciceList = new ArrayList<>();
        results = new ArrayList<>();

        // Récupération des infos de l'intent
        sousMatiere = (SousMatiere) getIntent().getSerializableExtra(CodeAndKey.SOUS_MATIERE_KEY);
        nbQuestRestante = (Integer) getIntent().getIntExtra(CodeAndKey.NB_QUEST_KEY, 5);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Si exerciceList n'a pas déjà été récupéré on le récupère
        if(exerciceList.isEmpty()){
            getExercices();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeAndKey.REQUEST_CODE_ADD && resultCode == RESULT_OK) {

            nbQuestRestante--;

            // Récupération et stockage du result à la question
            results.add((Result) data.getSerializableExtra(CodeAndKey.RESULTS_UTI));

            // Retrait de l'exercice qui vient d'être passé
            exerciceList.remove(0);

            if(nbQuestRestante > 0 && exerciceList.size() > 0){
                // lancement du prochain exercice (en index 0)
                LancerExercice(exerciceList.get(0));
            }
            else{
                // Intent vers les résultats avec ajout du tableau results dans l'intent
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
                intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, sousMatiere);
                intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
                intent.putExtra(CodeAndKey.RESULTS_UTI, results);
                startActivity(intent);

                // fin de l'ExerciceActivity
                super.finish();
            }
        }
        else {
            // fin de l'ExerciceActivity
            super.finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     *
     *
     */
    private void getExercices() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des exercices et de mettre à jour le listView de l'activité
        class GetExercices extends AsyncTask<Void, Void, List<Exercice>> {

            @Override
            protected List<Exercice> doInBackground(Void... voids) {
                // récupération de la liste de tous les exercices associés à la sousMatiere
                List<Exercice> exerciceList_DB = mDb.getAppDatabase().exerciceDao().getExercices(sousMatiere.getId());
                return exerciceList_DB;
            }

            @Override
            protected void onPostExecute(List<Exercice> exerciceList_DB) {
                super.onPostExecute(exerciceList_DB);

                // Mettre à jour l'exerciceList avec la liste des exercices de la base
                exerciceList = (ArrayList) exerciceList_DB;
                Log.d("NB EXO", exerciceList.size()+"");
                for(Exercice e : exerciceList){
                    Log.d("EXERCICE ID", ""+e.getId()+" Consigne : "+e.getConsigne());
                }
                // Lancement du premier exercice
                LancerExercice(exerciceList.get(0));
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetExercices et execution de la demande asynchrone
        GetExercices gt = new GetExercices();
        gt.execute();
    }

    // Lance un exercice en fonction de son type (crée l'intent)
    public void LancerExercice(Exercice ex){
        Intent intent = new Intent();
        // création de l'intent en fontion du type de question
        if(ex.getType().compareTo("QAS") == 0){
            // Création d'une intention
            intent.setClass(this, QASActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(CodeAndKey.EXERCICE_KEY, ex);
            intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
            intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, sousMatiere);
            intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
        }
        else if(ex.getType().compareTo("QAT") == 0){
            // Création d'une intention
            intent.setClass(this, QATActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(CodeAndKey.EXERCICE_KEY, ex);
            intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
            intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, sousMatiere);
            intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
        }
        else if(ex.getType().compareTo("QCM") == 0){
            // Création d'une intention
            intent.setClass(this, QCMActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(CodeAndKey.EXERCICE_KEY, ex);
            intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
            intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, sousMatiere);
            intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
        }
        else{
            // Création d'une intention
            intent.setClass(this, MainActivity.class);
            // Message
            Toast.makeText(this, "Problème, retour à l'acceuil : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
        }
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_ADD);
    }

}
