package Accout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.security.identity.CipherSuiteNotSupportedException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BOOK_ACTIVITY.BookTable_Fragment;
import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;

public class detailhistory_Activity extends AppCompatActivity {

    // Khai báo nút 'OK' và nút 'Hủy Đơn Hàng'
    private Button btn_dthsok, btn_dthsback, btn_ohanhoi;
    private LinearLayout linedgph;
    private ImageView ngoisao1, ngoisao2, ngoisao3, ngoisao4, ngoisao5;
    boolean ktra = false;
    private DatabaseReference mDatabase;
    private TextView tv_tieude, tv_tgdahuy;
    private int sao = 0 ,id;
    private EditText edtph;
    private  ArrayList<String> tma;
    private ArrayList<Integer> gia , solg , img ;
//    private int img[] = {R.drawable.haisanca_cakhoto, R.drawable.img_baongubotoi, R.drawable.img_canuonghaivi};
//    private int giagoc[] = {80000, 45000, 25000};
//    private String tenmon[] = {"Cá khô tộ", "Bào ngư bơ tỏi", "Cá nướng hai vị"};
//    private int solg[] = {1, 2, 3};
    private ArrayAdate_DetailHistory apdate;
    private RecyclerView rcv;
    private ArrayList<Class_Detail> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailhistory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtph = findViewById(R.id.edtphanhoi);
        tv_tieude = findViewById(R.id.tv_tieudehuy);
        tv_tgdahuy = findViewById(R.id.tv_tgdahuyhd);
        tv_tgdahuy.setText("Đã đặt vào lúc 9:00 18/11/2024");
        linedgph = findViewById(R.id.linedgph);
        btn_ohanhoi = findViewById(R.id.btn_ohanhoi);


        linedgph.setVisibility(View.GONE);

        ngoisao1 = findViewById(R.id.imgngoisao1);
        ngoisao2 = findViewById(R.id.imgngoisao2);
        ngoisao3 = findViewById(R.id.imgngoisao3);
        ngoisao4 = findViewById(R.id.imgngoisao4);
        ngoisao5 = findViewById(R.id.imgngoisao5);

