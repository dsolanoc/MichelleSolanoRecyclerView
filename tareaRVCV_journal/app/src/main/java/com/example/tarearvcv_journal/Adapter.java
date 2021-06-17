package com.example.tarearvcv_journal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
   private Context Ctx;
   private List<Journal> journals;
   private LayoutInflater inflater;
    public Adapter(Context Ctx, List<Journal> journals){
        this.journals = journals;
        this.inflater=LayoutInflater.from(Ctx);
        this.Ctx=Ctx;
    }

   // @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_journal, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_journalTitle.setText(journals.get(position).getTitle());
        holder.tv_issue_id.setText(journals.get(position).getIssue_id());
        holder.tv_doi.setText(journals.get(position).getDoi());
        holder.tv_date_published.setText(journals.get(position).getDate_published());

        Glide.with(Ctx)
                .load(journals.get(position).getCover())
                .into(holder.cover);

    }
    @Override
    public int getItemCount() {
        return journals.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView tv_journalTitle,tv_issue_id,tv_doi,tv_date_published;
        private ImageView cover;

        ViewHolder(View itemView) {
            super(itemView);

            tv_journalTitle = itemView.findViewById(R.id.tv_journalTitle);
            tv_issue_id = itemView.findViewById(R.id.tv_issue_id);
            tv_doi = itemView.findViewById(R.id.tv_doi);
            tv_date_published = itemView.findViewById(R.id.tv_date_published);
            cover = itemView.findViewById(R.id.coverImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}