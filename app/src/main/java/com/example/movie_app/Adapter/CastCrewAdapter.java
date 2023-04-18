package com.example.movie_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_app.Model.CastCrew;
import com.example.movie_app.R;

import java.util.List;

public class CastCrewAdapter extends RecyclerView.Adapter<CastCrewAdapter.CastCrewViewHolder> {
    Context context;
    List<CastCrew> crewLists;

    public CastCrewAdapter(Context mContext, List<CastCrew> crewLists) {
        this.context = mContext;
        this.crewLists = crewLists;

    }

    @NonNull
    @Override
    public CastCrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast_crew, parent, false);
        return new CastCrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastCrewViewHolder holder, int position) {
        final CastCrew castCrew = crewLists.get(position);
        if(castCrew == null) return;
        // Load image url
        Glide.with(holder.imageCast).load(crewLists.get(position).getCimage()).into(holder.imageCast);
        holder.nameCast.setText(castCrew.getCname());

        if(position == getItemCount() - 1) {
            // Set the margin for the viewholder
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setMargins(0, 0, 25, 0);
        }
    }

    @Override
    public int getItemCount() {
        return crewLists.size();
    }

    public class CastCrewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageCast;
        TextView nameCast;

        public CastCrewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCast = (ImageView) itemView.findViewById(R.id.image_cast);
            nameCast = (TextView) itemView.findViewById(R.id.name_cast);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
