package mi1.todolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;

public class HomePageActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private MatieresAdapter adapter;

    // VIEW
    private ListView listMatiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        listMatiere = findViewById(R.id.listMatiere);

    }

}
