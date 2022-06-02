package mi1.todolist.db;



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

    @Insert
    void insert(User user);

    @Insert
    long[] insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT ID FROM user WHERE mdp = :motDePasseLog AND pseudo = :pseudoLog")
    int getLog(String pseudoLog, String motDePasseLog);

    @Query("SELECT PSEUDO FROM user WHERE ID = :id")
    String getPseudoFromId(Integer id);

}