package com.example.vi5h.cinemanow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

//This Class is used to declare and attach data to various UI elements for screen 1 and 2
public class ScreenAdapter extends RecyclerView.Adapter<ScreenAdapter.ScreenViewHolder>
{

    private Context con;
    private List<ScreenModel> screen1List;
    public ScreenAdapter(Context con, List<ScreenModel> screen1list) {
        this.con = con;
        this.screen1List = screen1list;
    }

    @Override
    public ScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.list_layout_s1,null);
        ScreenViewHolder holder = new ScreenViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScreenViewHolder holder, final int position) {
        ScreenModel screen1 = screen1List.get(position);
        holder.txtMovieName.setText(screen1.getMovieName());
        holder.txtShowtime.setText(screen1.getShowTime());
    }

    @Override
    public int getItemCount() {
        return screen1List.size() ;
    }

    class ScreenViewHolder extends RecyclerView.ViewHolder{
        TextView txtMovieName, txtShowtime;
        public ScreenViewHolder(View itemView) {
            super(itemView);
            txtMovieName = (TextView) itemView.findViewById(R.id.txtMovieNameS1);
            txtShowtime = (TextView) itemView.findViewById(R.id.txtShowTimeS1);
        }
    }
}
