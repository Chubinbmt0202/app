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
 * Use the {@link FoodToday_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodToday_Fragment extends Fragment {


    private View view;
    // Khai báo nút back
    private ImageView imgback;

    private  String tenmonan[] = {"Cá kho tộ",
            "Cá lóc kho tộ",
            "Cá mú kho tộ",
            "Gỏi cuốn thịt xá xíu",
            "Gỏi cuốn thịt xá xíu",
            "Gỏi cuốn tôm trung muoi",
            "Gỏi cuốn tôm trứng muối",
            "Gỏi cuốn tôm thịt heo"};
    private String giadonvi[] = {"225.000 vnd/1 tộ","170.000 vnd/1 đĩa","320.000 vnd/1 phần",
            "85.000 vnd/3 cuốn",
            "135.000 vnd/5 cuốn",
            "170.000vnd/5 cuốn",
            "117.000 vnd/3 cuốn",
            "139.000 vnd/5 cuốn"};
    private int imganh[] = {R.drawable.haisanca_cakhoto,
            R.drawable.img_calockhoto,
            R.drawable.img_camukhoto,
            R.drawable.img_goicuonthitxaxiu,
            R.drawable.img_goicuonthitxaxiu,
            R.drawable.img_goicuontomthitheo,
            R.drawable.img_goicuontomtrungmuoi,
            R.drawable.img_goicuontomthitheo};

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
        setMonAn();
        ExitMonantoday();
        return view;
    }

    private void setMonAn()
    {
        rcv = view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        list = new ArrayList<>();
        for ( int i = 0 ; i < imganh.length ; i++)
        {
            list.add(new Class_CategoryBanhCuon(tenmonan[i],imganh[i],giadonvi[i]));
        }
        apdate = new Apdate_OrderCategory(list,getActivity());
        rcv.setAdapter(apdate);

    }
    private void ExitMonantoday()
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