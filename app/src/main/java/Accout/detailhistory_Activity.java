package Accout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class detailhistory_Activity extends AppCompatActivity {

    // Khai báo nút 'OK' và nút 'Hủy Đơn Hàng'
    private Button  btn_dthsok , btn_dthsback ;

    private int img[] = {R.drawable.haisanca_cakhoto,R.drawable.img_baongubotoi,R.drawable.img_canuonghaivi};
    private int giagoc[] = {80000,45000,25000};
    private String tenmon[] = {"Cá khô tộ", "Bào ngư bơ tỏi","Cá nướng hai vị"};
    private int solg[] = {1,2,3};

    private ArrayList<Class_Detail> list ;
    private ArrayAdate_DetailHistory apdate ;
    private RecyclerView rcv;

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

        rcv = (RecyclerView) findViewById(R.id.rcv_detailhistory);
        rcv.setLayoutManager(new LinearLayoutManager(detailhistory_Activity.this));
        list = new ArrayList<>();
        for ( int i = 0 ; i < img.length ; i++)
        {
            int tg = giagoc[i] * solg[i];
            list.add(new Class_Detail(giagoc[i],tenmon[i],tg,solg[i],img[i]));
        }
        apdate = new ArrayAdate_DetailHistory(list,detailhistory_Activity.this);
        rcv.setAdapter(apdate);

        butttonOK();
        buttonHuyDonHang();

    }

    private void butttonOK()
    {
        btn_dthsok = (Button) findViewById(R.id.btn_deitailhtr_ok);
        btn_dthsok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(detailhistory_Activity.this,Sum_detailhistory_Activity.class);
                startActivity(it);
            }
        });
    }

    private void buttonHuyDonHang()
    {
        btn_dthsback = (Button) findViewById(R.id.btn_detailhtrback);
        btn_dthsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}