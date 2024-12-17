package fragment_ngv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import HOME.Addapter_Home;
import HOME.Apdate_CategoryHome;
import HOME.ImageQuangCao;
import HOME.Nagigationkey;
import HOME.PhotoApdater;
import HOME.Searach_Fragment;
import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // Khai bao cho nut quang cao
    private CircleIndicator circleindicator;

    // Khai bao tat ca

    private TextView tvtatca;
    // Khai bao recycleview
    private DatabaseReference mDatabase;

    private RecyclerView rcvhome, rcvhome1;
    private Apdate_CategoryHome gh , gh1;
    // Khai bao cho nut tim kiem san pham

    private TextView edtseach;
    // khai bao bien cho quang cao dau tien
    private ViewPager viewpager;
    private PhotoApdater madapter;
    // Khai bao bien cho combo thuc don
    private ViewPager viewPagercombo;
    private View view;

    private List<String> tma;
    private List<Integer> anhh;
    private List<String> Gia;
    private List<String> mota;
    private List<Integer> tt;
    private List<String> tma1;
    private List<Integer> anhh1;
    private List<String> Gia1;
    private List<String> mota1;
    private List<Integer> tt1;
    private ArrayList<Class_Home> list;
    private ArrayList<Class_Home> list1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        edtseach = view.findViewById(R.id.edtsearch1);

        // Kiêm tra co click dang nhap khong
        if(getActivity().getIntent()!= null)
        {
            Intent it = getActivity().getIntent();
            int id = it.getIntExtra("idus",0);
            SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = sharett.edit();
            edt.putInt("id",id);
            edt.commit();
        }


        tma = new ArrayList<>();
        anhh = new ArrayList<>();
        Gia = new ArrayList<>();
        mota = new ArrayList<>();
        list = new ArrayList<>();
        tt = new ArrayList<>();

        tma1 = new ArrayList<>();
        anhh1 = new ArrayList<>();
        Gia1 = new ArrayList<>();
        mota1 = new ArrayList<>();
        list1 = new ArrayList<>();
        tt1 = new ArrayList<>();

        edtseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Để chuyển từ HomeFragment sang Search_Fragment
                Fragment newFragment = new Searach_Fragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        tvtatca = view.findViewById(R.id.tvtatca);
        tvtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Để chuyển từ HomeFragment sang OrderFragment
                ((Nagigationkey) getActivity()).navigateToFragment(R.id.order); // Chuyển đến BookFragment
                Fragment newFragment = new OrderFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.commit();
            }
        });

        setimgquangcao();
        Themmonan();
        Themmonanchay();
        Quangcaocombo();
        return view;

    }

    // khai bao cho anh quang cao
    private void setimgquangcao() {
        viewpager = view.findViewById(R.id.viewpage);
        circleindicator = view.findViewById(R.id.circleindicator);
        madapter = new PhotoApdater(getContext(), getDanhsach());
        viewpager.setAdapter(madapter);
        circleindicator.setViewPager(viewpager);

    }

    private List<ImageQuangCao> getDanhsach() {
        List<ImageQuangCao> list = new ArrayList<>();
        list.add(new ImageQuangCao(R.drawable.quangcaoga));
        list.add(new ImageQuangCao(R.drawable.quangcao2));
        list.add(new ImageQuangCao(R.drawable.quangcao3));
        return list;
    }

    private void Quangcaocombo() {
        viewPagercombo = view.findViewById(R.id.viewpagethucdon);
        madapter = new PhotoApdater(getContext(), getDanhSachcombo());
        viewPagercombo.setAdapter(madapter);
    }

    private List<ImageQuangCao> getDanhSachcombo() {
        List<ImageQuangCao> listcombo = new ArrayList<>();
        listcombo.add(new ImageQuangCao(R.drawable.quangcao2));
        listcombo.add(new ImageQuangCao(R.drawable.combo_heathy));
        listcombo.add(new ImageQuangCao(R.drawable.combo_lau));
        return listcombo;
    }

    private void Themmonan() {
        rcvhome = view.findViewById(R.id.gvhome);
        rcvhome.setLayoutManager(new GridLayoutManager(getContext(),2));
        getTenMonAn();
    }

    private void Themmonanchay() {
        rcvhome1 = view.findViewById(R.id.rcvhome1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvhome1.setLayoutManager(layoutManager);
        getTenMonAn1();
    }


    private void getTenMonAn() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Home").child("Home2").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Home").child("Home2").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Home").child("Home2").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Home").child("Home2").child("Listt").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("Home").child("Home2").child("ListGia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }

                if (tma.size() == anhh.size() && anhh.size() == Gia.size()) {
                    for (int i = 0; i < tma.size(); i++) {
                        list.add(new Class_Home(1,tma.get(i), anhh.get(i), Gia.get(i),mota.get(i),tt.get(i)));
                    }
                    gh = new Apdate_CategoryHome(list, getActivity());
                    rcvhome.setAdapter(gh);
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

    private void getTenMonAn1() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Home").child("Home1").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tma1.add(itemSnapshot.getValue(String.class));
                }
                loadImageData1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadImageData1() {
        mDatabase.child("Home").child("Home1").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageName = itemSnapshot.getValue(String.class);
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
                    if (resourceId == 0) {
                        resourceId = R.drawable.haisanca_cakhoto; // Hình mặc định
                    }
                    anhh1.add(resourceId);
                }
                loadFirebaseMota1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải danh sách ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadFirebaseMota1() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Home").child("Home1").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    mota1.add(itemSnapshot.getValue(String.class));
                }
                loadFirebaseMtt1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseMtt1() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("Home").child("Home1").child("Listt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tt1.add(itemSnapshot.getValue(Integer.class));
                }
                loadPriceData1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPriceData1() {
        mDatabase.child("Home").child("Home1").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia1.add(itemSnapshot.getValue(String.class));
                }

                if (tma1.size() == anhh1.size() && anhh1.size() == Gia1.size()) {
                    for (int i = 0; i < tma1.size(); i++) {
                        list1.add(new Class_Home(2,tma1.get(i), anhh1.get(i), Gia1.get(i),mota1.get(i),tt1.get(i)));
                    }
                    // Update RecyclerView adapter
                    gh1 = new Apdate_CategoryHome(list1,getActivity());
                    rcvhome1.setAdapter(gh1);
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