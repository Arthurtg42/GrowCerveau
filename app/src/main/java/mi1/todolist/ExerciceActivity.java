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

import mi1.todolist.db.Calcul;
import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Result;
import mi1.todolist.db.SousMatiere;

public class ExerciceActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private Matiere matiere;
    private SousMatiere sousMatiere;
    private Integer nbQuestRestante;
    private ArrayList<Result> results;
    private ArrayList<Exercice> exerciceList;
    private ArrayList<Calcul> calculList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        // Instanciation des attributs
        exerciceList = new ArrayList<>();
        results = new ArrayList<>();
        calculList = new ArrayList<>();

        // Récupération des infos de l'intent
        matiere = (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY);
        sousMatiere = (SousMatiere) getIntent().getSerializableExtra(CodeAndKey.SOUS_MATIERE_KEY);
        nbQuestRestante = (Integer) getIntent().getIntExtra(CodeAndKey.NB_QUEST_KEY, 10);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Si la matière n'est pas mathématiques et que exerciceList n'a pas déjà été récupéré on le récupère
        if(exerciceList.isEmpty() && matiere.getNom().compareTo("Mathématiques")!=0){
            getExercices();
        }
        // si la matière est mathématiques et la calculList n'est pas déjà récupérée
        else if(calculList.isEmpty() && matiere.getNom().compareTo("Mathématiques")==0){
            for(int i = 0; i < nbQuestRestante; i++){
                ArrayList<String> operations = new ArrayList<>();
                operations.add("+");
                operations.add("-");
                operations.add("*");
                operations.add("/");
                if(sousMatiere.getNom().compareTo("Addition")==0){
                    calculList.add(new Calcul("+"));
                }
                else if(sousMatiere.getNom().compareTo("Soustraction")==0){
                    calculList.add(new Calcul("-"));
                }
                else if (sousMatiere.getNom().compareTo("Multiplication")==0){
                    calculList.add(new Calcul("*"));
                }
                else if(sousMatiere.getNom().compareTo("Division")==0){
                    calculList.add(new Calcul("/"));
                }
                else {
                    // ajout d'un calcul avec opération aléatoire (index choisis entre 0 et 3 dans operations)
                    calculList.add(new Calcul(operations.get((int) (Math.random() *4))));
                }
            }
            LancerCalcul(calculList.get(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeAndKey.REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            nbQuestRestante--;

            // Récupération et stockage du result à la question
            results.add((Result) data.getSerializableExtra(CodeAndKey.RESULTS_UTI));

            // Cas exercice non mathématiques
            if(matiere.getNom().compareTo("Mathématiques")!=0) {

                // Retrait de l'exercice qui vient d'être passé
                exerciceList.remove(0);

                if(nbQuestRestante > 0 && exerciceList.size() > 0){
                    // lancement du prochain exercice (en index 0)
                    LancerExercice(exerciceList.get(0));
                }
                else {
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
                // Retrait du calcul qui vient d'être passé
                calculList.remove(0);

                if(nbQuestRestante > 0 && calculList.size() > 0){
                    // lancement du prochain calcul (en index 0)
                    LancerCalcul(calculList.get(0));
                }
                else {
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

    // Lance un exercice de calcul sous forme de qas
    public void LancerCalcul(Calcul calc){
        // Création d'une intention
        Intent intent = new Intent(this, QASActivity.class);
        // ajoute la qas à l'intent
        intent.putExtra(CodeAndKey.QAS_KEY, calc.toQas());
        intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
        intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, sousMatiere);
        intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));

        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_ADD);
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
