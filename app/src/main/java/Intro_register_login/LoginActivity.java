package Intro_register_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apprestaurant.R;

import HOME.Nagigationkey;

public class LoginActivity extends AppCompatActivity {
    private TextView txtdki;
    private Button btn_login;
    private CheckBox cbghinho;
    private SharedPreferences share;
    private int check;
    private EditText edtsdt, edtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Thiết lập padding cho view chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view
        cbghinho = findViewById(R.id.cbghinho);
        txtdki = findViewById(R.id.txt_dangky);
        edtsdt = findViewById(R.id.edtsearch1);
        edtpass = findViewById(R.id.pass);
        btn_login = findViewById(R.id.btn_dangnhap); // Khởi tạo btn_login

        // Lưu trạng thái đăng nhập
        share = getSharedPreferences("mysave", MODE_PRIVATE);
        check = share.getInt("cb", 0);
        if (check == 1) {
            cbghinho.setChecked(true);
            edtsdt.setText(share.getString("tk", ""));
            edtpass.setText(share.getString("mk", ""));
        }

        // Thiết lập sự kiện cho TextView đăng ký
        txtdki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
                finish();
            }
        });

        // Thiết lập sự kiện cho nút đăng nhập
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtsdt.getText().toString().isEmpty() || edtpass.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("tk", edtsdt.getText().toString());
                    editor.putString("mk", edtpass.getText().toString());
                    editor.putInt("cb", 1);
                    editor.apply(); // Sử dụng apply() thay vì commit()

                    Intent it = new Intent(LoginActivity.this, Nagigationkey.class);
                    startActivity(it);
                    finish();
                }
            }
        });
    }
}
