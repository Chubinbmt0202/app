package BOOK_ACTIVITY;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.Accout_detail_userFragment;
import com.example.apprestaurant.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Intro_register_login.LoginActivity;
import Intro_register_login.RegisterActivity;
import Order.Class_CategoryBanhCuon;
import fragment_ngv.BookFragment;
import fragment_ngv.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayBook_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayBook_Fragment extends Fragment {

    private View view;
    private int kt1 = 0 , kt2 = 0 , kt3 = 0;
    private LinearLayout linepay;
    private Button btnpay;
    private Database_GioHang data;
    private DatabaseReference mDatabase;
    // Ngân hàng đã liên kết
    private CheckBox cbpay;
    private ArrayList<String>  anh ,tenmonan;
    private ArrayList<Integer> gia , slg;
    private LinearLayout line;
    private String mahd;
    // Khai bao cardcview ngan hang
    private CardView cvvt , cvmb , cvvtb , cvvcb , cvarb, cvtcb;
    private String people , ban , gio , ngay;
    private int ktbt;
    private TextView tvtongtienpay;
    private int id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayBook_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayBook_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayBook_Fragment newInstance(String param1, String param2) {
        PayBook_Fragment fragment = new PayBook_Fragment();
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
        view = inflater.inflate(R.layout.fragment_pay_book_, container, false);
        tvtongtienpay = view.findViewById(R.id.tvtongtienpay);
        ktbt = 0;
        SharedPreferences sharett = getActivity().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        id = sharett.getInt("id",0);

        if(getArguments()!= null)
        {
            Bundle bl = getArguments();
            tvtongtienpay.setText(bl.getString("sum"));
            people = bl.getString("people");
            ban = bl.getString("ban");
            gio = bl.getString("gio");
            ngay = bl.getString("date");
        }
        else
        {
            tvtongtienpay.setText("0 vnd");
        }
        line = view.findViewById(R.id.kt_login);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new Accout_detail_userFragment(); // Replace with your XuFragment class
                Bundle  bl = new Bundle();
                bl.putInt("bk",1);
                newFragment.setArguments(bl);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        PayThanhCong();
        Viettin();
        QuayLai();
        return view;
    }

    private void PayThanhCong() {
        btnpay = view.findViewById(R.id.btn_pay);
        cbpay = view.findViewById(R.id.cbpay);

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbpay.isChecked()) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                    ab.setMessage("Vui lòng thanh toán");
                    ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    ab.show();
                } else {
                    kt1 = 0;
                    kt2 = 0;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("DonHang").child(id+"").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                            kt1 = snapshot.exists() ? (int) snapshot.getChildrenCount() : 0;

                            mDatabase.child("DonHang").child(id+"").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot1) {
                                    if (snapshot1.exists()) {
                                        kt2 += snapshot1.child("1").getChildrenCount();
                                        kt2 += snapshot1.child("2").getChildrenCount();
                                        kt2 += snapshot1.child("3").getChildrenCount();
                                    }
                                        kt2 += 1;
                                        if (kt2 < 10) {
                                            mahd = "000" + kt2;
                                        } else if (kt2 >= 10 && kt2 < 100) {
                                            mahd = "00" + kt2;
                                        } else {
                                            mahd = "0" + kt2;
                                        }

                                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                                        Map<String, Object> newban = new HashMap<>();
                                        newban.put("Ban", ban);
                                        newban.put("Gio", gio);
                                        newban.put("Date", ngay);
                                        database.child("DonHang").child(id + "").child("1").child(mahd).setValue(newban)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                                    }
                                                });
                                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                                        DatabaseReference listPeopleRef = dbRef.child("Hang");
                                        listPeopleRef.child("People").child(String.valueOf(kt1)).setValue(people);
                                        listPeopleRef.child("Date").child(String.valueOf(kt1)).setValue(ngay);

                                        data = new Database_GioHang(getContext());
                                        Cursor cs = data.getTinhtrang();
                                        cs.moveToFirst();
                                        anh = new ArrayList<>();
                                        tenmonan = new ArrayList<>();
                                        gia = new ArrayList<>();
                                        slg = new ArrayList<>();

                                        if (cs.moveToFirst()) {
                                            do {
                                                String result = cs.getString(2).replaceAll("[^\\d]", "");
                                                if (!result.isEmpty()) {
                                                    int a = Integer.parseInt(result);
                                                    int imgColumnIndex = cs.getColumnIndex("IMG");
                                                    if (imgColumnIndex != -1) {
                                                        int img = cs.getInt(imgColumnIndex);
                                                        tenmonan.add(cs.getString(1));
                                                        String chuoianh = getResources().getResourceEntryName(img);
                                                        anh.add(chuoianh);
                                                        gia.add(a);
                                                        slg.add(cs.getInt(5));
                                                    } else {
                                                        Log.e("Database Error", "IMG column not found in the database");
                                                    }
                                                } else {
                                                    Log.e("Data Error", "Empty or invalid value found in the result column");
                                                }
                                            } while (cs.moveToNext());
                                        } else {
                                            Log.e("Database Error", "Cursor is empty");
                                        }
                                        if (anh != null && tenmonan != null) {
                                            Map<String, Object> newmonan = new HashMap<>();
                                            newmonan.put("Listgia", gia);
                                            newmonan.put("Listanh", anh);
                                            newmonan.put("Listsolg", slg);
                                            newmonan.put("Listtenmonan", tenmonan);
                                            database.child("DonHang").child(id + "").child("1").child(mahd).child("Monan").setValue(newmonan)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                                        }
                                                    });
                                            data = new Database_GioHang(getContext());
                                            data.XoaGioHang();
                                            Fragment newFragment = new Booksuccessfully_Fragment();
                                            FragmentManager fragmentManager = getParentFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        } else {
                                            Toast.makeText(getActivity(), "Lỗi list ?", Toast.LENGTH_SHORT).show();
                                        }


                                }
                                @Override
                                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                                    Toast.makeText(getActivity(), "Lỗi ", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void QuayLai()
    {
        linepay = view.findViewById(R.id.linepay);
        linepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new BookFragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }



    private void Viettin() {
        cvvt = view.findViewById(R.id.cv04);
        cvvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pay, null, false);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setView(dialogView);

                AlertDialog dialog = alertDialog.create();
                dialog.show();

                final ImageView imgview = dialogView.findViewById(R.id.imageView19);
                imgview.setImageResource(R.drawable.logo_vtb);

                final EditText edtstk = dialogView.findViewById(R.id.edtstk);
                final EditText edttentk = dialogView.findViewById(R.id.edttentk);
                final EditText edtngaycap = dialogView.findViewById(R.id.edtngaycap);
                final Button btnpay = dialogView.findViewById(R.id.btnpayy);

                btnpay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtstk.getText().toString().isEmpty() || edttentk.getText().toString().isEmpty() ||
                                edtngaycap.getText().toString().isEmpty()
                        ) {
                            Toast.makeText(getContext(), "Liên kết không thành công!", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else {
                            Toast.makeText(getContext(), "Liên kết thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
    }
        }
