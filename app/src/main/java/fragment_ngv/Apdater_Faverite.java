package fragment_ngv;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

import Category.DetailCategory_PhoFragment;


public class Apdater_Faverite extends RecyclerView.Adapter<Apdater_Faverite.ViewHolder>{

    private Context context;
    private ArrayList<Yeuthich> list;
    private DatabaseReference mDatabase;
    boolean isFavorite = false;
    private int iduser;
    private int kt1 , kt2  , id;
    private String kt3;
    public Apdater_Faverite(Context context, ArrayList<Yeuthich> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Apdater_Faverite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favourite, parent, false);
        return new Apdater_Faverite.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Apdater_Faverite.ViewHolder holder, int position) {
         Yeuthich yt = list.get(position);
        SharedPreferences sharett = context.getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",0);

         holder.imganh.setImageResource(yt.getImganh());
         holder.tvyeuthich.setText(yt.getTenmonan());


        holder.iconyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kt1 = 0;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("Listmasp").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            int i =0 ;
                            for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                String value = itemSnapshot.getValue(String.class);
                                if (value != null && value.equals(yt.getMasp()))
                                {
                                    kt3 = itemSnapshot.getKey();
                                    break;
                                }
                                i++;
                            }
                            kt1 = Integer.parseInt(kt3);
                            mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("Listmasp").child(kt1+"").removeValue()
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
                            mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("Listtenmonan").child(kt1+"").removeValue()
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
                            mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("Listidyt").child(kt1+"").removeValue()
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
                            mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("Listanh").child(kt1+"").removeValue()
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
                            mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("Listmota").child(kt1+"").removeValue()
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
                            mDatabase.child("YeuThich").child(iduser+"").child(yt.getMadm()).child("ListGia").child(kt1+"").removeValue()
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
                        Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        holder.cvyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new DetailCategory_PhoFragment();

                Bundle bundle = new Bundle();
                bundle.putString("madm", yt.getMadm());
                bundle.putString("masp", yt.getMasp());
                bundle.putInt("img", yt.getImganh());
                bundle.putString("gia", yt.getGia());
                bundle.putString("ten", yt.getTenmonan());
                bundle.putString("mota", yt.getMota());
                bundle.putInt("tt", yt.getTinhtrang());
                newFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imganh , iconyeuthich;
        private TextView tvyeuthich;
        private CardView cvyeuthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.imganhyeuthich);
            tvyeuthich = itemView.findViewById(R.id.tvtenyeuthich);
            iconyeuthich = itemView.findViewById(R.id.iconyeuthich);
            cvyeuthich = itemView.findViewById(R.id.cvyeuthich);


        }
    }
}
