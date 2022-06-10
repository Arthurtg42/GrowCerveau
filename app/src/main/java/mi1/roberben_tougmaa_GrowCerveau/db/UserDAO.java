package mi1.roberben_tougmaa_GrowCerveau.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE id= :id_user")
    User getUser(Integer id_user);

    @Query("SELECT ID FROM user WHERE mdp = :motDePasseLog AND pseudo = :pseudoLog LIMIT 1")
    int getIdUser(String pseudoLog, String motDePasseLog);

    @Query("SELECT PSEUDO FROM user WHERE ID = :id")
    String getPseudoFromId(Integer id);

    @Insert
    void insert(User user);

    @Insert
    long[] insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}