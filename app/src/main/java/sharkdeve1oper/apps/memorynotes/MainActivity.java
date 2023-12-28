package sharkdeve1oper.apps.memorynotes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sharkdeve1oper.apps.memorynotes.adapter.NotesListAdapter;
import sharkdeve1oper.apps.memorynotes.database.RoomDB;
import sharkdeve1oper.apps.memorynotes.models.Note;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    NotesListAdapter notesListAdapter;
    RoomDB roomDB;
    List<Note> notes = new ArrayList<>();

    Note selectedNote;

    SearchView searchView_home;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView_home = findViewById(R.id.searchView_home);

        roomDB = RoomDB.getInstance(this);

        notes = roomDB.mainDAO().getAll();

        updateRecycler(notes);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;

                        Note new_note = (Note) data.getSerializableExtra("note");
                        boolean statusUpdate = (boolean) data.getSerializableExtra("status");
                        if (statusUpdate) {
                            assert new_note != null;
                            roomDB.mainDAO().update(new_note.getID(), new_note.getTitle(), new_note.getData());
                        }
                        else roomDB.mainDAO().insert(new_note);
                        notes.clear();
                        notes.addAll(roomDB.mainDAO().getAll());
                        notesListAdapter.notifyDataSetChanged();
                    }
                });

        FloatingActionButton fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            activityResultLauncher.launch(intent);
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String newText) {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<Note> filteredList = new ArrayList<>();
            for (Note singleNote: notes) {
                if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(singleNote);
                }
            }

            handler.post(() -> notesListAdapter.filterList(filteredList));
        });
        executor.shutdown();
    }

    private void updateRecycler(List<Note> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note",note);
            activityResultLauncher.launch(intent);
        }

        @Override
        public void onLongClick(Note note, CardView cardView) {
            selectedNote = note;
            showPopupMenu(cardView);
        }
    };

    private void showPopupMenu(CardView cardView) {

        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.pin) {
            roomDB.mainDAO().pin(selectedNote.getID(), !selectedNote.isPinned());
            Toast.makeText(MainActivity.this, "Успешно изменено", Toast.LENGTH_SHORT).show();
            notes.clear();
            notes.addAll(roomDB.mainDAO().getAll());
            notesListAdapter.notifyDataSetChanged();
            return true;
        } else if (itemId == R.id.delete) {
            roomDB.mainDAO().delete(selectedNote);
            notes.remove(selectedNote);
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Успешно удалено", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (itemId == R.id.markdown) {
            Intent intent = new Intent(MainActivity.this, MarkDownReaderActivity.class);
            intent.putExtra("note_reader",selectedNote);
            activityResultLauncher.launch(intent);
        }
        return false;
    }
}