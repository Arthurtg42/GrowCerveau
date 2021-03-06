package mi1.roberben_tougmaa_GrowCerveau;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mi1.roberben_tougmaa_GrowCerveau.db.DatabaseClient;
import mi1.roberben_tougmaa_GrowCerveau.db.Matiere;

public class HomePageActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private MatieresAdapter adapter;

    // VIEW
    private ListView listMatiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Ajout du prénom du user global au message d'acceuil, avec capitalisation du prénom
        if(((MyApplication) this.getApplication()).getUser() != null){
            TextView txtIntro = findViewById(R.id.HomePageActivity_intro);
            String prenom = ((MyApplication) this.getApplication()).getUser().getPrenom();
            txtIntro.setText("Bonjour "+prenom.substring(0,1).toUpperCase() + prenom.substring(1).toLowerCase()+" ! Choisis une activité pour commencer l'entrainement !");
        }

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
                // Fixer la matière globale avec matiere
                ((MyApplication) HomePageActivity.this.getApplication()).setMatiere(matiere);
                // Lancement de la demande de changement d'activité
                startActivity(intent);
            }
        });
    }

    /**
     *
     *
     */
    public void getMatieres() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des matieres et de mettre à jour le listView de l'activité
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
                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(matieres);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetMatieres et execution de la demande asynchrone
        GetMatieres gt = new GetMatieres();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Mise à jour des matières
        getMatieres();
    }

    public void HomePageActivityCompte(View view){
        ////////////
        // On charge la main page
        // Création d'une intention
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        // flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Lancement de la demande de changement d'activité
        startActivity(intent);
        super.finish();
    }
}
