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
 * Use the {@link Order_CategoryBanhCuonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategoryBanhCuonFragment extends Fragment {

    private View view;

    private  String tenmonan[] = {"Gỏi cuốn thịt xá xíu",
                                  "Gỏi cuốn bắp bò cuốn",
                                  "Gỏi cuốn cá hồi",
                                  "Gỏi cuốn cá ngừ",
                                   "Gỏi cuốn bò bía"};
    private String giadonvi[] = {"15.000 vnd/cuốn",
                                 "20.000 vnd/cuốn",
                                 "17.000vnd/cuốn",
                                 "22.000 vnd/cuốn",
                                 "13.000 vnd/cuốn"};
    private int imganh[] = {
            R.drawable.img_goicuonthitxaxiu,
            R.drawable.img_goicuonthitxaxiu,
            R.drawable.img_goicuoncahoi,
            R.drawable.img_goicuoncangu,
            R.drawable.img_bobia
    };

    private ArrayList<Class_CategoryBanhCuon> list ;
    private Apdate_OrderCategory apdate ;
    private RecyclerView rcv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Khai báo nút back
    private ImageView imgback;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Order_CategoryBanhCuonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_CategoryBanhCuonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Order_CategoryBanhCuonFragment newInstance(String param1, String param2) {
        Order_CategoryBanhCuonFragment fragment = new Order_CategoryBanhCuonFragment();
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
        view = inflater.inflate(R.layout.fragment_order__category_banh_cuon, container, false);
        ExitBanhCuon();
        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        list = new ArrayList<>();
        for ( int i = 0 ; i < imganh.length ; i++)
        {
            list.add(new Class_CategoryBanhCuon(tenmonan[i],imganh[i],giadonvi[i]));
        }
        apdate = new Apdate_OrderCategory(list,getActivity());
        rcv.setAdapter(apdate);
        return view;
    }
    private void ExitBanhCuon()
    {
        imgback = view.findViewById(R.id.img_backtoday);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Để chuyển từ Order_CategoryBanhCuonFragment  về OrderFragment
                Fragment newFragment = new OrderFragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}