package sharkdeve1oper.apps.memorynotes;

import androidx.cardview.widget.CardView;

import sharkdeve1oper.apps.memorynotes.models.Note;

public interface NotesClickListener {
    void onClick (Note note);
    void onLongClick(Note note, CardView cardView);
}
