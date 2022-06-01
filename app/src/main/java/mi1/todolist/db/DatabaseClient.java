package mi1.todolist.db;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import mi1.todolist.ConnectionActivity;
import mi1.todolist.HomePageActivity;
import mi1.todolist.MainActivity;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    public static SupportSQLiteDatabase dataBase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GrowCerveau").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GrowCerveau").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            dataBase = db;

            // Matières
            db.execSQL("INSERT INTO matiere (nom) VALUES('Mathématiques');");
            db.execSQL("INSERT INTO matiere (nom) VALUES('Histoire et Géographie');");
            db.execSQL("INSERT INTO matiere (nom) VALUES('Français');");


            class AddMat extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {

                    // adding to database
                    return appDatabase.matiereDao().getIdMat("Mathématiques");

                }

                @Override
                protected void onPostExecute(Integer idMat) {
                    super.onPostExecute(idMat);

                    if (idMat != null){
                        // Sous Matières Mathématiques
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Addition');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Soustraction');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Multiplication');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Division');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Les quatres opérations');");
                    }

                }
            }

            //On execute en async
            AddMat AddMathSM = new AddMat();
            AddMathSM.execute();


/*
            Matiere mat = new Matiere();
            Integer idMat = new Integer(0);
            idMat = appDatabase.matiereDao().getIdMat("Mathématiques");
            if (idMat != 0){
                //idMat = mat.getId();
                // Sous Matières Mathématiques
                db.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Addition');");
                db.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Soustraction');");
                db.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Multiplication');");
                db.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Division');");
                db.execSQL("INSERT INTO sousmatiere (idM, nom) VALUES("+idMat+",'Les quatres opérations');");
            }*/

            /*
            mat = appDatabase.matiereDao().getMatiere("Histoire et Géographie");
            if (mat != null){
                idMat = mat.getId();
                // Sous Matières Histoire et Géographie
                db.execSQL("INSERT INTO matiere (idM, nom) VALUES("+idMat+",'Capitales');");
                db.execSQL("INSERT INTO matiere (idM, nom) VALUES("+idMat+",'Pays');");
                db.execSQL("INSERT INTO matiere (idM, nom) VALUES("+idMat+",'Histoire');");
            }

            mat = appDatabase.matiereDao().getMatiere("Français");
            if (mat != null){
                idMat = mat.getId();
                // Sous Matières Français
                db.execSQL("INSERT INTO matiere (idM, nom) VALUES("+idMat+",'Orthographe');");
                db.execSQL("INSERT INTO matiere (idM, nom) VALUES("+idMat+",'Grammaire');");
                db.execSQL("INSERT INTO matiere (idM, nom) VALUES("+idMat+",'Conjuguaison');");
            }

             */

            //Comptes
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('tiny', 'robert', 'benjamin', 'tiny');");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('totoQuiTue', 'totoQuiTue', 'totoQuiTue', '123456');");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('tataQuiTue', 'tataQuiTue', 'tataQuiTue', '123456');");

            //Questions
            /*
            db.execSQL("INSERT INTO exercice (consigne, type, matiere) VALUES('consigne trop dur', 'qcm', 'francais');");
            db.execSQL("INSERT INTO exercice (consigne, type, matiere) VALUES('consigne trop simple', 'qat', 'histoire');");
            db.execSQL("INSERT INTO qcm (bonnereponse, mauvaisereponse1, mauvaisereponse2, mauvaisereponse3) VALUES('a', 'b', 'c', 'd');");
            db.execSQL("INSERT INTO qcm (bonnereponse, mauvaisereponse1, mauvaisereponse2, mauvaisereponse3) VALUES('a', 'b', '', '');");
            db.execSQL("INSERT INTO qat (bloc1, bloc2, reponse) VALUES('Quel est le', 'manquant?', 'mot');");
            db.execSQL("INSERT INTO qat (bloc1, bloc2, reponse) VALUES('Une hirondelle est un', 'volant.', 'animal');");
             */

        }
    };
}
