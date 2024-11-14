package Accout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import fragment_ngv.AccountFragment;

public class Sum_detailhistory_Activity extends AppCompatActivity {

    private int ttrang[] = {0,1,1,1};
    private String id[] = {"0004","0003","0002","0001"};
    private String ngaydat[] = {"30/10/2024", "22/10/2024","1/10/2024","8/8/2024"};

    private ImageView imgbackdetail;
    private ArrayList<Class_sumdeltaihistory> list ;
    private ArrayApdat_SumDetailHistory apdate ;
    private RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sum_detailhistory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgbackdetail = findViewById(R.id.imgbacksumdeitail);

        imgbackdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Sum_detailhistory_Activity.this, AccountFragment.class);
                startActivity(it);
            }
        });
        rcv = (RecyclerView) findViewById(R.id.rcv_sumdetaiil);
        rcv.setLayoutManager(new LinearLayoutManager(Sum_detailhistory_Activity.this));
        list = new ArrayList<>();
        for ( int i = 0 ; i < id.length ; i++)
        {

            list.add(new Class_sumdeltaihistory(id[i],ttrang[i],ngaydat[i]));
        }
        apdate = new ArrayApdat_SumDetailHistory(list,Sum_detailhistory_Activity.this);
        rcv.setAdapter(apdate);
    }
}