package com.example.apprestaurant;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import BOOK_ACTIVITY.PayBook_Fragment;
import HOME.Nagigationkey;
import fragment_ngv.AccountFragment;

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
    private DatabaseReference mDatabase;
    private RadioButton rdnam , rdnu;
    private View view;
    private int iduser;
private int stt;
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
        BottomNavigationView bt = getActivity().findViewById(R.id.ngv_bottomNavigation);
        bt.setVisibility(View.GONE);
        view = inflater.inflate(R.layout.fragment_accout_detail_user, container, false);

        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",-1);

        edthoten = (EditText) view.findViewById(R.id.edtnameuser);

        edtemail = (EditText) view.findViewById(R.id.edt_email);

        edtphone = (EditText) view.findViewById(R.id.edtphone);

        imgback = (ImageView) view.findViewById(R.id.img_back);

        rdnam = view.findViewById(R.id.rd_nam);

        rdggtinh = view.findViewById(R.id.rdg_sex);

        btn_update = view.findViewById(R.id.btn_capnhat);

        rdnu = view.findViewById(R.id.rdnu);
        Exit();
        if(iduser != -1) {
            latthongtin();
            Kiemtraucapnhat();
        }
        else
        {
            Toast.makeText(getContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
        }
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
                        ((Nagigationkey) getActivity()).navigateToFragment(R.id.book);

                        Fragment newFragment = new PayBook_Fragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                        fragmentTransaction.commit();
                    }
                }
                else {
                    Fragment newFragment = new AccountFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }
       private  void Kiemtraucapnhat() {
           btn_update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (edthoten.getText().toString().trim().isEmpty() ||
                           edtemail.getText().toString().trim().isEmpty() ||
                           edtphone.getText().toString().trim().isEmpty()) {
                       Toast.makeText(getContext(), "Vui lòng nhập đầy đủ tên khách hàng , email , sdt.", Toast.LENGTH_LONG).show();
                   } else {
                       int h = 0;
                       if (rdnam.isChecked()) {
                           h = 1;
                       }
                       mDatabase = FirebaseDatabase.getInstance().getReference();
                       DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                       Map<String, Object> newban = new HashMap<>();
                       newban.put("SDT", edtphone.getText().toString());
                       newban.put("GioiTinh", h);
                       newban.put("Email", edtemail.getText().toString());
                       newban.put("name", edthoten.getText().toString());
                       database.child("User").child(iduser + "").updateChildren(newban)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                       Toast.makeText(getActivity(), "Cập nhật không thành công", Toast.LENGTH_LONG).show();
                                   }
                               });
                   }
               }
           });
       }

       private  void latthongtin() {
           mDatabase = FirebaseDatabase.getInstance().getReference();

           mDatabase.child("User").child(iduser+"").child("Email").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   edtemail.setText(snapshot.getValue(String.class));
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(getActivity(), "Lỗi khi tải người dùng", Toast.LENGTH_SHORT).show();
               }
           });

           mDatabase.child("User").child(iduser+"").child("SDT").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   edtphone.setText(snapshot.getValue(String.class));
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(getActivity(), "Lỗi khi tải người dùng", Toast.LENGTH_SHORT).show();
               }
           });

           mDatabase.child("User").child(iduser+"").child("name").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   edthoten.setText(snapshot.getValue(String.class));
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(getActivity(), "Lỗi khi tải người dùng", Toast.LENGTH_SHORT).show();
               }
           });

           mDatabase.child("User").child(iduser+"").child("GioiTinh").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(snapshot.getValue(Integer.class) ==1)
                   {
                       rdnam.setChecked(true);
                   }
                   else
                   {
                       rdnu.setChecked(true);
                   }
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(getActivity(), "Lỗi khi tải gioi tinh", Toast.LENGTH_SHORT).show();
               }
           });
       }
}

