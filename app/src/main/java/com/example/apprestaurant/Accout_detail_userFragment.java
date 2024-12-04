package com.example.apprestaurant;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import BOOK_ACTIVITY.PayBook_Fragment;
import HOME.Nagigationkey;
import User_Restaurant.DetailUser_Activity;
import fragment_ngv.AccountFragment;
import fragment_ngv.BookFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Accout_detail_userFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Accout_detail_userFragment extends Fragment {

    private EditText edthoten , edtemail , edtphone ;
    private RadioGroup rdggtinh;
    private ImageView imgback , imguser;
    private TextView tv_edit;
    private Button btn_update;
    private RadioButton rdnam , rdnu;
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Accout_detail_userFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Accout_detail_userFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Accout_detail_userFragment newInstance(String param1, String param2) {
        Accout_detail_userFragment fragment = new Accout_detail_userFragment();
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
        BottomNavigationView bt = getActivity().findViewById(R.id.ngv_bottomNavigation);
        bt.setVisibility(View.GONE);
        view = inflater.inflate(R.layout.fragment_accout_detail_user, container, false);
        edthoten = (EditText) view.findViewById(R.id.edtnameuser);

        edtemail = (EditText) view.findViewById(R.id.edt_email);

        edtphone = (EditText) view.findViewById(R.id.edtphone);

        imgback = (ImageView) view.findViewById(R.id.img_back);

        rdnam = view.findViewById(R.id.rd_nam);

        rdggtinh = view.findViewById(R.id.rdg_sex);

        btn_update = view.findViewById(R.id.btn_capnhat);

        rdnu = view.findViewById(R.id.rdnu);



        SharedPreferences share = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        if(share.getInt("kt",0) == 1)
        {
            edthoten.setText(share.getString("name",""));
            edtphone.setText(share.getString("phone",""));
            edtemail.setText(share.getString("email",""));
            if(share.getInt("ck",0) == 1)
            {
                rdnam.setChecked(true);
            }
            else
            {
                rdnu.setChecked(true);
            }
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edthoten.getText().toString().trim().isEmpty() ||
                        edtemail.getText().toString().trim().isEmpty() ||
                        edtphone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ tên khách hàng , email , sdt.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    SharedPreferences share = getActivity().getSharedPreferences("user", MODE_PRIVATE);

                    SharedPreferences.Editor editor = share.edit();
                    editor.clear();
                    editor.putString("name", edthoten.getText().toString());
                    editor.putString("email", edtemail.getText().toString());
                    editor.putString("phone", edtphone.getText().toString());
                    editor.putInt("ck", rdnam.isChecked() ? 1 : 0);
                    editor.putInt("kt", 1);
                    editor.commit();
                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                }
            }
        });
        Exit();
        return view;
    }
    private void Exit()
    {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = " "  , phone = " ";
                SharedPreferences share = getActivity().getSharedPreferences("user",MODE_PRIVATE);
                if(share.getInt("kt",0) == 1)
                {
                    s = share.getString("name",null);
                }
                Bundle bl = getArguments();
                if(bl!= null)
                {
                    int k = bl.getInt("bk",0);
                    if(k != 0)
                    {
                        ((Nagigationkey) getActivity()).navigateToFragment(R.id.book); // Chuyển đến BookFragment
                        // Tạo Fragment và thêm dữ liệu vào Bundle
                        Fragment newFragment = new PayBook_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("nameuser", s);
                        bundle.putInt("kt", 1);
                        newFragment.setArguments(bundle); // Gắn Bundle vào Fragment

                        // Thay thế Fragment hiện tại bằng Fragment mới
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                        fragmentTransaction.commit();
                    }
                }
                else {
                    // Tạo Fragment và thêm dữ liệu vào Bundle
                    Fragment newFragment = new AccountFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("nameuser", s);
                    bundle.putInt("kt", 1);
                    newFragment.setArguments(bundle); // Gắn Bundle vào Fragment

                    // Thay thế Fragment hiện tại bằng Fragment mới
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    }
//}