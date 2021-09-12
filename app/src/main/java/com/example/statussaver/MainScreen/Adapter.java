package com.example.statussaver.MainScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.statussaver.R;
import com.example.statussaver.VideoAndPicture.Picture;
import com.example.statussaver.VideoAndPicture.Video;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<PojoClass> content;
    Context context;

    public Adapter(ArrayList<PojoClass> content, Context context) {
        this.content = content;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(content.get(position).getUri().toString().endsWith(".mp4"))
            holder.playButton.setVisibility(View.VISIBLE);
        else
            holder.playButton.setVisibility(View.INVISIBLE);

        Glide.with(context).load(content.get(position).getUri()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(v -> {
            if(content.get(position).getUri().toString().endsWith(".mp4")){
                final String destination_path = Environment.getExternalStorageDirectory().getAbsolutePath()+Contants.DESTINATION_FOLDER;
                String path = content.get(position).getPath();
                Intent intent = new Intent(context, Video.class);
                intent.putExtra("VEDIO_DESTINATION_PATH",destination_path);
                intent.putExtra("VEDIO_CURRENT_FOLDER",path);
                intent.putExtra("FILENAME_VIDEO",content.get(position).getFilename());
                intent.putExtra("VEDIO_URI",content.get(position).getUri().toString());
                context.startActivity(intent);
            }
            else{
                final String destination_path = Environment.getExternalStorageDirectory().getAbsolutePath()+Contants.DESTINATION_FOLDER;
                String path = content.get(position).getPath();
                Intent intent = new Intent(context, Picture.class);
                intent.putExtra("DESTINATION_PATH",destination_path);
                intent.putExtra("CURRENT_FOLDER",path);
                intent.putExtra("FILENAME",content.get(position).getFilename());
                intent.putExtra("URI",content.get(position).getUri().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail,playButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            playButton = itemView.findViewById(R.id.play_pause_button);
        }
    }
}
