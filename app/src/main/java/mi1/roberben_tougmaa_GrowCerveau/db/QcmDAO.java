package mi1.roberben_tougmaa_GrowCerveau.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QcmDAO {

    @Query("SELECT * FROM Qcm")
    List<Qcm> getAll();

    @Query("SELECT * FROM Qcm WHERE idE= :id_ex")
    Qcm getQcm(Integer id_ex);

    @Insert
    void insert(Qcm qcm);

    @Insert
    long[] insertAll(Qcm... qcms);

    @Delete
    void delete(Qcm qcm);

    @Update
    void update(Qcm qcm);

}