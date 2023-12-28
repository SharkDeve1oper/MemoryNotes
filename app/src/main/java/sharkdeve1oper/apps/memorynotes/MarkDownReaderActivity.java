package sharkdeve1oper.apps.memorynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spanned;
import android.widget.TextView;

import io.noties.markwon.Markwon;
import sharkdeve1oper.apps.memorynotes.models.Note;

public class MarkDownReaderActivity extends AppCompatActivity {
    TextView textView_title, textView_data;

    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdownreader);

        textView_data = findViewById(R.id.textViewMark_data);
        textView_title = findViewById(R.id.textViewMark_title);

        try {
            note = (Note) getIntent().getSerializableExtra("note_reader");
            Markwon markwon = Markwon.create(this);
            String markdown = note.getData();
            Spanned spanned = markwon.toMarkdown(markdown);
            textView_data.setText(spanned);
            textView_title.setText(note.getTitle());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}