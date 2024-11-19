package Order;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import BOOK_ACTIVITY.Database_GioHang;
import Category.DetailCategory_PhoFragment;

public class Apdate_OrderCategory extends RecyclerView.Adapter<Apdate_OrderCategory.ViewHolder>{

    private Context contex;
    private ArrayList<Class_CategoryBanhCuon> list;
    boolean isFavorite = false;
    private Danhsachgiohang dsgh;
    ArrayList<Class_CategoryBanhCuon> ds = new ArrayList<>();
    Database_GioHang data;
    boolean one = false;


    @Override
    public void onBindViewHolder(@NonNull Apdate_OrderCategory.ViewHolder holder, int position) {
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

            holder.home_addmenubanhcuon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.imgmonan.setImageResource(item.getImgmonan());

                    ds.add(new Class_CategoryBanhCuon(item.getTenmonan(),item.getImgmonan(),item.getGiadonvi()));
                    data = new Database_GioHang(contex);
                    boolean kt = false;

                    if (data != null) {
                        Cursor cs = data.getTenmonan();
                        if (cs.getCount() != 0) {
                            cs.moveToFirst();
                                while(cs.isAfterLast() == false) {
                                   if (item.getTenmonan().equals(cs.getString(0)) && item.getGiadonvi().equals(cs.getString(1))) {
                                        kt = true;
                                        return;
                                    }
                                    cs.moveToNext();
                                }

                            if (kt== false) {
                                data.Themsanpham(item.getTenmonan(), item.getGiadonvi(), item.getImgmonan());
                                Toast.makeText(contex,"Thêm món ăn thành công.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            data.Themsanpham(item.getTenmonan(), item.getGiadonvi(), item.getImgmonan());
                            Toast.makeText(contex,"Thêm món ăn thành công.",Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            });

    }

    public interface Danhsachgiohang{
        void danhsachgiohangchange(ArrayList<Class_CategoryBanhCuon> list);
    }

    public Apdate_OrderCategory(ArrayList<Class_CategoryBanhCuon> list, Context contex ) {
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
        Button home_addmenubanhcuon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tv_monan); // ID của TextView trong layout
            tvtggia = itemView.findViewById(R.id.tv_giadonvi);
            imgmonan = itemView.findViewById(R.id.imgmonan);
            imgtraitim = itemView.findViewById(R.id.imgtraitim);
            home_addmenubanhcuon = itemView.findViewById(R.id.home_addmenubanhcuon);

            imgtraitim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Thay đổi trạng thái và cập nhật hình ảnh dựa trên trạng thái
                    if (isFavorite) {
                        imgtraitim.setImageResource(R.drawable.btn_2); // Đặt về biểu tượng "yêu thích"
                    } else {
                        imgtraitim.setImageResource(R.drawable.iconn_ttraitim); // Đặt về biểu tượng "không yêu thích"
                    }
                    // Thay đổi giá trị của biến boolean
                    isFavorite = !isFavorite;
                }
            });

        }
    }

}
