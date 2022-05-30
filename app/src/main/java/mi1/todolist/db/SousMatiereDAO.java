package mi1.todolist.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SousMatiereDAO {

    @Query("SELECT * FROM SousMatiere")
    List<SousMatiere> getAll();

    @Insert
    void insert(SousMatiere sousmatiere);

    @Insert
    long[] insertAll(SousMatiere... sousmatieres);

    @Delete
    void delete(SousMatiere sousmatiere);

    @Update
    void update(SousMatiere sousmatiere);

}