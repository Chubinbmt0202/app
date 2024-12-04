package fragment_ngv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apprestaurant.Accout_detail_userFragment;
import com.example.apprestaurant.R;

import Accout.orderdetails_Fragment;
import Category.DetailCategory_Activity;
import Intro_register_login.LoginActivity;
import User_Restaurant.Detail_RestaurantActivity;
import User_Restaurant.Layout_feedback;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private CardView cvUser, cvRestaurant, cvDetailCategory, cvBookHistory;
    private View view;
    private TextView tvUser;
    private LinearLayout lineFeedback , linegtdm;
    private TextView tx_action;
    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        view = inflater.inflate(R.layout.fragment_account, container, false);

        // Khởi tạo TextView và các CardView
        tvUser = view.findViewById(R.id.tv_nameuser);
        initializeCardViews();

        SharedPreferences sp = getContext().getSharedPreferences("Myaccount", Context.MODE_PRIVATE);
        if (sp != null && sp.getInt("check", 0) == 1) {
            tvUser.setText(sp.getString("name", "Nhom 12"));
        }

        // Kiểm tra và nhận dữ liệu từ Bundle
        if (getArguments() != null) {
            String nameUser = getArguments().getString("nameuser");
            int kt = getArguments().getInt("kt");
            if (kt == 1) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", nameUser);
                editor.putInt("check", 1);
                editor.apply();  // Sử dụng apply() thay vì commit()
                tvUser.setText(nameUser); // Cập nhật TextView nếu cần thiết
            }
        }

        initializeFeedback();

        tx_action = view.findViewById(R.id.tx_action);

        tx_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), LoginActivity.class);
                startActivity(it);
            }
        });

        return view;
    }

    private void initializeCardViews() {
        cvUser = view.findViewById(R.id.cv_user);
        cvRestaurant = view.findViewById(R.id.cardrestaurant);
        cvDetailCategory = view.findViewById(R.id.carddanhmuc);
        cvBookHistory = view.findViewById(R.id.cardviewbook);

        linegtdm = view.findViewById(R.id.line_acdanhmuc);


        linegtdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DetailCategory_Activity.class));
            }
        });
        cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new Accout_detail_userFragment();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.commit();
            }
        });

        cvRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Detail_RestaurantActivity.class));
            }
        });

        cvDetailCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new orderdetails_Fragment();

                Bundle bundle = new Bundle();
                bundle.putInt("key", 1); // Add your data here
                newFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null); // Thêm vào back stack
                fragmentTransaction.commit();
            }
        });

        cvBookHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new orderdetails_Fragment();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null); // Thêm vào back stack
                fragmentTransaction.commit();
            }
        });
    }

    private void initializeFeedback() {
        lineFeedback = view.findViewById(R.id.line_acphanhoi);
        lineFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Layout_feedback.class));
            }
        });
    }

}
