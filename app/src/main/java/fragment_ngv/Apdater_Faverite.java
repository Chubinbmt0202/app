package fragment_ngv;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import Category.DetailCategory_PhoFragment;

public class Apdater_Faverite extends RecyclerView.Adapter<Apdater_Faverite.ViewHolder>{

    private Context context;
    private ArrayList<Yeuthich> list;

    boolean isFavorite = false;
    public Apdater_Faverite(Context context, ArrayList<Yeuthich> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Apdater_Faverite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favourite, parent, false);
        return new Apdater_Faverite.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Apdater_Faverite.ViewHolder holder, int position) {
         Yeuthich yt = list.get(position);

         holder.imganh.setImageResource(yt.getImganh());
         holder.tvyeuthich.setText(yt.getTenmonan());

        holder.cvyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new DetailCategory_PhoFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("img", yt.getImganh());
                bundle.putString("gia", yt.getGia());
                bundle.putString("ten", yt.getTenmonan());
                bundle.putString("mota", yt.getMota());
                bundle.putInt("tt", yt.getTinhtrang());
                newFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imganh , iconyeuthich;
        private TextView tvyeuthich;
        private CardView cvyeuthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.imganhyeuthich);
            tvyeuthich = itemView.findViewById(R.id.tvtenyeuthich);
            iconyeuthich = itemView.findViewById(R.id.iconyeuthich);
            cvyeuthich = itemView.findViewById(R.id.cvyeuthich);


            iconyeuthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Thay đổi trạng thái và cập nhật hình ảnh dựa trên trạng thái
                    if (isFavorite) {
                        iconyeuthich.setImageResource(R.drawable.img_yeuthich); // Đặt về biểu tượng "không yêu thích"
                    } else {
                        iconyeuthich.setImageResource(R.drawable.btn_2); // Đặt về biểu tượng "yêu thích"
                    }
                    // Thay đổi giá trị của biến boolean
                    isFavorite = !isFavorite;
                }
            });

        }
    }
}
