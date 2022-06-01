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

                // Message
                Toast.makeText(NavigationActivity.this, "Click : " + sousMatiere.getNom(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void GoBackToHomePage(View view){
        // Création d'une intention
        Intent intent = new Intent(this, HomePageActivity.class);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }
}
