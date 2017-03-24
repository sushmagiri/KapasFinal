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

import java.util.ArrayList;

/**
 * Created by user on 3/5/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;
    ArrayList<Details> detailsList;
    private final CardAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Details details);
    }





    public CardAdapter(ArrayList<Details> detailsList, Context context, CardAdapter.OnItemClickListener listener) {
        this.listener = listener;
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
        holder.bind(detailsList.get(position),listener);



    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView;
        public TextView textViewName;


        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);


        }
        public void bind(final Details details, final OnItemClickListener listener) {

            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(details.getImageUrl(), ImageLoader.getImageListener(imageView, R.drawable.placeholder_banner_small, R.drawable.placeholder_banner_small));
            imageView.setImageUrl(details.getImageUrl(), imageLoader);
            textViewName.setText(details.getName());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(details);
                }
            });
        }

    }
}
