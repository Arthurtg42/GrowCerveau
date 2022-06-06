package mi1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Exercice;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.Qas;
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
        score.setText(nbBonneRep+"/"+results.size()+" bonnes réponses");

    }

    public void ResultatsActivity_Acceuil(View view){
        Intent intent = new Intent(this, HomePageActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(CodeAndKey.ID_SESSION, getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_RESULT);
        super.finish();
    }

    public void ResultatsActivity_Matière(View view){
        Intent intent = new Intent(this, NavigationActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
        intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, (SousMatiere) getIntent().getSerializableExtra(CodeAndKey.SOUS_MATIERE_KEY));
        intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_ADD);
        super.finish();
    }

    public void ResultatsActivity_NouvelleSerie(View view){
        // Création d'une intention
        Intent intent = new Intent(view.getContext(), ExerciceActivity.class);
        // ajoute la matière à l'intent
        intent.putExtra(CodeAndKey.MATIERE_KEY, (Matiere) getIntent().getSerializableExtra(CodeAndKey.MATIERE_KEY));
        intent.putExtra(CodeAndKey.SOUS_MATIERE_KEY, (SousMatiere) getIntent().getSerializableExtra(CodeAndKey.SOUS_MATIERE_KEY));
        intent.putExtra(CodeAndKey.ID_SESSION, (int) getIntent().getIntExtra(CodeAndKey.ID_SESSION, 0));
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CodeAndKey.REQUEST_CODE_ADD);

        // Message
        Toast.makeText(ResultActivity.this, "Nouvelle série", Toast.LENGTH_SHORT).show();
    }

}
