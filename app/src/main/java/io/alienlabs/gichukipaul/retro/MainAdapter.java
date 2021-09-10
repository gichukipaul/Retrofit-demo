package io.alienlabs.gichukipaul.retro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Viewholder> {

    private Activity activity;
    private ArrayList<MainData> list;

    public MainAdapter(Activity activity, ArrayList<MainData> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        MainData data = list.get(position);
        holder.txt.setText(data.getName());
        Glide.with(activity)
                .asBitmap()
                .load(data.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txt = itemView.findViewById(R.id.tv_name);
        }
    }
}
