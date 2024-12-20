package HOME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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

import BOOK_ACTIVITY.Database_GioHang;
import Category.DetailCategory_PhoFragment;
import Order.Apdate_OrderCategory;
import Order.Class_CategoryBanhCuon;
import fragment_ngv.Class_Home;

public class Apdate_CategoryHome extends RecyclerView.Adapter<Apdate_CategoryHome.ViewHolder>{
    private Context contex;
    private ArrayList<Class_Home> list;
    Database_GioHang data;
    private DatabaseReference mDatabase;
    int iduser;
    boolean isFavorite = false;
    private int  kt1,kt2 ;
    private String kt3;
    @Override
    public void onBindViewHolder(@NonNull Apdate_CategoryHome.ViewHolder holder,  int position) {

        Class_Home item = list.get(position);
        holder.tvtenmonan.setText(String.valueOf(item.getTenmonan()));
        holder.tvtggia.setText(String.valueOf(item.getGiadonvi()));
        holder.imgmonan.setImageResource(item.getImgmonan());

        SharedPreferences sharett = contex.getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",-1);


            if (item.getTraitim() == 1) {
                holder.imgtraitim.setImageResource(R.drawable.img_yeuthich);
                holder.imgtraitim.setTag(R.drawable.img_yeuthich);
            } else {
                holder.imgtraitim.setTag(R.drawable.btn_2);
            }

            if(iduser != -1) {
            holder.home_addmenujome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.imgmonan.setImageResource(item.getImgmonan());

                    data = new Database_GioHang(contex);
                    boolean kt = false;

                    if (data != null) {
                        Cursor cs = data.getTenmonan();
                        if (cs.getCount() != 0) {
                            cs.moveToFirst();
                            while (cs.isAfterLast() == false) {
                                if (item.getTenmonan().equals(cs.getString(0)) && item.getGiadonvi().equals(cs.getString(1))) {
                                    kt = true;
                                    return;
                                }
                                cs.moveToNext();
                            }
                            if (kt == false) {
                                data.Themsanpham(item.getTenmonan(), item.getGiadonvi(), item.getImgmonan());
                                Toast.makeText(contex, "Thêm món ăn thành công.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            data.Themsanpham(item.getTenmonan(), item.getGiadonvi(), item.getImgmonan());
                            Toast.makeText(contex, "Thêm món ăn thành công.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });


            holder.imgtraitim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = holder.getAdapterPosition();
                    if (id == RecyclerView.NO_POSITION) {
                        return;
                    }

                    int currentDrawableId = (int) holder.imgtraitim.getTag();
                    if (currentDrawableId == R.drawable.img_yeuthich) {
                        kt1 = 0;
                        holder.imgtraitim.setImageResource(R.drawable.btn_2);
                        holder.imgtraitim.setTag(R.drawable.btn_2);
                        String chuoi = null;
                        if (item.getMahome() == 1) {
                            chuoi = "Home/Home1/Listt";
                        } else {
                            chuoi = "Home/Home2/Listt";
                        }
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(chuoi);
                        Map<String, Object> updates = new HashMap<>();
                        updates.put(id + "", 0);

                        databaseReference.updateChildren(updates)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        // Load dish names
                        mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    int i = 0;
                                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                        String value = itemSnapshot.getValue(String.class);
                                        if (value != null && value.equals(item.getMasp())) {
                                            kt3 = itemSnapshot.getKey();
                                            break;
                                        }
                                        i++;
                                    }
                                    kt1 = Integer.parseInt(kt3);
                                    mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listmasp").child(kt1 + "").removeValue()
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
                                    mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listtenmonan").child(kt1 + "").removeValue()
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
                                    mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listidyt").child(kt1 + "").removeValue()
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
                                    mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listanh").child(kt1 + "").removeValue()
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
                                    mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listmota").child(kt1 + "").removeValue()
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
                                    mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("ListGia").child(kt1 + "").removeValue()
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
                                Toast.makeText(contex, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Home/Home1/Listt");
                        Map<String, Object> updates = new HashMap<>();
                        updates.put(id + "", 1);

                        databaseReference.updateChildren(updates)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));
                        holder.imgtraitim.setImageResource(R.drawable.img_yeuthich);
                        holder.imgtraitim.setTag(R.drawable.img_yeuthich);
                        kt1 = 0;
                        kt2 = 0;

                        mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listidyt").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    int i = 1;
                                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
//                                    i = Integer.parseInt(itemSnapshot.getKey().toString());
////                                    i -=1;
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
                                database.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listidyt").updateChildren(newid)
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
                                newtma.put(kt1 + "", item.getTenmonan());
                                database.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listtenmonan").updateChildren(newtma)
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
                                newmota.put(kt1 + "", item.getMota());
                                database.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listmota").updateChildren(newmota)
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
                                newmasp.put(kt1 + "", item.getMasp());
                                database.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listmasp").updateChildren(newmasp)
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

                                String chuoianh = contex.getResources().getResourceEntryName(item.getImgmonan());
                                Map<String, Object> newt = new HashMap<>();
                                newt.put(kt1 + "", chuoianh);
                                database.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("Listanh").updateChildren(newt)
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
                                String result = item.getGiadonvi().replaceAll("[^\\d]", "");
                                int dgia;
                                if (result.isEmpty()) {
                                    dgia = 0;
                                } else {
                                    dgia = Integer.parseInt(result);
                                }
                                newgia.put(kt1 + "", dgia + "");
                                database.child("YeuThich").child(iduser + "").child(item.getMadanhmuc()).child("ListGia").updateChildren(newgia)
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
                                Toast.makeText(contex, "Lỗi key ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    isFavorite = !isFavorite;
                }
            });
        }
        holder.imgmonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new DetailCategory_PhoFragment();


                Bundle bundle = new Bundle();
                bundle.putString("madm", item.getMadanhmuc());
                bundle.putString("masp", item.getMasp());
                bundle.putInt("img", item.getImgmonan());
                bundle.putString("gia", item.getGiadonvi());
                bundle.putString("ten", item.getTenmonan());
                bundle.putString("mota", item.getMota());
                newFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) contex).getSupportFragmentManager(); // Use getSupportFragmentManager for activity context
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }



    public Apdate_CategoryHome(ArrayList<Class_Home> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Apdate_CategoryHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.layout_categoryhome, parent, false);
        return new Apdate_CategoryHome.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonan , tvtggia  ;
        ImageView imgmonan , imgtraitim ,home_addmenujome;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tv_monanhome); // ID của TextView trong layout
            tvtggia = itemView.findViewById(R.id.tv_giadonvihome);
            imgmonan = itemView.findViewById(R.id.imgmonanhome);
            imgtraitim = itemView.findViewById(R.id.imgtraitimhome);
            home_addmenujome= itemView.findViewById(R.id.home_addmenujome);

        }
    }
}
