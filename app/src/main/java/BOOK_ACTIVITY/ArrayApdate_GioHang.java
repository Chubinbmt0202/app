package BOOK_ACTIVITY;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.R;

public class ArrayApdate_GioHang extends RecyclerView.Adapter<ArrayApdate_GioHang.ViewHolder> {
    private Context contex;
    private ArrayList<GioHang> list;


    public ArrayApdate_GioHang(ArrayList<GioHang> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ArrayApdate_GioHang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.activity_list_orderbook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrayApdate_GioHang.ViewHolder holder, int position) {
        GioHang item = list.get(position);
        holder.tvtenmonan.setText(item.getTenmon());
        int a = item.getGia()/1000;
        holder.tvgia.setText(a +"." +"000 vnd");
        holder.imgmonan.setImageResource(item.getImg());

        holder.imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int solg = Integer.parseInt(holder.tvkqsolg.getText().toString());
                solg +=1 ;
                int sum = solg * item.getGia();
                holder.tvkqsolg.setText(String.valueOf(solg));
                NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
                String formattedSum = formatter.format(sum);
                holder.tvsumkq.setText(formattedSum + " vnd");

            }
        });

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int solg = item.getGia();
                if(solg > 0) {
                    solg -= 1;
                    int sum =  item.getGia() / solg;
                    holder.tvkqsolg.setText(String.valueOf(solg));
                    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
                    String formattedSum = formatter.format(sum);
                    holder.tvsumkq.setText(formattedSum + " vnd");
                }
                else
                {
                    for (int i = 0 ; i < list.size() ; i++ )
                    {
                        GioHang gh = list.get(i);
                        if(gh.getTenmon().equals( holder.tvtenmonan.getText().toString()))
                        {
                            Toast.makeText(contex,"Gỡ món thành công",Toast.LENGTH_SHORT);
                        }
                    }

                }
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonan , tvgia , tvkqsolg , tvsumkq , tvtongtien;
        ImageView imgmonan , imgadd , imgdelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tvtenmonan); // ID của TextView trong layout
            tvgia = itemView.findViewById(R.id.tvsumdongia);
            imgmonan = itemView.findViewById(R.id.imgmonan);
            tvkqsolg = itemView.findViewById(R.id.tvkqsolg);
            imgadd = itemView.findViewById(R.id.imgadd);
            imgdelete = itemView.findViewById(R.id.imgdelete);
            tvsumkq = itemView.findViewById(R.id.tvsumdongia);


        }
    }
}
