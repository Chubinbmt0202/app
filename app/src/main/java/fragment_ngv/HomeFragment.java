package fragment_ngv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apprestaurant.R;

import java.util.ArrayList;
import java.util.List;

import HOME.ImageQuangCao;
import HOME.PhotoApdater;
import HOME.Searach_Fragment;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // Khai bao cho nut quang cao
    private CircleIndicator circleindicator;

    // Khai bao cho nut tim kiem san pham

    private TextView edtseach;
   // khai bao bien cho quang cao dau tien
    private ViewPager viewpager;
    private PhotoApdater madapter;
   // Khai bao bien cho combo thuc don
    private ViewPager viewPagercombo;
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        edtseach = view.findViewById(R.id.edtsearch1);

        edtseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Để chuyển từ HomeFragment sang Search_Fragment
                Fragment newFragment = new Searach_Fragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        setimgquangcao();
        Quangcaocombo();
        return view;

    }

    // khai bao cho anh quang cao
    private void setimgquangcao()
    {
        viewpager = view.findViewById(R.id.viewpage);
        circleindicator= view.findViewById(R.id.circleindicator);
        madapter = new PhotoApdater(getContext(),getDanhsach());
        viewpager.setAdapter(madapter);
        circleindicator.setViewPager(viewpager);

    }

    private List<ImageQuangCao> getDanhsach() {
        List<ImageQuangCao> list = new ArrayList<>();
        list.add(new ImageQuangCao(R.drawable.quangcaoga));
        list.add(new ImageQuangCao(R.drawable.quangcao2));
        list.add(new ImageQuangCao(R.drawable.quangcao3));
        return list;
    }

    private void Quangcaocombo()
    {
        viewPagercombo = view.findViewById(R.id.viewpagethucdon);
        madapter = new PhotoApdater(getContext(),getDanhSachcombo());
        viewPagercombo.setAdapter(madapter);
    }

    private List<ImageQuangCao> getDanhSachcombo() {
        List<ImageQuangCao> listcombo = new ArrayList<>();
        listcombo.add(new ImageQuangCao(R.drawable.quangcao2));
        listcombo.add(new ImageQuangCao(R.drawable.combo_heathy));
        listcombo.add(new ImageQuangCao(R.drawable.combo_lau));
        return listcombo;
    }
}