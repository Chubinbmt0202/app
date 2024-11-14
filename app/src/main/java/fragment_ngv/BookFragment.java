package fragment_ngv;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.health.connect.datatypes.units.Length;
import android.os.Bundle;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprestaurant.R;

import BOOK_ACTIVITY.ArrayApdate_GioHang;
import BOOK_ACTIVITY.BookTable_Fragment;
import BOOK_ACTIVITY.GioHang;
import BOOK_ACTIVITY.PayBook_Fragment;
import Intro_register_login.LoginActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Khai báo tổng tiền cần trả
    private TextView tvtongtien;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int img[] = {R.drawable.haisanca_cakhoto,R.drawable.img_baongubotoi,R.drawable.img_bonuong,R.drawable.img_canuonghaivi};
    private int dongia[] = {80000,45000,25000,75000};
    private String tenmon[] = {"Cá khô tộ", "Bào ngư bơ tỏi","Bò nướng","Cá nướng hai vị"};

    private ArrayList<GioHang> list ;
    private ArrayApdate_GioHang apdate ;
    private RecyclerView  rcv;

    // Tạo 1 SharedPreferences chứa các thành  phần
    private SharedPreferences share;
    private int ktra;

    private View view;
   // khai báo kết quả từ đặt bàn
    private TextView tvtang , tvban, tvdate , tvgio;
    // Khai báo 2 nút đăng nhập và đặt bàn và tiep tuc
    private TextView tvlogin , tvbook , btnnext;
    // Khai báo carviewbook cho Activity
    private CardView cv_book;
    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book, container, false);

        cv_book = view.findViewById(R.id.carviewbookdb);
        cv_book.setVisibility(View.GONE);

        rcv = (RecyclerView) view.findViewById(R.id.rcv_categorytoday);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        for ( int i = 0 ; i < img.length ; i++)
        {
            list.add(new GioHang(dongia[i],tenmon[i],img[i]));
        }
        apdate = new ArrayApdate_GioHang(list,getActivity());
        rcv.setAdapter(apdate);
        GanKQDatBan();
        ktr();
        share = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        ktra = 1;

        if( ktra == share.getInt("kt",0))
        {
            tvtang.setText(share.getString("kqtang"," "));
            tvban.setText(share.getString("kqban"," "));
            tvdate.setText(share.getString("kqdate"," "));
            tvgio.setText(share.getString("kqgio"," "));
            cv_book.setVisibility(View.VISIBLE);
            tvbook.setText("Chỉnh sửa");
        }

        if (getArguments() != null) {
              tvtang.setText(getArguments().getString("tang"));
              tvban.setText(getArguments().getString("ban"));
              tvdate.setText(getArguments().getString("date"));
              tvgio.setText(getArguments().getString("gio"));
              cv_book.setVisibility(View.VISIBLE);
              share = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
              SharedPreferences.Editor editor = share.edit();
              editor.putString("kqtang",getArguments().getString("tang") );
              editor.putString("kqban",getArguments().getString("ban") );
              editor.putString("kqdate",getArguments().getString("date") );
              editor.putString("kqgio",getArguments().getString("gio") );
              editor.putInt("kt", 1 );
              editor.commit();
        }
        TiepTuc();
        TinhTongTien();
        return view;
    }

    private void GanKQDatBan()
    {
        tvtang = view.findViewById(R.id.tv_tangkq);
        tvban = view.findViewById(R.id.tv_bankq);
        tvdate = view.findViewById(R.id.tv_datekq);
        tvgio = view.findViewById(R.id.tv_timekq);
    }
    private void ktr()
    {
        tvlogin = view.findViewById(R.id.kt_login);
        tvbook = view.findViewById(R.id.kt_booktb);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity() ,LoginActivity.class);
                startActivity(it);
            }
        });
     //   if(tvlogin.VISIBLE == 0)
     //   {
            tvbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = new BookTable_Fragment(); // Replace with your XuFragment class
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
              }
            });
        //}
    }
    private void TiepTuc()
    {
        btnnext = view.findViewById(R.id.btn_datban);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new PayBook_Fragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    private void TinhTongTien()
    {
        tvtongtien = view.findViewById(R.id.tvtongtien);
        int sum = 0;
        for ( int i = 0 ; i < img.length ; i++)
        {
            GioHang gh = list.get(i);
            sum += gh.getGia();
        }

        // Format the sum with thousands separators
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedSum = formatter.format(sum);

// Display the formatted sum
        tvtongtien.setText("Tổng tiền: " + formattedSum + " VND");
    }

}