package mi1.todolist;

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

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.Matiere;
import mi1.todolist.db.SousMatiere;
import mi1.todolist.db.User;

public class CompteActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private ComptesAdapter adapter;

    // VIEW
    private ListView listCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        listCompte = findViewById(R.id.listCompte);

        // Lier l'adapter au listView
        adapter = new ComptesAdapter(this, new ArrayList<User>());
        listCompte.setAdapter(adapter);

        // Ajouter un événement click à la listView
        listCompte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Récupération de la sous-matière cliquée à l'aide de l'adapter
                User user = adapter.getItem(position);

                // Création d'une intention
                Intent intent = new Intent(view.getContext(), ConnectionActivity.class);
                // Ajout du pseudo à l'intent pour pré-remplissage du champs
                intent.putExtra(CodeAndKey.PSEUDO_KEY, user.getPseudo());
                // Lancement de la demande de changement d'activité
                startActivity(intent);
            }
        });
    }

    /**
     *
     *
     */
    private void getAllUsers() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer les users et de mettre à jour le listView de l'activité
        class GetAllUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                return mDb.getAppDatabase().userDao().getAll();
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                // Mettre à jour l'adapter avec la liste de sous-matières
                adapter.clear();
                adapter.addAll(users);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }
        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetSousMatieres et execution de la demande asynchrone
        GetAllUsers gau = new GetAllUsers();
        gau.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Mise à jour des users
        getAllUsers();
    }

    public void GoBack(View view){
        super.finish();
    }
}
