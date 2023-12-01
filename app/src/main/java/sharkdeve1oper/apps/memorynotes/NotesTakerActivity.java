package sharkdeve1oper.apps.memorynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class NotesTakerActivity extends AppCompatActivity {
    EditText title, data;
    ImageView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        title = findViewById(R.id.editText_title);
        data = findViewById(R.id.editText_data);
        save = findViewById(R.id.imageView_save);
    }
}