package com.dicoding.associate.madesubmission3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.associate.madesubmission3.R;
import com.dicoding.associate.madesubmission3.ResultDetailActivity;
import com.dicoding.associate.madesubmission3.model.KamusModel;

import java.util.ArrayList;
import java.util.List;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {
    private ArrayList<KamusModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private List<KamusModel> words;

    public KamusAdapter(List<KamusModel> words, Context context){
        this.context = context;
        this.words = words;
    }


    @NonNull
    @Override
    public KamusAdapter.KamusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_words, null, false);
        return new KamusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusAdapter.KamusHolder holder, final int position) {
        holder.tvWord.setText(words.get(position).getWord());
        holder.tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailint = new Intent(context, ResultDetailActivity.class);
                detailint.putExtra("word", words.get(position).getWord());
                detailint.putExtra("translate", words.get(position).getTranslate());
                v.getContext().startActivity(detailint);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void addItem(ArrayList<KamusModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public static class KamusHolder extends RecyclerView.ViewHolder {
        TextView tvWord;

        public KamusHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
        }
    }
}
