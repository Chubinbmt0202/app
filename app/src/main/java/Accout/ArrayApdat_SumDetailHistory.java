package Accout;

import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class ArrayApdat_SumDetailHistory extends RecyclerView.Adapter<ArrayApdat_SumDetailHistory.ViewHolder>{
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Class_sumdeltaihistory item = list.get(position);
        holder.idhoadon.setText("Hóa đơn #"+String.valueOf(item.getIdhoadon()));
        holder.textngaydat.setText(item.getNgaydat());
        if(item.getTinhtrang() == 2)
        {
            holder.texttinhtrang.setText("Thành công");
            holder.tvtinhtrang.setText("Không thể hủy");
        }
        else if(item.getTinhtrang() == 3)
        {
            holder.texttinhtrang.setText("Đã hủy");
            holder.tvtinhtrang.setText("Hủy thành công");
            holder.btnchitiet.setText("Đặt Lại");
        }
        else if (item.getTinhtrang() == 1)
        {
            holder.texttinhtrang.setText("Chờ xử lí");
            holder.tvtinhtrang.setText("Có thể hủy");
        }
        else {
            holder.texttinhtrang.setText("Thành công");
            holder.tvtinhtrang.setText("Phản hồi");
        }

        holder.btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(contex, detailhistory_Activity.class);
                if(item.getTinhtrang() == 1)
                {
                    it.putExtra("ktra",1);
                }

                else if(item.getTinhtrang() == 3)
                {
                    it.putExtra("ktra",3);
                    it.putExtra("id",item.getIdhoadon());
                }
                else if(item.getTinhtrang() == 2)
                {
                    it.putExtra("ktra",2);
                }
                else if(item.getTinhtrang() == 4)
                {
                    it.putExtra("ktra",4);
                }
                startActivity(contex,it,null);
            }
        });

    }

    private Context contex;
    private ArrayList<Class_sumdeltaihistory> list;


    public ArrayApdat_SumDetailHistory(ArrayList<Class_sumdeltaihistory> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ArrayApdat_SumDetailHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.children_activity_sum_detailhistory, parent, false);
        return new ArrayApdat_SumDetailHistory.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textngaydat , texttinhtrang , idhoadon , tvtinhtrang;
        Button btnchitiet ;
        public ViewHolder(View itemView) {
            super(itemView);
            idhoadon = itemView.findViewById(R.id. idhoadon); // ID của TextView trong layout
            texttinhtrang = itemView.findViewById(R.id.tv_tinhtrang);
            textngaydat = itemView.findViewById(R.id.tv_ngaydat);
            btnchitiet = itemView.findViewById(R.id.btn_detail);
            tvtinhtrang = itemView.findViewById(R.id.tvtinhtrang);
        }
    }
}
