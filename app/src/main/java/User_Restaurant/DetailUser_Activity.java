package User_Restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.apprestaurant.R;

import fragment_ngv.AccountFragment;
import fragment_ngv.HomeFragment;

public class DetailUser_Activity extends AppCompatActivity {
    private EditText edthoten , edtemail , edtphone ;
    private RadioGroup rdggtinh;
    private ImageView imgback , imguser;
    private TextView tv_edit;
    private Button btn_update;
    private RadioButton rdnam , rdnu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edthoten = (EditText) findViewById(R.id.edtnameuser);

        edtemail = (EditText) findViewById(R.id.edt_email);

        edtphone = (EditText) findViewById(R.id.edtphone);

        imgback = (ImageView) findViewById(R.id.img_back);

        rdnam = findViewById(R.id.rd_nam);

        rdggtinh = findViewById(R.id.rdg_sex);

        btn_update = findViewById(R.id.btn_capnhat);

        rdnu = findViewById(R.id.rdnu);
        Laydatadki();
        SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
        if(share.getInt("kt",0) == 1)
        {
            edthoten.setText(share.getString("name",""));
            edtphone.setText(share.getString("phone",""));
            edtemail.setText(share.getString("email",""));
            if(share.getInt("ck",0) == 1)
            {
                rdnam.setChecked(true);
            }
            else
            {
                rdnu.setChecked(true);
            }
        }


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edthoten.getText().toString().trim().isEmpty() ||
                        edtemail.getText().toString().trim().isEmpty() ||
                        edtphone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(DetailUser_Activity.this, "Vui lòng nhập đầy đủ tên khách hàng , email , sdt.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);

                    SharedPreferences.Editor editor = share.edit();
                    editor.clear();
                    editor.putString("name", edthoten.getText().toString());
                    editor.putString("email", edtemail.getText().toString());
                    editor.putString("phone", edtphone.getText().toString());
                    editor.putInt("ck", rdnam.isChecked() ? 1 : 0);
                    editor.putInt("kt", 1);
                    editor.commit();
                    Toast.makeText(DetailUser_Activity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                }

            }
        });
        Exit();

    }

    private void Exit()
    {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = " " ;
                SharedPreferences share = getSharedPreferences("user",MODE_PRIVATE);
                if(share.getInt("kt",0) == 1)
                {
                    s = share.getString("name",null);
                }
                Intent it = new Intent(DetailUser_Activity.this,AccountFragment.class);
                it.putExtra("nameuser",s);
                it.putExtra("kt",1);
                startActivity(it);
            }
        });
    }
    private void Laydatadki() {
        Intent it = getIntent();
        String name = it.getStringExtra("name");
        String email = it.getStringExtra("email");
        String phone = it.getStringExtra("phone");

        if (name != null && !name.isEmpty()) {
            edthoten.setText(name);
        }

        if (email != null && !email.isEmpty()) {
            edtemail.setText(email);
        }

        if (phone != null && !phone.isEmpty()) {
            edtphone.setText(phone);
        }
    }



}