        ngoisao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ktra) {
                    ngoisao1.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao5.setImageResource(R.drawable.ngoisao_0);
                    ngoisao2.setImageResource(R.drawable.ngoisao_0);
                    ngoisao3.setImageResource(R.drawable.ngoisao_0);
                    ngoisao4.setImageResource(R.drawable.ngoisao_0);
                    sao =1;
                } else {
                    ngoisao1.setImageResource(R.drawable.ngoisao_0);
                    sao =0;
                }
                ktra = !ktra;
            }
        });

        ngoisao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ktra) {
                    ngoisao1.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao2.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao3.setImageResource(R.drawable.ngoisao_0);
                    ngoisao4.setImageResource(R.drawable.ngoisao_0);
                    ngoisao5.setImageResource(R.drawable.ngoisao_0);
                    sao = 1;
                } else {
                    ngoisao1.setImageResource(R.drawable.ngoisao_0);
                    ngoisao2.setImageResource(R.drawable.ngoisao_0);
                    sao =0;
                }
                ktra = !ktra;
            }
        });

        ngoisao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ktra) {
                    ngoisao1.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao2.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao3.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao4.setImageResource(R.drawable.ngoisao_0);
                    ngoisao5.setImageResource(R.drawable.ngoisao_0);
                    sao = 1;
                } else {
                    ngoisao1.setImageResource(R.drawable.ngoisao_0);
                    ngoisao2.setImageResource(R.drawable.ngoisao_0);
                    ngoisao3.setImageResource(R.drawable.ngoisao_0);
                    sao =0;
                }
                ktra = !ktra;
            }
        });

        ngoisao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ktra) {
                    ngoisao1.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao2.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao3.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao4.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao5.setImageResource(R.drawable.ngoisao_0);
                    sao =1;
                } else {
                    ngoisao1.setImageResource(R.drawable.ngoisao_0);
                    ngoisao2.setImageResource(R.drawable.ngoisao_0);
                    ngoisao3.setImageResource(R.drawable.ngoisao_0);
                    ngoisao4.setImageResource(R.drawable.ngoisao_0);
                    sao =0;
                }
                ktra = !ktra;
            }
        });

        ngoisao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ktra) {
                    ngoisao1.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao2.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao3.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao4.setImageResource(R.drawable.ngoisoi_2);
                    ngoisao5.setImageResource(R.drawable.ngoisoi_2);
                    sao = 1;
                } else {
                    ngoisao1.setImageResource(R.drawable.ngoisao_0);
                    ngoisao2.setImageResource(R.drawable.ngoisao_0);
                    ngoisao3.setImageResource(R.drawable.ngoisao_0);
                    ngoisao4.setImageResource(R.drawable.ngoisao_0);
                    ngoisao5.setImageResource(R.drawable.ngoisao_0);
                    sao =0;
                }
                ktra = !ktra;
            }
        });

        tma = new ArrayList<>();
        gia = new ArrayList<>();
        solg = new ArrayList<>();
        img = new ArrayList<>();

        Intent it = getIntent();
        int so = it.getIntExtra("id",-1);
        if(so!=-1) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Hang").child("MonAN").child(String.valueOf(so)).child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        tma.add(itemSnapshot.getValue(String.class));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Hang").child("MonAN").child(String.valueOf(so)).child("Listsolg").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        solg.add(itemSnapshot.getValue(Integer.class));
                   }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Hang").child("MonAN").child(String.valueOf(so)).child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String imageName = itemSnapshot.getValue(String.class);
                        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                        if (resourceId != 0) {
                            img.add(resourceId);
                        } else {
                            Toast.makeText(detailhistory_Activity.this,"ảnh lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Hang").child("MonAN").child(String.valueOf(so)).child("ListGia").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        gia.add(itemSnapshot.getValue(Integer.class));
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        rcv= findViewById(R.id.rcv_detailhistoryyy);
        rcv.setLayoutManager(new LinearLayoutManager(detailhistory_Activity.this));
        if (gia.size() == tma.size() && tma.size() == solg.size() && solg.size() == img.size()) {
            for (int i = 0; i < tma.size(); i++) {
                list.add(new Class_Detail(tma.get(i), gia.get(i), solg.get(i), img.get(i)));
            }
            apdate = new ArrayAdate_DetailHistory(list, detailhistory_Activity.this);
            rcv.setAdapter(apdate);
        } else {
            Log.e("Firebase", "Dữ liệu không đồng bộ.");
        }
        butttonOK();
        buttonHuyDonHang();

    }



    private void butttonOK() {
        btn_dthsok = (Button) findViewById(R.id.btn_deitailhtr_ok);
        btn_dthsok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = getIntent();
                if (it != null) {
                    int a = it.getIntExtra("ktra", 0);
                    if (a == 2 || a==1 || a==4) {
                        Fragment fragment = new Sum_detailhostoryFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.ngv_viewPager, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                    else if (a==3)
                    {
//                        Cancel_DetaildetailhistoryFragment fragment = new Cancel_DetaildetailhistoryFragment();
//                        // Replace the current fragment with the new one
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.ngv_viewPager, fragment) // Replace with the correct container ID
//                                .addToBackStack(null) // Optional: Adds this transaction to the back stack
//                                .commit();
                        Intent ik = new Intent(detailhistory_Activity.this,Cancel_DetaildetailhistoryFragment.class);
                        startActivity(ik);
                    }
                }

            }
        });
    }

    private void buttonHuyDonHang() {
        btn_dthsback = (Button) findViewById(R.id.btn_detailhtrback);
        Intent it = getIntent();
        if (it != null) {
            int a = it.getIntExtra("ktra", 1);
            id = it.getIntExtra("id",0);
            if (a == 1) {
                btn_dthsback.setVisibility(View.VISIBLE);
                btn_dthsback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(detailhistory_Activity.this);
                        ab.setMessage("Bạn có chăc chắn muốn hủy ?");
                        ab.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hang/Listt");
                                Map<String, Object> updates = new HashMap<>();
                                updates.put(id+"", 3);
                                databaseReference.updateChildren(updates)
                                        .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                                        .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));

                                Toast.makeText(detailhistory_Activity.this, "Đã hủy đơn hàng thành công !", Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(detailhistory_Activity.this, ArrayApdat_SumDetailHistory.class);
                                it.putExtra("huy", 1);
                                startActivity(it);
                                finish();
                            }
                        });
                        ab.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                    }

                });
            } else if (a == 3) {
                tv_tieude.setText("Chi Tiết Hóa Đơn Đã Hủy");
                tv_tgdahuy.setVisibility(View.VISIBLE);
                btn_dthsback.setText("Đặt Lại");
                btn_dthsback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(detailhistory_Activity.this);
                        ab.setMessage("Bạn có chăc chắn muốn đặt lại ?");
                        ab.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(detailhistory_Activity.this, "Đã đặt thành công", Toast.LENGTH_SHORT).show();
                                Intent k = getIntent();
                                String idtt = k.getStringExtra("id");
                                // Create the fragment and pass the data using Bundle
                                Cancel_DetaildetailhistoryFragment fragment = new Cancel_DetaildetailhistoryFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("iddonhang", idtt);
                                fragment.setArguments(bundle);

                                // Replace the current fragment with the new one
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.ngv_viewPager, fragment) // Replace with the correct container ID
                                        .addToBackStack(null) // Optional: Adds this transaction to the back stack
                                        .commit();
                            }
                        });
                        ab.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                    }
                });

            }
            else if (a == 4)
            {
                linedgph.setVisibility(View.VISIBLE);
                btn_ohanhoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edtph.getText().toString().isEmpty())
                        {
                            Toast.makeText(detailhistory_Activity.this, "Vui lòng nhập thông tin phản hồi.", Toast.LENGTH_SHORT).show();
                        }
                        if( sao == 0)
                        {
                            Toast.makeText(detailhistory_Activity.this, "Vui lòng đánh giá sao!.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(detailhistory_Activity.this, "Đã phản hồi thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    }

}