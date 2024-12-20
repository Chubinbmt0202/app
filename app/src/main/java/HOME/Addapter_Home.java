package HOME;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import Category.DetailCategory_PhoFragment;
import Order.Class_CategoryBanhCuon;
import fragment_ngv.Class_Home;

public class Addapter_Home extends BaseAdapter {
    private Activity context;
    private ArrayList<Class_Home> list;
    boolean isFavorite = false;

    public Addapter_Home(Activity context, ArrayList<Class_Home> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater li = context.getLayoutInflater();

            convertView = li.inflate(R.layout.list_categorycanhcuon, null);
        }
        Class_Home home = list.get(position);

        ImageView imghome = convertView.findViewById(R.id.imgmonan);
        imghome.setImageResource(home.getImgmonan());

        TextView tvtenmonan = convertView.findViewById(R.id.tv_monan);
        tvtenmonan.setText(home.getTenmonan());

        TextView tvtggia = convertView.findViewById(R.id.tv_giadonvi);
        tvtggia.setText(home.getGiadonvi());

        ImageView imgtraitim = convertView.findViewById(R.id.imgtraitim);

        imgtraitim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgtraitim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isFavorite) {
                            imgtraitim.setImageResource(R.drawable.btn_2);
                        } else {
                            imgtraitim.setImageResource(R.drawable.img_yeuthich);
                        }
                        isFavorite = !isFavorite;
                    }
                });

            }
        });

        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new DetailCategory_PhoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("img", home.getImgmonan());
                bundle.putString("gia", home.getGiadonvi());
                bundle.putString("ten", home.getTenmonan());
                bundle.putInt("ten", home.getTraitim());
                newFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager(); // Use getSupportFragmentManager for activity context
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return convertView;
    }
}
