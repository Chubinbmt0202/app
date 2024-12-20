package fragment_ngv;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import com.example.apprestaurant.R;
import com.google.firebase.database.DatabaseReference;

import BOOK_ACTIVITY.ArrayApdate_GioHang;
import BOOK_ACTIVITY.BookTable_Fragment;
import BOOK_ACTIVITY.Database_GioHang;
import BOOK_ACTIVITY.GioHang;
import BOOK_ACTIVITY.PayBook_Fragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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

    int tt = 0;

    // Khai báo tính tổng tiền
    private TextView tvtongtien;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    Database_GioHang data;
    private ArrayList<GioHang> list ;
    private ArrayApdate_GioHang apdate ;
    private RecyclerView  rcv;
    private int iduser;
    private DatabaseReference mDatabase;
    // Tạo 1 SharedPreferences chứa các thành  phần
    private SharedPreferences share;
    private int ktra;

    private View view;
   // khai báo kết quả từ đặt bàn
    private TextView tvgio , tvban, tvdate , tvsonguoi;
    // Khai báo 2 nút đăng nhập và đặt bàn và tiep tuc
    private TextView tvlogin , tvbook , btnnext;
    // Khai báo carviewbook cho Activity
    private CardView cv_book;
    private int tongtiendata;
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

        view = inflater.inflate(R.layout.fragment_book, container, false);

        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",0);
        
        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        data = new Database_GioHang(getContext());
        Cursor cs = data.Duyetmonan();
        list = new ArrayList<>();
        tongtiendata = 0;
        if (cs.moveToFirst()) {
            do {
                String result = cs.getString(2).replaceAll("[^\\d]", "");
                if (!result.isEmpty()) {
                    int a = Integer.parseInt(result);
                    int imgColumnIndex = cs.getColumnIndex("IMG");
                    if (imgColumnIndex != -1) {
                        int img = cs.getInt(imgColumnIndex);
                        if(cs.getInt(4) ==1)
                        {
                            tongtiendata += (a*cs.getInt(5));
                        }

                        GioHang ct = new GioHang(cs.getInt(0),a, img,cs.getString(1),cs.getInt(4),cs.getInt(5));
                        list.add(ct);
                    } else {
                        Log.e("Database Error", "IMG column not found in the database");
                    }
                } else {
                    Log.e("Data Error", "Empty or invalid value found in the result column");
                }
            } while (cs.moveToNext());
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
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String chuyendoi = formatter.format(tongtiendata);
        tvtongtien.setText(chuyendoi + " vnd");

        if(iduser != -1) {
            ktr();
        if (getArguments() != null) {
              tvgio.setText(getArguments().getString("gio"));
              tvban.setText(getArguments().getString("ban"));
              tvdate.setText(getArguments().getString("date"));
              tvsonguoi.setText(getArguments().getString("people"));
              share = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
              SharedPreferences.Editor editor = share.edit();
              editor.putString("kqgio",getArguments().getString("gio") );
              editor.putString("kqban",getArguments().getString("ban") );
              editor.putString("kqdate",getArguments().getString("date") );
              editor.putString("kqpeople",getArguments().getString("people") );
              editor.putInt("kt", 1 );
              editor.commit();
              tvbook.setText("Chỉnh sửa");
        }
            TiepTuc();
        }
        else
        {
            Toast.makeText(getContext(),"Vui lòng đăng nhập.",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onTotalAmountChanged(int formattedSum) {
        if (tvtongtien != null) {
            int b = formattedSum;
            String result = tvtongtien.getText().toString().replaceAll("[^\\d]", "");
            b += Integer.parseInt(result);

            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String chuyendoi = formatter.format(b);
            tvtongtien.setText(chuyendoi + " vnd");
        }
    }

   private void XoaTatCa(ArrayList<GioHang> gh)
   {
      if(gh!= null) {
          tvxoatatca = view.findViewById(R.id.tvxoatatca);
          tvxoatatca.setVisibility(View.VISIBLE);
          tvxoatatca.setVisibility(View.VISIBLE);
          tvxoatatca.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                  alert.setTitle("Xác nhận");
                  alert.setMessage("Bạn có chắc chắn muốn loại bỏ tất cả các món ăn?");
                  alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          data.Xoatatca();
                          list.clear();
                          apdate.notifyDataSetChanged();
                          Toast.makeText(getContext(), "Gỡ tất cả các món thành công", Toast.LENGTH_SHORT).show();
                          tvtongtien.setText("0 vnd");
                          dialog.dismiss();
                      }
                  });
                  alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                      }
                  });
                  alert.show();
              }
          });
      }
   }

    private void GanKQDatBan()
    {
        tvgio = view.findViewById(R.id.tv_giokqbk);
        tvban = view.findViewById(R.id.tv_bankqbk);
        tvdate = view.findViewById(R.id.tv_datekqbk);
        tvsonguoi = view.findViewById(R.id.tv_kqpeople);
    }
    private void ktr()
    {
        tvbook = view.findViewById(R.id.kt_booktb);
            tvbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = new BookTable_Fragment();
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
                Database_GioHang data = new Database_GioHang(getContext());
                Cursor cusor = data.getTinhtrang();
                cusor.moveToFirst();
                if(tvgio.getText().equals("Rỗng")) {
                  Toast.makeText(getContext(),"Vui lòng đặt bàn trước!",Toast.LENGTH_SHORT).show();
                }
                else if(cusor.getCount() <= 0)
                {
                    Toast.makeText(getContext(),"Vui lòng chọn món ăn!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Fragment newFragment = new PayBook_Fragment();
                    Bundle bl = new Bundle();
                    bl.putString("people",tvsonguoi.getText().toString());
                    bl.putString("ban",tvban.getText().toString());
                    bl.putString("gio",tvgio.getText().toString());
                    bl.putString("date",tvdate.getText().toString());
                    bl.putString("sum",tvtongtien.getText().toString());
                    newFragment.setArguments(bl);

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