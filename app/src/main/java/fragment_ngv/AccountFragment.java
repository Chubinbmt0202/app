package fragment_ngv;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.Accout_detail_userFragment;
import com.example.apprestaurant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Accout.Sum_detailhistory_Activity;
import BOOK_ACTIVITY.BookTable_Fragment;
import Category.DetailCategory_Activity;
import User_Restaurant.DetailUser_Activity;
import User_Restaurant.Detail_RestaurantActivity;
import User_Restaurant.Layout_feedback;
import User_Restaurant.PhanHoi_Restaurant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Khai báo Carview
    private CardView cv_user , cv_restaurant , cv_detailcategory , cv_bookhistory;


    // Khai báo view
    private View view;

    // Khai báo tên khách hàng
    private TextView tvuser;

    // Khai bao line phản hồi
    private LinearLayout linephanhoi;
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        SharedPreferences sp = getContext().getSharedPreferences("Myaccount", Context.MODE_PRIVATE);
        if(sp.getInt("check",0)==1)
        {
            tvuser.setText(sp.getString("name","Nhom 12"));
        }
        CarviewUser();
        CardviewRestaurant();
        CardViewDetailCategory();
        CardviewHistorybook();
        tvuser = view.findViewById(R.id.tv_nameuser);
        Intent it = getActivity().getIntent();
        if(it.getIntExtra("kt",0)==1)
        {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name",it.getStringExtra("nameuser"));
            editor.putInt("check",1);
            editor.commit();
            Toast.makeText(getActivity(),  it.getStringExtra("nameuser"), Toast.LENGTH_SHORT).show();
        }
        Phanhoi();
        return view;
    }
    private void CarviewUser()
    {
        cv_user = view.findViewById(R.id.cv_user);
        cv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent it = new Intent(getActivity(), DetailUser_Activity.class);
//                startActivity(it);
                Fragment newFragment = new Accout_detail_userFragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentcontain,newFragment);
                fragmentTransaction.commit();
            }
        });

    }
    private void CardviewRestaurant()
    {
        cv_restaurant = view.findViewById(R.id.cardrestaurant);
        cv_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Detail_RestaurantActivity.class);
                startActivity(it);
            }
        });

    }

    private void CardViewDetailCategory()
    {
        cv_detailcategory = view.findViewById(R.id.carddanhmuc);

        cv_detailcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), DetailCategory_Activity.class);
                startActivity(it);
            }
        });
    }

    private void CardviewHistorybook()
    {
        cv_bookhistory = view.findViewById(R.id.cardviewbook);
        cv_bookhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Sum_detailhistory_Activity.class);
                startActivity(it);
            }
        });
    }

    private void Phanhoi()
    {
        linephanhoi = view.findViewById(R.id.line_acphanhoi);
        linephanhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Layout_feedback.class);
                startActivity(it);
            }
        });
    }


}