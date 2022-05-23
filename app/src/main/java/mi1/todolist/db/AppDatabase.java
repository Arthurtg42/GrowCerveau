package mi1.todolist.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Exercice.class, Qas.class, Qcm.class, Qat.class, AssociationQ_E.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDao();
    public abstract ExerciceDAO exerciceDao();
    public abstract QasDAO qasDao();
    public abstract QcmDAO qcmDao();
    public abstract QatDAO qatDao();
    public abstract AssociationQ_EDAO associationQ_EDao();

}