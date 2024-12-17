package Accout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link canceled_orderdetail_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class canceled_orderdetail_Fragment extends Fragment {

    private View view;
//    private int ttrang[] = {3,3, 3, 3, 3};
//    private String id[] = {"0005", "0004", "0003", "0002","001"};
//    private String ngaydat[] = {"30/10/2024", "22/10/2024", "1/10/2024", "8/8/2024","1/8/2024"};

    private ArrayList<String> id;
    private ArrayList<Integer> tt;
    private ArrayList<String> date;
    private DatabaseReference mDatabase;

    private ImageView imgbackdetail;
    private ArrayList<Class_sumdeltaihistory> list;
    private ArrayApdat_SumDetailHistory apdate;
    private RecyclerView rcv;
    private int iduser;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public canceled_orderdetail_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment canceled_orderdetail_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static canceled_orderdetail_Fragment newInstance(String param1, String param2) {
        canceled_orderdetail_Fragment fragment = new canceled_orderdetail_Fragment();
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

        view = inflater.inflate(R.layout.fragment_canceled_orderdetail_, container, false);

        rcv = view.findViewById(R.id.rcv_canceled);

        id = new ArrayList<>();
        tt  = new ArrayList<>();
        date  = new ArrayList<>();

        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",0);

        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        loadFirebaseid();

        return view;
    }

    private void loadFirebaseid() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Load dish names
        mDatabase.child("DonHang").child(iduser+"").child("3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren())
                {
                    id.add(data.getKey());
                }
                loadNgay(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadNgay(List<String> id) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        for(int i = 0 ; i< id.size() ;i++) {
            Toast.makeText(getActivity(), "Lỗi " + iduser, Toast.LENGTH_SHORT).show();
            mDatabase.child("DonHang").child(iduser + "").child("3").child(id.get(i)).child("Date").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    date.add(snapshot.getValue(String.class));
                    if (id.size() == date.size()) {
                        for (int i = (id.size() - 1); i >= 0; i--) {
                            list.add(new Class_sumdeltaihistory(id.get(i), 3, date.get(i)));
                        }
                        apdate = new ArrayApdat_SumDetailHistory(list, getActivity());
                        rcv.setAdapter(apdate);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Lỗi khi tải tên món ăn", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}