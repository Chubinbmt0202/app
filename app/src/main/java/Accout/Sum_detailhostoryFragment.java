package Accout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import HOME.Nagigationkey;
import fragment_ngv.AccountFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sum_detailhostoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sum_detailhostoryFragment extends Fragment {

    private View view;
    private int ttrang[] = {1,4, 2, 2, 2};
    private String id[] = {"0005", "0004", "0003", "0002","001"};
    private String ngaydat[] = {"30/10/2024", "22/10/2024", "1/10/2024", "8/8/2024","1/8/2024"};

    private ImageView imgbackdetail;
    private ArrayList<Class_sumdeltaihistory> list;
    private ArrayApdat_SumDetailHistory apdate;
    private RecyclerView rcv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sum_detailhostoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sum_detailhostoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Sum_detailhostoryFragment newInstance(String param1, String param2) {
        Sum_detailhostoryFragment fragment = new Sum_detailhostoryFragment();
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
        view = inflater.inflate(R.layout.fragment_sum_detailhostory, container, false);
        imgbackdetail = view.findViewById(R.id.imgbackhuy);
        rcv = (RecyclerView) view.findViewById(R.id.rcv_huy);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {

            list.add(new Class_sumdeltaihistory(id[i], ttrang[i], ngaydat[i]));
        }
        apdate = new ArrayApdat_SumDetailHistory(list, getActivity());
        rcv.setAdapter(apdate);

        imgbackdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new AccountFragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}