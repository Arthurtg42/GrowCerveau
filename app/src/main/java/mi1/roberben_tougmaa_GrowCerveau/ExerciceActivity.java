package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mi1.roberben_tougmaa_GrowCerveau.db.Calcul;
import mi1.roberben_tougmaa_GrowCerveau.db.DatabaseClient;
import mi1.roberben_tougmaa_GrowCerveau.db.Exercice;
import mi1.roberben_tougmaa_GrowCerveau.db.Matiere;
import mi1.roberben_tougmaa_GrowCerveau.db.Result;
import mi1.roberben_tougmaa_GrowCerveau.db.SousMatiere;

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

        // vérification si restoration d'une instance précédemment détruite
        if(savedInstanceState != null){
            nbQuestRestante = savedInstanceState.getInt(CodeAndKey.STATE_NBQUESTIONS);
            results = (ArrayList<Result>) savedInstanceState.getSerializable(CodeAndKey.STATE_RESULTS);
            exerciceList = (ArrayList<Exercice>) savedInstanceState.getSerializable(CodeAndKey.STATE_EXERCICES);
            calculList = (ArrayList<Calcul>) savedInstanceState.getSerializable(CodeAndKey.STATE_CAlCULS);
        }
        else{
            // Instanciation des attributs
            exerciceList = new ArrayList<>();
            results = new ArrayList<>();
            calculList = new ArrayList<>();
            // Récupération des infos de l'intent
            nbQuestRestante = (Integer) getIntent().getIntExtra(CodeAndKey.NB_QUEST_KEY, 10);
        }

        // récupération de la matière et la sous-matière pour alléger le code
        matiere = ((MyApplication) this.getApplication()).getMatiere();
        sousMatiere = ((MyApplication) this.getApplication()).getSousMatiere();


        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Si la matière n'est pas mathématiques et que exerciceList n'a pas déjà été récupéré on le récupère
        if(exerciceList.isEmpty() && matiere.getNom().compareTo("Mathématiques")!=0){
            if(matiere.getNom().compareTo("Culture Générale")!=0){
                // si ce n'est pas culture générale, on récupère les exercices associé à la matière
                getExercices();
            }
            else{
                // sinon on recupère tous les exercices de la base
                getAllExercices();
            }
        }
        // si la matière est mathématiques et la calculList n'est pas déjà récupérée
        else if(calculList.isEmpty() && matiere.getNom().compareTo("Mathématiques")==0){
            ArrayList<String> operations = new ArrayList<>();
            operations.add("+");
            operations.add("-");
            operations.add("*");
            operations.add("/");
            for(int i = 0; i < nbQuestRestante; i++){
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
        if (requestCode == CodeAndKey.REQUEST_CODE_END_EXERCICE && resultCode == RESULT_OK) {
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
                    intent.putExtra(CodeAndKey.RESULTS_UTI, results);
                    // Cas où la matière est culture Générale
                    if(matiere.getNom().compareTo("Culture Générale") == 0){
                        // Récupération du nombre de questions dans le nom de la sousMatière
                        // format "[nb] questions"
                        Integer nbQuest = Integer.parseInt(sousMatiere.getNom().split(" ")[0]);
                        // ajout du nombre de question à l'intent
                        intent.putExtra(CodeAndKey.NB_QUEST_KEY, nbQuest);
                    }
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
                // mélange de l'ordre des exercices
                Collections.shuffle(exerciceList);
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

    /**
     *
     *
     */
    private void getAllExercices() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer tous les exercices et de mettre à jour le listView de l'activité
        class GetAllExercices extends AsyncTask<Void, Void, List<Exercice>> {

            @Override
            protected List<Exercice> doInBackground(Void... voids) {
                // récupération de la liste de tous les exercices en base
                List<Exercice> exerciceList_DB = mDb.getAppDatabase().exerciceDao().getAll();
                return exerciceList_DB;
            }

            @Override
            protected void onPostExecute(List<Exercice> exerciceList_DB) {
                super.onPostExecute(exerciceList_DB);
                // Mettre à jour l'exerciceList avec la liste des exercices de la base
                exerciceList = (ArrayList) exerciceList_DB;
                // mélange de l'ordre des exercices
                Collections.shuffle(exerciceList);
                // Lancement du premier exercice
                LancerExercice(exerciceList.get(0));
            }
        }
        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetExercices et execution de la demande asynchrone
        GetAllExercices gae = new GetAllExercices();
        gae.execute();
    }

    // Lance un exercice de calcul sous forme de qas
    public void LancerCalcul(Calcul calc){
        // Création d'une intention
        Intent intent = new Intent(this, QASActivity.class);
        // ajoute la qas à l'intent
        intent.putExtra(CodeAndKey.QAS_KEY, calc.toQas());
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_END_EXERCICE);
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
        }
        else if(ex.getType().compareTo("QAT") == 0){
            // Création d'une intention
            intent.setClass(this, QATActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(CodeAndKey.EXERCICE_KEY, ex);
        }
        else if(ex.getType().compareTo("QCM") == 0){
            // Création d'une intention
            intent.setClass(this, QCMActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(CodeAndKey.EXERCICE_KEY, ex);
        }
        else{
            // Création d'une intention
            intent.setClass(this, MainActivity.class);
            // Message
            Toast.makeText(this, "Problème, retour à l'acceuil : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
        }
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_END_EXERCICE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(CodeAndKey.STATE_NBQUESTIONS, nbQuestRestante);
        savedInstanceState.putSerializable(CodeAndKey.STATE_RESULTS, results);
        savedInstanceState.putSerializable(CodeAndKey.STATE_EXERCICES, exerciceList);
        savedInstanceState.putSerializable(CodeAndKey.STATE_CAlCULS, calculList);
        super.onSaveInstanceState(savedInstanceState);
    }
}
