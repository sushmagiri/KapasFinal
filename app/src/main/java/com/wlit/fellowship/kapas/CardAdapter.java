package com.wlit.fellowship.kapas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.wlit.fellowship.kapas.volley.CustomVolleyRequest;

import java.util.List;

/**
 * Created by user on 3/5/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;

    //List of superHeroes
    List<Details> detailsList;

    public CardAdapter(List<Details> detailsList, Context context ) {
        this.imageLoader = imageLoader;
        this.context = context;
        this.detailsList = detailsList;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_imagelist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
        Details details =  detailsList.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(details.getImageUrl(), ImageLoader.getImageListener(holder.imageView, android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(details.getImageUrl(), imageLoader);
        holder.textViewName.setText(details.getName());
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView;
        public TextView textViewName;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);

        }
    }
}
