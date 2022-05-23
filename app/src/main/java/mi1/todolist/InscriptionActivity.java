package mi1.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import mi1.todolist.db.DatabaseClient;

public class InscriptionActivity extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }
}
