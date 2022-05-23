package mi1.todolist.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MatiereDAO {

    @Query("SELECT * FROM Matiere")
    List<Matiere> getAll();

    @Insert
    void insert(Matiere matiere);

    @Insert
    long[] insertAll(Matiere... matieres);

    @Delete
    void delete(Matiere matiere);

    @Update
    void update(Matiere matiere);

}