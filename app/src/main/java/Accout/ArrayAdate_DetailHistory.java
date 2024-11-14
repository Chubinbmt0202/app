package Accout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class ArrayAdate_DetailHistory extends RecyclerView.Adapter<ArrayAdate_DetailHistory.ViewHolder>{
    private Context contex;
    private ArrayList<Class_Detail> list;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Class_Detail item = list.get(position);
        holder.tvtenmonan.setText(item.getTenmon());
        holder.tvtggia.setText(String.valueOf(item.getTonggia()));
        holder.imgmonan.setImageResource(item.getImg());
        holder.solg.setText(String.valueOf(item.getSolg()));
        holder.tvgiagoc.setText(String.valueOf(item.getGiagoc()));
    }


    public ArrayAdate_DetailHistory(ArrayList<Class_Detail> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ArrayAdate_DetailHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.list_item_detailhistory, parent, false);
        return new ViewHolder(view);
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonan , tvtggia , tvgiagoc , solg;
        ImageView imgmonan;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tvtenmonan); // ID của TextView trong layout
            tvtggia = itemView.findViewById(R.id.tvsumdongia);
            imgmonan = itemView.findViewById(R.id.imgmonan);
            tvgiagoc = itemView.findViewById(R.id.tv_giagoc);
            solg = itemView.findViewById(R.id.tv_solg);
        }
    }
}
