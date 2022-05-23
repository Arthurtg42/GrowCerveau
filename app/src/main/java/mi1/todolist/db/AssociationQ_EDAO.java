package mi1.todolist.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssociationQ_EDAO {

    @Query("SELECT * FROM AssociationQ_E")
    List<AssociationQ_E> getAll();

    @Insert
    void insert(AssociationQ_E assoQE);

    @Insert
    long[] insertAll(AssociationQ_E... assoQEs);

    @Delete
    void delete(AssociationQ_E assoQE);

    @Update
    void update(AssociationQ_E assoQE);

}