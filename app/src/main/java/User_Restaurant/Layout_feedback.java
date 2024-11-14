package User_Restaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apprestaurant.R;

import java.util.ArrayList;

import BOOK_ACTIVITY.BookTable_Fragment;
import fragment_ngv.AccountFragment;

public class Layout_feedback extends AppCompatActivity {
    ArrayList<PhanHoi_Restaurant> phanhoi ;
    String ten[] = {"Nguyen Van An" , "Dang Ngoc Dung" , "Nguyen Xuan Huyn" ,"Trinh Quang Thuan"};
    int anh[] = {R.drawable.ute,R.drawable.ute,R.drawable.ute,R.drawable.ute};
    String feedback[] = {"Thức ăn ngon","Nhà hàng sạch đẹp","Nước uống hơi nhạt nhưng không sao","Đồ ăn ngon"};
    ListView listv;
    Apdater_PhanHoi ph;

    // Khai báo imgview thoát cho phản hồi
    private LinearLayout lineback;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_feedbackrestaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        listv = findViewById(R.id.lvfeedback);
        phanhoi = new ArrayList<>();
        for (int i = 0 ; i < ten.length ; i++)
        {
            phanhoi.add(new PhanHoi_Restaurant(anh[i],ten[i],feedback[i]));
        }
        ph = new Apdater_PhanHoi(R.layout.children_layout_feedbackrestaurant,phanhoi,Layout_feedback.this);
        listv.setAdapter(ph);
        Thoatphanhoi();
    }
    private void Thoatphanhoi()
    {
        lineback = findViewById(R.id.linebackphanhoi);
        lineback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Layout_feedback.this, AccountFragment.class);
                startActivity(it);
            }
        });
    }
}
