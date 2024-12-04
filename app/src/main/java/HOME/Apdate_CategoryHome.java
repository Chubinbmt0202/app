package HOME;

import android.content.Context;
import android.net.Uri;
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

import Category.DetailCategory_PhoFragment;
import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;

public class Apdate_CategoryHome extends RecyclerView.Adapter<Apdate_CategoryHome.ViewHolder>{
    private Context contex;
    private ArrayList<Class_CategoryBanhCuon> list;
    boolean isFavorite = false;
    @Override
    public void onBindViewHolder(@NonNull Apdate_CategoryHome.ViewHolder holder, int position) {

        Class_CategoryBanhCuon item = list.get(position);
        holder.tvtenmonan.setText(String.valueOf(item.getTenmonan()));
        holder.tvtggia.setText(String.valueOf(item.getGiadonvi()));
        holder.imgmonan.setImageResource(item.getImgmonan());

        holder.imgmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new DetailCategory_PhoFragment();

                // Tạo Bundle và truyền dữ liệu
                Bundle bundle = new Bundle();
                bundle.putInt("img", item.getImgmonan());
                bundle.putString("gia", item.getGiadonvi());
                bundle.putString("ten", item.getTenmonan());
                newFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) contex).getSupportFragmentManager(); // Use getSupportFragmentManager for activity context
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }



    public Apdate_CategoryHome(ArrayList<Class_CategoryBanhCuon> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Apdate_CategoryHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.layout_categoryhome, parent, false);
        return new Apdate_CategoryHome.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonan , tvtggia  ;
        ImageView imgmonan , imgtraitim ;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tv_monanhome); // ID của TextView trong layout
            tvtggia = itemView.findViewById(R.id.tv_giadonvihome);
            imgmonan = itemView.findViewById(R.id.imgmonanhome);
            imgtraitim = itemView.findViewById(R.id.imgtraitimhome);




            imgtraitim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Thay đổi trạng thái và cập nhật hình ảnh dựa trên trạng thái
                    if (isFavorite) {
                        imgtraitim.setImageResource(R.drawable.btn_2); // Đặt về biểu tượng "yêu thích"
                    } else {
                        imgtraitim.setImageResource(R.drawable.img_yeuthich); // Đặt về biểu tượng "không yêu thích"
                    }
                    // Thay đổi giá trị của biến boolean
                    isFavorite = !isFavorite;
                }
            });

        }
    }
}
