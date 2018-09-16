package bwie.com.moni320180914.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bwie.com.moni320180914.R;
import bwie.com.moni320180914.data.bean.InfoBean;

public class MainAdapters extends RecyclerView.Adapter<MainAdapters.ViewHolder> {
    List<InfoBean.Pois> data;
    Context context;
    private ViewHolder viewHolder;

    public MainAdapters(List<InfoBean.Pois> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listtitie.setText(data.get(position).getName());
        holder.listtupiao.setText(data.get(position).getDistance() + "m");
        List<InfoBean.Pois.Photo> photos = data.get(position).getPhotos();
        if (photos != null && photos.size() > 0) {
            Picasso.with(context).load(photos.get(0).getUrl()).into(holder.imageView);
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView listtitie;
        private TextView listtupiao;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_img);
            listtitie = itemView.findViewById(R.id.list_title);
            listtupiao = itemView.findViewById(R.id.list_tupiao);
        }
    }
}
