package com.langhua.yicor.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yicor on 2016/8/21.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Article> article;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewAdapter(List<Article> article, Context context) {
        this.article = article;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_item, parent, false);
        ViewHolder nv = new ViewHolder(v);
        return nv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        final int j = position;

        holder.text_title.setText(article.get(position).getTitle());
        holder.text_desc.setText(article.get(position).getDesc());

        /*
        if (mOnItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }
        */

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("Article", article.get(j));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return article.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        TextView text_desc;

        public ViewHolder(final View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.article_title);
            text_desc = (TextView) itemView.findViewById(R.id.article_desc);
        }
    }
}
