package mi1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Qas;
import mi1.todolist.db.SousMatiere;

public class ResultActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 0;
    private static final String ID_SESSION = "id_session";
    private static final String ENONCE = "enonce_uti";
    private static final String REPONSE_UTI = "reponse_uti";
    private static final String REPONSE = "reponse";
    private static final String MATIERE_KEY = "matiere_key";
    private static final String SOUS_MATIERE_KEY = "sous_matiere_key";
    private static final String NB_QUEST_KEY = "nb_quest_key";

    private ArrayList<String> repsUti;
    private ArrayList<String> repsExercice;
    private ArrayList<String> enonceExercice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        repsUti = new ArrayList<>();
        repsExercice = new ArrayList<>();
        enonceExercice = new ArrayList<>();
        //On remplis nos listes de reponses toutes les reponses précédentes
        if(getIntent().getSerializableExtra(REPONSE_UTI) != null){
            repsUti = (ArrayList<String>) getIntent().getSerializableExtra(REPONSE_UTI);
        }
        if(getIntent().getSerializableExtra(REPONSE) != null){
            repsExercice = (ArrayList<String>) getIntent().getSerializableExtra(REPONSE);
        }
        if(getIntent().getSerializableExtra(ENONCE) != null){
            enonceExercice = (ArrayList<String>) getIntent().getSerializableExtra(ENONCE);
        }

        //On recupere les reponses
        Log.d("testiclule",enonceExercice.get(0) + " : " + repsExercice.get(0)+" / "+repsUti.get(0));
        //Problème dans la liste
        // Log.d("testiclule",enonceExercice.get(1) + " : " + repsExercice.get(1)+" / "+repsUti.get(1));

    }

    public void ResultatsActivity_Acceuil(View view){
        Intent intent = new Intent(this, HomePageActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ID_SESSION, getIntent().getIntExtra(ID_SESSION, 0));
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
        super.finish();
    }

    public void ResultatsActivity_Matière(View view){
        Intent intent = new Intent(this, NavigationActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
        intent.putExtra(SOUS_MATIERE_KEY, (SousMatiere) getIntent().getSerializableExtra(SOUS_MATIERE_KEY));
        intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
        super.finish();
    }

    public void ResultatsActivity_NouvelleSerie(View view){
        // Création d'une intention
        Intent intent = new Intent(view.getContext(), ExerciceActivity.class);
        // ajoute la matière à l'intent
        intent.putExtra(MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(MATIERE_KEY));
        intent.putExtra(SOUS_MATIERE_KEY, (SousMatiere) getIntent().getSerializableExtra(SOUS_MATIERE_KEY));
        intent.putExtra(ID_SESSION, (int) getIntent().getIntExtra(ID_SESSION, 0));
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);

        // Message
        Toast.makeText(ResultActivity.this, "Nouvelle série", Toast.LENGTH_SHORT).show();
    }

}
