package mi1.roberben_tougmaa_GrowCerveau.db;



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

    @Query("SELECT * FROM Matiere WHERE nom = :nom_mat LIMIT 1")
    Matiere getMatiere(String nom_mat);

    @Query("SELECT ID FROM Matiere WHERE nom=:nom_mat LIMIT 1")
    Integer getIdMat(String nom_mat);

    @Insert
    void insert(Matiere matiere);

    @Insert
    long[] insertAll(Matiere... matieres);

    @Delete
    void delete(Matiere matiere);

    @Update
    void update(Matiere matiere);

}