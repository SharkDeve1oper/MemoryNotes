package sharkdeve1oper.apps.memorynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import sharkdeve1oper.apps.memorynotes.models.Note;

public class NotesTakerActivity extends AppCompatActivity {
    EditText editText_title, editText_data;
    ImageView imageView_save;

    Note note;

    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editText_title = findViewById(R.id.editText_title);
        editText_data = findViewById(R.id.editText_data);
        imageView_save = findViewById(R.id.imageView_save);

        note = new Note();

        try {
            note = (Note) getIntent().getSerializableExtra("old_note");
            editText_title.setText(note.getTitle());
            editText_data.setText(note.getData());
            isOldNote = true;
        }
        catch (Exception ignored) {
            ignored.printStackTrace();
        }
        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_title.getText().toString();
                String description = editText_data.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Пожалуйста, введите содержимое", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (title.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Пожалуйста, введите заголовок", Toast.LENGTH_SHORT).show();
                }
                @SuppressLint("SimpleDateFormat") SimpleDateFormat datePattern = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
                Date date = new Date();

                if (!isOldNote) note = new Note();
                note.setTitle(title);
                note.setData(description);
                note.setDate(datePattern.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", note);
                intent.putExtra("status", isOldNote);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}