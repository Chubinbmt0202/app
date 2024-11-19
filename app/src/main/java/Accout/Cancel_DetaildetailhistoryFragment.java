package Accout;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.apprestaurant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fragment_ngv.AccountFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cancel_DetaildetailhistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cancel_DetaildetailhistoryFragment extends Fragment  {

    private View view;
    private int ttrang[] = {3};
    private String id[] = {"0004", "0003", "0002", "0001"};
    private String ngaydat[] = {"30/10/2024", "22/10/2024", "1/10/2024", "8/8/2024"};

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

    public Cancel_DetaildetailhistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cancel_DetaildetailhistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cancel_DetaildetailhistoryFragment newInstance(String param1, String param2) {
        Cancel_DetaildetailhistoryFragment fragment = new Cancel_DetaildetailhistoryFragment();
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
        view = inflater.inflate(R.layout.fragment_cancel__detaildetailhistory, container, false);
        imgbackdetail = view.findViewById(R.id.imgbackhuy);
        rcv = (RecyclerView) view.findViewById(R.id.rcv_huy);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        String ktid = "";
        // Tạo danh sách từ dữ liệu đầu vào
        for (int i = 0; i < id.length; i++) {
            list.add(new Class_sumdeltaihistory(id[i], ttrang[0], ngaydat[i]));
        }
        Bundle bl = getArguments();
        if (bl != null) {
            ktid = bl.getString("iddonhang", "");
            Toast.makeText(getContext(), ktid + " kokoko", Toast.LENGTH_SHORT).show();

            if (!ktid.isEmpty()) { // Kiểm tra chuỗi không rỗng
                for (int i = 0; i < list.size(); i++) { // Lặp qua danh sách
                    Class_sumdeltaihistory cl = list.get(i);
                    if (cl.getIdhoadon().equals(ktid)) { // So sánh chuỗi đúng cách
                        list.remove(i); // Xóa phần tử khi tìm thấy
                        break; // Dừng sau khi xóa
                    }
                }
            }
        }


        apdate = new ArrayApdat_SumDetailHistory(list, getActivity());
        rcv.setAdapter(apdate);

        imgbackdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new AccountFragment();
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