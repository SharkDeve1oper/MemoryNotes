package sharkdeve1oper.apps.memorynotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import sharkdeve1oper.apps.memorynotes.models.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB roomDB;
    private static final String DATABASE_NAME = "NoteApp";

    public synchronized static RoomDB getInstance(Context context) {
        if (roomDB == null)
            roomDB = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        return roomDB;
    }

    public abstract MainDAO mainDAO();
}
