package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Matiere;

public class HomePageActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final String ID_SESSION = "id_session";
    private static final String MATIERE_KEY = "matiere_key";

    // DATA
    private DatabaseClient mDb;
    private MatieresAdapter adapter;

    // VIEW
    private ListView listMatiere;

    public int idUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        //On rajoute le nom d'utilisateur
        class AddNomUtil extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {

                // adding to database
                return mDb.getAppDatabase().userDao().getPseudoFromId((Integer) getIntent().getSerializableExtra(ID_SESSION));

            }

            @Override
            protected void onPostExecute(String pseudo) {
                super.onPostExecute(pseudo);
                if((Integer) getIntent().getSerializableExtra(ID_SESSION) != 0){
                    TextView txtIntro = findViewById(R.id.HomePageActivity_intro);
                    txtIntro.setText("Bonjour "+pseudo+" ! Choisissez une activité pour commencer l'entrainement !");
                }
            }
        }
        //On execute en async
        AddNomUtil AddUt= new AddNomUtil();
        AddUt.execute();

        // Récupérer les vues
        listMatiere = findViewById(R.id.listMatiere);

        // Lier l'adapter au listView
        adapter = new MatieresAdapter(this, new ArrayList<Matiere>());
        listMatiere.setAdapter(adapter);

        // Ajouter un événement click à la listView
        listMatiere.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la matière cliquée à l'aide de l'adapter
                Matiere matiere = adapter.getItem(position);
                // Création d'une intention
                Intent intent = new Intent(view.getContext(), NavigationActivity.class);
                // ajoute la matière à l'intent
                intent.putExtra(MATIERE_KEY, matiere);
                // Lancement de la demande de changement d'activité
                startActivityForResult(intent, REQUEST_CODE_ADD);
                // Message
                Toast.makeText(HomePageActivity.this, "Click : " + matiere.getNom(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     *
     *
     */
    public void getMatieres() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetMatieres extends AsyncTask<Void, Void, List<Matiere>> {

            @Override
            protected List<Matiere> doInBackground(Void... voids) {
                return mDb.getAppDatabase()
                .matiereDao()
                .getAll();
            }

            @Override
            protected void onPostExecute(List<Matiere> matieres) {
                super.onPostExecute(matieres);

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(matieres);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetMatieres gt = new GetMatieres();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des taches
        getMatieres();

    }

    public void HomePageActivityCompte(View view){
        ////////////
        // On charge la home page
        // Création d'une intention
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
        finish();
    }

}
