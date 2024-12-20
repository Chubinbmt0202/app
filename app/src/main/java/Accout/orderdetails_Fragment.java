package Accout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


import com.example.apprestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderdetails_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderdetails_Fragment extends Fragment {

    // Khai báo thoát chi tiết hóa đơn
    private ImageView imgbackdetail;
    // Khai bao view
    private View view;

    // Khai báo fragment với tablayout
    private FrameLayout fgdetail;
    private TabLayout tbldetail;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int iduser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public orderdetails_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderdetails_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static orderdetails_Fragment newInstance(String param1, String param2) {
        orderdetails_Fragment fragment = new orderdetails_Fragment();
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
        view = inflater.inflate(R.layout.fragment_orderdetails_, container, false);

        fgdetail = view.findViewById(R.id.tabContentFrame);
        tbldetail = view.findViewById(R.id.tabLayout);

        imgbackdetail = view.findViewById(R.id.imgbackdetail);

        SharedPreferences sharett = getContext().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",-1);
        imgbackdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });


        if(getArguments()!= null)
        {
            Bundle bundle = getArguments();
            int value = bundle.getInt("key");
            if(value == 1)
            {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tabContentFrame, new canceled_orderdetail_Fragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                tbldetail.getTabAt(1).select();
            }
        }
        else {

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tabContentFrame, new placed_orderdeatail_Fragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }

        }

        if(iduser != -1) {
            tbldetail.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Fragment fg = null;
                    if (tab.getPosition() == 0) {
                        fg = new placed_orderdeatail_Fragment();
                    } else if (tab.getPosition() == 1) {
                        fg = new canceled_orderdetail_Fragment();
                    } else if (tab.getPosition() == 2) {
                        fg = new placedsuccessfully_orderdeatil_Fragment();
                    }

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tabContentFrame, fg)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


            fgdetail.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                }
            });
        }
        return view;
    }
}