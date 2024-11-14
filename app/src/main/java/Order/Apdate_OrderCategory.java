package Order;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import Accout.ArrayAdate_DetailHistory;
import Accout.Class_Detail;
import Category.DetailCategory_PhoFragment;

public class Apdate_OrderCategory extends RecyclerView.Adapter<Apdate_OrderCategory.ViewHolder>{

    private Context contex;
    private ArrayList<Class_CategoryBanhCuon> list;

    @Override
    public void onBindViewHolder(@NonNull Apdate_OrderCategory.ViewHolder holder, int position) {

            Class_CategoryBanhCuon item = list.get(position);
            holder.tvtenmonan.setText(String.valueOf(item.getTenmonan()));
            holder.tvtggia.setText(String.valueOf(item.getGiadonvi()));
            holder.imgmonan.setImageResource(item.getImgmonan());

    }



    public Apdate_OrderCategory(ArrayList<Class_CategoryBanhCuon> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Apdate_OrderCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.list_categorycanhcuon, parent, false);
        return new Apdate_OrderCategory.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonan , tvtggia  ;
        ImageView imgmonan , imgtraitim ;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tv_monan); // ID của TextView trong layout
            tvtggia = itemView.findViewById(R.id.tv_giadonvi);
            imgmonan = itemView.findViewById(R.id.imgmonan);
            imgtraitim = itemView.findViewById(R.id.imgtraitim);


            imgmonan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = new DetailCategory_PhoFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) contex).getSupportFragmentManager(); // Use getSupportFragmentManager for activity context
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

            imgtraitim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgtraitim.setImageResource(R.drawable.trautimm);
                }
            });

        }
    }

}
