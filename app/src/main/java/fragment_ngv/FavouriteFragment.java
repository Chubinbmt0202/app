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

public class FavouriteFragment extends Fragment {

    private View view;
    private RecyclerView rcvyeuthich;
    private Apdater_Faverite apdapter;
    private DatabaseReference mDatabase;

    private List<String> tma;
    private List<String> madm;
    private List<String> id;
    private List<Integer> anh;
    private List<String> Gia;
    private List<String> mota;
    private List<String> masp;
    private int iduser;
    private  ArrayList<Yeuthich> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        // Lấy ID người dùng từ SharedPreferences
        SharedPreferences sharett = requireActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id", -1);

        if (iduser != -1) {
            initViews();
            loadDataFromFirebase();
        } else {
            Toast.makeText(getActivity(), "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void initViews() {
        rcvyeuthich = view.findViewById(R.id.rcvyeuthich);
        rcvyeuthich.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        tma = new ArrayList<>();
        id = new ArrayList<>();
        anh = new ArrayList<>();
        Gia = new ArrayList<>();
        mota = new ArrayList<>();
        masp = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void loadDataFromFirebase() {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String chuoi = itemSnapshot.getKey();
                        if (chuoi != null) {
                            loadFirebaseDetails(chuoi);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Không có dữ liệu yêu thích!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải dữ liệu Firebase!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseDetails(String chuoi) {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).child(chuoi).child("Listidyt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    id.add(itemSnapshot.getValue(String.class));
                }
                loadOtherDetails(chuoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải ID!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadOtherDetails(String chuoi) {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).child(chuoi).child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    masp.add(itemSnapshot.getValue(String.class));
                }
                loadAdditionalData(chuoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải mã sản phẩm!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAdditionalData(String chuoi) {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).child(chuoi).child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    tma.add(itemSnapshot.getValue(String.class));
                }
                loadImageData(chuoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImageData(String chuoi) {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).child(chuoi).child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String imageName = itemSnapshot.getValue(String.class);
                    int resourceId = getActivity().getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
                    if (resourceId != 0) {
                        anh.add(resourceId);
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy ảnh: " + imageName, Toast.LENGTH_SHORT).show();
                    }
                }
                loadDescriptionData(chuoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải ảnh!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDescriptionData(String chuoi) {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).child(chuoi).child("Listmota").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    mota.add(itemSnapshot.getValue(String.class));
                }
                loadPriceData(chuoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải mô tả!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPriceData(String chuoi) {
        mDatabase.child("YeuThich").child(String.valueOf(iduser)).child(chuoi).child("ListGia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Gia.add(itemSnapshot.getValue(String.class));
                }
                updateRecyclerView(chuoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải giá!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView(String chuoi) {
        list = new ArrayList<>();
        if (isAdded() && tma.size() == anh.size() && anh.size() == Gia.size()) {
            for (int i = 0; i < tma.size(); i++) {
                list.add(new Yeuthich(chuoi,id.get(i), masp.get(i), tma.get(i), anh.get(i), Gia.get(i), mota.get(i), 1));
            }
            apdapter = new Apdater_Faverite(requireActivity(), list);
            rcvyeuthich.setAdapter(apdapter);
        } else {
            Toast.makeText(getActivity(), "Dữ liệu không đồng bộ!", Toast.LENGTH_SHORT).show();
        }
    }
}