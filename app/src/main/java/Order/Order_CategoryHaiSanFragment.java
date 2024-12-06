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
 * Use the {@link Order_CategoryHaiSanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategoryHaiSanFragment extends Fragment {

    private View view;
    private DatabaseReference mDatabase;

    // Khai báo quay lại
    private ImageView imgback;

//    private  String tenmonan[] = {"Cá kho tộ","Cá lóc kho tộ","Cá mú kho tộ","Cá nướng hai vị","Ốc hương hấp xả","Nghêu nướng mỡ hành","Ốc hương hấp xả"};
//    private String giadonvi[] = {"225.000 vnd/tộ","170.000 vnd/đĩa","320.000 vnd/phần","310.000 vnd/phần","366.000 vnd/đĩa","235.000 vnd/đĩa","366.000 vnd/thố"};
//    private int imganh[] = {
//            R.drawable.haisanca_cakhoto,
//            R.drawable.img_calockhoto,
//            R.drawable.img_camukhoto,
//            R.drawable.img_canuonghaivi,
//            R.drawable.menu_category_ochuonghapxa,
//            R.drawable.menu_category_nghuenuongmohanh,
//            R.drawable.menu_category_ochuonghapxa  // Duplicate entry
//    };
    private ArrayList<Class_CategoryBanhCuon> list ;
    private Apdate_OrderCategory apdate ;
    private RecyclerView rcv;

    private List<String> tma;
    private List<Integer> anhh;
    private List<Integer> tt;
    private List<String> Gia;
    private List<String> mota;
    private  String madanhmuc;
    private List<String> masanpham;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Order_CategoryHaiSanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_CategoryHaiSanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_CategoryHaiSanFragment newInstance(String param1, String param2) {
        Order_CategoryHaiSanFragment fragment = new Order_CategoryHaiSanFragment();
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
        view = inflater.inflate(R.layout.fragment_order__category_hai_san, container, false);

        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        tma = new ArrayList<>();
        anhh = new ArrayList<>();
        Gia = new ArrayList<>();
        list = new ArrayList<>();
        masanpham = new ArrayList<>();
        mota = new ArrayList<>();
        tt = new ArrayList<>();

        loadFirebasemadanhmuc();

        Quailai();
        return view;
    }
    private void Quailai() {
        imgback = view.findViewById(R.id.img_backtoday);
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
        mDatabase.child("Order").child("HaiSan").child("MaDanhMuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    madanhmuc = snapshot.getValue(String.class);
                    loadMasanpham();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadMasanpham() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("HaiSan").child("Listid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    masanpham.add(itemSnapshot.getValue(String.class));
                }
                loadFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải mã sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadFirebaseData() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("HaiSan").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Order").child("HaiSan").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageName = itemSnapshot.getValue(String.class);
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
                    if (resourceId == 0) {
                        resourceId = R.drawable.haisanca_cakhoto; // Hình mặc định
                    }
                    anhh.add(resourceId);
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
        mDatabase.child("Order").child("HaiSan").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    mota.add(itemSnapshot.getValue(String.class));
                }
                loadFirebaseMtt();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseMtt() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("HaiSan").child("Listt").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Order").child("HaiSan").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }

                if (tma.size() == anhh.size() && anhh.size() == Gia.size()) {
                    list = new ArrayList<>();
                    for (int i = 0; i < masanpham.size(); i++) {
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