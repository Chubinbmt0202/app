package Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apprestaurant.R;

import BOOK_ACTIVITY.BookTable_Fragment;
import fragment_ngv.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailCategory_PhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCategory_PhoFragment extends Fragment {

    private ImageView imgmonan , imgyeuthich;
    private TextView tvtenmonan , tvgia;
    private LinearLayout line;
    private View view;
    boolean isFavorite = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailCategory_PhoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailCategory_PhoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailCategory_PhoFragment newInstance(String param1, String param2) {
        DetailCategory_PhoFragment fragment = new DetailCategory_PhoFragment();
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
        view = inflater.inflate(R.layout.fragment_detail_category__pho, container, false);
        line = view.findViewById(R.id.lnout);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new OrderFragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            }
        });
        imgyeuthich = view.findViewById(R.id.foodyeuthich);
        imgmonan = view.findViewById(R.id.foodImage);
        tvtenmonan = view.findViewById(R.id.foodName);
        tvgia = view.findViewById(R.id.foodPrice);
        Bundle bundle = getArguments();
        if(bundle != null) {
            imgmonan.setImageResource(bundle.getInt("img"));
            tvgia.setText(bundle.getString("gia"));
            tvtenmonan.setText(bundle.getString("ten"));
        }

        imgyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thay đổi trạng thái và cập nhật hình ảnh dựa trên trạng thái
                if (isFavorite) {
                    imgyeuthich.setImageResource(R.drawable.btn_2); // Đặt về biểu tượng "yêu thích"
                } else {
                    imgyeuthich.setImageResource(R.drawable.img_yeuthich); // Đặt về biểu tượng "không yêu thích"
                }
                // Thay đổi giá trị của biến boolean
                isFavorite = !isFavorite;
            }
        });
        return view;
    }
}