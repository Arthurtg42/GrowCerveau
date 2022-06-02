package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
    private static final String EXERCICE_KEY = "exercice_key";

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
        sousMatiere = (SousMatiere) getIntent().getSerializableExtra("sous_matiere_key");
        nbQuestRestante = (Integer) getIntent().getIntExtra("nb_quest_key", 5);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // récupération de tous les exercices de la sous matière
        exerciceList = mDb.getAppDatabase().exerciceDao().getExercices(sousMatiere.getId());

        // choix aléatoire de l'index du prochain exercice
        indexNextExercice = (int) (Math.random()*(exerciceList.size()-1)) + 1;

        // récupération du prochain exercice
        Exercice nextExercice = new Exercice();
        nextExercice = exerciceList.get(indexNextExercice);

        Intent intent = new Intent();
        // création de l'intent en fontion du type de question
        if(nextExercice.getType() == "QAS"){
            // Création d'une intention
            intent.setClass(this, QASActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(EXERCICE_KEY, nextExercice);
        }
        else if(nextExercice.getType() == "QAT"){
            // Création d'une intention
            intent.setClass(this, QATActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(EXERCICE_KEY, nextExercice);
        }
        else if(nextExercice.getType() == "QCM"){
            // Création d'une intention
            intent.setClass(this, QCMActivity.class);
            // ajoute l'exercice à l'intent
            intent.putExtra(EXERCICE_KEY, nextExercice);
        }
        else{
            // Création d'une intention
            intent.setClass(this, MainActivity.class);
            // Message
            Toast.makeText(this, "Problème, retour à l'acceuil : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
        }
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

}