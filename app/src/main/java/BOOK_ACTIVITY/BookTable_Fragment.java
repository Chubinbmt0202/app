package BOOK_ACTIVITY;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.R;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fragment_ngv.BookFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookTable_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookTable_Fragment extends Fragment {
    //Kiem tra cac nut
    Boolean kt1 = false , kt2 = false , kt3 = false , kt4 = false , kt5 = false , kt6 = false , kt7 = false , kt8 = false , kt9 = false, kt10 = false ,
            kt11 = false ,kt12 = false ;

    // Khai báo cho combobox số người
    private Spinner cbbpeople;
    List<String> ktragio;
    // Khai bao nut back
    private ImageView imgback;
   // Khai báo cho thứ
    private TextView rank01 , rank02, rank03 , rank04 ,rank05;
    // Khai báo View
    private View view;
    // Khai báo nút Next
    private DatabaseReference mDatabase;
    private Button btnnext;
    // Khai báo radio button group
    private RadioGroup rdg ;

    // Khai báo cho ngày đặt
    private ImageView imgdate1 , imgdate2 , imgdate3 , imgdate4 , imgdate5 ;
    private TextView tv01 , tv02 , tv03 , tv04 , tv05 , tv06 ;

    // khai báo cho img ngày
    private ImageView imgban1 , imgban2 , imgban3 , imgban4 , imgban5 , imgban6 , imgban7 , imgban8
            , imgban9 , imgban10 ,imgban11 , imgban12;

    // Khai báo biến cho bàn
    private TextView ban1 , ban2 , ban3 , ban4 , ban5 , ban6 , ban7 , ban8 , ban9 , ban10 , ban11 , ban12;
    private List<String> ktraban;
    // Khai báo kết quả
    private TextView kqpeople , kqban , kqdate , kqgio ;
    private List<String> makh;
    // Khai báo giờ
    private Button bts1 , bts2 ,bts3 , bts4 , btt5 , btt6 , btt7  , btt8 , btc9 , btc10 , btc11 , btc12 ;
   int ktt ;
    private String nam1 , nam2 , nam3,nam4 ,nam5 ,nam;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String ktratt;

    public BookTable_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookTable_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookTable_Fragment newInstance(String param1, String param2) {
        BookTable_Fragment fragment = new BookTable_Fragment();
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


        view = inflater.inflate(R.layout.fragment_book_table_, container, false);
        // Khai báo biến cho kết quả
        kqpeople = (TextView) view.findViewById(R.id.kqpeople);
        kqban = (TextView) view.findViewById(R.id.kqban);
        kqdate = (TextView) view.findViewById(R.id.kqdate);
        kqgio = (TextView) view.findViewById(R.id.kqgio);

        BackTableFarme();
        NextFame();
        datacombobox();

        // Khai báo nút buttonimageview cho ngay
        imgdate1 = (ImageView) view.findViewById(R.id.cl_date01);
        imgdate2 = (ImageView) view.findViewById(R.id.cl_date02);
        imgdate3 = (ImageView) view.findViewById(R.id.cl_date03);
        imgdate4 = (ImageView) view.findViewById(R.id.cl_date04);
        imgdate5 = (ImageView) view.findViewById(R.id.cl_date05);

        // Khai báo ngày
        tv01 = (TextView) view.findViewById(R.id.date01);
        tv02 = (TextView) view.findViewById(R.id.date02);
        tv03 = (TextView) view.findViewById(R.id.date03);
        tv04 = (TextView) view.findViewById(R.id.date04);
        tv05 = (TextView) view.findViewById(R.id.date05);

        rank01 = (TextView) view.findViewById(R.id.rank01);
        rank02 = (TextView) view.findViewById(R.id.rank02);
        rank03 = (TextView) view.findViewById(R.id.rank03);
        rank04 = (TextView) view.findViewById(R.id.rank04);
        rank05 = (TextView) view.findViewById(R.id.rank05);

        Calendar cd = Calendar.getInstance();
        Calendar cd1 = Calendar.getInstance();
        Date today  = cd.getTime();

        SimpleDateFormat sd = new SimpleDateFormat("dd-MM");
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy");


        int thu = cd.get(Calendar.DAY_OF_WEEK);

        if(thu == Calendar.SUNDAY)
        {
            rank01.setText("Chủ nhật");
            rank02.setText("Thứ 2");
            rank03.setText("Thứ 3");
            rank04.setText("Thứ 4");
            rank05.setText("Thứ 5");
        }
        else if(thu == Calendar.MONDAY)
        {
            rank01.setText("Thứ 2");
            rank02.setText("Thứ 3");
            rank03.setText("Thứ 4");
            rank04.setText("Thứ 5");
            rank05.setText("Thứ 6");
        }
        else if(thu == Calendar.TUESDAY)
        {
            rank01.setText("Thứ 3");
            rank02.setText("Thứ 4");
            rank03.setText("Thứ 5");
            rank04.setText("Thứ 6");
            rank05.setText("Thứ 7");
        }
        else if(thu == Calendar.WEDNESDAY)
        {
            rank01.setText("Thứ 4");
            rank02.setText("Thứ 5");
            rank03.setText("Thứ 6");
            rank04.setText("Thứ 7");
            rank05.setText("Chủ nhật");
        }
        else if(thu == Calendar.THURSDAY)
        {
            rank01.setText("Thứ 5");
            rank02.setText("Thứ 6");
            rank03.setText("Thứ 7");
            rank04.setText("Chủ nhật");
            rank05.setText("Thứ 2");
        }
        else if(thu == Calendar.FRIDAY)
        {
            rank01.setText("Thứ 6");
            rank02.setText("Thứ 7");
            rank03.setText("Chủ nhật");
            rank04.setText("Thứ 2");
            rank05.setText("Thứ 3");
        }
        else
        {
            rank01.setText("Thứ 7");
            rank02.setText("Chủ nhật");
            rank03.setText("Thứ 2");
            rank04.setText("Thứ 3");
            rank05.setText("Thứ 4");
        }
        tv01.setText(sd.format(cd.getTime()));
        nam1 = sd1.format(cd.getTime());

        cd.add(Calendar.DAY_OF_MONTH, 1);
        tv02.setText(sd.format(cd.getTime()));
        nam2 = sd1.format(cd.getTime());

        cd.add(Calendar.DAY_OF_MONTH, 1);
        tv03.setText(sd.format(cd.getTime()));
        nam3 = sd1.format(cd.getTime());

        cd.add(Calendar.DAY_OF_MONTH, 1);
        tv04.setText(sd.format(cd.getTime()));
        nam4 = sd1.format(cd.getTime());

        cd.add(Calendar.DAY_OF_MONTH, 1);
        tv05.setText(sd.format(cd.getTime()));
        nam5 = sd1.format(cd.getTime());

// Khai báo biến cho bàn
        ban1 = (TextView) view.findViewById(R.id.textban01);
        ban2 = (TextView) view.findViewById(R.id.textban02);
        ban3 = (TextView) view.findViewById(R.id.textban03);
        ban4 = (TextView) view.findViewById(R.id.textban04);
        ban5 = (TextView) view.findViewById(R.id.textban05);
        ban6 = (TextView) view.findViewById(R.id.textban06);
        ban7 = (TextView) view.findViewById(R.id.textban07);
        ban8 = (TextView) view.findViewById(R.id.textban08);
        ban9 = (TextView) view.findViewById(R.id.textban09);
        ban10 = (TextView) view.findViewById(R.id.textban010);
        ban11 = (TextView) view.findViewById(R.id.textban011);
        ban12 = (TextView) view.findViewById(R.id.textban012);


// Khai báo imgview cho bàn
        imgban1 = (ImageView) view.findViewById(R.id.imgban01);
        imgban2 = (ImageView) view.findViewById(R.id.imgban02);
        imgban3 = (ImageView) view.findViewById(R.id.imgban03);
        imgban4 = (ImageView) view.findViewById(R.id.imgban04);
        imgban5 = (ImageView) view.findViewById(R.id.imgban05);
        imgban6 = (ImageView) view.findViewById(R.id.imgban06);
        imgban7 = (ImageView) view.findViewById(R.id.imgban07);
        imgban8 = (ImageView) view.findViewById(R.id.imgban08);
        imgban9 = (ImageView) view.findViewById(R.id.imgban09);
        imgban10 = (ImageView) view.findViewById(R.id.imgban010);
        imgban11 = (ImageView) view.findViewById(R.id.imgban011);
        imgban12 = (ImageView) view.findViewById(R.id.imgban012);

// Khai báo giờ
        bts1 = (Button) view.findViewById(R.id.button16);
        bts2 = (Button) view.findViewById(R.id.button7);
        bts3 = (Button) view.findViewById(R.id.button10);
        bts4 = (Button) view.findViewById(R.id.button15);

        btt5 = (Button) view.findViewById(R.id.button17);
        btt6 = (Button) view.findViewById(R.id.button8);
        btt7 = (Button) view.findViewById(R.id.button11);
        btt8 = (Button) view.findViewById(R.id.button14);

        btc9 = (Button) view.findViewById(R.id.button18);
        btc10 = (Button) view.findViewById(R.id.button9);
        btc11 = (Button) view.findViewById(R.id.button12);
        btc12 = (Button) view.findViewById(R.id.button13);


        // Khai báo chức năng giờ
        bts1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì"))
                {
                    Toast.makeText(getContext(),"Vui lòng chọn ngày trước",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(bts1.getText().toString().equals("Đã đặt"))
                    {

                    }
                    else {
                        bts1.setTextColor(Color.BLUE);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);

                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);

                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(bts1.getText());
                        kiemtraban();
                    }
                }
            }
        });

        bts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì"))
                {
                    Toast.makeText(getContext(),"Vui lòng chọn ngày trước",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(bts2.getText().toString().equals("Đã đặt"))
                    {

                    }
                    else {
                        bts2.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);

                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);

                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(bts2.getText());
                        kiemtraban();
                    }
                }
            }
        });

        bts3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(bts3.getText().toString().equals("Đã đặt")) {

                    } else {
                        bts3.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(bts3.getText());
                        kiemtraban();
                    }
                }
            }
        });

        bts4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(bts4.getText().toString().equals("Đã đặt")) {

                    } else {
                        bts4.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(bts4.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btt5.getText().toString().equals("Đã đặt")) {

                    } else {
                        btt5.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btt5.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btt6.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btt6.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btt6.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btt7.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btt7.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btt7.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btt8.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btt8.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btt8.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btc9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btc9.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btc9.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btc9.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btc10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btc10.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btc10.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btc10.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btc11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btc11.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btc11.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc12.setTextColor(Color.BLACK);
                        kqgio.setText(btc11.getText());
                        kiemtraban();
                    }
                }
            }
        });

        btc12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kqdate.getText().toString().equals("Không có gì")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    if(btc12.getText().toString().equals("Đã đặt")) {
                        // Do nothing
                    } else {
                        btc12.setTextColor(Color.BLUE);
                        bts1.setTextColor(Color.BLACK);
                        bts2.setTextColor(Color.BLACK);
                        bts3.setTextColor(Color.BLACK);
                        bts4.setTextColor(Color.BLACK);
                        btt5.setTextColor(Color.BLACK);
                        btt6.setTextColor(Color.BLACK);
                        btt7.setTextColor(Color.BLACK);
                        btt8.setTextColor(Color.BLACK);
                        btc9.setTextColor(Color.BLACK);
                        btc10.setTextColor(Color.BLACK);
                        btc11.setTextColor(Color.BLACK);
                        kqgio.setText(btc12.getText());
                        kiemtraban();
                    }
                }
            }
        });


        imgdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv01.setTextColor(Color.BLUE);
                tv02.setTextColor(Color.BLACK);
                tv03.setTextColor(Color.BLACK);
                tv04.setTextColor(Color.BLACK);
                tv05.setTextColor(Color.BLACK);
                nam = nam1;
                kqdate.setText(tv01.getText() +"-" + nam);

            }
        });

        imgdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv02.setTextColor(Color.BLUE);
                tv01.setTextColor(Color.BLACK);
                tv03.setTextColor(Color.BLACK);
                tv04.setTextColor(Color.BLACK);
                tv05.setTextColor(Color.BLACK);
                nam = nam2;
                kqdate.setText(tv02.getText()+"-" + nam);
            }
        });



        imgdate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv03.setTextColor(Color.BLUE);
                tv02.setTextColor(Color.BLACK);
                tv01.setTextColor(Color.BLACK);
                tv04.setTextColor(Color.BLACK);
                tv05.setTextColor(Color.BLACK);
                nam = nam3;
                kqdate.setText(tv03.getText()+"-" + nam);
            }
        });

        imgdate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv04.setTextColor(Color.BLUE);
                tv02.setTextColor(Color.BLACK);
                tv03.setTextColor(Color.BLACK);
                tv01.setTextColor(Color.BLACK);
                tv05.setTextColor(Color.BLACK);
                nam = nam4;
                kqdate.setText(tv04.getText()+"-" + nam);
            }
        });

        imgdate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv05.setTextColor(Color.BLUE);
                tv02.setTextColor(Color.BLACK);
                tv03.setTextColor(Color.BLACK);
                tv04.setTextColor(Color.BLACK);
                tv01.setTextColor(Color.BLACK);
                nam = nam5;
                kqdate.setText(tv05.getText() +"-" + nam);
            }
        });


        // khi click vào các bàn
        imgban1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban1.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban1.getText().toString().equals("Đã đặt")) {
                            if (kt1) {
                                kqban.setText(ban1.getText().toString().replaceAll("[^\\d]", ""));
                                ban1.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else if (!kt1) {
                                ban1.setTextColor(Color.BLACK);
                            }
                            kt1 = !kt1;
                        }
                    }
                }
            }
        });

        imgban2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban2.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban2.getText().toString().equals("Đã đặt")) {
                            if (kt2) {
                                kqban.setText(ban2.getText().toString().replaceAll("[^\\d]", ""));
                                ban2.setTextColor(Color.BLUE);
                                ban1.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban2.setTextColor(Color.BLACK);
                            }
                            kt2 = !kt2;
                        }
                    }
                }
            }
        });

        imgban3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban3.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban3.getText().toString().equals("Đã đặt")) {
                            if (kt3) {
                                kqban.setText(ban3.getText().toString().replaceAll("[^\\d]", ""));
                                ban3.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban3.setTextColor(Color.BLACK);
                            }
                            kt3 = !kt3;
                        }
                    }
                }
            }
        });

        imgban4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban4.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban4.getText().toString().equals("Đã đặt")) {
                            if (kt4) {
                                kqban.setText(ban4.getText().toString().replaceAll("[^\\d]", ""));
                                ban4.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban4.setTextColor(Color.BLACK);
                            }
                            kt4 = !kt4;
                        }
                    }
                }
            }
        });

        imgban5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban5.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban1.getText().toString().equals("Đã đặt")) {
                            if (kt5) {
                                kqban.setText(ban5.getText().toString().replaceAll("[^\\d]", ""));
                                ban5.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban5.setTextColor(Color.BLACK);
                            }
                            kt5 = !kt5;
                        }
                    }
                }
            }
        });

        imgban6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban6.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban6.getText().toString().equals("Đã đặt")) {
                            if (kt6) {
                                kqban.setText(ban6.getText().toString().replaceAll("[^\\d]", ""));
                                ban6.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban6.setTextColor(Color.BLACK);
                            }
                            kt6 = !kt6;
                        }
                    }
                }
            }
        });

        imgban7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban7.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban7.getText().toString().equals("Đã đặt")) {
                            if (kt7) {
                                kqban.setText(ban7.getText().toString().replaceAll("[^\\d]", ""));
                                ban7.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban7.setTextColor(Color.BLACK);
                            }
                            kt7 = !kt7;
                        }
                    }
                }
            }
        });

        imgban8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban8.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban8.getText().toString().equals("Đã đặt")) {
                            if (kt8) {
                                kqban.setText(ban8.getText().toString().replaceAll("[^\\d]", ""));
                                ban8.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban8.setTextColor(Color.BLACK);
                            }
                            kt8 = !kt8;
                        }
                    }
                }
            }
        });

        imgban9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban9.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban9.getText().toString().equals("Đã đặt")) {
                            if (kt9) {
                                kqban.setText(ban9.getText().toString().replaceAll("[^\\d]", ""));
                                ban9.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban5.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban9.setTextColor(Color.BLACK);
                            }
                            kt9 = !kt9;
                        }
                    }
                }
            }
        });

        imgban10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban10.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban10.getText().toString().equals("Đã đặt")) {
                            if (kt10) {
                                kqban.setText(ban10.getText().toString().replaceAll("[^\\d]", ""));
                                ban10.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban10.setTextColor(Color.BLACK);
                            }
                            kt10 = !kt10;
                        }
                    }
                }
            }
        });

        imgban11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban11.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!ban11.getText().toString().equals("Đã đặt")) {
                            if (kt11) {
                                kqban.setText(ban11.getText().toString().replaceAll("[^\\d]", ""));
                                ban11.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban12.setTextColor(Color.BLACK);
                            } else {
                                ban11.setTextColor(Color.BLACK);
                            }
                            kt11 = !kt11;
                        }
                    }
                }
            }
        });

        imgban12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ban12.getText().equals("Đã đặt")) {
                    if(kqgio.getText().toString().equals("Không có gì"))
                    {
                        Toast.makeText(getContext(),"Vui lòng chọn giờ trước" ,Toast.LENGTH_SHORT).show();
                    }
                        else {
                        if(!ban12.getText().toString().equals("Đã đặt")) {
                            if (kt12) {
                                kqban.setText(ban12.getText().toString().replaceAll("[^\\d]", ""));
                                ban12.setTextColor(Color.BLUE);
                                ban2.setTextColor(Color.BLACK);
                                ban3.setTextColor(Color.BLACK);
                                ban4.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                                ban6.setTextColor(Color.BLACK);
                                ban7.setTextColor(Color.BLACK);
                                ban8.setTextColor(Color.BLACK);
                                ban9.setTextColor(Color.BLACK);
                                ban10.setTextColor(Color.BLACK);
                                ban11.setTextColor(Color.BLACK);
                                ban1.setTextColor(Color.BLACK);
                            } else {
                                ban12.setTextColor(Color.BLACK);
                            }
                            kt12 = !kt12;
                        }
                        }
                }
            }
        });


            return view;
    }
    private void BackTableFarme()
    {
        imgback = view.findViewById(R.id.imgvback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder abl = new AlertDialog.Builder(getContext());
                abl.setTitle("Question");
                abl.setMessage("Bạn có muốn tiếp tục đặt bàn không.");
                abl.setIcon(R.drawable.iconngonghinh);
                abl.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                          dialogInterface.cancel();
                    }
                });
                abl.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment newFragment = new BookFragment(); // Replace with your XuFragment class

                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                abl.show();

            }
        });
    }

    // Khai bao chuc nang cho nut 'NEXT'
    // Chuyen tu BookTable_Fragment sang Book_Frame
    private void NextFame()
    {
        // Khai báo nút Next
        btnnext = view.findViewById(R.id.btnnext);

        // Chức năng next
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (kqban.getText().toString().equals("Không có gì"))
                {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày.", Toast.LENGTH_SHORT).show();
                }
                else if (kqpeople.getText().toString().equals("Không có gì"))
                {
                    Toast.makeText(getContext(), "Vui lòng chọn số người.", Toast.LENGTH_SHORT).show();
                }
                else if ( kqdate.getText().toString().equals("Không có gì"))
                {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày ngày.", Toast.LENGTH_SHORT).show();
                }
                else if(kqgio.getText().toString().equals("Không có gì"))
                {
                    Toast.makeText(getContext(), "Vui lòng chọn giờ.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Fragment newFragment = new BookFragment(); // Replace with your XuFragment class
                    Bundle args = new Bundle();
                    args.putString("gio", kqgio.getText().toString());
                    args.putString("ban", kqban.getText().toString());
                    args.putString("people",kqpeople.getText().toString());
                    args.putString("date",kqdate.getText().toString());
                    newFragment.setArguments(args);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void datacombobox()
    {
        cbbpeople = view.findViewById(R.id.cbbpeople);
        ArrayList<String> items = new ArrayList<>();
        items.add("1 đến 2 người");
        items.add("2 đến 4 người");
        items.add("4 đến 6 người");
        items.add("6 đến 10 người");
        items.add("Trên 10 người");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_item,
                items
        );


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbpeople.setAdapter(adapter);
        cbbpeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                kqpeople.setText(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Khi không chọn gì
            }
        });



    }

     private void kiemtraban()
     {
         ktraban = new ArrayList<>();
         mDatabase = FirebaseDatabase.getInstance().getReference();




         String dateToFind = kqgio.getText().toString().trim();
         mDatabase.child("DonHang").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()) {
                     for (DataSnapshot khachHangSnapshot : snapshot.getChildren()) {
                         String maKhachHang = khachHangSnapshot.getKey();

                         mDatabase.child("DonHang").child(maKhachHang).child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 for (DataSnapshot slg : snapshot.getChildren()) {
                                     String chuoi = slg.getKey();
                                     slg.child("Gio").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot dateSnapshot) {
                                             if (dateSnapshot.exists()) {
                                                 String dateValue = dateSnapshot.getValue(String.class);
                                                 if (dateToFind.equals(dateValue)) {
                                                     String ban = slg.child("Ban").getValue(String.class);
                                                     ktraban.add(ban);
                                                     kiemtrab(ktraban);
                                                 }
                                             }
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {
                                             Toast.makeText(getActivity(), "Lỗi khi truy vấn Giờ: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                         }
                                     });
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {
                                 Toast.makeText(getActivity(), "Lỗi ", Toast.LENGTH_SHORT).show();
                             }
                         });
                     }
                 } else {
                     Toast.makeText(getActivity(), "Không có dữ liệu DonHang", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(getActivity(), "Lỗi khi tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
         if(ktraban.size() == 0)
         {
             ban1.setText("Bàn 01");
             ban2.setText("Bàn 02");
             ban3.setText("Bàn 03");
             ban4.setText("Bàn 04");
             ban5.setText("Bàn 05");
             ban6.setText("Bàn 06");
             ban7.setText("Bàn 07");
             ban8.setText("Bàn 08");
             ban9.setText("Bàn 09");
             ban10.setText("Bàn 10");
             ban11.setText("Bàn 11");
             ban12.setText("Bàn 12");
         }
     }
     private void kiemtrab(List<String> ltban)
     {
         if(ltban.size() > 0) {
             //ban1 , ban2 , ban3 , ban4 , ban5 , ban6 , ban7 , ban8 , ban9 , ban10 , ban11 , ban12;
             for (int i = 0; i < ltban.size(); i++) {
                 if (ban1.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban1.setText("Đã đặt");
                 } else if (ban2.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban2.setText("Đã đặt");
                 } else if (ban3.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban3.setText("Đã đặt");
                 } else if (ban4.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban4.setText("Đã đặt");
                 } else if (ban5.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban5.setText("Đã đặt");
                 } else if (ban6.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban6.setText("Đã đặt");
                 } else if (ban7.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban7.setText("Đã đặt");
                 } else if (ban8.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban8.setText("Đã đặt");
                 } else if (ban9.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban9.setText("Đã đặt");
                 } else if (ban10.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban10.setText("Đã đặt");
                 } else if (ban11.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban11.setText("Đã đặt");
                 } else if (ban12.getText().toString().equals("Bàn " + ltban.get(i))) {
                     ban12.setText("Đã đặt");
                 }
             }
         }
     }

}