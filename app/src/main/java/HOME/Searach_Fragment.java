package HOME;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fragment_ngv.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Searach_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searach_Fragment extends Fragment {
   // khai bao view
    private View view;

    private LinearLayout lineseachhistory , linengancach , lineketquaseach;
    private EditText edtsearchhitory;
    private ListView lvhistory;
    private Apdatee_SearchHistory apdapter;
    private ImageView imgbacksearch;
    private ArrayList<String> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Searach_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searach_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Searach_Fragment newInstance(String param1, String param2) {
        Searach_Fragment fragment = new Searach_Fragment();
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
        view = inflater.inflate(R.layout.fragment_searach_, container, false);
        lvhistory = view.findViewById(R.id.lvhistory);
        edtsearchhitory = view.findViewById(R.id.edtsearch1);
        lineseachhistory = view.findViewById(R.id.linehistory);
        linengancach = view.findViewById(R.id.lineseparate);
        lineketquaseach = view.findViewById(R.id.lineketquaseach);

        lineseachhistory.setVisibility(View.GONE);
        lineketquaseach.setVisibility(View.GONE);
        linengancach.setVisibility(View.GONE);

        SharedPreferences sp = getContext().getSharedPreferences("Mysearchhistory", Context.MODE_PRIVATE);
        Set<String> lichsutimkiem = sp.getStringSet("timkiem", null);
        if (lichsutimkiem != null)
        {
            list = new ArrayList<String>();
            list.addAll(lichsutimkiem);
            apdapter = new Apdatee_SearchHistory(getActivity(), R.layout.layout_historysearch,list);
            lvhistory.setAdapter(apdapter);
            lineseachhistory.setVisibility(View.VISIBLE);
        }

        edtsearchhitory.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Set<String> kq = new HashSet<>();
                SharedPreferences sp = getContext().getSharedPreferences("Mysearchhistory", Context.MODE_PRIVATE);
                if(sp.getStringSet("timkiem",null) != null)
                {
                   kq = sp.getStringSet("timkiem",null);
                }
                SharedPreferences.Editor editor = sp.edit();
                kq.add(edtsearchhitory.getText().toString());
                editor.putStringSet("timkiem",kq);
                editor.commit();

                list.clear();
                list.addAll(kq);
                apdapter.notifyDataSetChanged();

                return false;
            }
        });
        BackSearch();
        return view;
    }

    // Khai bao nut thoat
    private void BackSearch()
    {
        imgbacksearch = view.findViewById(R.id.imgbacksearch);
        imgbacksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Để chuyển từ Search_Fragment sang HomeFragment
                Fragment newFragment = new HomeFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}