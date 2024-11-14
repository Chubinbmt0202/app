package HOME;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import BOOK_ACTIVITY.GioHang;

public class ArrayApdate_Home extends ArrayAdapter<GioHang> {

    private Context contex;
    private int idlayout;
    private ArrayList<GioHang> list;

    public ArrayApdate_Home(@NonNull Context contex, int idlayout, ArrayList<GioHang> list) {
        super(contex,idlayout,list);

        this.contex = contex;
        this.idlayout = idlayout;
        this.list = list;

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(contex);
        convertView = myinflater.inflate(idlayout,null);

        GioHang gh = list.get(position);

        ImageView imgview = convertView.findViewById(R.id.imgmonan);
        imgview.setImageResource(gh.getImg());

        TextView tv = convertView.findViewById(R.id.tvtenmonan);
        tv.setText(gh.getTenmon());

        TextView tvdg = convertView.findViewById(R.id.tvsumdongia);
        tvdg.setText(gh.getGia());

        return convertView;
    }
}
