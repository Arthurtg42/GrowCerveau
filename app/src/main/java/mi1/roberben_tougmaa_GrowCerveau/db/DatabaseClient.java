package mi1.roberben_tougmaa_GrowCerveau.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
            db.execSQL("INSERT INTO matiere (nom) VALUES('Culture Générale');");

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
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(5,"+idMat+",'Les quatre opérations');");
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


            class AddCultG extends AsyncTask<Void, Void, Integer> {

                @Override
                protected Integer doInBackground(Void... voids) {

                    // adding to database
                    return appDatabase.matiereDao().getIdMat("Culture Générale");

                }

                @Override
                protected void onPostExecute(Integer idMat) {
                    super.onPostExecute(idMat);

                    if (idMat != null){
                        // Sous Matières Histoire et Géographie
                        // Sous Matières Français
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(12,"+idMat+",'5 questions');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(13,"+idMat+",'10 questions');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(14,"+idMat+",'25 questions');");
                        DatabaseClient.dataBase.execSQL("INSERT INTO sousmatiere (id, idM, nom) VALUES(15,"+idMat+",'50 questions');");
                    }

                }
            }
            //On execute en async
            AddCultG AddCultGSM = new AddCultG();
            AddCultGSM.execute();


            //Comptes
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('tiny', 'robert', 'benjamin', 'tiny');");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('totoQuiTue', 'totoQuiTue', 'totoQuiTue', '123456');");
            db.execSQL("INSERT INTO user (pseudo, nom, prenom, mdp) VALUES('tataQuiTue', 'tataQuiTue', 'tataQuiTue', '123456');");

            // Exercices et questions

            // Mathématiques uniquement pour que la culture générale puisse venir piocher des exercices de mathématiques
            // sinon les exercices de calcul sont générés aléatoirement
            //              Addition
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(65, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(65, '18 + 37 = ', '55');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(66, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(66, '134 + 8 = ', '142');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(67, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(67, '12 + 91 = ', '103');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(68, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(68, '3 + 58 = ', '61');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(69, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(69, '29 + 87 = ', '116');");

            //              Soustraction
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(70, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(70, '37 - 18 = ', '19');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(71, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(71, '134 - 8 = ', '126');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(72, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(72, '91 - 12 = ', '79');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(73, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(73, '58 - 3 = ', '55');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(74, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(74, '87 - 29 = ', '56');");

            //              Multiplication
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(75, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(75, '8 * 7 = ', '56');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(76, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(76, '7 * 5 = ', '35');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(77, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(77, '6 * 6 = ', '36');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(78, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(78, '9 * 8 = ', '72');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(79, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(79, '4 * 7 = ', '28');");

            //              Division
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(80, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(80, '45 / 5 = ', '9');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(81, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(81, '18  / 3 = ', '6');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(82, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(82, '24 / 4 = ', '6');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(83, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(83, '48 / 2 = ', '24');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(84, 1, 'Résolver le calcul.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(84, '60 / 10 = ', '6');");


            // Histoire et Géographie
            //              Capitales
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(15, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(15, 'Quelle est la capitale de l''Allemagne ?', 'Berlin', 'Madrid', 'Munich', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(16, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(16, 'Quelle est la capitale de la France ?', 'Paris', 'Marseille', 'Londres', 'Lyon');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(17, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(17, 'Quelle est la capitale de la Belgique ?', 'Bruxelles', 'Oslo', 'Lille', 'Grenoble');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(18, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(18, 'Quelle est la capitale du Royaume-Uni ?', 'Londres', 'New York', '', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(19, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(19, 'Quelle est la capitale de l''Espagne ?', 'Madrid', 'Barcelone', 'Valence', 'Perpignan');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(20, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(20, 'Quelle est la capitale du Luxembourg ?', 'Luxembourg', 'Monaco', 'Strasbourg', 'Amsterdam');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(21, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(21, 'Quelle est la capitale de la Russie ?', 'Moscou', 'Varsovie', 'Kiev', 'Stockholm');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(22, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(22, 'Quelle est la capitale de la Grèce ?', 'Athènes', 'Zeus', 'Parthénon', 'Rouen');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(23, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(23, 'Quelle est la capitale de l''Italie ?', 'Rome', 'Milan', 'Naples', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(24, 6, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(24, 'Quelle est la capitale du Portugal ?', 'Lisbonne', 'Porto', '', '');");

            //              Pays
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(25, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(25, 'Dans quel pays se situe Strasbourg ?', 'France');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(26, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(26, 'Paris est la capitale de quel pays ?', 'France');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(27, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(27, 'Rome se situe dans quel pays ?', 'Italie');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(28, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(28, 'Les danois sont les habitants de quel pays ?', 'Danemark');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(29, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(29, 'Les hollandais habitent dans quel pays ?', 'Pays-Bas');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(30, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(30, 'Quel pays a pour capitale Luxembourg ?', 'Luxembourg');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(31, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(31, 'Lisbonne est la capitale du ', 'Portugal');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(32, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(32, 'Mexico est la capitale du ', 'Mexique');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(33, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(33, 'La Gaule correspond à quel pays aujourd''hui ?', 'France');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(34, 7, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(34, 'Berlin se situe en ', 'Allemagne');");

            //              Histoire
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(35, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(35, 'Quel était le surnom de Louis XIV ?', 'Le roi soleil', 'Le roi des glaces', 'Le roi rayonnant', 'Le roi tournesol');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(36, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(36, 'Où est mort Charlemagne ? ', 'Aix la chapelle', 'Saint jean en Royans', 'Paris', 'Lille');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(37, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(37, 'Quand est couronné Charlemagne ?', 'En 800', 'En 1001', 'En 1182', 'En 354');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(38, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(38, 'A quelle dynastie appartient Charlemagne ?', 'Les Carolingiens', 'Les Capaciens', 'Les Bizonthins', 'Les Royannais');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(39, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(39, 'Hugues Capet est :', 'Le roi des francs', 'Le roi des français', 'Le roi des françois', 'Le roi des franks');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(40, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(40, 'Quels sont les deux fondateurs de Rome selon la légende ?', 'Romulus et Remus', 'Romuald et Rébus', 'Jules et César', 'Titi et Grominet');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(41, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(41, 'Quel roi est mort décapité lors de la révolution ?', 'Louis XVI', 'Louis XV', 'Louis XIV', 'Louis XI');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(42, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(42, 'Quand a eu lieu la révolution ?', 'En 1789', 'En 1798', 'En 1760', 'En 1999');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(43, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(43, 'Quel est le symbole de la révolution ?', 'La Mariane', 'Le fusil', 'La barricade', 'Le tricorne');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(44, 8, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(44, 'Comment est mort Jules César ?', 'Par coup de couteau', 'Pendu', 'Guillotiné', 'Empoisonné');");



            // Français
            //              Orthographe
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(45, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(45, 'Parmi ces mots, lequel est mal orthographié ?', 'baim', 'lin', 'respecter', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(46, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(46, 'Parmi ces mots, lequel est mal orthographié ?', 'grinper', 'timbre', 'température', 'mouche');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(47, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(47, 'C''est un récipient qui contient le thé.', 'la théière', 'l''athéière', 'la téhière', 'lathéière');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(48, 9, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(48, 'Comment orthographier correctement le mot \"egzemple\" ?', 'exemple');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(49, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(49, 'C''est une boîte qui contient des bonbons.', 'la bonbonnière', 'la bonnière', 'la bonnebière', 'laboitabonbon');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(50, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(50, 'Trouve la bonne orthographe.', 'citron', 'sitron', 'çitron', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(51, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(51, 'Trouve la bonne orthographe.', 'pistache', 'pisstache', 'pisctache', 'pissetache');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(52, 9, 'Répondre en un seul mot à la question suivante.', 'QAS');");
            db.execSQL("INSERT INTO QAS (idE, enonce, reponse) VALUES(52, 'Comment orthographier correctement le mot \"poluzion\" ?', 'pollution');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(53, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(53, 'Trouve la bonne orthographe.', 'la dentition', 'la dantition', 'la dentiçion', 'le danttission');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(54, 9, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(54, 'Le mot \"métitation\" est-il correctement orthographié?', 'non', 'oui', '', '');");


            //              Grammaire
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(55, 10, 'Choisir entre or et hors.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(55, 'Elle a commandé un pull,', ' elle a reçu une robe.', 'or');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(56, 10, 'Choisir entre or et hors.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(56, 'Je suis ', ' de moi.', 'hors');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(57, 10, 'Choisir entre or et hors.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(57, 'Jeanne d''Arc voulait chasser les anglais ', ' de la France.', 'hors');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(58, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(58, 'Trouve le mot manquant : \"Tout à coup, un ... retentit au fond de la forêt.\"', 'appel', 'appelle', 'appellent', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(59, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(59, 'Trouve le mot manquant : \"Celui qui bouge sans arrêt les ... est inquiet.\"', 'oreilles', 'ils', 'posture', 'jambe');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(60, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(60, 'Trouve le mot manquant : \"Des ... seront utiles avant de partir.\"', 'conseils', 'conseil', 'conseillent', 'consoler');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(61, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(61, '\"ta soeur\", dans la phrase : \"Ce matin, ta soeur t''a écrit\" est', 'sujet', 'complément d''objet direct', 'attribut du sujet', 'verbe');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(62, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(62, 'Quelle est la forme négative de :\"Nous nous sommes déjà rencontrés?\"', 'Nous ne nous sommes jamais rencontrés ?', 'On s''est jamais rencontré ?', 'Nous ne nous sommes pas rencontrés ?', 'aucune');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(63, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(63, 'Quelle est la forme négative de :\"Une hésitation est encore possible?\"', 'Une hésitation n’est plus possible.', 'Une hésitation ne sera pas possible.', 'Une hésitation n’est pas possible.', '');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(64, 10, 'Cliquer sur la bonne réponse.', 'QCM');");
            db.execSQL("INSERT INTO QCM (idE, enonce, bonneReponse, mauvaiseReponse1, mauvaiseReponse2, mauvaiseReponse3) VALUES(64, 'Laquelle de ces phrases n''est pas à la forme négative ?', 'L''orage éclate brusquement', 'Sur ce parking, il n''y a guère d''endroit où s''abriter.', 'Les clients n''ont plus envie de quitter le magasin.', '');");

            //              Conjuguaison
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(5, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(5, 'Nous ', 'au marché.', 'allons');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(6, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(6, 'Je ', ' à la piscine.', 'vais');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(7, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(7, 'Tu ', ' chercher des champignons.', 'vas');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(8, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(8, 'Il ', ' à la plage.', 'va');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(9, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(9, 'Elles ', ' voir le match de football.', 'vont');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(10, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(10, 'Je ', ' à mon club de judo.', 'vais');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(11, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(11, 'Vous ', ' bien vous amuser.', 'allez');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(12, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(12, 'Tu ', ' vendre des fleurs ?', 'vas');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(13, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(13, 'Ils ', ' partir demain matin.', 'vont');");
            db.execSQL("INSERT INTO exercice (id, idSM, consigne, type) VALUES(14, 11, 'Conjuguer le verbe aller.', 'QAT');");
            db.execSQL("INSERT INTO QAT (idE, bloc1, bloc2, reponse) VALUES(14, 'On ', ' réussir à passer.', 'va');");
        }
    };
}
