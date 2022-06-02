package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.SousMatiere;

public class NavigationActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final String SOUS_MATIERE_KEY = "sous_matiere_key";
    private static final String ID_SESSION = "id_session";

    // DATA
    private Matiere matiere;
    private DatabaseClient mDb;
    private SousMatieresAdapter adapter;

    // VIEW
    private ListView listSousMatiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Récupération de la matière
        matiere = (Matiere) getIntent().getSerializableExtra("matiere_key");

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        listSousMatiere = findViewById(R.id.listSousMatiere);

        // Lier l'adapter au listView
        adapter = new SousMatieresAdapter(this, new ArrayList<SousMatiere>());
        listSousMatiere.setAdapter(adapter);

        // Ajouter un événement click à la listView
        listSousMatiere.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la matière cliquée à l'aide de l'adapter
                SousMatiere sousMatiere = adapter.getItem(position);

                // Création d'une intention
                Intent intent = new Intent(view.getContext(), ExerciceActivity.class);
                // ajoute la matière à l'intent
                intent.putExtra(SOUS_MATIERE_KEY, sousMatiere);
                // Lancement de la demande de changement d'activité
                startActivityForResult(intent, REQUEST_CODE_ADD);

                // Message
                Toast.makeText(NavigationActivity.this, "Click : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     *
     */
    private void getSousMatieres() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetSousMatieres extends AsyncTask<Void, Void, List<SousMatiere>> {

            @Override
            protected List<SousMatiere> doInBackground(Void... voids) {
                List<SousMatiere> sousMatiereList = mDb.getAppDatabase()
                        .sousMatiereDao()
                        .getSousMat(matiere.getId());
                return sousMatiereList;
            }

            @Override
            protected void onPostExecute(List<SousMatiere> sousMatieres) {
                super.onPostExecute(sousMatieres);

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(sousMatieres);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetSousMatieres gt = new GetSousMatieres();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des taches
        getSousMatieres();

    }

    public void GoBackToHomePage(View view){
        // Création d'une intention
        Intent intent = new Intent(this, HomePageActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // ajoute la matière à l'intent
        if(getIntent().getSerializableExtra(ID_SESSION) != null) {
            intent.putExtra(ID_SESSION, (int) getIntent().getSerializableExtra(ID_SESSION));
        }
        else{
            intent.putExtra(ID_SESSION, 0);
        }
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }
}
