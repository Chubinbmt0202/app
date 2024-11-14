package fragment_ngv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apprestaurant.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    private View view;
    Apdater_Faverite apdapter;
    private RecyclerView rcvyeuthich;
    private int[] anh ={R.drawable.img_goicuonthitxaxiu,
            R.drawable.img_goicuonthitxaxiu,
            R.drawable.img_goicuontomthitheo,
            R.drawable.haisanca_cakhoto,
            R.drawable.img_calockhoto,
            R.drawable.img_camukhoto,
            R.drawable.img_phobap,
            R.drawable.img_phobapgau,
            R.drawable.img_photainam
    };
     private String[] temmonan = {"Gỏi cuốn thịt xá xíu",
             "Gỏi cuốn thịt xá xíu",
             "Gỏi cuốn tôm trung muoi",
             "Cá kho tộ",
             "Cá lóc kho tộ",
             "Cá mú kho tộ","Phở bắp","Phở bắp gâu","Phở tái nạm"
     };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
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
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        ArrayList<Yeuthich>  list = new ArrayList<>();

        for ( int i = 0 ; i< anh.length ;i++)
        {
            list.add(new Yeuthich(anh[i],temmonan[i]));
        }
        apdapter = new Apdater_Faverite(getActivity(),list);
        rcvyeuthich = view.findViewById(R.id.rcvyeuthich);
        rcvyeuthich.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcvyeuthich.setAdapter(apdapter);
        return view;
    }
}