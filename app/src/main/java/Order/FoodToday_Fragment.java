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
 * Use the {@link FoodToday_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodToday_Fragment extends Fragment {


    private View view;
    // Khai báo nút back
    private ImageView imgback;


    private List<String> tma;
    private List<Integer> anhh;
    private List<Integer> tt;
    private List<String> Gia;
    private List<String> masanpham,madanhmuc;
    private List<String> mota ;
    private ArrayList<Class_CategoryBanhCuon> list ;
    private Apdate_OrderCategory apdate ;
    private RecyclerView rcv;
    private DatabaseReference mDatabase;
    private int iduser;
    private  int k ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodToday_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodToday_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodToday_Fragment newInstance(String param1, String param2) {
        FoodToday_Fragment fragment = new FoodToday_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_today_, container, false);
        tma = new ArrayList<>();
        anhh = new ArrayList<>();
        Gia = new ArrayList<>();
        mota = new ArrayList<>();
        list = new ArrayList<>();
        masanpham = new ArrayList<>();
        madanhmuc = new ArrayList<>();
        tt = new ArrayList<>();

        rcv = view.findViewById(R.id.rcvcategoryfooday);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        ExitMonantoday();
        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",-1);
        loadFirebasemadanhmuc();
        return view;
    }

    private void loadFirebasemadanhmuc() {
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("Monanhangngay").child("Listdm").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    madanhmuc.add(String.valueOf(ds.getValue(Integer.class)));
                }
                loadMasanpham();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải mã danh mục món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadMasanpham() {
        for(int i = 0; i < madanhmuc.size() ;i++) {
            k =i;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Monanhangngay").child(madanhmuc.get(i)).child("Listid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        masanpham.add(String.valueOf(itemSnapshot.getValue(Integer.class)));
                        if (iduser == -1)
                        {
                            tt.add(0);
                        } else
                        if (iduser != -1) {
                            mDatabase.child("YeuThich").child(iduser + "").child(madanhmuc.get(k)).child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    Toast.makeText(getActivity(), "Lỗi khi tải mã món ăn", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadFirebaseData() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Monanhangngay").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Monanhangngay").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
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

        mDatabase.child("Monanhangngay").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Monanhangngay").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }

                if (tma.size() == anhh.size() && anhh.size() == Gia.size()) {
                    list = new ArrayList<>();
                    for (int i = 0; i < tma.size(); i++) {
                        list.add(new Class_CategoryBanhCuon(madanhmuc.get(i),masanpham.get(i),tma.get(i), anhh.get(i), Gia.get(i),mota.get(i),tt.get(i)));
                    }


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
    private void ExitMonantoday()
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
}