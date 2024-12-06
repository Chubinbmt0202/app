package Order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apprestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fragment_ngv.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Order_CategorydessertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategorydessertFragment extends Fragment {

    private View view;
    // Khai báo quay lại
    private ImageView imgback;
    private List<String> tma;
    private List<Integer> anhh;
    private List<Integer> tt;
    private List<String> Gia;
    private String madanhmuc;
    private List<String> masanpham;
    private List<String> mota;
    private DatabaseReference mDatabase;
//    private String tenmonan[] = {"Trái cây thập cẩm", "Chè khúc bạch", "Sữa chua xoài", "Caremen", "Chè hoa cau", "Chè hạt lựu"};
//    private String giadonvi[] = {"138.000 vnd/đĩa", "48.000 vnd/chén", "48.000 vnd/phần", "42.000 vnd/đĩa", "32.000 vnd/đĩa", "48.000 vnd/ly"};
//    private int imganh[] = {
//            R.drawable.img_traicaythapcam,
//            R.drawable.img_chekhucbach,
//            R.drawable.img_suachuaxoai,
//            R.drawable.img_caramen,
//            R.drawable.img_chehoacau,
//            R.drawable.img_chesshatluu
//    };
    private ArrayList<Class_CategoryBanhCuon> list;
    private Apdate_OrderCategory apdate;
    private RecyclerView rcv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Order_CategorydessertFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_CategorydessertFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_CategorydessertFragment newInstance(String param1, String param2) {
        Order_CategorydessertFragment fragment = new Order_CategorydessertFragment();
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
        view = inflater.inflate(R.layout.fragment_order__categorydessert, container, false);


        rcv = (RecyclerView) view.findViewById(R.id.rcv_categorydessert);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tma = new ArrayList<>();
        anhh = new ArrayList<>();
        tt = new ArrayList<>();
        Gia = new ArrayList<>();
        list = new ArrayList<>();
        mota = new ArrayList<>();
        masanpham = new ArrayList<>();

        loadFirebasemadanhmuc();
        Quailai();
        return view;
    }

    private void Quailai() {
        imgback = view.findViewById(R.id.img_backdesert);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment = new OrderFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void loadFirebasemadanhmuc() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("TrangMieng").child("MaDanhMuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    madanhmuc = snapshot.getValue(String.class);
                loadMasanpham();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadMasanpham() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("TrangMieng").child("Listid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    masanpham.add(itemSnapshot.getValue(String.class));
                }
                loadFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseData() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("TrangMieng").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tma.add(itemSnapshot.getValue(String.class));
                }
                loadImageData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImageData() {
        mDatabase.child("Order").child("TrangMieng").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageName = itemSnapshot.getValue(String.class);
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
                    if (resourceId != 0) {
                        anhh.add(resourceId);
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy ảnh: " + imageName, Toast.LENGTH_SHORT).show();
                    }
                }
                loadFirebaseMota();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải danh sách ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseMota() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("TrangMieng").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    mota.add(itemSnapshot.getValue(String.class));
                }
                loadFirebasetinhtrang();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebasetinhtrang() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("TrangMieng").child("Listt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tt.add(itemSnapshot.getValue(Integer.class));
                }
                loadPriceData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadPriceData() {
        mDatabase.child("Order").child("TrangMieng").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }

                if (tma.size() == anhh.size() && anhh.size() == Gia.size()) {
                    list = new ArrayList<>();
                    for (int i = 0; i < tma.size(); i++) {
                        list.add(new Class_CategoryBanhCuon(madanhmuc,masanpham.get(i),tma.get(i), anhh.get(i), Gia.get(i),mota.get(i),tt.get(i)));
                    }

                    // Update RecyclerView adapter
                    apdate = new Apdate_OrderCategory(list, getActivity());
                    rcv.setAdapter(apdate);
                } else {
                    Toast.makeText(getActivity(), "Dữ liệu không đồng bộ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải giá", Toast.LENGTH_SHORT).show();
            }
        });
    }
}