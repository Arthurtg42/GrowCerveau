package mi1.todolist.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GrowCerveau").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "MyToDos").addCallback(roomDatabaseCallback).build();
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

            //
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES(\"totoQuiTue\", \"totoQuiTue\", \"totoQuiTue\", \"123456\");");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES(\"tataQuiTue\", \"tataQuiTue\", \"tataQuiTue\", \"123456\");");
            db.execSQL("INSERT INTO exercice (consigne, type, matiere) VALUES(\"consigne trop dur\", \"qcm\", \"francais\");");
            db.execSQL("INSERT INTO exercice (consigne, type, matiere) VALUES(\"consigne trop simple\", \"qat\", \"histoire\");");
            db.execSQL("INSERT INTO qcm (bonnereponse, mauvaisereponse1, mauvaisereponse2, mauvaisereponse3) VALUES(\"a\", \"b\", \"c\", \"d\");");
            db.execSQL("INSERT INTO qcm (bonnereponse, mauvaisereponse1, mauvaisereponse2, mauvaisereponse3) VALUES(\"a\", \"b\", \"\", \"\");");
            db.execSQL("INSERT INTO qat (bloc1, bloc2, reponse) VALUES(\"Quel est le\", \"manquant?\", \"mot\");");
            db.execSQL("INSERT INTO qat (bloc1, bloc2, reponse) VALUES(\"Une hirondelle est un\", \"volant.\", \"animal\");");
            db.execSQL("INSERT INTO matiere (nom) VALUES(\"mathematique\");");
            db.execSQL("INSERT INTO matiere (nom) VALUES(\"francais\");");

        }
    };
}
