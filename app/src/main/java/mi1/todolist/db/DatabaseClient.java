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


            //Sous Matières
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
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(1,"+idMat+",'Addition');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(2,"+idMat+",'Soustraction');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(3,"+idMat+",'Multiplication');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(4,"+idMat+",'Division');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(5,"+idMat+",'Les quatres opérations');");
                    }

                }
            }
            //On execute en async
            AddMat AddMathSM = new AddMat();
            AddMathSM.execute();

            class AddGeo extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {

                    // adding to database
                    return appDatabase.matiereDao().getIdMat("Histoire et Géographie");

                }

                @Override
                protected void onPostExecute(Integer idMat) {
                    super.onPostExecute(idMat);

                    if (idMat != null){
                        // Sous Matières Histoire et Géographie
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(6,"+idMat+",'Capitales');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(7,"+idMat+",'Pays');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(8,"+idMat+",'Histoire');");
                    }

                }
            }
            //On execute en async
            AddGeo AddGeoSM = new AddGeo();
            AddGeoSM.execute();

            class AddFr extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {

                    // adding to database
                    return appDatabase.matiereDao().getIdMat("Français");

                }

                @Override
                protected void onPostExecute(Integer idMat) {
                    super.onPostExecute(idMat);

                    if (idMat != null){
                        // Sous Matières Histoire et Géographie
                        // Sous Matières Français
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(9,"+idMat+",'Orthographe');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(10,"+idMat+",'Grammaire');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(11,"+idMat+",'Conjuguaison');");
                    }

                }
            }
            //On execute en async
            AddFr AddFrSM = new AddFr();
            AddFrSM.execute();

            //Comptes
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('tiny', 'robert', 'benjamin', 'tiny');");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('totoQuiTue', 'totoQuiTue', 'totoQuiTue', '123456');");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('tataQuiTue', 'tataQuiTue', 'tataQuiTue', '123456');");

            // Exercice et Question associé

            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(1, 1, 'Additionez ces deux nombres', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(1, '17 + 29 =', '46');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(2, 1, 'Additionez ces deux nombres', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(2, '31 + 29 =', '60');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(3, 1, 'Additionez ces deux nombres', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(3, '10 + 29 =', '39');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(4, 1, 'Additionez ces deux nombres', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(4, '10 + 5 =', '15');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(5, 11, 'conjuguer le verbe aller', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(5, 'Nous ', 'au marché.', 'allons');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(6, 11, 'conjuguer le verbe aller', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(6, 'Je ', ' à la piscine', 'vais');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(7, 6, 'trouver la bonne réponse', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(7, 'Quelle est la capitale de l''Allemagne ?', 'Berlin', 'Madrid', 'Munich', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(8, 6, 'trouver la bonne réponse', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(8, 'Quelle est la capitale de la France ?', 'Paris', 'Marseille', 'Londres', 'Lyon');");


        }
    };
}
