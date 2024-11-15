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
 * Use the {@link Order_CategoryPhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Order_CategoryPhoFragment extends Fragment {

    private View view;

    private  String tenmonan[] = {"Phở bắp","Phở bắp gâu","Phở tái nạm","Phở tái bắp","Hũ tiếu bò khô","Hũ tiếu khô"};
    private String giadonvi[] = {"76.000 vnd/1 tô","99.000 vnd/1 tô","99.000 vnd/1 tô","99.000 vnd/1 tô","76.000 vnd/1 tô","82.000 vnd/1 tô"};
    private int imganh[] = {R.drawable.img_phobap, R.drawable.img_phobapgau, R.drawable.img_photainam, R.drawable.img_photaibap,R.drawable.menu_category_hutieubokho,R.drawable.menu_category_hutieukho};

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

        rcv = (RecyclerView) view.findViewById(R.id.rcv_categoryccsup);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        list = new ArrayList<>();
        for ( int i = 0 ; i < imganh.length ; i++)
        {
            list.add(new Class_CategoryBanhCuon(tenmonan[i],imganh[i],giadonvi[i]));
        }
        apdate = new Apdate_OrderCategory(list,getActivity());
        rcv.setAdapter(apdate);
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
}