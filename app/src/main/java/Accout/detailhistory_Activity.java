package Accout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BOOK_ACTIVITY.BookTable_Fragment;
import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;

public class detailhistory_Activity extends AppCompatActivity {

    // Khai báo nút 'OK' và nút 'Hủy Đơn Hàng'
    private Button btn_dthsok, btn_dthsback, btn_ohanhoi;
    private LinearLayout linedgph;
    String  idd;
    private ImageView ngoisao1, ngoisao2, ngoisao3, ngoisao4, ngoisao5;
    boolean ktra = false;
    String ngaydat = null;
    private  int k = 0;
    private DatabaseReference mDatabase , mDatabase1;
    private TextView tv_tieude, tv_tgdahuy;
    private int sao = 0 ,id;
    private EditText edtph;
    private int iduser;
    private TextView textttcn,textttdb;
    private  ArrayList<String> tma;
    private ArrayList<Integer> gia , solg , img ;
  //  "haisanca_cakhoto","img_baongubotoi","img_canuonghaivi"
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

        // thông tin người dùng và thông tin đặt bàn
        textttcn = findViewById(R.id.textttcn);
        textttdb = findViewById(R.id.textttdb);

        SharedPreferences sharett = getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",0);
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
        idd = it.getStringExtra("id");
        int so = it.getIntExtra("ktra",0);
        List<Integer> ktlist = new ArrayList<Integer>();

        if(so!=0 && idd != null) {
            String chuoiktra ;
            if(so==1)
            {
                chuoiktra = "1";

            }
            else if(so==3)
            {
                chuoiktra = "3";
            }
            else
            {
                chuoiktra = "2";
            }

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("User").child(iduser+"").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    textttcn.setText("Tên: " + snapshot.child("name").getValue(String.class));
                    textttcn.setText(textttcn.getText()+" - SDT: " + snapshot.child("SDT").getValue(String.class));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mDatabase.child("DonHang").child(iduser+"").child(chuoiktra).child(idd).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    textttdb.setText("Bàn: " + snapshot.child("Ban").getValue(String.class));
                    textttdb.setText(textttdb.getText() + ", Ngày: " + snapshot.child("Date").getValue(String.class));
                    textttdb.setText(textttdb.getText() + ", Giờ: " + snapshot.child("Gio").getValue(String.class));
                    ngaydat = snapshot.child("Date").getValue(String.class);
                    if(chuoiktra.equals("1"))
                    {
                        tv_tgdahuy.setText("Đã đặt vào ngày " + ngaydat);
                    }
                    else if(chuoiktra.equals("3"))
                    {
                        tv_tgdahuy.setText("Đã hủy vào ngày " + ngaydat);
                    }
                    else
                    {
                        tv_tgdahuy.setText("Đã đặt vào ngày " + ngaydat);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });


            mDatabase1 = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/" + chuoiktra +"/" + idd);
            mDatabase1.child("Monan").child("Listtenmonan").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        tma.add(itemSnapshot.getValue(String.class));
                    }
                    mDatabase1.child("Monan").child("Listsolg").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                solg.add(itemSnapshot.getValue(Integer.class));
                            }
                            mDatabase1.child("Monan").child("Listanh").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    mDatabase1.child("Monan").child("Listgia").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                                gia.add(itemSnapshot.getValue(Integer.class));
                                            }
                                            rcv= findViewById(R.id.rcv_detailhistoryyy);
                                            rcv.setLayoutManager(new LinearLayoutManager(detailhistory_Activity.this));
                                            if (gia.size() == tma.size() && tma.size() == solg.size() && solg.size() == img.size()) {
                                                list = new ArrayList<>();
                                                for (int i = 0; i < tma.size(); i++) {
                                                    list.add(new Class_Detail(tma.get(i), gia.get(i), solg.get(i), img.get(i)));
                                                }
                                                if(list != null) {
                                                    apdate = new ArrayAdate_DetailHistory(list, detailhistory_Activity.this);
                                                    rcv.setAdapter(apdate);
                                                }
                                            } else {
                                                Log.e("Firebase", "Dữ liệu không đồng bộ.");
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

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

                                        DatabaseReference sourceRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/1/" + idd );
                                        DatabaseReference destinationRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/3/" + idd );

                                        sourceRef.addListenerForSingleValueEvent(new  ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    destinationRef.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                            if (error != null) {
                                                                Log.e("YourTag", "Lỗi khi sao chép dữ liệu: " + error.getMessage());
                                                            } else {

                                                                sourceRef.removeValue();
                                                                Toast.makeText(detailhistory_Activity.this, "Đã hủy đơn hàng thành công !", Toast.LENGTH_SHORT).show();
                                                                Intent it = new Intent(detailhistory_Activity.this, orderdetails_Fragment.class);
                                                                it.putExtra("huy", 1);
                                                                startActivity(it);
                                                                finish();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Log.d("YourTag", "Dữ liệu nguồn không tồn tại");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("YourTag", "Lỗi khi truy xuất dữ liệu nguồn: " + error.getMessage());
                                            }
                                        });

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
                        ab.setMessage("Bạn có chắc chắn muốn đặt lại ?");
                        ab.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference sourceRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/3/" + idd);
                                DatabaseReference destinationRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/1/" + idd);

                                sourceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            destinationRef.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                    if (error != null) {
                                                        Log.e("YourTag", "Lỗi khi sao chép dữ liệu: " + error.getMessage());
                                                    } else {
                                                        sourceRef.removeValue();
                                                        Toast.makeText(detailhistory_Activity.this, "Đã đặt thành công", Toast.LENGTH_SHORT).show();

//

                                                        Intent it = new Intent(detailhistory_Activity.this, orderdetails_Fragment.class);
                                                        it.putExtra("huy", 1);
                                                        startActivity(it);
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("YourTag", "Lỗi khi đọc dữ liệu: " + error.getMessage());
                                    }
                                });
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
            else if (a == 2)
            {
                btn_dthsback.setVisibility(View.GONE);
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