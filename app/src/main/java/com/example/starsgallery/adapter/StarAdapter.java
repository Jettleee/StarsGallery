package com.example.starsgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.starsgallery.R;
import com.example.starsgallery.beans.Star;
import com.example.starsgallery.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {
    private final List<Star> fullList;
    private final List<Star> filteredList;
    private final Context ctx;

    public StarAdapter(Context ctx, List<Star> list) {
        this.ctx = ctx;
        this.fullList = list;
        this.filteredList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.star_item, parent, false);
        final StarViewHolder holder = new StarViewHolder(v);

        holder.itemView.setOnClickListener(view -> {
            // Utilisation de getBindingAdapterPosition() au lieu de getAdapterPosition()
            int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                showEditPopup(filteredList.get(position), position);
            }
        });
        return holder;
    }

    private void showEditPopup(Star selected, int position) {
        View popupLayout = LayoutInflater.from(ctx).inflate(R.layout.star_edit_item, null);
        final ImageView imgPop = popupLayout.findViewById(R.id.img_pop);
        final RatingBar barPop = popupLayout.findViewById(R.id.rating_pop);
        final TextView idPop = popupLayout.findViewById(R.id.id_pop);

        idPop.setText(String.valueOf(selected.getId()));
        barPop.setRating(selected.getNote());
        Glide.with(ctx).load(selected.getImage()).into(imgPop);

        new AlertDialog.Builder(ctx)
                .setTitle("Modifier la note")
                .setView(popupLayout)
                .setPositiveButton("Valider", (dialog, which) -> {
                    selected.setNote(barPop.getRating());
                    StarService.getInstance().update(selected);
                    notifyItemChanged(position);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star s = filteredList.get(position);
        holder.name.setText(s.getNom().toUpperCase());
        holder.rating.setRating(s.getNote());
        Glide.with(ctx).load(s.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Star> resultsList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    resultsList.addAll(fullList);
                } else {
                    String pattern = constraint.toString().toLowerCase().trim();
                    for (Star item : fullList) {
                        if (item.getNom().toLowerCase().startsWith(pattern)) {
                            resultsList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = resultsList;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                if (results.values != null) {
                    filteredList.addAll((List<Star>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    // Changé en public static pour éviter les erreurs de scope
    public static class StarViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        RatingBar rating;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_star);
            name = itemView.findViewById(R.id.name_star);
            rating = itemView.findViewById(R.id.rating_star);
        }
    }
}