package mi1.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mi1.todolist.db.DatabaseClient;
import mi1.todolist.db.User;
import mi1.todolist.db.Exercice;

public class MainActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;

    // DATA
    private DatabaseClient mDb;

    // VIEW
    private Button buttonAdd;
    private ListView listMatiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

    }

    private void MainActivity_Connexion(View view){
        // Création d'une intention
        Intent intent = new Intent(this, ConnectionActivity.class);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    private void MainActivity_Inscription(View view){
        // Création d'une intention
        Intent intent = new Intent(this, InscriptionActivity.class);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    private void MainActivity_Anonyme(View view){
        // Création d'une intention
        Intent intent = new Intent(this, HomePageActivity.class);

        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }
}
