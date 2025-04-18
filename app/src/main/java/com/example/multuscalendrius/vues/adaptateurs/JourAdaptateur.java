package com.example.multuscalendrius.vues.adaptateurs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.multuscalendrius.R;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JourAdaptateur extends RecyclerView.Adapter<JourAdaptateur.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(TextView date);
    }

    private List<String> jours;
    private OnItemClickListener listener;
    private int selectedPosition = LocalDate.now().getDayOfMonth() - 1;

    public JourAdaptateur(int nbJour, OnItemClickListener listener) {
        List<String> jours = new ArrayList<>();
        for (int i=1; i < nbJour; i++) {
            DecimalFormat formatter = new DecimalFormat("00");
            String jour = formatter.format(i);
            jours.add(jour);
        }
        this.jours = jours;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_jour, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(jours.get(position), listener, position == selectedPosition);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return jours.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = itemView.findViewById(R.id.jour);
        }

        public void bind(final String item, final OnItemClickListener listener, boolean isSelected) {
            textView.setText(item);

            // Change appearance based on selection
            if (isSelected) {
                textView.setBackgroundResource(R.drawable.round_background);
                textView.setTextColor(textView.getResources().getColor(R.color.couleur_primaire, null));
            } else {
                textView.setBackground(null);
                textView.setTextColor(textView.getResources().getColor(R.color.couleur_texte, null));
            }

            itemView.setOnClickListener(v -> {
                int oldPosition = selectedPosition;
                selectedPosition = getAdapterPosition();

                // Notify old and new selected item to redraw
                notifyItemChanged(oldPosition);
                notifyItemChanged(selectedPosition);

                listener.onItemClick(textView);
            });
        }
    }
}
