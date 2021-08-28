package com.codingstuff.quizzyapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingstuff.quizzyapp.Model.QuizListModel;
import com.codingstuff.quizzyapp.R;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizListViewHolder> {

    private List<QuizListModel> quizListModels;
    private OnItemClickedListner onItemClickedListner;

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        this.quizListModels = quizListModels;
    }

    public QuizListAdapter(OnItemClickedListner onItemClickedListner){
        this.onItemClickedListner = onItemClickedListner;
    }
    @NonNull
    @Override
    public QuizListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_quiz , parent , false);
        return new QuizListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListAdapter.QuizListViewHolder holder, int position) {
        QuizListModel model = quizListModels.get(position);
        holder.title.setText(model.getTitle());
        Glide.with(holder.itemView).load(model.getImage()).into(holder.quizImage);
    }

    @Override
    public int getItemCount() {

        if (quizListModels == null){
            return 0;
        }else{
            return quizListModels.size();
        }

    }

    public class QuizListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title ;
        private ImageView quizImage;
        private ConstraintLayout constraintLayout;

        public QuizListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.quizTitleList);
            quizImage = itemView.findViewById(R.id.quizImageList);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickedListner.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickedListner {
        void onItemClick(int position);
    }
}
