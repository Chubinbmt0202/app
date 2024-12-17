package fragment_ngv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apprestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    private View view;
    Apdater_Faverite apdapter;
    private RecyclerView rcvyeuthich;
//    private int[] anh ={R.drawable.img_goicuonthitxaxiu,
//            R.drawable.img_goicuontomthitheo,
//            R.drawable.haisanca_cakhoto
//    };
//     private String[] temmonan = {"Gỏi cuốn thịt xá xíu",
//             "Gỏi cuốn bắp bò cuốn",
//             "Cá lóc kho tộ",
//     };

    private List<String> tma;
    private List<String> id;
    private List<Integer> anh;
    private int iduser;
    private List<Integer> tt;
    private List<String> Gia;
    private List<String> mota;
    private List<String> masp;
    private DatabaseReference mDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
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
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",0);


        tma = new ArrayList<>();
        anh = new ArrayList<>();
        Gia = new ArrayList<>();
        mota = new ArrayList<>();
        id = new ArrayList<>();
        tt = new ArrayList<>();
        masp = new ArrayList<>();

        rcvyeuthich = view.findViewById(R.id.rcvyeuthich);
        rcvyeuthich.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        loadFirebaseid();
        return view;
    }

    private void loadFirebaseid() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("YeuThich").child(iduser+"").child("Listidyt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    id.add(itemSnapshot.getValue(String.class));
                }
                loadFirebasemasp();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebasemasp() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("YeuThich").child(iduser+"").child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    masp.add(itemSnapshot.getValue(String.class));
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
        mDatabase.child("YeuThich").child(iduser+"").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("YeuThich").child(iduser+"").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageName = itemSnapshot.getValue(String.class);
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
                    if (resourceId != 0) {
                        anh.add(resourceId);
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
        mDatabase.child("YeuThich").child(iduser+"").child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
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
        mDatabase.child("YeuThich").child(iduser+"").child("ListGia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }
                ArrayList<Yeuthich>  list = new ArrayList<>();
                if (tma.size() == anh.size() && anh.size() == Gia.size()) {
                    for (int i = 0; i < tma.size(); i++) {
                        list.add(new Yeuthich(id.get(i),masp.get(i),tma.get(i), anh.get(i), Gia.get(i),mota.get(i),1));
                    }

                    // Update RecyclerView adapter
                    apdapter = new Apdater_Faverite( getActivity(),list);
                    rcvyeuthich.setAdapter(apdapter);
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