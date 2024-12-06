package Order;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Accout.ArrayAdate_DetailHistory;
import Accout.Class_Detail;
import BOOK_ACTIVITY.Database_GioHang;
import Category.DetailCategory_PhoFragment;

public class Apdate_OrderCategory extends RecyclerView.Adapter<Apdate_OrderCategory.ViewHolder>{
    private DatabaseReference mDatabase;
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

            if(item.getTinhtrang() == 1)
            {
                holder.imgtraitim.setImageResource(R.drawable.iconn_ttraitim); // Đặt về biểu tượng "yêu thích"
            }
        holder.imgtraitim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = holder.getAdapterPosition();
                if (id == RecyclerView.NO_POSITION) {
                    return;
                }
                String chuoi = null;
                if(item.getMadanhmuc().equals("1"))
                {
                    chuoi = "Order/BanhCuon/Listt";
                }
                else if(item.getMadanhmuc().equals("2"))
                {
                    chuoi = "Order/Comchaosup/Listt";
                }
                else if(item.getMadanhmuc().equals("3"))
                {
                    chuoi = "Order/Drink/Listt";
                }
                else if(item.getMadanhmuc().equals("4"))
                {
                    chuoi = "Order/Gaboheo/Listt";
                }
                else if(item.getMadanhmuc().equals("5"))
                {
                    chuoi = "Order/HaiSan/Listt";
                }
                else if(item.getMadanhmuc().equals("6"))
                {
                    chuoi = "Order/HotPog/Listt";
                }
                else if(item.getMadanhmuc().equals("7"))
                {
                    chuoi = "Order/Pho/Listt";
                }
                else if(item.getMadanhmuc().equals("8"))
                {
                    chuoi = "Order/TrangMieng/Listt";
                }

                if (isFavorite) {
                    mDatabase.child("Order").child("TrangMieng").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    holder.imgtraitim.setImageResource(R.drawable.btn_2);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(chuoi);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put(id + "", 0);

                    databaseReference.updateChildren(updates)
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(chuoi);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put(id+"", 1);

                    databaseReference.updateChildren(updates)
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));
                    holder.imgtraitim.setImageResource(R.drawable.img_yeuthich);
                }

                isFavorite = !isFavorite;
            }
        });
            holder.imgmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment = new DetailCategory_PhoFragment();

                // Tạo Bundle và truyền dữ liệu
                Bundle bundle = new Bundle();
                bundle.putInt("img", item.getImgmonan());
                bundle.putString("gia", item.getGiadonvi());
                bundle.putString("ten", item.getTenmonan());
                bundle.putString("mota", item.getMota());
                bundle.putInt("tt", item.getTinhtrang());
                newFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) contex).getSupportFragmentManager();
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

                    ds.add(new Class_CategoryBanhCuon(item.getMadanhmuc(),item.getMasanpham(),item.getTenmonan(),item.getImgmonan(),item.getGiadonvi(),item.getMota(),item.getTinhtrang()));
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
        ImageView imgmonan , imgtraitim ,home_addmenubanhcuon ;
//        Button home_addmenubanhcuon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tv_monan); // ID của TextView trong layout
            tvtggia = itemView.findViewById(R.id.tv_giadonvi);
            imgmonan = itemView.findViewById(R.id.imgmonan);
            imgtraitim = itemView.findViewById(R.id.imgtraitim);
            home_addmenubanhcuon = itemView.findViewById(R.id.home_addmenubanhcuon);

        }
    }

}
