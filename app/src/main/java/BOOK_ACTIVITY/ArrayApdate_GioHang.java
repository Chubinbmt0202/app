package BOOK_ACTIVITY;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.R;

import fragment_ngv.BookFragment;

public class ArrayApdate_GioHang extends RecyclerView.Adapter<ArrayApdate_GioHang.ViewHolder> {
    private Context contex;
    private ArrayList<GioHang> list;
    private OnTotalAmountChangedListener listener;
    private Database_GioHang data;
    private Load load;
    public ArrayApdate_GioHang(ArrayList<GioHang> list, Context contex, OnTotalAmountChangedListener listener,Load l) {
        this.list = list;
        this.contex = contex;
        this.listener = listener;
        this.load = l;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ArrayApdate_GioHang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.activity_list_orderbook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrayApdate_GioHang.ViewHolder holder, int position) {
        GioHang item = list.get(position);

        holder.tvtenmonan.setText(item.getTenmon());
        int a = item.getGia()/1000;
        holder.tvsumkq.setText(a +"." +"000 vnd");
        holder.imgmonan.setImageResource(item.getImg());

        holder.img_icondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(contex);
                alert.setTitle("Xác nhận");
                alert.setMessage("Bạn có chắc chắn muốn xóa "+ item.getTenmon() + " không?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data = new Database_GioHang(contex);
                        int giaca = 0;
                        int sosanh = 0;
                        String ktgia = "";
                        if (data != null) {
                            Cursor cs = data.getTenmonan();
                            cs.moveToFirst();
                            while (cs.isAfterLast() == false) {
                                String result = cs.getString(1).replaceAll("[^\\d]", "");
                                int gia = Integer.parseInt(result);
                                int ktsl = Integer.parseInt(holder.tvkqsolg.getText().toString());
                                giaca = Integer.parseInt(holder.tvsumkq.getText().toString().replaceAll("[^\\d]", ""));
                                sosanh = giaca / ktsl;
                                if (gia == sosanh) {
                                    ktgia = cs.getString(1);
                                    if (listener != null) {
                                        listener.onTotalAmountChanged(-giaca);
                                    }
                                    break;
                                }
                                cs.moveToNext();
                            }
                            if (ktgia != "") {
                                int i = data.XoaSanPham(holder.tvtenmonan.getText().toString(), ktgia);
                                load.Loaddata(holder.tvtenmonan.getText().toString(), sosanh);
                                if(holder.cb_book.isChecked())
                                {
                                    i -=1;
                                    SharedPreferences share = contex.getSharedPreferences("tinhtrang",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.putInt("tt",i);
                                    editor.commit();
                                }
                            }
                        }
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        holder.cb_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int b = 0;
                if(!holder.tvsumkq.getText().toString().equals("0 vnd"))
                {
                    String result = holder.tvsumkq.getText().toString().replaceAll("[^\\d]", "");
                    b = Integer.parseInt(result);
                }
                if(holder.cb_book.isChecked())
                {
                    holder.img_icondelete.setVisibility(View.VISIBLE);
                    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
                    String formattedSum = formatter.format(b);
                    holder.tvsumkq.setText(formattedSum + " vnd");
                    if (listener != null) {
                        listener.onTotalAmountChanged(b);
                    }
                }
                else
                {
                    holder.img_icondelete.setVisibility(View.GONE);
                    int tru = -b;
                    // Gọi listener để thông báo BookFragment
                    if (listener != null) {
                        listener.onTotalAmountChanged(tru);
                    }
                }
            }
        });


        holder.imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int solg = Integer.parseInt(holder.tvkqsolg.getText().toString());
                solg +=1 ;
                int sum = solg * item.getGia();
                holder.tvkqsolg.setText(String.valueOf(solg));
                NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

                if (holder.cb_book.isChecked())
                {
                    int b = 0;
                    String result = holder.tvsumkq.getText().toString().replaceAll("[^\\d]", "");
                    b = Integer.parseInt(result);
                    b += item.getGia();
                    String formattedSum1 = formatter.format(b);
                    holder.tvsumkq.setText(formattedSum1 + " vnd");

                    // Gọi listener để thông báo BookFragment
                    if (listener != null) {
                        listener.onTotalAmountChanged(item.getGia());
                    }
                }
                else
                {
                    String formattedSum = formatter.format(sum);
                    holder.tvsumkq.setText(formattedSum + " vnd");
                }


            }
        });

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = item.getGia();
                int solg = Integer.parseInt(holder.tvkqsolg.getText().toString());
                solg -= 1;
                if (solg > 0) {
                    int sum = item.getGia() / solg;
                    holder.tvkqsolg.setText(String.valueOf(solg));
                    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

                    if (holder.cb_book.isChecked()) {
                        int b = 0;
                        String result = holder.tvsumkq.getText().toString().replaceAll("[^\\d]", "");
                        b = Integer.parseInt(result);
                        b -= item.getGia();
                        String formattedSum1 = formatter.format(b);
                        holder.tvsumkq.setText(formattedSum1 + " vnd");
                        // Gọi listener để thông báo BookFragment
                        if (listener != null) {
                            int am = -(item.getGia());
                            listener.onTotalAmountChanged(am);
                        }
                    } else {
                        String formattedSum = formatter.format(sum);
                        holder.tvsumkq.setText(formattedSum + " vnd");
                    }
                } else {
                    data = new Database_GioHang(contex);
                    int giaca = 0;
                    int sosanh = 0;
                    String ktgia = "";
                    if (data != null) {
                        Cursor cs = data.getTenmonan();
                        cs.moveToFirst();
                        while (cs.isAfterLast() == false) {
                            String result = cs.getString(1).replaceAll("[^\\d]", "");  // Clean the result string
                            String rl1 = result.substring(0, result.length() - 1);

                            int gia = Integer.parseInt(rl1);
                            int ktsl = Integer.parseInt(holder.tvkqsolg.getText().toString());
                            giaca = Integer.parseInt(holder.tvsumkq.getText().toString().replaceAll("[^\\d]", ""));
                            sosanh = giaca / ktsl;
                            if (gia == sosanh) {
                                ktgia = cs.getString(1);
                                if (listener != null) {
                                    listener.onTotalAmountChanged(-giaca);
                                }
                                break;
                            }
                            cs.moveToNext();
                        }
                        if (ktgia != "") {
                            int i = data.XoaSanPham(holder.tvtenmonan.getText().toString(), ktgia);
                            load.Loaddata(holder.tvtenmonan.getText().toString(), sosanh);
                        }
                    }
                }
            }
        });

    }

    public interface Load {
        void Loaddata(String tenmonan , int gia);
    }

    public interface OnTotalAmountChangedListener {
        void onTotalAmountChanged(int formattedSum);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonan , tvgia , tvkqsolg , tvsumkq  ;
        ImageView imgmonan , imgadd , imgdelete , img_icondelete;
        CheckBox cb_book;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtenmonan = itemView.findViewById(R.id.tvtenmonan); // ID của TextView trong layout

            imgmonan = itemView.findViewById(R.id.imgmonan); //Anh
            tvkqsolg = itemView.findViewById(R.id.tvkqsolg); // Solg
            imgadd = itemView.findViewById(R.id.imgadd);
            imgdelete = itemView.findViewById(R.id.imgdelete);
            tvsumkq = itemView.findViewById(R.id.tvsumdongia);
            cb_book = itemView.findViewById(R.id.cb_book);
            img_icondelete = itemView.findViewById(R.id.img_icondelete);

        }
    }

}
