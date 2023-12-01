package sharkdeve1oper.apps.memorynotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sharkdeve1oper.apps.memorynotes.NotesClickListener;
import sharkdeve1oper.apps.memorynotes.R;
import sharkdeve1oper.apps.memorynotes.models.Note;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{
    Context context;
    List<Note> list;

    public NotesListAdapter(Context context, List<Note> list, NotesClickListener notesClickListener) {
        this.context = context;
        this.list = list;
        this.notesClickListener = notesClickListener;
    }

    NotesClickListener notesClickListener;

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.title.setSelected(true);

        holder.data.setText(list.get(position).getData());
        holder.date.setText(list.get(position).getDate());

        if (list.get(position).isPinned())
            holder.pin.setImageResource(R.drawable.ic_pinned);
        else
            holder.pin.setImageResource(0);

        int colorCode = getRandomColor();
        holder.notes_container.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), colorCode));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesClickListener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                notesClickListener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return false;
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return random_color;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView title, data, date;
    ImageView pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        title = itemView.findViewById(R.id.textView_title);
        data = itemView.findViewById(R.id.textView_data);
        date = itemView.findViewById(R.id.textView_date);
        pin = itemView.findViewById(R.id.imageView_pin);
    }
}