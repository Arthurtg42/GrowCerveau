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
import java.util.List;
import java.util.Random;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.SousMatiere;

public class ExerciceActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final String ID_SESSION = "id_session";
    private static final String EXERCICE_KEY = "exercice_key";
    private static final String MATIERE_KEY = "matiere_key";
    private static final String SOUS_MATIERE_KEY = "sous_matiere_key";
    private static final String NB_QUEST_KEY = "nb_quest_key";

    // DATA
    private DatabaseClient mDb;
    private SousMatiere sousMatiere;
    private List<Exercice> exerciceList;
    private HashSet<Integer> indexsExerciceFait;
    private Integer nbQuestRestante;
    private Integer indexNextExercice;
    private List<String> repsUti;
    private List<String> repsExercice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Instanciation des attributs
        exerciceList = new ArrayList<>();
        indexsExerciceFait = new HashSet<>();
        repsUti = new ArrayList<>();
        repsExercice = new ArrayList<>();

        // Récupération de la sous-matière
        sousMatiere = (SousMatiere) getIntent().getSerializableExtra(SOUS_MATIERE_KEY);
        nbQuestRestante = (Integer) getIntent().getIntExtra(NB_QUEST_KEY, 5);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

    }


    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour la qas
        getExercices();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nbQuestRestante--;
        String repUti = getIntent().getStringExtra("reponse_uti");
        String rep = getIntent().getStringExtra("reponse");
        repsUti.add(repUti);
        repsExercice.add(rep);
        Intent intent = new Intent();
        indexsExerciceFait.add(indexNextExercice);
        if(nbQuestRestante > 0){
            // choix aléatoire de l'index du prochain exercice
            indexNextExercice = (int) (Math.floor(Math.random()*(exerciceList.size())));

            // récupération du prochain exercice
            Exercice nextExercice = new Exercice();
            nextExercice = exerciceList.get(indexNextExercice);
            // création de l'intent en fontion du type de question
            if(nextExercice.getType().compareTo("QAS") == 0){
                // Création d'une intention
                intent.setClass(this, QASActivity.class);
                // ajoute l'exercice à l'intent
                intent.putExtra(EXERCICE_KEY, nextExercice);
                intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
                intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
            }
            else if(nextExercice.getType().compareTo("QAT") == 0){
                // Création d'une intention
                intent.setClass(this, QATActivity.class);
                // ajoute l'exercice à l'intent
                intent.putExtra(EXERCICE_KEY, nextExercice);
                intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
                intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
            }
            else if(nextExercice.getType().compareTo("QCM") == 0){
                // Création d'une intention
                intent.setClass(this, QCMActivity.class);
                // ajoute l'exercice à l'intent
                intent.putExtra(EXERCICE_KEY, nextExercice);
                intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
                intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
            }
            else{
                // Création d'une intention
                intent.setClass(this, MainActivity.class);
                // Message
                Toast.makeText(this, "Problème, retour à l'acceuil : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     *
     */
    private void getExercices() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetExercices extends AsyncTask<Void, Void, List<Exercice>> {

            @Override
            protected List<Exercice> doInBackground(Void... voids) {
                List<Exercice> exerciceList_DB = mDb.getAppDatabase().exerciceDao().getExercices(sousMatiere.getId());
                return exerciceList_DB;
            }

            @Override
            protected void onPostExecute(List<Exercice> exerciceList_DB) {
                super.onPostExecute(exerciceList_DB);

                // Mettre à jour l'adapter avec la liste de taches
                exerciceList = exerciceList_DB;
                // choix aléatoire de l'index du prochain exercice
                indexNextExercice = (int) (Math.floor(Math.random()*(exerciceList.size())));

                // récupération du prochain exercice
                Exercice nextExercice = new Exercice();
                nextExercice = exerciceList.get(indexNextExercice);

                Intent intent = new Intent();
                // création de l'intent en fontion du type de question
                if(nextExercice.getType().compareTo("QAS")==0){
                    // Création d'une intention
                    intent.setClass(ExerciceActivity.this, QASActivity.class);
                    // ajoute l'exercice à l'intent
                    intent.putExtra(EXERCICE_KEY, nextExercice);
                    intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
                    intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                    intent.putExtra(NB_QUEST_KEY, nbQuestRestante-1);
                    intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
                }
                else if(nextExercice.getType().compareTo("QAT")==0){
                    // Création d'une intention
                    intent.setClass(ExerciceActivity.this, QATActivity.class);
                    // ajoute l'exercice à l'intent
                    intent.putExtra(EXERCICE_KEY, nextExercice);
                    intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
                    intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                    intent.putExtra(NB_QUEST_KEY, nbQuestRestante-1);
                    intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
                }
                else if(nextExercice.getType().compareTo("QCM")==0){
                    // Création d'une intention
                    intent.setClass(ExerciceActivity.this, QCMActivity.class);
                    // ajoute l'exercice à l'intent
                    intent.putExtra(EXERCICE_KEY, nextExercice);
                    intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
                    intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                    intent.putExtra(NB_QUEST_KEY, nbQuestRestante-1);
                    intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
                }
                else{
                    // Création d'une intention
                    intent.setClass(ExerciceActivity.this, MainActivity.class);
                    // Message
                    Toast.makeText(ExerciceActivity.this, "Problème, retour à l'acceuil : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
                }
                // Lancement de la demande de changement d'activité
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetExercices gt = new GetExercices();
        gt.execute();
    }


}
