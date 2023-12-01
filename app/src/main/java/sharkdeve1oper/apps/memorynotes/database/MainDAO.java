package sharkdeve1oper.apps.memorynotes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy.Companion;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import sharkdeve1oper.apps.memorynotes.models.Note;

@Dao
public interface MainDAO {

    @Insert (onConflict = Companion.REPLACE)
    void insert(Note note);

    @Query("SELECT * FROM note ORDER BY pinned, date DESC")
    List<Note> getAll();

    @Query("UPDATE note SET title = :title, data = :data, pinned = :pinned WHERE id = :id ")
    void update(int id, String title, String data, boolean pinned);

    @Query("UPDATE note SET title = :title, data = :data WHERE id = :id ")
    void update(int id, String title, String data);

    @Delete
    void delete(Note note);
}
