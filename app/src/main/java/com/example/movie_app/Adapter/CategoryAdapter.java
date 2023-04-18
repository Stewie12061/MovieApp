package com.example.movie_app.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_app.Model.CategoryList;
import com.example.movie_app.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<CategoryList> categoryListList;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CategoryList> categoryListList){
        this.categoryListList = categoryListList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryList categoryList = categoryListList.get(position);
        if(categoryList == null){
            return;
        }

        if(position == getItemCount() - 1) {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.rightMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 25, holder.itemView.getResources().getDisplayMetrics());
        }
        holder.tvNameCategory.setText(categoryList.getNameCategory());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.rcvMovie.setLayoutManager(linearLayoutManager);

        ImageMovieAdapter movieAdapter = new ImageMovieAdapter();
        movieAdapter.setData(categoryList.getCategoryMovieList());
        holder.rcvMovie.setAdapter(movieAdapter);

    }

    @Override
    public int getItemCount() {
        return categoryListList != null ? categoryListList.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameCategory;
        private RecyclerView rcvMovie;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory = itemView.findViewById(R.id.tv_title_movie);
            rcvMovie = itemView.findViewById(R.id.rcv_movie);
        }
    }
}
