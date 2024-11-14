package User_Restaurant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class Apdater_PhanHoi extends ArrayAdapter<PhanHoi_Restaurant> {
    int idimg;
    ArrayList<PhanHoi_Restaurant> mylist;
    Activity act ;

    public Apdater_PhanHoi(@NonNull int idimg, ArrayList<PhanHoi_Restaurant> mylist, Activity act) {
        super(act, idimg, mylist);
        this.idimg = idimg;
        this.mylist = mylist;
        this.act = act;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater lay = act.getLayoutInflater();

        convertView = lay.inflate(idimg,null);
        PhanHoi_Restaurant mp = mylist.get(position);

        ImageView imv = convertView.findViewById(R.id.imguser);
        imv.setImageResource(mp.getImguser());

        TextView tvuser = convertView.findViewById(R.id.nameuser);
        tvuser.setText(mp.getNameuser());

        TextView tvph = convertView.findViewById(R.id.textphanhoi);
        tvph.setText(mp.getText_phanhoi());

        return convertView;
    }
}
