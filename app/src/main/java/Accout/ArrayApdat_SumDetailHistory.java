package Accout;

import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class ArrayApdat_SumDetailHistory extends RecyclerView.Adapter<ArrayApdat_SumDetailHistory.ViewHolder>{
    private int iduser;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Class_sumdeltaihistory item = list.get(position);
        holder.idhoadon.setText("Hóa đơn #" + item.getIdhoadon() );
        holder.textngaydat.setText(item.getNgaydat());
        SharedPreferences sharett = contex.getSharedPreferences("idnguoidung", Context.MODE_PRIVATE);
        iduser = sharett.getInt("id",0);
        if(item.getTinhtrang() == 2)
        {
            holder.texttinhtrang.setText("Đặt thành công");
            holder.btnchitiet.setText("Phản hổi");
        }
        else if(item.getTinhtrang() == 3)
        {
            holder.texttinhtrang.setText("Đã hủy");
            holder.btnchitiet.setText("Đặt lại");
        }
        else if (item.getTinhtrang() == 1)
        {
            holder.texttinhtrang.setText("Đã đặt bàn");
            holder.btnchitiet.setText("Hủy hóa đơn");
        }

        holder.btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( holder.btnchitiet.getText().toString().equals("Hủy hóa đơn"))
                {
                    AlertDialog.Builder ab = new AlertDialog.Builder(contex);
                    ab.setMessage("Bạn có chăc chắn muốn hủy ?");
                    ab.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference sourceRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/1/" + item.getIdhoadon() );
                            DatabaseReference destinationRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/3/" + item.getIdhoadon() );

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
                                                    Toast.makeText(contex, "Đã hủy đơn hàng thành công !", Toast.LENGTH_SHORT).show();
                                                    Log.d("YourTag", "Sao chép dữ liệu thành công");
                                                    sourceRef.removeValue();
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
                            Toast.makeText(contex, "Hủy không thành công", Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }
                    }).show();
                }
                else if( holder.btnchitiet.getText().toString().equals("Phản hổi"))
                {
                    Intent it = new Intent(contex, detailhistory_Activity.class);
                    it.putExtra("ktra",4);
                    it.putExtra("id",item.getIdhoadon());
                    startActivity(contex,it,null);
                }
                else if( holder.btnchitiet.getText().toString().equals("Đặt lại"))
                {
                    AlertDialog.Builder ab = new AlertDialog.Builder(contex);
                    ab.setMessage("Bạn có chăc chắn muốn đặt lại ?");
                    ab.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference sourceRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/3/" + item.getIdhoadon() );
                            DatabaseReference destinationRef = FirebaseDatabase.getInstance().getReference("DonHang/" + iduser + "/1/" + item.getIdhoadon() );

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
                                                    Toast.makeText(contex, "Đặt thành công !", Toast.LENGTH_SHORT).show();
                                                    sourceRef.removeValue();
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
                            Toast.makeText(contex, "Đặt không thành công", Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }
                    }).show();
                }

            }
        });

        holder.cdorderdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(contex, detailhistory_Activity.class);
                if(item.getTinhtrang() == 1)
                {
                    it.putExtra("ktra",1);
                }
                else if(item.getTinhtrang() == 3)
                {
                    it.putExtra("ktra",3);
                }
                else if(item.getTinhtrang() == 2)
                {
                    it.putExtra("ktra",2);
                }
                else if(item.getTinhtrang() == 4)
                {
                    it.putExtra("ktra",4);
                }
                it.putExtra("id",item.getIdhoadon());
                startActivity(contex,it,null);
            }
        });

    }

    private Context contex;
    private ArrayList<Class_sumdeltaihistory> list;


    public ArrayApdat_SumDetailHistory(ArrayList<Class_sumdeltaihistory> list, Context contex) {
        this.list = list;
        this.contex = contex;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ArrayApdat_SumDetailHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.children_activity_sum_detailhistory, parent, false);
        return new ArrayApdat_SumDetailHistory.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textngaydat , texttinhtrang , idhoadon ;
        Button btnchitiet ;
        CardView cdorderdetail;
        public ViewHolder(View itemView) {
            super(itemView);
            idhoadon = itemView.findViewById(R.id. idhoadon); // ID của TextView trong layout
            texttinhtrang = itemView.findViewById(R.id.tv_tinhtrang);
            textngaydat = itemView.findViewById(R.id.tv_ngaydat);
            btnchitiet = itemView.findViewById(R.id.btn_detail);
            cdorderdetail = itemView.findViewById(R.id.cdorderdetail);
        }
    }
}
