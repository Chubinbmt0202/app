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
 * Use the {@link Order_CategoryPhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategoryPhoFragment extends Fragment {

    private View view;
    private DatabaseReference mDatabase;

//    private  String tenmonan[] = {"Phở bắp","Phở bắp gâu","Phở tái nạm","Phở tái bắp","Hũ tiếu bò khô","Hũ tiếu khô"};
//    private String giadonvi[] = {"76.000 vnd/tô","99.000 vnd/tô","99.000 vnd/tô","99.000 vnd/tô","76.000 vnd/tô","82.000 vnd/tô"};
//    private String imganh[] = {"R.drawable.img_phobap", "R.drawable.img_phobapgau", "R.drawable.img_photainam", "R.drawable.img_photaibap","R.drawable.menu_category_hutieubokho","R.drawable.menu_category_hutieukho"};

    private ArrayList<Class_CategoryBanhCuon> list ;
    private Apdate_OrderCategory apdate ;
    private RecyclerView rcv;


    // Khai báo nút quay lại
    private ImageView imgback;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Order_CategoryPhoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_CategoryPhoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_CategoryPhoFragment newInstance(String param1, String param2) {
        Order_CategoryPhoFragment fragment = new Order_CategoryPhoFragment();
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
        view= inflater.inflate(R.layout.fragment_order__category_pho, container, false);
        // ...

        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        loadFirebasemadanhmuc();
        Quailai();
        return view;
    }

    private void Quailai()
    {
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
        String madanhmuc ;
        // Load dish names
        mDatabase.child("Order").child("Pho").child("MaDanhMuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
              {
                  String madanhmuc = snapshot.getValue(String.class); // Lấy giá trị từ Firebase
                  if (madanhmuc != null) {
                      loadMasanpham(madanhmuc); // Chỉ gọi tiếp nếu `madanhmuc` không null
                  } else {
                      Toast.makeText(getActivity(), "Không tìm thấy danh mục", Toast.LENGTH_SHORT).show();
                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadMasanpham(String madanhmuc) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        List<String> masanpham = new ArrayList<>();
        // Load dish names
        mDatabase.child("Order").child("Pho").child("Listid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    masanpham.add(itemSnapshot.getValue(String.class));
                }
                loadFirebaseData(madanhmuc,masanpham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải mã sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseData(String madanhmuc, List<String> masanpham) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Order").child("Pho").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> tma = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tma.add(itemSnapshot.getValue(String.class));
                }

                loadImageData(madanhmuc,masanpham,tma);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImageData(String madanhmuc, List<String> masanpham,List<String> tma) {
        mDatabase.child("Order").child("Pho").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Integer> anhh = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageName = itemSnapshot.getValue(String.class);
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());

                    if (resourceId != 0) {
                        anhh.add(resourceId);
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy ảnh: " + imageName, Toast.LENGTH_SHORT).show();
                    }
                }

                loadPriceData(madanhmuc,masanpham,tma, anhh); // Chuyển tiếp để tải giá
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải danh sách ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPriceData(String madanhmuc, List<String> masanpham,List<String> tma, List<Integer> anhh) {
        mDatabase.child("Order").child("Pho").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> Gia = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }
                loadPriceTinhtrang(madanhmuc, masanpham, tma,  anhh, Gia) ;


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải giá", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPriceTinhtrang(String madanhmuc, List<String> masanpham,List<String> tma, List<Integer> anhh,List<String> gia) {
        mDatabase.child("Order").child("Pho").child("Listt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Integer> tt = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tt.add(itemSnapshot.getValue(Integer.class));
                }
                loadMota(madanhmuc, masanpham, tma,  anhh, gia,tt) ;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải giá", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMota(String madanhmuc, List<String> masanpham,List<String> tma, List<Integer> anhh,List<String> Gia, List<Integer> tt) {
        mDatabase.child("Order").child("Pho").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> mota = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    mota.add(itemSnapshot.getValue(String.class));
                }
                if (tma.size() == anhh.size() && anhh.size() == Gia.size()) {
                    list = new ArrayList<>();
                    for (int i = 0; i < masanpham.size(); i++) {
                        list.add(new Class_CategoryBanhCuon(madanhmuc,masanpham.get(i),tma.get(i), anhh.get(i), Gia.get(i),mota.get(i),tt.get(i)));
                    }
                    apdate = new Apdate_OrderCategory(list, getActivity());
                    rcv.setAdapter(apdate);
                } else {
                    Toast.makeText(getActivity(), "Dữ liệu không đồng bộ!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải danh sách ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

}