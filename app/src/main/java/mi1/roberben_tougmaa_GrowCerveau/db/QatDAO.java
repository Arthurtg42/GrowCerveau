package mi1.roberben_tougmaa_GrowCerveau.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QatDAO {

    @Query("SELECT * FROM Qat")
    List<Qat> getAll();

    @Query("SELECT * FROM Qat WHERE idE= :id_ex")
    Qat getQat(Integer id_ex);

    @Insert
    void insert(Qat qat);

    @Insert
    long[] insertAll(Qat... qats);

    @Delete
    void delete(Qat qat);

    @Update
    void update(Qat qat);

}