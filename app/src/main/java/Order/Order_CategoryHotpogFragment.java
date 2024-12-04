package Order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import fragment_ngv.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Order_CategoryHotpogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategoryHotpogFragment extends Fragment {

    private View view;
    // Khai báo quay lại
    private ImageView imgback;

    private String tenmonan[] = {"Lẩu cá măng chua", "Lẩu hải sản chua cay", "Lẩu măng cá Mú", "Lẩu thập cẩm"};
    private String giadonvi[] = {"482.000 vnd/lẩu", "439.000 vnd/lẩu", "428.000 vnd/lẩu", "510.000 vnd/lẩu"};
    private int imganh[] = {R.drawable.img_laumangcachua, R.drawable.img_lauhaisanchuacay, R.drawable.img_laumangcamu, R.drawable.img_lauthapcam};


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

    public Order_CategoryHotpogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_CategoryHotpogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_CategoryHotpogFragment newInstance(String param1, String param2) {
        Order_CategoryHotpogFragment fragment = new Order_CategoryHotpogFragment();
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
        view = inflater.inflate(R.layout.fragment_order__category_hotpog, container, false);

        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryhotpog);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        list = new ArrayList<>();
        for (int i = 0; i < imganh.length; i++) {
            list.add(new Class_CategoryBanhCuon(tenmonan[i], imganh[i], giadonvi[i]));
        }
        apdate = new Apdate_OrderCategory(list, getActivity());
        rcv.setAdapter(apdate);
        Quailai();

        return view;
    }

    private void Quailai() {
        imgback = view.findViewById(R.id.img_backhotpog);
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