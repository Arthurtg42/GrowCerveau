package mi1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mi1.todolist.db.Matiere;
import mi1.todolist.db.Result;
import mi1.todolist.db.SousMatiere;

public class ResultActivity extends AppCompatActivity {

    // DATA
    private ArrayList<Result> results;
    private ResultsAdapter adapter;

    // VIEW
    private ListView listResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        // Instanciation des attributs
        results = new ArrayList<>();

        // Récupération des infos de l'intent
        results = (ArrayList) getIntent().getSerializableExtra(CodeAndKey.RESULTS_UTI);

        // Recupérer les vues
        listResult = findViewById(R.id.listResultats);

        // Lier l'adapter au listView
        adapter = new ResultsAdapter(this, new ArrayList<Result>());
        adapter.addAll(results);
        listResult.setAdapter(adapter);

        // Now, notify the adapter of the change in source
        adapter.notifyDataSetChanged();

        // mise à jour du score
        int nbBonneRep = 0;
        for (Result r : results){
            if (r.isCorrect()){
                nbBonneRep++;
            }
        }
        TextView score = findViewById(R.id.score);
        score.setText(nbBonneRep+"/"+results.size()+(nbBonneRep > 1 ?" bonnes réponses":" bonne réponse"));
    }

    public void ResultatsActivity_Acceuil(View view){
        // Création d'une intention
        Intent intent = new Intent(this, HomePageActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
        super.finish();
    }

    public void ResultatsActivity_Matière(View view){
        // Création d'une intention
        Intent intent = new Intent(this, NavigationActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
        super.finish();
    }

    public void ResultatsActivity_NouvelleSerie(View view){
        // Création d'une intention
        Intent intent = new Intent(view.getContext(), ExerciceActivity.class);
        // ajoute le nombre de questions à l'intent
        intent.putExtra(CodeAndKey.NB_QUEST_KEY, results.size());
        // Lancement de la demande de changement d'activité
        startActivity(intent);
    }

}
