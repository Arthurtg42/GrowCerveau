package mi1.todolist.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciceDAO {

    @Query("SELECT * FROM exercice")
    List<Task> getAll();

    @Insert
    void insert(Exercice exercice);

    @Insert
    long[] insertAll(Exercice... exercices);

    @Delete
    void delete(Exercice exercice);

    @Update
    void update(Exercice exercice);

}