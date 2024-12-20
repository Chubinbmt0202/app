package Order;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Use the {@link Order_CategoryComChaoSupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategoryComChaoSupFragment extends Fragment {

    private View view;

    // Khai báo quay lại
    private ImageView imgback;
    private List<String> tma;
    private List<Integer> anhh;
    private List<Integer> tt;
    private List<String> Gia;
    private String madanhmuc;
    private List<String> mota;
    private List<String> masanpham;
    private DatabaseReference mDatabase;
    private int iduser;

//    private  String tenmonan[] = {"Cơm chiên lá é","Cơm chiên hải sản","Cơm chiên trứng","Súp hải sản","Súp thập cẩm","Cháo bò bằm","Xôi nấm hạt sen"};
//    private String giadonvi[] = {"135.000 vnd/phần","210.000 vnd/thố","155.000 vnd/thố","62.000 vnd/chén","102.000 vnd/thố","109.000 vnd/thố","138.000 vnd/phần"};
//    private int imganh[] = {
//            R.drawable.img_comchiengionlae,
//            R.drawable.img_comchienhaisan,
//            R.drawable.img_comchientrung,
//            R.drawable.img_suphaisan,
//            R.drawable.img_supthapcam,
//            R.drawable.img_chaobobam,
//            R.drawable.img_xoinamhatsen
//    };
    private ArrayList<Class_CategoryBanhCuon> list ;
    private Apdate_OrderCategory apdate ;
    private RecyclerView rcv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Order_CategoryComChaoSupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_CategoryComChaoSupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_CategoryComChaoSupFragment newInstance(String param1, String param2) {
        Order_CategoryComChaoSupFragment fragment = new Order_CategoryComChaoSupFragment();
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
        view = inflater.inflate(R.layout.fragment_order__category_com_chao_sup, container, false);

        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tma = new ArrayList<>();
        tt = new ArrayList<>();
        anhh = new ArrayList<>();
        Gia = new ArrayList<>();
        mota = new ArrayList<>();
        list = new ArrayList<>();
        masanpham = new ArrayList<>();
        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",-1);
        loadFirebasemadanhmuc();
        Quailai();
        return view;
    }
    private void loadFirebasemadanhmuc() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Order").child("Comchaosup").child("MaDanhMuc").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Order").child("Comchaosup").child("Listid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    masanpham.add(itemSnapshot.getValue(String.class));
                    if (iduser == -1)
                    {
                        tt.add(0);
                    } else
                    if (iduser != -1) {
                        mDatabase.child("YeuThich").child(iduser + "").child("2").child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean ktratt = false;
                                if (snapshot.exists()) {
                                    for (DataSnapshot itemSnapshott : snapshot.getChildren()) {
                                        if (itemSnapshott.getValue(String.class).equals(itemSnapshot.getValue(String.class))) {
                                            ktratt = true;
                                            break;
                                        }
                                    }
                                }
                                if (false == ktratt) {
                                    tt.add(0);
                                } else {
                                    tt.add(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getActivity(), "Lỗi khi tải mã món ăn", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
        mDatabase.child("Order").child("Comchaosup").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Order").child("Comchaosup").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Order").child("Comchaosup").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    mota.add(itemSnapshot.getValue(String.class));
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
        mDatabase.child("Order").child("Comchaosup").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
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
}