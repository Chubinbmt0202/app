package fragment_ngv;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprestaurant.R;

import HOME.Searach_Fragment;
import Order.FoodToday_Fragment;
import Order.Order_CategoryBanhCuonFragment;
import Order.Order_CategoryComChaoSupFragment;
import Order.Order_CategoryDrinkFragment;
import Order.Order_CategoryGaBoHeoFragment;
import Order.Order_CategoryHaiSanFragment;
import Order.Order_CategoryHotpogFragment;
import Order.Order_CategoryPhoFragment;
import Order.Order_CategorydessertFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    private View view;
    private TextView edtsearch1;
    private CardView cvmonsoi , cvbanhcuon  , cvhaisanca , cvgaboheo , cvcschao , cvtrangmieng , cvlau , cvdouong , cvtoday;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        view = inflater.inflate(R.layout.fragment_order, container, false);

        edtsearch1 = view.findViewById(R.id.edtsearch1);
        edtsearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Để chuyển từ OrderFragment sang Search_Fragment
                Fragment newFragment = new Searach_Fragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Danhmucmonsoi();
        Danhmuchaisanca();
        Danhmucbanhcuon();
        Danhmuctoday();
        Danhmucgaboheo();
        Danhmucccsup();
        Danhmucctrangmieng();
        Danhmucclau();
        Danhmucnuocuong();
        return view;
    }

    private void Danhmucmonsoi()
    {
        cvmonsoi = view.findViewById(R.id.cview_monsoi);
        cvmonsoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategoryPhoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmuchaisanca()
    {
        cvhaisanca = view.findViewById(R.id.cview_haisanca);
        cvhaisanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategoryHaiSanFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmucbanhcuon()
    {
        cvbanhcuon = view.findViewById(R.id.cview_banhcuon);
        cvbanhcuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategoryBanhCuonFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmuctoday()
    {
        cvtoday = view.findViewById(R.id.cview_monantoday);
        cvtoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new FoodToday_Fragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmucgaboheo()
    {
        cvgaboheo = view.findViewById(R.id.cview_gaboheo);
        cvgaboheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategoryGaBoHeoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmucccsup()
    {
        cvcschao = view.findViewById(R.id.cview_cschao);
        cvcschao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategoryComChaoSupFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmucctrangmieng()
    {
        cvtrangmieng = view.findViewById(R.id.cview_trangmieng);
        cvtrangmieng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategorydessertFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Danhmucclau() {
        cvlau = view.findViewById(R.id.cview_lau);
        cvlau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Order_CategoryHotpogFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
        private void Danhmucnuocuong()
        {
            cvdouong = view.findViewById(R.id.cview_douong);
            cvdouong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = new Order_CategoryDrinkFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
    }


}