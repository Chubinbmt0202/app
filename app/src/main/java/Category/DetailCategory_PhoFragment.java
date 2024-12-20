package Category;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BOOK_ACTIVITY.BookTable_Fragment;
import BOOK_ACTIVITY.Database_GioHang;
import Order.Class_CategoryBanhCuon;
import fragment_ngv.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailCategory_PhoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCategory_PhoFragment extends Fragment {

    private ImageView imgmonan , imgyeuthich;
    private TextView tvtenmonan , tvgia, tvmota;
    private LinearLayout line;
    private View view;
    private Button btntgiohang;
    Database_GioHang data;
    private int linkiimg;
    private int iduser;
    private DatabaseReference mDatabase;
    ArrayList<Class_CategoryBanhCuon> ds = new ArrayList<>();
    private  int a = 0;
    private String madm , masp;
    private int kt1 , kt2;
    private String kt3;

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
        SharedPreferences sharett = getContext().getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",-1);

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new OrderFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            }
        });

        imgyeuthich = view.findViewById(R.id.foodyeuthich);
        imgmonan = view.findViewById(R.id.foodImage);
        tvtenmonan = view.findViewById(R.id.foodName);
        btntgiohang  = view.findViewById(R.id.btntgiohang);
        tvgia = view.findViewById(R.id.foodPrice);
        tvmota = view.findViewById(R.id.foodDescription);
        Bundle bundle = getArguments();
        if(bundle != null) {
            madm = bundle.getString("madm");
            masp = bundle.getString("masp");
            imgmonan.setImageResource(bundle.getInt("img"));
            tvgia.setText(bundle.getString("gia"));
            tvtenmonan.setText(bundle.getString("ten"));
            tvmota.setText(bundle.getString("mota"));
            linkiimg = bundle.getInt("img");
            a = bundle.getInt("tt");
        }
        if(iduser != -1) {
            if (a == 1) {
                imgyeuthich.setImageResource(R.drawable.img_yeuthich);
                imgyeuthich.setTag(R.drawable.img_yeuthich);
            }
            else
            {
                imgyeuthich.setTag(R.drawable.btn_2);
            }
            imgyeuthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    int currentDrawableId = (int) imgyeuthich.getTag();
                    if (currentDrawableId == R.drawable.img_yeuthich) {
                        imgyeuthich.setImageResource(R.drawable.btn_2);
                        imgyeuthich.setTag(R.drawable.btn_2);
                        kt1 = 0;
                        mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    int i = 0;
                                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                        String value = itemSnapshot.getValue(String.class);
                                        if (value != null && value.equals(masp)) {
                                            kt3 = itemSnapshot.getKey();
                                            break;
                                        }
                                        i++;
                                    }
                                    kt1 = Integer.parseInt(kt3);
                                    mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listmasp").child(kt1 + "").removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firebase", "Dữ liệu đã được xóa thành công.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Firebase", "Xóa dữ liệu thất bại: ", e);
                                                }
                                            });
                                    mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listtenmonan").child(kt1 + "").removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firebase", "Dữ liệu đã được xóa thành công.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Firebase", "Xóa dữ liệu thất bại: ", e);
                                                }
                                            });
                                    mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listidyt").child(kt1 + "").removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firebase", "Dữ liệu đã được xóa thành công.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Firebase", "Xóa dữ liệu thất bại: ", e);
                                                }
                                            });
                                    mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listanh").child(kt1 + "").removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firebase", "Dữ liệu đã được xóa thành công.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Firebase", "Xóa dữ liệu thất bại: ", e);
                                                }
                                            });
                                    mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listmota").child(kt1 + "").removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firebase", "Dữ liệu đã được xóa thành công.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Firebase", "Xóa dữ liệu thất bại: ", e);
                                                }
                                            });
                                    mDatabase.child("YeuThich").child(iduser + "").child(madm).child("ListGia").child(kt1 + "").removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firebase", "Dữ liệu đã được xóa thành công.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Firebase", "Xóa dữ liệu thất bại: ", e);
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        kt1 = 0;
                        kt2 = 0;
                        imgyeuthich.setImageResource(R.drawable.img_yeuthich);
                        imgyeuthich.setTag(R.drawable.img_yeuthich);
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("YeuThich").child(iduser + "").child(madm).child("Listidyt").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    int i = 1;
                                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
//
                                        if (snapshot.getChildrenCount() == i) {
                                            kt1 = Integer.parseInt(itemSnapshot.getKey());
                                        }
                                        i++;
                                    }
                                }
                                kt1++;
                                kt2 = kt1 + 1;
                                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                                Map<String, Object> newid = new HashMap<>();
                                newid.put(kt1 + "", kt2 + "");
                                database.child("YeuThich").child(iduser + "").child(madm).child("Listidyt").updateChildren(newid)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                                Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                            }
                                        });
                                Map<String, Object> newtma = new HashMap<>();
                                newtma.put(kt1 + "", tvtenmonan.getText().toString());
                                database.child("YeuThich").child(iduser + "").child(madm).child("Listtenmonan").updateChildren(newtma)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                                Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                            }
                                        });

                                Map<String, Object> newmota = new HashMap<>();
                                newmota.put(kt1 + "", tvmota.getText().toString());
                                database.child("YeuThich").child(iduser + "").child(madm).child("Listmota").updateChildren(newmota)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                                Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                            }
                                        });

                                Map<String, Object> newmasp = new HashMap<>();
                                newmasp.put(kt1 + "", masp);
                                database.child("YeuThich").child(iduser + "").child(madm).child("Listmasp").updateChildren(newmasp)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                                Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                            }
                                        });

                                String chuoianh = getContext().getResources().getResourceEntryName(imgmonan.getId());
                                Map<String, Object> newt = new HashMap<>();
                                newt.put(kt1 + "", chuoianh);
                                database.child("YeuThich").child(iduser + "").child(madm).child("Listanh").updateChildren(newt)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                                Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                            }
                                        });

                                Map<String, Object> newgia = new HashMap<>();
                                String result = tvgia.getText().toString().replaceAll("[^\\d]", "");
                                int dgia;
                                if (result.isEmpty()) {
                                    dgia = 0;
                                } else {
                                    dgia = Integer.parseInt(result);
                                }
                                newgia.put(kt1 + "", dgia + "");
                                database.child("YeuThich").child(iduser + "").child(madm).child("ListGia").updateChildren(newgia)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Firebase", "Dữ liệu đã được thêm thành công.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                                                Log.e("Firebase", "Thêm dữ liệu thất bại: ", e);
                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "Lỗi key ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

                        btntgiohang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                data = new Database_GioHang(getContext());
                                boolean kt = false;

                                if (data != null) {
                                    Cursor cs = data.getTenmonan();
                                    if (cs.getCount() != 0) {
                                        cs.moveToFirst();
                                        while (cs.isAfterLast() == false) {
                                            if (tvtenmonan.getText().toString().equals(cs.getString('0')) && tvgia.getText().toString().equals(cs.getString(1))) {
                                                kt = true;
                                                return;
                                            }
                                            cs.moveToNext();
                                        }

                                        if (kt == false) {

                                            data.Themsanpham(tvtenmonan.getText().toString(), tvgia.getText().toString(), bundle.getInt("img"));
                                            Toast.makeText(getContext(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        data.Themsanpham(tvtenmonan.getText().toString(), tvgia.getText().toString(), bundle.getInt("img"));
                                        Toast.makeText(getContext(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        });
                    }

                    else {
                        Toast.makeText(getContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();

                    }
                    return view;
                }
}