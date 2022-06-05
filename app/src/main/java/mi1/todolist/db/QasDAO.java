package mi1.todolist.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QasDAO {

    @Query("SELECT * FROM Qas")
    List<Qas> getAll();

    @Query("SELECT * FROM Qas WHERE id= :id_ex")
    Qas getQas(Integer id_ex);

    @Insert
    void insert(Qas qas);

    @Insert
    long[] insertAll(Qas... qass);

    @Delete
    void delete(Qas qas);

    @Update
    void update(Qas qas);

}