package sharkdeve1oper.apps.memorynotes.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "note")
public class Note implements Serializable {

    @PrimaryKey (autoGenerate = true)
    int ID = 0;

    @ColumnInfo (name = "title")
    String title = "";

    @ColumnInfo (name = "data")
    String data = "";

    @ColumnInfo (name = "date")
    String date = "";

    @ColumnInfo (name = "pinned")
    boolean pinned = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
