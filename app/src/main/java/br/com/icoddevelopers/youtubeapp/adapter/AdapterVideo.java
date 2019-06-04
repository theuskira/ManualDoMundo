package br.com.icoddevelopers.youtubeapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.icoddevelopers.youtubeapp.R;
import br.com.icoddevelopers.youtubeapp.model.Item;
import br.com.icoddevelopers.youtubeapp.model.Video;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Item> videos;
    private Context context;

    public AdapterVideo(List<Item> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_video, viewGroup, false);

        return  new AdapterVideo.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Item video = videos.get(i);
        myViewHolder.titulo.setText(video.snippet.title);

        String url = video.snippet.thumbnails.high.url;

        Picasso.get().load(url).into(myViewHolder.capa);

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{

            TextView titulo, descricao, data;
            ImageView capa;

            public  MyViewHolder(View itemView){
                super(itemView);

                titulo = itemView.findViewById(R.id.txtTitulo);
                capa = itemView.findViewById(R.id.imgCapa);

            }

        }

}
