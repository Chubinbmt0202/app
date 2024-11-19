package fragment_ngv;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.Accout_detail_userFragment;
import com.example.apprestaurant.R;

import BOOK_ACTIVITY.ArrayApdate_GioHang;
import BOOK_ACTIVITY.BookTable_Fragment;
import BOOK_ACTIVITY.Database_GioHang;
import BOOK_ACTIVITY.GioHang;
import BOOK_ACTIVITY.PayBook_Fragment;
import BOOK_ACTIVITY.Save_Money;
import HOME.Nagigationkey;
import Intro_register_login.LoginActivity;
import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment  implements ArrayApdate_GioHang.OnTotalAmountChangedListener , ArrayApdate_GioHang.Load  {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Khai báo xoá tất cả
    private TextView tvxoatatca;

    // Khai báo tính tổng tiền
    private TextView tvtongtien;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private int img[] = {R.drawable.haisanca_cakhoto,R.drawable.img_baongubotoi,R.drawable.img_bonuong,R.drawable.img_canuonghaivi};
//    private int dongia[] = {80000,45000,25000,75000};
//    private String tenmon[] = {"Cá khô tộ", "Bào ngư bơ tỏi","Bò nướng","Cá nướng hai vị"};

    Database_GioHang data;
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

//        order = new Apdate_OrderCategory(this);
        cv_book = view.findViewById(R.id.carviewbookdb);



        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        data = new Database_GioHang(getContext());
        Cursor cs = data.Duyetmonan();
        list = new ArrayList<>();

        if (cs.moveToFirst()) {
            do {
                String result = cs.getString(2).replaceAll("[^\\d]", "");  // Clean the result string
                String ktr = result.substring(0,result.length() - 1);
                if (!result.isEmpty()) {
                    int a = Integer.parseInt(ktr);  // Parse the numeric value only if it's not empty
                    int imgColumnIndex = cs.getColumnIndex("IMG");
                    if (imgColumnIndex != -1) {
                        int img = cs.getInt(imgColumnIndex);  // Safely get the image column value

                        // Add the item to the list
                        GioHang ct = new GioHang(a, img,cs.getString(1));
                        list.add(ct);
                    } else {
                        Log.e("Database Error", "IMG column not found in the database");
                    }
                } else {
                    Log.e("Data Error", "Empty or invalid value found in the result column");
                }
            } while (cs.moveToNext());  // Continue to the next row
        } else {
            Log.e("Database Error", "Cursor is empty");
        }



        if(list.size() != 0) {
            apdate = new ArrayApdate_GioHang(list, getActivity(), this,this);
            rcv.setAdapter(apdate);
            XoaTatCa(list);
        }

        GanKQDatBan();
        tvtongtien = view.findViewById(R.id.tvtongtien);

        ktr();
        share = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        if(share != null)
        {
            ktra = 1;
            if (ktra == share.getInt("kt", 0))
            {
                tvtang.setText(share.getString("kqtang", " "));
                tvban.setText(share.getString("kqban", " "));
                tvdate.setText(share.getString("kqdate", " "));
                tvgio.setText(share.getString("kqgio", " "));
                tvbook.setText("Chỉnh sửa");
            }
        }
        if (getArguments() != null) {
              tvtang.setText(getArguments().getString("tang"));
              tvban.setText(getArguments().getString("ban"));
              tvdate.setText(getArguments().getString("date"));
              tvgio.setText(getArguments().getString("gio"));
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
        return view;
    }

    @Override
    public void onTotalAmountChanged(int formattedSum) {
        // Cập nhật tvtongtien trong BookFragment
        if (tvtongtien != null) {
            int b = formattedSum;
            String result = tvtongtien.getText().toString().replaceAll("[^\\d]", "");
            b += Integer.parseInt(result);

            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String chuyendoi = formatter.format(b);
            tvtongtien.setText("Tổng tiền: "+chuyendoi + " vnd");
        }
    }

   private void XoaTatCa(ArrayList<GioHang> gh)
   {
      if(gh!= null) {
          tvxoatatca = view.findViewById(R.id.tvxoatatca);
          tvxoatatca.setVisibility(View.VISIBLE);
          tvxoatatca.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  data.Xoatatca();
                  list.clear();
                  apdate.notifyDataSetChanged();
                  Toast.makeText(getContext(), "Gỡ tất cả các món thành công", Toast.LENGTH_SHORT).show();
              }
          });
      }
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
                ((Nagigationkey) getActivity()).navigateToFragment(R.id.account);
                Fragment newFragment = new Accout_detail_userFragment(); // Replace with your XuFragment class

                Bundle  bl = new Bundle();
                bl.putInt("bk",1);
                newFragment.setArguments(bl);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

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

    }
    private void TiepTuc()
    {
        btnnext = view.findViewById(R.id.btn_datban);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvtang.getText().equals("Rỗng")) {
                  Toast.makeText(getContext(),"Vui long chọn bàn!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Fragment newFragment = new PayBook_Fragment(); // Replace with your XuFragment class
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

    }


    @Override
    public void Loaddata(String tenmonan, int gia) {
        for(int i = 0 ; i< list.size() ; i++)
        {
            GioHang gh = list.get(i);
            if(gh.getTenmon().equals(tenmonan) && gh.getGia() == gia)
            {
                list.remove(i);
            }
        }
        apdate.notifyDataSetChanged();
    }
}